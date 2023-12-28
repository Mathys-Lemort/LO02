package Cartes;
import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Panique extends Carte{
    public Panique() {
        super("Panique","rouge", 1, "Défaussez la première carte de la Pile d'un joueur. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        adversaire.defausserCartePile();
        System.out.println("Vous avez defausser la première carte de la Pile de " + adversaire.getPseudo());
        Affichage.afficherMessage("Voulez vous jouer une autre carte ? (O/N)");
        if (Partie.getInstance().getScanner().nextLine().equals("O")) {
            Partie.getInstance().rejouer(joueur);
        }
    }
}
