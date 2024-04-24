package modelos;

public class Aves extends Animal {
    private String tipoBico;

    public Aves(String nome, String especie, String familia, int idade, String tipoBico) {
        super(nome, especie, familia, idade);
        this.tipoBico = tipoBico;
    }

    public String voar() {
        return "A ave está voando";
    }

    public String toString() {
        return super.toString() + ", tipoBico = " + this.tipoBico;
    }

}
