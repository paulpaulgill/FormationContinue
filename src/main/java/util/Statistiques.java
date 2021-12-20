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

public class Statistiques {

    private int declarationTraitees = 0;
    private int declarationCompletes = 0;
    private int declarationIncompletesInvalides = 0;
    private int declarationHomme = 0;
    private int declarationFemme = 0;
    private int declarationSexeInconnu = 0;
    private int totalActivites = 0;
    private int[] activitesParCat = new int[10];
    private int[] declarationCompletesParOrdre = new int[4];
    private int[] declarationIncompletesParOrdre = new int[4];
    private int declarationPermisInvalide = 0;

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
                        @JsonProperty("activitesParCat")int[] activitesParCat,
                        @JsonProperty("declarationCompletesParOrdre")int[] declarationCompletesParOrdre,
                        @JsonProperty("declarationIncompletesParOrdre")int[] declarationIncompletesParOrdre,
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

    public int[] getActivitesParCat() {
        return activitesParCat;
    }

    public void setActivitesParCat(int[] activitesParCat) {
        this.activitesParCat = activitesParCat;
    }

    public int[] getDeclarationCompletesParOrdre() {
        return declarationCompletesParOrdre;
    }

    public void setDeclarationCompletesParOrdre(int[] declarationCompletesParOrdre) {
        this.declarationCompletesParOrdre = declarationCompletesParOrdre;
    }

    public int[] getDeclarationIncompletesParOrdre() {
        return declarationIncompletesParOrdre;
    }

    public void setDeclarationIncompletesParOrdre(int[] declarationIncompletesParOrdre) {
        this.declarationIncompletesParOrdre = declarationIncompletesParOrdre;
    }

    public int getDeclarationPermisInvalide() {
        return declarationPermisInvalide;
    }

    public void setDeclarationPermisInvalide(int declarationPermisInvalide) {
        this.declarationPermisInvalide = declarationPermisInvalide;
    }

    public Statistiques chargerStat(String nomFichierStat, boolean reinitialise) {
        Statistiques stat = new Statistiques();
        try {
            if (reinitialise){
                stat = creerFichierStat();
                System.out.println("Statistique réinitialiser");
            }else {
                stat = objectMapper.readValue(new File(nomFichierStat), Statistiques.class);
            }
        }catch (FileNotFoundException e){
            stat = creerFichierStat();
        }catch (IOException e){
            System.err.println("erreur inatendu lors de l'importation des statistique");
            stat = null;
        }
        return stat;
    }

    public Statistiques creerFichierStat() {
        Statistiques stat = new Statistiques();
        try {
            pp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE );
            objectMapper.writer(pp).writeValue(new File("stat.json"),stat);
        } catch (IOException e) {
            System.err.println("erreur inatendu lors de l'importation des statistique");
            stat = null;
        }
        return stat;
    }

    public boolean exporterStat(String nomFichierStat) {
        boolean echouer = false;
        try {
            pp.indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE );
            objectMapper.writer(pp).writeValue(new File(nomFichierStat),this);
        }catch (FileNotFoundException erreur){
            System.err.println("Fichier des statistiques introuvable.");
            echouer = true;
        }catch (IOException erreur){
            System.err.println("erreur inatendu lors de l'exportation des statistique");
            echouer = true;
        }
        return echouer;
    }

    public void genererStat(Profession declaration){
        declarationTraitees ++;
        compterValideEtInvalide(declaration);
        compterSexe(declaration);
        totalActivites = totalActivites + declaration.getActivites().size();
        compterActivitesParCat(declaration);
        compterCompletOuIncompletOrdre(declaration);
    }

    public void genererStatInvalid(Profession declaration) {
        declarationTraitees ++;
        declarationIncompletesInvalides ++;
        try {
            declaration.validerPermis();
        }catch (FormationContinueException e){
            declarationPermisInvalide++;
        }catch (Exception e){
            System.exit(-1);
        }
    }

    public void compterSexe(Profession declaration){
        if (declaration.getSexe() == 1){
            declarationHomme ++;
        }else if (declaration.getSexe() == 2){
            declarationFemme ++;
        }else if (declaration.getSexe() == 0){
            declarationSexeInconnu++;
        }
    }

    public void compterValideEtInvalide(Profession declaration){
        if (declaration.getResultat().isComplet()){
            declarationCompletes ++;
        }else {
            declarationIncompletesInvalides ++;
        }
    }

    public void compterActivitesParCat(Profession declaration){
        int x;
        for (int i = 0; i < declaration.getActivites().size(); i++){
            x = declaration.getActivites().get(i).getCategorieNum() - 1;
            activitesParCat[x] = activitesParCat[x] + 1;
        }
    }

    public void compterCompletOuIncompletOrdre(Profession declaration){
        int x = declaration.getOrdreNum();
        if(declaration.getResultat().isComplet()){
            declarationCompletesParOrdre[x] = declarationCompletesParOrdre[x] + 1;
        }else {
            declarationIncompletesParOrdre[x] = declarationIncompletesParOrdre[x] + 1;
        }
    }

    @Override
    public String toString(){
        String affichage;
        affichage =
        "\nnombre total de déclarations traitées : " + declarationTraitees +
        "\nnombre total de déclarations complètes : " + declarationCompletes +
        "\nnombre total de déclarations incomplètes ou invalides : " + declarationIncompletesInvalides +
        "\nnombre total de déclarations faites par des hommes : " + declarationHomme +
        "\nnombre total de déclarations faites par des femmes : " + declarationFemme +
        "\nnombre total de déclarations faites par des gens de sexe inconnu : " + declarationSexeInconnu +
        "\nnombre total d'activités dans les déclarations : " + totalActivites +
        "\nnombre d'activités par catégorie :" +
                "\n\tcours : " + activitesParCat[0] +
                "\n\tatelier : " + activitesParCat[1] +
                "\n\tséminaire : " + activitesParCat[2] +
                "\n\tcolloque : " + activitesParCat[3] +
                "\n\tconférence : " + activitesParCat[4] +
                "\n\tlecture dirigée : " + activitesParCat[5] +
                "\n\tprésentation : " + activitesParCat[6] +
                "\n\tgroupe de discussion : " + activitesParCat[7] +
                "\n\tprojet de recherche : " + activitesParCat[8] +
                "\n\trédaction professionnell : " + activitesParCat[9] +
        "\nnombre total de déclarations valides et complètes par type d'ordre professionnel : " +
                "\n\tarchitectes : " + declarationCompletesParOrdre[0] +
                "\n\tgéologues : " + declarationCompletesParOrdre[1] +
                "\n\tpsychologues : " + declarationCompletesParOrdre[2] +
                "\n\tpodiatres : " + declarationCompletesParOrdre[3] +
        "\nnombre total de déclarations valides et incomplètes par type d'ordre professionnel : " +
                "\n\tarchitectes : " + declarationIncompletesParOrdre[0] +
                "\n\tgéologues : " + declarationIncompletesParOrdre[1] +
                "\n\tpsychologues : " + declarationIncompletesParOrdre[2] +
                "\n\tpodiatres : " + declarationIncompletesParOrdre[3] +
        "\nnombre de déclarations soumises avec un numéro de permis invalide : " + declarationPermisInvalide;
        return affichage;
    }

}
