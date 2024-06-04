package modelos;

//Classe Imovel:
//Atributos: codigo (int), areaconstruida (float), areatotal(float), numeroquartos (int),
//tipo (int - Casa, Apartamento - ex: 0 – Casa, 1 – Apartamento)
//preco (float)
//localizacao (Endereco)

public class Imovel {

    private int codigo;
    private float areaconstruida;
    private float areatotal;
    private int numeroquartos;
    private int tipo; // tipo (int - Casa, Apartamento - ex: 0 – Casa, 1 – Apartamento)
    private float preco;
    private Endereco localizacao;

    public Imovel(int codigo, float areaconstruida, float areatotal, int numeroquartos, int tipo, float preco,
            Endereco localizacao) {
        this.codigo = codigo;
        this.areaconstruida = areaconstruida;
        this.areatotal = areatotal;
        this.numeroquartos = numeroquartos;
        this.tipo = tipo;
        this.preco = preco;
        this.localizacao = localizacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public float getAreaconstruida() {
        return areaconstruida;
    }

    public void setAreaconstruida(float areaconstruida) {
        this.areaconstruida = areaconstruida;
    }

    public float getAreatotal() {
        return areatotal;
    }

    public void setAreatotal(float areatotal) {
        this.areatotal = areatotal;
    }

    public int getNumeroquartos() {
        return numeroquartos;
    }

    public void setNumeroquartos(int numeroquartos) {
        this.numeroquartos = numeroquartos;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getCidade() {
        return localizacao.getCidade();
    }

    public String getBairro() {
        return localizacao.getBairro();
    }

    public void setLocalizacao(Endereco localizacao) {
        this.localizacao = localizacao;
    }

    public Endereco getLocalizacao() {
        return localizacao;
    }

}
