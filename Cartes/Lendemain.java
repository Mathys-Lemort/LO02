package Cartes;
import Joueurs.Joueur;
import Core.Affichage;
import Core.Partie;

public class Lendemain extends Carte{    
    public Lendemain() {
        super("Lendemain","vert", 1, "Puisez une carte à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Partie.getInstance().piocherSourceMain(joueur);
        Affichage.afficherMessage("Vous avez pioché une carte à la Source");
        Affichage.afficherMessage("Voulez-vous jouer une autre carte ? (O/N)");
        String choix = Partie.getInstance().getScanner().next();
        if (choix.equals("oui")) {
            Partie.getInstance().rejouer(joueur);
        }

        
    }
    
}
