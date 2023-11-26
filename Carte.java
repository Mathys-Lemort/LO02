public abstract class Carte {
    private String couleur;
    private int points;
    private String pouvoir;
    private boolean estMosaique;

    public Carte(String couleur, int points, String pouvoir, boolean estMosaique) {
        this.couleur = couleur;
        this.points = points;
        this.pouvoir = pouvoir;
        this.estMosaique = estMosaique;
    }

    public String getCouleur() {
        return this.couleur;
    }

    public int getPoints() {
        return this.points;
    }

    public String getPouvoir() {
        return this.pouvoir;
    }

    public boolean getEstMosaique() {
        return this.estMosaique;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setPouvoir(String pouvoir) {
        this.pouvoir = pouvoir;
    }

    public void setEstMosaique(boolean estMosaique) {
        this.estMosaique = estMosaique;
    }

    public abstract void action(Joueur joueur, Joueur adversaire);

    public String toString() {
        return "Carte{" + "couleur='" + this.couleur + "'" + ", points='" + this.points + "'" + ", pouvoir='"
                + this.pouvoir + "'" + ", estMosaique='" + this.estMosaique + "'" + "}";
    }
    
}
