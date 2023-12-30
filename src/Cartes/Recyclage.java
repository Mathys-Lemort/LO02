package Cartes;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;
import Joueurs.Joueur;
import javafx.scene.control.TextInputDialog;
import Core.Partie;

public class Recyclage extends Carte {
    public Recyclage() {
        super("Recyclage","vert", 1, "Ajoutez à votre Vie Future une des 3 dernières cartes de la Fosse.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesFosse = joueur.getCartesFosse(3);
        if (cartesFosse.isEmpty()) {
            Affichage.afficherMessage("Il n'y a pas de cartes dans votre fosse pour ajouter à votre Vie Future.");
            return;
        }
    
        Affichage.afficherMessage("Choisissez une carte à ajouter à votre Vie Future :");
        for (int i = 0; i < cartesFosse.size(); i++) {
            Affichage.afficherOption(i + 1, cartesFosse.get(i).getNom());
        }

        // Si le jeu est en mode graphique on créer une alerte de texte pour demander à l'utilisateur de choisir une carte
        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Choix de la carte à ajouter à la Vie Future");
            dialog.setHeaderText("Choisissez le numéro de la carte à ajouter à la Vie Future");
            dialog.setContentText("Numéro de la carte :");
            dialog.showAndWait();
            String result = dialog.getResult();
            try {
                int choix = Integer.parseInt(result);
                if (choix < 1 || choix > cartesFosse.size()) {
                    Affichage.afficherMessage("Choix non valide. Veuillez réessayer avec un numéro de carte valide.");
                    return;
                }
                Carte carte = cartesFosse.get(choix - 1);
                joueur.ajouterCarteDansVieFuture(carte);
                joueur.getFosse().remove(carte);
                Affichage.afficherMessage("Vous avez ajouté la carte " + carte.getNom() + " à votre Vie Future.");
            } catch (NumberFormatException e) {
                Affichage.afficherMessage("Entrée non valide. Veuillez entrer un nombre.");
                return;
            }
            return;
        }
    
        Scanner scanner = Partie.getInstance().getScanner();
        int choix = 0;
        while (choix < 1 || choix > cartesFosse.size()) {
            try {
                choix = scanner.nextInt();
                scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()
            } catch (InputMismatchException e) {
                Affichage.afficherMessage("Entrée non valide. Veuillez entrer un nombre.");
                scanner.nextLine(); // Nettoie le buffer du scanner
                continue;
            }
    
            if (choix < 1 || choix > cartesFosse.size()) {
                Affichage.afficherMessage("Choix non valide. Veuillez choisir un numéro entre 1 et " + cartesFosse.size() + ".");
            }
        }
    
        Carte carte = cartesFosse.get(choix - 1);
        if (carte != null) {
            joueur.ajouterCarteDansVieFuture(carte);
            joueur.getFosse().remove(carte);
            Affichage.afficherMessage("Vous avez ajouté la carte " + carte.getNom() + " à votre Vie Future.");
        } else {
            Affichage.afficherMessage("Erreur : La carte sélectionnée est introuvable.");
        }
    }
    

    
}