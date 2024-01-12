package Cartes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;

public abstract class Carte {
    private String nom;
    private String couleur;
    private int points;
    private String pouvoir;
    private boolean estMosaique;

    public Carte(String nom, String couleur, int points, String pouvoir, boolean estMosaique) {
        this.nom = nom;
        this.couleur = couleur;
        this.points = points;
        this.pouvoir = pouvoir;
        this.estMosaique = estMosaique;
    }

 

    protected boolean demanderJouerAutreCarte() {
        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            return demanderJouerAutreCarteGraphique();
        } else {
            return demanderJouerAutreCarteConsole();
        }
    }

    private boolean demanderJouerAutreCarteGraphique() {
        if (Partie.getInstance().getJoueurActif() instanceof Joueurs.JoueurBot) {
            return false;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Jouer une autre carte");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous jouer une autre carte ?");
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private boolean demanderJouerAutreCarteConsole() {
        if (Partie.getInstance().getJoueurActif() instanceof Joueurs.JoueurBot) {
            return false;
        }
        Scanner scanner = Partie.getInstance().getScanner();
        int choix;

        
        do {
            Affichage.afficherMessage("Voulez-vous jouer une autre carte ?");
            Affichage.afficherOption(1, "Oui");
            Affichage.afficherOption(2, "Non");

            choix = scanner.nextInt();
            scanner.nextLine();
        } while (choix != 1 && choix != 2);

        return choix == 1;
    }


    protected int obtenirChoixCarte(List<Carte> cartes) {
        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            return afficherDialogueGraphique(cartes);
        } else {
            return demanderChoixConsole(cartes);
        }
    }
    
    protected int afficherDialogueGraphique(List<Carte> cartes) {
        if (Partie.getInstance().getJoueurActif() instanceof Joueurs.JoueurBot) {
            return (int) (Math.random() * cartes.size());

        }
        List<String> choixCartes = new ArrayList<>();
        for (int i = 0; i < cartes.size(); i++) {
            choixCartes.add((i + 1) + ". " + cartes.get(i).getNom());
        }
    
        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, choixCartes);
        dialog.setTitle("Choix de la carte");
        dialog.setHeaderText("Choisissez une carte");
        Optional<String> result = dialog.showAndWait();
    
        if (result.isPresent()) {
            String selectedOption = result.get();
            return choixCartes.indexOf(selectedOption);
        } else {
            return -1;
        }
    }
    
    protected int demanderChoixConsole(List<Carte> cartes) {
        if (Partie.getInstance().getJoueurActif() instanceof Joueurs.JoueurBot) {
            return (int) (Math.random() * cartes.size());

        }
        Affichage.afficherMessage("Choisissez une carte :");
        for (int i = 0; i < cartes.size(); i++) {
            Affichage.afficherOption(i + 1, cartes.get(i).getNom());
        }
    
        Scanner scanner = Partie.getInstance().getScanner();
        int choix;
        do {
            choix = scanner.nextInt() - 1;
            scanner.nextLine();
            if (choix < 0 || choix >= cartes.size()) {
                Affichage.afficherMessage("Choix non valide. Veuillez choisir un numéro valide.");
            }
        } while (choix < 0 || choix >= cartes.size());
    
        return choix;
    }
    
    public String getNom() {
        return this.nom;
    }

    public String getCouleur() {
        return this.couleur;
    }

    public int getPoints() {
        return this.points;
    }

    public String getPouvoir() {
        return this.pouvoir;
    }

    public boolean getEstMosaique() {
        return this.estMosaique;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPouvoir(String pouvoir) {
        this.pouvoir = pouvoir;
    }

    public void setEstMosaique(boolean estMosaique) {
        this.estMosaique = estMosaique;
    }

    public abstract void action(Joueur joueur, Joueur adversaire);

    public String toString() {
        String base = this.nom + " - Couleur: ";
        if (this.estMosaique) {
            base += "Mosaique";
        } else {
            base += this.couleur;
        }
        return base + ", Points: " + this.points + ", Pouvoir: " + this.pouvoir + "\n";
    }

}
