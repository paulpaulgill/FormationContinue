
public class main {
    public static void main(String args[]) throws Exception {

        import_export deux = new import_export("/Users/antoinegimalac/IdeaProjects/projet/src/person.json", "/Users/antoinegimalac/IdeaProjects/projet/src/resultat.json");
        deux.chargement();
        deux.to_string();
        
        deux.Categories();
        deux.VerificationCycle();
        deux.CategoriesReconnues();
        deux.Verification40Heures();

        deux.exportation_erreur();
    }
}


