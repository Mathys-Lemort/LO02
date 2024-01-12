package Core;

import java.io.*;
import java.util.*;
import Cartes.*;
import Joueurs.*;
import Plateau.Plateau;
import javafx.scene.control.ChoiceDialog;

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

    /**
     * Renvoie la valeur de la variable rejouer.
     * 
     * @return true si le joueur souhaite rejouer, false sinon.
     */
    public boolean getRejouer() {
        return rejouer;
    }

    /**
     * Définit si la partie doit être rejouée ou non.
     * 
     * @param rejouer true si la partie doit être rejouée, false sinon
     */
    public void setRejouer(boolean rejouer) {
        this.rejouer = rejouer;
    }

    /**
     * Définit le mode de la partie.
     * 
     * @param mode le mode de la partie
     */
    public void setMode(Mode mode) {
        this.mode = mode;
    }

    /**
     * Renvoie le mode de la partie.
     *
     * @return le mode de la partie
     */
    public Mode getMode() {
        return mode;
    }

    /**
     * Cette méthode retourne l'instance unique de la classe Partie.
     * Si aucune instance n'existe, elle en crée une nouvelle et la retourne.
     * 
     * @return l'instance unique de la classe Partie
     */
    public static synchronized Partie getInstance() {
        if (instance == null) {
            instance = new Partie();
        }
        return instance;
    }

    /**
     * Renvoie le scanner utilisé par la partie.
     *
     * @return le scanner utilisé par la partie
     */
    public Scanner getScanner() {
        return scanner;
    }

    /**
     * Renvoie l'état actuel de la partie.
     *
     * @return l'état de la partie
     */
    public EtatPartie getEtatPartie() {
        return etatPartie;
    }

    /**
     * Définit l'état de la partie.
     * 
     * @param etatPartie L'état de la partie à définir.
     */
    public void setEtatPartie(EtatPartie etatPartie) {
        this.etatPartie = etatPartie;
    }

    /**
     * Initialise la partie en affichant le titre "Initialisation de la Partie".
     * Si le mode graphique n'est pas activé, vérifie si une sauvegarde doit être
     * chargée.
     * Si une sauvegarde doit être chargée, la partie est chargée et la fonction se
     * termine.
     * Sinon, les joueurs sont créés et le joueur malchanceux est désigné.
     * Enfin, les cartes sont distribuées aux joueurs.
     */
    public void initialiserPartie() {
        Affichage.afficherTitre("Initialisation de la Partie");

        if (!modeGraphique()) {
            if (demandeChargementSauvegarde()) {
                Sauvegarde.chargerPartie(this);
                return;
            }
            creerJoueurs();
            designerMalchanceux();
        }

        distribuerCartesAuxJoueurs();
    }

    /**
     * Vérifie si le mode de la partie est graphique.
     * 
     * @return true si le mode est graphique, sinon false.
     */
    private boolean modeGraphique() {
        return this.getMode().equals(Mode.GRAPHIQUE);
    }

    /**
     * Vérifie si une sauvegarde existe et demande à l'utilisateur s'il souhaite
     * charger une partie sauvegardée.
     * 
     * @return true si l'utilisateur souhaite charger une partie sauvegardée, sinon
     *         false.
     */
    private boolean demandeChargementSauvegarde() {
        File sauvegarde = new File("src/sauvegarde.txt");
        if (sauvegarde.exists()) {
            Affichage.afficherMessage("Voulez-vous charger une partie sauvegardée?");
            Affichage.afficherOption(1, "Oui");
            Affichage.afficherOption(2, "Non");
            return scanner.nextInt() == 1;
        }
        return false;
    }

    /**
     * Distribue les cartes aux joueurs.
     * Cette fonction distribue les cartes en appelant les méthodes distribuerMain()
     * et distribuerPileInitiale().
     */
    private void distribuerCartesAuxJoueurs() {
        this.distribuerMain();
        this.distribuerPileInitiale();
    }

    /**
     * Cette fonction permet de créer les joueurs de la partie.
     * Elle demande aux joueurs s'ils veulent jouer contre un Bot.
     * Si oui, un joueur Bot est créé et le joueur humain entre son pseudo.
     * Si non, les pseudos des joueurs humains sont demandés.
     * Les pseudos doivent être uniques, sinon un message d'erreur est affiché.
     * Les joueurs sont ajoutés à la liste des joueurs de la partie.
     */
    private void creerJoueurs() {
        Affichage.afficherMessage("Voulez-vous jouer contre un Bot?");
        Affichage.afficherOption(1, "Oui");
        Affichage.afficherOption(2, "Non");
        int choix = scanner.nextInt();
        scanner.nextLine();
        if (choix == 1) {
            creerJoueurBot();
            creerJoueurHumain();
        } else {
            for (int i = 1; i <= 2; i++) {
                creerJoueurHumain();
            }
        }
    }

    /**
     * Crée un joueur bot avec le pseudo "Bot" et l'ajoute à la liste des joueurs de
     * la partie.
     */
    private void creerJoueurBot() {
        joueurs.add(new JoueurBot("Bot"));
    }

    /**
     * Demande au joueur humain d'entrer son pseudo et l'ajoute à la liste des
     * joueurs de la partie.
     * Vérifie si le pseudo existe déjà et affiche un message d'erreur le cas
     * échéant.
     */
    private void creerJoueurHumain() {
        Affichage.afficherMessage("Entrez votre pseudo:");
        String pseudo = scanner.nextLine();

        boolean pseudoExiste = false;
        for (Joueur joueurExist : this.joueurs) {
            if (joueurExist.getPseudo().equals(pseudo)) {
                pseudoExiste = true;
                break;
            }
        }

        if (pseudoExiste) {
            Affichage.afficherMessage("Ce pseudo existe déjà. Veuillez choisir un autre.");
            creerJoueurHumain(); // Retry creating a human player
        } else {
            Joueur joueur = new Joueur(pseudo);
            this.joueurs.add(joueur);
        }
    }

    /**
     * Permet au joueur de piocher une carte de sa pile de cartes.
     * Si la pile du joueur n'est pas vide, une carte est ajoutée à sa main.
     *
     * @param joueur le joueur qui pioche une carte
     */
    public void piocherCarte(Joueur joueur) {
        if (!joueur.pileVide()) {
            joueur.ajouterCarte();
        }

    }

    /**
     * Pioche une carte si nécessaire pour le joueur donné.
     * Affiche le titre du joueur et lui ajoute une carte s'il a encore des cartes
     * dans sa pile.
     * Affiche un message indiquant que le joueur a pioché une carte.
     *
     * @param joueur le joueur pour lequel piocher une carte
     */
    private void piocherCarteSiNecessaire(Joueur joueur) {
        Affichage.afficherTitre(joueur.getPseudo() + ", c'est à votre tour.");
        if (!joueur.pileVide()) {
            joueur.ajouterCarte();
            Affichage.afficherMessage("Vous avez pioché une carte.");
        }
    }

    /**
     * Cette fonction demande au joueur de choisir une action à effectuer pendant
     * son tour.
     * Elle affiche un menu d'options et attend que le joueur saisisse un choix
     * valide.
     * Si le choix est valide, la fonction retourne le choix du joueur.
     * Si le choix n'est pas valide, un message d'erreur est affiché et le joueur
     * est invité à choisir à nouveau.
     *
     * @param joueur Le joueur en cours
     * @return Le choix d'action du joueur
     */
    private int demanderChoixAction(Joueur joueur) {
        while (true) {
            Affichage.afficherTitre(joueur.getPseudo() + ", choisissez votre action");
            Affichage.afficherOption(1, "Jouer une carte pour des points");
            Affichage.afficherOption(2, "Jouer une carte pour un pouvoir");
            Affichage.afficherOption(3, "Placer une carte dans votre Vie Future");
            Affichage.afficherOption(4, "Passer votre tour");
            Affichage.afficherOption(5, "Sauvegarder et quitter la partie");

            try {
                int choix = scanner.nextInt();
                if (choix >= 1 && choix <= 5) {
                    return choix;
                }
                Affichage.afficherMessage("Choix non valide. Veuillez choisir une option entre 1 et 4.");
            } catch (InputMismatchException e) {
                Affichage.afficherMessage("Entrée invalide. Veuillez entrer un nombre.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Effectue les actions d'un tour pour un joueur donné.
     * Si le joueur n'est pas en train de rejouer, il pioche une carte si
     * nécessaire.
     * Si le joueur est un JoueurBot, il joue automatiquement un coup.
     * Sinon, le joueur est invité à choisir une action parmi les suivantes :
     * 1. Jouer une carte pour obtenir des points.
     * 2. Jouer une carte pour utiliser son pouvoir.
     * 3. Placer une carte dans la pile de vie future.
     * 4. Passer son tour (si la pile de cartes est vide, le joueur ne peut pas
     * passer son tour).
     * 5. Sauvegarder la partie et quitter le jeu.
     * Si le choix du joueur est invalide, un message d'erreur est affiché.
     *
     * @param joueur     le joueur pour lequel les actions du tour sont effectuées
     * @param estRejouer indique si le joueur est en train de rejouer
     */
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
                case 5:
                    Sauvegarde.sauvegarderPartie(this);
                    System.exit(0);
                    break;
                default:
                    Affichage.afficherMessage("Choix non valide.");
                    break;
            }
        }
    }

    /**
     * Joue une carte de la main du joueur pour obtenir des points.
     * Si la main du joueur est vide, affiche un message indiquant qu'il n'y a pas
     * de cartes à jouer.
     * Demande au joueur de choisir une carte à jouer parmi celles disponibles dans
     * sa main.
     * Joue la carte choisie et affiche un message indiquant la carte jouée et le
     * nombre de points obtenus.
     *
     * @param joueur le joueur qui joue la carte
     */
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

    /**
     * Joue une carte pour activer son pouvoir.
     * Si le joueur n'a pas de cartes dans sa main, affiche un message et retourne.
     * Demande au joueur de choisir une carte dans sa main et la joue pour activer
     * son pouvoir.
     * 
     * @param joueur le joueur qui joue la carte
     */
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

    /**
     * Place une carte de la main du joueur dans sa Vie Future.
     * Si la main du joueur est vide, affiche un message d'erreur.
     * Demande au joueur de choisir une carte à placer dans sa Vie Future.
     * Affiche un message indiquant la carte choisie et la place dans la Vie Future
     * du joueur.
     *
     * @param joueur le joueur dont la carte sera placée dans la Vie Future
     */
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

    /**
     * Passe le tour du joueur spécifié.
     *
     * @param joueur le joueur dont le tour doit être passé
     */
    private void passerTour(Joueur joueur) {
        joueur.passerTour();
    }

    /**
     * Obtient le choix de carte de l'utilisateur.
     * 
     * @param nombreDeCartes Le nombre total de cartes disponibles.
     * @return Le choix de carte de l'utilisateur.
     */
    private int obtenirChoixCarte(int nombreDeCartes) {
        int choix;
        do {
            choix = scanner.nextInt();
            scanner.nextLine();
            if (choix < 1 || choix > nombreDeCartes) {
                Affichage.afficherMessage(
                        "Choix non valide. Veuillez choisir un numéro entre 1 et " + nombreDeCartes + ".");
            }
        } while (choix < 1 || choix > nombreDeCartes);
        return choix;
    }

    /**
     * Advances to the next turn in the game.
     * If the active player is null or not in the list of players, it calls the
     * designerMalchanceux() method.
     * Otherwise, it updates the active player to the next player in the list.
     * If the active player has an empty hand and an empty pile, it displays a
     * message and calls the reincarnation() method.
     * Otherwise, it calls the effectuerActionsTour() method for the active player.
     */
    public void tourSuivant() {
        determinerJoueurActif();
        verifierReincarnation();
        executerActionsTour();
    }

    /**
     * Détermine le joueur actif.
     * Si le joueur actif est null ou n'est pas dans la liste des joueurs, la
     * méthode designerMalchanceux() est appelée.
     * Sinon, le joueur actif est mis à jour pour le joueur suivant dans la liste.
     */
    private void determinerJoueurActif() {
        if (joueurActif == null || !joueurs.contains(joueurActif)) {
            designerMalchanceux();
        } else {
            int currentIndex = joueurs.indexOf(joueurActif);
            joueurActif = joueurs.get((currentIndex + 1) % joueurs.size());
        }
    }

    /**
     * Vérifie si le joueur actif doit être réincarné.
     * Si le joueur actif n'a plus de cartes dans sa main et dans sa pile, la
     * méthode reincarnation() est appelée.
     */
    private void verifierReincarnation() {
        if (joueurActif.mainVide() && joueurActif.pileVide()) {
            Affichage.afficherTitre("Réincarnation de " + joueurActif.getPseudo());
            Affichage.afficherMessage("Vous n'avez plus de cartes dans votre main ni dans votre pile.");

            joueurActif.reincarnation();
        }
    }

    /**
     * Exécute les actions du tour du joueur actif.
     * 
     * Si le joueur actif est un JoueurBot, il joue automatiquement un coup.
     * Sinon, la méthode effectuerActionsTour() est appelée pour le joueur actif.
     */
    private void executerActionsTour() {
        effectuerActionsTour(joueurActif, false);
    }
    
    /**
     * Termine la partie en mettant à jour l'état de la partie et en affichant un
     * message de fin.
     * Le joueur actif est déclaré comme le gagnant de la partie.
     */
    public void terminerPartie() {
        this.setEtatPartie(EtatPartie.TERMINE);
        if (modeGraphique()) {
            ChoiceDialog<String> dialog = new ChoiceDialog<>("Oui", "Oui", "Non");
            dialog.setTitle("Fin de la partie");
            dialog.setHeaderText("Voulez-vous rejouer?");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String selectedOption = result.get();
                if (selectedOption.equals("Oui")) {
                    // Vider les joueurs et le plateau
                    joueurs.clear();
                    return;
                } else {
                    Affichage.afficherMessage("Merci d'avoir joué!");
                    System.exit(0);
                }
            }
        } else {
            Affichage.afficherMessage("Voulez-vous rejouer?");
            Affichage.afficherOption(1, "Oui");
            Affichage.afficherOption(2, "Non");
            int choix = this.getScanner().nextInt();
            this.getScanner().nextLine();
            if (choix == 1) {
                // Ici on va rappeler la méthode commencerPartie()
            } else {
                Affichage.afficherMessage("Merci d'avoir joué!");
                System.exit(0);
            }
        }
    }

    /**
     * Démarre une partie.
     * 
     * Cette méthode initialise la partie, met l'état de la partie à "EN_COURS" et
     * effectue les tours jusqu'à ce que l'état de la partie change.
     * Une fois la partie terminée, elle demande à l'utilisateur s'il souhaite
     * rejouer.
     * Si l'utilisateur répond "O", la méthode recommence une nouvelle partie.
     * Sinon, elle affiche un message de remerciement.
     */
    public void commencerPartie() {
        initialiserPartie();

        this.setEtatPartie(EtatPartie.EN_COURS);

        while (this.etatPartie == EtatPartie.EN_COURS) {
            tourSuivant();
        }
    }

    /**
     * Distribue un nombre spécifié de cartes à chaque joueur à partir de la source
     * du plateau.
     * 
     * @param nombreDeCartes Le nombre de cartes à distribuer à chaque joueur.
     */
    private void distribuerCartes(int nombreDeCartes) {
        for (Joueur joueur : joueurs) {
            for (int i = 0; i < nombreDeCartes; i++) {
                Carte carte = plateau.getSource().remove(0);
                joueur.ajouterCarteDansMain(carte);
            }
        }
    }

    private void distribuerCartesPile(int nombreDeCartes) {
        for (Joueur joueur : joueurs) {
            for (int i = 0; i < nombreDeCartes; i++) {
                Carte carte = plateau.getSource().remove(0);
                joueur.ajouterCarteDansPile(carte);
            }
        }
    }

    /**
     * Distribue les cartes aux joueurs.
     */
    public void distribuerMain() {
        distribuerCartes(4);
    }

    /**
     * Distribue une pile initiale de cartes aux joueurs.
     * Chaque joueur reçoit deux cartes de la source du plateau.
     */
    public void distribuerPileInitiale() {
        distribuerCartesPile(2);
    }

    /**
     * Cette méthode permet de désigner le joueur malchanceux qui commencera la
     * partie.
     * Les joueurs lancent chacun un dé et celui qui obtient le plus petit nombre
     * commence la partie.
     * En cas d'égalité, les joueurs relancent les dés jusqu'à ce qu'il y ait un
     * gagnant.
     */
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

    /**
     * Renvoie le joueur actif.
     *
     * @return le joueur actif
     */
    public Joueur getJoueurActif() {
        return joueurActif;
    }

    /**
     * Renvoie le joueur rival.
     *
     * @return le joueur rival
     */
    public Joueur getJoueurRival() {
        if (joueurActif == joueurs.get(0)) {
            return joueurs.get(1);
        } else {
            return joueurs.get(0);
        }
    }

    /**
     * Réexécute le tour du joueur spécifié.
     * 
     * @param joueur le joueur dont le tour doit être rejoué
     */
    public void rejouer(Joueur joueur) {
        effectuerActionsTour(joueur, true);
    }

    /**
     * Renvoie une liste contenant les trois premières cartes de la source du
     * plateau.
     * 
     * @return une liste contenant les trois premières cartes de la source du
     *         plateau
     */
    public ArrayList<Carte> afficher3PremieresCartesSource() {
        ArrayList<Carte> cartes = new ArrayList<Carte>();
        for (int i = 0; i < 3; i++) {
            cartes.add(plateau.getSource().get(i));
        }
        return cartes;
    }

    /**
     * Renvoie la liste des cartes de la source.
     *
     * @return la liste des cartes de la source
     */
    public List<Carte> getCartesSource() {
        return plateau.getSource();
    }

    /**
     * Pioche une carte depuis la source et l'ajoute à la main du joueur.
     * 
     * @param joueur le joueur qui pioche la carte
     */
    public void piocherSourceMain(Joueur joueur) {
        joueur.ajouterCarteDansMain(plateau.getSource().remove(0));
        if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage(
                    "Vous avez pioché la carte " + joueur.getMain().get(joueur.getMain().size() - 1).getNom());
        }
    }

    /**
     * Pioche une carte de la source du plateau et l'ajoute dans la pile du joueur.
     * 
     * @param joueur le joueur dont la pile doit recevoir la carte piochée
     */
    public void piocherSourcePile(Joueur joueur) {
        joueur.ajouterCarteDansPile(plateau.getSource().remove(0));
    }

    /**
     * Renvoie le joueur 1.
     * Si aucun joueur n'est présent dans la liste des joueurs, un nouveau joueur
     * avec le nom "Joueur 1" est créé et ajouté à la liste.
     * 
     * @return le joueur 1
     */
    public Joueur getJoueur1() {
        if (joueurs.size() < 1) {
            joueurs.add(new Joueur("Joueur 1"));
        }
        return joueurs.get(0);
    }

    /**
     * Renvoie le deuxième joueur de la partie.
     * Si le nombre de joueurs est inférieur à 2, un nouveau joueur est ajouté à la
     * liste des joueurs.
     * 
     * @return le deuxième joueur de la partie
     */
    public Joueur getJoueur2() {
        if (joueurs.size() < 2) {
            joueurs.add(new Joueur("Joueur 2"));
        }
        return joueurs.get(1);
    }

    /**
     * Renvoi le plateau de la partie.
     *
     * @return le plateau de la partie
     */

    public Plateau getPlateau() {
        return plateau;
    }

    /**
     * Lance les dés pour déterminer le joueur actif et le joueur qui commence.
     * Les dés sont lancés pour les deux joueurs jusqu'à ce que les résultats des
     * lancers soient différents.
     * Le résultat de chaque lancer est stocké dans les variables
     * resultatLanceJoueur1 et resultatLanceJoueur2.
     * Le joueur actif est déterminé en fonction du résultat du lancer.
     * Le joueur qui commence est également déterminé en fonction du résultat du
     * lancer.
     */
    public void lancerDes() {
        int resultatLanceJoueur1 = 0, resultatLanceJoueur2 = 0;
        while (resultatLanceJoueur1 == resultatLanceJoueur2) {
            resultatLanceJoueur1 = (int) (Math.random() * 6) + 1;
            resultatLanceJoueur2 = (int) (Math.random() * 6) + 1;
        }
        this.resultatLanceJoueur1 = resultatLanceJoueur1;
        this.resultatLanceJoueur2 = resultatLanceJoueur2;
        if (resultatLanceJoueur1 < resultatLanceJoueur2) {
            joueurActif = getJoueur1();
            joueurCommence = getJoueur1().getPseudo();
        } else {
            joueurActif = getJoueur2();
            joueurCommence = getJoueur2().getPseudo();
        }
    }

    /**
     * Renvoie le résultat du lancer du joueur 1.
     *
     * @return le résultat du lancer du joueur 1
     */
    public int getResultatLanceJoueur1() {
        return resultatLanceJoueur1;
    }

    /**
     * Renvoie le résultat du lancer du joueur 2.
     *
     * @return le résultat du lancer du joueur 2
     */
    public int getResultatLanceJoueur2() {
        return resultatLanceJoueur2;
    }

    /**
     * Renvoie le joueur qui commence la partie.
     *
     * @return le joueur qui commence la partie
     */
    public String getJoueurCommence() {
        return joueurCommence;
    }

    /**
     * Modifie le pseudo du joueur 1.
     * 
     * @param pseudo Le nouveau pseudo du joueur 1.
     */
    public void setJoueur1Pseudo(String pseudo) {
        getJoueur1().setPseudo(pseudo);
    }

    /**
     * Modifie le pseudo du joueur 2.
     * Si le pseudo est "Bot", crée un nouveau joueur bot avec ce pseudo.
     * 
     * @param pseudo Le nouveau pseudo du joueur 2.
     */
    public void setJoueur2Pseudo(String pseudo) {
        if (pseudo.equals("Bot")) {
            getJoueur2().setPseudo(pseudo);
            joueurs.set(1, new JoueurBot(pseudo));
        } else {
            getJoueur2().setPseudo(pseudo);
        }

    }

    /**
     * Définit le joueur actif de la partie.
     * 
     * @param joueur Le joueur actif à définir.
     */
    public void setJoueurActif(Joueur joueur) {
        // Vérifier si l'état du jeu est a terminé alors reinitialiser tous les éléments
        // du jeux

        joueurActif = joueur;
    }

    /**
     * Joue une carte pour obtenir des points.
     * 
     * @param carte la carte à jouer
     */
    public void jouerCartePoints(Carte carte) {
        joueurActif.jouerCartePourPoints(carte);
    }

    /**
     * Joue une carte pour le futur du joueur actif.
     * 
     * @param carte la carte à jouer pour le futur
     */
    public void jouerCarteVieFuture(Carte carte) {
        joueurActif.jouerCartePourFutur(carte);
    }

    /**
     * Renvoie le joueur correspondant au pseudo donné.
     * 
     * @param pseudo Le pseudo du joueur recherché.
     * @return Le joueur correspondant au pseudo donné.
     */
    public Joueur getJoueurPseudo(String pseudo) {
        if (getJoueur1().getPseudo().equals(pseudo)) {
            return getJoueur1();
        } else {
            return getJoueur2();
        }

    }

}
