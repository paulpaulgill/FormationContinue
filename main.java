public class main {
    public static void main(String args[]) throws Exception {

        JSONHash deux = new JSONHash("test.txt", "resultat.json");
        deux.chargement();
        deux.validerDate();
       // System.out.println(deux.verificationDate(0));
       // System.out.println(deux.verificationDate(1));
        deux.Categories();
        //deux.verificationCycle();
        //deux.categoriesReconnues();
        deux.verification40Heures();
        deux.exportationErreur();
    }
}


