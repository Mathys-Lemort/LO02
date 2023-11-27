import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;




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
    
        // Boucle pour ajouter deux joueurs
        for (int i = 1; i <= 2; i++) {
            System.out.println("Entrez le pseudo du joueur " + i + ":");
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
                System.out.println("Ce pseudo existe déjà. Veuillez choisir un autre.");
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

        System.out.println(joueur + ", c'est votre tour.");
        // Check if the player can draw a card (if the pile is not empty)
        if (!joueur.pileVide()) {
            joueur.piocher();
            System.out.println("Vous avez pioché une carte.");
        }

        // Give the player options for their turn
        boolean tourValide = false;
        while (!tourValide) {
            System.out.println("Choisissez une action: \n" +
                    "1. Jouer une carte pour des points\n" +
                    "2. Jouer une carte pour un pouvoir\n" +
                    "3. Placer une carte dans votre Vie Future\n" +
                    "4. Passer votre tour");
            int choix = scanner.nextInt();

            switch (choix) {
                case 1:
                    plateau.afficherSource();
                    System.out.println("Choisissez une carte de la Source:");
                    joueur.jouerCartePourPoints(null);
                    tourValide = true;
                    break;
                case 2:
                    joueur.jouerCartePourPouvoir(null, null);
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
                        System.out.println("Vous ne pouvez pas passer votre tour car votre pioche est vide.");
                    }
                    break;
                default:
                    System.out.println("Action non valide. Veuillez choisir une action parmi les options.");
                    break;
            }
        }
    }

    public void tourSuivant() {
        // Determine active player: assuming joueurActif is correctly set to the starting player.
        if (joueurActif == null || !joueurs.contains(joueurActif)) {
            joueurActif = joueurs.get(0); // Start with player 1
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
        System.out.println("Les joueurs ont reçu leur main");
    }

    public void distribuerPileInitiale() {
        for (Joueur joueur : joueurs) {
            for (int i = 0; i < 2; i++) {
                Carte carte = plateau.getSource().remove(0);
                joueur.ajouterCarteDansPile(carte);
            }
        }
        System.out.println("Les joueurs ont reçu leur pile initiale");
    }

    public void designerMalchanceux() {
        int lancéJoueur1, lancéJoueur2;
    
        do {
            lancéJoueur1 = (int) (Math.random() * 6) + 1;
            lancéJoueur2 = (int) (Math.random() * 6) + 1;
    
            System.out.println("\n\nLancé de dé pour déterminer le joueur qui commence la partie");
            System.out.println("Le joueur "+ joueurs.get(0) +" a lancé un " + lancéJoueur1);
            System.out.println("Le joueur "+ joueurs.get(1) + " a lancé un " + lancéJoueur2);
    
            if (lancéJoueur1 < lancéJoueur2) {
                joueurActif = joueurs.get(0);
                System.out.println("Le joueur " + joueurs.get(0) + " commence la partie\n\n");
            } else if (lancéJoueur2 < lancéJoueur1) {
                joueurActif = joueurs.get(1);
                System.out.println("Le joueur " + joueurs.get(1) + " commence la partie\n\n");
            } else {
                System.out.println("Égalité. Les joueurs relancent les dés.");
            }
        } while (lancéJoueur1 == lancéJoueur2);
    }
    
    

    // Autres getters et setters pour les attributs de la classe
    // ...

    // Méthodes pour interagir avec les joueurs et le plateau
    // ...
}

// Vous devrez également définir les classes Joueur et Plateau selon votre diagramme
