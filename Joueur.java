import java.util.ArrayList;
import java.util.List;

public class Joueur {
    private String id;
    private List<Carte> main;
    private List<Carte> pile;
    private List<Carte> defausse;
    private List<Carte> cartesEnJeu;
    private List<Carte> vieFuture;
    private List<StrategieJeu> strategieStrategies;

    // Constructeur
    public Joueur(String id) {
        this.id = id;
        this.main = new ArrayList<>();
        this.pile = new ArrayList<>();
        this.defausse = new ArrayList<>();
        this.cartesEnJeu = new ArrayList<>();
        this.vieFuture = new ArrayList<>();
        this.strategieStrategies = new ArrayList<>();
    }
    public void jouerCartePourPoints(Carte carte) {
        this.cartesEnJeu.add(carte);
        this.main.remove(carte);
    }

    public void jouerCartePourPouvoir(Carte carte, Joueur rival) {
        carte.action(this, rival);
        this.main.remove(carte);
    }

    public void jouerCartePourFutur(Carte carte) {
        this.vieFuture.add(carte);
        this.main.remove(carte);
    }


    public void piocher() {
        this.cartesEnJeu.add(this.pile.get(0));

    }

    public void defausser() {
        this.defausse.add(this.main.get(0));
        this.main.remove(0);

    }

    public boolean pileVide() {
        return this.pile.isEmpty();
    }

    public void ajouterCarte() {
        this.main.add(this.pile.get(0));

    }

    public void trasmettreCarte() {
        // TODO Auto-generated method stub

    }

    public void passerTour() {
        // TODO Auto-generated method stub

    }

    public void setStrategie() {
        

    }

    public String toString(){
        return "Joueur: " + this.id;
    }


}
