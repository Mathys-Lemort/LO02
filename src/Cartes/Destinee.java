package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

import java.util.ArrayList;

public class Destinee extends Carte {
    public Destinee() {
        super("Destinee", "bleu", 2,
                "Regardez les 3 premières cartes de la Source ; ajoutez-en jusqu’à 2 à votre Vie Future. Replacez le reste dans l'ordre souhaité",
                false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        ArrayList<Carte> cartesSource = Partie.getInstance().afficher3PremieresCartesSource();

        for (int i = 0; i < 2 && !cartesSource.isEmpty(); i++) {
            int choix = obtenirChoixCarte(cartesSource);
            if (choix != -1) {
                Carte carteChoisie = cartesSource.remove(choix);
                joueur.ajouterCarteDansVieFuture(carteChoisie);
                
                Affichage.afficherMessage("Vous avez ajouté " + carteChoisie.getNom() + " à votre Vie Future.");
                
            } else {
                break; // Aucune carte choisie
            }
        }
    }


}
