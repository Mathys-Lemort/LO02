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

    // Méthodes basées sur le diagramme UML
    public void piocherCarte() {
        // Implémentation de la méthode piocherCarte
    }

    public void jouerCarte() {
        // Implémentation de la méthode jouerCarte
    }

    public void defausserCarte() {
        // Implémentation de la méthode defausserCarte
    }

    // Autres méthodes selon le diagramme UML...
}
