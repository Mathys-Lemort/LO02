package Cartes;
import java.util.InputMismatchException;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

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
