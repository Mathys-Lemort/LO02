package Controleur;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
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
        Joueur joueurActif = modelePartie.getJoueurActif();
        if (!peutPasserTour(joueurActif)) {
            Affichage.afficherMessage("Vous ne pouvez pas passer votre tour car vous n'avez pas de cartes dans votre pile.");
            return;
        }

        Joueur joueurSuivant = modelePartie.getJoueurRival();
        traiterJoueurSuivant(joueurSuivant);
    }

    private boolean peutPasserTour(Joueur joueur) {
        return !joueur.getPile().isEmpty();
    }

    private void traiterJoueurSuivant(Joueur joueur) {
        verifierEtReincarnerSiNecessaire(joueur);
        modelePartie.piocherCarte(joueur);

        if (joueur instanceof JoueurBot) {
            gererBot((JoueurBot) joueur);
        } else {
            miseAJourInterfaceJoueur(joueur);
        }
    }

    private void verifierEtReincarnerSiNecessaire(Joueur joueur) {
        if (joueur.getMain().isEmpty() && joueur.getPile().isEmpty()) {
            joueur.reincarnation();
        }
    }

    private void gererBot(JoueurBot bot) {
        Joueur joueurActuel = modelePartie.getJoueurActif();
        miseAJourInterfaceJoueur(joueurActuel);
        modelePartie.setJoueurActif(bot);
        bot.jouerCoup();
        modelePartie.setJoueurActif(joueurActuel);
        miseAJourInterfaceJoueur(joueurActuel);
     }

    private void miseAJourInterfaceJoueur(Joueur joueur) {
        verifierEtReincarnerSiNecessaire(joueur);
        modelePartie.piocherCarte(joueur);
        vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(), joueur.getVieFuture(), joueur.getOeuvres());
        modelePartie.setJoueurActif(joueur);
    }
}
