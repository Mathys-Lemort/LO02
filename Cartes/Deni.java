package Cartes;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Deni extends Carte{    
    public Deni() {
        super("Deni","bleu", 2, "Defaussez une carte de votre Main. Copiez le pouvoir de cette carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Affichage.afficherMessage("Voici votre main:");
        joueur.afficherMain();
        Affichage.afficherMessage("Choisissez une carte à défausser dont vous allez copier le pouvoir:");
        Scanner scanner = Partie.getInstance().getScanner();
        int choix = scanner.nextInt();
        Carte carte = joueur.getMain().get(choix-1);
        joueur.defausserCarteChoisit(carte);
        carte.action(joueur, adversaire);       
    }
    
}
