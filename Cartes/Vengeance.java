package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

public class Vengeance extends Carte{    
    public Vengeance() {
        super("Vengeance","rouge", 3, "Defaussez l’Oeuvre Exposée d’un rival.", false);
    }
    
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Carte carte = adversaire.getOeuvreExposee();
        adversaire.defausserOeuvreChoisit(carte);
        Affichage.afficherMessage("Vous avez défaussé l'Oeuvre Exposée de " + adversaire.getPseudo() + ".");

    }
    
}
