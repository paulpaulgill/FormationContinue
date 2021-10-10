public class Main {
    public static void main(String[] args) throws Exception {

        GestionJSON deux = new GestionJSON(args[0], args[1]);
        deux.chargement();
        deux.Categories();
        deux.verificationCycle();
        deux.validerDate();
        deux.verification17Heurescategories();
        deux.verification40Heures();
        deux.exportationErreur();
    }
}


