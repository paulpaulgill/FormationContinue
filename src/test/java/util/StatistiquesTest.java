package util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatistiquesTest {

    @Test
    void chargerStat() {
        Statistiques stat = new Statistiques();
        stat = stat.chargerStat("src/test/resources/testStat1.json",false);
        assertEquals(18,stat.getTotalActivites());
    }

    @Test
    void chargerStatReinitialiser() {
        Statistiques stat = new Statistiques();
        stat = stat.chargerStat("src/test/resources/testStat1.json",true);
        assertEquals(0,stat.getTotalActivites());
    }

    @Test
    void creerFichierStat() {
    }

    @Test
    void exporterStat() {
    }

    @Test
    void genererStat() {
    }

    @Test
    void genererStatInvalid() {
    }

    @Test
    void compterSexe() {
    }

    @Test
    void compterValideEtInvalide() {
    }

    @Test
    void compterActivitesParCat() {
    }

    @Test
    void compterCompletOuIncompletOrdre() {
    }
}