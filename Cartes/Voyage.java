package Cartes;
import Joueurs.Joueur;

public class Voyage extends Carte{
    public Voyage() {
        super("Voyage","vert", 3, "Puisez 3 cartes à la Source. Vous pouvez ensuite jouer une autre carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
            joueur.ajouterCarte();
            joueur.ajouterCarte();
            joueur.ajouterCarte();
            
    }
    
}
