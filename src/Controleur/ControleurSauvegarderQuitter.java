package Controleur;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import vue.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Core.Sauvegarde;


/**
 * Cette classe implémente l'interface EventHandler<ActionEvent> et représente le contrôleur pour l'action de sauvegarder et quitter le jeu.
 */
public class ControleurSauvegarderQuitter implements EventHandler<ActionEvent> {
    private Jeu vueJeu;
    private Partie modelePartie;

    /**
     * Constructeur de la classe ControleurSauvegarderQuitter.
     * @param vueJeu l'instance de la classe Jeu qui représente la vue du jeu.
     * @param modelePartie l'instance de la classe Partie qui représente le modèle du jeu.
     */
    public ControleurSauvegarderQuitter(Jeu vueJeu, Partie modelePartie) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
    }

    /**
     * Méthode qui gère l'événement lorsqu'on clique sur le bouton de sauvegarde et quitter.
     * Si le joueur actif est un bot, affiche un message d'erreur et retourne.
     * Sinon, sauvegarde la partie et ferme la fenêtre du jeu.
     * @param actionEvent l'événement associé au clic sur le bouton.
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Joueur joueurActif = modelePartie.getJoueurActif();
        if (joueurActif instanceof JoueurBot) {
            Affichage.afficherMessage("Vous ne pouvez pas sauvegarder et quitter pendant le tour d'un bot.");
            return;
        }
        Sauvegarde.sauvegarderPartie(modelePartie);
        vueJeu.fermerFenetre();
    }
}
