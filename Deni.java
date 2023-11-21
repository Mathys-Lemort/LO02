public class Deni extends Carte{    
    public Deni() {
        super("bleu", 2, "Defaussez une carte de votre Main. Copiez le pouvoir de cette carte.", false);
    }

    @Override
    public void action() {
        throw new UnsupportedOperationException("Unimplemented method 'action'");
    }
    
}
