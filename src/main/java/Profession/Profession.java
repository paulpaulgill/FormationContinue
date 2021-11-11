package Profession;


public abstract class Profession extends Declaration {
    protected Resultat resultat = new Resultat();

    @JsonCreator
    public Profession(@JsonProperty("numero_de_permis") String permis,
                      @JsonProperty("cycle") String cycle,
                      @JsonProperty("heures_transferees_du_cycle_precedent") int heuresTrans,
                      @JsonProperty("ordre") String ordre,
                      @JsonProperty("activites") ArrayList<Activite> activites) {
        super(permis, cycle, heuresTrans, ordre, activites);
    }

    @Override
    public Resultat getResultat() {
        return resultat;
    }

    public void validerPemis() throws FormationContinueException {
        Pattern p = Pattern.compile("\\b[ARSZ][0-9]{4}\\b");
        Matcher m = p.matcher(permis);
        if (!m.matches()){
            lancerErreurStrut();
        }
    }
    
    public void validerDescription() throws FormationContinueException {
        for (int i = 0; i < activites.size(); i++) {
            Pattern p = Pattern.compile(".{21,}");
            Matcher m = p.matcher(activites.get(i).getDescription());
            if (!m.matches()){
                lancerErreurStrut();
            }
        }
    }

    public void validerHeure() throws FormationContinueException {
        for (int i = 0; i < activites.size(); i++){
            if (activites.get(i).getHeures() == 0) {
                resultat.ajouterErreur("Le nombre d'heure minimum est de 1. L'activite "
                        + activites.get(i).getDescription() + " sera ignoree");
                activites.get(i).setIgnore(true);
            }if (activites.get(i).getHeures() < 0) {
                lancerErreurStrut();
            }
        }
    }

     public void lancerErreurStrut() throws FormationContinueException {
        resultat.ecraserErreur("Le fichier d'entrée est invalide.");
        resultat.setComplet(false);
        throw new FormationContinueException("La structure du fichier d'entrée n'est pas respecté");
    }

    private boolean verifierFormatDate(int i) {
        boolean valide = false;
        String date = activites.get(i).getDate();
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("uuuu-M-d").withResolverStyle(ResolverStyle.STRICT));
            valide = true;
        } catch (IllegalArgumentException erreur) {
            System.out.println("Une erreur est survenue lors du traitement de la date.");
            System.exit(-1);
        } catch (DateTimeParseException erreur) {
            valide = false;
        }
        return valide;
    }
}
