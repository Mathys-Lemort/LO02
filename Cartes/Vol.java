package Cartes;
import Joueurs.Joueur;

public class Vol extends Carte {
    public Vol() {
        super("Vol","bleu", 3, "Placez dans votre Main l Oeuvre Exposée d'un rival.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Carte carte = adversaire.getOeuvreExposee();
        joueur.ajouterCarteDansMain(carte);
        adversaire.defausserOeuvreChoisit(carte);
        System.out.println("Vous avez ajouté l'Oeuvre Exposée de " + adversaire.getPseudo() + " à votre Main.");
    }
    
}
