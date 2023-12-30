package Cartes;
import Joueurs.Joueur;
import javafx.scene.control.TextInputDialog;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;

public class Duperie extends Carte {
    public Duperie() {
        super("Duperie","bleu", 3, "Regardez 3 cartes de la Main d’un rival ; ajoutez-en une à votre Main.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getMain().isEmpty()) {
            Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas de cartes en main.");
            return;
        }

        int nombreCartesAPresenter = Math.min(adversaire.getMain().size(), 3);
        ArrayList<Carte> main = adversaire.getMainChiffre(nombreCartesAPresenter);
        Affichage.afficherMessage("Voici les " + nombreCartesAPresenter + " premières cartes de la main de " + adversaire.getPseudo());
        for (int i = 0; i < main.size(); i++) {
            Affichage.afficherOption(i + 1, main.get(i).getNom());
        }

        Scanner scanner = Partie.getInstance().getScanner();
        int choix = 0;
        while (choix < 1 || choix > nombreCartesAPresenter) {
            Affichage.afficherMessage("Quelle carte voulez-vous prendre ? (entrez un numéro valide)");
            // Si le jeu est en mode graphique on créer une alerte de texte pour demander à l'utilisateur de choisir une carte
            if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Choix de la carte à prendre");
                dialog.setHeaderText("Choisissez le numéro de la carte à prendre");
                dialog.setContentText("Numéro de la carte :");
                dialog.showAndWait();
                String result = dialog.getResult();
                try {
                    choix = Integer.parseInt(result);
                    if (choix < 1 || choix > nombreCartesAPresenter) {
                        Affichage.afficherMessage("Choix non valide. Veuillez réessayer avec un numéro de carte valide.");
                        continue;
                    }
                } catch (NumberFormatException e) {
                    Affichage.afficherMessage("Entrée non valide. Veuillez entrer un nombre.");
                    continue;
                }
                break;
            }


            try {
                choix = scanner.nextInt();
            } catch (InputMismatchException e) {
                Affichage.afficherMessage("Entrée non valide. Veuillez entrer un nombre.");
                scanner.nextLine(); // Nettoie le buffer du scanner
                continue;
            }
            scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()

            if (choix < 1 || choix > nombreCartesAPresenter) {
                Affichage.afficherMessage("Choix non valide. Veuillez choisir un numéro de 1 à " + nombreCartesAPresenter + ".");
            }
        }

        Carte carte = main.get(choix - 1);
        joueur.ajouterCarteDansMain(carte);
        adversaire.suppCarteMain(carte);
        Affichage.afficherMessage("Vous avez pris la carte " + carte.getNom() + " de la main de " + adversaire.getPseudo());
    }

}
