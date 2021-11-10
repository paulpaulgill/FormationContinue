package Util;

import Profession.*;
import Exception.FormationContinueException;
import java.util.*;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.*;
import net.sf.json.*;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;


public class JSONHash {
    private Declaration declaInit;
    private Resultat resultat;
    private ObjectMapper objectMapper = new ObjectMapper();
    private DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
    private final String fichiers_entree;
    private final String fichiers_sortie;
    protected JSONObject jsonO;
    private Boolean complet = true;
    private JSONArray list1 = new JSONArray();
    private JSONObject obj = new JSONObject();
    private ArrayList<String> categories = new ArrayList<>();
    private final Date DATE_MAX = new GregorianCalendar(2022, Calendar.APRIL, 1).getTime();
    private final Date DATE_MIN = new GregorianCalendar(2020, Calendar.APRIL, 1).getTime();
    private JSONArray activites = new JSONArray();
    protected Profession ordre;


    /**
     * Constructeur de l'objet GestionJSON qui facilite le calcul et la manipulation
     * des données du fichier d'entrée.
     *
     * @param entree le fichier d'entré
     * @param sortie le fichier de sortie
     */
    public JSONHash( String entree, String sortie )
    {
        this.fichiers_entree = entree;
        this.fichiers_sortie = sortie;
    }

    public JSONArray getList1() {
        return list1;
    }

    /**
     * Lis le fichier JSON d'entrée et l'insert dans un objet JSON
     *
     * @throws FormationContinueException
     */
    public Declaration chargement() throws FormationContinueException {
        try{
            objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
            declaInit = objectMapper.readValue(new File(fichiers_entree), Declaration.class);
            if (declaInit.getOrdre().equals("architectes")){
                declaInit = objectMapper.readValue(new File(fichiers_entree), Architectes.class);
            }else if(declaInit.getOrdre().equals("géologues")){
                declaInit = objectMapper.readValue(new File(fichiers_entree), Geologues.class);
            }else if(declaInit.getOrdre().equals("psychologues")){
                declaInit = objectMapper.readValue(new File(fichiers_entree), Psychologues.class);
            }
        }catch(FileNotFoundException erreur) {
            throw new FormationContinueException("Le fichier donné est introuvable.");
        }catch (JsonMappingException erreur){
            throw new FormationContinueException("La structure du fichier d'entrée n'est pas respecté");
        }catch (IOException erreur){
            throw new FormationContinueException("Une erreur inattendue est survenue");
        }
        return declaInit;
    }

    /**
     * Écris dans le fichier résultat si le cycle est complet et les erreurs
     * dans le fichier d'entré.
     *
     * @throws FileNotFoundException
     */
    public void exporterErreur(Declaration decla) throws FormationContinueException {
        try {
            pp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE );
            objectMapper.writer(pp).writeValue(new File(fichiers_sortie),decla.getResultat());
        }catch (IOException erreur){
            throw new FormationContinueException("erreur inatendu lors de l'exportation des resultats");
        }


    }

}




