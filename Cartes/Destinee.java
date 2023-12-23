package Cartes;
import Joueurs.Joueur;

public class Destinee extends Carte {
    public Destinee() {
        super("Deni","bleu", 2, "Regardez les 3 premières cartes de la Source ; ajoutez-en jusqu’à 2 à votre Vie Future. Replacez le reste dans l'ordre souhaité", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
