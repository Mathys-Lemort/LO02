package Cartes;

import java.util.List;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * Cette classe représente la carte "Reves Brises" dans le jeu.
 * Elle hérite de la classe Carte.
 */
public class RevesBrises extends Carte {

    /**
     * Constructeur de la classe RevesBrises.
     * Initialise les attributs de la carte.
     */
    public RevesBrises() {
        super("Reves Brises", "bleu", 2, "Placez la première carte de la Vie Future d'un rival sur la vôtre.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte "Reves Brises".
     * Place la première carte de la Vie Future d'un rival sur la Vie Future du
     * joueur actuel.
     * 
     * @param joueur     le joueur actuel
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> vieFutureAdversaire = adversaire.getVieFuture();

        if (vieFutureAdversaire.isEmpty()) {
            Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas de cartes dans sa Vie Future.");
            return;
        }

        Carte carteAjoutee = vieFutureAdversaire.get(0);
        joueur.ajouterCarteDansVieFuture(carteAjoutee);
        vieFutureAdversaire.remove(0);

        Affichage.afficherMessage("Vous avez ajouté la carte " + carteAjoutee.getNom() + " de la Vie Future de "
                + adversaire.getPseudo() + " à votre Vie Future.");
        Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }

}
