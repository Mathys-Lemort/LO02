package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

/**
 * La classe Vol représente une carte de type "Vol" dans le jeu.
 * Cette carte permet de placer dans la main une Oeuvre Exposée d'un rival.
 */
public class Vol extends Carte {
    /**
     * Constructeur de la classe Vol.
     * Initialise les attributs de la carte "Vol".
     */
    public Vol() {
        super("Vol","bleu", 3, "Placez dans votre Main l Oeuvre Exposée d'un rival.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte "Vol".
     * Ajoute l'Oeuvre Exposée d'un adversaire dans la main du joueur courant.
     * Si l'adversaire n'a pas d'Oeuvre Exposée, affiche un message d'erreur.
     * @param joueur le joueur courant
     * @param adversaire l'adversaire du joueur courant
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Carte carte = adversaire.getOeuvreExposee();
        if (carte == null) {
            Affichage.afficherMessage(adversaire.getPseudo() + " n'a pas d'Oeuvre Exposée à ajouter à votre Main.");
            return;
        }

        joueur.ajouterCarteDansMain(carte);
        adversaire.defausserOeuvreChoisit(carte);
        
        Affichage.afficherMessage("Vous avez ajouté l'Oeuvre Exposée de " + adversaire.getPseudo() + ", \"" + carte.getNom() + "\", à votre Main.");
    }
}
