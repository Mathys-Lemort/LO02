package Cartes;
import java.util.List;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Longevite extends Carte {  // Longevite.java
    public Longevite() {
        super("Longevite","vert", 2, "Placez 2 cartes puisées à la Source sur la Pile d'un joueur.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> source = Partie.getInstance().getCartesSource();

        if (source.size() < 2) {
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage("Il n'y a pas assez de cartes dans la Source pour effectuer cette action.");
            }
            return;
        }
        if (!(joueur instanceof Joueurs.JoueurBot)) {

        Affichage.afficherMessage("Les cartes placées sur la Pile de " + adversaire.getPseudo() + " sont :");
        }
        for (int i = 0; i < 2; i++) {
            Carte cartePiochee = source.get(0);
            Partie.getInstance().piocherSourcePile(adversaire);
                        if (!(joueur instanceof Joueurs.JoueurBot)) {            
            Affichage.afficherMessage("- " + cartePiochee.getNom());
                        }
        }
    }

    
}
