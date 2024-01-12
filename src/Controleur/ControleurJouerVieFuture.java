package Controleur;

import vue.Jeu;
import Cartes.Carte;
import Core.EtatPartie;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Cette classe est un contrôleur qui gère l'action de jouer une carte "Vie Future" dans le jeu.
 * Elle implémente l'interface EventHandler<ActionEvent> pour gérer l'événement de l'action.
 */
public class ControleurJouerVieFuture implements EventHandler<ActionEvent> {
    private Jeu vueJeu;
    private Core.Partie modelePartie;
    private Carte carte;

    /**
     * Constructeur de la classe ControleurJouerVieFuture.
     *
     * @param vueJeu       La vue du jeu.
     * @param modelePartie Le modèle de la partie.
     * @param carte        La carte "Vie Future" à jouer.
     */
    public ControleurJouerVieFuture(Jeu vueJeu, Core.Partie modelePartie, Carte carte) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        this.carte = carte;
    }

    /**
     * Gère l'événement de jouer une carte "Vie Future".
     *
     * @param actionEvent L'événement d'action.
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        modelePartie.jouerCarteVieFuture(carte);

        if (!verifierEtReincarnerSiNecessaire(modelePartie.getJoueurActif())) {
            vueJeu.afficherEcranAccueil(true);
            return;
        }
        Joueur joueurSuivant = modelePartie.getJoueurRival();
        traiterJoueurSuivant(joueurSuivant);
    }

    /**
     * Traite le joueur suivant après avoir joué une carte "Vie Future".
     *
     * @param joueur Le joueur suivant.
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
     * Vérifie si le joueur a besoin d'être réincarné et le réincarne si nécessaire.
     *
     * @param joueur Le joueur à vérifier.
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
     * Gère les actions du joueur bot.
     *
     * @param bot Le joueur bot.
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
     * Met à jour l'interface du joueur.
     *
     * @param joueur Le joueur dont l'interface doit être mise à jour.
     */
    private void miseAJourInterfaceJoueur(Joueur joueur) {
        verifierEtReincarnerSiNecessaire(joueur);
        modelePartie.piocherCarte(joueur);
        vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(), joueur.getVieFuture(), joueur.getOeuvres());
        modelePartie.setJoueurActif(joueur);
    }
}
