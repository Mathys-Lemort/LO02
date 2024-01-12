package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Panique extends Carte {
    public Panique() {
        super("Panique", "rouge", 1, "Défaussez la première carte de la Pile d'un joueur. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getPile().isEmpty()) {
            
            Affichage.afficherMessage("La Pile de " + adversaire.getPseudo() + " est vide, aucune action possible.");
            
            return;
        }

        adversaire.defausserCartePile();

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
