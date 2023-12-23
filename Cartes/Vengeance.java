package Cartes;
import Joueurs.Joueur;

public class Vengeance extends Carte{    
    public Vengeance() {
        super("Vengeance","rouge", 3, "Defaussez l’Oeuvre Exposée d’un rival.", false);
    }
    
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
