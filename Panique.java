public class Panique extends Carte{
    public Panique() {
        super("rouge", 1, "Défaussez la première carte de la Pile d'un joueur. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
