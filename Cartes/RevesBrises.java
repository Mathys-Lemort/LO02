package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

public class RevesBrises extends Carte {

    public RevesBrises() {
        super("Reves Brises","bleu", 2, "Placez la première carte de la Vie Future d'un rival sur la vôtre.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        joueur.ajouterCarteDansVieFuture(adversaire.getCarteVieFuture(0));
        adversaire.getVieFuture().remove(0);
        Affichage.afficherMessage("Vous avez ajouté la première carte de la Vie Future de " + adversaire.getPseudo() + " à votre Vie Future.");
    }
    
}
