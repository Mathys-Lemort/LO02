package Cartes;
import Cartes.Carte;
import Joueurs.Joueur;

public class DernierSouffle extends Carte {
    public DernierSouffle() {
        super("Dernier Souffle","rouge", 1, "Le joueur de votre choix défausse une carte de sa Main.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        adversaire.defausserCarte(1);
    }
}
