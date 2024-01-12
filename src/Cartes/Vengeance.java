package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

/**
 * La classe Vengeance représente une carte de type "Vengeance" dans le jeu.
 * Cette carte permet de défausser l'Oeuvre Exposée d'un joueur adverse.
 */
public class Vengeance extends Carte{    
    /**
     * Constructeur de la classe Vengeance.
     * Initialise les attributs de la carte avec les valeurs spécifiées.
     */
    public Vengeance() {
        super("Vengeance","rouge", 3, "Defaussez l’Oeuvre Exposée d’un rival.", false);
    }
    
    /**
     * Méthode qui effectue l'action de la carte Vengeance.
     * Défausse l'Oeuvre Exposée d'un joueur adverse.
     * 
     * @param joueur le joueur qui utilise la carte Vengeance
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Carte carte = adversaire.getOeuvreExposee();
        if (carte == null) {
            Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas d'Oeuvre Exposée à défausser.");
            return;
        }

        adversaire.defausserOeuvreChoisit(carte);
        
        Affichage.afficherMessage("Vous avez défaussé l'Oeuvre Exposée de " + adversaire.getPseudo() + " : " + carte.getNom() + ".");
    }
}
