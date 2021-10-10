public class Main {
    public static void main(String[] args) throws Exception {

        JSONHash deux = new JSONHash(args[0], args[1]);
        deux.chargement();
        deux.Categories();
        if (deux.complet)
            deux.verificationCycle();
        if (deux.complet)
            deux.validerDate();
        if (deux.complet)
            deux.verification17Heurescategories();
        if (deux.complet)
            deux.verification40Heures();

        deux.exportationErreur();
    }
}


