public class DernierSouffle extends Carte {
    public DernierSouffle() {
        super("rouge", 1, "Le joueur de votre choix défausse une carte de sa Main.", false);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
