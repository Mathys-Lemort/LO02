package Cartes;
import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import javafx.scene.control.TextInputDialog;

public class Deni extends Carte{    
    public Deni() {
        super("Deni","bleu", 2, "Defaussez une carte de votre Main. Copiez le pouvoir de cette carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        // Vérifier si le joueur a des cartes en main
        if (joueur.getMain().isEmpty()) {
            Affichage.afficherMessage("Vous n'avez pas de cartes en main.");
            return;
        }

        Affichage.afficherMessage("Voici votre main:");
        joueur.afficherMain();
        Affichage.afficherMessage("Choisissez une carte à défausser dont vous allez copier le pouvoir (entrez le numéro de la carte):");
        // Si le jeu est en mode graphique on créer une alerte de texte pour demander à l'utilisateur de choisir une carte
        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Choix de la carte à défausser");
            dialog.setHeaderText("Choisissez le numéro de la carte à défausser");
            dialog.setContentText("Numéro de la carte :");
            dialog.showAndWait();
            String result = dialog.getResult();
            try {
                int choix = Integer.parseInt(result);
                if (choix < 1 || choix > joueur.getMain().size()) {
                    Affichage.afficherMessage("Choix non valide. Veuillez réessayer avec un numéro de carte valide.");
                    return;
                }
                Carte carte = joueur.getMain().get(choix - 1);
                joueur.defausserCarteChoisit(carte);
                Affichage.afficherMessage("Vous avez défaussé la carte " + carte.getNom() + " et copiez son pouvoir.");
                carte.action(joueur, adversaire);
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

        // Vérifier si le choix est dans la plage valide
        if (choix < 1 || choix > joueur.getMain().size()) {
            Affichage.afficherMessage("Choix non valide. Veuillez réessayer avec un numéro de carte valide.");
            return;
        }

        Carte carte = joueur.getMain().get(choix - 1);
        joueur.defausserCarteChoisit(carte);
        Affichage.afficherMessage("Vous avez défaussé la carte " + carte.getNom() + " et copiez son pouvoir.");
        carte.action(joueur, adversaire); 
    }

    
}
