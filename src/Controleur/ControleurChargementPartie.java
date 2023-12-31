package Controleur;

import Core.Partie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import vue.Jeu;

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
        // Votre logique de gestion du chargement de la partie
        if (chargerPartie) {
            partie.chargerPartie();
            jeu.afficherEcranJoueur(partie.getJoueurActif().getPseudo(), partie.getJoueurActif().getMain(),
                    partie.getJoueurActif().getPile(), partie.getJoueurActif().getFosse(),
                    partie.getJoueurActif().getVieFuture(), partie.getJoueurActif().getOeuvres());
            // Afficvher les info du joueur 
            System.out.println("Chargement de la partie");
            System.out.println("Joueur actif : " + partie.getJoueurActif().getPseudo());
            System.out.println("Joueur rival : " + partie.getJoueurRival().getPseudo());
            // Afficher les cartes du joueur actif
            System.out.println("Cartes du joueur actif : ");
            partie.getJoueurActif().getMain().forEach(carte -> System.out.println(carte.getNom()));
        } else {
            jeu.demanderModeJeu();
        }
    }
}
