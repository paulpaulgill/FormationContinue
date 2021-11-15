package util;

import exception.FormationContinueException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import profession.Architectes;
import profession.Geologues;
import profession.Psychologues;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GestionJSONTest {

    @Test
    void chargement_geologues() throws FormationContinueException {
        GestionJSON fichier = new GestionJSON("src/test/resources/testGeologues.json","src/test/resources/resultat.json");
        assertTrue(fichier.chargement() instanceof Geologues);
    }

    @Test
    void chargement_psychologues() throws FormationContinueException {
        GestionJSON fichier = new GestionJSON("src/test/resources/testPsychologues.json","src/test/resources/resultat.json");
        assertTrue(fichier.chargement() instanceof Psychologues);
    }

    @Test
    void chargement_architectes() throws FormationContinueException {
        GestionJSON fichier = new GestionJSON("src/test/resources/testArchitectes.json","src/test/resources/resultat.json");
        assertTrue(fichier.chargement() instanceof Architectes);
    }

    @ParameterizedTest
    @ValueSource(ints = {1,2,3,4,5,6})
    void chargement_echouer(int entre) {
        GestionJSON fichier = new GestionJSON("src/test/resources/testFichierInvalide"+entre+".json","src/test/resources/resultat.json");
        assertThrows(FormationContinueException.class, fichier::chargement);
    }

    @Test
    void exportationErreur() throws IOException, FormationContinueException {
        Architectes archi = new Architectes();
        Resultat result = new Resultat();
        GestionJSON fichier = new GestionJSON("src/test/resources/testArchitectes.json","src/test/resources/testResultat.json");
        result.ajouterErreur("test");
        result.setComplet(false);
        archi.setResultat(result);
        fichier.exporterErreur(archi);
        File file1 = new File("src/test/resources/testResultat.json");
        File file2 = new File("src/test/resources/testResultatValide.json");
        assertTrue(FileUtils.contentEquals(file1, file2));
    }
}