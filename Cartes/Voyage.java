package Cartes;
import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Voyage extends Carte{
    public Voyage() {
        super("Voyage","vert", 3, "Puisez 3 cartes à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        for (int i = 0; i < 3; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }
        Affichage.afficherMessage("Vous avez pioché 3 cartes à la Source.");
        Affichage.afficherMessage("Voulez vous jouer une autre carte ? (O/N)");
        if (Partie.getInstance().getScanner().nextLine().equals("O")) {
            Partie.getInstance().rejouer(joueur);
        }
                   
    }
    
}
