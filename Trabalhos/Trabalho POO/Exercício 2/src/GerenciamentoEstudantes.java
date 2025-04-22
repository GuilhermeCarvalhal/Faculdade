import modelos.*;

import java.util.Scanner;

public class GerenciamentoEstudantes {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Disciplina disciplina = new Disciplina();

    public static void main(String[] args) {
        disciplina.carregaDados(); // Carrega os dados dos estudantes do arquivo ao iniciar o programa

        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer do scanner

            switch (opcao) {
                case 1:
                    cadastrarEstudante();
                    break;
                case 2:
                    alterarEstudante();
                    break;
                case 3:
                    removerEstudante();
                    break;
                case 4:
                    consultarEstudante();
                    break;
                case 5:
                    disciplina.listarEstudantes();
                    break;
                case 6:
                    disciplina.listarEstudantesAbaixoDeSeis();
                    break;
                case 7:
                    disciplina.listarEstudantesAcimaDeSeis();
                    break;
                case 8:
                    mostrarMediaTurma();
                    break;
                case 9:
                    sair();
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
            }
        } while (opcao != 9);
    }

    // Método para exibir o menu de opções
    private static void exibirMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Cadastrar um novo estudante");
        System.out.println("2. Alterar os dados de um estudante");
        System.out.println("3. Remover um estudante");
        System.out.println("4. Consultar um estudante");
        System.out.println("5. Listar todos os estudantes");
        System.out.println("6. Listar estudantes com média abaixo de 6.0");
        System.out.println("7. Listar estudantes com média acima de 6.0");
        System.out.println("8. Mostrar a média da turma");
        System.out.println("9. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // Método para cadastrar um novo estudante
    private static void cadastrarEstudante() {
        System.out.println("\n=== Cadastro de Novo Estudante ===");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        String matricula;
        do {
            System.out.print("Matrícula: ");
            matricula = scanner.nextLine();
        } while (matricula.equals(null));

        System.out.print("Nota 01: ");
        double nota01 = scanner.nextDouble();
        System.out.print("Nota 02: ");
        double nota02 = scanner.nextDouble();

        // Criar um novo estudante e adicionar à disciplina
        Estudante novoEstudante = new Estudante(nome, cpf, matricula, nota01, nota02);
        disciplina.adicionarEstudante(novoEstudante);

        System.out.println("Estudante cadastrado com sucesso!\n");
    }

    // Método para alterar os dados de um estudante
    private static void alterarEstudante() {
        System.out.println("\n=== Alteração de Dados de Estudante ===");
        System.out.print("Digite a matrícula do estudante: ");
        String matricula = scanner.nextLine();

        Estudante estudante = disciplina.buscarEstudante(matricula);
        if (estudante != null) {
            System.out.println("Dados atuais do estudante:");
            exibirDadosEstudante(estudante);

            System.out.println("Digite os novos dados:");
            System.out.print("Nome: ");
            estudante.setNome(scanner.nextLine());
            System.out.print("CPF: ");
            estudante.setCpf(scanner.nextLine());
            System.out.print("Matrícula: ");
            estudante.setMatricula(scanner.nextLine());
            System.out.print("Nota 01: ");
            estudante.setNota01(scanner.nextDouble());
            System.out.print("Nota 02: ");
            estudante.setNota02(scanner.nextDouble());

            System.out.println("Dados do estudante atualizados com sucesso!\n");
        } else {
            System.out.println("Estudante não encontrado.\n");
        }
    }

    // Método para remover um estudante
    private static void removerEstudante() {
        System.out.println("\n=== Remoção de Estudante ===");
        System.out.print("Digite a matrícula do estudante que deseja remover: ");
        String matricula = scanner.nextLine();

        Estudante estudante = disciplina.buscarEstudante(matricula);
        if (estudante != null) {
            disciplina.removerEstudante(matricula);
            System.out.println("Estudante removido com sucesso.\n");
        } else {
            System.out.println("Estudante não encontrado.\n");
        }
    }

    // Método para consultar um estudante
    private static void consultarEstudante() {
        System.out.println("\n=== Consulta de Estudante ===");
        System.out.print("Digite a matrícula do estudante que deseja consultar: ");
        String matricula = scanner.nextLine();

        Estudante estudante = disciplina.buscarEstudante(matricula);
        if (estudante != null) {
            exibirDadosEstudante(estudante);
        } else {
            System.out.println("Estudante não encontrado.\n");
        }
    }

    // Método para exibir os dados de um estudante
    private static void exibirDadosEstudante(Estudante estudante) {
        System.out.println("Nome: " + estudante.getNome());
        System.out.println("CPF: " + estudante.getCpf());
        System.out.println("Matrícula: " + estudante.getMatricula());
        System.out.println("Nota 01: " + estudante.getNota01());
        System.out.println("Nota 02: " + estudante.getNota02());
        System.out.println("Média: " + estudante.calcularMedia());
        System.out.println("-----------------------------");
    }

    // Método para listar estudantes com média abaixo de 6.0
    private static void listarEstudantesAbaixoDeSeis() {
        System.out.println("\n=== Estudantes com Média Abaixo de 6.0 ===");
        disciplina.listarEstudantesAbaixoDeSeis();
    }

    // Método para listar estudantes com média acima de 6.0
    private static void listarEstudantesAcimaDeSeis() {
        System.out.println("\n=== Estudantes com Média Acima ou Igual a 6.0 ===");
        disciplina.listarEstudantesAcimaDeSeis();
    }

    // Método para mostrar a média da turma
    private static void mostrarMediaTurma() {
        double mediaTurma = disciplina.calcularMediaTurma();
        System.out.println("\n=== Média da Turma ===");
        System.out.println("Média da Turma: " + mediaTurma + "\n");
    }

    // Método para encerrar o programa
    private static void sair() {
        disciplina.gravar(); // Grava os dados dos estudantes no arquivo antes de sair
        System.out.println("Encerrando o programa. Até logo!");
    }
}
