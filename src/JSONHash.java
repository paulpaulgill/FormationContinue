import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.*;
import org.apache.commons.io.IOUtils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.*;

public class JSONHash {
    private final String fichiers_entree;
    private final String fichiers_sortie;
    private JSONObject jsonO;
    Boolean complet = true;
    JSONArray list1 = new JSONArray();
    JSONObject obj = new JSONObject();
    ArrayList<String> categories = new ArrayList<>();
    boolean succes;
    int heureMax;
    int heures = 0;
    final Date DATE_MAX = new GregorianCalendar(2022, Calendar.APRIL, 1).getTime();
    final Date DATE_MIN =  new GregorianCalendar(2020, Calendar.APRIL, 1).getTime();
    JSONArray activites = new JSONArray();

    public void Categories()
    {
        categories.addAll(Arrays.asList("cours", "atelier","séminaire","collogue","conférence"
                ,"lecture dirigée","présentation","groupe de discussion","projet de recherche","rédaction professionnelle"));
    }

    /**
     * Constructeur de l'objet GestionJSON qui facilite le calcul et la manipulation
     * des données du fichier d'entrée.
     * @param entree le fichier d'entré
     * @param sortie le fichier de sortie
     */
    public JSONHash(String entree, String sortie)
    {
        this.fichiers_entree = entree;
        this.fichiers_sortie = sortie;
    }

    /**
     * Lis le fichier JSON d'entrée et l'insert dans un objet JSON
     * @throws FormationContinueException
     */
    public void chargement() throws FormationContinueException{
        try{
            suiteChargement();
        }catch(FileNotFoundException erreur) {
            System.err.println("Le fichier donné est introuvable.");
            System.exit(-2);
        }catch (JSONException erreur){
            System.err.println("Ce n'est pas un fichier JSON bien formaté");
            System.exit(-3);
        }catch (IOException erreur){
            throw new FormationContinueException("Une erreur inattendue est survenue");
        }
    }

    /**
     *  Nécessaire pour respect de 10 ligne par methode.
     * @throws IOException
     */
    private void suiteChargement() throws IOException
    {
        String stringJson = IOUtils.toString(new FileReader(fichiers_entree, StandardCharsets.UTF_8));
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(stringJson);
        this.jsonO = jsonObject;
        succes = true;
        activites = (JSONArray) JSONSerializer.toJSON(jsonO.getString("activites"));
    }

    /**
     * Écris dans le fichier résultat si le cycle est complet et les erreurs
     * dans le fichier d'entré.
     * @throws FileNotFoundException
     */
    public void exportationErreur() throws IOException
    {
        PrintWriter sortie = new PrintWriter(fichiers_sortie, StandardCharsets.UTF_8);
        obj.put("complet", complet);
        obj.put("erreur", list1);
        sortie.write(obj.toString(3));
        sortie.flush();
        sortie.close();
    }

    /**
     * Compare les heures de l'activité avec le max donné en param
     * @param heureMax  Le nombre d'heure maximum a comparé avec les heure de l'activité
     * @param i index de l'activité a comparé dans le JSONArray
     * @param activites JSONArray de toute les activités
     * @return si les heures sont supérieur au heures max alors elle retourne
     * la valeur max sinon elle retourne les heures original de l'activité
     */
    private int confirmerHeure(int heureMax, int i, JSONArray activites){
        int heure = activites.getJSONObject(i).getInt("heures");
        if(heure > heureMax){
            heure = heureMax;
        }else if(heure < 1) {
            heure = 0;
            list1.add("Le nombre d'heure minimum est de 1. L'activite "
                    + activites.getJSONObject(i).getString("description") + " sera ignoree");
        }
        return heure;
    }

    /**
     * Compare un activité pour determiner si il est dans la catégorie d'activité
     * qui on une restriction d'heures. retourne le nombre d'heures déclaré si
     * il n'a pas de restriction.
     * @param i index de l'activité a évaluer
     * @return nombre d'heure qui seras ajouté au total
     */
    private int ignorerHeureTrop(int i){
        if ((activites.getJSONObject(i).getString("categorie").equals("présentation")) ||
                (activites.getJSONObject(i).getString("categorie").equals("projet de recherche"))) {
            heureMax = 23;
            heures = confirmerHeure(heureMax, i, activites);
        } else if ((activites.getJSONObject(i).getString("categorie").equals("groupe de discussion")) ||
                (activites.getJSONObject(i).getString("categorie").equals("rédaction professionnelle"))) {
            heureMax = 17;
            heures = confirmerHeure(heureMax, i, activites);
        } else {
            heures = confirmerHeure(1000, i, activites);
        } return heures;
    }

    /**
     * Verification du format de la date respect le ISO 8601
     * @param i index de l'activité a évaluer
     * @return boolean true si valide et false si non valide
     */
    private boolean verificationDate(int i){
        boolean valide = false;
        String date = activites.getJSONObject(i).getString("date");
        try{
            LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            valide = true;
        }catch (IllegalArgumentException erreur){
            System.out.println("Une erreur est survenue lors du traitement de la date.");
            System.exit(-1);
        }catch (DateTimeParseException erreur){}
        return valide;
    }

