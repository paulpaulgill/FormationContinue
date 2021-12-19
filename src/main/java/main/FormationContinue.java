package main;

import exception.FormationContinueException;
import org.apache.commons.cli.*;
import profession.Architectes;
import profession.Profession;
import util.GestionJSON;
import util.Statistiques;

public class FormationContinue {
    public static void main(String[] args) {
        int argX = 0;
        Options options = new Options();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            options.addOption("S", false,"Affiche les statistiques");
            options.addOption("SR", false,"Réinitialise les statistique");
            cmd = parser.parse(options,args);
        }catch (ParseException e){
            System.err.println("Erreur lors de la lecture des options de la commande");
            System.exit(-1);
        }
        if(cmd.hasOption("S") && cmd.hasOption("SR")){argX = argX + 2;}
        else if(cmd.hasOption("S") || cmd.hasOption("SR")){argX = argX + 1;}
        GestionJSON fichier = new GestionJSON(args[argX], args[argX + 1]);
        evaluerDeclaration( fichier, cmd);
    }


    /**
     * Évalue et donner les résultats de l'évaluation de la déclration.
     * @param fichier l'instance de gestion de fichier JSON
     * @param cmd   pour evaluer la presence d'option de ligne de commande
     */
    public static void evaluerDeclaration(GestionJSON fichier , CommandLine cmd){
        Profession declaration = new Architectes();
        Statistiques stat = new Statistiques();
        try {
            stat = stat.chargerStat("stat.json",cmd.hasOption("SR"));
            declaration = (Profession) fichier.chargement();
            testerPara(declaration);
            fichier.exporterErreur(declaration);
            stat.genererStat(declaration);
            stat.exporterStat();
            if(cmd.hasOption("S")) System.out.println(stat.toString());
        }catch (FormationContinueException e){
            System.err.println(e);
            declaration.getResultat().ecraserErreur("Le fichier d'entrée est invalide.");
            declaration.getResultat().setComplet(false);
            fichier.exporterErreur(declaration);
            stat.genererStatInvalid(declaration);
            stat.exporterStat();
            if(cmd.hasOption("S")) System.out.println(stat.toString());
            System.exit(-1);
        }
    }

    /**
     * Valide la completion du cycle de la déclaration et la validité du format
     * @param declaration instance de déclaration a évaluer
     * @throws FormationContinueException lancer si declaration est invalide
     */
    public static void testerPara(Profession declaration) throws FormationContinueException{
        declaration.validerCycle();
        declaration.validerPermis();
        declaration.validerPrenom();
        declaration.validerNom();
        declaration.validerSexe();
        declaration.validerDescription();
        declaration.validerCatActivites();
        declaration.validerHeure();
        declaration.validerDate();
        declaration.validerCatJour(); // emplacement tres important ne pas deplacer
        declaration.validerHTotal();
    }
}


