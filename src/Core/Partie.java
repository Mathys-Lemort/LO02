package Core;

import java.util.*;
import java.util.stream.Collectors;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import Cartes.*;
import Joueurs.*;
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


    public void initialiserPartie() {
        Affichage.afficherTitre("Initialisation de la Partie");
    
        if (!(this.getMode().equals(Mode.GRAPHIQUE))) {
            File sauvegarde = new File("src/sauvegarde.txt");
            if (sauvegarde.exists()) {
                Affichage.afficherMessage("Voulez-vous charger une partie sauvegardée?");
                Affichage.afficherOption(1, "Oui");
                Affichage.afficherOption(2, "Non");
                int choix = scanner.nextInt();
                scanner.nextLine();
                if (choix == 1) {
                    chargerPartie();
                    return; 
                }
            }
            creerJoueurs();
            this.designerMalchanceux();
        }
    
        this.distribuerMain();
        this.distribuerPileInitiale();
    }
    
    private void creerJoueurs() {
        Affichage.afficherMessage("Voulez-vous jouer contre un Bot?");
        Affichage.afficherOption(1, "Oui");
        Affichage.afficherOption(2, "Non");
        int choix = scanner.nextInt();
        scanner.nextLine();
        if (choix == 1) {
            joueurs.add(new JoueurBot("Bot"));
            Affichage.afficherMessage("Entrez votre pseudo:");
            String pseudo = scanner.nextLine();
            Joueur joueur = new Joueur(pseudo);
            this.joueurs.add(joueur);
            
        } else {
            for (int i = 1; i <= 2; i++) {
                Affichage.afficherMessage("Entrez le pseudo du joueur " + i + ":");
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
                    i--; 
                } else {
                    Joueur joueur = new Joueur(pseudo);
                    this.joueurs.add(joueur);
                }
            }
        }
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
                    sauvegarderPartie();
                    System.exit(0);
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
            scanner.nextLine();
            if (choix < 1 || choix > nombreDeCartes) {
                Affichage.afficherMessage(
                        "Choix non valide. Veuillez choisir un numéro entre 1 et " + nombreDeCartes + ".");
            }
        } while (choix < 1 || choix > nombreDeCartes);
        return choix;
    }

    public void tourSuivant() {
        if (joueurActif == null || !joueurs.contains(joueurActif)) {
            designerMalchanceux();
        } else {
            int currentIndex = joueurs.indexOf(joueurActif);
            joueurActif = joueurs.get((currentIndex + 1) % joueurs.size());
        }

        if (joueurActif.mainVide() && joueurActif.pileVide()) {
            Affichage.afficherTitre("Réincarnation de " + joueurActif.getPseudo());
            Affichage.afficherMessage("Vous n'avez plus de cartes dans votre main ni dans votre pile.");
         
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
        initialiserPartie(); 

        this.setEtatPartie(EtatPartie.EN_COURS);

        while (this.etatPartie == EtatPartie.EN_COURS) {
            tourSuivant(); 
        }

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
        if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage(
                    "Vous avez pioché la carte " + joueur.getMain().get(joueur.getMain().size() - 1).getNom());
        }
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


    public void sauvegarderPartie() {
        System.out.println("Joueur1: " + getJoueur1().getPseudo());
        System.out.println("Joueur2: " + getJoueur2().getPseudo());
        System.out.println("JoueurActif: " + getJoueurActif().getPseudo());
        System.out.println("ResultatLanceJoueur1: " + getResultatLanceJoueur1());
        System.out.println("ResultatLanceJoueur2: " + getResultatLanceJoueur2());

        try (FileWriter myWriter = new FileWriter("src/sauvegarde.txt")) {
            myWriter.write("Joueur1: " + getJoueur1().getPseudo() + "\n");
            myWriter.write("Joueur2: " + getJoueur2().getPseudo() + "\n");
            myWriter.write("JoueurActif: " + getJoueurActif().getPseudo() + "\n");
            myWriter.write("ResultatLanceJoueur1: " + getResultatLanceJoueur1() + "\n");
            myWriter.write("ResultatLanceJoueur2: " + getResultatLanceJoueur2() + "\n");
            myWriter.write("EtatPartie: " + getEtatPartie() + "\n");
            myWriter.write("Mode: " + getMode() + "\n");
            myWriter.write("Rejouer: " + getRejouer() + "\n");
            myWriter.write("Source: " + cartesToString(getCartesSource()) + "\n");
            myWriter.write("Joueur1Main: " + cartesToString(getJoueur1().getMain()) + "\n");
            myWriter.write("Joueur1Pile: " + cartesToString(getJoueur1().getPile()) + "\n");
            myWriter.write("Joueur1VieFuture: " + cartesToString(getJoueur1().getVieFuture()) + "\n");
            myWriter.write("Joueur1Oeuvre: " + cartesToString(getJoueur1().getOeuvres()) + "\n");
            myWriter.write("Joueur1Fosse: " + cartesToString(getJoueur1().getFosse()) + "\n");
            myWriter.write("Joueur2Main: " + cartesToString(getJoueur2().getMain()) + "\n");
            myWriter.write("Joueur2Pile: " + cartesToString(getJoueur2().getPile()) + "\n");
            myWriter.write("Joueur2VieFuture: " + cartesToString(getJoueur2().getVieFuture()) + "\n");
            myWriter.write("Joueur2Oeuvre: " + cartesToString(getJoueur2().getOeuvres()) + "\n");
            myWriter.write("Joueur2Fosse: " + cartesToString(getJoueur2().getFosse()) + "\n");
            myWriter.write("Joueur1Position: " + getJoueur1().getPositionEchelleKarmique() + "\n");
            myWriter.write("Joueur2Position: " + getJoueur2().getPositionEchelleKarmique() + "\n");

        } catch (IOException e) {
            Affichage.afficherMessage("Une erreur est survenue lors de la sauvegarde de la partie.");
            e.printStackTrace();
        }
    }

    private String cartesToString(List<Carte> cartes) {
        return cartes.stream()
                     .map(Carte::getNom)
                     .collect(Collectors.joining(" | "));
      
    }
    
    

    public void chargerPartie() {
        try (Scanner scanner = new Scanner(new File("src/sauvegarde.txt"))) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] dataSplit = data.split(": ");
                if (dataSplit.length < 2) {
                    System.out.println("Format de ligne incorrect : " + data);
                    continue; 
                }
                switch (dataSplit[0]) {
                    case "Joueur1":
                        setJoueur1Pseudo(dataSplit[1]);
                        break;
                    case "Joueur2":
                        setJoueur2Pseudo(dataSplit[1]);
                        break;
                    case "JoueurActif":
                        setJoueurActif(getJoueurPseudo(dataSplit[1]));
                        break;
                    case "ResultatLanceJoueur1":
                        resultatLanceJoueur1 = Integer.parseInt(dataSplit[1]);
                        break;
                    case "ResultatLanceJoueur2":
                        resultatLanceJoueur2 = Integer.parseInt(dataSplit[1]);
                        break;
                    case "EtatPartie":
                        setEtatPartie(EtatPartie.valueOf(dataSplit[1]));
                        break;
                    case "Mode":
                        setMode(Mode.valueOf(dataSplit[1]));
                        break;
                    case "Rejouer":
                        setRejouer(Boolean.parseBoolean(dataSplit[1]));
                        break;
                    case "Source":
                        chargerCartes(plateau.getSource(), dataSplit[1]);
                        break;
                    case "Joueur1Main":
                        chargerCartes(getJoueur1().getMain(), dataSplit[1]);
                        break;
                    case "Joueur1Pile":
                        chargerCartes(getJoueur1().getPile(), dataSplit[1]);
                        break;
                    case "Joueur1VieFuture":
                        chargerCartes(getJoueur1().getVieFuture(), dataSplit[1]);
                        break;
                    case "Joueur1Oeuvre":
                        chargerCartes(getJoueur1().getOeuvres(), dataSplit[1]);
                        break;
                    case "Joueur1Fosse":
                        chargerCartes(getJoueur1().getFosse(), dataSplit[1]);
                        break;
                    case "Joueur2Main":
                        chargerCartes(getJoueur2().getMain(), dataSplit[1]);
                        break;
                    case "Joueur2Pile":
                        chargerCartes(getJoueur2().getPile(), dataSplit[1]);
                        break;
                    case "Joueur2VieFuture":
                        chargerCartes(getJoueur2().getVieFuture(), dataSplit[1]);
                        break;
                    case "Joueur2Oeuvre":
                        chargerCartes(getJoueur2().getOeuvres(), dataSplit[1]);
                        break;
                    case "Joueur2Fosse":
                        chargerCartes(getJoueur2().getFosse(), dataSplit[1]);
                        break;
                    case "Joueur1Position":
                        getJoueur1().setPositionEchelleKarmique(dataSplit[1]);
                        break;
                    case "Joueur2Position":
                        getJoueur2().setPositionEchelleKarmique(dataSplit[1]);
                        break;
                    default:
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void chargerCartes(List<Carte> listeCartes, String dataCartes) {
        String[] nomsCartes = dataCartes.split(" \\| ");
        for (String nomCarte : nomsCartes) {
            Carte carte = creerCarte(nomCarte);
            if (carte != null) {
                listeCartes.add(carte);
            }
        }
        
    }
    


    private Carte creerCarte(String carteInfo) {
        String[] carteElements = carteInfo.split(" - Couleur: |, Points: |, Pouvoir: ");
        String nom = carteElements[0].trim();
        switch (nom) {
            case "Bassesse":
                return new Bassesse();
            case "Coup d'Œil":
                return new CoupdOeil();
            case "Crise":
                return new Crise();
            case "Deni":
                return new Deni();
            case "Dernier Souffle":
                return new DernierSouffle();
            case "Destinee":
                return new Destinee();
            case "Duperie":
                return new Duperie();
            case "Fournaise":
                return new Fournaise();
            case "Incarnation":
                return new Incarnation();
            case "Jubile":
                return new Jubile();
            case "Lendemain":
                return new Lendemain();
            case "Longevite":
                return new Longevite();
            case "Mimetisme":
                return new Mimetisme();
            case "Panique":
                return new Panique();
            case "Recyclage":
                return new Recyclage();
            case "Reves Brises":
                return new RevesBrises();
            case "Roulette":
                return new Roulette();
            case "Sauvetage":
                return new Sauvetage();
            case "Semis":
                return new Semis();
            case "Transmigration":
                return new Transmigration();
            case "Vengeance":
                return new Vengeance();
            case "Vol":
                return new Vol();
            case "Voyage":
                return new Voyage();
            default:
                return null;
        }
    }

}
