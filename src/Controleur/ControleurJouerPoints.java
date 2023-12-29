package Controleur;

import vue.Jeu;
import Cartes.Carte;
import Core.Partie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une
 * partie
 */
public class ControleurJouerPoints implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    /**
     * vue du jeu
     **/
    private Jeu vueJeu;
    private Core.Partie modelePartie;
    private Carte carte;

    /**
     * 
     * @param p vue du jeu
     * @param m modèle du jeu
     */
    public ControleurJouerPoints(Jeu vueJeu, Core.Partie modelePartie, Carte carte) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        this.carte = carte;
        
    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas
     * une partie en cours
     * 
     * @param actionEvent l'événement action
     */

    @Override
    public void handle(ActionEvent actionEvent) {
        System.out.println(carte.getNom());
        modelePartie.jouerCartePoints(carte);
        if (modelePartie.getJoueurActif() == modelePartie.getJoueur1()) {
            vueJeu.afficherEcranJoueur(modelePartie.getJoueur2().getPseudo(), modelePartie.getJoueur2().getMain(), modelePartie.getJoueur2().getPile(), modelePartie.getJoueur2().getFosse(), modelePartie.getJoueur2().getVieFuture());
            modelePartie.setJoueurActif(modelePartie.getJoueur2());
        } else {
            vueJeu.afficherEcranJoueur(modelePartie.getJoueur1().getPseudo(), modelePartie.getJoueur1().getMain(), modelePartie.getJoueur1().getPile(), modelePartie.getJoueur1().getFosse(), modelePartie.getJoueur1().getVieFuture());
            modelePartie.setJoueurActif(modelePartie.getJoueur1());
        }        
    }

}
