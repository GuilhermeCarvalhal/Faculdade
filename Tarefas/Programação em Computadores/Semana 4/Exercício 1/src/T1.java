import modelos.Funcionario;
import java.util.Scanner;

public class T1 {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        Funcionario f1 = new Funcionario();

        f1.setNome("Romário");
        f1.setData("03/02/1998");
        f1.setCPF(783453622);
        f1.setMatricula(165650);
        f1.setSalario(2500);
        int menu = 9;
        do {
            System.out.println("=======================================");
            System.out.println("Digite 1 para mostrar informações gerais.");
            System.out.println("Digite 2 para dar um aumento.");
            System.out.println("Digite 3 para mostrar ganhos e impostos.");
            System.out.println("Digite 0 para sair.");

            menu = sc.nextInt();

            switch (menu) {
                case 1:
                    System.out.println("Nome: " + f1.getNome());
                    System.out.println("Mátricula: " + f1.getMatricula());
                    System.out.println("Salário: " + f1.getSalario());
                    System.out.println("Data de admissão: " + f1.getData());
                    System.out.println("CPF: " + f1.getCPF());
                    break;

                case 2:
                    System.out.println("Digite o aumento: ");
                    double buff = sc.nextDouble();
                    f1.receberAumento(buff);
                    break;

                case 3:
                    System.out.println("Salário: " + f1.getSalario());
                    System.out.println("Ganho bruto anual: " + f1.calcularGanhoBrutoAnual());
                    System.out.println("Ganho liquído mensal: " + f1.calcularGanhoLiquidoMensal());
                    System.out.println("Ganho líquido anual: " + f1.calcularGanhoLiquidoAnual());
                    System.out.println("Impostos anuais: " + f1.calcularImposto());
                case 0:
                    break;
            }

        } while (menu != 0);
        sc.close();
    }
}
