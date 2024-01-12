package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * La classe Semis représente une carte de type "Semis" dans le jeu.
 * Elle hérite de la classe Carte.
 */
public class Semis extends Carte {
    /**
     * Constructeur de la classe Semis.
     * Initialise les attributs de la carte "Semis".
     */
    public Semis() {
        super("Semis", "vert", 2, "Puisez 2 cartes à la Source, puis placez sur votre Vie Future 2 cartes de votre Main.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte "Semis".
     * Elle permet au joueur de piocher 2 cartes à la Source,
     * puis de placer 2 cartes de sa Main dans sa Vie Future.
     * 
     * @param joueur le joueur qui joue la carte "Semis"
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        for (int i = 0; i < 2; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }
        Affichage.afficherMessage("Vous avez pioché 2 cartes à la Source.");

        for (int i = 0; i < 2; i++) {
            if (joueur.getMain().isEmpty()) {
                Affichage.afficherMessage("Vous n'avez plus de cartes en main pour placer dans votre Vie Future.");
                break;
            }

            int choix = obtenirChoixCarte(joueur.getMain());
            if (choix == -1) {
                continue;
            }

            Carte carte = joueur.getMain().get(choix);
            joueur.ajouterCarteDansVieFuture(carte);
            joueur.getMain().remove(carte);
            
            Affichage.afficherMessage("Vous avez placé la carte " + carte.getNom() + " dans votre Vie Future.");
        }
    }
}
