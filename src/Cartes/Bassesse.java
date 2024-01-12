package Cartes;
import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * Classe représentant la carte "Bassesse".
 * Cette carte a pour effet de défausser au hasard des cartes de la main d'un adversaire.
 */
public class Bassesse extends Carte {

    /**
     * Constructeur pour la carte Bassesse.
     * Initialise la carte avec des paramètres spécifiques tels que le nom, la couleur, 
     * les points et la description de l'effet.
     */
    public Bassesse() {
        super("Bassesse", "rouge", 3, "Défaussez au hasard 2 cartes\n" +
                "de la Main d’un rival.", false);
    }

    /**
     * Effectue l'action spécifique de la carte Bassesse.
     * Défausse au hasard 2 cartes de la main d'un adversaire.
     * Si l'adversaire n'a pas de cartes en main, aucun effet ne se produit.
     *
     * @param joueur Le joueur qui joue la carte.
     * @param adversaire Le joueur adverse affecté par l'action de la carte.
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        int tailleMainAdversaire = adversaire.getMain().size();

        if (tailleMainAdversaire == 0) {
            Affichage.afficherMessage("Votre adversaire n'a pas de carte en main, aucune action possible.");
            return;
        }

        int nbCartesADefaussées = Math.min(tailleMainAdversaire, 2);
        adversaire.defausserCarte(nbCartesADefaussées);

        String message = (nbCartesADefaussées == 1) 
                        ? "Votre adversaire a défaussé 1 carte au hasard." 
                        : "Votre adversaire a défaussé 2 cartes au hasard.";
        Affichage.afficherMessage(message);
                    Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}
