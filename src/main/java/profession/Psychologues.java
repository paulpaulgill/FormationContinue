package profession;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import util.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Psychologues extends Profession{
    private final Date DATE_MAX = new GregorianCalendar(2023, Calendar.JANUARY, 1).getTime();
    private final Date DATE_MIN = new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime();

    private int totalHMin = 90;

    @JsonCreator
    public Psychologues(@JsonProperty("numero_de_permis") String permis,
                        @JsonProperty("cycle") String cycle,
                        @JsonProperty("heures_transferees_du_cycle_precedent") int heuresTrans,
                        @JsonProperty("ordre") String ordre,
                        @JsonProperty("activites") ArrayList<Activite> activites) {
        super(permis, cycle, heuresTrans, ordre, activites);
    }

    /**
     * Valide si le nombre d'heure minimum pour la categorie cours est respecte
     * Si ce ne l'est pas, un message d'erreur est produit dans le fichier de sortie
     * et complet devient faux
     */
    public void validerHMin(){
        if (calculerHCat(1) < 25){
            resultat.ajouterErreur("Il manque " + (25 - calculerHCat(1)) +
                    " pour atteindre le min de 25h pour la catégorie : cours" );
            resultat.setComplet(false);
        }
    }

    /**
     * Verifie le cycle et retourne le nombre d'heure total, les bonne dates et le String du cycle
     * qui correspondent au cycle.
     * @return IntervalCycle qui contient le cycle, les date min et max  et le nb d'heure minimum
     */
    @Override
    public IntervalCycle mesurerInter() {
        IntervalCycle inter;
        if (cycle.equals("2018-2023")){
            inter = new IntervalCycle("2018-2023",DATE_MAX,DATE_MIN);
        }else {
            inter = null;
        }
        return inter;
    }

    /**
     * Valide si le nombre d'heure minimum est respecte. Si ce ne l'est pas
     * un message d'erreur est produit et complet devient faux.
     */
    @Override
    public void validerHTotal() {
        int heures = 0;
        for (int i = 1; i <= 10; i++){
            if ((i == 5) && (calculerHCat(i) >= 15)){
                heures = heures + 15;
            }else {heures = heures + calculerHCat(i);}
        }
        if (heures < totalHMin){
            resultat.ajouterErreur("Il manque " + (totalHMin - heures) +
                    " heures" + " de formation pour compléter le cycle.");
            resultat.setComplet(false);
        }
        validerHMin();
    }
}
