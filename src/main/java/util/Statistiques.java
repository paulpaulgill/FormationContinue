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

    int declarationTraitees;
    int declarationCompletes;
    int declarationIncompletesInvalides;
    int declarationHomme;
    int declarationFemme;
    int declarationSexeInconnu;
    int totalActivites;
    ArrayList<Integer> activitesParCat;
    ArrayList<Integer> declarationCompletesParOrdre;
    ArrayList<Integer> declarationIncompletesParOrdre;
    int declarationPermisInvalide;

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

    public Statistiques chargerStat() throws FormationContinueException {
        Statistiques stat;
        try {
            stat = objectMapper.readValue(new File("src/main/resources/stat.json"), Statistiques.class);
        }catch (FileNotFoundException e){
            stat = new Statistiques();
        }catch (IOException e){
            throw new FormationContinueException("erreur inatendu lors de l'importation des statistique");
        }
        return stat;
    }
}
