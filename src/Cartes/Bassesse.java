package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

public class Bassesse extends Carte {
    public Bassesse() {
        super("Bassesse","rouge", 3, "Défaussez au hasard 2 cartes\n" + //
                "de la Main d’un rival.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        int tailleMainAdversaire = adversaire.getMain().size();

        if (tailleMainAdversaire == 0) {
            
            Affichage.afficherMessage("Votre adversaire n'a pas de carte en main, aucune action possible.");
            
            return;
        }

        int nbCartesADefaussées = Math.min(tailleMainAdversaire, 2);
        adversaire.defausserCarte(nbCartesADefaussées);
        
        String message = (nbCartesADefaussées == 1) 
                        ? "Votre adversaire a défaussé 1 carte au hasard." 
                        : "Votre adversaire a défaussé 2 cartes au hasard.";
        Affichage.afficherMessage(message);
        
    }


}
