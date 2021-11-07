import net.sf.json.JSONObject;
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

    /*
    @Test
    public void testerVerifierCycle(){
        p1 = null;
        JSONObject j  = new JSONObject();
        assertEquals(p1 instanceof Architectes , JSONHash.verifierTypeOrdre().);
    }
    */
    @Tag("Format du permis")

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

    @Tag("Format heures des activités")

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



}