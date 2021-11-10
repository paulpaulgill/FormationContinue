package Util;

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
                    @JsonProperty("heures") int houres,
                    @JsonProperty("date") String date) {
        this.description = description;
        this.categorie = category;
        this.heures = houres;
        this.date = date;
    }
}
