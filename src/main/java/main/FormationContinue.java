package main;

import exception.FormationContinueException;
import profession.Architectes;
import profession.Profession;
import util.GestionJSON;

public class FormationContinue {
    public static void main(String[] args) throws FormationContinueException {
        GestionJSON fichier = new GestionJSON(args[0], args[1]);
        Profession declaration = new Architectes();
        try {
            declaration = (Profession) fichier.chargement();
            declaration.validerCycle();
            if (declaration.getResultat().isComplet()) {
                declaration.validerPermis();
                declaration.validerPrenom();
                declaration.validerNom();
                declaration.validerDescription();
                declaration.validerCatActivites();
                declaration.validerHeure();
                declaration.validerDate();
                declaration.validerCatJour(); // emplacement tres important ne pas deplacer
                declaration.validerHTotal();
            }
            fichier.exporterErreur(declaration);
        }catch (FormationContinueException e){
            System.err.println(e);
            declaration.getResultat().ecraserErreur("Le fichier d'entrée est invalide.");
            declaration.getResultat().setComplet(false);
            fichier.exporterErreur(declaration);
            System.exit(-1);
        }
    }
}


