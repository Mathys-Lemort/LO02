package Cartes;
import Joueurs.Joueur;

import java.util.Scanner;

import Core.Affichage;
import Core.Partie;

public class CoupdOeil extends Carte {
    public CoupdOeil() {
        super("Coup Doeil","bleu", 1, "Regardez la Main d’un rival.\n" + 
                "Vous pouvez ensuite jouer une autre carte.", false);
                
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Affichage.afficherTitre("Voici la main de votre adversaire");
        adversaire.afficherMain();
        Affichage.afficherMessage("Voulez-vous jouer une autre carte ? (O/N)");
        Scanner scannerPartie = Partie.getInstance().getScanner();
        String choix = scannerPartie.nextLine();
        if (choix.equals("O")) {
            Partie.getInstance().rejouer(joueur);
        }
        else {
            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
        }
        

    }
}
