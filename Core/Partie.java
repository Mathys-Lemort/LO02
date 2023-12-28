package Core;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import Cartes.Carte;
import Joueurs.Joueur;
import Plateau.Plateau;


public class Partie {
    private static Partie instance;
    private List<Joueur> joueurs;
    private Plateau plateau;
    private Joueur joueurActif;
    private EtatPartie etatPartie;
    private Scanner scanner;

    private Partie() {
        this.scanner = new Scanner(System.in);
    }

    // Méthode pour obtenir l'instance du singleton
    public static synchronized Partie getInstance() {
        if (instance == null) {
            instance = new Partie();
        }
        return instance;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public EtatPartie getEtatPartie() {
        return etatPartie;
    }

    public void setEtatPartie(EtatPartie etatPartie) {
        this.etatPartie = etatPartie;
    }
    
    // Méthodes pour gérer le déroulement de la partie
    public void initialiserPartie() {
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
        this.plateau = new Plateau();
        this.setEtatPartie(EtatPartie.INITIALISATION);
        this.plateau.initialiserSource();


        this.designerMalchanceux();
        this.distribuerMain();
        this.distribuerPileInitiale();
                System.out.println(this.plateau.getSource().size());

    }
    
    private void piocherCarteSiNecessaire(Joueur joueur) {
        Affichage.afficherTitre(joueur.getPseudo() + ", c'est à votre tour.");
        if (!joueur.pileVide()) {
            joueur.ajouterCarte();
            Affichage.afficherMessage("Vous avez pioché une carte.");
        }
    }


    private void effectuerActionsTour(Joueur joueur,boolean estRejouer) {

        if (!estRejouer) {
            piocherCarteSiNecessaire(joueur);
        }
        Affichage.afficherTitre("Voici votre main:" + joueur.getPseudo());
        joueur.afficherMain();

        boolean tourValide = false;
        while (!tourValide) {
            Affichage.afficherTitre("Choisissez une action:");
            Affichage.afficherOption(1, "Jouer une carte pour des points");
            Affichage.afficherOption(2, "Jouer une carte pour un pouvoir");
            Affichage.afficherOption(3, "Placer une carte dans votre Vie Future");
            Affichage.afficherOption(4, "Passer votre tour");
            int choix = scanner.nextInt();
            switch (choix) {
                case 1:
                // Demander a l'utilisateur de choisir une carte    
                    Affichage.afficherMessage("Choisissez une carte à jouer:");
                    choix = scanner.nextInt();
                    Carte carte = joueur.getMain().get(choix-1);  
                    Affichage.afficherMessage("Vous avez choisi la carte " + carte.getNom() +" que vous ajoutez à vos oeuvres pour " + carte.getPoints() + " points !");            
                    joueur.jouerCartePourPoints(carte);
                    Affichage.afficherMessage("Voici vos oeuvres:\n");
                    joueur.afficherCartesOeuvres();
                  
                    tourValide = true;
                    break;                   
                case 2:
                    Affichage.afficherMessage("Choisissez une carte à jouer:");
                    choix = scanner.nextInt();
                    Carte carte2 = joueur.getMain().get(choix-1);
                    Affichage.afficherMessage("Vous avez choisi la carte " + carte2.getNom() +" que vous jouez pour son pouvoir !");
                    joueur.jouerCartePourPouvoir(carte2, this.getJoueurRival());
                    tourValide = true;
                    break;
                case 3:
                    Affichage.afficherMessage("Choisissez une carte à placer dans votre Vie Future:");
                    choix = scanner.nextInt();
                    Carte carte3 = joueur.getMain().get(choix-1);
                    Affichage.afficherMessage("Vous avez choisi la carte " + carte3.getNom() +" que vous ajoutez à votre Vie Future !");
                    joueur.jouerCartePourFutur(carte3);
                  
                    tourValide = true;
                    break;                   
                case 4:
                    if (!joueur.pileVide()) {
                        joueur.passerTour();
                        tourValide = true;
                    } else {
                        Affichage.afficherMessage("Vous ne pouvez pas passer votre tour car votre pile est vide.");
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
        effectuerActionsTour(joueurActif,false);
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

    // Fonction qui fait rejouer le même joueur
    public void rejouer(Joueur joueur) {
        effectuerActionsTour(joueur,true);
    }

    public void afficher3PremieresCartesSource(){
        for (int i = 0; i < 3; i++) {
            Affichage.afficherOption(i, plateau.getSource().get(i).getNom());
        }
    }

    public List<Carte> getCartesSource(){
        return plateau.getSource();
    }
    
}

