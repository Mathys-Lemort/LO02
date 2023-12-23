package Core;
public class Affichage {

    public static void afficherTitre(String titre) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║ " + centrerTexte(titre, 30) + " ║");
        System.out.println("╚══════════════════════════════╝\n");
    }

    public static void afficherMessage(String message) {
        System.out.println("  ➤ " + message);
    }

    public static void afficherOption(int num, String option) {
        System.out.println("  [" + num + "] " + option);
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
