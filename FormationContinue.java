import java.io.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class main {
    public static void main(String args[]) {
       // int nom_des = 1;
       // String [ ] categories_reconnues = new String[10];
        String [ ] activities = new String[10];


        JSONParser jsonP = new JSONParser();
        try {
            JSONObject jsonO;
            jsonO = (JSONObject)jsonP.parse(new FileReader("/Users/antoinegimalac/IdeaProjects/projet/src/person.json"));

            String cycle = (String) jsonO.get("cycle");
            Number heures_transferees_du_cycle_precedent =  (Number) jsonO.get("heures_transferees_du_cycle_precedent");
            String numero_de_permis = (String) jsonO.get("numero_de_permis");

            for(int i = 0; i < 10; i++)
            {
                activities[1] = (String) jsonO.get("activities[1]categorie'[2]");
            }


            System.out.println("Numero_de_permis : "+ numero_de_permis);
            System.out.println("Cycle : "+ cycle);
            System.out.println("Heures_transferees_du_cycle_precedent : " + heures_transferees_du_cycle_precedent);
            System.out.println("Activities : "+ activities[1]);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


    }
}

