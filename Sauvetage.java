public class Sauvetage extends Carte {
    public Sauvetage() {
        super("vert", 2, "Ajoutez à votre Main une des3 dernières cartes de la Fosse.", false);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
