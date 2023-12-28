package Cartes;
import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Longevite extends Carte {  // Longevite.java
    public Longevite() {
        super("Longevite","vert", 2, "Placez 2 cartes puisées à la Source sur la Pile d'un joueur.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        for (int i = 0; i < 2; i++) {
            Partie.getInstance().piocherSourcePile(adversaire);
        }
        Affichage.afficherMessage("Vous avez placé 2 cartes de la Source sur la Pile de " + adversaire.getPseudo());
    }
    
}
