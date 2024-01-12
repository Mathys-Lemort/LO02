package Cartes;

import Core.Affichage;
import Joueurs.Joueur;
import Core.Partie;


/**
 * Cette classe représente une carte de type "Roulette" dans le jeu.
 * La carte "Roulette" permet au joueur de défausser jusqu'à 2 cartes de sa main.
 * Ensuite, le joueur peut piocher à la source un nombre de cartes égal au nombre de cartes défaussées plus 1.
 */
public class Roulette extends Carte {
    /**
     * Constructeur de la classe Roulette.
     * Initialise les attributs de la carte "Roulette" avec les valeurs spécifiques.
     */
    public Roulette() {
        super("Roulette", "rouge", 2, "Défaussez jusqu’à 2 cartes de votre Main. Vous pouvez ensuite puiser à la Source autant de carte(s) + 1.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte "Roulette".
     * Permet au joueur de défausser jusqu'à 2 cartes de sa main.
     * Ensuite, le joueur pioche à la source un nombre de cartes égal au nombre de cartes défaussées plus 1.
     * @param joueur le joueur qui joue la carte "Roulette"
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        int carteDefaussee = 0;
        
        for (int i = 0; i < 2; i++) {
            if (joueur.getMain().isEmpty()) {
                break; 
            }

            int choix = obtenirChoixCarte(joueur.getMain());
            if (choix == -1) {
                break;
            }

            joueur.defausserCarteChoisit(joueur.getMain().get(choix));
            carteDefaussee++;
        }
        
        Affichage.afficherMessage("Vous avez défaussé " + carteDefaussee + " carte(s).");
        
        for (int i = 0; i <= carteDefaussee; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }
        Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}
