package Controleur;

import vue.Jeu;
import Cartes.Carte;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class ControleurJouerPoints implements EventHandler<ActionEvent> {
  
    private Jeu vueJeu;
    private Core.Partie modelePartie;
    private Carte carte;

 
    public ControleurJouerPoints(Jeu vueJeu, Core.Partie modelePartie, Carte carte) {
        this.vueJeu = vueJeu;
        this.modelePartie = modelePartie;
        this.carte = carte;

    }


     @Override
     public void handle(ActionEvent actionEvent) {
         modelePartie.jouerCartePoints(carte);
     
         Joueur joueurSuivant = modelePartie.getJoueurRival();
         traiterJoueurSuivant(joueurSuivant);
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
