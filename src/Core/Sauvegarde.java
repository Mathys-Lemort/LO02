package Core;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Cartes.*;

public class Sauvegarde {
    /**
     * Cette méthode permet de sauvegarder les informations de la partie dans un
     * fichier.
     * Les informations sauvegardées incluent les pseudonymes des joueurs, le joueur
     * actif,
     * les résultats des lancers de dés, l'état de la partie, la possibilité de
     * rejouer,
     * les cartes sources, les mains, les piles, les vies futures, les oeuvres et
     * les fosses
     * des joueurs, ainsi que les positions des joueurs sur l'échelle karmique.
     * Les informations sont écrites dans le fichier "src/sauvegarde.txt".
     * En cas d'erreur lors de la sauvegarde, une exception IOException est levée et
     * une
     * message d'erreur est affiché.
     */
    public static void sauvegarderPartie(Partie partie) {
        try (FileWriter myWriter = new FileWriter("src/sauvegarde.txt")) {
            myWriter.write("Joueur1: " + partie.getJoueur1().getPseudo() + "\n");
            myWriter.write("Joueur2: " + partie.getJoueur2().getPseudo() + "\n");
            myWriter.write("JoueurActif: " + partie.getJoueurActif().getPseudo() + "\n");
            myWriter.write("EtatPartie: " + partie.getEtatPartie() + "\n");
            myWriter.write("Rejouer: " + partie.getRejouer() + "\n");
            myWriter.write("Source: " + cartesToString(partie.getCartesSource()) + "\n");
            myWriter.write("Joueur1Main: " + cartesToString(partie.getJoueur1().getMain()) + "\n");
            myWriter.write("Joueur1Pile: " + cartesToString(partie.getJoueur1().getPile()) + "\n");
            myWriter.write("Joueur1VieFuture: " + cartesToString(partie.getJoueur1().getVieFuture()) + "\n");
            myWriter.write("Joueur1Oeuvre: " + cartesToString(partie.getJoueur1().getOeuvres()) + "\n");
            myWriter.write("Joueur1Fosse: " + cartesToString(partie.getJoueur1().getFosse()) + "\n");
            myWriter.write("Joueur2Main: " + cartesToString(partie.getJoueur2().getMain()) + "\n");
            myWriter.write("Joueur2Pile: " + cartesToString(partie.getJoueur2().getPile()) + "\n");
            myWriter.write("Joueur2VieFuture: " + cartesToString(partie.getJoueur2().getVieFuture()) + "\n");
            myWriter.write("Joueur2Oeuvre: " + cartesToString(partie.getJoueur2().getOeuvres()) + "\n");
            myWriter.write("Joueur2Fosse: " + cartesToString(partie.getJoueur2().getFosse()) + "\n");
            myWriter.write("Joueur1Position: " + partie.getJoueur1().getPositionEchelleKarmique() + "\n");
            myWriter.write("Joueur2Position: " + partie.getJoueur2().getPositionEchelleKarmique() + "\n");

        } catch (IOException e) {
            Affichage.afficherMessage("Une erreur est survenue lors de la sauvegarde de la partie.");
            e.printStackTrace();
        }
    }

    /**
     * Convertit une liste de cartes en une chaîne de caractères.
     * 
     * @param cartes la liste de cartes à convertir
     * @return une chaîne de caractères représentant les noms des cartes, séparés
     *         par des barres verticales
     */
    private static String cartesToString(List<Carte> cartes) {
        return cartes.stream()
                .map(Carte::getNom)
                .collect(Collectors.joining(" | "));
    }

    /**
     * Charge les données d'une partie à partir d'un fichier de sauvegarde.
     * Les données sont lues ligne par ligne et les informations sont extraites et
     * utilisées pour restaurer l'état de la partie.
     * Les informations sont stockées dans les différents attributs de l'objet
     * Partie.
     * Si une ligne du fichier de sauvegarde est mal formatée, un message d'erreur
     * est affiché et la ligne est ignorée.
     * 
     * @throws FileNotFoundException si le fichier de sauvegarde n'est pas trouvé.
     */
    public static void chargerPartie(Partie partie) {
        try (Scanner scanner = new Scanner(new File("src/sauvegarde.txt"))) {
            while (scanner.hasNextLine()) {
                String data = scanner.nextLine();
                String[] dataSplit = data.split(": ");
                if (dataSplit.length < 2) {
                    continue;
                }
                switch (dataSplit[0]) {
                    case "Joueur1":
                        partie.setJoueur1Pseudo(dataSplit[1]);
                        break;
                    case "Joueur2":
                        partie.setJoueur2Pseudo(dataSplit[1]);
                        break;
                    case "JoueurActif":
                        partie.setJoueurActif(partie.getJoueurPseudo(dataSplit[1]));
                        break;
                    case "EtatPartie":
                        partie.setEtatPartie(EtatPartie.valueOf(dataSplit[1]));
                        break;
                    case "Rejouer":
                        partie.setRejouer(Boolean.parseBoolean(dataSplit[1]));
                        break;
                    case "Source":
                        chargerCartes(partie.getPlateau().getSource(), dataSplit[1]);
                        break;
                    case "Joueur1Main":
                        chargerCartes(partie.getJoueur1().getMain(), dataSplit[1]);
                        break;
                    case "Joueur1Pile":
                        chargerCartes(partie.getJoueur1().getPile(), dataSplit[1]);
                        break;
                    case "Joueur1VieFuture":
                        chargerCartes(partie.getJoueur1().getVieFuture(), dataSplit[1]);
                        break;
                    case "Joueur1Oeuvre":
                        chargerCartes(partie.getJoueur1().getOeuvres(), dataSplit[1]);
                        break;
                    case "Joueur1Fosse":
                        chargerCartes(partie.getJoueur1().getFosse(), dataSplit[1]);
                        break;
                    case "Joueur2Main":
                        chargerCartes(partie.getJoueur2().getMain(), dataSplit[1]);
                        break;
                    case "Joueur2Pile":
                        chargerCartes(partie.getJoueur2().getPile(), dataSplit[1]);
                        break;
                    case "Joueur2VieFuture":
                        chargerCartes(partie.getJoueur2().getVieFuture(), dataSplit[1]);
                        break;
                    case "Joueur2Oeuvre":
                        chargerCartes(partie.getJoueur2().getOeuvres(), dataSplit[1]);
                        break;
                    case "Joueur2Fosse":
                        chargerCartes(partie.getJoueur2().getFosse(), dataSplit[1]);
                        break;
                    case "Joueur1Position":
                        partie.getJoueur1().setPositionEchelleKarmique(dataSplit[1]);
                        break;
                    case "Joueur2Position":
                        partie.getJoueur2().setPositionEchelleKarmique(dataSplit[1]);
                        break;
                    default:
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Charge les cartes à partir d'une chaîne de caractères et les ajoute à la
     * liste de cartes donnée.
     * 
     * @param listeCartes la liste de cartes à laquelle les cartes doivent être
     *                    ajoutées
     * @param dataCartes  la chaîne de caractères contenant les noms des cartes à
     *                    charger
     */
    private static void chargerCartes(List<Carte> listeCartes, String dataCartes) {
        String[] nomsCartes = dataCartes.split(" \\| ");
        for (String nomCarte : nomsCartes) {
            Carte carte = creerCarte(nomCarte);
            if (carte != null) {
                listeCartes.add(carte);
            }
        }
    }

    /**
     * Crée une carte à partir d'une chaîne de caractères.
     * 
     * @param carteInfo la chaîne de caractères contenant les informations de la
     *                  carte
     * @return la carte créée
     */
    private static Carte creerCarte(String carteInfo) {
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