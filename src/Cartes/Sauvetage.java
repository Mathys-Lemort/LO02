package Cartes;

import Core.Affichage;
import Joueurs.Joueur;

import java.util.ArrayList;

public class Sauvetage extends Carte {
    public Sauvetage() {
        super("Sauvetage", "vert", 2, "Ajoutez à votre Main une des 3 dernières cartes de la Fosse.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesFosse = joueur.getCartesFosse(3);
        if (cartesFosse.isEmpty()) {
            Affichage.afficherMessage("Il n'y a pas de cartes dans votre fosse pour ajouter à votre Main.");
            return;
        }

        int choix = obtenirChoixCarte(cartesFosse);
        if (choix == -1) {
            return; // Aucune carte sélectionnée
        }

        Carte carteChoisie = cartesFosse.get(choix);
        joueur.ajouterCarteDansMain(carteChoisie);
        joueur.getFosse().remove(carteChoisie);
        Affichage.afficherMessage("Vous avez ajouté la carte " + carteChoisie.getNom() + " à votre Main.");
    }

}
