public class Main {
    public static void main(String[] args) throws Exception {

        JSONHash declaration = new JSONHash(args[0], args[1]);
        declaration.chargement();
        declaration.Categories();
        if (declaration.getComplet())
            declaration.verifierTypeOrdre();
        if (declaration.getComplet())
            declaration.verifierCycle();
        if (declaration.getComplet())
            declaration.validerDate();
        if (declaration.getComplet())
            declaration.verifierHeuresMinCategories();
        if (declaration.getComplet())
            declaration.verifierHeuresMin();
        if(declaration.getComplet())
            declaration.verifierTailleDescription();

        declaration.exporterErreur();
    }
}


