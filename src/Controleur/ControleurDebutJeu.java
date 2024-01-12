package Controleur;

import vue.Jeu;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ControleurDebutJeu implements EventHandler<ActionEvent> {
 
    private Jeu vueJeu;
    private Core.Partie modelePartie;


    public ControleurDebutJeu(Jeu vueJeu, Core.Partie modelePartie) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;

    }


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
