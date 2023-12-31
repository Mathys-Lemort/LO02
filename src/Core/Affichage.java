package Core;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;

public class Affichage {

    // Utilisez ces constantes pour ajouter de la couleur à votre affichage
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void afficherTitre(String titre) {
        System.out.println(ANSI_CYAN + "\n╔══════════════════════════════╗");
        System.out.println("║ " + centrerTexte(titre, 30) + " ║");
        System.out.println("╚══════════════════════════════╝\n" + ANSI_RESET);
    }

    public static void afficherMessage(String message) {
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


    public static void afficherOption(int num, String option) {
        System.out.println(ANSI_GREEN + "  [" + num + "] " + option + ANSI_RESET);
        if (Partie.getInstance().getMode().equals(Partie.Mode.GRAPHIQUE)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("[" + num + "] " + option);
            alert.showAndWait();         
        }
    }


    private static String centrerTexte(String texte, int largeur) {
        // Tronquer le texte si nécessaire
        if (texte.length() > largeur) {
            texte = texte.substring(0, largeur);
        }

        int espacesDevant = (largeur - texte.length()) / 2;
        StringBuilder sb = new StringBuilder();

        // Ajouter des espaces avant le texte
        for (int i = 0; i < espacesDevant; i++) {
            sb.append(" ");
        }

        sb.append(texte);

        // Compléter avec des espaces pour aligner à droite
        while (sb.length() + 2 < largeur) {
            sb.append(" ");
        }

        return sb.toString();
    }

    // Autres méthodes d'affichage personnalisées
}
