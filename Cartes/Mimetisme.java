public class Mimetisme extends Carte{
    public Mimetisme() {
        super("Mimetisme","None", 1, "Choisissez un Rival.Copiez le pouvoir de sonOeuvre Exposée.", true);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
