package Cartes;
import java.util.ArrayList;

import Core.Affichage;
import Joueurs.Joueur;
import Core.Partie;

public class Recyclage extends Carte {
    public Recyclage() {
        super("Recyclage","vert", 1, "Ajoutez à votre Vie Future une des 3 dernières cartes de la Fosse.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesFosse = joueur.getCartesFosse(3);
        Affichage.afficherMessage("Choisissez une carte à ajouter à votre Vie Future :");
        for (int i = 0; i < cartesFosse.size(); i++) {
            Affichage.afficherOption(i+1, cartesFosse.get(i).getNom());
        }
        int choix = Partie.getInstance().getScanner().nextInt();
        Carte carte = cartesFosse.get(choix-1);
        joueur.ajouterCarteDansVieFuture(carte);
        joueur.getFosse().remove(carte);
        Affichage.afficherMessage("Vous avez ajouté la carte " + carte.getNom() + " à votre Vie Future.");
    }
    
}