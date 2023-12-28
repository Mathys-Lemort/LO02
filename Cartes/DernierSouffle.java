package Cartes;
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
        adversaire.afficherMain();
        Affichage.afficherMessage(adversaire.getPseudo() + " choisissez une carte à défausser de votre main:");
        Scanner scanner = Partie.getInstance().getScanner();
        int choix = scanner.nextInt();
        Carte carte = joueur.getMain().get(choix-1);
        joueur.defausserCarteChoisit(carte);
    }
}
