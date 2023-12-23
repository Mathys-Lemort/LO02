package Cartes;
import Joueurs.Joueur;

public class Panique extends Carte{
    public Panique() {
        super("Panique","rouge", 1, "Défaussez la première carte de la Pile d'un joueur. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
