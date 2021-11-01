public class Main {
    public static void main(String[] args) throws Exception {

        JSONHash declaration = new JSONHash(args[0], args[1]);
        declaration.chargement();
        declaration.Categories();
        if (declaration.getComplet())
            declaration.verificationCycle();
        if (declaration.getComplet())
            declaration.validerDate();
        if (declaration.getComplet())
            declaration.verification17HeuresCategories();
        if (declaration.getComplet())
            declaration.verification40Heures();

        declaration.exportationErreur();
    }
}


