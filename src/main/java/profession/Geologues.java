package profession;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import util.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Geologues extends Profession{
    @JsonIgnore
    protected int heuresTrans;

    private final Date DATE_MAX = new GregorianCalendar(2021, Calendar.JUNE, 1).getTime();
    private final Date DATE_MIN = new GregorianCalendar(2018, Calendar.JUNE, 1).getTime();

    private int totalHMin = 55;


    @JsonCreator
    public Geologues(@JsonProperty("numero_de_permis") String permis,
                     @JsonProperty("cycle") String cycle,
                     @JsonProperty("ordre") String ordre,
                     @JsonProperty("activites") ArrayList<Activite> activites) {
        this.permis = permis;
        this.cycle = cycle;
        this.ordre = ordre;
        this.activites = activites;
    }

    public Geologues(){}

    /**
     * Valide que le nombre d'heures dans chaque catégorie soit respecté.
     * Sinon un message d'erreur est produit dans le fichier de sortie et
     * le cycle devient incomplet.
     */
    public void validerHMin(){
        if (calculerHCat(1) < 22){
            ecrireErreurHMin(22,calculerHCat(1),"cours");
        }
        if (calculerHCat(8) < 1){
            ecrireErreurHMin(1,calculerHCat(8),"groupe de discussion");
        }
        if (calculerHCat(9) < 3){
            ecrireErreurHMin(3,calculerHCat(9),"projet de recherche");
        }
    }

    /**
     * Ecrit un message d'erreur dans le fichier de sortie et le cycle
     * n'est pas complet.
     *
     * @param heuresMin le nombre d'heure min a respecter
     * @param heures le nombre d'heure fait lors du cycle
     * @param categorie la categorie non respectee
     */
    public void ecrireErreurHMin(int heuresMin, int heures, String categorie ){
        resultat.ajouterErreur("Il manque " + (heuresMin - heures) +
                " pour atteindre le min de "+ heuresMin +"h pour la catégorie :" + categorie );
        resultat.setComplet(false);
    }

    /**
     * Verifie le cycle et retourne le nombre d'heure total, les bonne dates et le String du cycle
     * qui correspondent au cycle.
     * @return IntervalCycle qui contient le cycle, les date min et max  et le nb d'heure minimum
     */
    @Override
    public IntervalCycle mesurerInter() {
        IntervalCycle inter;
        if (cycle.equals("2018-2021")){
            inter = new IntervalCycle("2018-2021",DATE_MAX,DATE_MIN);
        }else {
            inter = null;
        }
        return inter;
    }

    /**
     * Valide si le nombre d'heures total a ete respecte du cycle
     * sinon un message est ecrit sur le fichier de sortie et
     * le cycle n'est pas complet
     */
    @Override
    public void validerHTotal() {
        int heures = 0;
        for (int i = 1; i <= 10; i++){
            heures = heures + calculerHCat(i);
        }
        if (heures < totalHMin){
            resultat.ajouterErreur("Il manque " + (totalHMin - heures) +
                    " heures" + " de formation pour compléter le cycle.");
            resultat.setComplet(false);
        }
        validerHMin();
    }

    /**
     * Valide si le permis respect le format
     */
    public boolean validerPermis(){
        boolean valide = true;
        Pattern p = Pattern.compile("^[A-B]{2}[0-9]{4}$");
        Matcher m = p.matcher(permis);
        if (!(m.matches() && valideNomPrenomPermis())){
            valide = false;
        }
        return valide;
    }

    public boolean valideNomPrenomPermis(){
        return nom.charAt(0) == permis.charAt(0) && prenom.charAt(0) == permis.charAt(1);
    }
}
