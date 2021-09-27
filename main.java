
public class main {
    public static void main(String args[]) throws Exception {

        import_export deux = new import_export("declaration.json", "resultat.json");
        deux.chargement();

        System.out.println(deux.verificationDate(1));

        //deux.recherche_erreur();
        deux.to_String();


        deux.Categories();
        deux.VerificationCycle();
        deux.categoriesReconnues();
        deux.verification40Heures();


        deux.exportation_erreur();
    }
}


