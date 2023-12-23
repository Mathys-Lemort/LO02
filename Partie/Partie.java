package Partie;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

import Cartes.Carte;
import Joueurs.Joueur;




public class Partie {
    private static Partie instance;
    private List<Joueur> joueurs;
    private Plateau plateau;
    private Joueur joueurActif;
    private EtatPartie etatPartie;

    // Le constructeur est privé pour empêcher l'instanciation directe.
    private Partie() {
        // initialisation des attributs
    }

    // Méthode pour obtenir l'instance du singleton
    public static synchronized Partie getInstance() {
        if (instance == null) {
            instance = new Partie();
        }
        return instance;
    }

    public EtatPartie getEtatPartie() {
        return etatPartie;
    }

    public void setEtatPartie(EtatPartie etatPartie) {
        this.etatPartie = etatPartie;
    }

    // Méthodes pour gérer le déroulement de la partie
    public void initialiserPartie() {
        Scanner scanner = new Scanner(System.in);
        this.joueurs = new ArrayList<Joueur>();
    
        Affichage.afficherTitre("Initialisation de la Partie");

        // Boucle pour ajouter deux joueurs
        for (int i = 1; i <= 2; i++) {
            Affichage.afficherMessage("Entrez le pseudo du joueur " + i + ":");
            String pseudo = scanner.nextLine();
            
            // Vérifiez si un joueur avec le même pseudo existe déjà
            boolean pseudoExiste = false;
            for (Joueur joueurExist : this.joueurs) {
                if (joueurExist.getPseudo().equals(pseudo)) {
                    pseudoExiste = true;
                    break;
                }
            }
    
            if (pseudoExiste) {
                Affichage.afficherMessage("Ce pseudo existe déjà. Veuillez choisir un autre.");
                i--; // Répétez la saisie pour le même joueur
            } else {
                Joueur joueur = new Joueur(pseudo);
                this.joueurs.add(joueur);
            }

        }
    
        // Vous pouvez initialiser le plateau ici si nécessaire
        List<Carte> source = new ArrayList<Carte>();
        List<Carte> fosse = new ArrayList<Carte>();
        this.plateau = new Plateau(source, fosse);
        // utilise initialiser source 
        this.plateau.initialiserSource();
    
        this.setEtatPartie(EtatPartie.INITIALISATION);

        this.designerMalchanceux();
        this.distribuerMain();
        this.distribuerPileInitiale();
    }
    

