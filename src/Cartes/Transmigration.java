package Cartes;
import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * La classe Transmigration représente une carte de jeu appelée "Transmigration".
 * Cette carte permet au joueur de placer une carte de sa Vie Future dans sa Main.
 * 
 * @see Carte
 */
public class Transmigration extends Carte{

    /**
     * Constructeur de la classe Transmigration.
     * Initialise les attributs de la carte "Transmigration".
     */
    public Transmigration() {
        super("Transmigration","bleu", 1, "Placez dans votre Main n’importe quelle carte de votre Vie Future.", false);
    }

    /**
     * Méthode qui effectue l'action de la carte "Transmigration".
     * Si le joueur n'a pas de cartes dans sa Vie Future, un message est affiché.
     * Sinon, une carte aléatoire est sélectionnée dans la Vie Future du joueur et ajoutée à sa Main.
     * 
     * @param joueur le joueur qui joue la carte "Transmigration"
     * @param adversaire le joueur adverse
     */
    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        if (joueur.getVieFuture().isEmpty()) {
            
            Affichage.afficherMessage(joueur.getPseudo() + " n'a pas de cartes dans sa Vie Future.");
            
            return;
        }

        Carte carte = joueur.getCarteVieFutureRandom();
        if (carte != null) {
            joueur.ajouterCarteDansMain(carte);
            joueur.getVieFuture().remove(carte);
            
            Affichage.afficherMessage("Vous avez ajouté la carte " + carte.getNom() + " à votre Main.");
            
            
        } else {
            
            Affichage.afficherMessage("Erreur lors de la récupération d'une carte de la Vie Future.");
            
        }
                    Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}
