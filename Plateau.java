import java.util.Collections;
import java.util.List;

import javax.xml.transform.Source;

public class Plateau {
    private List<Carte> source;
    private List<Carte> fosse;

    public Plateau(List<Carte> source, List<Carte> fosse) {
        // la source contient toutes les cartes du jeu initialise toutes les cartes dans la source

        this.source = source;
        this.fosse = fosse;
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

    public void melangerSource() {
        Collections.shuffle((java.util.List<?>) this.source);
    }

    public String afficherSource() {
        for (Carte carte : this.source) {
            System.out.println(carte);
        }
        return "afficherSource";
    }

    public void initialiserSource() {
        this.source.add(new Duperie());
        this.source.add(new Duperie());
        this.source.add(new Destinee());
        this.source.add(new Destinee());
        this.source.add(new Destinee());
        this.source.add(new Transmigration());
        this.source.add(new Transmigration());
        this.source.add(new Transmigration());
        this.source.add(new CoupdOeil());
        this.source.add(new CoupdOeil());
        this.source.add(new CoupdOeil());
        this.source.add(new Vol());
        this.source.add(new Vol());
        this.source.add(new Lendemain());
        this.source.add(new Lendemain());
        this.source.add(new Lendemain());
        this.source.add(new Recyclage());
        this.source.add(new Recyclage());
        this.source.add(new Recyclage());
        this.source.add(new Sauvetage());
        this.source.add(new Sauvetage());
        this.source.add(new Sauvetage());
        this.source.add(new Longevite());
        this.source.add(new Longevite());
        this.source.add(new Longevite());
        this.source.add(new Semis());
        this.source.add(new Semis());
        this.source.add(new Semis());
        this.source.add(new RevesBrises());
        this.source.add(new RevesBrises());
        this.source.add(new RevesBrises());
        this.source.add(new Deni());
        this.source.add(new Deni());
        this.source.add(new Deni());
        this.source.add(new Voyage());
        this.source.add(new Voyage());
        this.source.add(new Voyage());
        this.source.add(new Jubile());
        this.source.add(new Jubile());
        this.source.add(new Panique());
        this.source.add(new Panique());
        this.source.add(new Panique());
        this.source.add(new Roulette());
        this.source.add(new Roulette());
        this.source.add(new Roulette());
        this.source.add(new Fournaise());
        this.source.add(new Fournaise());
        this.source.add(new Fournaise());
        this.source.add(new Vengeance());
        this.source.add(new Vengeance());
        this.source.add(new Bassesse());
        this.source.add(new Bassesse());
        this.source.add(new DernierSouffle());
        this.source.add(new DernierSouffle());
        this.source.add(new DernierSouffle());
        this.source.add(new Crise());
        this.source.add(new Crise());
        this.source.add(new Crise());
        this.source.add(new Incarnation());
        this.source.add(new Incarnation());
        this.source.add(new Incarnation());
        this.source.add(new Incarnation());
        this.source.add(new Incarnation());
        this.source.add(new Mimetisme());
        this.source.add(new Mimetisme());


    }

    public String toString() {
        return "Plateau{" + "source='" + this.source + "'" + ", fosse='" + this.fosse + "'" + "}";
    }
}
