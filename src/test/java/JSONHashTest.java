import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONHashTest {
    Profession p1;
    Profession p2;
    Profession p3;

    @BeforeEach
    void setUp(){
        p1 = new Architectes();
        p2 = new Geologues();
        p3 = new Psychologues();
    }

    @AfterEach
    void tearDown(){
        p1 = null;
        p2 = null;
        p3 = null;
    }


    @Test
    public void testerVerifierCycleArchitect1() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("cycle", "2020-2022");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Architectes();
        h.verifierCycle();
        assertEquals(true , h.getList1().isEmpty());
    }

    @Test
    public void testerVerifierCycleArchitect2() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("cycle", "2018-2020");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Architectes();
        h.verifierCycle();
        assertEquals(true , h.getList1().isEmpty() );
    }

    @Test
    public void testerVerifierCycleArchitect3() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("cycle", "2016-2018");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Architectes();
        h.verifierCycle();
        assertEquals(true , h.getList1().isEmpty());
    }

    @Test
    public void testerVerifierCycleArchitectPasBon() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("cycle", "1939-1945");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Architectes();
        h.verifierCycle();
        assertEquals(false , h.getList1().isEmpty() );
    }

    @Test
    public void testerVerifierCycleGeo() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("cycle", "2018-2021");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Geologues();
        h.verifierCycle();
        assertEquals(true , h.getList1().isEmpty() );
    }

    @Test
    public void testerVerifierCycleGeoPasBon() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("cycle", "2552-2554");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Geologues();
        h.verifierCycle();
        assertEquals(false , h.getList1().isEmpty() );
    }

    @Test
    public void testerVerifierCyclePsy() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("cycle", "2018-2023");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Psychologues();
        h.verifierCycle();
        assertEquals(true , h.getList1().isEmpty() );
    }

    @Test
    public void testerVerifierCyclePsyPasBon() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("cycle", "1914-1918");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Psychologues();
        h.verifierCycle();
        assertEquals(false , h.getList1().isEmpty() );
    }

    @Test
    public void testVerifierTypeOrdreArchitecte() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("ordre", "architectes");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.verifierTypeOrdre();
        assertEquals(p1 instanceof Architectes, h.ordre instanceof Architectes);
    }

    @Test
    public void testVerifierTypeOrdreGeo() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("ordre", "géologues");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.verifierTypeOrdre();
        assertEquals(p2 instanceof Geologues, h.ordre instanceof Geologues);
    }

    @Test
    public void testVerifierTypeOrdrePsy() {
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("ordre", "psychologue");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.verifierTypeOrdre();
        assertEquals(p2 instanceof Psychologues, h.ordre instanceof Psychologues);
    }


    @Test
    public void testVerifierHeuresMinArchitecte(){
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("heures_transferees_du_cycle_precedent","0");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Architectes();
        h.verifierHeuresMin();
        //false car nbheure = 0 donc list un contient
        assertEquals(false, h.getList1().isEmpty());
    }

    @Test
    public void testVerifierHeuresMinGeo(){
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("heures_transferees_du_cycle_precedent","0");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Geologues();
        h.verifierHeuresMin();
        //false car nbheure = 0 donc list un contient
        assertEquals(false, h.getList1().isEmpty());
    }

    @Test
    public void testVerifierHeuresMinPsy(){
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("heures_transferees_du_cycle_precedent","0");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Psychologues();
        h.verifierHeuresMin();
        //false car nbheure = 0 donc list un contient
        assertEquals(false, h.getList1().isEmpty());
    }


    @Test
    public void testVerifierHeureTrfArchitecte(){
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("heures_transferees_du_cycle_precedent","4");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Architectes();
        assertEquals(4,h.verifierHeureTrf());
    }
    @Test
    public void testVerifierHeureTrfArchitecteHeureTrop(){
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("heures_transferees_du_cycle_precedent","10");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Architectes();
        assertEquals(7,h.verifierHeureTrf());
    }

    @Test
    public void testVerifierHeureTrfPasArchitecte(){
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj.put("heures_transferees_du_cycle_precedent","4");
        JSONHash h = new JSONHash(obj.toString(), obj2.toString());
        h.jsonO = (JSONObject) JSONSerializer.toJSON(obj.toString());
        h.ordre = new Psychologues();
        assertEquals(0,h.verifierHeureTrf());
    }
}