package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Recyclage extends Carte {
    public Recyclage() {
        super("Recyclage", "vert", 1, "Ajoutez à votre Vie Future une des 3 dernières cartes de la Fosse.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesFosse = joueur.getCartesFosse(3);
        if (cartesFosse.isEmpty()) {
            Affichage.afficherMessage("Il n'y a pas de cartes dans votre fosse pour ajouter à votre Vie Future.");
            return;
        }

        int choix = obtenirChoixCarte(cartesFosse);
        if (choix >= 0) {
            Carte carte = cartesFosse.get(choix);
            joueur.ajouterCarteDansVieFuture(carte);
            joueur.getFosse().remove(carte);
            Affichage.afficherMessage("Vous avez ajouté la carte " + carte.getNom() + " à votre Vie Future.");
        }
    }
}
