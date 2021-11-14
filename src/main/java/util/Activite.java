package util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Activite {
    private String description;
    private String categorie;
    private int heures;
    private String date;
    private boolean ignore = false;

    private static final String[] validCategoryTable = {
            "cours",//1
            "atelier",//2
            "séminaire",//3
            "colloque",//4
            "conférence",//5
            "lecture dirigée",//6
            "présentation",//7
            "groupe de discussion",//8
            "projet de recherche",//9
            "rédaction professionnelle"//10
    };

    public Activite() {
    }
    @JsonCreator
    public Activite(@JsonProperty("description") String description,
                    @JsonProperty("categorie") String category,
                    @JsonProperty("heures") int heures,
                    @JsonProperty("date") String date) {
        this.description = description;
        this.categorie = category;
        this.heures = heures;
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategorie() {
        return categorie;
    }

    public int getHeures() {
        return heures;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public void setHeures(int heures) {
        this.heures = heures;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getIgnore() {
        return ignore;
    }

    public void setIgnore(boolean ignore) {
        this.ignore = ignore;
    }

    public int getCategorieNum(){
        int a = 0;
        for (int i = 0; i < validCategoryTable.length ; i++){
            if(validCategoryTable[i].equals(categorie)){
                a = i + 1;
            }
        }
        return a;
    }
}
