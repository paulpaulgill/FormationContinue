package util;

import exception.FormationContinueException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import profession.Podiatres;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatistiquesTest {
    private String descr = "qwertyuiopqwertyuiopqwwq";
    private Activite act1 = new Activite(descr, "cours", 10, "2021-02-02");
    private Activite act2 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act3 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act4 = new Activite(descr, "groupe de discussion", 10, "2021-01-02");
    private Activite act5 = new Activite(descr, "projet de recherche", 10, "2021-01-02");
    private Activite act6 = new Activite(descr, "projet de recherche", 10, "2021-01-02");
    private List<Activite> liste = Arrays.asList(act1, act2, act3, act5, act4, act6);
    private Podiatres p1 = new Podiatres("83453","2018-2021", "Serge", "Dogny",0, "podiatres", null);
    private ArrayList<Activite> arrayList;

    @BeforeEach
    public void setUp(){
        arrayList = new ArrayList<>();
    }

    @AfterEach
    public void tearDown(){
        arrayList = null;
        p1 = new Podiatres(descr,"2018-2021", "Serge", "Dogny",0, "podiatres", null);
    }

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
    void exporterStat() {
        Statistiques stat = new Statistiques();
        Statistiques stat2 = new Statistiques();
        stat = stat.chargerStat("src/test/resources/testStat2.json",false);
        stat.setDeclarationTraitees(stat.getDeclarationTraitees() + 1);
        stat.exporterStat("src/test/resources/testStat2.json");
        stat2 = stat2.chargerStat("src/test/resources/testStat2.json",false);
        assertEquals(stat.getDeclarationTraitees(),stat2.getDeclarationTraitees());
    }

    @Test
    void genererStat() {
        arrayList.addAll(liste);
        p1 = new Podiatres("83453","2018-2021", "Serge", "Dogny",0, "podiatres", arrayList);
        Statistiques stat = new Statistiques();
        stat = stat.chargerStat("src/test/resources/testStat1.json",false);
        stat.genererStat(p1);
        assertEquals(24,stat.getTotalActivites());

    }

    @Test
    void genererStatIncomplet() {
        arrayList.addAll(liste);
        p1 = new Podiatres("83453","2018-2021", "Serge", "Dogny",0, "podiatres", arrayList);
        p1.getResultat().setComplet(false);
        Statistiques stat = new Statistiques();
        stat = stat.chargerStat("src/test/resources/testStat1.json",false);
        stat.genererStat(p1);
        assertEquals(1,stat.getDeclarationIncompletesInvalides());

    }

    @Test
    void genererStatInvalid() {
        arrayList.addAll(liste);
        p1 = new Podiatres("834A53","2018-2021", "Serge", "Dogny",0, "podiatres", arrayList);
        Statistiques stat = new Statistiques();
        stat = stat.chargerStat("src/test/resources/testStat1.json",false);
        stat.genererStatInvalid(p1);
        assertEquals(1,stat.getDeclarationPermisInvalide());
    }
}