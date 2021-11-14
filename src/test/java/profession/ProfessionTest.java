package profession;

import exception.FormationContinueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.Activite;
import util.Resultat;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProfessionTest{

    //Déclaration avant
    int positif;
    Profession p1;
    Profession p2;
    Profession p3;
    String permis;
    String cycle;
    int heuresTrans;
    String ordre;
    ArrayList<Activite> activites = new ArrayList<>();


    @BeforeEach
    void setUp(){
        //attribution de valeur
        positif = 8;
        activites.clear();
        p1 = null;
    }


    @Test
    @DisplayName("Le bon resultat est retourné")
    void resultat(){
    }

    @Test
    @DisplayName("Le permis n'est pas correct")
    void validerPermisPasCorrect(){
        permis = "faux";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        assertThrows(FormationContinueException.class, ()->p1.validerPermis());
    }

    @Test
    @DisplayName("Le permis est correct")
    void validerPermisCorrect(){
    }

    @Test //OK
    @DisplayName("La description est correct")
    void validerDescriptionCorrect() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerDescription();
        assertEquals(true, p1.getResultat().isComplet());
    }

    @Test //OK
    @DisplayName("La description n'est pas correct")
    void validerDescriptionIncorrect(){
        Activite a = new Activite("Rien", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        assertThrows(FormationContinueException.class, ()->p1.validerDescription());
    }

    @Test //OK
    @DisplayName("L'heure est négative")
    void validerHeureNegative(){
        Activite a = new Activite("Cours sur la déontologie", "cours", -2, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        assertThrows(FormationContinueException.class, ()->p1.validerHeure());
    }
    @Test //OK
    @DisplayName("L'heure est nulle")
    void validerHeureNulle() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 0, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerHeure();
        assertEquals(1,p1.getResultat().getErreur().size());
    }

    @Test //OK
    @DisplayName("L'heure est correcte")
    void validerHeureCorrecte() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerHeure();
        assertEquals(0,p1.getResultat().getErreur().size());
    }

    @Test //OK
    @DisplayName("La date est correcte")
    void validerDateCorrecte(){
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerDate();
        assertEquals(0,p1.getResultat().getErreur().size());
    }

    @Test //OK
    @DisplayName("La date est incorrecte")
    void validerDateIncorrecte(){
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "D");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerDate();
        assertEquals(1,p1.getResultat().getErreur().size());
    }



}