package Cartes;
import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Destinee extends Carte {
    public Destinee() {
        super("Destinée","bleu", 2, "Regardez les 3 premières cartes de la Source ; ajoutez-en jusqu’à 2 à votre Vie Future. Replacez le reste dans l'ordre souhaité", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Partie.getInstance().afficher3PremieresCartesSource();
        // demander combien de cartes il veut mettre dans sa vie future (0,1 ou 2)
        Affichage.afficherMessage("Combien de cartes voulez-vous mettre dans votre vie future ? (0, 1 ou 2)");
        int nbCartes = Partie.getInstance().getScanner().nextInt();
        // demander quelles cartes il veut mettre dans sa vie future
        for (int i = 0; i < nbCartes; i++) {
            Affichage.afficherMessage("Quelle carte voulez-vous mettre dans votre vie future ? (1, 2 ou 3)");
            int choix = Partie.getInstance().getScanner().nextInt();
            Carte carte = Partie.getInstance().getCartesSource().get(choix-1);
            joueur.ajouterCarteDansVieFuture(carte);
            Partie.getInstance().getCartesSource().remove(carte);
        }
        Affichage.afficherMessage("Vous avez ajouté " + nbCartes + " cartes dans votre vie future");
        
    }
}
