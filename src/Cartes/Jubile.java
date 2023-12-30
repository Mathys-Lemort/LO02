package Cartes;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import javafx.scene.control.TextInputDialog;

public class Jubile extends Carte {
    public Jubile() {
        super("Jubile","vert", 3, "Placez jusqu’à 2 cartes de votre Main sur vos Oeuvres.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Scanner scanner = Partie.getInstance().getScanner();

        for (int i = 0; i < 2; i++) {
            List<Carte> main = joueur.getMain();
            if (main.isEmpty()) {
                Affichage.afficherMessage("Vous n'avez plus de cartes en main.");
                break;
            }

            Affichage.afficherMessage("Voici vos cartes :");
            Affichage.afficherOption(0, "Ne pas placer de carte");
            for (int j = 0; j < main.size(); j++) {
                Affichage.afficherOption(j + 1, main.get(j).getNom());
            }

            int choix = -1;
            while (choix < 0 || choix > main.size()) {
                Affichage.afficherMessage("Quelle carte voulez-vous placer sur vos œuvres ? (entrez un numéro valide)");
                // Si le jeu est en mode graphique on créer une alerte de texte pour demander à l'utilisateur de choisir une carte
                if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Choix de la carte à placer");
                    dialog.setHeaderText("Choisissez le numéro de la carte à placer");
                    dialog.setContentText("Numéro de la carte :");
                    dialog.showAndWait();
                    String result = dialog.getResult();
                    try {
                        choix = Integer.parseInt(result);
                        if (choix < 0 || choix > main.size()) {
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

                if (choix < 0 || choix > main.size()) {
                    Affichage.afficherMessage("Choix non valide. Veuillez choisir un numéro entre 0 et " + main.size() + ".");
                }
            }

            if (choix != 0) {
                Carte carte = main.get(choix - 1);
                joueur.ajouterCarteDansOeuvres(carte);
                joueur.suppCarteMain(carte);
                Affichage.afficherMessage("Vous avez placé la carte " + carte.getNom() + " sur vos œuvres.");
            }
        }
    }

}
