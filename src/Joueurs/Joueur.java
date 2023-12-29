package Joueurs;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Cartes.Carte;
import Core.Affichage;
import Core.Partie;

//Faire une enum des positions de l'échelle karmique



public class Joueur {
    private String id;
    private List<Carte> main;
    private List<Carte> pile;
    private List<Carte> defausse;
    private List<Carte> Oeuvres;
    private List<Carte> vieFuture;
    private EchelleKarmique positionEchelleKarmique;
    private int anneauxKarmiques;


    public enum EchelleKarmique {
        BOUSIER(0),
        SERPENT(4), // 4 est le nombre de points nécessaires pour passer à l'étape suivante
        LOUP(5),
        SINGE(6),
        TRANSCEANDANCE(7);    
        private final int pointsPourAvancer;
    
        EchelleKarmique(int points) {
            this.pointsPourAvancer = points;
        }
    
        public int getPointsPourAvancer() {
            return this.pointsPourAvancer;
        }
    }
    
    // Constructeur
    public Joueur(String id) {
        this.id = id;
        this.main = new ArrayList<>();
        this.pile = new ArrayList<>();
        this.defausse = new ArrayList<>();
        this.Oeuvres = new ArrayList<>();
        this.vieFuture = new ArrayList<>();
        this.anneauxKarmiques = 0;
        this.positionEchelleKarmique = EchelleKarmique.BOUSIER;


    }

    public void setPseudo(String pseudo) {
        this.id = pseudo;
    }

    public int getPoints(){
        int pointsRouge = 0;
        int pointsVert = 0;
        int pointsBleu = 0;
        for (Carte carte : this.Oeuvres) {
            if (carte.getCouleur().equals("rouge")) {
                pointsRouge += carte.getPoints();
            } else if (carte.getCouleur().equals("vert")) {
                pointsVert += carte.getPoints();
            } else if (carte.getCouleur().equals("bleu")) {
                pointsBleu += carte.getPoints();
            } else {
                pointsRouge += carte.getPoints();
                pointsVert += carte.getPoints();
                pointsBleu += carte.getPoints();
            }
        }
        pointsRouge += this.anneauxKarmiques;
        pointsVert += this.anneauxKarmiques;
        pointsBleu += this.anneauxKarmiques;
        Affichage.afficherMessage(this.getPseudo() + ", vous avez " + pointsRouge + " points rouges, " + pointsVert + " points verts et " + pointsBleu + " points bleus");
        return Math.max(pointsRouge, Math.max(pointsVert, pointsBleu));
    }

    public String getPositionEchelleKarmique() {
        return this.positionEchelleKarmique.name();
    }

    public void reincarnation() {
        // Vérifier si le joueur a assez de points pour passer à l'étape suivante
        if (this.getPoints() >= this.positionEchelleKarmique.getPointsPourAvancer()) {
            // Passer à l'étape suivante
            this.positionEchelleKarmique = EchelleKarmique.values()[this.positionEchelleKarmique.ordinal() + 1];
            Affichage.afficherMessage("Vous passez maintenant à l'étape " + this.positionEchelleKarmique.name() + " de l'échelle karmique");

        }
        else {
            // Rajouter un anneau karmique
            this.anneauxKarmiques++;
            Affichage.afficherMessage(this.getPseudo() + ", vous avez gagné un anneau karmique mais vous restez à la même position de l'échelle karmique");
        }
        // Si il n'a pas atteint la transceandance, il est réincarné
        if (this.positionEchelleKarmique != EchelleKarmique.TRANSCEANDANCE) {
             this.defausse.addAll(this.Oeuvres);
            this.Oeuvres.clear();
            this.main.addAll(this.vieFuture);
            this.vieFuture.clear();
            this.pile.clear();
            while ((this.main.size() + this.pile.size()) < 6) {
                Partie.getInstance().piocherSourcePile(this);
            }
            Affichage.afficherMessage(this.getPseudo() + ", vous avez été réincarné");
        }
        else {
            Affichage.afficherTitre(this.getPseudo() + " Transceandance !!");
            Affichage.afficherTitre(this.getPseudo() + " Transceandance !!");
            Affichage.afficherTitre(this.getPseudo() + " Transceandance !!");

            Partie.getInstance().terminerPartie();
        }
        
       



    }

    public void jouerCartePourPoints(Carte carte) {
        this.Oeuvres.add(carte);
        this.main.remove(carte);
    }


    public String getPseudo () {
        return this.id;
    }