    private void effectuerActionsTour(Joueur joueur) {
        Scanner scanner = new Scanner(System.in);

        Affichage.afficherTitre("Tour de " + joueur.getPseudo());
        // Check if the player can draw a card (if the pile is not empty)
        if (!joueur.pileVide()) {
            joueur.piocher();
            Affichage.afficherMessage("Vous avez pioché une carte.");
        }

        // Give the player options for their turn
        boolean tourValide = false;
        while (!tourValide) {
            Affichage.afficherTitre("Choisissez une action:");
            Affichage.afficherOption(1, "Jouer une carte pour des points");
            Affichage.afficherOption(2, "Jouer une carte pour un pouvoir");
            Affichage.afficherOption(3, "Placer une carte dans votre Vie Future");
            Affichage.afficherOption(4, "Passer votre tour");

                    // "1. Jouer une carte pour des points\n" +
                    // "2. Jouer une carte pour un pouvoir\n" +
                    // "3. Placer une carte dans votre Vie Future\n" +
                    // "4. Passer votre tour");
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    plateau.afficherSource();
                    Affichage.afficherMessage("Choisissez une carte de la Source:");
                    joueur.jouerCartePourPoints(null);
                    tourValide = true;
                    break;
                case 2:
                    joueur.afficherMain();
                    joueur.jouerCartePourPouvoir(null,null);
                    tourValide = true;
                    break;
                case 3:
                    joueur.jouerCartePourFutur(null);
                    tourValide = true;
                    break;
                case 4:
                    if (!joueur.pileVide()) {
                        joueur.passerTour();
                        tourValide = true;
                    } else {
                        Affichage.afficherMessage("Vous ne pouvez pas passer votre tour car votre pioche est vide.");
                    }
                    break;
                default:
                    Affichage.afficherMessage("Action non valide. Veuillez choisir une action parmi les options.");
                    break;
            }
        }
    }

    public void tourSuivant() {
        // Determine active player: assuming joueurActif is correctly set to the starting player.
        if (joueurActif == null || !joueurs.contains(joueurActif)) {
            designerMalchanceux();
        } else {
            // Get the next player's index and update joueurActif.
            int currentIndex = joueurs.indexOf(joueurActif);
            joueurActif = joueurs.get((currentIndex + 1) % joueurs.size());
        }

        // Perform actions for the active player's turn
        effectuerActionsTour(joueurActif);
    }

    public void verifierFinPartie() {
        // Code pour vérifier si la partie est terminée
    }    

    public void terminerPartie() {
        // Code pour terminer la partie
    }
    
    public void commencerPartie() {
        initialiserPartie(); // Préparez le jeu pour le début.
    
        this.setEtatPartie(EtatPartie.EN_COURS);

    
        // Démarrez la boucle de jeu.
        while (this.etatPartie == EtatPartie.EN_COURS) {
            tourSuivant(); // Exécutez un tour de jeu.
    
            verifierFinPartie(); // Vérifiez si la partie est terminée.
    
            // Ici, vous pouvez également implémenter une logique pour sauvegarder l'état du jeu,
            // mettre à jour l'interface utilisateur, etc.
        }
    
        // Terminez la partie et nettoyez si nécessaire.
        terminerPartie();
    }
  
    public void distribuerMain(){
        for (Joueur joueur : joueurs) {
            for (int i = 0; i < 4; i++) {
                Carte carte = plateau.getSource().remove(0);
                joueur.ajouterCarteDansMain(carte);
            }
        }       
    }

    public void distribuerPileInitiale() {
        for (Joueur joueur : joueurs) {
            for (int i = 0; i < 2; i++) {
                Carte carte = plateau.getSource().remove(0);
                joueur.ajouterCarteDansPile(carte);
            }
        }
    }

    public void designerMalchanceux() {
        int lancéJoueur1, lancéJoueur2;
    
        do {
            lancéJoueur1 = (int) (Math.random() * 6) + 1;
            lancéJoueur2 = (int) (Math.random() * 6) + 1;
    

            Affichage.afficherMessage("Le joueur qui a lancé le plus petit nombre commence la partie.");
            Affichage.afficherMessage("En cas d'égalité, les joueurs relancent les dés.");
            Affichage.afficherMessage("Appuyez sur Entrée pour continuer...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
            Affichage.afficherMessage(joueurs.get(0).getPseudo() + " a lancé " + lancéJoueur1);
            Affichage.afficherMessage(joueurs.get(1).getPseudo() + " a lancé " + lancéJoueur2);


            if (lancéJoueur1 > lancéJoueur2) {
                joueurActif = joueurs.get(0);
            } else if (lancéJoueur2 > lancéJoueur1) {
                joueurActif = joueurs.get(1);
            } else {
                Affichage.afficherMessage("Égalité! Relancez les dés.");
            }
        } while (lancéJoueur1 == lancéJoueur2);
    }
    
    public Joueur getJoueurActif(){
        return joueurActif;
    }

    public Joueur getJoueurRival(){
        if (joueurActif == joueurs.get(0)){
            return joueurs.get(1);
        } else {
            return joueurs.get(0);
        }
    }
    

    // Autres getters et setters pour les attributs de la classe
    // ...

    // Méthodes pour interagir avec les joueurs et le plateau
    // ...
}

// Vous devrez également définir les classes Joueur et Plateau selon votre diagramme
