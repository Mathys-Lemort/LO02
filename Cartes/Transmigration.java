package Cartes;
import Joueurs.Joueur;

public class Transmigration extends Carte{

    public Transmigration() {
        super("Transmigration","bleu", 1, "Placez dans votre Main n’importequelle carte de votre Vie Future.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        // // afficher les cartes de la vie future
        // joueur.afficherCartesVieFuture();
        // // demander quelle carte il veut placer dans sa main
        // int choix = joueur.choisirCarteVieFuture();
        // // ajouter la carte à la main
        // joueur.ajouterCarteMain(joueur.getCarteVieFuture(choix));
    }
    
    
}
