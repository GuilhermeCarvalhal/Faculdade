package modelos;

public class Funcionario {

    protected int id;
    protected String name;
    protected double salario;

    public Funcionario(int id, String name, double salario) {
        this.id = id;
        this.name = name;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getSalario() {
        System.out.printf("Salário %.2f\n", this.salario);
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public void aumentarSalario(float percentual) {
        this.salario += this.salario * (percentual / 100);
    }

}
