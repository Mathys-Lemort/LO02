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
        Map<Class<? extends Carte>, Integer> cartesAInitialiser = new HashMap<>();
        cartesAInitialiser.put(Duperie.class, 2);
        cartesAInitialiser.put(Destinee.class, 3);
        cartesAInitialiser.put(Transmigration.class, 3);
        cartesAInitialiser.put(CoupdOeil.class, 3);
        cartesAInitialiser.put(RevesBrises.class, 3);
        cartesAInitialiser.put(Deni.class, 3);
        cartesAInitialiser.put(Vol.class, 2);
        cartesAInitialiser.put(Lendemain.class, 3);
        cartesAInitialiser.put(Recyclage.class, 3);
        cartesAInitialiser.put(Sauvetage.class, 3);
        cartesAInitialiser.put(Longevite.class, 3);
        cartesAInitialiser.put(Semis.class, 3);
        cartesAInitialiser.put(Voyage.class, 2);
        cartesAInitialiser.put(Jubile.class, 2);
        cartesAInitialiser.put(Panique.class, 3);
        cartesAInitialiser.put(DernierSouffle.class, 3);
        cartesAInitialiser.put(Crise.class, 3);
        cartesAInitialiser.put(Roulette.class, 3);
        cartesAInitialiser.put(Fournaise.class, 3);
        cartesAInitialiser.put(Vengeance.class, 2);
        cartesAInitialiser.put(Bassesse.class, 2);
        cartesAInitialiser.put(Incarnation.class, 5);
        cartesAInitialiser.put(Mimetisme.class, 2);
    
        for (Map.Entry<Class<? extends Carte>, Integer> entry : cartesAInitialiser.entrySet()) {
            Class<? extends Carte> carteClass = entry.getKey();
            Integer nombre = entry.getValue();
            for (int i = 0; i < nombre; i++) {
                try {
                    this.source.add(carteClass.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        Collections.shuffle(this.source);
        
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
