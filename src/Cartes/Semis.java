package Cartes;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Semis extends Carte {
    public Semis() {
        super("Semis", "vert", 2, "Puisez 2 cartes à la Source, puis placez sur votre Vie Future 2 cartes de votre Main.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        // Piocher 2 cartes à la Source
        for (int i = 0; i < 2; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }
                    Affichage.afficherMessage("Vous avez pioché 2 cartes à la Source.");
        

        // Placer jusqu'à 2 cartes de la main dans la Vie Future
        for (int i = 0; i < 2; i++) {
            if (joueur.getMain().isEmpty()) {
                                Affichage.afficherMessage("Vous n'avez plus de cartes en main pour placer dans votre Vie Future.");
                
                break;
            }

            int choix = obtenirChoixCarte(joueur.getMain());
            if (choix == -1) {
                continue;
            }

            Carte carte = joueur.getMain().get(choix);
            joueur.ajouterCarteDansVieFuture(carte);
            joueur.getMain().remove(carte);
            
            Affichage.afficherMessage("Vous avez placé la carte " + carte.getNom() + " dans votre Vie Future.");
            
        }
    }
}
