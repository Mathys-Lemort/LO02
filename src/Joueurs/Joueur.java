package Joueurs;

import java.util.*;
import Cartes.Carte;
import Core.Affichage;
import Core.Partie;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;

/**
 * Classe représentant un joueur dans le jeu. Elle gère les différentes collections de cartes
 * comme la main, la pile, la défausse, les œuvres et la vie future. Elle gère également la position
 * du joueur sur l'échelle karmique et le nombre d'anneaux karmiques.
 */

public class Joueur {
    // Attributs de la classe Joueur
    private String id;
    private List<Carte> main;
    private List<Carte> pile;
    private List<Carte> defausse;
    private List<Carte> Oeuvres;
    private List<Carte> vieFuture;
    private EchelleKarmique positionEchelleKarmique;
    private int anneauxKarmiques;
    /**
     * Enumération pour les différentes positions sur l'échelle karmique.
     */
    public enum EchelleKarmique {
        BOUSIER(0),
        SERPENT(4),
        LOUP(5),
        SINGE(6),
        TRANSCEANDANCE(7);

        private final int pointsPourAvancer;

        EchelleKarmique(int points) {
            this.pointsPourAvancer = points;
        }
        /**
         * Obtient le nombre de points nécessaires pour avancer sur l'échelle.
         * @return Le nombre de points requis pour l'avancement.
         */
        public int getPointsPourAvancer() {
            return this.pointsPourAvancer;
        }
    }
     /**
     * Constructeur pour créer un joueur.
     * @param id Identifiant unique du joueur.
     */
    public Joueur(String id) {
        this.id = id;
        this.main = new ArrayList<>();
        this.pile = new ArrayList<>();
        this.defausse = new ArrayList<>();
        this.Oeuvres = new ArrayList<>();
        this.vieFuture = new ArrayList<>();
        this.anneauxKarmiques = 0;
        this.positionEchelleKarmique = EchelleKarmique.BOUSIER;

    }
    /**
     * Définit le pseudo du joueur.
     * @param pseudo Le nouveau pseudo du joueur.
     */
    public void setPseudo(String pseudo) {
        this.id = pseudo;
    }
    /**
     * Définit le nombre d'anneaux karmiques du joueur.
     * @param anneauxKarmiques Le nouveau nombre d'anneaux karmiques du joueur.
     */

    public void setPositionEchelleKarmique(String position) {
        this.positionEchelleKarmique = EchelleKarmique.valueOf(position);
    }
    /**
     * Obtient les points du joueur.
     * @return Le nombre de points du joueur.
     */
    public int getPoints() {
        int pointsRouge = 0;
        int pointsVert = 0;
        int pointsBleu = 0;
        for (Carte carte : this.Oeuvres) {
            if (carte.getCouleur().equals("rouge")) {
                pointsRouge += carte.getPoints();
            } else if (carte.getCouleur().equals("vert")) {
                pointsVert += carte.getPoints();
            } else if (carte.getCouleur().equals("bleu")) {
                pointsBleu += carte.getPoints();
            } else {
                pointsRouge += carte.getPoints();
                pointsVert += carte.getPoints();
                pointsBleu += carte.getPoints();
            }
        }
        pointsRouge += this.anneauxKarmiques;
        pointsVert += this.anneauxKarmiques;
        pointsBleu += this.anneauxKarmiques;
        return Math.max(pointsRouge, Math.max(pointsVert, pointsBleu));
    }
    /**
     * Obtient la position du joueur sur l'échelle karmique.
     * @return La position du joueur sur l'échelle karmique.
     */
    public String getPositionEchelleKarmique() {
        return this.positionEchelleKarmique.name();
    }
    /**
     * Réincarne le joueur en fonction de sa position sur l'échelle karmique.
     */

    public void reincarnation() {
        String message;

        if (this.getPoints() >= this.positionEchelleKarmique.getPointsPourAvancer()) {
            this.positionEchelleKarmique = EchelleKarmique.values()[this.positionEchelleKarmique.ordinal() + 1];
            message = this.getPseudo() + ", vous passez maintenant à l'étape " + this.positionEchelleKarmique.name()
                    + " de l'échelle karmique";
        } else {
            this.anneauxKarmiques++;
            message = this.getPseudo()
                    + ", vous avez gagné un anneau karmique mais vous restez à la même position de l'échelle karmique";
        }

        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réincarnation");
            alert.setHeaderText("Réincarnation");

            Label label = new Label(message);
            label.setWrapText(true); 
            alert.getDialogPane().setContent(label);

            alert.showAndWait();
        } else {
            Affichage.afficherMessage(message);
        }

