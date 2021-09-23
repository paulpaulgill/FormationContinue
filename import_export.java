import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
    JSONParser jsonP = new JSONParser();
    JSONArray list1 = new JSONArray();
    JSONObject obj = new JSONObject();

    public import_export(String entree, String sortie)
    {
        this.fichiers_entree = entree;
        this.fichiers_sortie = sortie;
    }

    public void chargement() throws IOException,ParseException
    {
        jsonO = (JSONObject)jsonP.parse(new FileReader(fichiers_entree));
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

    public void recherche_erreur()
    {
        complet =  true;
        if (cycle == "2020-2022")
        {
            complet = false;
            list1.add("Le cycle " + cycle + " n'est pas supporté. Il serra donc ignoré");
        }

        if(heure_minu == heure_supp)
        {

        }



    }

    /*public void to_string()
    {
        cycle = (String) jsonO.get("cycle");
        heure_supp =  (Number) jsonO.get("heures_transferees_du_cycle_precedent");
        String numero_de_permis = (String) jsonO.get("numero_de_permis");


        System.out.println("Numero_de_permis : "+ numero_de_permis);
        System.out.println("Cycle : "+ cycle);
        System.out.println("Heures_transferees_du_cycle_precedent : " + heure_supp);

        JSONArray tableau_activities = (JSONArray) jsonO.get("activites");
        for (Object arrayObj : tableau_activities) {
            JSONObject activies = (JSONObject) arrayObj;
            System.out.println("===========================");
            System.out.println("Description : " + activies.get("description"));
            System.out.println("Categorie : " + activies.get("categorie"));
            System.out.println("Heures : " + activies.get("heures") );
            System.out.println("Date : " + activies.get("date"));

    }

}*/


}