    public void jouerCartePourPouvoir(Carte carte, Joueur rival) {
        this.main.remove(carte);
        // demander au rival si il veut mettre la carte dans sa vie future
        Affichage.afficherTitre(rival.getPseudo() + ", Attention !");
        Affichage.afficherMessage("Voulez vous mettre la carte " + carte.getNom() + " dans votre vie future ?");
        Affichage.afficherOption(1, "- Oui");
        Affichage.afficherOption(2, "- Non");
        int choix = Partie.getInstance().getScanner().nextInt();
        Partie.getInstance().getScanner().nextLine(); // Consomme la nouvelle ligne après nextInt()
        if (choix == 1) {
            rival.ajouterCarteDansVieFuture(carte);
        } else {
            rival.defausserCarteChoisit(carte);
        }
        carte.action(this, rival);
    }
    

    public void jouerCartePourFutur(Carte carte) {
        this.vieFuture.add(carte);
        this.main.remove(carte);
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
        for (int i = 0; i < this.main.size(); i++) {
            System.out.println((i+1) + " - " + this.main.get(i));
        }
    }
    
    public List<Carte> getMain() {
        return this.main;
    }

    public List<Carte> getOeuvres() {
        return this.Oeuvres;
    }
    public List<Carte> getVieFuture() {
        return this.vieFuture;
    }
    public List<Carte> getDefausse() {
        return this.defausse;
    }

    public List<Carte> getPile() {
        return this.pile;
    }
    public boolean pileVide() {
        return this.pile.isEmpty();
    }

    public boolean mainVide() {
        return this.main.isEmpty();
    }

    public void ajouterCarte() {
        this.main.add(this.pile.remove(0));

    }

    public void ajouterCarteDansMain(Carte carte) {
        this.main.add(carte);

    }

    public void suppCarteMain(Carte carte) {
        this.main.remove(carte);

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
        for (int i = 0; i < this.Oeuvres.size(); i++) {
            System.out.println((i+1) + " - " + this.Oeuvres.get(i));
        }
    }

    public void defausserOeuvreChoix(){
        Affichage.afficherMessage("Voici vos oeuvres:");
        afficherCartesOeuvres();
        Affichage.afficherMessage("Choisissez une carte à défausser:");
        Scanner scanner = Partie.getInstance().getScanner();
        int choix = scanner.nextInt();
        scanner.nextLine(); // Consomme la nouvelle ligne après nextInt()
        Carte carte = this.Oeuvres.get(choix-1);
        this.defausse.add(carte);
        this.Oeuvres.remove(carte);
        Affichage.afficherMessage(this.getPseudo() +", vous avez défaussé la carte " + carte.getNom()+" de vos oeuvres");          
    }

    public void defausserCarteChoisit(Carte carte){
        this.defausse.add(carte);
        this.main.remove(carte);
        Affichage.afficherMessage(this.getPseudo() +", vous avez défaussé la carte " + carte.getNom()+ "de votre main");
    }


    public void passerTour() {
        Affichage.afficherMessage(this.getPseudo() + " passe son tour");
    }

    public void setStrategie() {
    }

    public String toString(){
        return this.id;
    }

    public ArrayList<Carte> getMainChiffre(int nbCartes) {
        ArrayList<Carte> temp = new ArrayList<>();
        for (int i = 0; i < nbCartes; i++) {
            int index = (int) (Math.random() * this.main.size());
            if (!temp.contains(this.main.get(index))) {
                temp.add(this.main.get(index));
            } else {
                i--;
            }
        }
        return temp;              
    }

    public void defausserCarteVieFutureChiffre(int nbCartes) {
        for (int i = 0; i < nbCartes; i++) {
            this.defausse.add(this.vieFuture.get(i));
            this.vieFuture.remove(i);
        }
    }

    public Carte getOeuvreExposee(){
        // Vérifier si il y a une oeuvre exposée si oui la return sinon return null
        if (this.Oeuvres.size() > 0) {
            return this.Oeuvres.get(0);
        }
        else {
            return null;
        }
    }

    public void defausserCartePile() {
        Affichage.afficherMessage("Vous avez défaussé la carte " + this.pile.get(0).getNom() + " de la pile");
        this.defausse.add(this.pile.get(0));
        this.pile.remove(0);
    }

    public ArrayList<Carte> getCartesFosse(int nbCartes) {
    ArrayList<Carte> temp = new ArrayList<>();
    // Limitez le nombre de cartes à récupérer à la taille de la liste 'defausse'
    int actualNbCartes = Math.min(nbCartes, this.defausse.size());
    for (int i = 0; i < actualNbCartes; i++) {
        temp.add(this.defausse.get(i));
    }
    return temp;              
}


    public List<Carte> getFosse(){
        return this.defausse;
    }

    public Carte getCarteVieFuture(int index){
        return this.vieFuture.get(index);
    }

    public Carte getCarteVieFutureRandom(){
        int index = (int) (Math.random() * this.vieFuture.size());
        return this.vieFuture.get(index);
    }

    public void defausserOeuvreChoisit(Carte carte){
        this.defausse.add(carte);
        this.Oeuvres.remove(carte);
    }
    

}
