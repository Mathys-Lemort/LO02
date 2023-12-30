package vue;

import java.util.List;

import Cartes.Carte;
import Controleur.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import Core.Partie;

public class Jeu extends Application {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 800;
    private static final String FONT_STYLE = "Arial";
    private static final int FONT_SIZE = 20;

    private BorderPane panelCentral;
    private Button boutonJouer;
    private TextField joueur1TextField;
    private TextField joueur2TextField;
    private String joueur1Pseudo;
    private String joueur2Pseudo;
    private Partie partie;
    private Button debutJeu;
    private Button passerTour;

    @Override
    public void init() {
        partie = Partie.getInstance();
        panelCentral = new BorderPane();
        boutonJouer = new Button("Jouer à Karmaka");
        boutonJouer.setFont(Font.font(FONT_STYLE, FONT_SIZE));
        boutonJouer.setDisable(true); // Initially disable the button

        passerTour = new Button("Passer le tour");
        passerTour.setFont(Font.font(FONT_STYLE, FONT_SIZE));
        passerTour.setOnAction(new ControleurPasserTour(this, partie));

        debutJeu = new Button("Commencer la partie");
        debutJeu.setFont(Font.font(FONT_STYLE, FONT_SIZE));

        joueur1TextField = new TextField();
        joueur1TextField.setPromptText("Pseudo du joueur 1");
        joueur2TextField = new TextField();
        joueur2TextField.setPromptText("Pseudo du joueur 2");

        // Set an action when text is typed in the TextFields
        joueur1TextField.textProperty().addListener((obs, oldText, newText) -> verifierPseudos());
        joueur2TextField.textProperty().addListener((obs, oldText, newText) -> verifierPseudos());
    }

    @Override
    public void start(Stage primaryStage) {
        afficherEcranAccueil();
        Scene scene = new Scene(panelCentral, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setTitle("Karmaka");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void afficherEcranAccueil() {
        panelCentral.setCenter(creerEcranAccueil());
    }

    private BorderPane creerEcranAccueil() {
        BorderPane fenetre = new BorderPane();
        fenetre.setStyle("-fx-background-image: url('file:img/karmaka.jpg');" +
                "-fx-background-size: cover;");

        VBox vbox = new VBox(10); // Spacing between nodes
        vbox.getChildren().add(new Label("Entrez les pseudos des joueurs pour commencer"));
        vbox.getChildren().add(joueur1TextField);
        vbox.getChildren().add(joueur2TextField);
        vbox.getChildren().add(boutonJouer);
        vbox.setAlignment(Pos.CENTER);
        VBox.setMargin(boutonJouer, new Insets(20, 0, 0, 0)); // Margin above the button

        fenetre.setCenter(vbox);
        return fenetre;
    }

    // Dans Jeu.java
    private void verifierPseudos() {
        String pseudo1 = joueur1TextField.getText().trim();
        String pseudo2 = joueur2TextField.getText().trim();

        if (!pseudo1.isEmpty() && !pseudo2.isEmpty() && !pseudo1.equals(pseudo2)) {
            boutonJouer.setDisable(false);
            boutonJouer.setOnAction(event -> {
                joueur1Pseudo = pseudo1;
                joueur2Pseudo = pseudo2;
                new ControleurLancerPartie(this, partie, joueur1Pseudo, joueur2Pseudo).handle(event);
            });
        } else {
            boutonJouer.setDisable(true);
        }
    }

    // Dans Jeu.java
    public String getJoueur1Pseudo() {
        return joueur1Pseudo;
    }

    public String getJoueur2Pseudo() {
        return joueur2Pseudo;
    }

    



    // Dans vue.Jeu
    public void afficherResultatDes(int lanceJoueur1, int lanceJoueur2, String joueurCommence) {
        // Cette méthode sera appelée par le contrôleur après le lancer de dés
        // Mettre à jour l'interface utilisateur avec les résultats
        Label resultatLabel = new Label(
                this.joueur1Pseudo + " a lancé un " + lanceJoueur1 +
                        ", " + this.joueur2Pseudo + " a lancé un " + lanceJoueur2 +
                        ".\n" + joueurCommence + " commence !");
        VBox resultatContainer = new VBox(resultatLabel);
        resultatContainer.setAlignment(Pos.CENTER);

        // Afficher les résultats dans la vue centrale du BorderPane
        this.debutJeu.setOnAction(new ControleurDebutJeu(this, partie));

        panelCentral.setCenter(resultatContainer);
        panelCentral.setBottom(debutJeu);
    }

    // Puis, dans votre méthode afficherEcranJeu(), vous initialiserez la partie
    // avec le joueur actif.

    private VBox createCardButton(Carte carte) {
        VBox cardBox = new VBox();
        Button cardButton = new Button(carte.getNom());
        cardBox.getChildren().add(cardButton);

        // Créer le menu contextuel
        ContextMenu contextMenu = new ContextMenu();

        MenuItem utiliserPoints = new MenuItem("Utiliser pour points");
        utiliserPoints.setOnAction(new ControleurJouerPoints(this, partie, carte));

        MenuItem utiliserPouvoirs = new MenuItem("Utiliser pour pouvoirs");
        utiliserPouvoirs.setOnAction(new ControleurJouerPouvoirs(this, partie, carte));

        MenuItem mettreVieFuture = new MenuItem("Mettre dans la vie future");
        mettreVieFuture.setOnAction(new ControleurJouerVieFuture(this, partie, carte));

        contextMenu.getItems().addAll(utiliserPoints, utiliserPouvoirs, mettreVieFuture);

        // Gérer le clic sur le bouton pour afficher le menu contextuel
        cardButton.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(cardButton, event.getScreenX(), event.getScreenY());
            }
        });

