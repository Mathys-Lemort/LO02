package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Lendemain extends Carte {    
    public Lendemain() {
        super("Lendemain", "vert", 1, "Puisez une carte à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        // Piocher une carte
        Partie.getInstance().piocherSourceMain(joueur);

        // Demander si le joueur veut jouer une autre carte
        if (demanderJouerAutreCarte()) {
            // Si on est en mode graphique alors mettre le boolean rejouer à true pour qu'on puisse rejouer
            if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
                Partie.getInstance().setRejouer(true);
            }
            else{
            Partie.getInstance().rejouer(joueur);
            }
        } else {
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
            }
        }
    }
}
