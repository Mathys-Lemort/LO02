package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

public class Transmigration extends Carte{

    public Transmigration() {
        super("Transmigration","bleu", 1, "Placez dans votre Main n’importe quelle carte de votre Vie Future.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (joueur.getVieFuture().isEmpty()) {
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage(joueur.getPseudo() + " n'a pas de cartes dans sa Vie Future.");
            }
            return;
        }

        Carte carte = joueur.getCarteVieFutureRandom();
        if (carte != null) {
            joueur.ajouterCarteDansMain(carte);
            joueur.getVieFuture().remove(carte);
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage("Vous avez ajouté la carte " + carte.getNom() + " à votre Main.");
            }
        } else {
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage("Erreur lors de la récupération d'une carte de la Vie Future.");
            }
        }
    }

    
    
}
