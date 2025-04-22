package modelos;

import java.io.*;
import java.util.ArrayList;

public class Disciplina {
    private ArrayList<Estudante> turma;

    public Disciplina() {
        this.turma = new ArrayList<>();
    }

    // Método para adicionar um novo estudante à turma
    public void adicionarEstudante(Estudante estudante) {
        turma.add(estudante);
    }

    // Método para remover um estudante da turma, dado o número de matrícula
    public void removerEstudante(String matricula) {
        turma.removeIf(estudante -> estudante.getMatricula().equals(matricula));
    }

    // Método para buscar um estudante na turma, dado o número de matrícula
    public Estudante buscarEstudante(String matricula) {
        for (Estudante estudante : turma) {
            if (estudante.getMatricula().equals(matricula)) {
                return estudante;
            }
        }
        return null;
    }

    // Método para listar todos os estudantes da turma
    public void listarEstudantes() {
        for (Estudante estudante : turma) {
            System.out.println("Nome: " + estudante.getNome());
            System.out.println("Matrícula: " + estudante.getMatricula());
            System.out.println("Nota 01: " + estudante.getNota01());
            System.out.println("Nota 02: " + estudante.getNota02());
            System.out.println("Média: " + estudante.calcularMedia());
            System.out.println("-----------------------------");
        }
    }

    // Método para listar estudantes com média abaixo de 6.0
    public void listarEstudantesAbaixoDeSeis() {
        for (Estudante estudante : turma) {
            if (estudante.calcularMedia() < 6.0) {
                System.out.println("Nome: " + estudante.getNome());
                System.out.println("Matrícula: " + estudante.getMatricula());
                System.out.println("Nota 01: " + estudante.getNota01());
                System.out.println("Nota 02: " + estudante.getNota02());
                System.out.println("Média: " + estudante.calcularMedia());
                System.out.println("-----------------------------");
            }
        }
    }

    // Método para listar estudantes com média acima de 6.0
    public void listarEstudantesAcimaDeSeis() {
        for (Estudante estudante : turma) {
            if (estudante.calcularMedia() >= 6.0) {
                System.out.println("Nome: " + estudante.getNome());
                System.out.println("Matrícula: " + estudante.getMatricula());
                System.out.println("Nota 01: " + estudante.getNota01());
                System.out.println("Nota 02: " + estudante.getNota02());
                System.out.println("Média: " + estudante.calcularMedia());
                System.out.println("-----------------------------");
            }
        }
    }

    // Método para calcular a média da turma
    public double calcularMediaTurma() {
        if (turma.isEmpty()) {
            return 0.0;
        }

        double somaDasMedias = 0.0;
        for (Estudante estudante : turma) {
            somaDasMedias += estudante.calcularMedia();
        }
        return somaDasMedias / turma.size();
    }

    // Método para carregar dados dos estudantes de um arquivo CSV
    public void carregaDados() {
        try (BufferedReader br = new BufferedReader(new FileReader("estudantes.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                Estudante estudante = new Estudante("", "", "", 0.0, 0.0);
                estudante.setEstudanteCSV(linha);
                turma.add(estudante);
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar dados dos estudantes do arquivo.");
        }
    }

    // Método para gravar os dados dos estudantes em um arquivo CSV
    public void gravar() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("estudantes.csv"))) {
            for (Estudante estudante : turma) {
                bw.write(estudante.getEstudanteCSV());
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Erro ao gravar dados dos estudantes no arquivo.");
        }
    }
}
