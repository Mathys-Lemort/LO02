package Cartes;
import Joueurs.Joueur;

import java.util.InputMismatchException;
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
        Scanner scannerPartie = Partie.getInstance().getScanner();
        int choix = 0;

        while (choix != 1 && choix != 2) {
            Affichage.afficherMessage("Voulez-vous jouer une autre carte ?");
            Affichage.afficherOption(1, "Oui");
            Affichage.afficherOption(2, "Non");

            try {
                choix = scannerPartie.nextInt();
            } catch (InputMismatchException e) {
                Affichage.afficherMessage("Entrée non valide. Veuillez choisir 1 ou 2.");
                scannerPartie.nextLine(); // Nettoie le buffer du scanner
                continue;
            }

            scannerPartie.nextLine(); // Consomme la nouvelle ligne après nextInt()

            if (choix != 1 && choix != 2) {
                Affichage.afficherMessage("Choix non valide. Veuillez choisir 1 ou 2.");
            }
        }

        if (choix == 1) {
            Partie.getInstance().rejouer(joueur);
        } else {
            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
        }
    }

}
