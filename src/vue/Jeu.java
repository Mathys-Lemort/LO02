package vue;

import java.io.File;
import java.util.*;

import Cartes.Carte;
import Controleur.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
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
    private Button sauvegarderQuitter;
    private Stage primaryStage;

    /**
     * Cette méthode est appelée lors de l'initialisation de l'application.
     * Elle initialise la partie en récupérant une instance de la classe Partie.
     * Ensuite, elle configure le mode de jeu de la partie en mode graphique.
     * Elle crée un panneau central de type BorderPane.
     * Elle crée un bouton "Jouer à Karmaka" et le désactive.
     * Elle crée les boutons "Passer le tour" et "Sauvegarder et quitter" avec leur
     * texte et leur police.
     * Elle associe les actions correspondantes aux boutons "Passer le tour" et
     * "Sauvegarder et quitter".
     * Elle crée un bouton "Commencer la partie" avec son texte et sa police.
     * Elle crée deux champs de texte pour les pseudos des joueurs 1 et 2 avec des
     * invitations.
     * Elle ajoute des écouteurs de changement de texte aux champs de texte pour
     * vérifier les pseudos.
     */
    @Override
    public void init() {
        partie = Partie.getInstance();
        partie.setMode(Partie.Mode.GRAPHIQUE);
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

    /**
     * Cette méthode est appelée lors du démarrage de l'application.
     * Elle configure la fenêtre principale et affiche le contenu graphique.
     * La méthode crée une nouvelle instance de la classe Stage et lui donne un
     * titre.
     * Ensuite, elle crée une nouvelle instance de la classe Scene en utilisant le
     * panneau central et les dimensions de la fenêtre.
     * Elle ajoute également une feuille de style à la scène.
     * Ensuite, elle définit la scène sur la fenêtre principale et l'affiche.
     * Enfin, la méthode appelle la méthode demanderChargementPartie() pour demander
     * à l'utilisateur s'il souhaite charger une partie sauvegardée.
     *
     * @param primaryStage la fenêtre principale de l'application
     */
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

    // Cette méthode affiche une boîte de dialogue demandant à l'utilisateur s'il
    // souhaite charger une partie sauvegardée.
    // Elle utilise une boîte de dialogue de type CONFIRMATION avec les options
    // "Oui" et "Non".
    // Si l'utilisateur sélectionne "Oui", la méthode crée un objet
    // ControleurChargementPartie et appelle sa méthode handle().
    // Le résultat de la boîte de dialogue est passé en tant que paramètre à la
    // méthode handle().
    private void demanderChargementPartie() {
        if (!new File("src/sauvegarde.txt").exists()) {
            demanderModeJeu();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Charger une partie");
        alert.setHeaderText("Voulez-vous charger une partie sauvegardée ?");
        alert.setContentText("Choisissez votre option.");

        ButtonType buttonTypeYes = new ButtonType("Oui");
        ButtonType buttonTypeNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            boolean chargerPartie = result.get() == buttonTypeYes;
            ControleurChargementPartie controleur = new ControleurChargementPartie(this, partie, chargerPartie);
            controleur.handle(new ActionEvent());
        }
    }

    /**
     * Cette méthode affiche une boîte de dialogue demandant à l'utilisateur de
     * choisir le mode de jeu.
     * La boîte de dialogue propose les options "Oui" et "Non".
     * Si l'utilisateur sélectionne "Oui", la méthode appelle la méthode
     * afficherEcranAccueil() avec le paramètre true pour indiquer que le mode de
     * jeu est contre un bot.
     * Sinon, la méthode appelle la méthode afficherEcranAccueil() avec le paramètre
     * false pour indiquer que le mode de jeu n'est pas contre un bot.
     */
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

    /**
     * Cette méthode affiche l'écran d'accueil du jeu.
     * Si le mode de jeu est contre un bot, le champ de texte du joueur 2 est
     * automatiquement rempli avec "Bot" et désactivé.
     * Sinon, le champ de texte du joueur 2 est activé.
     * L'écran d'accueil est créé à l'aide de la méthode creerEcranAccueil() et est
     * affiché dans le panneau central.
     * 
     * @param contreBot indique si le mode de jeu est contre un bot ou non
     */
    public void afficherEcranAccueil(boolean contreBot) {
        panelCentral.setStyle(null);
        panelCentral.setTop(null);
        System.out.println("Affichage de l'écran d'accueil");
        if (contreBot) {
            joueur2TextField.setText("Bot");
            joueur2TextField.setDisable(true);
        } else {
            joueur2TextField.setDisable(false);
        }
        panelCentral.setCenter(creerEcranAccueil());

    }

    /**
     * Cette méthode crée et retourne l'écran d'accueil du jeu.
     * L'écran d'accueil contient un fond d'écran avec une image de fond.
     * Il affiche également un message demandant aux joueurs d'entrer leurs pseudos.
     * Les champs de texte pour les pseudos des joueurs sont ajoutés à une boîte
     * verticale (VBox).
     * Le bouton "Jouer" est également ajouté à la boîte verticale.
     * La boîte verticale est centrée dans le panneau principal (BorderPane) de
     * l'écran d'accueil.
     * 
     * @return le panneau principal (BorderPane) de l'écran d'accueil
     */
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

    /**
     * Vérifie les pseudos des joueurs avant de permettre de jouer.
     * 
     * Les pseudos des joueurs sont extraits des champs de texte joueur1TextField et
     * joueur2TextField.
     * Si les deux pseudos ne sont pas vides et ne sont pas identiques, le
     * boutonJouer est activé et un gestionnaire d'événements est défini pour le
     * bouton.
     * Lorsque le bouton est cliqué, les pseudos des joueurs sont assignés aux
     * variables joueur1Pseudo et joueur2Pseudo, et une nouvelle instance de
     * ControleurLancerPartie est créée pour lancer la partie.
     * Si les pseudos ne satisfont pas les conditions, le boutonJouer est désactivé.
     */
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

    /**
     * Cette méthode retourne le pseudo du joueur 1.
     * 
     * @return le pseudo du joueur 1
     */
    public String getJoueur1Pseudo() {
        return joueur1Pseudo;
    }

    /**
     * Cette méthode retourne le pseudo du joueur 2.
     * 
     * @return le pseudo du joueur 2
     */
    public String getJoueur2Pseudo() {
        return joueur2Pseudo;
    }

    /**
     * Cette méthode affiche le résultat des lancers de dés des joueurs et le joueur
     * qui commence.
     * Elle crée un label contenant les informations sur les lancers de dés et le
     * joueur qui commence.
     * Le label est ensuite ajouté à un conteneur VBox centré.
     * Le style du label est modifié pour avoir une couleur de texte blanche.
     * Un bouton "Débuter le jeu" est créé et un gestionnaire d'événements est
     * défini pour le bouton.
     * Lorsque le bouton est cliqué, le jeu est démarré en appelant le contrôleur
     * approprié.
     * Le bouton est ajouté à un conteneur HBox centré, qui est ensuite ajouté au
     * conteneur VBox.
     * Des marges sont définies pour le conteneur HBox pour l'espacement.
     * Le conteneur VBox est ensuite ajouté au panneau central de l'écran.
     * Le style du panneau central est modifié pour afficher une image de fond.
     * 
     * @param lanceJoueur1   le résultat du lancer de dés du joueur 1
     * @param lanceJoueur2   le résultat du lancer de dés du joueur 2
     * @param joueurCommence le joueur qui commence la partie
     */
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

    /**
     * Met à jour l'indicateur de tour avec le pseudo du joueur actif.
     * 
     * Cette méthode crée un label contenant le pseudo du joueur actif et sa
     * position sur l'échelle karmique.
     * Le label est ensuite ajouté à un conteneur HBox centré.
     * Des marges et un espacement sont définis pour le conteneur HBox.
     * Le conteneur HBox est ensuite ajouté à la partie supérieure du panneau
     * central de l'écran.
     * 
     * @param joueurActifPseudo le pseudo du joueur actif
     */
    private void mettreAJourIndicateurTour(String joueurActifPseudo) {
        Label labelTour = new Label(
                "Tour de : " + joueurActifPseudo + " - " + partie.getJoueurActif().getPositionEchelleKarmique());
        labelTour.setId("label-tour");

        HBox hboxTour = new HBox(labelTour);
        hboxTour.setAlignment(Pos.CENTER);
        hboxTour.setSpacing(10);

        panelCentral.setTop(hboxTour);
    }

    /**
     * Cette méthode crée une carte pour la fosse du joueur.
     * 
     * Elle crée une boîte verticale (VBox) pour contenir la carte.
     * Une image de la carte est créée à partir du nom de la carte et ajoutée à un
     * ImageView.
     * Les dimensions de l'image sont ajustées pour s'adapter à la taille souhaitée.
     * L'ImageView est ajouté à la boîte verticale (VBox).
     * La classe de style "card-box" est ajoutée à la boîte verticale (VBox).
     * Un label contenant le nom de la carte est créé et ajouté à la boîte verticale
     * (VBox).
     * La classe de style "white-label" est ajoutée au label.
     * La boîte verticale (VBox) est renvoyée en tant que résultat.
     * 
     * @param carte la carte à afficher dans la fosse du joueur
     * @return la boîte verticale (VBox) contenant la carte de la fosse du joueur
     */
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

    /**
     * Cette méthode crée une carte pour une oeuvre.
     * 
     * Elle crée une boîte verticale (VBox) pour contenir la carte.
     * Une image de la carte est créée à partir du nom de la carte et ajoutée à un
     * ImageView.
     * Les dimensions de l'image sont ajustées pour s'adapter à la taille souhaitée.
     * L'ImageView est ajouté à la boîte verticale (VBox).
     * La classe de style "card-box" est ajoutée à la boîte verticale (VBox).
     * Un label contenant le nom de la carte est créé et ajouté à la boîte verticale
     * (VBox).
     * La classe de style "white-label" est ajoutée au label.
     * La boîte verticale (VBox) est renvoyée en tant que résultat.
     * 
     * @param carte la carte représentant une oeuvre
     * @return la boîte verticale (VBox) contenant la carte de l'oeuvre
     */
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

    /**
     * Cette méthode crée une carte pour la main du joueur.
     * 
     * Elle crée une boîte verticale (VBox) pour contenir la carte.
     * Une image de la carte est créée à partir du nom de la carte et ajoutée à un
     * ImageView.
     * Les dimensions de l'image sont ajustées pour s'adapter à la taille souhaitée.
     * Un menu contextuel est créé pour la carte, permettant différentes actions.
     * Le menu contextuel contient des options pour utiliser la carte pour des
     * points, des pouvoirs ou la mettre dans la vie future.
     * Lorsque la carte est cliquée avec le bouton gauche de la souris, le menu
     * contextuel est affiché.
     * La classe de style "card-box" est ajoutée à la boîte verticale (VBox).
     * L'opacité de l'image de la carte est réduite lorsque la souris survole la
     * carte.
     * La boîte verticale (VBox) est renvoyée en tant que résultat.
     * 
     * @param carte la carte à afficher dans la main du joueur
     * @return la boîte verticale (VBox) contenant la carte de la main du joueur
     */
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

    /**
     * Cette méthode crée l'écran de jeu principal.
     * 
     * Elle prend en paramètre le pseudo du joueur actif, la liste des cartes de sa
     * main, la liste des cartes de sa pile,
     * la liste des cartes de sa fosse, la liste des cartes de sa vie future et la
     * liste des cartes de ses oeuvres.
     * 
     * L'écran de jeu est créé en utilisant un BorderPane comme conteneur principal.
     * L'indicateur de tour est mis à jour avec le pseudo du joueur actif.
     * Un SplitPane est utilisé pour diviser l'écran en deux parties : la main du
     * joueur à gauche et la fosse et les oeuvres à droite.
     * Un ScrollPane est utilisé pour afficher la main du joueur.
     * Un autre SplitPane est utilisé pour diviser la partie droite en deux : la
     * fosse du joueur en haut et les oeuvres en bas.
     * Des ScrollPanes sont utilisés pour afficher la fosse et les oeuvres du
     * joueur.
     * Les différents éléments sont ajoutés au BorderPane et renvoyés en tant que
     * résultat.
     * 
     * @param joueurActifPseudo le pseudo du joueur actif
     * @param mainJoueur        la liste des cartes de la main du joueur
     * @param pileJoueur        la liste des cartes de la pile du joueur
     * @param fosseJoueur       la liste des cartes de la fosse du joueur
     * @param vieFuture         la liste des cartes de la vie future du joueur
     * @param oeuvres           la liste des cartes des oeuvres du joueur
     * @return l'écran de jeu principal
     */
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

    /**
     * Cette méthode crée un ScrollPane pour afficher la main du joueur.
     * 
     * Elle prend en paramètre la liste des cartes de la main du joueur.
     * 
     * Le ScrollPane est configuré pour s'adapter à la largeur.
     * Une VBox est créée pour contenir les éléments à afficher.
     * Un Label est créé pour afficher le titre "Main du joueur".
     * Le texte du Label est ajusté pour s'adapter à la taille.
     * La classe de style "titre_cartes" est ajoutée au Label.
     * Le Label est ajouté à la VBox.
     * Pour chaque carte dans la liste des cartes de la main du joueur, une VBox est
     * créée en utilisant la méthode createMainCard().
     * La VBox de la carte est ajoutée à la VBox principale.
     * Un Label est créé pour afficher le pouvoir de la carte.
     * Le texte du Label est ajusté pour s'adapter à la taille.
     * La classe de style "white-label" est ajoutée au Label.
     * Le Label est ajouté à la VBox principale.
     * Un Label est créé pour afficher les points de la carte.
     * Le texte du Label est ajusté pour s'adapter à la taille.
     * La classe de style "white-label" est ajoutée au Label.
     * Le Label est ajouté à la VBox principale.
     * Le contenu de la VBox principale est ajouté au ScrollPane.
     * Le ScrollPane est renvoyé en tant que résultat.
     * 
     * @param mainJoueur la liste des cartes de la main du joueur
     * @return le ScrollPane pour afficher la main du joueur
     */
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

    /**
     * Cette méthode crée un ScrollPane pour afficher la fosse du joueur.
     * 
     * Elle prend en paramètre la liste des cartes de la fosse du joueur.
     * 
     * Le ScrollPane est configuré pour s'adapter à la largeur.
     * Une VBox est créée pour contenir les éléments à afficher.
     * Un Label est créé pour afficher le titre "Fosse du joueur".
     * Le texte du Label est ajusté pour s'adapter à la taille.
     * La classe de style "titre_cartes" est ajoutée au Label.
     * Le Label est ajouté à la VBox.
     * Pour chaque carte dans la liste des cartes de la fosse du joueur, une VBox
     * est créée en utilisant la méthode createFosseCard().
     * La VBox de la carte est ajoutée à la VBox principale.
     * Le contenu de la VBox principale est ajouté au ScrollPane.
     * Le ScrollPane est renvoyé en tant que résultat.
     * 
     * @param fosseJoueur la liste des cartes de la fosse du joueur
     * @return le ScrollPane pour afficher la fosse du joueur
     */
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

    /**
     * Cette méthode crée un ScrollPane pour afficher les oeuvres du joueur.
     * 
     * Elle prend en paramètre la liste des cartes des oeuvres du joueur.
     * 
     * Le ScrollPane est configuré pour s'adapter à la largeur.
     * Une VBox est créée pour contenir les éléments à afficher.
     * Un Label est créé pour afficher le titre "Oeuvres du joueur".
     * Le texte du Label est ajusté pour s'adapter à la taille.
     * La classe de style "titre_cartes" est ajoutée au Label.
     * Le Label est ajouté à la VBox.
     * Pour chaque carte dans la liste des cartes des oeuvres du joueur, une VBox
     * est créée en utilisant la méthode createOeuvreCard().
     * La VBox de la carte est ajoutée à la VBox principale.
     * Le contenu de la VBox principale est ajouté au ScrollPane.
     * Le ScrollPane est renvoyé en tant que résultat.
     * 
     * @param oeuvres la liste des cartes des oeuvres du joueur
     * @return le ScrollPane pour afficher les oeuvres du joueur
     */
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

    /**
     * Cette méthode affiche l'écran du joueur avec les informations fournies.
     * 
     * Elle prend en paramètre le pseudo du joueur actif, la liste des cartes de sa
     * main, de sa pile, de sa fosse, de sa vie future et de ses oeuvres.
     * 
     * Le panneau central est configuré pour ne pas afficher de contenu en bas.
     * Le panneau central est mis à jour avec l'écran de jeu créé à partir des
     * informations fournies.
     * Le style du panneau central est configuré avec une image de fond et une
     * taille de couverture.
     * L'indicateur de tour est mis à jour avec le pseudo du joueur actif.
     * 
     * @param joueurActifPseudo le pseudo du joueur actif
     * @param mainJoueur        la liste des cartes de la main du joueur
     * @param pileJoueur        la liste des cartes de la pile du joueur
     * @param fosseJoueur       la liste des cartes de la fosse du joueur
     * @param vieFuture         la liste des cartes de la vie future du joueur
     * @param oeuvres           la liste des cartes des oeuvres du joueur
     */
    public void afficherEcranJoueur(String joueurActifPseudo, List<Carte> mainJoueur, List<Carte> pileJoueur,
            List<Carte> fosseJoueur, List<Carte> vieFuture, List<Carte> oeuvres) {
        panelCentral.setBottom(null);
        panelCentral
                .setCenter(creerEcranJeu(joueurActifPseudo, mainJoueur, pileJoueur, fosseJoueur, vieFuture, oeuvres));

        panelCentral.setStyle("-fx-background-image: url('file:img/background.png');" +
                "-fx-background-size: cover;");
        mettreAJourIndicateurTour(joueurActifPseudo);

    }

    /**
     * Cette méthode rafraîchit la vue du joueur avec les informations fournies.
     * 
     * Elle prend en paramètre le pseudo du joueur actif, la liste des cartes de sa
     * main, de sa pile, de sa fosse, de sa vie future et de ses oeuvres.
     * 
     * Le panneau central est mis à jour avec l'écran de jeu créé à partir des
     * informations fournies.
     * L'indicateur de tour est mis à jour avec le pseudo du joueur actif.
     * 
     * @param joueurActifPseudo le pseudo du joueur actif
     * @param mainJoueur        la liste des cartes de la main du joueur
     * @param pileJoueur        la liste des cartes de la pile du joueur
     * @param fosseJoueur       la liste des cartes de la fosse du joueur
     * @param vieFuture         la liste des cartes de la vie future du joueur
     * @param oeuvres           la liste des cartes des oeuvres du joueur
     */
    public void rafraichirVueJoueur(String joueurActifPseudo, List<Carte> mainJoueur, List<Carte> pileJoueur,
            List<Carte> fosseJoueur, List<Carte> vieFuture, List<Carte> oeuvres) {
        panelCentral
                .setCenter(creerEcranJeu(joueurActifPseudo, mainJoueur, pileJoueur, fosseJoueur, vieFuture, oeuvres));
        mettreAJourIndicateurTour(joueurActifPseudo);

    }

    public void fermerFenetre() {
        primaryStage.close();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
