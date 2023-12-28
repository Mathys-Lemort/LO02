package Cartes;
import java.util.ArrayList;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Sauvetage extends Carte {
    public Sauvetage() {
        super("Sauvetage","vert", 2, "Ajoutez à votre Main une des 3 dernières cartes de la Fosse.", false);
    }
    
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesFosse = joueur.getCartesFosse(3);
        Affichage.afficherMessage("Choisissez une carte à ajouter à votre Main :");
        for (int i = 0; i < cartesFosse.size(); i++) {
            Affichage.afficherOption(i+1, cartesFosse.get(i).getNom());
        }
        int choix = Partie.getInstance().getScanner().nextInt();
        Carte carte = cartesFosse.get(choix-1);
        joueur.ajouterCarteDansMain(carte);
        joueur.getFosse().remove(carte);
        Affichage.afficherMessage("Vous avez ajouté la carte " + carte.getNom() + " à votre Main.");

    }
    
}