        if (this.positionEchelleKarmique != EchelleKarmique.TRANSCEANDANCE) {
            preparerPourReincarnation();
        } else {
            celebrerTransceandance();
        }
    }
    /**
     * Prépare le joueur pour la réincarnation.
     */
    private void preparerPourReincarnation() {
        this.defausse.addAll(this.Oeuvres);
        this.Oeuvres.clear();
        this.main.addAll(this.vieFuture);
        this.vieFuture.clear();
        this.pile.clear();
        while ((this.main.size() + this.pile.size()) < 6) {
            Partie.getInstance().piocherSourcePile(this);
        }
    }
    /**
     * Célèbre la transceandance du joueur.
     */

    private void celebrerTransceandance() {
        Affichage.afficherTitre(this.getPseudo() + " Transceandance !!");
        Partie.getInstance().terminerPartie();
    }
    /**
     * Joue une carte pour des points.
     * @param carte La carte à jouer.
     */
    public void jouerCartePourPoints(Carte carte) {
        this.Oeuvres.add(carte);
        this.main.remove(carte);
    }
    /**
     * Obtient le pseudo du joueur.
     * @return Le pseudo du joueur.
     */
    public String getPseudo() {
        return this.id;
    }
    /**
     * Demande au joueur de choisir une carte à jouer pour son pouvoir.
     * @param rival Le joueur rival.
     * @param carte La carte à jouer.
     */
    public void demanderPrendreCarte(Joueur rival, Carte carte) {
        if (Partie.getInstance().getMode().equals(Partie.Mode.CONSOLE)) {
            Partie.getInstance().setJoueurActif(rival);
        } 
        if (rival instanceof JoueurBot) {
            int choix = (int) (Math.random() * 2) + 1;
            if (choix == 1) {
                rival.ajouterCarteDansVieFuture(carte);
            } else {
                rival.defausserCarteChoisit(carte);
            }
            return;
        }
        String message = rival.getPseudo() + ", voulez-vous mettre la carte "
                + carte.getNom() + " dans votre vie future ?";
        int choix = obtenirChoixUtilisateur(rival, message, "Choix de la carte à défausser");

        if (choix == 1) {
            rival.ajouterCarteDansVieFuture(carte);
        } else if (choix == 2) {
            rival.defausserCarteChoisit(carte);
        } else {
            Affichage.afficherMessage("Choix non valide. Veuillez réessayer avec un numéro valide.");
        }
    }
    /**
     * Demande au joueur de choisir une carte à jouer pour son pouvoir.
     * @param rival Le joueur rival.
     * @param carte La carte à jouer.
     */
    public int obtenirChoixUtilisateur(Joueur joueur, String message, String titre) {
        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            return afficherDialogueGraphique(joueur, message, titre);
        } else {
            Affichage.afficherMessage(message);
            Affichage.afficherOption(1, "- Oui");
            Affichage.afficherOption(2, "- Non");
            return Partie.getInstance().getScanner().nextInt();
        }
    }
    /**
     * Affiche un dialogue graphique pour demander au joueur de choisir une carte à jouer pour son pouvoir.
     * @param joueur Le joueur rival.
     * @param message Le message à afficher.
     * @param titre Le titre de la fenêtre.
     * @return Le choix du joueur.
     */
    private int afficherDialogueGraphique(Joueur joueur, String message, String titre) {
        if (joueur instanceof JoueurBot) {
            return (int) (Math.random() * 2) + 1;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("- Oui", "- Oui", "- Non");
        dialog.setTitle(titre);
        dialog.setHeaderText(joueur.getPseudo() + ", Attention !");
        dialog.setContentText(message);
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setManaged(false);
        dialog.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> event.consume());


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get().equals("- Oui") ? 1 : 2;
        }
        return -1; 
    }
    /**
     * Joue une carte pour son pouvoir.
     * @param carte La carte à jouer.
     * @param rival Le joueur rival.
     */
    public void jouerCartePourPouvoir(Carte carte, Joueur rival) {
        this.main.remove(carte);        
        demanderPrendreCarte(rival, carte);
        carte.action(this, rival);
    }
    /**
     * Joue une carte pour la vie future.
     * @param carte La carte à jouer.
     */
    public void jouerCartePourFutur(Carte carte) {
        this.vieFuture.add(carte);
        this.main.remove(carte);
    }
    /**
     * Défausse une carte de la main du joueur.
     */
    public void defausser() {
        this.defausse.add(this.main.get(0));
        this.main.remove(0);

    }
    /**
     * Défausse une carte de la main du joueur.
     * @param carte La carte à défausser.
     */
    public void defausserCarte(int nbCartes) {
        for (int i = 0; i < nbCartes; i++) {
            int index = (int) (Math.random() * this.main.size());
            this.defausse.add(this.main.get(index));
            this.main.remove(index);
        }

    }
    /**
     * Affiche les cartes de la main du joueur.
     */
    public void afficherMain() {
        String message = "";
        for (int i = 0; i < this.main.size(); i++) {
            message += (i + 1) + " - " + this.main.get(i).toString() + "\n";
        }
        Affichage.afficherMessage(message);

    }
    /**
     * Affiche les cartes de la main du joueur.
     * @return La liste des cartes de la main du joueur.
     */

    public List<Carte> getMain() {
        return this.main;
    }
    /**
     * Affiche les cartes des oeuvres du joueur.
     * @return La liste des cartes des oeuvres du joueur.
     */
    public List<Carte> getOeuvres() {
        return this.Oeuvres;
    }
    /**
     * Affiche les cartes de la vie future du joueur.
     * @return La liste des cartes de la vie future du joueur.
     */
    public List<Carte> getVieFuture() {
        return this.vieFuture;
    }
    /**
     * Affiche les cartes de la défausse du joueur.
     * @return La liste des cartes de la défausse du joueur.
     */
    public List<Carte> getDefausse() {
        return this.defausse;
    }
    /**
     * Affiche les cartes de la pile du joueur.
     * @return La liste des cartes de la pile du joueur.
     */
    public List<Carte> getPile() {
        return this.pile;
    }
    /**
     * renvoie vrai si la pile est vide.
     * @return Vrai si la pile est vide.
     */
    public boolean pileVide() {
        return this.pile.isEmpty();
    }
    /**
     * renvoie vrai si la main est vide.
     * @return Vrai si la main est vide.
     */
    public boolean mainVide() {
        return this.main.isEmpty();
    }
    /**
     * ajoute une carte à la main du joueur.
     */
    public void ajouterCarte() {
        this.main.add(this.pile.remove(0));

    }
    /**
     * ajoute une carte à la main du joueur.
     * @param carte La carte à ajouter.
     */
    public void ajouterCarteDansMain(Carte carte) {
        this.main.add(carte);

    }
    /**
     * Supprime une carte de la main du joueur.
     * @param carte La carte à supprimer.
     */
    public void suppCarteMain(Carte carte) {
        this.main.remove(carte);

    }
    /**
     * ajoute une carte à la pile du joueur.
     * @param carte La carte à ajouter.
     */
    public void ajouterCarteDansPile(Carte carte) {
        this.pile.add(carte);

    }
    /**
     * ajoute une carte à la défausse du joueur.
     * @param carte La carte à ajouter.
     */
    public void ajouterCarteDansDefausse(Carte carte) {
        this.defausse.add(carte);

    }
    /**
     * Ajoute une carte à la liste des oeuvres du joueur.
     * @param carte La carte à ajouter.
     */
    public void ajouterCarteDansOeuvres(Carte carte) {
        this.Oeuvres.add(carte);

    }
    /**
     * Ajoute une carte à la liste de la vie future du joueur.
     * @param carte La carte à ajouter.
     */
    public void ajouterCarteDansVieFuture(Carte carte) {
        this.vieFuture.add(carte);

    }
    /**
     * Affiche les cartes de la vie future du joueur.
     */
    public void afficherCartesVieFuture() {
        String message = "";
        for (int i = 0; i < this.vieFuture.size(); i++) {
            message += (i + 1) + " - " + this.vieFuture.get(i).toString() + "\n";
        }
        Affichage.afficherMessage(message);

    }
    /**
     * Affiche les cartes Oeuvres du joueur.
     */
    public void afficherCartesOeuvres() {
        String message = "";
        for (int i = 0; i < this.Oeuvres.size(); i++) {
            message += (i + 1) + " - " + this.Oeuvres.get(i).toString() + "\n";
        }
        Affichage.afficherMessage(message);
    }
    /**
     * Defausser une oeuvre du joueur.
     */
    public void defausserOeuvreChoix() {
        Affichage.afficherMessage("Voici vos oeuvres:");
        afficherCartesOeuvres();
        Affichage.afficherMessage("Choisissez une carte à défausser:");
        Scanner scanner = Partie.getInstance().getScanner();
        int choix = scanner.nextInt();
        scanner.nextLine(); 
        Carte carte = this.Oeuvres.get(choix - 1);
        this.defausse.add(carte);
        this.Oeuvres.remove(carte);
        
        Affichage.afficherMessage(
                this.getPseudo() + ", vous avez défaussé la carte " + carte.getNom() + " de vos oeuvres");
    }
    /**
     * Defausser une carte de la pile du joueur.
     * @param carte La carte à défausser.
     */
    public void defausserCarteChoisit(Carte carte) {
        this.defausse.add(carte);
        this.main.remove(carte);
                if (!(this instanceof JoueurBot)) {

        Affichage.afficherMessage(
                this.getPseudo() + ", vous avez défaussé la carte " + carte.getNom() + " de votre main");
                }
    }
    /**
     * Passer le tour du joueur.
     */
    public void passerTour() {
        Affichage.afficherMessage(this.getPseudo() + " passe son tour");
    }

    public void setStrategie() {
    }

    public String toString() {
        return this.id;
    }
    /**
     * Recupèr le chiffre d'une carte.  
     * @param carte La carte dont on veut récupèrer le chiffre.
     * @return Le chiffre de la carte.
     */
    public ArrayList<Carte> getMainChiffre(int nbCartes) {
        ArrayList<Carte> temp = new ArrayList<>();
        for (int i = 0; i < nbCartes; i++) {
            int index = (int) (Math.random() * this.main.size());
            if (!temp.contains(this.main.get(index))) {
                temp.add(this.main.get(index));
            } else {
                i--;
            }
        }
        return temp;
    }
    /**
     * Defausser une carte de la vie future du joueur.
     * @param carte La carte à défausser.
     */
    public void defausserCarteVieFutureChiffre(int nbCartes) {
        for (int i = 0; i < nbCartes; i++) {
            this.defausse.add(this.vieFuture.get(i));
            this.vieFuture.remove(i);
        }
    }
    /**
     * Recupere les oeuvres exposées du joueur.
     * @return La liste des oeuvres exposées du joueur.
     */
    public Carte getOeuvreExposee() {
        if (this.Oeuvres.size() > 0) {
            return this.Oeuvres.get(0);
        } else {
            return null;
        }
    }
    /**
     * Defausser une carte de la pile du joueur.
     */
    public void defausserCartePile() {
                if (!(this instanceof JoueurBot)) {

        Affichage.afficherMessage("Vous avez défaussé la carte " + this.pile.get(0).getNom() + " de la pile");
                }
        this.defausse.add(this.pile.get(0));
        this.pile.remove(0);
    }
    /**
     * Defausser une carte de la main du joueur.
     * @param carte La carte à défausser.
     */
    public ArrayList<Carte> getCartesFosse(int nbCartes) {
        ArrayList<Carte> temp = new ArrayList<>();
        int actualNbCartes = Math.min(nbCartes, this.defausse.size());
        for (int i = 0; i < actualNbCartes; i++) {
            temp.add(this.defausse.get(i));
        }
        return temp;
    }
    /**
     * Recupere les cartes de la fosse du joueur.
     * @return La liste des cartes de la fosse du joueur.
     */
    public List<Carte> getFosse() {
        return this.defausse;
    }
    /**
     * Recupere les cartes de la vie future du joueur.
     * @return La liste des cartes de la vie future du joueur.
     */
     */
    public Carte getCarteVieFuture(int index) {
        return this.vieFuture.get(index);
    }
    /**
     * recupere une carte aléatoire de la vie future du joueur.
     * @return La carte aléatoire de la vie future du joueur.
     */
    public Carte getCarteVieFutureRandom() {
        int index = (int) (Math.random() * this.vieFuture.size());
        return this.vieFuture.get(index);
    }
    /**
     * Méthode qui permet de défausser une carte de la main du joueur.
     * @param carte La carte à défausser.
     */
    public void defausserOeuvreChoisit(Carte carte) {
        this.defausse.add(carte);
        this.Oeuvres.remove(carte);
    }

}
