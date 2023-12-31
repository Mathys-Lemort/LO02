package Controleur;

import vue.Jeu;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une
 * partie
 */
public class ControleurDebutJeu implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    /**
     * vue du jeu
     **/
    private Jeu vueJeu;
    private Core.Partie modelePartie;

    /**
     * 
     * @param p vue du jeu
     * @param m modèle du jeu
     */
    public ControleurDebutJeu(Jeu vueJeu, Core.Partie modelePartie) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        
    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas
     * une partie en cours
     * 
     * @param actionEvent l'événement action
     */

    @Override
    public void handle(ActionEvent actionEvent) {
        modelePartie.initialiserPartie();
        Joueur joueur = modelePartie.getJoueurActif();
        modelePartie.piocherCarte(joueur);

        // Si le joueur est un bot, on joue son coup
        if (joueur instanceof Joueurs.JoueurBot) {
            ((JoueurBot) joueur).jouerCoup();
            Joueur joueurSuivant = modelePartie.getJoueurRival();
            modelePartie.piocherCarte(joueurSuivant);
        vueJeu.afficherEcranJoueur(joueurSuivant.getPseudo(), joueurSuivant.getMain(), joueurSuivant.getPile(), joueurSuivant.getFosse(), joueurSuivant.getVieFuture(), joueurSuivant.getOeuvres());
        } else {
            vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(), joueur.getVieFuture(), joueur.getOeuvres());

        }

    }

}
