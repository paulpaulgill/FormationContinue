public class main {
    public static void main(String args[]) throws Exception {

        import_export deux = new import_export(args[0], args[1]);
        deux.chargement();
        deux.validerDate();
        deux.Categories();
        deux.verification17Heurescategories();
        deux.verification40Heures();
        deux.exportation_erreur();
    }
}


