package Joueurs;

import Cartes.Carte;
import Core.Partie;

public class JoueurBot extends Joueur {

    public JoueurBot(String id) {
        super(id);
    }

    public void jouerCoup() {
        System.out.println(this.getPseudo() + " (bot) joue son coup.");

        // Choix basique de l'action
        if (!this.getMain().isEmpty()) {
            Carte carteAvecMaxPoints = choisirCarteAvecMaxPoints();
            if (carteAvecMaxPoints != null) {
                jouerCartePourPoints(carteAvecMaxPoints);
            } else {
                Carte carteAvecPouvoir = choisirCarteAvecPouvoir();
                if (carteAvecPouvoir != null) {
                    jouerCartePourPouvoir(carteAvecPouvoir, Partie.getInstance().getJoueurRival());
                } else {
                    Carte cartePourVieFuture = choisirCartePourVieFuture();
                    if (cartePourVieFuture != null) {
                        jouerCartePourFutur(cartePourVieFuture);
                    } else {
                        passerTour();
                    }
                }
            }
        } else {
            passerTour();
        }
    }

    private Carte choisirCarteAvecMaxPoints() {
        Carte carteMaxPoints = null;
        int maxPoints = 0;
        for (Carte carte : this.getMain()) {
            if (carte.getPoints() > maxPoints) {
                maxPoints = carte.getPoints();
                carteMaxPoints = carte;
            }
        }
        return carteMaxPoints;
    }

    private Carte choisirCarteAvecPouvoir() {
        // Choisir une carte au hasard qui n'a pas beaucoup de point 
        Carte carteAvecPouvoir = null;
        for (Carte carte : this.getMain()) {
            if (carte.getPoints() < 3) {
                carteAvecPouvoir = carte;
            }
        }
        return carteAvecPouvoir;
    }

    

    private Carte choisirCartePourVieFuture() {
        // Choisir une carte au hasard qui n'a pas beaucoup de point 
        Carte cartePourVieFuture = null;
        for (Carte carte : this.getMain()) {
            if (carte.getPoints() < 3) {
                cartePourVieFuture = carte;
            }
        }
        return cartePourVieFuture;
        
    }
}
