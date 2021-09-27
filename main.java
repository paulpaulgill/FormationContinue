

public class main {
    public static void main(String args[]) throws Exception {

        import_export deux = new import_export("declaration.json", "resultat.json");
        deux.chargement();
        System.out.println(deux.verificationDate(0));
        //deux.recherche_erreur();
        deux.to_string();
        deux.exportation_erreur();
    }
}


