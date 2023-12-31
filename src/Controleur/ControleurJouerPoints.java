package Controleur;

import vue.Jeu;
import Cartes.Carte;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Contrôleur à activer lorsque l'on clique sur le bouton rejouer ou Lancer une
 * partie
 */
public class ControleurJouerPoints implements EventHandler<ActionEvent> {
    /**
     * modèle du jeu
     */
    /**
     * vue du jeu
     **/
    private Jeu vueJeu;
    private Core.Partie modelePartie;
    private Carte carte;

    /**
     * 
     * @param p vue du jeu
     * @param m modèle du jeu
     */
    public ControleurJouerPoints(Jeu vueJeu, Core.Partie modelePartie, Carte carte) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        this.carte = carte;

    }

    /**
     * L'action consiste à recommencer une partie. Il faut vérifier qu'il n'y a pas
     * une partie en cours
     * 
     * @param actionEvent l'événement action
     */

     @Override
     public void handle(ActionEvent actionEvent) {
         modelePartie.jouerCartePoints(carte);
     
         Joueur joueurSuivant = modelePartie.getJoueurRival();
         traiterJoueurSuivant(joueurSuivant);
     }
     
     private void traiterJoueurSuivant(Joueur joueur) {
         verifierEtReincarnerSiNecessaire(joueur);
         modelePartie.piocherCarte(joueur);
        //  Afficher la pile et la main du joueur
        
     
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
         bot.jouerCoup();
         Joueur joueurActuel = modelePartie.getJoueurActif();
         miseAJourInterfaceJoueur(joueurActuel);
     }
     
     private void miseAJourInterfaceJoueur(Joueur joueur) {
        verifierEtReincarnerSiNecessaire(joueur);
        modelePartie.piocherCarte(joueur);
        vueJeu.afficherEcranJoueur(joueur.getPseudo(), joueur.getMain(), joueur.getPile(), joueur.getFosse(), joueur.getVieFuture(), joueur.getOeuvres());
        modelePartie.setJoueurActif(joueur);
     }
     

}
