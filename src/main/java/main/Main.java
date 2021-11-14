package main;

import exception.FormationContinueException;
import profession.Profession;
import util.GestionJSON;

public class Main {
    public static void main(String[] args) throws FormationContinueException {
        GestionJSON fichier = new GestionJSON(args[0], args[1]);
        Profession declaration;
        try {
            declaration = (Profession) fichier.chargement();
            declaration.validerCycle();
            if (declaration.getResultat().isComplet()) {
                declaration.validerPermis();
                declaration.validerDescription();
                declaration.validerCatAtivites();
                declaration.validerHeure();
                declaration.validerDate();
                declaration.validerHTotal();
            }
            fichier.exporterErreur(declaration);
        }catch (FormationContinueException e){
            System.err.println(e);
            System.exit(-1);
        }
    }
}


