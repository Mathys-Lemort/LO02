package Cartes;
import Core.Affichage;
import Joueurs.Joueur;
import Core.Partie;

public class Roulette extends Carte{
    public Roulette() {
        super("Roulette","rouge", 2, "Defaussez jusqu’à 2 cartes de votre Main. Vous pouvez ensuite puiser à la Source autant de carte(s) + 1.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        int carteDeffause = 0;
        for (int i = 0; i < 2; i++) {
            Affichage.afficherMessage("Choisissez une carte à défausser :");
            Affichage.afficherOption(0, "Ne pas défausser de carte");
            for (int j = 0; j < joueur.getMain().size(); j++) {
                Affichage.afficherOption(j+1, joueur.getMain().get(j).getNom());
            }
            int choix = Partie.getInstance().getScanner().nextInt();
            if (choix != 0) {
                joueur.defausserCarteChoisit(joueur.getMain().get(choix-1));
                carteDeffause++;
            }
        }

        Affichage.afficherMessage("Vous avez défaussé " + carteDeffause + " carte(s).");
        for (int i = 0; i < carteDeffause+1; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }     





        
    }
}
