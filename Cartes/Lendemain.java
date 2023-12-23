package Cartes;
import Cartes.Carte;
import Joueurs.Joueur;

public class Lendemain extends Carte{    
    public Lendemain() {
        super("Lendemain","vert", 1, "Puisez une carte à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
