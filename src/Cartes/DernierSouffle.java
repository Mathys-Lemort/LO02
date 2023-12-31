package Cartes;

import Core.Affichage;
import Joueurs.Joueur;


public class DernierSouffle extends Carte {
    public DernierSouffle() {
        super("Dernier Souffle","rouge", 1, "Le joueur de votre choix défausse une carte de sa Main.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        // Vérifier si l'adversaire a des cartes en main
        if (adversaire.getMain().isEmpty()) {
            
            Affichage.afficherMessage("Votre adversaire n'a pas de carte en main.");
            
            return;
        }

        int choix = obtenirChoixCarte(adversaire.getMain());
        if (choix != -1) {
            Carte carte = adversaire.getMain().get(choix);
            adversaire.defausserCarteChoisit(carte);
            
            Affichage.afficherMessage(adversaire.getPseudo() + " a défaussé la carte " + carte.getNom() + ".");
            
        }
    }


}
