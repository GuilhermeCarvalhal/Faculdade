package modelos;

public class Animal {

    private String nome;
    private String especie;
    private String familia;
    private int idade;

    public Animal(String nome, String especie, String familia, int idade) {
        this.nome = nome;
        this.especie = especie;
        this.familia = familia;
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia = familia;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String alimentar() {
        return "alimentou na classe " + getClass().getSimpleName();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " nome = " + this.nome + ", especie = " + this.especie + ", família = "
                + this.familia + ", idade = " + this.idade;
    }

}
