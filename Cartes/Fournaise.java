package Cartes;
import Joueurs.Joueur;
import Core.Affichage;

public class Fournaise extends Carte {
    public Fournaise() {
        super("Fournaise","rouge", 2, "Defaussez les 2 premières cartes de la Vie Future d'un rival.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        adversaire.defausserCarteVieFutureChiffre(2);
        Affichage.afficherMessage("Vous avez defausser les 2 premières cartes de la Vie Future de " + adversaire.getPseudo());
    }
    
}