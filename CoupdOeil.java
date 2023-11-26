public class CoupdOeil extends Carte {
    public CoupdOeil() {
        super("Coup Doeil","bleu", 1, "Regardez la Main d’un rival.\n" + //
                "Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
