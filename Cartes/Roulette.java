package Cartes;
import Joueurs.Joueur;

public class Roulette extends Carte{
    public Roulette() {
        super("Roulette","rouge", 2, "Defaussez jusqu’à 2 cartes de votre Main. Vous pouvez ensuite puiser à la Source autant de carte(s) + 1.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
