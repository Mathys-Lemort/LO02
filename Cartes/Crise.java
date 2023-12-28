package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

public class Crise extends Carte {
    public Crise() {
        super("Crise","rouge", 2, "Le rival de votre choix défausse\n" + //
                "une de ses Oeuvres.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        // Si l'adversaire n'a pas d'oeuvre, on ne fait rien 
        if (adversaire.getOeuvres().size() == 0) {
            Affichage.afficherMessage("Votre adversaire n'a pas d'oeuvre, tant pis pour vous !");
            return;
        }
        Affichage.afficherTitre(adversaire.getPseudo()+" doit défausser une oeuvre");
        adversaire.defausserOeuvreChoix();
        

    }
}
