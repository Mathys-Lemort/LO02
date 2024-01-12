package Core;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;

/**
 * La classe Affichage est responsable de l'affichage des messages et des options dans le jeu.
 */
public class Affichage {

    /**
     * Code ANSI pour réinitialiser la couleur de la console.
     */
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Code ANSI pour la couleur jaune dans la console.
     */
    public static final String ANSI_YELLOW = "\u001B[33m";

    /**
     * Code ANSI pour la couleur cyan dans la console.
     */
    public static final String ANSI_CYAN = "\u001B[36m";

    /**
     * Code ANSI pour la couleur verte dans la console.
     */
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Affiche le titre dans la console.
     * 
     * @param titre Le titre à afficher.
     */
    public static void afficherTitre(String titre) {
        if (Partie.getInstance().getJoueurActif() instanceof Joueurs.JoueurBot) {
            return;
        }
        System.out.println(ANSI_CYAN + "\n╔══════════════════════════════╗");
        System.out.println("║ " + centrerTexte(titre, 30) + " ║");
        System.out.println("╚══════════════════════════════╝\n" + ANSI_RESET);
    }

    /**
     * Affiche le message dans la console et éventuellement dans une fenêtre d'alerte graphique.
     * 
     * @param message Le message à afficher.
     */
    public static void afficherMessage(String message) {
        if (Partie.getInstance().getJoueurActif() instanceof Joueurs.JoueurBot) {
            return;
        }
        System.out.println(ANSI_YELLOW + "  ➤ " + message + ANSI_RESET);

        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);

            Label label = new Label(message);
            label.setWrapText(true);
            alert.getDialogPane().setContent(label);
            alert.showAndWait();
        }
    }

    /**
     * Affiche l'option avec son numéro dans la console et éventuellement dans une fenêtre d'alerte graphique.
     * 
     * @param num    Le numéro de l'option.
     * @param option L'option à afficher.
     */
    public static void afficherOption(int num, String option) {
        if (Partie.getInstance().getJoueurActif() instanceof Joueurs.JoueurBot) {
            return;
        }
        System.out.println(ANSI_GREEN + "  [" + num + "] " + option + ANSI_RESET);
        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("[" + num + "] " + option);
            alert.showAndWait();
        }
    }

    /**
     * Centre le texte en ajoutant des espaces devant et derrière.
     * 
     * @param texte   Le texte à centrer.
     * @param largeur La largeur totale du texte centré.
     * @return Le texte centré.
     */
    private static String centrerTexte(String texte, int largeur) {
        if (texte.length() > largeur) {
            texte = texte.substring(0, largeur);
        }

        int espacesDevant = (largeur - texte.length()) / 2;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < espacesDevant; i++) {
            sb.append(" ");
        }

        sb.append(texte);

        while (sb.length() + 2 < largeur) {
            sb.append(" ");
        }

        return sb.toString();
    }

}
