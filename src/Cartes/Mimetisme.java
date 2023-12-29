package Cartes;
import Core.Affichage;
import Joueurs.Joueur;

public class Mimetisme extends Carte{
    public Mimetisme() {
        super("Mimetisme","None", 1, "Choisissez un Rival.Copiez le pouvoir de son Oeuvre Exposée.", true);
    }

        @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Carte oeuvreExposee = adversaire.getOeuvreExposee();

        if (oeuvreExposee == null) {
            Affichage.afficherMessage("Vous ne pouvez pas copier le pouvoir de l'œuvre exposée de " + adversaire.getPseudo() + " car il n'en a pas.");
            return;
        }

        String nomOeuvre = oeuvreExposee.getNom();
        String pouvoirOeuvre = oeuvreExposee.getPouvoir();

        Affichage.afficherMessage("Vous avez copié le pouvoir de l'œuvre exposée de " + adversaire.getPseudo() + ", \"" + nomOeuvre + "\" : " + pouvoirOeuvre);
        oeuvreExposee.action(joueur, adversaire);
    }
}
