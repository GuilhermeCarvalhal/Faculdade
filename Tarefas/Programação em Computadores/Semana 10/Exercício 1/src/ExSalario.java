import modelos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExSalario {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o valor referente ao limite de funcionários: ");
        int N = sc.nextInt();

        List<Funcionario> lista = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            System.out.println("Funcionário " + (i + 1));

            System.out.println("Digite o Nome do funcionário:");
            String name = sc.nextLine();

            System.out.println("Digite o ID do funcionário:");
            int id = sc.nextInt();

            System.out.println("Digite o Salario do funcionário:");
            double iF = sc.nextDouble();

            boolean repetido = false;
            for (Funcionario funcionario : lista) {
                if (funcionario.getId() == id) {
                    repetido = true;
                    break;
                }

            }

            if (repetido) {
                System.out.println("Id repetido, tente novamente.");
                i--;
            } else {
                Funcionario funcionario = new Funcionario(id, name, iF);
                lista.add(funcionario);
            }
        }

        System.out.println("Digite o ID do funcionário que você deseja aumentar o salário: ");
        int id = sc.nextInt();
        boolean encontrado = false;
        for (Funcionario funcionario : lista) {
            if (funcionario.getId() == id) {
                encontrado = true;
                System.out.println("Digite um valor para o percentual de aumento salarial: ");
                float X = sc.nextFloat();
                funcionario.aumentarSalario(X);
                System.out.println("Aumento de salário efetuado com sucesso");
                break;
            }

        }
        if (!encontrado) {
            System.out.println("O ID não foi encontrado, abortando aumento de salário...");
        }
        for (Funcionario funcionario : lista) {
            System.out.println("Nome " + funcionario.getName());
            System.out.println("ID " + funcionario.getId());
            System.out.println("Sálario: ");
            funcionario.getSalario();
        }
        sc.close();
    }

}
