package profession;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import exception.FormationContinueException;
import util.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Profession extends Declaration {
    protected Resultat resultat = new Resultat();

    @JsonCreator
    public Profession(@JsonProperty("numero_de_permis") String permis,
                      @JsonProperty("cycle") String cycle,
                      @JsonProperty("heures_transferees_du_cycle_precedent") int heuresTrans,
                      @JsonProperty("ordre") String ordre,
                      @JsonProperty("activites") ArrayList<Activite> activites) {
        super(permis, cycle, heuresTrans, ordre, activites);
    }

    @Override
    public Resultat getResultat() {
        return resultat;
    }

    public void validerPermis() throws FormationContinueException {
        Pattern p = Pattern.compile("\\b[ARSZ][0-9]{4}\\b");
        Matcher m = p.matcher(permis);
        if (!m.matches()){
            lancerErreurStrut();
        }
    }
    public void validerDescription() throws FormationContinueException {
        for (int i = 0; i < activites.size(); i++) {
            Pattern p = Pattern.compile(".{21,}");
            Matcher m = p.matcher(activites.get(i).getDescription());
            if (!m.matches()){
                lancerErreurStrut();
            }
        }
    }

    public void validerHeure() throws FormationContinueException {
        for (int i = 0; i < activites.size(); i++){
            if (activites.get(i).getHeures() == 0) {
                resultat.ajouterErreur("Le nombre d'heure minimum est de 1. L'activite "
                        + activites.get(i).getDescription() + " sera ignoree");
                activites.get(i).setIgnore(true);
            }if (activites.get(i).getHeures() < 0) {
                lancerErreurStrut();
            }
        }
    }

    public void lancerErreurStrut() throws FormationContinueException {
        resultat.ecraserErreur("Le fichier d'entrée est invalide.");
        resultat.setComplet(false);
        throw new FormationContinueException("La structure du fichier d'entrée n'est pas respecté");
    }

    private boolean verifierFormatDate(int i) {
        boolean valide = false;
        String date = activites.get(i).getDate();
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            valide = true;
        } catch (IllegalArgumentException erreur) {
            System.out.println("Une erreur est survenue lors du traitement de la date.");
            System.exit(-1);
        } catch (DateTimeParseException erreur) {
            valide = false;
        }
        return valide;
    }


    private boolean estEntreDate(int i, IntervalCycle interCycle) {
        boolean valide = false;
        Date max = interCycle.getMax();
        Date min = interCycle.getMin();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(activites.get(i).getDate());
            valide = date.getTime() >= min.getTime() && date.getTime() <= max.getTime();
        } catch (ParseException erreur) {
            valide = false;
        }
        return valide;
    }


    private void ecrireErrDate(String finMsg, int i) {
        resultat.ajouterErreur("La date de l'activité " +
                activites.get(i).getDescription() + finMsg);
        activites.get(i).setIgnore(true);
    }

    public void validerDate() {
        for (int i = 0; i < activites.size(); i++) {
            if (!verifierFormatDate(i)){
                ecrireErrDate(" ne respecte pas le format ISO 8601. Elle sera ignoré", i);
            }else if (!estEntreDate(i,mesurerInter())){
                ecrireErrDate(" n'est pas dans l'intervalle exigée. Elle sera ignoré", i);
            }
        }
    }

    public void validerCatActivites(){
        for (int i = 0; i < activites.size(); i++){
            if (activites.get(i).getCategorieNum() == 0){
                resultat.ajouterErreur("L'activité " + activites.get(i).getDescription() +
                        " est dans une catégorie non reconnue. Elle sera ignorée");
                activites.get(i).setIgnore(true);
            }else if (activites.get(i).getHeures() > 10){
                resultat.ajouterErreur("L'activité " + activites.get(i).getDescription() +
                        " inclue plus que 10h dans la même journée. Seulement 10h" +
                        "seront considéré dans les calculs");
                activites.get(i).setHeures(10);
            }
        }
    }

    public int calculerHCat(int cat){
        int heuresCat = 0;
        for (int i = 0; i < activites.size(); i++ ){
            if(activites.get(i).getCategorieNum() == (cat) && !activites.get(i).getIgnore()){
                heuresCat = heuresCat + activites.get(i).getHeures();
            }
        }
        return heuresCat;
    }

    public void validerCycle(){
        if (mesurerInter() == null){
            resultat.ajouterErreur("Cycle erroné.");
            resultat.setComplet(false);
        }
    }

    public abstract IntervalCycle mesurerInter();

    public abstract void validerHTotal();
}
