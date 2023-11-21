public class Mimetisme extends Carte{
    public Mimetisme(String couleur, int points, String pouvoir, boolean estMosaique) {
        super(couleur, points, pouvoir, estMosaique);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
