public class Vol extends Carte {
    public Vol() {
        super("bleu", 3, "Placez dans votre Main l Oeuvre Exposée d'un rival.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
