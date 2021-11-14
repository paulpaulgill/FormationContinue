/*import Profession.Profession;
import Util.JSONHash;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class JSONHashTest {
    Profession p1;
    Profession p2;
    Profession p3;
    JSONHash json = new JSONHash("entree","sortie");
    String minuscule;
    String majusculevrai;
    String majusculefausse;
    String premiere_lettre;
    String inf5;
    String sup5;
    String egal5vrai;
    String egal5faux;
    int positif;
    int negatif;


    @BeforeEach
    void setUp(){
        p1 = new Architectes();
        p2 = new Geologues();
        p3 = new Psychologues();
        minuscule = "a";
        majusculefausse = "H";
        majusculevrai = "S";
        premiere_lettre = "1";
        inf5 = "A02";
        sup5 = "A00005";
        egal5vrai = "S2459";
        egal5faux = "Z54L8";
        positif = 8;
        negatif = -13;
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

    @Test
    @DisplayName("La première lettre du permis est A,R,S ou Z et la première lettre du permis est une majuscule")
    void permismajusculeARSZ(){
        assertEquals(true,json.verifierPremiereLettrePermis(majusculevrai));
    }

    @Test
    @DisplayName("La première lettre du permis n'est pas A,R,S ou Z et la première lettre du permis est une majuscule")
    void permismajusculesansARSZ(){
        assertEquals(false,json.verifierPremiereLettrePermis(majusculefausse));
    }

    @Test
    @DisplayName("La première lettre du permis est une minuscule")
    void permisminusculeARSZ(){
        assertEquals(false,json.verifierPremiereLettrePermis(minuscule));
    }

    @Test
    @DisplayName("La première lettre du permis est un entier")
    void permisentier(){
        assertEquals(false,json.verifierPremiereLettrePermis(premiere_lettre));
    }

    @Test
    @DisplayName("La taille du permis est inférieur à 5")
    void permisinferieura5(){
        assertEquals(false,json.verifier4chiffresPermis(inf5));
    }

    @Test
    @DisplayName("La taille du permis est supérieur à 5")
    void permissuperieura5(){
        assertEquals(false,json.verifier4chiffresPermis(sup5));
    }

    @Test
    @DisplayName("La taille du permis est égale à 5 et la première lettre est suivie de 4 chiffres")
    void permisegal5vrai(){
        assertEquals(true,json.verifier4chiffresPermis(egal5vrai));
    }

    @Test
    @DisplayName("La taille du permis est égale à 5 et la première lettre est suivie de 3 chiffres et une lettre")
    void permisegal5faux(){
        assertEquals(false,json.verifier4chiffresPermis(egal5faux));
    }


    @Test
    @DisplayName("L'heure de l'activité est négative")
    void entiernegatif(){
        assertEquals(false,json.estEntierpositif(negatif));
    }

    @Test
    @DisplayName("L'heure de l'activité est positive")
    void entierpositif(){
        assertEquals(true,json.estEntierpositif(positif));
    }


}*/