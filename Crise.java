public class Crise extends Carte {
    public Crise() {
        super("rouge", 2, "Le rival de votre choix défausse\n" + //
                "une de ses Oeuvres.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
