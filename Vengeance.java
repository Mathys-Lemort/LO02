public class Vengeance extends Carte{    
    public Vengeance() {
        super("rouge", 3, "Defaussez l’Oeuvre Exposée d’un rival.", false);
    }
    
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
