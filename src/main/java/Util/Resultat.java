package util;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Resultat {
    @JsonProperty("complet1")
    private boolean complet = true;
    @JsonProperty("erreur")
    private ArrayList<String> erreur = new ArrayList<>();

    public boolean isComplet() {
        return complet;
    }

    public void setComplet(boolean complet) {
        this.complet = complet;
    }

    public void ajouterErreur(String e){
        erreur.add(e);
    }

    public void ecraserErreur(String e){
        erreur = new ArrayList<>();
        erreur.add(e);
    }
}
