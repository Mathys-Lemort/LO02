package Cartes;
import java.util.List;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * La classe Longevite représente une carte du jeu avec une action spécifique.
 * Elle hérite de la classe Carte.
 */
public class Longevite extends Carte {  
    /**
     * Constructeur de la classe Longevite.
     * Initialise les attributs de la carte.
     */
    public Longevite() {
        super("Longevite","vert", 2, "Placez 2 cartes puisées à la Source sur la Pile d'un joueur.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte Longevite.
     * Place 2 cartes puisées à la Source sur la Pile d'un joueur.
     * @param joueur le joueur qui joue la carte
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> source = Partie.getInstance().getCartesSource();

        if (source.size() < 2) {
            Affichage.afficherMessage("Il n'y a pas assez de cartes dans la Source pour effectuer cette action.");
            return;
        }
        
        Affichage.afficherMessage("Les cartes placées sur la Pile de " + adversaire.getPseudo() + " sont :");
        
        for (int i = 0; i < 2; i++) {
            Carte cartePiochee = source.get(0);
            Partie.getInstance().piocherSourcePile(adversaire);
            
            if (!(joueur instanceof Joueurs.JoueurBot)) {            
                Affichage.afficherMessage("- " + cartePiochee.getNom());
            }
        }
    }
}
