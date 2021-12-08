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
                      @JsonProperty(value = "heures_transferees_du_cycle_precedent" , required = false) int heuresTrans,
                      @JsonProperty("ordre") String ordre,
                      @JsonProperty("activites") ArrayList<Activite> activites) {
        super(permis, cycle, heuresTrans, ordre, activites);
    }

    public Profession(){}

    @Override
    public Resultat getResultat() {
        return resultat;
    }

    public void setResultat(Resultat resultat){
        this.resultat = resultat;
    }

    /**
     * Valide si le permis respect le format
     * @throws FormationContinueException
     */
    public void validerPermis() throws FormationContinueException {
        Pattern p = Pattern.compile("\\b[ARSZ][0-9]{4}\\b");
        Matcher m = p.matcher(permis);
        if (!m.matches()){
            lancerErreurStrut();
        }
    }

    /**
     * Valide que la description fait respecte la longueur demandee
     * @throws FormationContinueException
     */
    public boolean validerDescription() throws FormationContinueException {
        boolean valide = true;
        for (int i = 0; i < activites.size(); i++) {
            Pattern p = Pattern.compile(".{21,}");
            Matcher m = p.matcher(activites.get(i).getDescription());
            if (!m.matches()){
                valide = false;
            }
        }
        return valide;
    }

    /**
     * Valide si le nombre d'heure est un entier positif plus grand que 0
     * si == 0, msg est produit sur fichier de sortie et l'activitee est ignoree
     * si < 0 une erreur est lancee
     * @throws FormationContinueException
     */
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

    /**
     * Lance une esception, ecrit sur le fichier de sortie et met complet a faux
     * @throws FormationContinueException
     */
    public void lancerErreurStrut() throws FormationContinueException {
        resultat.ecraserErreur("Le fichier d'entrée est invalide.");
        resultat.setComplet(false);
        throw new FormationContinueException("La structure du fichier d'entrée n'est pas respecté");
    }

    /**
     * Valide que le format de la date soit respectee et respect les annees bisectiles.
     * Si elle ne l'est pas, retourne faux
     * @param i cpt
     * @return boolean valide qui indique si la date est valide
     */
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

    /**
     * vierifie si la date d'une activite est dans l'interval valide.
     * @param i cpt
     * @param interCycle contient le cycle en String, la date min et la date max
     * @return
     */
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

    /**
     * Ecrit un msg d'erreur sur le fichier de sortie a propos de la date.
     * @param finMsg String qui est la fin du msg
     * @param i cpt
     */
    private void ecrireErrDate(String finMsg, int i) {
        resultat.ajouterErreur("La date de l'activité " +
                activites.get(i).getDescription() + finMsg);
        activites.get(i).setIgnore(true);
    }

    /**
     * si le format ou la date n'est pas valide, un msg d'erreur est produit sur fichier de sortie
     */
    public void validerDate() {
        for (int i = 0; i < activites.size(); i++) {
            if (!verifierFormatDate(i)){
                ecrireErrDate(" ne respecte pas le format ISO 8601. Elle sera ignoré", i);
            }else if (!estEntreDate(i,mesurerInter())){
                ecrireErrDate(" n'est pas dans l'intervalle exigée. Elle sera ignoré", i);
            }
        }
    }

    /**
     * Verifie si les activites sont dans une categorie valide ou qu'une categorie
     * n'est pas plus de 10 heures dans la meme journee. Un message d'erreur est produit
     * si cela n'est pas respecte.
     */
    public void validerCatActivites(){
        for (int i = 0; i < activites.size(); i++){
            if (activites.get(i).getCategorieNum() == 0){
                resultat.ajouterErreur("L'activité " + activites.get(i).getDescription() +
                        " est dans une catégorie non reconnue. Elle sera ignorée");
                activites.get(i).setIgnore(true);
            }else if (activites.get(i).getHeures() > 10){
                resultat.ajouterErreur("L'activité " + activites.get(i).getDescription() +
                        " inclue plus que 10h dans la même journée. Seulement 10h" +
                        " seront considéré dans les calculs");
                activites.get(i).setHeures(10);
            }
        }
    }

    /**
     * Calcul le nombre d'heure d'une categorie
     * @param cat
     * @return
     */
    public int calculerHCat(int cat){
        int heuresCat = 0;
        for (int i = 0; i < activites.size(); i++ ){
            if(activites.get(i).getCategorieNum() == (cat) && !activites.get(i).getIgnore()){
                heuresCat = heuresCat + activites.get(i).getHeures();
            }
        }
        return heuresCat;
    }

    /**
     * Cherche dans les activités si il a des activités qui se produise le
     * meme jour ET sont dans la meme catégorie. Si il en est le cas les heures
     * sont additionné et si elle dépasse 10h alors un message d'erreur est
     * ajouté.
     */
    public void validerCatJour(){
        int heures = 0;
        activites.removeIf(Activite::getIgnore);
        for (int y = 0; y < activites.size(); y++) {
            heures = 0;
            for (int i = 0; i < activites.size(); i++) {
                if (activites.get(i).equals(activites.get(y))) {
                    heures = heures + activites.get(i).getHeures();
                }
            }
            if (heures > 10) {
                for (int i = 0; i < activites.size(); i++) {
                    if (i == y) {
                        activites.get(y).setHeures(10);
                    } else if (activites.get(i).equals(activites.get(y))) {
                        activites.get(i).setHeures(0);
                    }
                }
                resultat.ajouterErreur("Des activités de catégorie " + activites.get(y).getCategorie() +
                        " inclue plus que 10h dans la même journée. Seulement 10h" +
                        " seront considéré dans les calculs de cette journée");
            }
            activites.removeIf(activite -> activite.getHeures() == 0);
        }
    }

    /**
     * verifie si le IntervalCycle est null. Si il l'est un msg d'erreur est
     * produit sur le fichier de sortie.
     */
    public void validerCycle(){
        if (mesurerInter() == null){
            resultat.ajouterErreur("Cycle erroné.");
            resultat.setComplet(false);
        }
    }

    /**
     * Trouve quel interval est mentionne
     * @return IntervalCycle contenant les infos du cycle
     */
    public abstract IntervalCycle mesurerInter();

    /**
     * Valide si le nombre d'heure total respecte le minimum demande
     */
    public abstract void validerHTotal();
}
