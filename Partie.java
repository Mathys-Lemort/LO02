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
            Joueur joueur = new Joueur(pseudo);
            this.joueurs.add(joueur);
        }

        // Vous pouvez initialiser le plateau ici si nécessaire
        this.plateau = new Plateau();
        scanner.close();

        this.setEtatPartie(EtatPartie.INITIALISATION);

        // Afficher l'état et les deux joueurs 
        System.out.println("L'état de la partie est: " + this.etatPartie);
        System.out.println("Les joueurs sont: " + this.joueurs);
        System.out.println("La partie a été initialisée avec 2 joueurs.");
    }

    public void tourSuivant() {
        // Code pour passer au tour suivant
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
  

    // Autres getters et setters pour les attributs de la classe
    // ...

    // Méthodes pour interagir avec les joueurs et le plateau
    // ...
}

// Vous devrez également définir les classes Joueur et Plateau selon votre diagramme
