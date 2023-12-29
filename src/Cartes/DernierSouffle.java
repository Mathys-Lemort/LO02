package Cartes;
import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class DernierSouffle extends Carte {
    public DernierSouffle() {
        super("Dernier Souffle","rouge", 1, "Le joueur de votre choix défausse une carte de sa Main.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        // Vérifier si l'adversaire a des cartes en main
        if (adversaire.getMain().size() == 0) {
            Affichage.afficherMessage("Votre adversaire n'a pas de carte en main.");
            return;
        }

        adversaire.afficherMain();
        Affichage.afficherMessage(adversaire.getPseudo() + ", choisissez une carte à défausser de votre main (entrez le numéro de la carte):");
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
        if (choix < 1 || choix > adversaire.getMain().size()) {
            Affichage.afficherMessage("Choix non valide. Veuillez réessayer avec un numéro de carte valide.");
            return;
        }

        Carte carte = adversaire.getMain().get(choix - 1);
        adversaire.defausserCarteChoisit(carte);
        Affichage.afficherMessage("Vous avez défaussé " + carte.getNom() + " de la main de " + adversaire.getPseudo() + ".");
    }

}
