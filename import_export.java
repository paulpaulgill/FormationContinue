import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.*;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;

public class import_export {
    private final String fichiers_entree;
    private final String fichiers_sortie;
    private JSONObject jsonO;
    private JSONObject erreur2;
    private final String erreur = "{'erreur ': }";
    Number heure_supp;
    Number heure_minu = 0;
    String cycle;
    Boolean complet = false;
    String numero_de_permis;
    boolean message = false;
    JSONParser jsonP = new JSONParser();
    JSONArray list1 = new JSONArray();
    JSONObject obj = new JSONObject();
    ArrayList<String> categories = new ArrayList<String>();

    public void Categories()
    {
        categories.add("cours");
        categories.add("atelier");
        categories.add("séminaire");
        categories.add("collogue");
        categories.add("conférence");
        categories.add("lecture dirigéé");
        categories.add("présentation");
        categories.add("groupe de discussion");
        categories.add("projet de recherche");
        categories.add("rédaction professionnelle");
    }

    public import_export(String entree, String sortie)
    {
        this.fichiers_entree = entree;
        this.fichiers_sortie = sortie;
    }

    /**public void chargement() throws IOException,ParseException
     {
        jsonO = (JSONObject.fromObject(jsonP.parse(new FileReader(fichiers_entree))));

    }
    */
    public boolean chargement() throws IOException, FormationContinueException{
        boolean succes;
        try{
            String stringJson = IOUtils.toString(new FileReader(fichiers_entree));
            JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(stringJson);
            this.jsonO = jsonObject;
            succes = true;
        }catch(FileNotFoundException erreur){
            throw new FormationContinueException("Le fichier d'entree n'existe pas");
        }catch (IOException erreur){
            throw new FormationContinueException("Une erreure innatendue est survenue");
        }
        return succes;
    }

    public void exportation_erreur() throws FileNotFoundException
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
        int heureMax;
        int heures = 0;
        JSONArray activites = (JSONArray) JSONSerializer.toJSON(jsonO.getString("activites"));
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
            }

        return heures;
    }

    private void genererMsgErreur(String msgPremier, String msgDeux, String msgTrois){
        list1.add(msgPremier + msgDeux + msgTrois );
    }

    //Verification du format de la date respect le ISO 8601 (A mettre private pour utilisation par autre methode)
    public boolean verificationDate(int indice){
        boolean valide = false;
        JSONArray activites = (JSONArray) JSONSerializer.toJSON(jsonO.getString("activites"));
        String date = activites.getJSONObject(indice).getString("date");
        try{
            LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            valide = true;
        }catch (IllegalArgumentException erreur){
            System.out.println("Une erreur est survenue lors du traitement de la date.");
        }catch (DateTimeParseException erreur){
            //retourne faux si la date n'est pas legale.
        }
        return valide;
    }

    private int VerificationHeureTrf(){
        int nbHeureTrf = 0;
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

    public boolean verificationCycle() {
        boolean accepte = true;
        if (!("2020-2022".equals(cycle))) {
            genererMsgErreur("Le cycle ", cycle , " n'est pas supporté");
            complet = false;
            accepte = false;
        }
        return accepte;
    }

    /**
    public void categoriesReconnues(){
        JSONArray activites = (JSONArray) JSONSerializer.toJSON(jsonO.getString("activites"));
        for (Object arrayObj : activites) {
            boolean comparaison = false;
            JSONObject activites_categories = (JSONObject) arrayObj;
            for (int i = 0 ; i < categories.size(); i++) {
                for (int j = 0; j < activites.size(); j++) {
                    if (categories.get(i).equals(activites_categories.get("categorie"))) {
                        comparaison = true;
                    }
                }
            }
            if (comparaison == false){
                genererMsgErreur("L'activité ", activites_categories.getString("description"),
                        " est dans une catégorie non reconnue. Elle sera ignorée");
            }
        }

    }*/
    private boolean estActiviteValide(JSONObject obj){
        boolean comparaison = false;
        for (int i = 0 ; i < categories.size(); i++) {
            if (categories.get(i).equals(obj.get("categorie"))) {
                comparaison = true;
            }
        }
        if (comparaison == false){
            genererMsgErreur("L'activité ", obj.getString("description"),
                    " est dans une catégorie non reconnue. Elle sera ignorée");
        }
        return comparaison;
    }


    public void verification40Heures() {
        JSONArray activites = (JSONArray) JSONSerializer.toJSON(jsonO.getString("activites"));
        int sommeheures = VerificationHeureTrf();
        for(int i = 0 ; i < activites.size(); i++){
            if(estActiviteValide(activites.getJSONObject(i))){
                int heures = ignorerHeureTrop(i);
                sommeheures = sommeheures + heures;
            }
        }
        System.out.println(sommeheures); //A retirer plus tard
        if (sommeheures < 40){
            genererMsgErreur("Il manque ", Math.abs(40 - sommeheures) + //nb heure negatif a voir
                    " heures", " de formation pour compléter le cycle.");
            complet = false;
        }else if(verificationCycle() == true ){complet = true;}
    }


    public void to_String() {
        cycle = (String) jsonO.get("cycle");
        heure_supp =  (Number) jsonO.get("heures_transferees_du_cycle_precedent");
        numero_de_permis = (String) jsonO.get("numero_de_permis");
        System.out.println("Numero_de_permis : "+ numero_de_permis);
        System.out.println("Cycle : "+ cycle);
        System.out.println("Heures_transferees_du_cycle_precedent : " + heure_supp);
       JSONArray tableau_activites = (JSONArray) jsonO.get("activites");
        for (Object arrayObj : tableau_activites) {
            JSONObject activies = (JSONObject) arrayObj;
            System.out.println("===========================");
            System.out.println("Description : " + activies.get("description"));
            System.out.println("Categorie : " + activies.get("categorie"));
            System.out.println("Heures : " + activies.get("heures") );
            System.out.println("Date : " + activies.get("date"));
        }
    }
}


