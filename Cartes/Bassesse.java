package Cartes;
import Cartes.Carte;
import Joueurs.Joueur;

public class Bassesse extends Carte {
    public Bassesse() {
        super("Bassesse","rouge", 3, "Défaussez au hasard 2 cartes\n" + //
                "de la Main d’un rival.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        adversaire.defausserCarte(2);
    }

}
