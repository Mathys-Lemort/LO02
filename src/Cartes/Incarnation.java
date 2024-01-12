package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

import java.util.List;

/**
 * La classe Incarnation représente une carte d'incarnation dans le jeu.
 * Elle hérite de la classe Carte.
 */
public class Incarnation extends Carte {
    /**
     * Constructeur de la classe Incarnation.
     * Initialise les attributs de la carte d'incarnation.
     */
    public Incarnation() {
        super("Incarnation", "None", 1, "Choisissez une de vos Oeuvres. Copiez son pouvoir.", true);
    }

    /**
     * Méthode qui effectue l'action de la carte d'incarnation.
     * Copie le pouvoir d'une des œuvres du joueur.
     * 
     * @param joueur le joueur qui utilise la carte d'incarnation
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> oeuvres = joueur.getOeuvres();
        if (oeuvres.isEmpty()) {
            Affichage.afficherMessage("Vous n'avez pas d'œuvres à copier.");
            return;
        }

        int choix = obtenirChoixCarte(oeuvres);
        if (choix != -1) {
            Carte carteChoisie = oeuvres.get(choix);
            Affichage.afficherMessage(
                    "Vous avez choisi la carte " + carteChoisie.getNom() + " dont le pouvoir est : " + carteChoisie.getPouvoir());
            carteChoisie.action(joueur, adversaire);
        }
                    Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}
