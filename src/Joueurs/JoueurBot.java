package Joueurs;

import java.util.Random;

import Cartes.Carte;
import Core.Partie;

public class JoueurBot extends Joueur {
    private Random random = new Random();

    public JoueurBot(String id) {
        super(id);
    }

    public void jouerCoup() {
        if (this.getMain().isEmpty() && this.getPile().isEmpty()) {
            this.reincarnation();
            return;
        }

        if (!this.getMain().isEmpty()) {
            // Stratégie dynamique basée sur la situation
            Carte carteChoisie = choisirStrategiquement();
            if (carteChoisie != null) {
                jouerCarteStrategiquement(carteChoisie);
            } else {
                passerTour();
            }
        } else {
            passerTour();
        }
    }

    private Carte choisirStrategiquement() {
        // Exemple de stratégie: choisir aléatoirement entre jouer pour les points, les pouvoirs ou la vie future
        int choix = random.nextInt(3);
        switch (choix) {
            case 0: return choisirCarteAvecMaxPoints();
            case 1: return choisirCarteAvecPouvoir();
            case 2: return choisirCartePourVieFuture();
            default: return null;
        }
    }

    private void jouerCarteStrategiquement(Carte carte) {
        if (carte.getPoints() >= 3) {
            jouerCartePourPoints(carte);
        } else if (carte.getPouvoir() != null && !carte.getPouvoir().isEmpty()) {
            jouerCartePourPouvoir(carte, Partie.getInstance().getJoueurRival());
        } else {
            jouerCartePourFutur(carte);
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
