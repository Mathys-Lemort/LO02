package Cartes;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Incarnation extends Carte{
    public Incarnation() {
        super("Incarnation","None", 1, "Choisissez une de vos Oeuvres. Copiez son pouvoir.", true);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> oeuvres = joueur.getOeuvres();
        if (oeuvres.isEmpty()) {
            Affichage.afficherMessage("Vous n'avez pas d'œuvres à copier.");
            return;
        }

        Affichage.afficherMessage("Voici vos œuvres :");
        for (int i = 0; i < oeuvres.size(); i++) {
            Affichage.afficherOption(i + 1, oeuvres.get(i).getNom());
        }

        Scanner scanner = Partie.getInstance().getScanner();
        int choix = 0;
        while (choix < 1 || choix > oeuvres.size()) {
            Affichage.afficherMessage("Quel pouvoir voulez-vous copier ? (entrez un numéro valide)");
            try {
                choix = scanner.nextInt();
            } catch (InputMismatchException e) {
                Affichage.afficherMessage("Entrée non valide. Veuillez entrer un nombre.");
                scanner.nextLine(); // Nettoie le buffer du scanner
                continue;
            }
            scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()

            if (choix < 1 || choix > oeuvres.size()) {
                Affichage.afficherMessage("Choix non valide. Veuillez choisir un numéro entre 1 et " + oeuvres.size() + ".");
            }
        }

        Carte carte = oeuvres.get(choix - 1);
        Affichage.afficherMessage("Vous avez choisi la carte " + carte.getNom() + " dont le pouvoir est : " + carte.getPouvoir());
        carte.action(joueur, adversaire);
    }

}
