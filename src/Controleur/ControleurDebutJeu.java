package Controleur;

import vue.Jeu;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


/**
 * Cette classe est responsable de la gestion de l'événement de début de jeu.
 * Elle implémente l'interface EventHandler<ActionEvent> pour pouvoir gérer l'événement.
 */
public class ControleurDebutJeu implements EventHandler<ActionEvent> {
 
    private Jeu vueJeu;
    private Core.Partie modelePartie;


    /**
     * Constructeur de la classe ControleurDebutJeu.
     * @param vueJeu L'instance de la classe Jeu qui représente la vue du jeu.
     * @param modelePartie L'instance de la classe Partie qui représente le modèle du jeu.
     */
    public ControleurDebutJeu(Jeu vueJeu, Core.Partie modelePartie) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
    }


    /**
     * Méthode qui gère l'événement de début de jeu.
     * Elle initialise la partie, pioche une carte pour le joueur actif,
     * puis effectue les actions correspondantes en fonction du type de joueur actif.
     * @param actionEvent L'événement de début de jeu.
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        modelePartie.initialiserPartie();
        Joueur joueur = modelePartie.getJoueurActif();
        modelePartie.piocherCarte(joueur);

        if (joueur instanceof Joueurs.JoueurBot) {
            modelePartie.setJoueurActif(joueur);
            Joueur joueurSuivant = modelePartie.getJoueurRival();
            vueJeu.afficherEcranJoueur(joueurSuivant.getPseudo(), joueurSuivant.getMain(), joueurSuivant.getPile(),
                    joueurSuivant.getFosse(), joueurSuivant.getVieFuture(), joueurSuivant.getOeuvres());

            ((JoueurBot) joueur).jouerCoup();
            modelePartie.setJoueurActif(joueurSuivant);
            modelePartie.piocherCarte(joueurSuivant);
            vueJeu.afficherEcranJoueur(joueurSuivant.getPseudo(), joueurSuivant.getMain(), joueurSuivant.getPile(),
                    joueurSuivant.getFosse(), joueurSuivant.getVieFuture(), joueurSuivant.getOeuvres());
        } else {
            vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(),
                    joueur.getVieFuture(), joueur.getOeuvres());
        }
    }
}
