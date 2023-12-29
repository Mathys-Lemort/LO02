package Cartes;
import Core.Partie;
import Joueurs.Joueur;

import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;

public class Semis extends Carte {
    public Semis() {
        super("Semis","vert", 2, "Puisez 2 cartes à la Source, puis placez sur votre Vie Future 2 cartes de votre Main.", false);
    }


    
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        for (int i = 0; i < 2; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }
        Affichage.afficherMessage("Vous avez pioché 2 cartes à la Source.");

        Scanner scanner = Partie.getInstance().getScanner();
        for (int i = 0; i < 2; i++) {
            if (joueur.getMain().isEmpty()) {
                Affichage.afficherMessage("Vous n'avez plus de cartes en main pour placer dans votre Vie Future.");
                break;
            }

            Affichage.afficherMessage("Choisissez une carte à placer dans votre Vie Future :");
            for (int j = 0; j < joueur.getMain().size(); j++) {
                Affichage.afficherOption(j + 1, joueur.getMain().get(j).getNom());
            }

            int choix = 0;
            while (choix < 1 || choix > joueur.getMain().size()) {
                try {
                    choix = scanner.nextInt();
                } catch (InputMismatchException e) {
                    Affichage.afficherMessage("Entrée non valide. Veuillez entrer un nombre.");
                    scanner.nextLine(); // Nettoie le buffer du scanner
                    continue;
                }
                scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()

                if (choix < 1 || choix > joueur.getMain().size()) {
                    Affichage.afficherMessage("Choix non valide. Veuillez choisir un numéro entre 1 et " + joueur.getMain().size() + ".");
                }
            }

            Carte carte = joueur.getMain().get(choix - 1);
            joueur.ajouterCarteDansVieFuture(carte);
            joueur.getMain().remove(carte);
            Affichage.afficherMessage("Vous avez placé la carte " + carte.getNom() + " dans votre Vie Future.");
        }
    }

}
