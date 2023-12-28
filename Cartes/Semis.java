package Cartes;
import Core.Partie;
import Joueurs.Joueur;
import Core.Affichage;

public class Semis extends Carte {
    public Semis() {
        super("Semis","vert", 2, "Puisez 2 cartes à la Source, puis placez sur votre Vie Future 2 cartes de votre Main.", false);
    }


    
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        for (int i = 0; i < 2; i++) {
            Partie.getInstance().piocherSourceMain(joueur);
        }
        Affichage.afficherMessage("Vous avez pioché 2 cartes à la Source.");
        for (int i = 0; i < 2; i++) {
            Affichage.afficherMessage("Choisissez une carte à placer dans votre Vie Future :");
            for (int j = 0; j < joueur.getMain().size(); j++) {
                Affichage.afficherOption(j+1, joueur.getMain().get(j).getNom());
            }
            int choix = Partie.getInstance().getScanner().nextInt();
            joueur.ajouterCarteDansVieFuture(joueur.getMain().get(choix-1));
            joueur.getMain().remove(choix-1);
        }

        Affichage.afficherMessage("Vous avez placé 2 cartes de votre Main dans votre Vie Future.");
    }
}
