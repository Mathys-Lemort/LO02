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
        // Faire un lien vers le fichier css qui est dans vue/style.css sachant que le
        // fichier jeu est dans vue/Jeu.java
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
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
            List<Carte> fosseJoueur, List<Carte> vieFuture) {
        BorderPane ecranJeu = new BorderPane();
        ecranJeu.getStyleClass().add("border-pane");

        mettreAJourIndicateurTour(joueurActifPseudo);
        // Mettre l'image img/background.jpg en arrière-plan de l'écran de jeu
        
        
        // Créer un SplitPane pour diviser l'écran en deux parties
        SplitPane splitPane = new SplitPane();
        // DOnner 70% de l'espace à la main du joueur et 30% à la fosse
        splitPane.setDividerPositions(0.7);

        // Partie gauche du SplitPane : la main du joueur
        ScrollPane scrollPaneMainJoueur = new ScrollPane();
        scrollPaneMainJoueur.setFitToWidth(true);

        VBox vboxMainJoueur = new VBox(5); // Container for the player's hand
        Label labelMainJoueur = new Label("Main du joueur");
        labelMainJoueur.getStyleClass().add("titre_cartes");
        vboxMainJoueur.getChildren().add(labelMainJoueur);

        for (Carte carte : mainJoueur) {
            VBox carteBox = createMainCard(carte);
            vboxMainJoueur.getChildren().add(carteBox);
            Label pouvoir = new Label("Pouvoir : " + carte.getPouvoir());
            pouvoir.getStyleClass().add("white-label");
            vboxMainJoueur.getChildren().add(pouvoir);
            pouvoir.setWrapText(true);        
            Label points = new Label("Points : " + carte.getPoints());
            points.getStyleClass().add("white-label");
            vboxMainJoueur.getChildren().add(points);            
        }

        // Ajouter la VBox contenant la main du joueur au ScrollPane
        scrollPaneMainJoueur.setContent(vboxMainJoueur);

        // Partie droite du SplitPane : la fosse du joueur
        ScrollPane scrollPaneFosseJoueur = new ScrollPane();
        scrollPaneFosseJoueur.setFitToWidth(true);

        VBox vboxFosseJoueur = new VBox(5); // Container for the discard pile
        System.out.println(fosseJoueur);
        Label labelFosseJoueur = new Label("Fosse du joueur");
        labelFosseJoueur.getStyleClass().add("titre_cartes");
        vboxFosseJoueur.getChildren().add(labelFosseJoueur);
        for (Carte carte : fosseJoueur) {
            VBox carteBox = createFosseCard(carte);
            vboxFosseJoueur.getChildren().add(carteBox);
        }

        scrollPaneMainJoueur.getStyleClass().add("scroll-pane-card");
        scrollPaneFosseJoueur.getStyleClass().add("scroll-pane-card");
        splitPane.getStyleClass().add("split-pane-game");

        // Ajouter les conteneurs aux parties gauche et droite du SplitPane
        // Ajouter du texte pour indiquer la source et la fosse
        scrollPaneFosseJoueur.setContent(vboxFosseJoueur);

        splitPane.getItems().addAll(scrollPaneMainJoueur, scrollPaneFosseJoueur);

        // Ajouter le SplitPane à l'écran de jeu
        ecranJeu.setCenter(splitPane);
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
        BackgroundImage backgroundImage = new BackgroundImage(new Image("file:img/background.png"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false));
        panelCentral.setBackground(new javafx.scene.layout.Background(backgroundImage));
        mettreAJourIndicateurTour(joueurActifPseudo);

    }

    // Dans Jeu.java
    public void rafraichirVueJoueur(String joueurActifPseudo, List<Carte> mainJoueur, List<Carte> pileJoueur,
            List<Carte> fosseJoueur, List<Carte> vieFuture) {
        // Supprime les anciens éléments de la main du joueur et ajoute les nouveaux
        panelCentral.setCenter(creerEcranJeu(joueurActifPseudo, mainJoueur, pileJoueur, fosseJoueur, vieFuture));
        mettreAJourIndicateurTour(joueurActifPseudo);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
