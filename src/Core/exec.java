package Core;
public class exec {

    public static void main(String[] args) {
        Partie partie = Partie.getInstance();
        partie.setMode(Partie.Mode.CONSOLE);
        partie.commencerPartie();
    }
    

}
