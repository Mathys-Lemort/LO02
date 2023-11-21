import java.util.ArrayList;
import java.util.List;

public class Joueur {
    // Attributs basés sur le diagramme UML
    private String id;
    private List<Carte> main;
    private List<Carte> pioche;
    private List<Carte> defausse;
    private List<Carte> cartesEnJeu;
    private List<StrategieJeu> strategieStrategies;

    // Constructeur
    public Joueur(String id) {
        this.id = id;
        this.main = new ArrayList<>();
        this.pioche = new ArrayList<>();
        this.defausse = new ArrayList<>();
        this.cartesEnJeu = new ArrayList<>();
        this.strategieStrategies = new ArrayList<>();
    }
    public void jouerCartePourPoints() {
        // TODO Auto-generated method stub

    }

    public void jouerCartePourPouvoir() {
        // TODO Auto-generated method stub

    }

    public void jouerCartePourFutur() {
        // TODO Auto-generated method stub

    }

    public void piocher() {
        // TODO Auto-generated method stub

    }

    public void defausser() {
        // TODO Auto-generated method stub

    }

    public boolean pileVide() {
        // TODO Auto-generated method stub
        return false;
    }

    public void ajouterCarte() {
        // TODO Auto-generated method stub

    }

    public void trasmettreCarte() {
        // TODO Auto-generated method stub

    }

    public void passerTour() {
        // TODO Auto-generated method stub

    }

    public void setStrategie() {
        // TODO Auto-generated method stub

    }

    public String toString(){
        return "Joueur: " + this.id;
    }


}
