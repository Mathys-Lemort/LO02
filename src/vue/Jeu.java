package vue;

import java.util.List;
import java.util.Optional;

import Cartes.Carte;
import Controleur.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private Button sauvegarderQuitter;
    private Stage primaryStage;

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

        // Faire un boutton Sauvegarder et quitter
        sauvegarderQuitter = new Button("Sauvegarder et quitter");
        sauvegarderQuitter.setFont(Font.font(FONT_STYLE, FONT_SIZE));
        sauvegarderQuitter.setOnAction(new ControleurSauvegarderQuitter(this, partie));

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
        this.primaryStage = primaryStage; // Conserver une référence au primaryStage
        primaryStage.setTitle("Karmaka");
        Scene scene = new Scene(panelCentral, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        // Mettre background.png
        panelCentral.setStyle("-fx-background-image: url('file:img/background.png');" +
                "-fx-background-size: cover;");
                

        demanderChargementPartie(); // Appel de la nouvelle méthode
    }

    private void demanderChargementPartie() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Charger une partie");
        alert.setHeaderText("Voulez-vous charger une partie sauvegardée ?");
        alert.setContentText("Choisissez votre option.");

        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            ControleurChargementPartie controleur = new ControleurChargementPartie(this, partie,
                    result.get() == buttonTypeYes);
            controleur.handle(new ActionEvent());
        }
    }

    public void demanderModeJeu() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Choix du mode de jeu");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous jouer contre un Bot?");

        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            afficherEcranAccueil(true); // true indique qu'on joue contre un Bot
        } else {
            afficherEcranAccueil(false); // false indique qu'on joue contre un joueur
        }
    }

    public void afficherEcranAccueil(boolean contreBot) {
        if (contreBot) {
            joueur2TextField.setText("Bot");
            joueur2TextField.setDisable(true);
        } else {
            joueur2TextField.setDisable(false);
        }
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
        resultatLabel.getStyleClass().add("white-label"); // Ajout de la classe CSS

        // Afficher les résultats dans la vue centrale du BorderPane
        this.debutJeu.setOnAction(new ControleurDebutJeu(this, partie));
        // Mettre le bouton "Commencer la partie" au milieu en dessous des résultats
        HBox hboxDebutJeu = new HBox(debutJeu);
        hboxDebutJeu.setAlignment(Pos.CENTER);
        resultatContainer.getChildren().add(hboxDebutJeu);
        VBox.setMargin(hboxDebutJeu, new Insets(100, 0, 100, 0));

        panelCentral.setCenter(resultatContainer);

        panelCentral.setStyle("-fx-background-image: url('file:img/de.png');" +
                "-fx-background-size: cover;");
    }

    private void mettreAJourIndicateurTour(String joueurActifPseudo) {
        Label labelTour = new Label("Tour de : " + joueurActifPseudo);
        // Lui donner un identifiant
        labelTour.setId("label-tour");

        HBox hboxTour = new HBox(labelTour);
        hboxTour.setAlignment(Pos.CENTER);
        hboxTour.setSpacing(10);

        panelCentral.setTop(hboxTour);
    }

    private VBox createFosseCard(Carte carte) {
        VBox cardBox = new VBox();

        // Créer un ImageView pour afficher l'image de la carte
        ImageView cardImageView = new ImageView(new Image("file:img/" + carte.getNom() + ".png"));

        // Définir la taille de l'ImageView pour correspondre à la taille de l'image
        cardImageView.setFitWidth(150);
        cardImageView.setFitHeight(200);

        // Ajouter l'ImageView à la VBox
        cardBox.getChildren().add(cardImageView);
        cardBox.getStyleClass().add("card-box");

        // Ajouter le nom de la carte en dessous de l'image
        Label cardNameLabel = new Label(carte.getNom());
        cardNameLabel.getStyleClass().add("white-label"); // Ajout de la classe CSS
        cardBox.getChildren().add(cardNameLabel);

        return cardBox;
    }

    private VBox createOeuvreCard(Carte carte) {
        VBox cardBox = new VBox();

        // Créer un ImageView pour afficher l'image de la carte
        ImageView cardImageView = new ImageView(new Image("file:img/" + carte.getNom() + ".png"));

        // Définir la taille de l'ImageView pour correspondre à la taille de l'image
        cardImageView.setFitWidth(150);
        cardImageView.setFitHeight(200);

        // Ajouter l'ImageView à la VBox
        cardBox.getChildren().add(cardImageView);
        cardBox.getStyleClass().add("card-box");

        // Ajouter le nom de la carte en dessous de l'image
        Label cardNameLabel = new Label(carte.getNom());
        cardNameLabel.getStyleClass().add("white-label"); // Ajout de la classe CSS
        cardBox.getChildren().add(cardNameLabel);

        return cardBox;
    }

    private VBox createMainCard(Carte carte) {
        VBox cardBox = new VBox();

        // Créer un ImageView pour afficher l'image de la carte
        ImageView cardImageView = new ImageView(new Image("file:img/" + carte.getNom() + ".png"));

        // Définir la taille de l'ImageView pour correspondre à la taille de l'image
        cardImageView.setFitWidth(150);
        cardImageView.setFitHeight(200);

        // Créer le menu contextuel
        ContextMenu contextMenu = new ContextMenu();

        MenuItem utiliserPoints = new MenuItem("Utiliser pour points");
        utiliserPoints.setOnAction(new ControleurJouerPoints(this, partie, carte));

        MenuItem utiliserPouvoirs = new MenuItem("Utiliser pour pouvoirs");
        utiliserPouvoirs.setOnAction(new ControleurJouerPouvoirs(this, partie, carte));

        MenuItem mettreVieFuture = new MenuItem("Mettre dans la vie future");
        mettreVieFuture.setOnAction(new ControleurJouerVieFuture(this, partie, carte));

        contextMenu.getItems().addAll(utiliserPoints, utiliserPouvoirs, mettreVieFuture);

        // Gérer le clic sur l'image pour afficher le menu contextuel
        cardImageView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(cardImageView, event.getScreenX(), event.getScreenY());
            }
        });
        cardBox.getStyleClass().add("card-box");

        cardImageView.setOnMouseEntered(event -> cardImageView.setOpacity(0.7));
        cardImageView.setOnMouseExited(event -> cardImageView.setOpacity(1.0));

        // Ajouter l'ImageView à la VBox
        cardBox.getChildren().add(cardImageView);

        return cardBox;
    }

    private BorderPane creerEcranJeu(String joueurActifPseudo, List<Carte> mainJoueur, List<Carte> pileJoueur,
            List<Carte> fosseJoueur, List<Carte> vieFuture, List<Carte> oeuvres) {
        BorderPane ecranJeu = new BorderPane();
        ecranJeu.getStyleClass().add("border-pane");

        mettreAJourIndicateurTour(joueurActifPseudo);

        SplitPane splitPanePrincipal = new SplitPane();
        splitPanePrincipal.setDividerPositions(0.7);

        // Création du ScrollPane pour la main du joueur
        ScrollPane scrollPaneMainJoueur = creerScrollPaneMain(mainJoueur);

        // Création du SplitPane vertical pour la fosse et les oeuvres
        SplitPane splitPaneDroite = new SplitPane();
        splitPaneDroite.setOrientation(javafx.geometry.Orientation.VERTICAL);
        ScrollPane scrollPaneFosseJoueur = creerScrollPaneFosse(fosseJoueur);
        ScrollPane scrollPaneOeuvresJoueur = creerScrollPaneOeuvres(oeuvres);

        splitPaneDroite.getItems().addAll(scrollPaneFosseJoueur, scrollPaneOeuvresJoueur);

        splitPanePrincipal.getItems().addAll(scrollPaneMainJoueur, splitPaneDroite);

        ecranJeu.setCenter(splitPanePrincipal);
        // Mettre le boutton "Passer le tour" "Sauvegarder et quitter" cote a cote
        HBox hboxBouttons = new HBox(passerTour, sauvegarderQuitter);
        hboxBouttons.setAlignment(Pos.CENTER);
        hboxBouttons.setSpacing(10);
        ecranJeu.setBottom(hboxBouttons);

        return ecranJeu;
    }

    private ScrollPane creerScrollPaneMain(List<Carte> mainJoueur) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        VBox vbox = new VBox(5);
        Label label = new Label("Main du joueur");
        label.setWrapText(true);
        label.getStyleClass().add("titre_cartes");
        vbox.getChildren().add(label);

        for (Carte carte : mainJoueur) {
            // Ajouter le
            VBox carteBox = createMainCard(carte);
            vbox.getChildren().add(carteBox);
            Label pouvoir = new Label("Pouvoir : " + carte.getPouvoir());
            pouvoir.getStyleClass().add("white-label");
            vbox.getChildren().add(pouvoir);
            pouvoir.setWrapText(true);
            Label points = new Label("Points : " + carte.getPoints());
            points.getStyleClass().add("white-label");
            vbox.getChildren().add(points);
        }
        scrollPane.setContent(vbox);
        return scrollPane;
    }

    private ScrollPane creerScrollPaneFosse(List<Carte> fosseJoueur) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        VBox vbox = new VBox(5);
        Label label = new Label("Fosse du joueur");
        label.setWrapText(true);
        label.getStyleClass().add("titre_cartes");
        vbox.getChildren().add(label);

        for (Carte carte : fosseJoueur) {
            vbox.getChildren().add(createFosseCard(carte));
        }
        scrollPane.setContent(vbox);
        return scrollPane;
    }

    private ScrollPane creerScrollPaneOeuvres(List<Carte> oeuvres) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        VBox vbox = new VBox(5);
        Label label = new Label("Oeuvres du joueur");
        label.setWrapText(true);

        label.getStyleClass().add("titre_cartes");
        vbox.getChildren().add(label);

        for (Carte carte : oeuvres) {
            vbox.getChildren().add(createOeuvreCard(carte));
        }
        scrollPane.setContent(vbox);
        return scrollPane;
    }

    public void afficherEcranJoueur(String joueurActifPseudo, List<Carte> mainJoueur, List<Carte> pileJoueur,
            List<Carte> fosseJoueur, List<Carte> vieFuture, List<Carte> oeuvres) {
        // Print pour test
        panelCentral.setBottom(null); // Cela supprimera le bouton "Commencer la partie"
        panelCentral
                .setCenter(creerEcranJeu(joueurActifPseudo, mainJoueur, pileJoueur, fosseJoueur, vieFuture, oeuvres));
        // BackgroundImage backgroundImage = new BackgroundImage(new
        // Image("file:img/background.png"),
        // BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
        // BackgroundPosition.CENTER,
        // new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
        // true, false));
        // panelCentral.setBackground(new
        // javafx.scene.layout.Background(backgroundImage));

        panelCentral.setStyle("-fx-background-image: url('file:img/background.png');" +
                "-fx-background-size: cover;");
        mettreAJourIndicateurTour(joueurActifPseudo);

    }

    // Dans Jeu.java
    public void rafraichirVueJoueur(String joueurActifPseudo, List<Carte> mainJoueur, List<Carte> pileJoueur,
            List<Carte> fosseJoueur, List<Carte> vieFuture, List<Carte> oeuvres) {
        // Supprime les anciens éléments de la main du joueur et ajoute les nouveaux
        panelCentral
                .setCenter(creerEcranJeu(joueurActifPseudo, mainJoueur, pileJoueur, fosseJoueur, vieFuture, oeuvres));
        mettreAJourIndicateurTour(joueurActifPseudo);

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void fermerFenetre() {
        primaryStage.close();

    }

}
