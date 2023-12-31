package Joueurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Cartes.Carte;
import Core.Affichage;
import Core.Partie;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;

//Faire une enum des positions de l'échelle karmique

public class Joueur {
    private String id;
    private List<Carte> main;
    private List<Carte> pile;
    private List<Carte> defausse;
    private List<Carte> Oeuvres;
    private List<Carte> vieFuture;
    private EchelleKarmique positionEchelleKarmique;
    private int anneauxKarmiques;

    public enum EchelleKarmique {
        BOUSIER(0),
        SERPENT(4), // 4 est le nombre de points nécessaires pour passer à l'étape suivante
        LOUP(5),
        SINGE(6),
        TRANSCEANDANCE(7);

        private final int pointsPourAvancer;

        EchelleKarmique(int points) {
            this.pointsPourAvancer = points;
        }

        public int getPointsPourAvancer() {
            return this.pointsPourAvancer;
        }
    }

    // Constructeur
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

    public void setPseudo(String pseudo) {
        this.id = pseudo;
    }

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

    public String getPositionEchelleKarmique() {
        return this.positionEchelleKarmique.name();
    }

    public void reincarnation() {
        String message;

        // Vérifier si le joueur a assez de points pour passer à l'étape suivante
        if (this.getPoints() >= this.positionEchelleKarmique.getPointsPourAvancer()) {
            // Passer à l'étape suivante
            this.positionEchelleKarmique = EchelleKarmique.values()[this.positionEchelleKarmique.ordinal() + 1];
            message = this.getPseudo() + ", vous passez maintenant à l'étape " + this.positionEchelleKarmique.name()
                    + " de l'échelle karmique";
        } else {
            // Rajouter un anneau karmique
            this.anneauxKarmiques++;
            message = this.getPseudo()
                    + ", vous avez gagné un anneau karmique mais vous restez à la même position de l'échelle karmique";
        }

        // Afficher le message selon le mode de jeu
        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réincarnation");
            alert.setHeaderText("Réincarnation");

            Label label = new Label(message);
            label.setWrapText(true); // Autorise le texte à revenir à la ligne
            alert.getDialogPane().setContent(label);

            alert.showAndWait();
        } else {
            Affichage.afficherMessage(message);
        }

