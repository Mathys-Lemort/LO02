package Cartes;

import Core.Affichage;
import Joueurs.Joueur;

import java.util.ArrayList;

/**
 * Cette classe représente une carte de type "Sauvetage".
 * Elle hérite de la classe Carte.
 * 
 * La carte "Sauvetage" permet d'ajouter à la main du joueur l'une des 3 dernières cartes de la fosse.
 * Si la fosse est vide, aucun ajout n'est effectué.
 * 
 * Lorsque la carte est jouée, le joueur choisit une carte parmi les 3 dernières cartes de sa fosse
 * et l'ajoute à sa main. La carte choisie est ensuite retirée de la fosse.
 */
public class Sauvetage extends Carte {
    /**
     * Constructeur de la classe Sauvetage.
     * Initialise les attributs de la carte "Sauvetage".
     */
    public Sauvetage() {
        super("Sauvetage", "vert", 2, "Ajoutez à votre Main une des 3 dernières cartes de la Fosse.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte "Sauvetage".
     * Ajoute à la main du joueur l'une des 3 dernières cartes de sa fosse.
     * Si la fosse est vide, affiche un message indiquant qu'il n'y a pas de cartes à ajouter.
     * 
     * @param joueur le joueur qui joue la carte "Sauvetage"
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesFosse = joueur.getCartesFosse(3);
        if (cartesFosse.isEmpty()) {
            Affichage.afficherMessage("Il n'y a pas de cartes dans votre fosse pour ajouter à votre Main.");
            return;
        }

        int choix = obtenirChoixCarte(cartesFosse);
        if (choix == -1) {
            return;
        }

        Carte carteChoisie = cartesFosse.get(choix);
        joueur.ajouterCarteDansMain(carteChoisie);
        joueur.getFosse().remove(carteChoisie);
        
        Affichage.afficherMessage("Vous avez ajouté la carte " + carteChoisie.getNom() + " à votre Main.");
    }
}
