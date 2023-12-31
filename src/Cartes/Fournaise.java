package Cartes;
import Joueurs.Joueur;

import java.util.List;

import Core.Affichage;

public class Fournaise extends Carte {
    public Fournaise() {
        super("Fournaise","rouge", 2, "Defaussez les 2 premières cartes de la Vie Future d'un rival.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> vieFutureAdversaire = adversaire.getVieFuture();

        if (vieFutureAdversaire.size() < 2) {
            
            Affichage.afficherMessage("Votre adversaire n'a pas assez de cartes dans sa Vie Future.");
            
            return;
        }
        
        Affichage.afficherMessage("Les 2 premières cartes de la Vie Future de " + adversaire.getPseudo() + " sont :");
        
        for (int i = 0; i < 2; i++) {
            Affichage.afficherMessage((i + 1) + ". " + vieFutureAdversaire.get(i).getNom());
        }

        adversaire.defausserCarteVieFutureChiffre(2);
        
        Affichage.afficherMessage("Vous avez défaussé les 2 premières cartes de la Vie Future de " + adversaire.getPseudo() + ".");
        
    }

    
}