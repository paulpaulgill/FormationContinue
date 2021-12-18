package util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Declaration {

    @JsonProperty("numero_de_permis")
    protected String permis;
    protected String cycle;
    @JsonProperty("heures_transferees_du_cycle_precedent")
    protected int heuresTrans;
    protected String nom;
    protected String prenom;
    @JsonProperty("sexe")
    protected int sexe;
    protected String ordre;
    protected ArrayList<Activite> activites = new ArrayList<>();

    @JsonCreator
    public Declaration(@JsonProperty("numero_de_permis") String permis,
                       @JsonProperty("cycle") String cycle,
                       @JsonProperty("heures_transferees_du_cycle_precedent") int heuresTrans,
                       @JsonProperty("nom") String nom,
                       @JsonProperty("prenom") String prenom,
                       @JsonProperty("sexe") int sexe,
                       @JsonProperty("ordre") String ordre,
                       @JsonProperty("activites") ArrayList<Activite> activites) {
        this.permis = permis;
        this.cycle = cycle;
        this.heuresTrans = heuresTrans;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
        this.ordre = ordre;
        this.activites = activites;
    }

    public Declaration() {}

    public String getPermis() {
        return permis;
    }

    public void setPermis(String permis) {
        this.permis = permis;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public int getHeuresTrans() {
        return heuresTrans;
    }

    public void setHeuresTrans(int heuresTrans) {
        this.heuresTrans = heuresTrans;
    }

    public String getNom(){ return nom; }

    public void setNom(String nom){ this.nom = nom; }

    public String getPrenom(){ return prenom; }

    public void setPrenom(String prenom){ this.prenom = prenom; }

    public int getSexe(){ return sexe; }

    public void setSexe(int sexe){ this.sexe = sexe; }

    public String getOrdre() {
        return ordre;
    }

    public void setOrdre(String ordre) {
        this.ordre = ordre;
    }

    public ArrayList<Activite> getActivites() {
        return activites;
    }

    public void setActivites(ArrayList<Activite> activites) {
        this.activites = activites;
    }

    public Resultat getResultat() {
        return null;
    }


}
