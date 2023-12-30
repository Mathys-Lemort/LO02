package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Duperie extends Carte {
    public Duperie() {
        super("Duperie", "bleu", 3, "Regardez 3 cartes de la Main d’un rival ; ajoutez-en une à votre Main.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getMain().isEmpty()) {
            Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas de cartes en main.");
            return;
        }

        int nombreCartesAPresenter = Math.min(adversaire.getMain().size(), 3);
        ArrayList<Carte> cartesAPresenter = adversaire.getMainChiffre(nombreCartesAPresenter);

        int choix = obtenirChoixCarte(cartesAPresenter);
        if (choix != -1) {
            Carte carteChoisie = cartesAPresenter.get(choix);
            joueur.ajouterCarteDansMain(carteChoisie);
            adversaire.suppCarteMain(carteChoisie);
            Affichage.afficherMessage("Vous avez pris la carte " + carteChoisie.getNom() + " de la main de " + adversaire.getPseudo());
        }
    }

}
