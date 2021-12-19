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
                      @JsonProperty("nom") String nom,
                      @JsonProperty("prenom") String prenom,
                      @JsonProperty("sexe") int sexe,
                      @JsonProperty("ordre") String ordre,
                      @JsonProperty("activites") ArrayList<Activite> activites) {
        super(permis, cycle, heuresTrans, nom, prenom, sexe, ordre, activites);
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
     * @return
     */
    public abstract boolean validerPermis() throws FormationContinueException;

    public void validerPrenom() throws FormationContinueException{
        if (prenom.isEmpty())
        {
            throw new FormationContinueException("Prenom invalide");
        }
    }

    public void validerNom() throws FormationContinueException{
        if (nom.isEmpty())
        {
            throw new FormationContinueException("Nom invalide");
        }
    }

    public void validerSexe() throws FormationContinueException{
        if(sexe != 0 && sexe != 1 && sexe != 2){
            throw new FormationContinueException("Sexe invalide");
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
                throw new FormationContinueException("Description invalide");
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
    public boolean validerHeure() throws FormationContinueException {
        boolean valide = true;
        for (int i = 0; i < activites.size(); i++){
            if (activites.get(i).getHeures() <= 0){
                throw new FormationContinueException("Declaration Invalide: Heure d'une activité est negative ou 0.");
            }
        }
        return valide;
    }


    /**
     * Valide que le format de la date soit respectee et respect les annees bisectiles.
     * Si elle ne l'est pas, retourne faux
     * @return vérifie si la date est valide
     */
    private void verifierFormatDate(int i) throws FormationContinueException {
            String date = activites.get(i).getDate();
            try {
                LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            } catch (DateTimeParseException erreur) {
                throw new FormationContinueException("Une date n'est pas en format ISO8601.");
            }
    }

    /**
     * vierifie si la date d'une activite est dans l'interval valide.
     * @param i cpt
     * @param interCycle contient le cycle en String, la date min et la date max
     * @return
     */
    private boolean estEntreDate(int i, IntervalCycle interCycle) throws FormationContinueException {
        boolean valide = false;
        Date max = interCycle.getMax();
        Date min = interCycle.getMin();
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(activites.get(i).getDate());
            if (!(date.getTime() >= min.getTime() && date.getTime() <= max.getTime())){
                throw new FormationContinueException("La date de l'activité " +
                        activites.get(i).getDescription() +" " +
                        "n'est pas dans l'intervalle exigée.");
            }
        } catch (ParseException erreur) {
            throw new FormationContinueException(erreur.getMessage());
        }
        return valide;
    }

    /**
     * si le format ou la date n'est pas valide, un msg d'erreur est produit sur fichier de sortie
     */
    public void validerDate() throws FormationContinueException {
        for (int i = 0; i < activites.size(); i++) {
            verifierFormatDate(i);
            estEntreDate(i,mesurerInter());
        }
    }

    /**
     * Verifie si les activites sont dans une categorie valide ou qu'une categorie
     * n'est pas plus de 10 heures dans la meme journee. Un message d'erreur est produit
     * si cela n'est pas respecte.
     */
    public void validerCatActivites() throws FormationContinueException {
        for (int i = 0; i < activites.size(); i++){
            if (activites.get(i).getCategorieNum() == 0){
                throw new FormationContinueException("L'activité " + activites.get(i).getDescription() +
                        " est dans une catégorie non reconnue.");
            }else if (activites.get(i).getHeures() > 10){
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
        for (int y = 0; y < activites.size(); y++) {
            heures = 0;
            if (!activites.get(y).getIgnore()){
                for (int i = 0; i < activites.size(); i++) {
                    if (activites.get(i).equals(activites.get(y)) &&
                            !activites.get(i).getIgnore()) {
                        heures = heures + activites.get(i).getHeures();
                    }
                }
                if (heures > 10) {
                    for (int i = 0; i < activites.size(); i++) {
                        if (i == y) {
                            activites.get(y).setHeures(10);
                        } else if (activites.get(i).equals(activites.get(y)) &&
                                !activites.get(i).getIgnore()) {
                            activites.get(i).setIgnore(true);
                        }
                    }
                }
            }
        }
    }

    /**
     * verifie si le IntervalCycle est null. Si il l'est un msg d'erreur est
     * produit sur le fichier de sortie.
     */
    public void validerCycle() throws FormationContinueException {
        if (mesurerInter() == null){
            resultat.ajouterErreur("Cycle erroné.");
            resultat.setComplet(false);
            throw new FormationContinueException("Cycle invalide");
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
    public abstract void validerHTotal() throws FormationContinueException;
}
