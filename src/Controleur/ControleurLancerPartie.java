package Controleur;

import vue.Jeu;
import Core.Partie.Mode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Cette classe est responsable de la gestion de l'événement de lancement de la partie.
 * Elle implémente l'interface EventHandler<ActionEvent> pour pouvoir gérer l'événement.
 */
public class ControleurLancerPartie implements EventHandler<ActionEvent> {
    /**
     * La vue du jeu.
     */
    private Jeu vueJeu;
    
    /**
     * Le modèle de la partie.
     */
    private Core.Partie modelePartie;

    /**
     * Constructeur de la classe ControleurLancerPartie.
     * 
     * @param vueJeu La vue du jeu.
     * @param modelePartie Le modèle de la partie.
     * @param joueur1Pseudo Le pseudo du joueur 1.
     * @param joueur2Pseudo Le pseudo du joueur 2.
     */
    public ControleurLancerPartie(Jeu vueJeu, Core.Partie modelePartie, String joueur1Pseudo, String joueur2Pseudo) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        this.modelePartie.setJoueur1Pseudo(joueur1Pseudo);
        this.modelePartie.setJoueur2Pseudo(joueur2Pseudo);
        this.modelePartie.setMode(Mode.GRAPHIQUE);
    }

    /**
     * Gère l'événement de lancement de la partie.
     * 
     * @param actionEvent L'événement de lancement de la partie.
     */
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
