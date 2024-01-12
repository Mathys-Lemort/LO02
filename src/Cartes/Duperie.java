package Cartes;

import Core.Affichage;
import Joueurs.Joueur;

import java.util.ArrayList;

/**
 * La classe Duperie représente une carte de type "Duperie" dans le jeu.
 * Cette carte permet au joueur de regarder 3 cartes de la main d'un adversaire
 * et d'en ajouter une à sa propre main.
 */
public class Duperie extends Carte {
    /**
     * Constructeur de la classe Duperie.
     * Initialise les attributs de la carte avec les valeurs spécifiées.
     */
    public Duperie() {
        super("Duperie", "bleu", 3, "Regardez 3 cartes de la Main d’un rival ; ajoutez-en une à votre Main.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte Duperie.
     * Permet au joueur de regarder 3 cartes de la main d'un adversaire
     * et d'en ajouter une à sa propre main.
     * 
     * @param joueur le joueur qui joue la carte
     * @param adversaire l'adversaire du joueur
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getMain().isEmpty()) {
            Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas de cartes en main.");
            return;
        }

        int nombreCartesAPresenter = Math.min(adversaire.getMain().size(), 3);
        ArrayList<Carte> cartesAPresenter = adversaire.getMainChiffre(nombreCartesAPresenter);

        int choix = obtenirChoixCarte(cartesAPresenter);
        if (choix != -1) {
            Carte carteChoisie = cartesAPresenter.get(choix);
            joueur.ajouterCarteDansMain(carteChoisie);
            adversaire.suppCarteMain(carteChoisie);
            Affichage.afficherMessage("Vous avez pris la carte " + carteChoisie.getNom() + " de la main de " + adversaire.getPseudo());
        }
    }
}
