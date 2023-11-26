public class Duperie extends Carte {
    public Duperie() {
        super("Duperie","bleu", 3, "Regardez 3 cartes de la Main d’un rival ; ajoutez-en une à votre Main.", false);
    }

    @Override
    public void action(Joueur joueur, Joueur adversaire) {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
}
