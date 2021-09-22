import net.sf.json.JSONArray;
import net.sf.json.JSONSerializer;
import net.sf.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
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

public class import_export {
    private final String fichiers_entree;
    private final String fichiers_sortie;
    private JSONObject jsonO;
    private JSONObject erreur2;
    private final String erreur = "{'erreur ': }";
    Number heure_supp;
    Number heure_minu = 0;
    String cycle;
    String complet;
    JSONParser jsonP = new JSONParser();

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
        sortie.write( "{\n\"complet\":" + complet +"\n[\n{" + "]}");
        sortie.flush();
        sortie.close();
    }

    //Verification du format de la date respect le ISO 8601
    private boolean verificationDate(int indice){
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

    private boolean cycleAccepter(){
        boolean accepte = false;
        if(jsonO.getString("cycle").equals("2020-2022")){
            accepte = true;
        }
        return accepte;
    }

    public void recherche_erreur()
    {
        complet = " true, \n\"erreur\": ";
        if (cycle != "2020-2022")
        {
            complet = " false, \n\"erreur\": ";
        }

        if(heure_minu == heure_supp)
        {

        }


    }

    public void to_string()
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
}
}
