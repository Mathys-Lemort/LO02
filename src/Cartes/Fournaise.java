package Cartes;
import Joueurs.Joueur;

import java.util.List;

import Core.Affichage;
import Core.Partie;

/**
 * La classe Fournaise représente une carte spécifique du jeu.
 * Elle hérite de la classe Carte.
 * La carte Fournaise permet de défausser les 2 premières cartes de la Vie Future d'un rival.
 */
public class Fournaise extends Carte {
    /**
     * Constructeur de la classe Fournaise.
     * Initialise les attributs de la carte avec les valeurs spécifiques.
     */
    public Fournaise() {
        super("Fournaise","rouge", 2, "Defaussez les 2 premières cartes de la Vie Future d'un rival.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte Fournaise.
     * Défausse les 2 premières cartes de la Vie Future d'un adversaire.
     * Affiche les cartes défaussées et un message de confirmation.
     * Si l'adversaire n'a pas assez de cartes dans sa Vie Future, affiche un message d'avertissement.
     * @param joueur le joueur qui utilise la carte Fournaise
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> vieFutureAdversaire = adversaire.getVieFuture();

        if (vieFutureAdversaire.size() < 2) {
            Affichage.afficherMessage("Votre adversaire n'a pas assez de cartes dans sa Vie Future.");
            return;
        }
        
        Affichage.afficherMessage("Les 2 premières cartes de la Vie Future de " + adversaire.getPseudo() + " sont :");
        
        for (int i = 0; i < 2; i++) {
            Affichage.afficherMessage((i + 1) + ". " + vieFutureAdversaire.get(i).getNom());
        }

        adversaire.defausserCarteVieFutureChiffre(2);
        
        Affichage.afficherMessage("Vous avez défaussé les 2 premières cartes de la Vie Future de " + adversaire.getPseudo() + ".");
                    Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}