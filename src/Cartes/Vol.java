package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

public class Vol extends Carte {
    public Vol() {
        super("Vol","bleu", 3, "Placez dans votre Main l Oeuvre Exposée d'un rival.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Carte carte = adversaire.getOeuvreExposee();
        if (carte == null) {
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas d'Oeuvre Exposée à ajouter à votre Main.");
            }
            return;
        }

        joueur.ajouterCarteDansMain(carte);
        adversaire.defausserOeuvreChoisit(carte);
        if (!(joueur instanceof Joueurs.JoueurBot)) {

        Affichage.afficherMessage("Vous avez ajouté l'Oeuvre Exposée de " + adversaire.getPseudo() + ", \"" + carte.getNom() + "\", à votre Main.");
        }
    }

    
}
