package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * La classe Panique représente une carte du jeu.
 * Elle permet de défausser la première carte de la Pile d'un joueur
 * et de jouer une autre carte si désiré.
 */
public class Panique extends Carte {
    /**
     * Constructeur de la classe Panique.
     * Initialise les attributs de la carte.
     */
    public Panique() {
        super("Panique", "rouge", 1, "Défaussez la première carte de la Pile d'un joueur. Vous pouvez ensuite jouer une autre carte.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte Panique.
     * Défausse la première carte de la Pile d'un joueur.
     * Permet de jouer une autre carte si désiré.
     * 
     * @param joueur le joueur qui joue la carte
     * @param adversaire le joueur adverse
     */
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
            } else {
                Partie.getInstance().rejouer(joueur);
            }
        } else {
            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
        }
    }
}
