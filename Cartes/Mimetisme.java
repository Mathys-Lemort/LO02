package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

public class Mimetisme extends Carte{
    public Mimetisme() {
        super("Mimetisme","None", 1, "Choisissez un Rival.Copiez le pouvoir de son Oeuvre Exposée.", true);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Affichage.afficherMessage("Vous avez copié le pouvoir de l'oeuvre exposée de " + adversaire.getPseudo() + " : " + adversaire.getOeuvreExposee().getPouvoir());
        adversaire.getOeuvreExposee().action(joueur, adversaire);       
    }
}
