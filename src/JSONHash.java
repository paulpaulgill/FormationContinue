import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.*;
import org.apache.commons.io.IOUtils;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class JSONHash {
  private final String fichiers_entree;
    private final String fichiers_sortie;
    private JSONObject jsonO;
    String cycle;
    Boolean complet = false;
    JSONArray list1 = new JSONArray();
    JSONObject obj = new JSONObject();
    ArrayList<String> categories = new ArrayList<String>();
    boolean succes;
    int heureMax;
    int heures = 0;
    final Date DATE_MAX = new Date(2022,04,01);
    final Date DATE_MIN = new Date(2020,04,01);
    JSONArray activites = new JSONArray();
    int nbHeureTrf = 0;
    boolean valide = false;
    boolean accepte = false;
    boolean comparaison = false;

    public void Categories()
    {
        categories.addAll(Arrays.asList("cours", "atelier","séminaire","collogue","conférence"
                ,"lecture dirigée","présentation","groupe de discussion","projet de recherche","rédaction professionnelle"));
    }

    public JSONHash(String entree, String sortie)
    {
        this.fichiers_entree = entree;
        this.fichiers_sortie = sortie;
    }

    public void chargement() throws FormationContinueException{
        try{
            suiteChargement();
        }catch(FileNotFoundException erreur) {
            System.err.println("Le fichier donné est introuvable.");
            System.exit(-2);
        }catch (JSONException erreur){
            System.err.println("Ce n'est pas un fichier JSON bien formatté");
            System.exit(-3);
        }catch (IOException erreur){
            throw new FormationContinueException("Une erreure innatendue est survenue");
        }
    }

    public void suiteChargement() throws IOException
    {
        String stringJson = IOUtils.toString(new FileReader(fichiers_entree));
        JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(stringJson);
        this.jsonO = jsonObject;
        succes = true;
        activites = (JSONArray) JSONSerializer.toJSON(jsonO.getString("activites"));
    }

    public void exportationErreur() throws FileNotFoundException
    {
        PrintWriter sortie = new PrintWriter(fichiers_sortie);
        obj.put("complet", complet);
        obj.put("erreur", list1);
        sortie.write(obj.toString(3));
        sortie.flush();
        sortie.close();
    }

    private int confirmerHeure(int heureMax, int compteur, JSONArray activites){
        int heure = activites.getJSONObject(compteur).getInt("heures");
        if(heure > heureMax){
            heure = heureMax;
        }else if(heure < 1) {
            heure = 0;
            genererMsgErreur("Le nombre d'heure minimum est de 1. ",
                    "L'activite "  + activites.getJSONObject(compteur).getString("description"), " sera ignoree");
        }
        return heure;
    }

    public int ignorerHeureTrop(int i){
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

    private void genererMsgErreur(String msgPremier, String msgDeux, String msgTrois){
        list1.add(msgPremier + msgDeux + msgTrois );
    }

    //Verification du format de la date respect le ISO 8601 (A mettre private pour utilisation par autre methode)
    public boolean verificationDate(int indice){
        String date = activites.getJSONObject(indice).getString("date");
        try{
            LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            valide = true;
        }catch (IllegalArgumentException erreur){
            System.out.println("Une erreur est survenue lors du traitement de la date.");
            System.exit(-1);
        }catch (DateTimeParseException erreur){}//retourne faux si la date n'est pas legale.
        return valide;
    }

    private boolean estEntreDate(Date aTester){
        return aTester.getTime() >= DATE_MIN.getTime() && aTester.getTime() <= DATE_MAX.getTime();
    }

    private void ecrireErrDate(String finMsg, int i){
        genererMsgErreur("La date de l'activité ",
                activites.getJSONObject(i).getString("description"),
                finMsg);
        activites.discard(i);
    }

    public void validerDate(){
        try {
            for (int i = 0; i < activites.size(); i++) {
                if (verificationDate(i) ){
                    SimpleDateFormat date = new SimpleDateFormat("uuuu-MM-dd");
                    String sDate = activites.getJSONObject(i).getString("date");
                    Date dDate = date.parse(sDate);
                    if(!estEntreDate(dDate)){
                        ecrireErrDate(" n'est pas dans l'intervalle exigée. Elle sera ignoré", i);
                    }
                }else{
                    ecrireErrDate(" ne respecte pas le format ISO 8601. Elle sera ignoré", i);
                }
            }
        }catch (ParseException err){}
    }

    private int verificationHeureTrf(){
        if(jsonO.getInt("heures_transferees_du_cycle_precedent") > 7){
            nbHeureTrf = 7;
            genererMsgErreur("Le nombre d'heures transférées ne peut être supérieur à ",
                    "7"," heures. Seulement 7 heures seront considérées." );
        }else if(jsonO.getInt("heures_transferees_du_cycle_precedent") <= 7
                && jsonO.getInt("heures_transferees_du_cycle_precedent") >= 0){
            nbHeureTrf = jsonO.getInt("heures_transferees_du_cycle_precedent");
        }
        return nbHeureTrf;
    }

    public boolean verificationCycle(){
        if(jsonO.getString("cycle").equals("2020-2022")){
            accepte = true;
        }else{
            complet = false;
        }
        return accepte;
    }

    private boolean estActiviteValide(JSONObject obj){
        for (int i = 0 ; i < categories.size(); i++) {
            if (categories.get(i).equals(obj.get("categorie"))) {
                comparaison = true;
            }
        }
        if (comparaison == false){
            genererMsgErreur("L'activité ", obj.getString("description"),
                    " est dans une catégorie non reconnue. Elle sera ignorée");
            activites.discard(obj);
        }
        return comparaison;
    }


    public void verification40Heures() {
        int sommeheures = verificationHeureTrf();
        for(int i = 0 ; i < activites.size(); i++){
            if(estActiviteValide(activites.getJSONObject(i))){
                int heures = ignorerHeureTrop(i);
                sommeheures = sommeheures + heures;
            }
        }
        if (sommeheures < 40){
            genererMsgErreur("Il manque ", Math.abs(40 - sommeheures) + //nb heure negatif a voir
                    " heures", " de formation pour compléter le cycle.");
            complet = false;
        }else if(verificationCycle() == true ){complet = true;}
    }

    public void voirCat(int i, int heure){
        if ((activites.getJSONObject(i).getString("categorie").equals("cours")) ||
                (activites.getJSONObject(i).getString("categorie").equals("atelier")) ||
                (activites.getJSONObject(i).getString("categorie").equals("séminaire"))
                || (activites.getJSONObject(i).getString("categorie").equals("collogue"))
                || (activites.getJSONObject(i).getString("categorie").equals("conférence"))
                || (activites.getJSONObject(i).getString("categorie").equals("lecture dirigée"))) {
            heure += Integer.parseInt(activites.getJSONObject(i).getString("heures"));
        }
    }
    public void verification17Heurescategories (){
        int heure = verificationHeureTrf();
        for (int i = 0 ; i < activites.size(); i++) {voirCat(i, heure);}
        if (heure < 17){
            genererMsgErreur("Le nombre d'heures déclarées pour les catégories suivantes "+
                            ": cours, atelier, séminaire, colloque, conférence, lecture dirigée est de : ",
                    String.valueOf(heure)," heures. Le nombre d'heures déclarées est trop faible" );
        }
    }
}


