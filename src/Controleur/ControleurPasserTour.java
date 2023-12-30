package Controleur;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import vue.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ControleurPasserTour implements EventHandler<ActionEvent> {
    private Jeu vueJeu;
    private Partie modelePartie;

    public ControleurPasserTour(Jeu vueJeu, Partie modelePartie) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
    }

    @Override
    public void handle(ActionEvent actionEvent) {
        // Vérifier si le joueur actif a des cartes dans sa pile sinon il ne peut pas passer son tour 
        if (modelePartie.getJoueurActif() == modelePartie.getJoueur1()) {
            Joueur joueur = modelePartie.getJoueur1();
            if (joueur.getPile().isEmpty()) {
                Affichage.afficherMessage("Vous ne pouvez pas passer votre tour car vous n'avez pas de cartes dans votre pile.");
                return;
            }
        } else {
            Joueur joueur = modelePartie.getJoueur2();
            if (joueur.getPile().isEmpty()) {
                Affichage.afficherMessage("Vous ne pouvez pas passer votre tour car vous n'avez pas de cartes dans votre pile.");
                return;
            }
        } 
        // Vérifier si la pile du joueur actif n'est pas vide
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
