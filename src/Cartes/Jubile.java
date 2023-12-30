package Cartes;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import javafx.scene.control.ChoiceDialog;

public class Jubile extends Carte {
    public Jubile() {
        super("Jubile", "vert", 3, "Placez jusqu’à 2 cartes de votre Main sur vos Oeuvres.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        for (int i = 0; i < 2; i++) {
            List<Carte> main = joueur.getMain();
            if (main.isEmpty()) {
                Affichage.afficherMessage("Vous n'avez plus de cartes en main.");
                break;
            }

            int choix = obtenirChoixCarte(main);
            if (choix != -1) {
                Carte carteChoisie = main.get(choix);
                joueur.ajouterCarteDansOeuvres(carteChoisie);
                joueur.suppCarteMain(carteChoisie);
                Affichage.afficherMessage("Vous avez placé la carte " + carteChoisie.getNom() + " sur vos œuvres.");
            }
        }
    }

}
