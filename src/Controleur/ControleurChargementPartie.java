package Controleur;

import Core.Partie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.Jeu;
import Core.Sauvegarde;


public class ControleurChargementPartie implements EventHandler<ActionEvent> {
    private Jeu jeu;
    private Partie partie;
    private Boolean chargerPartie;

    public ControleurChargementPartie(Jeu jeu, Partie partie,Boolean chargerPartie) {
        this.jeu = jeu;
        this.partie = partie;
        this.chargerPartie = chargerPartie;
    }

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
