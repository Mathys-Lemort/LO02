package Cartes;

import Core.Affichage;
import Joueurs.Joueur;
import Core.Partie;


public class Roulette extends Carte {
    public Roulette() {
        super("Roulette", "rouge", 2, "Défaussez jusqu’à 2 cartes de votre Main. Vous pouvez ensuite puiser à la Source autant de carte(s) + 1.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        int carteDefaussee = 0;
        
        for (int i = 0; i < 2; i++) {
            if (joueur.getMain().isEmpty()) {
                break; // Pas de cartes à défausser
            }

            int choix = obtenirChoixCarte(joueur.getMain());
            if (choix == -1) {
                break;
            }

            joueur.defausserCarteChoisit(joueur.getMain().get(choix));
            carteDefaussee++;
        }
        
        Affichage.afficherMessage("Vous avez défaussé " + carteDefaussee + " carte(s).");
        
        for (int i = 0; i <= carteDefaussee; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }
    }

}
