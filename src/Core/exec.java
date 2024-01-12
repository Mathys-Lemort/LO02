package Core;
/**
 * La classe exec est la classe principale qui permet d'exécuter le jeu.
 */
public class exec {

    /**
     * La méthode main est le point d'entrée du programme.
     * Elle crée une instance de la classe Partie, configure le mode de jeu en mode console,
     * puis commence la partie.
     * 
     * @param args les arguments de ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        Partie partie = Partie.getInstance();
        partie.setMode(Partie.Mode.CONSOLE);
        partie.commencerPartie();
    }
}
