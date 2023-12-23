package joueurs;
import java.util.ArrayList;
import java.util.List;
import Cartes.Carte;

public class Joueur {
    private String id;
    private List<Carte> main;
    private List<Carte> pile;
    private List<Carte> defausse;
    private List<Carte> Oeuvres;
    private List<Carte> vieFuture;
    private List<StrategieJeu> strategieStrategies;
    private String positionEchelleKarmique;
    private int anneauxKarmiques;

    // Constructeur
    public Joueur(String id) {
        this.id = id;
        this.main = new ArrayList<>();
        this.pile = new ArrayList<>();
        this.defausse = new ArrayList<>();
        this.Oeuvres = new ArrayList<>();
        this.vieFuture = new ArrayList<>();
        this.strategieStrategies = new ArrayList<>();
    }
    public void jouerCartePourPoints(Carte carte) {
        this.Oeuvres.add(carte);
        this.main.remove(carte);
    }

    public String getPseudo () {
        return this.id;
    }

    public void jouerCartePourPouvoir(Carte carte, Joueur rival) {
        carte.action(this, rival);
        this.main.remove(carte);
        rival.main.add(carte);
    }

    public void jouerCartePourFutur(Carte carte) {
        this.vieFuture.add(carte);
        this.main.remove(carte);
    }


    public void piocher() {
        this.main.add(this.pile.get(0));

    }

    public void defausser() {
        this.defausse.add(this.main.get(0));
        this.main.remove(0);

    }

    public void defausserCarte(int nbCartes) {
        for (int i = 0; i < nbCartes; i++) {
            int index = (int) (Math.random() * this.main.size());
            this.defausse.add(this.main.get(index));
            this.main.remove(index);
        }

    }

    public void afficherMain() {
        for (Carte carte : this.main) {
            System.out.println(carte);
        }

    }

    public void afficherCartesMain(int nbCartes) {
        for (int i = 0; i < nbCartes; i++) {
            System.out.println(this.main.get(i));
        }
    }
    
    public List<Carte> getMain() {
        return this.main;
    }

    public boolean pileVide() {
        return this.pile.isEmpty();
    }

    public void ajouterCarte() {
        this.main.add(this.pile.get(0));

    }

    public void ajouterCarteDansMain(Carte carte) {
        this.main.add(carte);

    }

    public void ajouterCarteDansPile(Carte carte) {
        this.pile.add(carte);

    }


    public void ajouterCarteDansDefausse(Carte carte) {
        this.defausse.add(carte);

    }

    public void ajouterCarteDansOeuvres(Carte carte) {
        this.Oeuvres.add(carte);

    }

    public void ajouterCarteDansVieFuture(Carte carte) {
        this.vieFuture.add(carte);

    }

    public void afficherCartesVieFuture() {
        for (Carte carte : this.vieFuture) {
            System.out.println(carte);
        }
    }

    public void afficherCartesOeuvres() {
        for (Carte carte : this.Oeuvres) {
            System.out.println(carte);
        }
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
        return this.id;
    }


}