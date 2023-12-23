package Cartes;
import Cartes.Carte;
import Joueurs.Joueur;

public class RevesBrises extends Carte {

    public RevesBrises() {
        super("Reves Brises","bleu", 2, "Placez la première carte de la Vie Future d'un rival sur la vôtre.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
