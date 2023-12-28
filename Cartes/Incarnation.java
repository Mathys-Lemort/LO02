package Cartes;
import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Incarnation extends Carte{
    public Incarnation() {
        super("Incarnation","None", 1, "Choisissez une de vos Oeuvres. Copiez son pouvoir.", true);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        Affichage.afficherMessage("Voici vos oeuvres :");
        for (int i = 0; i < joueur.getOeuvres().size(); i++) {
            Affichage.afficherOption(i+1, joueur.getOeuvres().get(i).getNom());
        }
        Affichage.afficherMessage("Quelle pouvoir voulez-vous copier ? (1, 2 ou 3)");
        int choix = Partie.getInstance().getScanner().nextInt();
        Carte carte = joueur.getOeuvres().get(choix-1);
        Affichage.afficherMessage("Vous avez choisi la carte " + carte.getNom() + " dont le pouvoir est : " + carte.getPouvoir());
        carte.action(joueur, adversaire);
    }
}
