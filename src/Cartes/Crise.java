package Cartes;
import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import javafx.scene.control.TextInputDialog;

public class Crise extends Carte {
    public Crise() {
        super("Crise","rouge", 2, "Le rival de votre choix défausse\n" + //
                "une de ses Oeuvres.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getOeuvres().isEmpty()) {
            Affichage.afficherMessage("Votre adversaire n'a pas d'œuvre, tant pis pour vous !");
            return;
        }

        Affichage.afficherTitre(adversaire.getPseudo() + " doit défausser une œuvre");
        Affichage.afficherMessage("Voici vos œuvres :");
        adversaire.afficherCartesOeuvres();
        
        Affichage.afficherMessage("Choisissez le numéro de l'œuvre à défausser :");
        // Si le jeu est en mode graphique on créer une alerte de texte pour demander à l'utilisateur de choisir une carte
        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Choix de l'oeuvre à défausser");
            dialog.setHeaderText("Choisissez le numéro de l'oeuvre à défausser");
            dialog.setContentText("Numéro de l'oeuvre :");
            dialog.showAndWait();
            String result = dialog.getResult();
            try {
                int choix = Integer.parseInt(result);
                if (choix < 1 || choix > adversaire.getOeuvres().size()) {
                    Affichage.afficherMessage("Choix non valide. Veuillez réessayer avec un numéro de carte valide.");
                    return;
                }
                Carte carte = adversaire.getOeuvres().get(choix - 1);
                adversaire.defausserOeuvreChoisit(carte);
                Affichage.afficherMessage(adversaire.getPseudo() + " a défaussé l'œuvre " + carte.getNom() + ".");
            } catch (NumberFormatException e) {
                Affichage.afficherMessage("Entrée non valide. Veuillez entrer un nombre.");
                return;
            }
            return;
        }
        Scanner scanner = Partie.getInstance().getScanner();
        int choix;
        try {
            choix = scanner.nextInt();
        } catch (InputMismatchException e) {
            Affichage.afficherMessage("Entrée non valide. Veuillez entrer un nombre.");
            scanner.nextLine(); // Nettoie le buffer du scanner
            return;
        }

        if (choix < 1 || choix > adversaire.getOeuvres().size()) {
            Affichage.afficherMessage("Choix non valide. Veuillez réessayer avec un numéro de carte valide.");
            return;
        }

        Carte carte = adversaire.getOeuvres().get(choix - 1);
        adversaire.defausserOeuvreChoisit(carte);
        Affichage.afficherMessage(adversaire.getPseudo() + " a défaussé l'œuvre " + carte.getNom() + ".");
    }

}
