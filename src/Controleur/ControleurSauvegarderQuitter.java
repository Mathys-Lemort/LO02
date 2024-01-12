package Controleur;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import vue.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import Core.Sauvegarde;


public class ControleurSauvegarderQuitter implements EventHandler<ActionEvent> {
    private Jeu vueJeu;
    private Partie modelePartie;

    public ControleurSauvegarderQuitter(Jeu vueJeu, Partie modelePartie) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
    }

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
