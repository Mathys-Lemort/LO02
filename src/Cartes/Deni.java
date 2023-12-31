package Cartes;

import Core.Affichage;
import Joueurs.Joueur;


public class Deni extends Carte {
    public Deni() {
        super("Deni", "bleu", 2, "Defaussez une carte de votre Main. Copiez le pouvoir de cette carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        // Vérifier si le joueur a des cartes en main
        if (joueur.getMain().isEmpty()) {
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage("Vous n'avez pas de cartes en main.");
            }
            return;
        }

        int choix = obtenirChoixCarte(joueur.getMain());
        if (choix != -1) {
            Carte carte = joueur.getMain().get(choix);
            joueur.defausserCarteChoisit(carte);
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage("Vous avez défaussé la carte " + carte.getNom() + " et copiez son pouvoir.");
            }
            carte.action(joueur, adversaire);
        }
    }
}