        return cardBox;
    }

    private BorderPane creerEcranJeu(String joueurActifPseudo, List<Carte> mainJoueur, List<Carte> pileJoueur,
            List<Carte> fosseJoueur, List<Carte> vieFuture) {
        BorderPane ecranJeu = new BorderPane();

        // Créer les différents composants pour la main, la pile, la fosse, etc.
        VBox vboxMainJoueur = new VBox(5); // Container for the player's hand
        vboxMainJoueur.getChildren().add(new Label("Main du joueur"));
        for (Carte carte : mainJoueur) {
            VBox carteButton = createCardButton(carte);
            vboxMainJoueur.getChildren().add(carteButton);
            vboxMainJoueur.getChildren().add(new Label("Pouvoir : " + carte.getPouvoir()));
            vboxMainJoueur.getChildren().add(new Label("Points : " + carte.getPoints()));
        }
        // Add more UI components that represent the player's hand cards

        VBox vboxFosseJoueur = new VBox(5); // Container for the discard pile
        vboxFosseJoueur.getChildren().add(new Label("Fosse du joueur"));
        for (Carte carte : fosseJoueur) {
            // Créer un bouton avec le nom de la carte et metter en dessus le pouvoir et le
            // nombre de point de la carte
            vboxFosseJoueur.getChildren().add(new Label(carte.getNom()));
        }

        // Ajouter en bas un InputText pour permettre au joueur d'entre le nombre de l'option qu'il veut choisir avec un boutton envoyer qui permettra de print directement dans le terminal le choix du joueur
        
        // Ajouter les conteneurs à l'écran de jeu
        ecranJeu.setLeft(vboxMainJoueur); // Positionner la main du joueur à gauche
        ecranJeu.setRight(vboxFosseJoueur); // Positionner la fosse du joueur à droite
        ecranJeu.setBottom(passerTour); // Positionner le bouton passer le tour en bas
        

        // Vous pouvez également ajouter des boutons ou d'autres éléments d'interaction
        // ici

        return ecranJeu;
    }

    public void afficherEcranJoueur(String joueurActifPseudo, List<Carte> mainJoueur, List<Carte> pileJoueur,
            List<Carte> fosseJoueur, List<Carte> vieFuture) {
        System.out.println(joueurActifPseudo + " commence !");
        panelCentral.setBottom(null); // Cela supprimera le bouton "Commencer la partie"
        panelCentral.setCenter(creerEcranJeu(joueurActifPseudo, mainJoueur, pileJoueur, fosseJoueur, vieFuture));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
