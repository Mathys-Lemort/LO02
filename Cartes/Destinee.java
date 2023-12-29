package Cartes;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Destinee extends Carte {
    public Destinee() {
        super("Destinée","bleu", 2, "Regardez les 3 premières cartes de la Source ; ajoutez-en jusqu’à 2 à votre Vie Future. Replacez le reste dans l'ordre souhaité", false);
    }

        @Override
        public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesSource = Partie.getInstance().afficher3PremieresCartesSource();
        Scanner scanner = Partie.getInstance().getScanner();

        for (int i = 0; i < 2 && !cartesSource.isEmpty(); i++) {
            Affichage.afficherMessage("Quelle carte voulez-vous ajouter à votre Vie Future ?");
            Affichage.afficherOption(0, "Aucune");
            for (int j = 0; j < cartesSource.size(); j++) {
                Affichage.afficherOption(j + 1, cartesSource.get(j).getNom());
            }

            int choix;
            try {
                choix = scanner.nextInt();
            } catch (InputMismatchException e) {
                Affichage.afficherMessage("Entrée non valide. Veuillez entrer un nombre.");
                scanner.nextLine(); // Nettoie le buffer du scanner
                i--;
                continue;
            }

            if (choix < 0 || choix > cartesSource.size()) {
                Affichage.afficherMessage("Choix non valide. Veuillez réessayer.");
                i--;
            } else if (choix != 0) {
                Carte carteChoisie = cartesSource.remove(choix - 1);
                joueur.ajouterCarteDansVieFuture(carteChoisie);
                Affichage.afficherMessage("Vous avez ajouté " + carteChoisie.getNom() + " à votre Vie Future.");
            } else {
                break;
            }
        }
    }

}
