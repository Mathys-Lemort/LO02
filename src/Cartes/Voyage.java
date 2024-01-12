package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Voyage extends Carte {
    public Voyage() {
        super("Voyage", "vert", 3, "Puisez 3 cartes à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        for (int i = 0; i < 3; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }

        if (demanderJouerAutreCarte()) {
            if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
                Partie.getInstance().setRejouer(true);
            }
            else{
            Partie.getInstance().rejouer(joueur);
            }
        } else {
            
            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
            
        }
    }
}
