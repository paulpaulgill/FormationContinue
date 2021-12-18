package util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import exception.FormationContinueException;
import profession.Profession;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Statistiques {

    int declarationTraitees = 0;
    int declarationCompletes = 0;
    int declarationIncompletesInvalides = 0;
    int declarationHomme = 0;
    int declarationFemme = 0;
    int declarationSexeInconnu = 0;
    int totalActivites = 0;
    ArrayList<Integer> activitesParCat;
    ArrayList<Integer> declarationCompletesParOrdre;
    ArrayList<Integer> declarationIncompletesParOrdre;
    int declarationPermisInvalide = 0;

    private ObjectMapper objectMapper = new ObjectMapper();
    private DefaultPrettyPrinter pp = new DefaultPrettyPrinter();

    @JsonCreator
    public Statistiques(@JsonProperty("declarationTraitees") int declarationTraitees,
                        @JsonProperty("declarationCompletes")int declarationCompletes,
                        @JsonProperty("declarationIncompletesInvalides")int declarationIncompletesInvalides,
                        @JsonProperty("declarationHomme")int declarationHomme,
                        @JsonProperty("declarationFemme")int declarationFemme,
                        @JsonProperty("declarationSexeInconnu")int declarationSexeInconnu,
                        @JsonProperty("totalActivites")int totalActivites,
                        @JsonProperty("activitesParCat")ArrayList<Integer> activitesParCat,
                        @JsonProperty("declarationCompletesParOrdre")ArrayList<Integer> declarationCompletesParOrdre,
                        @JsonProperty("declarationIncompletesParOrdre")ArrayList<Integer> declarationIncompletesParOrdre,
                        @JsonProperty("declarationPermisInvalide")int declarationPermisInvalide) {
        this.declarationTraitees = declarationTraitees;
        this.declarationCompletes = declarationCompletes;
        this.declarationIncompletesInvalides = declarationIncompletesInvalides;
        this.declarationHomme = declarationHomme;
        this.declarationFemme = declarationFemme;
        this.declarationSexeInconnu = declarationSexeInconnu;
        this.totalActivites = totalActivites;
        this.activitesParCat = activitesParCat;
        this.declarationCompletesParOrdre = declarationCompletesParOrdre;
        this.declarationIncompletesParOrdre = declarationIncompletesParOrdre;
        this.declarationPermisInvalide = declarationPermisInvalide;
    }

    public Statistiques() {
    }

    public int getDeclarationTraitees() {
        return declarationTraitees;
    }

    public void setDeclarationTraitees(int declarationTraitees) {
        this.declarationTraitees = declarationTraitees;
    }

    public int getDeclarationCompletes() {
        return declarationCompletes;
    }

    public void setDeclarationCompletes(int declarationCompletes) {
        this.declarationCompletes = declarationCompletes;
    }

    public int getDeclarationIncompletesInvalides() {
        return declarationIncompletesInvalides;
    }

    public void setDeclarationIncompletesInvalides(int declarationIncompletesInvalides) {
        this.declarationIncompletesInvalides = declarationIncompletesInvalides;
    }

    public int getDeclarationHomme() {
        return declarationHomme;
    }

    public void setDeclarationHomme(int declarationHomme) {
        this.declarationHomme = declarationHomme;
    }

    public int getDeclarationFemme() {
        return declarationFemme;
    }

    public void setDeclarationFemme(int declarationFemme) {
        this.declarationFemme = declarationFemme;
    }

    public int getDeclarationSexeInconnu() {
        return declarationSexeInconnu;
    }

    public void setDeclarationSexeInconnu(int declarationSexeInconnu) {
        this.declarationSexeInconnu = declarationSexeInconnu;
    }

    public int getTotalActivites() {
        return totalActivites;
    }

    public void setTotalActivites(int totalActivites) {
        this.totalActivites = totalActivites;
    }

    public ArrayList<Integer> getActivitesParCat() {
        return activitesParCat;
    }

    public void setActivitesParCat(ArrayList<Integer> activitesParCat) {
        this.activitesParCat = activitesParCat;
    }

    public ArrayList<Integer> getDeclarationCompletesParOrdre() {
        return declarationCompletesParOrdre;
    }

    public void setDeclarationCompletesParOrdre(ArrayList<Integer> declarationCompletesParOrdre) {
        this.declarationCompletesParOrdre = declarationCompletesParOrdre;
    }

    public ArrayList<Integer> getDeclarationIncompletesParOrdre() {
        return declarationIncompletesParOrdre;
    }

    public void setDeclarationIncompletesParOrdre(ArrayList<Integer> declarationIncompletesParOrdre) {
        this.declarationIncompletesParOrdre = declarationIncompletesParOrdre;
    }

    public int getDeclarationPermisInvalide() {
        return declarationPermisInvalide;
    }

    public void setDeclarationPermisInvalide(int declarationPermisInvalide) {
        this.declarationPermisInvalide = declarationPermisInvalide;
    }

    public Statistiques chargerStat() throws FormationContinueException {
        Statistiques stat = new Statistiques();
        try {
            stat = objectMapper.readValue(new File("src/main/resources/stat.json"), Statistiques.class);
        }catch (FileNotFoundException e){
            stat = creerFichierStat();
        }catch (IOException e){
            throw new FormationContinueException("erreur inatendu lors de l'importation des statistique");
        }
        return stat;
    }

    public Statistiques creerFichierStat() throws FormationContinueException {
        Statistiques stat = new Statistiques(0,0,0,0,0,0,0,null,null,null,0);
        pp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE );
        try {
            objectMapper.writer(pp).writeValue(new File("src/main/resources/stat.json"),stat);
        } catch (IOException e) {
            throw new FormationContinueException("erreur inatendu lors de l'importation des statistique");
        }
        return stat;
    }

}
