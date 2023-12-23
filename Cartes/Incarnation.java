public class Incarnation extends Carte{
    public Incarnation() {
        super("Incarnation","None", 1, "Choisissez une de vos Oeuvres. Copiez son pouvoir.", true);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
