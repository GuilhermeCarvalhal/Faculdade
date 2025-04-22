package modelos;

public class Estudante extends Pessoa {
    private String matricula;
    private double nota01;
    private double nota02;

    public Estudante(String nome, String cpf, String matricula, double nota01, double nota02) {
        super(nome, cpf);
        this.matricula = matricula;
        this.nota01 = nota01;
        this.nota02 = nota02;
    }

    // Método para formatar o estudante em formato CSV
    public String getEstudanteCSV() {
        return String.join(";", getNome(), getCpf(), matricula, Double.toString(nota01), Double.toString(nota02));
    }

    // Método para configurar os dados do estudante a partir de uma string CSV
    public void setEstudanteCSV(String linha) {
        String[] dados = linha.split(";");
        if (dados.length == 5) {
            setNome(dados[0]);
            setCpf(dados[1]);
            matricula = dados[2];
            nota01 = Double.parseDouble(dados[3]);
            nota02 = Double.parseDouble(dados[4]);
        } else {
            throw new IllegalArgumentException("Formato inválido para linha CSV de estudante.");
        }
    }

    // Getter para matrícula, nota01 e nota02
    public String getMatricula() {
        return matricula;
    }

    public double getNota01() {
        return nota01;
    }

    public double getNota02() {
        return nota02;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public void setNota01(double nota01) {
        this.nota01 = nota01;
    }

    public void setNota02(double nota02) {
        this.nota02 = nota02;
    }

    // Método para calcular a média do estudante
    public double calcularMedia() {
        return (nota01 + nota02) / 2.0;
    }
}
