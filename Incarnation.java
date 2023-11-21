public class Incarnation extends Carte{
    public Incarnation() {
        super("None", 1, "Choisissez une de vos Oeuvres. Copiez son pouvoir.", true);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
