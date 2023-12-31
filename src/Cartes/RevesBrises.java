package Cartes;

import java.util.List;

import Core.Affichage;
import Joueurs.Joueur;

public class RevesBrises extends Carte {

    public RevesBrises() {
        super("Reves Brises", "bleu", 2, "Placez la première carte de la Vie Future d'un rival sur la vôtre.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> vieFutureAdversaire = adversaire.getVieFuture();

        if (vieFutureAdversaire.isEmpty()) {
            
                Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas de cartes dans sa Vie Future.");
            
            return;
        }

        Carte carteAjoutee = vieFutureAdversaire.get(0);
        joueur.ajouterCarteDansVieFuture(carteAjoutee);
        vieFutureAdversaire.remove(0);
        
            Affichage.afficherMessage("Vous avez ajouté la carte " + carteAjoutee.getNom() + " de la Vie Future de "
                    + adversaire.getPseudo() + " à votre Vie Future.");
        
    }

}
