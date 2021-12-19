package util;

import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.*;
import exception.FormationContinueException;
import profession.*;


import java.io.*;

public class GestionJSON {

    private Declaration declaInit;
    private ObjectMapper objectMapper = new ObjectMapper();
    private DefaultPrettyPrinter pp = new DefaultPrettyPrinter();
    private final String fichiers_entree;
    private final String fichiers_sortie;

    /**
     * Constructeur de l'objet GestionJSON qui facilite le calcul et la manipulation
     * des données du fichier d'entrée.
     * @param entree le fichier d'entré
     * @param sortie le fichier de sortie
     */
    public GestionJSON(String entree, String sortie)
    {
        this.fichiers_entree = entree;
        this.fichiers_sortie = sortie;
    }

    /**
     * Lis le fichier JSON d'entrée et l'insert dans un objet JSON
     * @throws FormationContinueException
     */
    public Declaration chargement() throws FormationContinueException {
        try{
            declaInit = objectMapper.readValue(new File(fichiers_entree), Declaration.class);
            String ordre = declaInit.getOrdre();
            if (ordre == "architectes"){ declaInit = new Architectes();}
            else if (ordre == "géologues" ){ declaInit = new Geologues();}
            else if (ordre == "psychologues"){  declaInit = new Psychologues();}
            else if (ordre == "podiatres" ){ declaInit = new Podiatres();}
            else{ throw new FormationContinueException("La structure du fichier d'entrée n'est pas respecté");}
            return chargerType(declaInit.getClass());
        }catch(FileNotFoundException erreur) {
            throw new FormationContinueException("Le fichier donné est introuvable.");
        }catch (JsonMappingException erreur){
            throw new FormationContinueException("La structure du fichier d'entrée n'est pas respecté");
        }catch (IOException erreur){
            throw new FormationContinueException("Une erreur inattendue est survenue");
        }
    }

    public <E> E chargerType(Class<E> eClass) throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        objectMapper.configure(DeserializationFeature.ACCEPT_FLOAT_AS_INT,false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);
        return objectMapper.readValue(new File(fichiers_entree), eClass);
    }

    /**
     * Écris dans le fichier résultat si le cycle est complet et les erreurs
     * dans le fichier d'entré.
     * @throws FileNotFoundException
     */
    public void exporterErreur(Declaration decla) {
        try {
            pp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE );
            objectMapper.writer(pp).writeValue(new File(fichiers_sortie),decla.getResultat());
        }catch (FileNotFoundException erreur){
            System.err.println("Fichier des resultats introuvable.");
            System.exit(-1);
        }catch (IOException erreur){
            System.err.println("erreur inatendu lors de l'exportation des resultats");
            System.exit(-1);
        }


    }
}


