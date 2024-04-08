package modelos;

public class Funcionario {

    // Atributos
    private String nome;
    private int matricula;
    private double salario;
    private long CPF;
    private String data;

    public Funcionario() {
    }

    public Funcionario(String nome, int matricula, double salario, long CPF, String data) {
        this.nome = nome;
        this.matricula = matricula;
        this.salario = salario;
        this.CPF = CPF;
        this.data = data;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public long getCPF() {
        return CPF;
    }

    public void setCPF(long CPF) {
        this.CPF = CPF;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void receberAumento(double Aumento) {
        salario += Aumento;
        return;
    }

    public double calcularGanhoBrutoAnual() {
        return salario * 12;
    }

    public double calcularImposto() {
        double temp;
        temp = salario * 0.11;

        if (salario > 2500) {
            temp += (salario - 2500) * (17.5 / 100);

        }

        return temp * 12;
    }

    public double calcularGanhoLiquidoMensal() {
        double imposto;
        imposto = salario * 0.11;

        if (salario > 2500) {
            imposto += (salario - 2500) * (17.5 / 100);

        }

        return (salario - imposto);
    }

    public double calcularGanhoLiquidoAnual() {
        double imposto;
        imposto = salario * 0.11;

        if (salario > 2500) {
            imposto += (salario - 2500) * (17.5 / 100);

        }

        return ((salario - imposto) * 12);
    }
}
