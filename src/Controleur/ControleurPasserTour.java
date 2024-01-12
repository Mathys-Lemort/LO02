package Controleur;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import vue.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Cette classe est responsable de la gestion de l'événement de passage de tour dans le jeu.
 * Elle implémente l'interface EventHandler<ActionEvent> pour pouvoir gérer l'événement.
 */
public class ControleurPasserTour implements EventHandler<ActionEvent> {
    private Jeu vueJeu;
    private Partie modelePartie;

    /**
     * Constructeur de la classe ControleurPasserTour.
     * @param vueJeu l'instance de la classe Jeu qui représente l'interface utilisateur du jeu.
     * @param modelePartie l'instance de la classe Partie qui représente le modèle du jeu.
     */
    public ControleurPasserTour(Jeu vueJeu, Partie modelePartie) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
    }

    /**
     * Méthode qui gère l'événement de passage de tour.
     * Elle est appelée lorsque le joueur clique sur le bouton de passage de tour.
     * @param actionEvent l'événement de l'action.
     */
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

    /**
     * Méthode qui vérifie si le joueur peut passer son tour.
     * Un joueur peut passer son tour s'il n'a pas de cartes dans sa pile.
     * @param joueur le joueur dont on vérifie s'il peut passer son tour.
     * @return true si le joueur peut passer son tour, false sinon.
     */
    private boolean peutPasserTour(Joueur joueur) {
        return !joueur.getPile().isEmpty();
    }

    /**
     * Méthode qui traite le joueur suivant après le passage de tour.
     * Elle effectue les actions nécessaires pour le joueur suivant.
     * @param joueur le joueur suivant.
     */
    private void traiterJoueurSuivant(Joueur joueur) {
        verifierEtReincarnerSiNecessaire(joueur);
        modelePartie.piocherCarte(joueur);

        if (joueur instanceof JoueurBot) {
            gererBot((JoueurBot) joueur);
        } else {
            miseAJourInterfaceJoueur(joueur);
        }
    }

    /**
     * Méthode qui vérifie si le joueur doit être réincarné et le réincarne si nécessaire.
     * Un joueur doit être réincarné s'il n'a ni cartes dans sa main ni cartes dans sa pile.
     * @param joueur le joueur à vérifier et réincarner.
     */
    private void verifierEtReincarnerSiNecessaire(Joueur joueur) {
        if (joueur.getMain().isEmpty() && joueur.getPile().isEmpty()) {
            joueur.reincarnation();
        }
    }

    /**
     * Méthode qui gère les actions du joueur bot.
     * Elle met à jour l'interface du joueur actuel, fait jouer le bot et rétablit l'interface du joueur actuel.
     * @param bot le joueur bot.
     */
    private void gererBot(JoueurBot bot) {
        Joueur joueurActuel = modelePartie.getJoueurActif();
        miseAJourInterfaceJoueur(joueurActuel);
        modelePartie.setJoueurActif(bot);
        bot.jouerCoup();
        modelePartie.setJoueurActif(joueurActuel);
        miseAJourInterfaceJoueur(joueurActuel);
     }

    /**
     * Méthode qui met à jour l'interface du joueur.
     * Elle vérifie si le joueur doit être réincarné, pioche une carte pour le joueur et met à jour l'interface du jeu.
     * @param joueur le joueur dont l'interface doit être mise à jour.
     */
    private void miseAJourInterfaceJoueur(Joueur joueur) {
        verifierEtReincarnerSiNecessaire(joueur);
        modelePartie.piocherCarte(joueur);
        vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(), joueur.getVieFuture(), joueur.getOeuvres());
        modelePartie.setJoueurActif(joueur);
    }
}
