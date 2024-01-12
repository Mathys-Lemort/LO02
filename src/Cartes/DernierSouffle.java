package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * Classe représentant la carte "Dernier Souffle" dans le jeu.
 * Cette carte permet au joueur de forcer un adversaire à défausser une carte de sa main.
 */
public class DernierSouffle extends Carte {

    /**
     * Constructeur pour la carte Dernier Souffle.
     * Initialise la carte avec un nom, une couleur, un nombre de points et une description de son effet.
     */
    public DernierSouffle() {
        super("Dernier Souffle", "rouge", 1, "Le joueur de votre choix défausse une carte de sa Main.", false);
    }

    /**
     * Effectue l'action spécifique de la carte Dernier Souffle.
     * Force un adversaire à défausser une carte de sa main. Si l'adversaire n'a pas de cartes en main,
     * un message est affiché et aucune action ne se produit.
     *
     * @param joueur Le joueur qui joue la carte.
     * @param adversaire Le joueur adverse ciblé par l'action de la carte.
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getMain().isEmpty()) {
            Affichage.afficherMessage("Votre adversaire n'a pas de carte en main.");
            return;
        }

        int choix = obtenirChoixCarte(adversaire.getMain());
        if (choix != -1) {
            Carte carte = adversaire.getMain().get(choix);
            adversaire.defausserCarteChoisit(carte);
            Affichage.afficherMessage(adversaire.getPseudo() + " a défaussé la carte " + carte.getNom() + ".");
        }
                    Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}
