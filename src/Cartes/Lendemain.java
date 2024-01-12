package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * Cette classe représente la carte "Lendemain" dans le jeu.
 * Elle hérite de la classe Carte.
 */
public class Lendemain extends Carte {    
    /**
     * Constructeur de la classe Lendemain.
     * Initialise les attributs de la carte.
     */
    public Lendemain() {
        super("Lendemain", "vert", 1, "Puisez une carte à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte Lendemain.
     * Elle permet au joueur de piocher une carte à la Source et de jouer une autre carte.
     * @param joueur le joueur qui joue la carte Lendemain
     * @param adversaire le joueur adverse
     */
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
            Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

            
        }

    }
}
