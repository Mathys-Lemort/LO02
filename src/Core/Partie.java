package Core;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import Cartes.Carte;
import Joueurs.Joueur;
import Joueurs.JoueurBot;
import Plateau.Plateau;
import javafx.application.Platform;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class Partie {
    private static Partie instance;
    private List<Joueur> joueurs;
    private Plateau plateau;
    private Joueur joueurActif;
    private EtatPartie etatPartie;
    private Scanner scanner;
    private int resultatLanceJoueur1;
    private int resultatLanceJoueur2;
    private String joueurCommence;
    private Mode mode;
    private boolean rejouer;

    public enum Mode {
        CONSOLE, GRAPHIQUE
    }

    private Partie() {
        this.scanner = new Scanner(System.in);
        this.joueurs = new ArrayList<Joueur>();
        this.plateau = new Plateau();
        this.setEtatPartie(EtatPartie.INITIALISATION);
        this.plateau.initialiserSource();
        this.rejouer = false;

    }

    public boolean getRejouer() {
        return rejouer;
    }

    public void setRejouer(boolean rejouer) {
        this.rejouer = rejouer;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
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
        Affichage.afficherTitre("Initialisation de la Partie");

        // Boucle pour ajouter deux joueurs
        // Si les deux joueurs ne sont pas encore crée seulement
        // Si on n'est pas en mode graphique
        if (!(this.getMode().equals(Mode.GRAPHIQUE))) {
            Affichage.afficherMessage("Voulez-vous jouer contre un Bot? (O/N)");
            Affichage.afficherOption(1, "Oui");
            Affichage.afficherOption(2, "Non");
            int choix = scanner.nextInt();
            scanner.nextLine();
            if (choix == 1) {
                joueurs.add(new Joueur("Joueur 1"));
                joueurs.add(new JoueurBot("Bot"));
            } else {
                if (joueurs.size() < 2) {
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
                }
            }
            this.designerMalchanceux();
        }

        this.distribuerMain();
        this.distribuerPileInitiale();

    }

    public void piocherCarte(Joueur joueur) {
        if (!joueur.pileVide()) {
            joueur.ajouterCarte();
        }

    }

    private void piocherCarteSiNecessaire(Joueur joueur) {
        Affichage.afficherTitre(joueur.getPseudo() + ", c'est à votre tour.");
        if (!joueur.pileVide()) {
            joueur.ajouterCarte();
            Affichage.afficherMessage("Vous avez pioché une carte.");
        }
    }

    private int demanderChoixAction(Joueur joueur) {
        while (true) {
            Affichage.afficherTitre(joueur.getPseudo() + ", choisissez votre action");
            Affichage.afficherOption(1, "Jouer une carte pour des points");
            Affichage.afficherOption(2, "Jouer une carte pour un pouvoir");
            Affichage.afficherOption(3, "Placer une carte dans votre Vie Future");
            Affichage.afficherOption(4, "Passer votre tour");

            try {
                int choix = scanner.nextInt();
                if (choix >= 1 && choix <= 4) {
                    return choix;
                }
                Affichage.afficherMessage("Choix non valide. Veuillez choisir une option entre 1 et 4.");
            } catch (InputMismatchException e) {
                Affichage.afficherMessage("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine(); // Nettoie le buffer du scanner
            }
        }
    }

    private void effectuerActionsTour(Joueur joueur, boolean estRejouer) {
        if (!estRejouer) {
            piocherCarteSiNecessaire(joueur);
        }

        if (joueur instanceof JoueurBot) {
            ((JoueurBot) joueur).jouerCoup();
        } else {
            Affichage.afficherTitre("Tour de " + joueur.getPseudo());
            joueur.afficherMain();

            int choixAction = demanderChoixAction(joueur);
            switch (choixAction) {
                case 1:
                    jouerCartePourPoints(joueur);
                    break;
                case 2:
                    jouerCartePourPouvoir(joueur);
                    break;
                case 3:
                    placerCarteDansVieFuture(joueur);
                    break;
                case 4:
                    if (joueur.pileVide()) {
                        Affichage.afficherMessage(
                                "Vous ne pouvez pas passer votre tour car vous n'avez pas de cartes dans votre pile.");
                        effectuerActionsTour(joueur, false);
                    } else {
                        passerTour(joueur);
                    }
                    break;
                default:
                    Affichage.afficherMessage("Choix non valide.");
                    break;
            }
        }
    }

    private void jouerCartePourPoints(Joueur joueur) {
        if (joueur.getMain().isEmpty()) {
            Affichage.afficherMessage("Vous n'avez pas de cartes à jouer pour des points.");
            return;
        }

        Affichage.afficherMessage("Choisissez une carte à jouer pour des points:");
        joueur.afficherMain();
        int choix = obtenirChoixCarte(joueur.getMain().size());

        Carte carteChoisie = joueur.getMain().get(choix - 1);
        joueur.jouerCartePourPoints(carteChoisie);
        Affichage.afficherMessage(
                "Vous avez joué " + carteChoisie.getNom() + " pour " + carteChoisie.getPoints() + " points.");
    }

    private void jouerCartePourPouvoir(Joueur joueur) {
        if (joueur.getMain().isEmpty()) {
            Affichage.afficherMessage("Vous n'avez pas de cartes à jouer pour un pouvoir.");
            return;
        }

        Affichage.afficherMessage("Choisissez une carte à jouer pour son pouvoir:");
        joueur.afficherMain();
        int choix = obtenirChoixCarte(joueur.getMain().size());

        Carte carteChoisie = joueur.getMain().get(choix - 1);
        joueur.jouerCartePourPouvoir(carteChoisie, getJoueurRival());
    }

    private void placerCarteDansVieFuture(Joueur joueur) {
        if (joueur.getMain().isEmpty()) {
            Affichage.afficherMessage("Vous n'avez pas de cartes à placer dans votre Vie Future.");
            return;
        }

        Affichage.afficherMessage("Choisissez une carte à placer dans votre Vie Future:");
        joueur.afficherMain();
        int choix = obtenirChoixCarte(joueur.getMain().size());

        Carte carteChoisie = joueur.getMain().get(choix - 1);
        joueur.jouerCartePourFutur(carteChoisie);
        Affichage.afficherMessage("Vous avez placé " + carteChoisie.getNom() + " dans votre Vie Future.");
    }

    private void passerTour(Joueur joueur) {
        joueur.passerTour();
    }

    private int obtenirChoixCarte(int nombreDeCartes) {
        int choix;
        do {
            choix = scanner.nextInt();
            scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()
            if (choix < 1 || choix > nombreDeCartes) {
                Affichage.afficherMessage(
                        "Choix non valide. Veuillez choisir un numéro entre 1 et " + nombreDeCartes + ".");
            }
        } while (choix < 1 || choix > nombreDeCartes);
        return choix;
    }

    public void tourSuivant() {
        // Determine active player: assuming joueurActif is correctly set to the
        // starting player.
        if (joueurActif == null || !joueurs.contains(joueurActif)) {
            designerMalchanceux();
        } else {
            // Get the next player's index and update joueurActif.
            int currentIndex = joueurs.indexOf(joueurActif);
            joueurActif = joueurs.get((currentIndex + 1) % joueurs.size());
        }

        // Vérifier si la main et la pile du joueur actif sont vides
        if (joueurActif.mainVide() && joueurActif.pileVide()) {
            Affichage.afficherTitre("Réincarnation de " + joueurActif.getPseudo());
            Affichage.afficherMessage("Vous n'avez plus de cartes dans votre main ni dans votre pile.");
            // Le joueur meurt donc et il essaie de se réincarner dans sa prochaine vie (si
            // il a assez de points)
            joueurActif.reincarnation();
        }

        else {
            effectuerActionsTour(joueurActif, false);
        }
    }

    public void terminerPartie() {
        this.setEtatPartie(EtatPartie.TERMINE);
        Affichage.afficherTitre("Fin de la partie");
        Affichage.afficherMessage("Le joueur " + joueurActif.getPseudo() + " a gagné la partie!");
    }

    public void commencerPartie() {
        initialiserPartie(); // Préparez le jeu pour le début.

        this.setEtatPartie(EtatPartie.EN_COURS);

        // Démarrez la boucle de jeu.
        while (this.etatPartie == EtatPartie.EN_COURS) {
            tourSuivant(); // Exécutez un tour de jeu.
        }

        // Demander aux joueurs s'ils veulent rejouer ou quitter
        Affichage.afficherMessage("Voulez-vous rejouer? (O/N)");
        String choix = scanner.nextLine();
        if (choix.equals("O")) {
            commencerPartie();
        } else {
            Affichage.afficherMessage("Merci d'avoir joué!");
        }
    }

    public void distribuerMain() {
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

    public Joueur getJoueurActif() {
        return joueurActif;
    }

    public Joueur getJoueurRival() {
        if (joueurActif == joueurs.get(0)) {
            return joueurs.get(1);
        } else {
            return joueurs.get(0);
        }
    }

    // Fonction qui fait rejouer le même joueur
    public void rejouer(Joueur joueur) {
        effectuerActionsTour(joueur, true);
    }

    public ArrayList<Carte> afficher3PremieresCartesSource() {
        ArrayList<Carte> cartes = new ArrayList<Carte>();
        for (int i = 0; i < 3; i++) {
            cartes.add(plateau.getSource().get(i));
        }
        return cartes;
    }

    public List<Carte> getCartesSource() {
        return plateau.getSource();
    }

    public void piocherSourceMain(Joueur joueur) {
        joueur.ajouterCarteDansMain(plateau.getSource().remove(0));
        Affichage.afficherMessage(
                "Vous avez pioché la carte " + joueur.getMain().get(joueur.getMain().size() - 1).getNom());
    }

    public void piocherSourcePile(Joueur joueur) {
        joueur.ajouterCarteDansPile(plateau.getSource().remove(0));
    }

    public Joueur getJoueur1() {
        if (joueurs.size() < 1) {
            joueurs.add(new Joueur("Joueur 1"));
        }
        return joueurs.get(0);
    }

    public Joueur getJoueur2() {
        if (joueurs.size() < 2) {
            joueurs.add(new Joueur("Joueur 2"));
        }
        return joueurs.get(1);
    }

    public void lancerDes() {
        // Le joueur qui commence est celui qui a lancé le plus petit nombre, il ne peut pas y avoir d'égalité
        int resultatLanceJoueur1 =0, resultatLanceJoueur2 = 0;
        while (resultatLanceJoueur1 == resultatLanceJoueur2) {
            resultatLanceJoueur1 = (int) (Math.random() * 6) + 1;
            resultatLanceJoueur2 = (int) (Math.random() * 6) + 1;
        }
        this.resultatLanceJoueur1 = resultatLanceJoueur1;
        this.resultatLanceJoueur2 = resultatLanceJoueur2;
        // C'est le plus petit nombre qui commence
        if (resultatLanceJoueur1 < resultatLanceJoueur2) {
            joueurActif = getJoueur1();
            joueurCommence = getJoueur1().getPseudo();
        } else {
            joueurActif = getJoueur2();
            joueurCommence = getJoueur2().getPseudo();
        }
    }

    public int getResultatLanceJoueur1() {
        return resultatLanceJoueur1;
    }

    public int getResultatLanceJoueur2() {
        return resultatLanceJoueur2;
    }

    public String getJoueurCommence() {
        return joueurCommence;
    }

    public void setJoueur1Pseudo(String pseudo) {
        getJoueur1().setPseudo(pseudo);
    }

    public void setJoueur2Pseudo(String pseudo) {
        // SI le string est "Bot" alors on crée un bot
        if (pseudo.equals("Bot")) {
            getJoueur2().setPseudo(pseudo);
            joueurs.set(1, new JoueurBot(pseudo));
        } else {
            getJoueur2().setPseudo(pseudo);
        }

    }

    public void setJoueurActif(Joueur joueur) {
        joueurActif = joueur;
    }

    public void jouerCartePoints(Carte carte) {
        joueurActif.jouerCartePourPoints(carte);
    }

    public void jouerCarteVieFuture(Carte carte) {
        joueurActif.jouerCartePourFutur(carte);
    }

    public Joueur getJoueurPseudo(String pseudo) {
        if (getJoueur1().getPseudo().equals(pseudo)) {
            return getJoueur1();
        } else {
            return getJoueur2();
        }
    }

}
