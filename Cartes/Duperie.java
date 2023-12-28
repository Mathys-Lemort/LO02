package Cartes;
import Joueurs.Joueur;

import java.util.ArrayList;

import Core.Affichage;
import Core.Partie;

public class Duperie extends Carte {
    public Duperie() {
        super("Duperie","bleu", 3, "Regardez 3 cartes de la Main d’un rival ; ajoutez-en une à votre Main.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> main = adversaire.getMainChiffre(3);
        Affichage.afficherMessage("Voici les 3 premières cartes de la main de " + adversaire.getPseudo());
        for (int i = 0; i < main.size(); i++) {
            Affichage.afficherOption(i+1, main.get(i).getNom());
        }
        Affichage.afficherMessage("Quelle carte voulez-vous prendre ? (1, 2 ou 3)");
        int choix = Partie.getInstance().getScanner().nextInt();
        Carte carte = main.get(choix-1);
        joueur.ajouterCarteDansMain(carte);
        adversaire.suppCarteMain(carte);
        Affichage.afficherMessage("Vous avez pris la carte " + carte.getNom() + " de la main de " + adversaire.getPseudo());
        
    }
}
