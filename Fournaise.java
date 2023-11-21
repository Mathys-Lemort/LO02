public class Fournaise extends Carte {
    public Fournaise() {
        super("rouge", 2, "Defaussez les 2 premières cartes de la Vie Future d'un rival.", false);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}