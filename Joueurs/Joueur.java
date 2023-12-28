package Joueurs;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import Cartes.Carte;
import Core.Affichage;
import Core.StrategieJeu;
import Core.Partie;

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
        this.positionEchelleKarmique = "Boursier";
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
        rival.main.add(carte);
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
        return this.Oeuvres.get(this.Oeuvres.size()-1);    
    }

    public void defausserCartePile() {
        this.defausse.add(this.pile.get(0));
        this.pile.remove(0);
    }

    public ArrayList<Carte> getCartesFosse(int nbCartes) {
        ArrayList<Carte> temp = new ArrayList<>();
        for (int i = 0; i < nbCartes; i++) {
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
