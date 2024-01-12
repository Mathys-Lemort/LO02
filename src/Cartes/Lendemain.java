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
        Partie.getInstance().piocherSourceMain(joueur);

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
