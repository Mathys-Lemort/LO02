package Controleur;

import vue.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une
 * partie
 */
public class ControleurLancerPartie implements EventHandler<ActionEvent> {
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
    public ControleurLancerPartie(Jeu vueJeu, Core.Partie modelePartie,String joueur1Pseudo, String joueur2Pseudo) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        this.modelePartie.setJoueur1Pseudo(joueur1Pseudo);
        this.modelePartie.setJoueur2Pseudo(joueur2Pseudo);

    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas
     * une partie en cours
     * 
     * @param actionEvent l'événement action
     */

    @Override
    public void handle(ActionEvent actionEvent) {
        modelePartie.lancerDes();
        int lanceJoueur1 = modelePartie.getResultatLanceJoueur1();
        int lanceJoueur2 = modelePartie.getResultatLanceJoueur2();
        String joueurCommence = modelePartie.getJoueurCommence();
        this.modelePartie.setJoueurActif(modelePartie.getJoueurPseudo(joueurCommence));
        

        // Mettre à jour la vue avec les résultats
        vueJeu.afficherResultatDes(lanceJoueur1, lanceJoueur2, joueurCommence);
    }

}
