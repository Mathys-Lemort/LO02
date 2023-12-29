package Cartes;
import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Panique extends Carte{
    public Panique() {
        super("Panique","rouge", 1, "Défaussez la première carte de la Pile d'un joueur. Vous pouvez ensuite jouer une autre carte.", false);
    }

    
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getPile().isEmpty()) {
            Affichage.afficherMessage("La Pile de " + adversaire.getPseudo() + " est vide, aucune action possible.");
            return;
        }

        adversaire.defausserCartePile();

        int choix =0;
        do {
            Affichage.afficherMessage("Voulez-vous jouer une autre carte ?");
            Affichage.afficherOption(1, "Oui");
            Affichage.afficherOption(2, "Non");
            Scanner scannerPartie = Partie.getInstance().getScanner();

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
        } while (choix != 1 && choix != 2);

        if (choix == 1) {
            Partie.getInstance().rejouer(joueur);
        } else {
            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
        }
    }

}
