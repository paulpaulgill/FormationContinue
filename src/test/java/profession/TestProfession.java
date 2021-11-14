package profession;

import exception.FormationContinueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.Activite;
import util.Resultat;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestProfession {

    Profession p1;
    String permis;
    String cycle;
    int heuresTrans;
    String ordre;
    ArrayList<Activite> activites = new ArrayList<>();


    @BeforeEach
    void setUp(){
        activites.clear();
        p1 = null;
    }


    @Test
    @DisplayName("Le bon resultat est retourné")
    void resultatCorrect(){
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        assertEquals(true,p1.getResultat().isComplet());
        assertEquals(0,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("Le mauvais resultat est retourné")
    void resultatIncorrect(){
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.resultat.ajouterErreur("err");
        p1.resultat.setComplet(false);
        assertNotEquals(true,p1.getResultat().isComplet());
        assertNotEquals(0,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("Le permis n'est pas correct")
    void validerPermisPasCorrect(){
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "44489";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        assertThrows(FormationContinueException.class, ()->p1.validerPermis());
    }

    @Test
    @DisplayName("Le permis est correct")
    void validerPermisCorrect() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerPermis();
        assertEquals(true, p1.resultat.isComplet());
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
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

    @Test
    @DisplayName("La date est n'est pas dans l'intervalle exigé")
    void validerDateIncorrecte2(){
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2016-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerDate();
        assertEquals(1,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("L'activité n'est pas reconnue")
    void validerCatActivitesNulle(){
        Activite a = new Activite("Cours sur la déontologie", "f", 0, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerCatActivites();
        assertEquals(1,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("L'activité est reconnue")
    void validerCatActivitesReconnue() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 0, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerCatActivites();
        assertEquals(0,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("L'activité a plus de 10h")
    void validerCatActivitesPlusDe10h() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 15, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerCatActivites();
        assertEquals(1,p1.getResultat().getErreur().size());
        assertEquals(10,p1.getActivites().get(0).getHeures());
    }

    @Test
    @DisplayName("L'activité amoins de 10h")
    void validerCatActivitesPMoinsDe10h() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 9, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerCatActivites();
        assertEquals(0,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("Le cyle est correct")
    void validerCycleCorrect() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 9, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerCycle();
        assertEquals(0,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("Le cyle est incorrect")
    void validerCycleIncorrect() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 9, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2017-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        p1.validerCycle();
        assertEquals(1,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("Les heures de la catégories sont correctes")
    void calculerHCatCorrect() {
        Activite a = new Activite("Conférence sur la déontologie", "conférence", 6, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2017-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        assertEquals(6,p1.calculerHCat(5));
    }

    @Test
    @DisplayName("Les heures de la catégorie sont incorrectes")
    void calculerHCatIncorrect() {
        Activite a = new Activite("Conférence sur la déontologie", "conférence", 6, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2017-2023";
        heuresTrans = 3;
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,heuresTrans,ordre,activites);
        assertNotEquals(6,p1.calculerHCat(4));
        assertEquals(0,p1.calculerHCat(4));
    }

}