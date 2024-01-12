package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * Cette classe représente une carte de type "Voyage" dans le jeu.
 * Elle hérite de la classe Carte.
 */
public class Voyage extends Carte {
    /**
     * Constructeur de la classe Voyage.
     * Initialise les attributs de la carte avec les valeurs spécifiques au type "Voyage".
     */
    public Voyage() {
        super("Voyage", "vert", 3, "Puisez 3 cartes à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    /**
     * Méthode qui effectue l'action associée à la carte "Voyage".
     * Elle permet au joueur de piocher 3 cartes à la Source.
     * Ensuite, le joueur a la possibilité de jouer une autre carte.
     * Si le joueur choisit de ne pas jouer de carte, un message est affiché.
     * 
     * @param joueur Le joueur qui joue la carte "Voyage".
     * @param adversaire Le joueur adverse.
     */
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
            Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

            
        }
    }
}
