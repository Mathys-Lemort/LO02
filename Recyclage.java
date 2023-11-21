public class Recyclage extends Carte {
    public Recyclage() {
        super("vert", 1, "Ajoutez à votre Vie Future une des 3 dernières cartes de la Fosse.", false);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}