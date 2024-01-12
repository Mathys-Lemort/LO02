package Cartes;
import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

/**
 * La classe Mimetisme représente une carte de type Mimetisme dans le jeu.
 * Cette carte permet au joueur de copier le pouvoir d'une œuvre exposée d'un rival.
 */
public class Mimetisme extends Carte{
    /**
     * Constructeur de la classe Mimetisme.
     * Initialise les attributs de la carte Mimetisme.
     */
    public Mimetisme() {
        super("Mimetisme","None", 1, "Choisissez un Rival.Copiez le pouvoir de son Oeuvre Exposée.", true);
    }

    /**
     * Méthode qui effectue l'action de la carte Mimetisme.
     * Copie le pouvoir de l'œuvre exposée d'un adversaire et l'applique au joueur actuel.
     * @param joueur le joueur actuel
     * @param adversaire le joueur adversaire
     */
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
                    Partie.getInstance().setJoueurActif(Partie.getInstance().getJoueurRival());

    }
}
