package Cartes;
import Joueurs.Joueur;
import Core.Affichage;
import Core.Partie;


public class CoupdOeil extends Carte {
    public CoupdOeil() {
        super("Coup d'Œil", "bleu", 1, "Regardez la Main d’un rival. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (adversaire.getMain().isEmpty()) {
            Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas de cartes en main.");
            return;
        } else {
        adversaire.afficherMain();
        }

        if (demanderJouerAutreCarte()) {
            Partie.getInstance().rejouer(joueur);
        } else {
            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
        }
    }

}