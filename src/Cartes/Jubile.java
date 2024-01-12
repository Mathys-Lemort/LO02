package Cartes;
import java.util.List;

import Core.Affichage;
import Joueurs.Joueur;

/**
 * La classe Jubile représente une carte spécifique du jeu.
 * Elle hérite de la classe Carte.
 */
public class Jubile extends Carte {
    
    /**
     * Constructeur de la classe Jubile.
     * Initialise les attributs de la carte Jubile.
     */
    public Jubile() {
        super("Jubile", "vert", 3, "Placez jusqu’à 2 cartes de votre Main sur vos Oeuvres.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte Jubile.
     * Permet au joueur de placer jusqu'à 2 cartes de sa main sur ses œuvres.
     * 
     * @param joueur le joueur qui joue la carte Jubile
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        for (int i = 0; i < 2; i++) {
            List<Carte> main = joueur.getMain();
            if (main.isEmpty()) {
                Affichage.afficherMessage("Vous n'avez plus de cartes en main.");
                break;
            }

            int choix = obtenirChoixCarte(main);
            if (choix != -1) {
                Carte carteChoisie = main.get(choix);
                joueur.ajouterCarteDansOeuvres(carteChoisie);
                joueur.suppCarteMain(carteChoisie);
                Affichage.afficherMessage("Vous avez placé la carte " + carteChoisie.getNom() + " sur vos œuvres.");
            }
        }
    }
}
