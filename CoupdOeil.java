public class CoupdOeil extends Carte {
    public CoupdOeil() {
        super("bleu", 1, "Regardez la Main d’un rival.\n" + //
                "Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
