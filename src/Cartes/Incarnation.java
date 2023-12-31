package Cartes;

import Core.Affichage;
import Joueurs.Joueur;

import java.util.List;

public class Incarnation extends Carte {
    public Incarnation() {
        super("Incarnation", "None", 1, "Choisissez une de vos Oeuvres. Copiez son pouvoir.", true);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        List<Carte> oeuvres = joueur.getOeuvres();
        if (oeuvres.isEmpty()) {
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage("Vous n'avez pas d'œuvres à copier.");
            }
            return;
        }

        int choix = obtenirChoixCarte(oeuvres);
        if (choix != -1) {
            Carte carteChoisie = oeuvres.get(choix);
            if (!(joueur instanceof Joueurs.JoueurBot)) {

            Affichage.afficherMessage(
                    "Vous avez choisi la carte " + carteChoisie.getNom() + " dont le pouvoir est : " + carteChoisie.getPouvoir());
            carteChoisie.action(joueur, adversaire);
            }
        }
    }

}
