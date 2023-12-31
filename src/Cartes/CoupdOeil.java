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
            // Si c'est un bot, on affiche pas sa main
            if (!(adversaire instanceof Joueurs.JoueurBot)) {
        adversaire.afficherMain();
            }
        }

        if (demanderJouerAutreCarte()) {
            // Si on est en mode graphique alors mettre le boolean rejouer à true pour qu'on puisse rejouer
            if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
                Partie.getInstance().setRejouer(true);
            }
            else{
            Partie.getInstance().rejouer(joueur);
            }
        } else {
            
            Affichage.afficherMessage("Vous avez choisi de ne pas jouer de carte.");
            
        }
    
    }

}
