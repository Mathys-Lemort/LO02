public class Voyage extends Carte{
    public Voyage() {
        super("vert", 3, "Puisez 3 cartes à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
