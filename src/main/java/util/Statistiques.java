package util;

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

    public Statistiques() {
    }
}
