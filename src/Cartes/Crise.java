package Cartes;

import Core.Affichage;
import Joueurs.Joueur;



public class Crise extends Carte {
    public Crise() {
        super("Crise", "rouge", 2, "Le rival de votre choix défausse\nune de ses Oeuvres.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getOeuvres().isEmpty()) {
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage("Votre adversaire n'a pas d'œuvre, tant pis pour vous !");
            }
            return;
        }

        int choix = obtenirChoixCarte(adversaire.getOeuvres());
        if (choix != -1) {
            Carte carte = adversaire.getOeuvres().get(choix);
            adversaire.defausserOeuvreChoisit(carte);
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage(adversaire.getPseudo() + " a défaussé l'œuvre " + carte.getNom() + ".");
            }
        }
    }

    
}
