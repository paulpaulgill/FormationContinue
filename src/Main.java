public class Main {
    public static void main(String[] args) throws Exception {

        JSONHash deux = new JSONHash(args[0], args[1]);
        deux.chargement();
        deux.validerDate();
        deux.Categories();
        deux.verification17Heurescategories();
        deux.verification40Heures();
        deux.exportationErreur();
    }
}


