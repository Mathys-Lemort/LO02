import java.util.Collections;
public class Plateau {
    private List<Carte> source;
    private List<Carte> fosse;

    public Plateau(List<Carte> source, List<Carte> fosse) {
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

    public void initialiserSource() {
        // TODO Auto-generated method stub
    }

    public String toString() {
        return "Plateau{" + "source='" + this.source + "'" + ", fosse='" + this.fosse + "'" + "}";
    }
}
