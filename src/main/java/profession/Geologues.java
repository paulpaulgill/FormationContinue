package profession;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import util.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public void validerHMin(){
        if (calculerHCat(1) < 22){
            ecrireErruerHMin(22,calculerHCat(1),"cours");
        }else if (calculerHCat(8) < 1){
            ecrireErruerHMin(1,calculerHCat(8),"groupe de discussion");
        }else if (calculerHCat(9) < 3){
            ecrireErruerHMin(3,calculerHCat(9),"projet de recherche");
        }
    }

    public void ecrireErruerHMin(int heuresMin, int heures, String categorie ){
        resultat.ajouterErreur("Il manque " + (heuresMin - heures) +
                " pour atteindre le min de "+ heuresMin +"h pour la catégorie :" + categorie );
        resultat.setComplet(false);
    }

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
}
