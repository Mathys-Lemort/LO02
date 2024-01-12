package vue;

import java.util.*;

import Cartes.Carte;
import Controleur.*;
import javafx.*;

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
        boutonJouer.setDisable(true); 

        passerTour = new Button("Passer le tour");
        passerTour.setFont(Font.font(FONT_STYLE, FONT_SIZE));
        passerTour.setOnAction(new ControleurPasserTour(this, partie));

        sauvegarderQuitter = new Button("Sauvegarder et quitter");
        sauvegarderQuitter.setFont(Font.font(FONT_STYLE, FONT_SIZE));
        sauvegarderQuitter.setOnAction(new ControleurSauvegarderQuitter(this, partie));

        debutJeu = new Button("Commencer la partie");
        debutJeu.setFont(Font.font(FONT_STYLE, FONT_SIZE));

        joueur1TextField = new TextField();
        joueur1TextField.setPromptText("Pseudo du joueur 1");
        joueur2TextField = new TextField();
        joueur2TextField.setPromptText("Pseudo du joueur 2");

        joueur1TextField.textProperty().addListener((obs, oldText, newText) -> verifierPseudos());
        joueur2TextField.textProperty().addListener((obs, oldText, newText) -> verifierPseudos());
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage; 
        primaryStage.setTitle("Karmaka");
        Scene scene = new Scene(panelCentral, WINDOW_WIDTH, WINDOW_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        panelCentral.setStyle("-fx-background-image: url('file:img/background.png');" +
                "-fx-background-size: cover;");
                

        demanderChargementPartie(); 
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
            afficherEcranAccueil(true); 
        } else {
            afficherEcranAccueil(false); 
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

        VBox vbox = new VBox(10); 
        vbox.getChildren().add(new Label("Entrez les pseudos des joueurs pour commencer"));
        vbox.getChildren().add(joueur1TextField);
        vbox.getChildren().add(joueur2TextField);
        vbox.getChildren().add(boutonJouer);
        vbox.setAlignment(Pos.CENTER);
        VBox.setMargin(boutonJouer, new Insets(20, 0, 0, 0));

        fenetre.setCenter(vbox);
        return fenetre;
    }

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

    public String getJoueur1Pseudo() {
        return joueur1Pseudo;
    }

    public String getJoueur2Pseudo() {
        return joueur2Pseudo;
    }

    public void afficherResultatDes(int lanceJoueur1, int lanceJoueur2, String joueurCommence) {
        Label resultatLabel = new Label(
                this.joueur1Pseudo + " a lancé un " + lanceJoueur1 +
                        ", " + this.joueur2Pseudo + " a lancé un " + lanceJoueur2 +
                        ".\n" + joueurCommence + " commence !");
        VBox resultatContainer = new VBox(resultatLabel);
        resultatContainer.setAlignment(Pos.CENTER);
        resultatLabel.getStyleClass().add("white-label"); 

        this.debutJeu.setOnAction(new ControleurDebutJeu(this, partie));
        HBox hboxDebutJeu = new HBox(debutJeu);
        hboxDebutJeu.setAlignment(Pos.CENTER);
        resultatContainer.getChildren().add(hboxDebutJeu);
        VBox.setMargin(hboxDebutJeu, new Insets(100, 0, 100, 0));

        panelCentral.setCenter(resultatContainer);

        panelCentral.setStyle("-fx-background-image: url('file:img/de.png');" +
                "-fx-background-size: cover;");
    }

    private void mettreAJourIndicateurTour(String joueurActifPseudo) {
        Label labelTour = new Label("Tour de : " + joueurActifPseudo + " - " + partie.getJoueurActif().getPositionEchelleKarmique());
        labelTour.setId("label-tour");

        HBox hboxTour = new HBox(labelTour);
        hboxTour.setAlignment(Pos.CENTER);
        hboxTour.setSpacing(10);

        panelCentral.setTop(hboxTour);
    }

    private VBox createFosseCard(Carte carte) {
        VBox cardBox = new VBox();

        ImageView cardImageView = new ImageView(new Image("file:img/" + carte.getNom() + ".png"));

        cardImageView.setFitWidth(150);
        cardImageView.setFitHeight(200);

        cardBox.getChildren().add(cardImageView);
        cardBox.getStyleClass().add("card-box");

        Label cardNameLabel = new Label(carte.getNom());
        cardNameLabel.getStyleClass().add("white-label"); 
        cardBox.getChildren().add(cardNameLabel);

        return cardBox;
    }

    private VBox createOeuvreCard(Carte carte) {
        VBox cardBox = new VBox();

        ImageView cardImageView = new ImageView(new Image("file:img/" + carte.getNom() + ".png"));

        cardImageView.setFitWidth(150);
        cardImageView.setFitHeight(200);

        cardBox.getChildren().add(cardImageView);
        cardBox.getStyleClass().add("card-box");

        Label cardNameLabel = new Label(carte.getNom());
        cardNameLabel.getStyleClass().add("white-label"); 
        cardBox.getChildren().add(cardNameLabel);

        return cardBox;
    }

    private VBox createMainCard(Carte carte) {
        VBox cardBox = new VBox();

        ImageView cardImageView = new ImageView(new Image("file:img/" + carte.getNom() + ".png"));

        cardImageView.setFitWidth(150);
        cardImageView.setFitHeight(200);

        ContextMenu contextMenu = new ContextMenu();

        MenuItem utiliserPoints = new MenuItem("Utiliser pour points");
        utiliserPoints.setOnAction(new ControleurJouerPoints(this, partie, carte));

        MenuItem utiliserPouvoirs = new MenuItem("Utiliser pour pouvoirs");
        utiliserPouvoirs.setOnAction(new ControleurJouerPouvoirs(this, partie, carte));

        MenuItem mettreVieFuture = new MenuItem("Mettre dans la vie future");
        mettreVieFuture.setOnAction(new ControleurJouerVieFuture(this, partie, carte));

        contextMenu.getItems().addAll(utiliserPoints, utiliserPouvoirs, mettreVieFuture);

        cardImageView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                contextMenu.show(cardImageView, event.getScreenX(), event.getScreenY());
            }
        });
        cardBox.getStyleClass().add("card-box");

        cardImageView.setOnMouseEntered(event -> cardImageView.setOpacity(0.7));
        cardImageView.setOnMouseExited(event -> cardImageView.setOpacity(1.0));

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

        ScrollPane scrollPaneMainJoueur = creerScrollPaneMain(mainJoueur);

        SplitPane splitPaneDroite = new SplitPane();
        splitPaneDroite.setOrientation(javafx.geometry.Orientation.VERTICAL);
        ScrollPane scrollPaneFosseJoueur = creerScrollPaneFosse(fosseJoueur);
        ScrollPane scrollPaneOeuvresJoueur = creerScrollPaneOeuvres(oeuvres);

        splitPaneDroite.getItems().addAll(scrollPaneFosseJoueur, scrollPaneOeuvresJoueur);

        splitPanePrincipal.getItems().addAll(scrollPaneMainJoueur, splitPaneDroite);

        ecranJeu.setCenter(splitPanePrincipal);
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
        panelCentral.setBottom(null); 
        panelCentral
                .setCenter(creerEcranJeu(joueurActifPseudo, mainJoueur, pileJoueur, fosseJoueur, vieFuture, oeuvres));


        panelCentral.setStyle("-fx-background-image: url('file:img/background.png');" +
                "-fx-background-size: cover;");
        mettreAJourIndicateurTour(joueurActifPseudo);

    }

    public void rafraichirVueJoueur(String joueurActifPseudo, List<Carte> mainJoueur, List<Carte> pileJoueur,
            List<Carte> fosseJoueur, List<Carte> vieFuture, List<Carte> oeuvres) {
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
