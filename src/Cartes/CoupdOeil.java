package Cartes;
import Joueurs.Joueur;
import Core.Affichage;
import Core.Partie;

/**
 * Classe représentant la carte "Coup d'Œil" dans le jeu.
 * Cette carte permet au joueur de regarder la main d'un rival et offre la possibilité de jouer une autre carte ensuite.
 */
public class CoupdOeil extends Carte {

    /**
     * Constructeur pour la carte Coup d'Œil.
     * Initialise la carte avec un nom, une couleur, un nombre de points et une description de son effet.
     */
    public CoupdOeil() {
        super("Coup d'Œil", "bleu", 1, "Regardez la Main d’un rival. Vous pouvez ensuite jouer une autre carte.", false);
    }

    /**
     * Effectue l'action spécifique de la carte Coup d'Œil.
     * Permet au joueur de voir la main de l'adversaire et donne l'option de jouer une autre carte.
     *
     * @param joueur Le joueur qui joue la carte.
     * @param adversaire Le joueur adverse dont la main sera regardée.
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getMain().isEmpty()) {
            Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas de cartes en main.");
            return;
        } else {
            // Affiche la main de l'adversaire si ce n'est pas un JoueurBot
            if (!(adversaire instanceof Joueurs.JoueurBot)) {
                adversaire.afficherMain();
            }
        }

        // Offre la possibilité de jouer une autre carte
        if (demanderJouerAutreCarte()) {
            if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
                Partie.getInstance().setRejouer(true);
            } else {
                Partie.getInstance().rejouer(joueur);
            }
        } else {
            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
            Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

        }
    }

}
