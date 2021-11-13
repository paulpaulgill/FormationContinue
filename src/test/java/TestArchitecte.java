import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import profession.Architectes;
import util.Activite;
import org.junit.Assert;
import util.IntervalCycle;

import java.util.*;

public class TestArchitecte {
    private String descr = "qwertyuiopqwertyuiopqwwq";
    private Activite act1 = new Activite(descr, "cours", 5, "2021-02-02");
    private Activite act2 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act3 = new Activite(descr, "cours", 10, "2021-01-02");
    private Activite act4 = new Activite(descr, "groupe de discussion", 10, "2021-01-02");
    private Activite act5 = new Activite(descr, "séminaire", 10, "2021-01-02");
    private Architectes a1 = new Architectes(descr,null,0, null, null);
    private List<Activite> liste = Arrays.asList(act1, act2, act3, act5, act4);
    private List<Activite> liste2 = Arrays.asList(act1);
    private ArrayList<Activite> arrayList;
    private Architectes a2NV;
    private Architectes a3;
    private IntervalCycle i;

    @AfterEach
    public void tearDown(){
        a1 = null;
        a2NV = null;
        a3 = null;
        arrayList = null;
        i = null;
    }

    @BeforeEach
    public void setUp(){
        i = new IntervalCycle();
        arrayList = new ArrayList<>();
    }

    @Test
    public void testMesurerInterval2020(){
        IntervalCycle i = new IntervalCycle();
        a1.setCycle("2020-2022");
        i = a1.mesurerInter();
        Assert.assertEquals("2020-2022", i.getCycle());
        Assert.assertEquals(new GregorianCalendar(2022, Calendar.APRIL, 1).getTime(), i.getMax());
        Assert.assertEquals(new GregorianCalendar(2020, Calendar.APRIL, 1).getTime(), i.getMin());
    }

    @Test
    public void testMesurerInterval2018(){
        a1.setCycle("2018-2020");
        i = a1.mesurerInter();
        Assert.assertEquals("2018-2020", i.getCycle());
        Assert.assertEquals(new GregorianCalendar(2020, Calendar.APRIL, 1).getTime(), i.getMax());
        Assert.assertEquals(new GregorianCalendar(2018, Calendar.APRIL, 1).getTime(), i.getMin());
    }

    @Test
    public void testMesurerInterval2016(){
        a1.setCycle("2016-2018");
        i = a1.mesurerInter();
        Assert.assertEquals("2016-2018", i.getCycle());
        Assert.assertEquals(new GregorianCalendar(2018, Calendar.JULY, 1).getTime(), i.getMax());
        Assert.assertEquals(new GregorianCalendar(2016, Calendar.APRIL, 1).getTime(), i.getMin());
    }

    @Test
    public void testMesurerIntervalNonValide(){
        a1.setCycle("1939-1945");
        i = a1.mesurerInter();
        Assert.assertEquals(null, i);
    }

    @Test
    public void testValiderHTotal(){
        arrayList.addAll(liste);
        a1.setHeuresTrans(4);
        a1.setActivites(arrayList);
        a1.validerHTotal();
        Assert.assertEquals(true, a1.getResultat().isComplet());
    }

    @Test
    public void testValiderHTotalNonValide(){
        arrayList.addAll(liste2);
        a1.setHeuresTrans(0);
        a1.setActivites(arrayList);
        a1.validerHTotal();
        Assert.assertEquals(false, a1.getResultat().isComplet());
    }

    @Test
    public void testValiderHMin(){
        arrayList.addAll(liste);
        a1.setActivites(arrayList);
        a1.validerHMin();
        Assert.assertEquals(true, a1.getResultat().isComplet());
    }

    @Test
    public void testValiderHMinPasComplet(){
        arrayList.addAll(liste2);
        a1.setActivites(arrayList);
        a1.validerHMin();
        Assert.assertEquals(false, a1.getResultat().isComplet());
    }





}
