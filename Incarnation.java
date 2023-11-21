public class Incarnation extends Carte{
    public Incarnation(String couleur, int points, String pouvoir, boolean estMosaique) {
        super(couleur, points, pouvoir, estMosaique);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
