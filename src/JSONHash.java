package src;

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
import java.util.Calendar;

public class JSONHash {
    private final String fichiers_entree;
    private final String fichiers_sortie;
    private JSONObject jsonO;
    Boolean complet = false;
    JSONArray list1 = new JSONArray();
    JSONObject obj = new JSONObject();
    ArrayList<String> categories = new ArrayList<>();
    boolean succes;
    int heureMax;
    int heures = 0;
    final Date DATE_MAX = new Date(2022,04,1);
    final Date DATE_MIN = new Date(2020,04,1);
    JSONArray activites = new JSONArray();
    int nbHeureTrf = 0;
    boolean valide = false;

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
            throw new FormationContinueException("Une erreur innatendue est survenue");
        }
    }

    private void suiteChargement() throws IOException
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
            list1.add("Le nombre d'heure minimum est de 1. L'activite "
                    + activites.getJSONObject(compteur).getString("description") + " sera ignoree");
        }
        return heure;
    }

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

    //Verification du format de la date respect le ISO 8601 (A mettre private pour utilisation par autre methode)
    private boolean verificationDate(int i){
        String date = activites.getJSONObject(i).getString("date");
        try{
            LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            valide = true;
        }catch (IllegalArgumentException erreur){
            System.out.println("Une erreur est survenue lors du traitement de la date.");
            System.exit(-1);
        }catch (DateTimeParseException erreur){}//retourne faux si la date n'est pas legale.
        return valide;
    }

    private boolean estEntreDate(Date date){
        return date.getTime() >= DATE_MIN.getTime() && date.getTime() <= DATE_MAX.getTime();
    }

    private void ecrireErrDate(String finMsg, int i){
        list1.add("La date de l'activité " +
                activites.getJSONObject(i).getString("description") +
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
            list1.add("Le nombre d'heures transférées ne peut être supérieur à "+
                    "7 heures. Seulement 7 heures seront considérées." );
        }else if(jsonO.getInt("heures_transferees_du_cycle_precedent") <= 7
                && jsonO.getInt("heures_transferees_du_cycle_precedent") >= 0){
            nbHeureTrf = jsonO.getInt("heures_transferees_du_cycle_precedent");
        }
        return nbHeureTrf;
    }

    public boolean verificationCycle(){
        return jsonO.getString("cycle").equals("2020-2022");
    }

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
        }else if(verificationCycle()){complet = true;}
    }

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

    public void verification17Heurescategories (){
        int heure = verificationHeureTrf();
        for (int i = 0 ; i < activites.size(); i++) {
            heure = voirCat(i, heure);
        }
        if (heure < 17){
            list1.add("Le nombre d'heures déclarées pour les catégories suivantes "+
                    ": cours, atelier, séminaire, colloque, conférence, lecture dirigée est de : " +
                    heure +" heures. Le nombre d'heures déclarées est trop faible" );
        }
    }
}


