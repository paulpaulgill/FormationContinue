package profession;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import util.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Architectes extends Profession{
    private final Date DATE_MAX1 = new GregorianCalendar(2022, Calendar.APRIL, 1).getTime();
    private final Date DATE_MIN1 = new GregorianCalendar(2020, Calendar.APRIL, 1).getTime();
    private final Date DATE_MAX2 = new GregorianCalendar(2020, Calendar.APRIL, 1).getTime();
    private final Date DATE_MIN2 = new GregorianCalendar(2018, Calendar.APRIL, 1).getTime();
    private final Date DATE_MAX3 = new GregorianCalendar(2018, Calendar.JULY, 1).getTime();
    private final Date DATE_MIN3 = new GregorianCalendar(2016, Calendar.APRIL, 1).getTime();

    private int totalHMin = 42;
    @JsonCreator
    public Architectes(@JsonProperty("numero_de_permis") String permis,
                       @JsonProperty("cycle") String cycle,
                       @JsonProperty("heures_transferees_du_cycle_precedent") int heuresTrans,
                       @JsonProperty("ordre") String ordre,
                       @JsonProperty("activites") ArrayList<Activite> activites) {
        super(permis, cycle, heuresTrans, ordre, activites);
    }

    public int calculerHTCat(){
        int heures = 0;
        for (int i = 1; i <= 10; i++){
            if ((i == 7 || i == 9) && (calculerHCat(i) >= 23)){
                heures = heures + 23;
            } else if ((i == 8 || i == 10) && (calculerHCat(i) >= 17)){
                heures = heures + 17;
            }else {heures = heures + calculerHCat(i);}
        }
        return heures;
    }
    @Override
    public void validerHTotal(){
        int heures = 0;
        heures = calculerHTCat() + verifierHeureTrf();
        if (heures < totalHMin){
            resultat.ajouterErreur("Il manque " + (totalHMin - heures) +
                    " heures" + " de formation pour compléter le cycle.");
            resultat.setComplet(false);
        }
        validerHMin();
    }

    private int verifierHeureTrf(){
        if (heuresTrans < 0){
            heuresTrans = 0;
            resultat.ajouterErreur("Le nombre d'heures transférées ne peut être négatif. " +
                    "0 heures seront considérées." );
        }else if(heuresTrans > 7){
            heuresTrans = 7;
            resultat.ajouterErreur("Le nombre d'heures transférées ne peut être supérieur à "+
                    "7 heures. Seulement 7 heures seront considérées." );
        }
        return heuresTrans;
    }

    public void validerHMin(){
        int heures = 0;
        for (int i = 0; i < activites.size(); i++){
            int y = activites.get(i).getCategorieNum();
            if (y >= 1 && y <= 6){
                heures = heures + activites.get(i).getHeures();
            }
        }
        if (heures < 17){
            resultat.ajouterErreur("Il manque " + (17 - heures) +
                    " pour atteindre le min de 17h pour la liste des 6 catégories." );
            resultat.setComplet(false);
        }
    }


    @Override
    public IntervalCycle mesurerInter() {
        IntervalCycle inter;
        if (cycle.equals("2020-2022")){
            inter = new IntervalCycle("2020-2022",DATE_MAX1,DATE_MIN1);
            totalHMin = 40;
        }else if (cycle.equals("2018-2020")){
            inter = new IntervalCycle("2018-2020",DATE_MAX2,DATE_MIN2);
            totalHMin = 42;
        }else if (cycle.equals("2016-2018")){
            inter = new IntervalCycle("2016-2018",DATE_MAX3,DATE_MIN3);
            totalHMin = 42;
        }else {
            inter = null;
        }
        return inter;
    }
}
