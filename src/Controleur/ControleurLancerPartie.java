package Controleur;

import vue.Jeu;
import Core.Partie.Mode;
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

   
    public ControleurLancerPartie(Jeu vueJeu, Core.Partie modelePartie,String joueur1Pseudo, String joueur2Pseudo) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        this.modelePartie.setJoueur1Pseudo(joueur1Pseudo);
        this.modelePartie.setJoueur2Pseudo(joueur2Pseudo);
        this.modelePartie.setMode(Mode.GRAPHIQUE);

    }

   

    @Override
    public void handle(ActionEvent actionEvent) {
        modelePartie.lancerDes();
        int lanceJoueur1 = modelePartie.getResultatLanceJoueur1();
        int lanceJoueur2 = modelePartie.getResultatLanceJoueur2();
        String joueurCommence = modelePartie.getJoueurCommence();
        this.modelePartie.setJoueurActif(modelePartie.getJoueurPseudo(joueurCommence));
        

        vueJeu.afficherResultatDes(lanceJoueur1, lanceJoueur2, joueurCommence);
    }

}
