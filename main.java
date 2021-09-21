public class main {
    public static void main(String args[]) throws Exception {

        import_export deux = new import_export("/Users/antoinegimalac/IdeaProjects/projet/src/person.json", "/Users/antoinegimalac/IdeaProjects/projet/src/resultat.json");
        deux.chargement();
        deux.to_string();
        deux.recherche_erreur();
        deux.exportation_erreur();
    }
}
