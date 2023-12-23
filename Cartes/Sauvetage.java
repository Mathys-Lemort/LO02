package Cartes;
import Joueurs.Joueur;

public class Sauvetage extends Carte {
    public Sauvetage() {
        super("Sauvetage","vert", 2, "Ajoutez à votre Main une des3 dernières cartes de la Fosse.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
