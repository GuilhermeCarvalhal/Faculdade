package modelos;

public class Mamifero extends Animal {
    private String tipoPelagem;

    public Mamifero(String nome, String especie, String familia, int idade, String tipoPelagem) {
        super(nome, especie, familia, idade);
        this.tipoPelagem = tipoPelagem;
    }

    public String toString() {
        return super.toString() + ", tipoPelagem = " + this.tipoPelagem;
    }

}
