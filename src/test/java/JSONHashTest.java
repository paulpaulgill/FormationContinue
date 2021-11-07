import net.sf.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JSONHashTest {
    Profession p1;
    Profession p2;
    Profession p3;
    String minuscule;
    String majusculevrai;
    String majusculefausse;
    String premiere_lettre;
    String inf5;
    String sup5;
    String egal5vrai;
    String egal5faux;


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
    }

    @AfterEach
    void tearDown(){
        p1 = null;
        p2 = null;
        p3 = null;
    }

    /*
    @Test
    public void testerVerifierCycle(){
        p1 = null;
        JSONObject j  = new JSONObject();
        assertEquals(p1 instanceof Architectes , JSONHash.verifierTypeOrdre().);
    }
    */

    @Test
    @DisplayName("La première lettre du permis est A,R,S ou Z et la première lettre du permis est une majuscule")
    void permismajusculeARSZ() throws FormationContinueException {
        assertEquals(true,JSONHash.verifierPremiereLettrePermis(majusculevrai));
    }

    @Test
    @DisplayName("La première lettre du permis n'est pas A,R,S ou Z et la première lettre du permis est une majuscule")
    void permismajusculesansARSZ() throws FormationContinueException {
        assertEquals(false,JSONHash.verifierPremiereLettrePermis(majusculefausse));
    }

    @Test
    @DisplayName("La première lettre du permis est une minuscule")
    void permisminusculeARSZ() throws FormationContinueException {
        assertEquals(false,JSONHash.verifierPremiereLettrePermis(minuscule));
    }

    @Test
    @DisplayName("La première lettre du permis est un entier")
    void permisentier() throws FormationContinueException {
        assertEquals(false,JSONHash.verifierPremiereLettrePermis(premiere_lettre));
    }

    @Test
    @DisplayName("La taille du permis est inférieur à 5")
    void permisinferieura5() throws FormationContinueException {
        assertEquals(false,JSONHash.verifier4chiffresPermis(inf5));
    }

    @Test
    @DisplayName("La taille du permis est supérieur à 5")
    void permissuperieura5() throws FormationContinueException {
        assertEquals(false,JSONHash.verifier4chiffresPermis(sup5));
    }

    @Test
    @DisplayName("La taille du permis est égale à 5 et la première lettre est suivie de 4 chiffres")
    void permisegal5vrai() throws FormationContinueException {
        assertEquals(true,JSONHash.verifier4chiffresPermis(egal5vrai));
    }

    @Test
    @DisplayName("La taille du permis est égale à 5 et la première lettre est suivie de 3 chiffres et une lettre")
    void permisegal5faux() throws FormationContinueException {
        assertEquals(false,JSONHash.verifier4chiffresPermis(egal5faux));
    }

}