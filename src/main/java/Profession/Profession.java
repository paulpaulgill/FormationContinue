package Profession;


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
}
