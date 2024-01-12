package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * Classe représentant la carte "Deni" dans le jeu.
 * Cette carte permet au joueur de défausser une de ses cartes en main pour copier et exécuter son pouvoir.
 */
public class Deni extends Carte {

    /**
     * Constructeur pour la carte Deni.
     * Initialise la carte avec un nom, une couleur, un nombre de points et une description de son effet.
     */
    public Deni() {
        super("Deni", "bleu", 2, "Defaussez une carte de votre Main. Copiez le pouvoir de cette carte.", false);
    }

    /**
     * Effectue l'action spécifique de la carte Deni.
     * Le joueur doit choisir une carte à défausser de sa main, puis il copie et exécute le pouvoir de cette carte.
     * Si le joueur n'a pas de cartes en main, un message est affiché et aucune action ne se produit.
     *
     * @param joueur Le joueur qui joue la carte.
     * @param adversaire Le joueur adverse, nécessaire si le pouvoir de la carte copiée affecte un adversaire.
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (joueur.getMain().isEmpty()) {
            Affichage.afficherMessage("Vous n'avez pas de cartes en main.");
            return;
        }

        int choix = obtenirChoixCarte(joueur.getMain());
        if (choix != -1) {
            Carte carte = joueur.getMain().get(choix);
            joueur.defausserCarteChoisit(carte);
            Affichage.afficherMessage("Vous avez défaussé la carte " + carte.getNom() + " et copiez son pouvoir.");
            carte.action(joueur, adversaire);
        }
                    Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}
