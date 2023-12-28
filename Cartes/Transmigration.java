package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

public class Transmigration extends Carte{

    public Transmigration() {
        super("Transmigration","bleu", 1, "Placez dans votre Main n’importe quelle carte de votre Vie Future.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Carte carte = joueur.getCarteVieFutureRandom();
        joueur.ajouterCarteDansMain(carte);
        joueur.getVieFuture().remove(carte);
        Affichage.afficherMessage("Vous avez ajouté la carte " + carte.getNom() + " à votre Main.");

    }
    
    
}
