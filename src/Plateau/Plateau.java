package Plateau;

import java.util.*;
import Cartes.*;

/**
 * Classe représentant un plateau de jeu avec des cartes.
 * Elle gère deux ensembles de cartes : source et fosse.
 */
public class Plateau {
    private List<Carte> source = new ArrayList<>();
    private List<Carte> fosse = new ArrayList<>();

    /**
     * Constructeur par défaut.
     * Initialise un plateau vide.
     */
    public Plateau() {
    }

    /**
     * Constructeur avec des listes de cartes pour source et fosse.
     * 
     * @param source La liste initiale de cartes dans la source.
     * @param fosse La liste initiale de cartes dans la fosse.
     */
    public Plateau(List<Carte> source, List<Carte> fosse) {
        this.source = new ArrayList<>(source);
        this.fosse = new ArrayList<>(fosse);
    }

    /**
     * Retourne la liste des cartes dans la source.
     * 
     * @return La liste des cartes dans la source.
     */
    public List<Carte> getSource() { return source; }

    /**
     * Définit la liste des cartes dans la source.
     * 
     * @param source La nouvelle liste des cartes pour la source.
     */
    public void setSource(List<Carte> source) { this.source = new ArrayList<>(source); }

    /**
     * Retourne la liste des cartes dans la fosse.
     * 
     * @return La liste des cartes dans la fosse.
     */
    public List<Carte> getFosse() { return fosse; }

    /**
     * Définit la liste des cartes dans la fosse.
     * 
     * @param fosse La nouvelle liste des cartes pour la fosse.
     */
    public void setFosse(List<Carte> fosse) { this.fosse = new ArrayList<>(fosse); }

    /**
     * Initialise la source avec un ensemble prédéfini de cartes.
     * Les cartes sont ajoutées en fonction de leur classe et du nombre spécifié.
     */
    public void initialiserSource() {
        Map<Class<? extends Carte>, Integer> cartesAInitialiser = getCartesAInitialiser();
        cartesAInitialiser.forEach((carteClass, nombre) -> {
            for (int i = 0; i < nombre; i++) {
                ajouterCarte(carteClass);
            }
        });
        Collections.shuffle(source);
    }

    /**
     * Crée une carte de chaque type spécifié et l'ajoute à la source.
     */
    private void ajouterCarte(Class<? extends Carte> carteClass) {
        try {
            source.add(carteClass.getDeclaredConstructor().newInstance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retourne une représentation sous forme de chaîne de caractères du plateau.
     * 
     * @return La représentation sous forme de chaîne de caractères du plateau.
     */
    @Override
    public String toString() {
        return "Plateau{" + "source=" + source + ", fosse=" + fosse + "}";
    }

    /**
     * Point d'entrée principal pour tester le plateau.
     * 
     * @param args Arguments passés depuis la ligne de commande.
     */
    public static void main(String[] args) {
        Plateau plateau = new Plateau();
        plateau.initialiserSource();
        Map<String, Integer> couleurCartes = new HashMap<>();

        plateau.getSource().stream().map(Carte::getCouleur).forEach(
            couleur -> couleurCartes.merge(couleur, 1, Integer::sum)
        );

        couleurCartes.forEach((couleur, nombre) ->
            System.out.println(couleur + ": " + nombre)
        );
    }
}
