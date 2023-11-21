public class Lendemain extends Carte{    
    public Lendemain(String couleur, int points, String pouvoir, boolean estMosaique) {
        super(couleur, points, pouvoir, estMosaique);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
