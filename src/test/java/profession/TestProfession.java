package profession;

import exception.FormationContinueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.Activite;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TestProfession {

    Profession p1;
    String permis;
    String cycle;
    String nom;
    String prenom;
    String ordre;
    int sexe;
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
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
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
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
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
        prenom = "DOGNY";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class, ()->p1.validerPermis());
    }

    @Test
    @DisplayName("Le permis est correct")
    void validerPermisCorrect() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        permis = "DS0201";
        cycle = "2018-2021";
        ordre = "géologues";
        nom = "Dogny";
        prenom = "Serge";
        sexe = 1;
        p1 = new Geologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        p1.validerPermis();
        assertDoesNotThrow(() -> p1.validerPermis());
    }

    @Test
    @DisplayName("Le sexe est correct")
    void validerSexeCorrect() throws  FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        sexe = 1;
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        p1.validerDescription();
        assertEquals(true, p1.getResultat().isComplet());
    }

    @Test
    @DisplayName("Le sexe est incorrect")
    void validerSexeIncorrect() throws  FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        sexe = 8;
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        p1.validerDescription();
        assertEquals(true, p1.getResultat().isComplet());
    }

    @Test
    @DisplayName("La description est correct")
    void validerDescriptionCorrect() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
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
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class, ()->p1.validerDescription());
    }

    @Test
    @DisplayName("L'heure est négative")
    void validerHeureNegative() {
        Activite a = new Activite("Cours sur la déontologie", "cours", -2, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class, ()->p1.validerHeure());
    }

    @Test
    @DisplayName("L'heure est nulle")
    void validerHeureNulle() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 0, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class,() -> p1.validerHeure());
    }

    @Test
    @DisplayName("L'heure est correcte")
    void validerHeureCorrecte() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        p1.validerHeure();
        assertEquals(0,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("La date est correcte")
    void validerDateCorrecte() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        p1.validerDate();
        assertEquals(0,p1.getResultat().getErreur().size());
    }

    @Test
    @DisplayName("La date est incorrecte")
    void validerDateIncorrecte() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "D");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class,() -> p1.validerDate());
    }

    @Test
    @DisplayName("La date est n'est pas dans l'intervalle exigé")
    void validerDateIncorrecte2() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2016-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class,() -> p1.validerDate());
    }

    @Test
    @DisplayName("L'activité n'est pas reconnue")
    void validerCatActivitesNulle(){
        Activite a = new Activite("Cours sur la déontologie", "f", 0, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class,() -> p1.validerCatActivites());
    }

    @Test
    @DisplayName("L'activité est reconnue")
    void validerCatActivitesReconnue() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 0, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertDoesNotThrow(() -> p1.validerCatActivites());
    }

    @Test
    @DisplayName("L'activité a plus de 10h")
    void validerCatActivitesPlusDe10h() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 15, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        p1.validerCatActivites();
        assertDoesNotThrow(() -> p1.validerCatActivites());
        assertEquals(10,p1.getActivites().get(0).getHeures());
    }

    @Test
    @DisplayName("L'activité amoins de 10h")
    void validerCatActivitesPMoinsDe10h() {
        Activite a = new Activite("Cours sur la déontologie", "cours", 9, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertDoesNotThrow(() -> p1.validerCatActivites());
    }

    @Test
    @DisplayName("Le cyle est correct")
    void validerCycleCorrect() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 9, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
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
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class,() -> p1.validerCycle());
    }

    @Test
    @DisplayName("Les heures de la catégories sont correctes")
    void calculerHCatCorrect() {
        Activite a = new Activite("Conférence sur la déontologie", "conférence", 6, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2017-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertEquals(6,p1.calculerHCat(5));
    }

    @Test
    @DisplayName("Les heures de la catégorie sont incorrectes")
    void calculerHCatIncorrect() {
        Activite a = new Activite("Conférence sur la déontologie", "conférence", 6, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2017-2023";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertNotEquals(6,p1.calculerHCat(4));
        assertEquals(0,p1.calculerHCat(4));
    }

    @Test
    @DisplayName("Max de 10h par jour pour la meme categorie")
    void validerCatJour(){
        Activite a = new Activite("Conférence sur la déontologie", "conférence", 6, "2021-02-01");
        Activite b = new Activite("Conférence sur la déontologie", "conférence", 6, "2021-02-01");
        activites.add(a);
        activites.add(b);
        p1 = new Psychologues("A0001","2017-2023", "Johanson", "Dogny",2,"psychologue",activites);
        p1.validerCatJour();
        assertEquals(10,p1.calculerHCat(5));
    }

    @Test
    @DisplayName("Le prenom n'est pas correct")
    void validerPrenomPasCorrect(){
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        prenom= "";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class, ()->p1.validerPrenom());
    }

    @Test
    @DisplayName("Le prenom est correct")
    void validerPrenomCorrect() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        prenom = "Serge";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        p1.validerPrenom();
        assertEquals(true, p1.resultat.isComplet());
    }

    @Test
    @DisplayName("Le nom n'est pas correct")
    void validerNomPasCorrect(){
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        prenom = "Serge";
        nom = "";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        assertThrows(FormationContinueException.class, ()->p1.validerNom());
    }

    @Test
    @DisplayName("Le nom est correct")
    void validerNomCorrect() throws FormationContinueException {
        Activite a = new Activite("Cours sur la déontologie", "cours", 4, "2021-02-01");
        activites.add(a);
        permis = "A0001";
        cycle = "2018-2023";
        prenom = "Serge";
        nom = "Dogny";
        ordre = "psychologue";
        p1 = new Psychologues(permis,cycle,prenom, nom, sexe,ordre,activites);
        p1.validerNom();
        assertEquals(true, p1.resultat.isComplet());
    }
}
