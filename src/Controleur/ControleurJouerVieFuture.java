package Controleur;

import vue.Jeu;
import Cartes.Carte;
import Joueurs.Joueur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une
 * partie
 */
public class ControleurJouerVieFuture implements EventHandler<ActionEvent> {
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
    public ControleurJouerVieFuture(Jeu vueJeu, Core.Partie modelePartie, Carte carte) {
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
        modelePartie.jouerCarteVieFuture(carte);
        if (modelePartie.getJoueurActif() == modelePartie.getJoueur1()) {
            Joueur joueur = modelePartie.getJoueur2();
            if (joueur.getMain().isEmpty() && joueur.getPile().isEmpty()) {
                // Le joueur se réincarne
                joueur.reincarnation();
            }
            modelePartie.piocherCarte(joueur);
            vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(), joueur.getVieFuture());
            modelePartie.setJoueurActif(joueur);
        } else {
            Joueur joueur = modelePartie.getJoueur1();
            if (joueur.getMain().isEmpty() && joueur.getPile().isEmpty()) {
                // Le joueur se réincarne
                joueur.reincarnation();
            }
            modelePartie.piocherCarte(joueur);
            vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(), joueur.getVieFuture());
            modelePartie.setJoueurActif(joueur);
        }        
    }

}
