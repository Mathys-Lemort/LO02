package Cartes;
import Joueurs.Joueur;

public class Semis extends Carte {
    public Semis() {
        super("Semis","vert", 2, "Puisez 2 cartes à la Source, puis placez sur votre Vie Future 2 cartes de votre Main.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
