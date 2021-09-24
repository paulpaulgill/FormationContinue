import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.*;
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
    Boolean complet;
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

    public void chargement() throws IOException, ParseException
    {
        jsonO = (JSONObject.fromObject(jsonP.parse(new FileReader(fichiers_entree))));

    }

    public void exportation_erreur() throws FileNotFoundException
    {
        PrintWriter sortie = new PrintWriter(fichiers_sortie);
        obj.put("Complet", complet);
        obj.put("erreur", list1);
        sortie.write(String.valueOf(obj));
        sortie.flush();
        sortie.close();

    }

    //Verification du format de la date respect le ISO 8601
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

    public int VerificationHeureTrf(){
        int nbHeureTrf = 0;
        if(jsonO.getInt("heures_transferees_du_cycle_precedent") > 7){
            nbHeureTrf = 7;
            //AJOUTER PARTIE POUR MSG ERREUR SUR DOCUMENT JSON
        }else if(jsonO.getInt("heures_transferees_du_cycle_precedent") >= 0 ){
            nbHeureTrf = jsonO.getInt("heures_transferees_du_cycle_precedent");
        }
        return nbHeureTrf;

    }

    /*private boolean cycleAccepter(){
        boolean accepte = false;
        if(jsonO.getString("cycle").equals("2020-2022")){
            accepte = true;
        }
        return accepte;
    }*/

    public void verif_cycle() {
        complet = true ;
        if (!"2020-2022".equals(cycle)) {
            if (message == false) {
                list1.add(" false, \n\"erreur\": [\n \"Le cycle " + cycle + " n'est pas supporté\"");
                complet = false;
                message = true;
            } else if (message == true) {
                list1.add(",\n\"Le cycle " + cycle + " n'est pas supporté\"");
                complet = false;
            }

        }

    }

    public void categories_reconnues(){
        boolean comparaison = false;
        JSONArray activites = (JSONArray) JSONSerializer.toJSON(jsonO.getString("activites"));
        for (Object arrayObj : activites) {
            comparaison = false;
            JSONObject activites_categories = (JSONObject) arrayObj;
            for (int i = 0 ; i < categories.size(); i++) {
                for (int j = 0; j < activites.size(); j++) {
                    if (categories.get(i).equals(activites_categories.get("categorie"))) {
                        comparaison = true;
                    } else ;
                }
            }
            if (comparaison == false){
                complet = false;
                if(message == false)
                {
                    list1.add(" false, \n\"erreur\": [\n \"L'activité " + activites_categories.get("categorie") +" est dans une" +
                            " catégorie non reconnue. Elle sera ignorée.\"");

                } else if (message == true){
                    list1.add(",\n \"L'activité " + activites_categories.get("categorie") +" est dans une" +
                            " catégorie non reconnue. Elle sera ignorée.\"");
                }

            }
        }

    }

    public void to_string()
    {
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
