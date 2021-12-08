package profession;

import exception.FormationContinueException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Activite;
import util.IntervalCycle;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestPodiatres {
    private String descr = "qwertyuiopqwertyuiopqwwq";
    private Activite act1 = new Activite(descr, "cours", 10, "2021-02-02");
    private Activite act2 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act3 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act4 = new Activite(descr, "groupe de discussion", 10, "2021-01-02");
    private Activite act5 = new Activite(descr, "projet de recherche", 10, "2021-01-02");
    private Activite act6 = new Activite(descr, "projet de recherche", 10, "2021-01-02");
    private Podiatres p1 = new Podiatres(descr,null, null, null);
    private List<Activite> liste = Arrays.asList(act1, act2, act3, act5, act4, act6);
    private List<Activite> liste2 = Arrays.asList(act1);
    private ArrayList<Activite> arrayList;
    private IntervalCycle i;
    private String permis1 = "85963";
    private String permis2 = "M8457";
    private String permis3 = "45";
    String cycle = "2018-2023";
    String ordre = "podiatres";

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
    public void testValiderHMin(){
        arrayList.addAll(liste);
        p1.setActivites(arrayList);
        p1.validerHMin();
        assertEquals(true, p1.getResultat().isComplet());
    }

    @Test
    void validerPermisPasCorrect(){
        arrayList.addAll(liste);
        p1 = new Podiatres(permis2,cycle,ordre,arrayList);
        assertThrows(FormationContinueException.class, ()->p1.validerPermis());
        p1 = new Podiatres(permis3,cycle,ordre,arrayList);
        assertThrows(FormationContinueException.class, ()->p1.validerPermis());
    }

    @Test
    void validerPermisCorrect() throws FormationContinueException {
        arrayList.addAll(liste);
        p1 = new Podiatres(permis1,cycle,ordre,arrayList);
        assertEquals(true, p1.resultat.isComplet());
    }

    @Test
    public void testMesurerInter(){
        p1.setCycle("2018-2021");
        i = p1.mesurerInter();
        assertEquals(new GregorianCalendar(2021, Calendar.JUNE, 1).getTime(), i.getMax());
        assertEquals(new GregorianCalendar(2018, Calendar.JUNE, 1).getTime(), i.getMin());
        assertEquals("2018-2021", i.getCycle());
    }

    @Test
    public void testMesurerInterNonVal(){
        p1.setCycle("2552-2558");
        i = p1.mesurerInter();
        assertEquals(null, i);
    }

    @Test
    public void testValiderHTotal(){
        arrayList.addAll(liste);
        p1.setActivites(arrayList);
        p1.validerHTotal();
        assertEquals(true, p1.getResultat().isComplet());
    }

    @Test
    public void tesValiderHTotalNonValide(){
        arrayList.addAll(liste2);
        p1.setActivites(arrayList);
        p1.validerHTotal();
        assertEquals(false, p1.getResultat().isComplet());
    }

}
