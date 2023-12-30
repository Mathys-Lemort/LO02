package Cartes;
import Joueurs.Joueur;

import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;

public class Lendemain extends Carte{    
    public Lendemain() {
        super("Lendemain","vert", 1, "Puisez une carte à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Partie.getInstance().piocherSourceMain(joueur);

        Scanner scanner = Partie.getInstance().getScanner();
        int choix = 0;
        do {
            Affichage.afficherMessage("Voulez-vous jouer une autre carte ?");
            Affichage.afficherOption(1, "Oui");
            Affichage.afficherOption(2, "Non");
            
            try {
                choix = scanner.nextInt();
            } catch (InputMismatchException e) {
                Affichage.afficherMessage("Entrée non valide. Veuillez entrer 1 ou 2.");
                scanner.nextLine(); // Nettoie le buffer du scanner
                continue;
            }
            scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()

            if (choix != 1 && choix != 2) {
                Affichage.afficherMessage("Choix non valide. Veuillez choisir 1 ou 2.");
            }
        } while (choix != 1 && choix != 2);

        if (choix == 1) {
            Partie.getInstance().rejouer(joueur);
        } else {
            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
        }
    }

    
}
