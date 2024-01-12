package Joueurs;

import java.util.Random;
import Cartes.Carte;
import Core.Partie;

/**
 * Classe représentant un joueur robot (IA) dans le jeu.
 * Cette classe étend la classe Joueur pour implémenter des comportements automatisés
 * spécifiques à l'IA, tels que la sélection et le jeu des cartes.
 */
public class JoueurBot extends Joueur {
    private Random random = new Random();
     /**
     * Constructeur de JoueurBot.
     * 
     * @param id L'identifiant unique du JoueurBot.
     */
    public JoueurBot(String id) {
        super(id);
    }
    /**
     * Joue un coup automatiquement en fonction de l'état actuel du jeu.
     * Cette méthode décide de la carte à jouer ou de passer le tour
     * en fonction des cartes disponibles en main et dans la pile.
     */

    public void jouerCoup() {
        if (this.getMain().isEmpty() && this.getPile().isEmpty()) {
            this.reincarnation();
            return;
        }

        if (!this.getMain().isEmpty()) {
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
     /**
     * Sélectionne une carte de la main du bot de manière stratégique.
     * 
     * @return La carte sélectionnée selon la stratégie définie, ou null si aucune carte n'est choisie.
     */
    private Carte choisirStrategiquement() {
        int choix = random.nextInt(3);
        switch (choix) {
            case 0: return choisirCarteAvecMaxPoints();
            case 1: return choisirCarteAvecPouvoir();
            case 2: return choisirCartePourVieFuture();
            default: return null;
        }
    }
    /**
     * Joue la carte sélectionnée de manière stratégique.
     * 
     * @param carte La carte à jouer.
     */

    private void jouerCarteStrategiquement(Carte carte) {
        if (carte.getPoints() >= 3) {
            jouerCartePourPoints(carte);
        } else if (carte.getPouvoir() != null && !carte.getPouvoir().isEmpty()) {
            jouerCartePourPouvoir(carte, Partie.getInstance().getJoueurRival());
        } else {
            jouerCartePourFutur(carte);
        }
    }
    /**
     * Choisit la carte avec le maximum de points parmi les cartes en main.
     * 
     * @return La carte avec le plus de points, ou null si aucune carte ne correspond.
     */
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
    /**
     * Choisit une carte avec un pouvoir spécial parmi les cartes en main.
     * 
     * @return La carte avec un pouvoir spécial, ou null si aucune carte ne correspond.
     */
    private Carte choisirCarteAvecPouvoir() {
        Carte carteAvecPouvoir = null;
        for (Carte carte : this.getMain()) {
            if (carte.getPoints() < 3) {
                carteAvecPouvoir = carte;
            }
        }
        return carteAvecPouvoir;
    }

    /**
     * Choisit une carte appropriée pour être placée dans la vie future du bot.
     * 
     * @return La carte choisie pour la vie future, ou null si aucune carte ne correspond.
     */

    private Carte choisirCartePourVieFuture() {
        Carte cartePourVieFuture = null;
        for (Carte carte : this.getMain()) {
            if (carte.getPoints() < 3) {
                cartePourVieFuture = carte;
            }
        }
        return cartePourVieFuture;
        
    }
}
