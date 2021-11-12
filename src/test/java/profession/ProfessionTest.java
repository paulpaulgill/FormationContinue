package profession;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.Activite;
import util.Declaration;
import util.IntervalCycle;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ProfessionTest extends Declaration {

    //Déclaration avant
    int positif;
    public ProfessionTest(String permis, String cycle, int heuresTrans, String ordre, ArrayList<Activite> activites) {
        super(permis, cycle, heuresTrans, ordre, activites);
    }

    @BeforeEach
    void setUp(){
        //attribution de valeur
        positif = 8;
    }


    @Test
    @DisplayName("")
    void validerPemis() {
    }

    @Test
    void validerDescription() {
    }

    @Test
    void validerHeure() {
    }

    @Test
    void lancerErreurStrut() {
    }

    @Test
    void validerDate() {
    }

    @Test
    void validerCatAtivites() {
    }

    @Test
    void calculerHCat() {
    }

    @Test
    void validerCycle() {
    }

    @Test
    void mesurerInter() {
    }

    @Test
    void validerHTotal() {
    }
}