package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Incarnation extends Carte {
    public Incarnation() {
        super("Incarnation", "None", 1, "Choisissez une de vos Oeuvres. Copiez son pouvoir.", true);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> oeuvres = joueur.getOeuvres();
        if (oeuvres.isEmpty()) {
            Affichage.afficherMessage("Vous n'avez pas d'œuvres à copier.");
            return;
        }

        int choix = obtenirChoixCarte(oeuvres);
        if (choix != -1) {
            Carte carteChoisie = oeuvres.get(choix);
            Affichage.afficherMessage(
                    "Vous avez choisi la carte " + carteChoisie.getNom() + " dont le pouvoir est : " + carteChoisie.getPouvoir());
            carteChoisie.action(joueur, adversaire);
        }
    }

}
