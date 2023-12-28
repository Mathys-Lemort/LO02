package Cartes;
import Core.Affichage;
import Core.Partie;
import Joueurs.Joueur;

public class Jubile extends Carte {
    public Jubile() {
        super("Jubile","vert", 3, "Placez jusqu’à 2 cartes de votre Main sur vos Oeuvres.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        for (int i = 0; i < 2; i++) {
            Affichage.afficherMessage("Voici vos cartes :");
            Affichage.afficherOption(0, "Ne pas placer de carte");

            for (int j = 0; j < joueur.getMain().size(); j++) {
                Affichage.afficherOption(j+1, joueur.getMain().get(j).getNom());
            }
            Affichage.afficherMessage("Quelle carte voulez-vous placer sur vos oeuvres ?");
            int choix = Partie.getInstance().getScanner().nextInt();
            if (choix != 0) {
                Carte carte = joueur.getMain().get(choix-1);
                joueur.ajouterCarteDansOeuvres(carte);
                joueur.suppCarteMain(carte);
                Affichage.afficherMessage("Vous avez placé la carte " + carte.getNom() + " sur vos oeuvres");
            }
        }
            

       
    }
}
