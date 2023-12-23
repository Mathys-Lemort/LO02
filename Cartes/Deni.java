package Cartes;
import Cartes.Carte;
import Joueurs.Joueur;

public class Deni extends Carte{    
    public Deni() {
        super("Deni","bleu", 2, "Defaussez une carte de votre Main. Copiez le pouvoir de cette carte.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
