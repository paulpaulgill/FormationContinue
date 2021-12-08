package profession;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Activite;
import util.IntervalCycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestPsychologues {
    private String descr = "qwertyuiopqwertyuiopqwwq";
    private Activite act1 = new Activite(descr, "cours", 10, "2021-02-02");
    private Activite act2 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act3 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act7 = new Activite(descr, "cours", 10, "2021-02-12");
    private Activite act8 = new Activite(descr, "cours", 10, "2020-12-02");
    private Activite act9 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act4 = new Activite(descr, "groupe de discussion", 10, "2021-01-02");
    private Activite act5 = new Activite(descr, "séminaire", 10, "2021-01-02");
    private Activite act10 = new Activite(descr, "séminaire", 10, "2021-01-04");
    private Psychologues p1 = new Psychologues(descr,null, null, null);
    private List<Activite> liste = Arrays.asList(act1, act2, act3, act5, act4, act7, act8, act9, act10);
    private List<Activite> liste2 = Arrays.asList(act1);
    private ArrayList<Activite> arrayList;
    private IntervalCycle i;

    @BeforeEach
    public void setUp(){
        i = new IntervalCycle();
        arrayList = new ArrayList<>();
    }

    @AfterEach
    public void tearDown(){
        p1 = null;
        arrayList = null;
        i = null;
    }

    @Test
    public void testValiderHTotal(){
        arrayList.addAll(liste);
        p1.setActivites(arrayList);
        p1.validerHTotal();
        assertEquals(true, p1.getResultat().isComplet());
    }

    @Test
    public void testValiderHTotalNonValide(){
        arrayList.addAll(liste2);
        p1.setActivites(arrayList);
        p1.validerHTotal();
        assertEquals(false, p1.getResultat().isComplet());
    }
}
