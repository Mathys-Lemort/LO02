package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * Classe représentant la carte "Crise" dans le jeu.
 * Cette carte permet de forcer un adversaire à défausser une de ses œuvres.
 */
public class Crise extends Carte {

    /**
     * Constructeur pour la carte Crise.
     * Initialise la carte avec un nom, une couleur, un nombre de points et une description de son effet.
     */
    public Crise() {
        super("Crise", "rouge", 2, "Le rival de votre choix défausse\nune de ses Oeuvres.", false);
    }

    /**
     * Effectue l'action spécifique de la carte Crise.
     * Oblige l'adversaire choisi à défausser une de ses œuvres. Si l'adversaire n'a pas d'œuvres,
     * affiche un message indiquant que l'action ne peut pas être effectuée.
     *
     * @param joueur Le joueur qui joue la carte.
     * @param adversaire Le joueur adverse affecté par l'action de la carte.
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getOeuvres().isEmpty()) {
            Affichage.afficherMessage("Votre adversaire n'a pas d'œuvre, tant pis pour vous !");
            return;
        }

        int choix = obtenirChoixCarte(adversaire.getOeuvres());
        if (choix != -1) {
            Carte carte = adversaire.getOeuvres().get(choix);
            adversaire.defausserOeuvreChoisit(carte);
            Affichage.afficherMessage(adversaire.getPseudo() + " a défaussé l'œuvre " + carte.getNom() + ".");
        }
                    Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}
