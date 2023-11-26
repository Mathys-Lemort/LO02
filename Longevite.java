public class Longevite extends Carte {  // Longevite.java
    public Longevite() {
        super("vert", 2, "Placez 2 cartes puisées à la Source sur la Pile d'un joueur.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
