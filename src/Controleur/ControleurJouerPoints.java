package Controleur;

import vue.Jeu;
import Cartes.Carte;
import Core.EtatPartie;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Cette classe est responsable de la gestion de l'événement de jouer une carte
 * avec des points dans le jeu.
 * Elle implémente l'interface EventHandler<ActionEvent> pour gérer l'événement
 * de manière appropriée.
 */
public class ControleurJouerPoints implements EventHandler<ActionEvent> {

    private Jeu vueJeu;
    private Core.Partie modelePartie;
    private Carte carte;

    /**
     * Constructeur de la classe ControleurJouerPoints.
     * 
     * @param vueJeu       L'instance de la classe Jeu qui représente l'interface
     *                     utilisateur du jeu.
     * @param modelePartie L'instance de la classe Partie qui représente le modèle
     *                     de jeu.
     * @param carte        La carte à jouer avec des points.
     */
    public ControleurJouerPoints(Jeu vueJeu, Core.Partie modelePartie, Carte carte) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        this.carte = carte;
    }

    /**
     * Méthode qui gère l'événement de jouer une carte avec des points.
     * Elle appelle la méthode jouerCartePoints du modèle de jeu et traite le joueur
     * suivant.
     * 
     * @param actionEvent L'événement de l'action.
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        modelePartie.jouerCartePoints(carte);

        if (!verifierEtReincarnerSiNecessaire(modelePartie.getJoueurActif())) {
            vueJeu.afficherEcranAccueil(true);
            return;
        }
        Joueur joueurSuivant = modelePartie.getJoueurRival();
        traiterJoueurSuivant(joueurSuivant);
    }

    /**
     * Méthode privée qui traite le joueur suivant.
     * Elle vérifie et réincarne le joueur si nécessaire, pioche une carte pour le
     * joueur,
     * et met à jour l'interface du joueur en fonction du type de joueur.
     * 
     * @param joueur Le joueur suivant à traiter.
     */
    private void traiterJoueurSuivant(Joueur joueur) {
        if (! verifierEtReincarnerSiNecessaire(joueur)) {
            return;
        }
        modelePartie.piocherCarte(joueur);

        if (joueur instanceof JoueurBot) {
            gererBot((JoueurBot) joueur);
        } else {
            miseAJourInterfaceJoueur(joueur);
        }
    }

    /**
     * Méthode privée qui vérifie et réincarne le joueur si nécessaire.
     * Elle vérifie si la main et la pile du joueur sont vides, et réincarne le
     * joueur si c'est le cas.
     * 
     * @param joueur Le joueur à vérifier et réincarner.
     */
    private Boolean verifierEtReincarnerSiNecessaire(Joueur joueur) {
        if (joueur.getMain().isEmpty() && joueur.getPile().isEmpty()) {
            joueur.reincarnation();
            System.out.println("Réincarnation de " + joueur.getPseudo());
            if (modelePartie.getEtatPartie().equals(EtatPartie.TERMINE)) {
                return false;
            }

        }
        return true;

    }

    /**
     * Méthode privée qui gère le joueur bot.
     * Elle met à jour l'interface du joueur actuel, définit le joueur bot comme
     * joueur actif,
     * fait jouer le bot, rétablit le joueur actuel et met à jour son interface.
     * 
     * @param bot Le joueur bot à gérer.
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
     * Méthode privée qui met à jour l'interface du joueur.
     * Elle vérifie et réincarne le joueur si nécessaire, pioche une carte pour le
     * joueur,
     * et affiche l'écran du joueur avec son pseudo, sa main, sa pile, sa fosse, sa
     * vie future et ses oeuvres.
     * Elle définit également le joueur comme joueur actif dans le modèle de jeu.
     * 
     * @param joueur Le joueur dont l'interface doit être mise à jour.
     */
    private void miseAJourInterfaceJoueur(Joueur joueur) {
        if (!verifierEtReincarnerSiNecessaire(joueur)) {
            return;
        }
            modelePartie.piocherCarte(joueur);
            vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(),
                    joueur.getVieFuture(), joueur.getOeuvres());
            modelePartie.setJoueurActif(joueur);
        

    }
}
