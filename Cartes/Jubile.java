package Cartes;
import Cartes.Carte;
import Joueurs.Joueur;

public class Jubile extends Carte {
    public Jubile() {
        super("Jubile","vert", 3, "Placez jusqu’à 2 cartes de votre Main sur vos Oeuvres.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
