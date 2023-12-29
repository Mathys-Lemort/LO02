package Plateau;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import Cartes.Bassesse;
import Cartes.Carte;
import Cartes.CoupdOeil;
import Cartes.Crise;
import Cartes.Deni;
import Cartes.DernierSouffle;
import Cartes.Destinee;
import Cartes.Duperie;
import Cartes.Fournaise;
import Cartes.Incarnation;
import Cartes.Jubile;
import Cartes.Lendemain;
import Cartes.Longevite;
import Cartes.Mimetisme;
import Cartes.Panique;
import Cartes.Recyclage;
import Cartes.RevesBrises;
import Cartes.Roulette;
import Cartes.Sauvetage;
import Cartes.Semis;
import Cartes.Transmigration;
import Cartes.Vengeance;
import Cartes.Vol;
import Cartes.Voyage;

public class Plateau {
    private List<Carte> source;
    private List<Carte> fosse;

    public Plateau(List<Carte> source, List<Carte> fosse) {
        // la source contient toutes les cartes du jeu initialise toutes les cartes dans la source

        this.source = source;
        this.fosse = fosse;
    }

    public Plateau() {
        this.source = new java.util.ArrayList<Carte>();
        this.fosse = new java.util.ArrayList<Carte>();
    }

    public List<Carte> getSource() {
        return this.source;
    }

    public List<Carte> getFosse() {
        return this.fosse;
    }

    public void setSource(List<Carte> source) {
        this.source = source;
    }

    public void setFosse(List<Carte> fosse) {
        this.fosse = fosse;
    }


    public void afficherSource() {
        for (Carte carte : this.source) {
            System.out.println(carte);
        }
    }

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
                // Gérez l'exception comme il convient (log, throw, etc.)
                e.printStackTrace();
            }
        }
    }

    // Mélangez la liste des cartes
    Collections.shuffle(this.source);
    }

    public String toString() {
        return "Plateau{" + "source='" + this.source + "'" + ", fosse='" + this.fosse + "'" + "}";
    }

     
     public static void main(String[] args) {
            Plateau plateau = new Plateau();
            plateau.initialiserSource();
            // Afficher le nombre de carte dans la source
            System.out.println(plateau.source.size());
            // Afficher le nombre de chaque couleur des cartes de la source ("rouge: 5", "bleu: 3", etc.)
            Map<String, Integer> couleurCartes = new HashMap<>();
            for (Carte carte : plateau.source) {
                String couleur = carte.getCouleur();
                if (couleurCartes.containsKey(couleur)) {
                    couleurCartes.put(couleur, couleurCartes.get(couleur) + 1);
                } else {
                    couleurCartes.put(couleur, 1);
                }
            }
            for (Map.Entry<String, Integer> entry : couleurCartes.entrySet()) {
                String couleur = entry.getKey();
                Integer nombre = entry.getValue();
                System.out.println(couleur + ": " + nombre);
            }
            
        }
}



