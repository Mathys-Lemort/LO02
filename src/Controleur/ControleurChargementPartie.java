package Controleur;

import Core.Partie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.Jeu;
import Core.Sauvegarde;


/**
 * Cette classe est responsable de la gestion de l'événement de chargement d'une partie.
 * Elle implémente l'interface EventHandler<ActionEvent> pour pouvoir gérer l'événement.
 */
public class ControleurChargementPartie implements EventHandler<ActionEvent> {
    private Jeu jeu;
    private Partie partie;
    private Boolean chargerPartie;

    /**
     * Constructeur de la classe ControleurChargementPartie.
     * @param jeu Le jeu en cours.
     * @param partie La partie en cours.
     * @param chargerPartie Un booléen indiquant si la partie doit être chargée ou non.
     */
    public ControleurChargementPartie(Jeu jeu, Partie partie, Boolean chargerPartie) {
        this.jeu = jeu;
        this.partie = partie;
        this.chargerPartie = chargerPartie;
    }

    /**
     * Méthode appelée lorsqu'un événement de chargement de partie est déclenché.
     * Si la partie doit être chargée, elle appelle la méthode chargerPartie() de l'objet Partie,
     * puis affiche les informations du joueur actif et les cartes de sa main.
     * Sinon, elle demande le mode de jeu au jeu en cours.
     * @param event L'événement de chargement de partie.
     */
    @Override
    public void handle(ActionEvent event) {
        if (chargerPartie) {
            Sauvegarde.chargerPartie(partie);
            jeu.afficherEcranJoueur(partie.getJoueurActif().getPseudo(), partie.getJoueurActif().getMain(),
                    partie.getJoueurActif().getPile(), partie.getJoueurActif().getFosse(),
                    partie.getJoueurActif().getVieFuture(), partie.getJoueurActif().getOeuvres());
        } else {
            jeu.demanderModeJeu();
        }
    }
}
