package Cartes;
import java.util.Scanner;

import Core.Affichage;
import Joueurs.Joueur;
import Core.Partie;

public class Roulette extends Carte{
    public Roulette() {
        super("Roulette","rouge", 2, "Defaussez jusqu’à 2 cartes de votre Main. Vous pouvez ensuite puiser à la Source autant de carte(s) + 1.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        int carteDefaussee = 0;
        Scanner scanner = Partie.getInstance().getScanner();

        for (int i = 0; i < 2; i++) {
            if (joueur.getMain().isEmpty()) {
                break; // Pas de cartes à défausser
            }

            Affichage.afficherMessage("Choisissez une carte à défausser :");
            Affichage.afficherOption(0, "Ne pas défausser de carte");
            for (int j = 0; j < joueur.getMain().size(); j++) {
                Affichage.afficherOption(j + 1, joueur.getMain().get(j).getNom());
            }

            int choix;
            do {
                choix = scanner.nextInt();
                scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()
                if (choix < 0 || choix > joueur.getMain().size()) {
                    Affichage.afficherMessage("Choix non valide. Veuillez choisir un numéro valide.");
                }
            } while (choix < 0 || choix > joueur.getMain().size());

            if (choix != 0) {
                joueur.defausserCarteChoisit(joueur.getMain().get(choix - 1));
                carteDefaussee++;
            }
        }

        Affichage.afficherMessage("Vous avez défaussé " + carteDefaussee + " carte(s).");
        for (int i = 0; i < carteDefaussee; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }
    }
}
