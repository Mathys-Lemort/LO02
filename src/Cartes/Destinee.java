package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

import java.util.ArrayList;

/**
 * La classe Destinee représente une carte "Destinee" dans le jeu.
 * Cette carte permet au joueur de regarder les 3 premières cartes de la Source,
 * d'en ajouter jusqu'à 2 à sa Vie Future et de replacer le reste dans l'ordre souhaité.
 */
public class Destinee extends Carte {
    /**
     * Construit une nouvelle instance de la carte "Destinee".
     * Définit le nom, la couleur, la valeur, la description et la réutilisabilité de la carte.
     */
    public Destinee() {
        super("Destinee", "bleu", 2,
                "Regardez les 3 premières cartes de la Source ; ajoutez-en jusqu’à 2 à votre Vie Future. Replacez le reste dans l'ordre souhaité",
                false);
    }

    /**
     * Effectue l'action de la carte "Destinee".
     * Permet au joueur de choisir jusqu'à 2 cartes parmi les 3 premières cartes du deck "Source"
     * et de les ajouter à son deck "Vie Future".
     * Les cartes restantes sont replacées dans le deck "Source" dans l'ordre souhaité.
     *
     * @param joueur     Le joueur qui a joué la carte.
     * @param adversaire Le joueur adverse.
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesSource = Partie.getInstance().afficher3PremieresCartesSource();

        for (int i = 0; i < 2 && !cartesSource.isEmpty(); i++) {
            int choix = obtenirChoixCarte(cartesSource);
            if (choix != -1) {
                Carte carteChoisie = cartesSource.remove(choix);
                joueur.ajouterCarteDansVieFuture(carteChoisie);

                Affichage.afficherMessage("Vous avez ajouté " + carteChoisie.getNom() + " à votre Vie Future.");

            } else {
                break;
            }
        }
        Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}