    /**
     * Vérification de l'intervalle valide de l'activité.
     * @param date  Date a vérifier
     * @return boolean true si valide et false si non valide
     */
    private boolean estEntreDate(Date date){
        return date.getTime() >= DATE_MIN.getTime() && date.getTime() <= DATE_MAX.getTime();
    }

    /**
     * Ajoute une erreur a la liste d'erreur et supprime l'activité avec la date
     * invalide
     * @param finMsg fin du message qui change selon le type d'erreur
     * @param i index de l'activité
     */
    private void ecrireErrDate(String finMsg, int i){
        list1.add("La date de l'activité " +
                activites.getJSONObject(i).getString("description") +
                finMsg);
        activites.discard(i);
    }

    /**
     * Valide la de toute les activités dans la liste des activités
     */
    public void validerDate(){
        try {
            for (int i = 0; i < activites.size(); i++) {
                if (verificationDate(i)){
                    String sDate = activites.getJSONObject(i).getString("date");
                    Date dDate = new SimpleDateFormat("yyyy-MM-dd").parse(sDate);
                    if(!(estEntreDate(dDate))){
                        ecrireErrDate(" n'est pas dans l'intervalle exigée. Elle sera ignoré", i);
                    }
                }else{
                    ecrireErrDate(" ne respecte pas le format ISO 8601. Elle sera ignoré", i);
                }
            }
        }catch (ParseException err){}
    }

    /**
     * Vérifie si les heures transféré ne dépasse pas le maximum d'heures transférable
     * @return les heures a ajouter au total
     */
    private int verificationHeureTrf(){
        int nbHeureTrf = jsonO.getInt("heures_transferees_du_cycle_precedent");
        if (nbHeureTrf < 0){
            nbHeureTrf = 0;
            list1.add("Le nombre d'heures transférées ne peut être négatif. " +
                    "0 heures seront considérées." );
        }else if(nbHeureTrf > 7){
            nbHeureTrf = 7;
            list1.add("Le nombre d'heures transférées ne peut être supérieur à "+
                    "7 heures. Seulement 7 heures seront considérées." );
        }
        return nbHeureTrf;
    }

    /**
     *  valide le cycle. Seulement 2020-2022 est valide
     */
    public void verificationCycle(){
        if (!jsonO.getString("cycle").equals("2020-2022")){
            list1.add("Uniquement le cycle 2020-2022 est valide");
            complet = false;
        }
    }

    /**
     * Vérifie si la catégorie de l'activité fait partie des la liste des
     * catégorie valide
     * @param obj l'activité a validé
     * @return boolean true si valide et false si non valide
     */
    private boolean estActiviteValide(JSONObject obj){
        boolean comparaison = false;
        for (int i = 0 ; i < categories.size(); i++) {
            if (categories.get(i).equals(obj.get("categorie"))) {
                comparaison = true;
            }
        }
        if (!comparaison){
            list1.add("L'activité " + obj.getString("description") +
                    " est dans une catégorie non reconnue. Elle sera ignorée");
            activites.discard(obj);
        }
        return comparaison;
    }

    /**
     * Vérifie si le nombre total d'heures est égal a 40 et ajuste la variable
     * de completion selon le cas.
     */
    public void verification40Heures() {
        int sommeheures = verificationHeureTrf();
        for(int i = 0 ; i < activites.size(); i++){
            if(estActiviteValide(activites.getJSONObject(i))){
                int heures = ignorerHeureTrop(i);
                sommeheures = sommeheures + heures;
            }
        }
        if (sommeheures < 40){
            list1.add("Il manque " + (40 - sommeheures) + //nb heure negatif a voir
                    " heures" + " de formation pour compléter le cycle.");
            complet = false;
        }
    }

    /**
     * Calcul les heures des activités qui font partie des catégories qui ont un
     * minimum de 17 heures
     * @param i index de l'activité
     * @param heure heure initiale a être additionner au heures dans la catégorie
     *              minimum 17 heures
     * @return le total des heures précédente plus les nouvelles heures
     */
    public int voirCat(int i, int heure){
        if ((activites.getJSONObject(i).getString("categorie").equals("cours")) ||
                (activites.getJSONObject(i).getString("categorie").equals("atelier")) ||
                (activites.getJSONObject(i).getString("categorie").equals("séminaire"))
                || (activites.getJSONObject(i).getString("categorie").equals("collogue"))
                || (activites.getJSONObject(i).getString("categorie").equals("conférence"))
                || (activites.getJSONObject(i).getString("categorie").equals("lecture dirigée"))) {
            heure += Integer.parseInt(activites.getJSONObject(i).getString("heures"));
        }
        return heure;
    }

    /**
     * Vérifie si le total des heures des activités qui font partie des catégories qui ont un
     * minimum de 17 heures est en dessous de 17 heures.
     * Sinon elle ajuste la variable de completion.
     */
    public void verification17HeuresCategories(){
        int heure = verificationHeureTrf();
        for (int i = 0 ; i < activites.size(); i++) {
            heure = voirCat(i, heure);
        }
        if (heure < 17){
            list1.add("Le nombre d'heures déclarées pour les catégories suivantes "+
                    ": cours, atelier, séminaire, colloque, conférence, lecture dirigée est de : " +
                    heure +" heures. Le nombre d'heures déclarées est trop faible" );
            complet = false;
        }
    }
}


