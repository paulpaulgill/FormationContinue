package profession;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Activite;
import util.IntervalCycle;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGeologues {
    private String descr = "qwertyuiopqwertyuiopqwwq";
    private Activite act1 = new Activite(descr, "cours", 5, "2021-02-02");
    private Activite act2 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act3 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act4 = new Activite(descr, "groupe de discussion", 10, "2021-01-02");
    private Activite act5 = new Activite(descr, "projet de recherche", 10, "2021-01-02");
    private Activite act6 = new Activite(descr, "projet de recherche", 10, "2021-01-02");
    private Geologues g1 = new Geologues(descr,null, null, null);
    private List<Activite> liste = Arrays.asList(act1, act2, act3, act5, act4, act6);
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
        g1 = null;
        arrayList = null;
        i = null;
    }

    @Test
    public void testValiderHMin(){
        arrayList.addAll(liste);
        g1.setActivites(arrayList);
        g1.validerHMin();
        assertEquals(true, g1.getResultat().isComplet());
    }

    @Test
    public void testMesurerInter(){
        g1.setCycle("2018-2021");
        i = g1.mesurerInter();
        assertEquals(new GregorianCalendar(2021, Calendar.JUNE, 1).getTime(), i.getMax());
        assertEquals(new GregorianCalendar(2018, Calendar.JUNE, 1).getTime(), i.getMin());
        assertEquals("2018-2021", i.getCycle());
    }

    @Test
    public void testMesurerInterNonVal(){
        g1.setCycle("2552-2558");
        i = g1.mesurerInter();
        assertEquals(null, i);
    }

    @Test
    public void testValiderHTotal(){
        arrayList.addAll(liste);
        g1.setActivites(arrayList);
        g1.validerHTotal();
        assertEquals(true, g1.getResultat().isComplet());
    }

    @Test
    public void tesValiderHTotalNonValide(){
        arrayList.addAll(liste2);
        g1.setActivites(arrayList);
        g1.validerHTotal();
        assertEquals(false, g1.getResultat().isComplet());
    }

}
