package Controleur;

import vue.Jeu;
import Cartes.Carte;
import Core.EtatPartie;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Cette classe est responsable de la gestion des événements liés au jeu des pouvoirs.
 * Elle implémente l'interface EventHandler<ActionEvent> pour pouvoir gérer les actions des joueurs.
 */
public class ControleurJouerPouvoirs implements EventHandler<ActionEvent> {
    private Jeu vueJeu;
    private Core.Partie modelePartie;
    private Carte carte;

    /**
     * Constructeur de la classe ControleurJouerPouvoirs.
     * @param vueJeu L'instance de la classe Jeu qui représente l'interface graphique du jeu.
     * @param modelePartie L'instance de la classe Partie qui représente le modèle du jeu.
     * @param carte La carte sur laquelle le joueur a cliqué pour jouer un pouvoir.
     */
    public ControleurJouerPouvoirs(Jeu vueJeu, Core.Partie modelePartie, Carte carte) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        this.carte = carte;
    }

    /**
     * Méthode qui gère l'action lorsqu'un joueur joue un pouvoir.
     * Elle est appelée lorsque l'événement ActionEvent est déclenché.
     * @param actionEvent L'événement ActionEvent qui a été déclenché.
     */
    @Override
    public void handle(ActionEvent actionEvent) {
        Joueur joueurActif = modelePartie.getJoueurActif();
        Joueur adversaire = modelePartie.getJoueurRival();

        joueurActif.getMain().remove(carte);
        vueJeu.rafraichirVueJoueur(joueurActif.getPseudo(), joueurActif.getMain(), joueurActif.getPile(), joueurActif.getFosse(), joueurActif.getVieFuture(), joueurActif.getOeuvres());

        joueurActif.demanderPrendreCarte(adversaire, carte);
        carte.action(joueurActif, adversaire);

        if (!modelePartie.getRejouer()) {
            Joueur joueurSuivant = modelePartie.getJoueurRival();
            if (!verifierEtReincarnerSiNecessaire(modelePartie.getJoueurActif())) {
                vueJeu.afficherEcranAccueil(true);
                return;
            }
            traiterJoueurSuivant(joueurSuivant);
        }
    }

    /**
     * Méthode privée qui traite le joueur suivant après que le joueur actif a joué un pouvoir.
     * @param joueur Le joueur suivant à traiter.
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
     * Méthode privée qui vérifie si le joueur doit être réincarné et le réincarne si nécessaire.
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
     * Méthode privée qui gère les actions du joueur bot.
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
     * @param joueur Le joueur dont l'interface doit être mise à jour.
     */
    private void miseAJourInterfaceJoueur(Joueur joueur) {
        verifierEtReincarnerSiNecessaire(joueur);
        modelePartie.piocherCarte(joueur);
        vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(), joueur.getVieFuture(), joueur.getOeuvres());
        modelePartie.setJoueurActif(joueur);
    }
}