        // Gérer la réincarnation et la transceandance
        if (this.positionEchelleKarmique != EchelleKarmique.TRANSCEANDANCE) {
            preparerPourReincarnation();
        } else {
            celebrerTransceandance();
        }
    }

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

    private void celebrerTransceandance() {
        Affichage.afficherTitre(this.getPseudo() + " Transceandance !!");
        Partie.getInstance().terminerPartie();
    }

    public void jouerCartePourPoints(Carte carte) {
        this.Oeuvres.add(carte);
        this.main.remove(carte);
    }

    public String getPseudo() {
        return this.id;
    }

    public void demanderPrendreCarte(Joueur rival, Carte carte) {
        // Si rival est un bot, il choisit aléatoirement entre les deux options
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

    private int afficherDialogueGraphique(Joueur joueur, String message, String titre) {
        // Si le joueur est un bot, il choisit aléatoirement entre les deux options
        if (joueur instanceof JoueurBot) {
            return (int) (Math.random() * 2) + 1;
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>("- Oui", "- Oui", "- Non");
        dialog.setTitle(titre);
        dialog.setHeaderText(joueur.getPseudo() + ", Attention !");
        dialog.setContentText(message);
        // masquer et desactiver le bouton "non" 
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setVisible(false);
        dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setManaged(false);
        // Desactiver la croix pour fermer la fenetre
        dialog.getDialogPane().getScene().getWindow().setOnCloseRequest(event -> event.consume());


        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get().equals("- Oui") ? 1 : 2;
        }
        return -1; // Gérer le cas où l'utilisateur n'a pas fait de choix
    }

    public void jouerCartePourPouvoir(Carte carte, Joueur rival) {
        this.main.remove(carte);
        demanderPrendreCarte(rival, carte);
        carte.action(this, rival);
    }

    public void jouerCartePourFutur(Carte carte) {
        this.vieFuture.add(carte);
        this.main.remove(carte);
    }

    public void defausser() {
        this.defausse.add(this.main.get(0));
        this.main.remove(0);

    }

    public void defausserCarte(int nbCartes) {
        for (int i = 0; i < nbCartes; i++) {
            int index = (int) (Math.random() * this.main.size());
            this.defausse.add(this.main.get(index));
            this.main.remove(index);
        }

    }

    public void afficherMain() {
        String message = "";
        for (int i = 0; i < this.main.size(); i++) {
            message += (i + 1) + " - " + this.main.get(i).toString() + "\n";
        }
        Affichage.afficherMessage(message);

    }

    public List<Carte> getMain() {
        return this.main;
    }

    public List<Carte> getOeuvres() {
        return this.Oeuvres;
    }

    public List<Carte> getVieFuture() {
        return this.vieFuture;
    }

    public List<Carte> getDefausse() {
        return this.defausse;
    }

    public List<Carte> getPile() {
        return this.pile;
    }

    public boolean pileVide() {
        return this.pile.isEmpty();
    }

    public boolean mainVide() {
        return this.main.isEmpty();
    }

    public void ajouterCarte() {
        this.main.add(this.pile.remove(0));

    }

    public void ajouterCarteDansMain(Carte carte) {
        this.main.add(carte);

    }

    public void suppCarteMain(Carte carte) {
        this.main.remove(carte);

    }

    public void ajouterCarteDansPile(Carte carte) {
        this.pile.add(carte);

    }

    public void ajouterCarteDansDefausse(Carte carte) {
        this.defausse.add(carte);

    }

    public void ajouterCarteDansOeuvres(Carte carte) {
        this.Oeuvres.add(carte);

    }

    public void ajouterCarteDansVieFuture(Carte carte) {
        this.vieFuture.add(carte);

    }

    public void afficherCartesVieFuture() {
        String message = "";
        for (int i = 0; i < this.vieFuture.size(); i++) {
            message += (i + 1) + " - " + this.vieFuture.get(i).toString() + "\n";
        }
        Affichage.afficherMessage(message);

    }

    public void afficherCartesOeuvres() {
        String message = "";
        for (int i = 0; i < this.Oeuvres.size(); i++) {
            message += (i + 1) + " - " + this.Oeuvres.get(i).toString() + "\n";
        }
        Affichage.afficherMessage(message);
    }

    public void defausserOeuvreChoix() {
        Affichage.afficherMessage("Voici vos oeuvres:");
        afficherCartesOeuvres();
        Affichage.afficherMessage("Choisissez une carte à défausser:");
        Scanner scanner = Partie.getInstance().getScanner();
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()
        Carte carte = this.Oeuvres.get(choix - 1);
        this.defausse.add(carte);
        this.Oeuvres.remove(carte);
        
        Affichage.afficherMessage(
                this.getPseudo() + ", vous avez défaussé la carte " + carte.getNom() + " de vos oeuvres");
    }

    public void defausserCarteChoisit(Carte carte) {
        this.defausse.add(carte);
        this.main.remove(carte);
                if (!(this instanceof JoueurBot)) {

        Affichage.afficherMessage(
                this.getPseudo() + ", vous avez défaussé la carte " + carte.getNom() + " de votre main");
                }
    }

    public void passerTour() {
        Affichage.afficherMessage(this.getPseudo() + " passe son tour");
    }

    public void setStrategie() {
    }

    public String toString() {
        return this.id;
    }

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

    public void defausserCarteVieFutureChiffre(int nbCartes) {
        for (int i = 0; i < nbCartes; i++) {
            this.defausse.add(this.vieFuture.get(i));
            this.vieFuture.remove(i);
        }
    }

    public Carte getOeuvreExposee() {
        // Vérifier si il y a une oeuvre exposée si oui la return sinon return null
        if (this.Oeuvres.size() > 0) {
            return this.Oeuvres.get(0);
        } else {
            return null;
        }
    }

    public void defausserCartePile() {
                if (!(this instanceof JoueurBot)) {

        Affichage.afficherMessage("Vous avez défaussé la carte " + this.pile.get(0).getNom() + " de la pile");
                }
        this.defausse.add(this.pile.get(0));
        this.pile.remove(0);
    }

    public ArrayList<Carte> getCartesFosse(int nbCartes) {
        ArrayList<Carte> temp = new ArrayList<>();
        // Limitez le nombre de cartes à récupérer à la taille de la liste 'defausse'
        int actualNbCartes = Math.min(nbCartes, this.defausse.size());
        for (int i = 0; i < actualNbCartes; i++) {
            temp.add(this.defausse.get(i));
        }
        return temp;
    }

    public List<Carte> getFosse() {
        return this.defausse;
    }

    public Carte getCarteVieFuture(int index) {
        return this.vieFuture.get(index);
    }

    public Carte getCarteVieFutureRandom() {
        int index = (int) (Math.random() * this.vieFuture.size());
        return this.vieFuture.get(index);
    }

    public void defausserOeuvreChoisit(Carte carte) {
        this.defausse.add(carte);
        this.Oeuvres.remove(carte);
    }

}
