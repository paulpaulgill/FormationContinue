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

    /**
     * Compare la valeurs de type string d'une activité selon une array
     * qui contient toute les categorie acepté. Ce qui a pour effet de valider
     * la catégorie en retournant 0 si elle fait pas partie de l'array.
     * @return la valeur numérique de la categorie (0 si categorie est invalide)
     */
    public int getCategorieNum(){
        int a = 0;
        for (int i = 0; i < validCategoryTable.length ; i++){
            if(validCategoryTable[i].equals(categorie)){
                a = i + 1;
            }
        }
        return a;
    }

    public String getCatNumToString(int i){
        return validCategoryTable[i];
    }

    /**
     * permet de savoir si 2 activiter on la meme date et la meme catégorie sans
     * prendre en compte les autres variable.
     * @param object Objet a evaluer
     * @return le resultat
     */
    @Override
    public boolean equals(Object object){
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        }else {
            Activite act = (Activite) object;
            if (this.date.equals(act.getDate())) {
                result = true;
            }
        }
        return result;
    }
}
