package Cartes;

import Core.Affichage;
import Joueurs.Joueur;

import java.util.ArrayList;

/**
 * La classe Recyclage représente une carte de recyclage dans le jeu.
 * Elle hérite de la classe Carte.
 */
public class Recyclage extends Carte {
    /**
     * Constructeur de la classe Recyclage.
     * Initialise les attributs de la carte de recyclage.
     */
    public Recyclage() {
        super("Recyclage", "vert", 1, "Ajoutez à votre Vie Future une des 3 dernières cartes de la Fosse.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte de recyclage.
     * Ajoute une carte de la fosse du joueur à sa Vie Future.
     * @param joueur le joueur qui utilise la carte de recyclage
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesFosse = joueur.getCartesFosse(3);
        if (cartesFosse.isEmpty()) {
            Affichage.afficherMessage("Il n'y a pas de cartes dans votre fosse pour ajouter à votre Vie Future.");
            return;
        }

        int choix = obtenirChoixCarte(cartesFosse);
        if (choix >= 0) {
            Carte carte = cartesFosse.get(choix);
            joueur.ajouterCarteDansVieFuture(carte);
            joueur.getFosse().remove(carte);
            Affichage.afficherMessage("Vous avez ajouté la carte " + carte.getNom() + " à votre Vie Future.");
        }
    }
}
