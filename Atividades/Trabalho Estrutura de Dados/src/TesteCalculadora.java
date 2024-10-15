import java.util.Scanner;

import modelos.*;

public class TesteCalculadora {
    public static void main(String[] args) {
        // Testando PilhaGenerica com Integer
       Calculadora calc = new Calculadora();

        Scanner sc = new Scanner(System.in);
        int menu = -1;

        while(menu != 0) {
            System.out.println("========================");           //Digite 5 para empilhar automaticamente.

            System.out.println("Bem vindo a calculadora!");
            System.out.println();
    
            System.out.println("Digite o valor correspondente a opção desejada.");
            System.out.println("1 - Empilhar operadores");
            System.out.println("2 - Empilhar valores");
            System.out.println("3 - Fazer cálculo");
            System.out.println("4 - Imprimir pilhas");
            System.out.println("0 - Sair da calculadora.");
            System.out.println();

            
            menu = sc.nextInt();

            switch (menu) {
                case 1:
                    System.out.println("Digite qual dos operadores válidos você deseja empilhar.");
                    System.out.println("Operadores válidos: +, -, /, *");
                    sc.nextLine();
                    String buffer = sc.nextLine();
                    Character op = buffer.charAt(0);
                    
                    calc.empilhaOperador(op);
                    break;
                case 2:
                    System.out.println("Digite um número real para ser empilhado:");
                    float val = sc.nextFloat();
                    calc.empilhaReal(val);
                    break;

                case 3:
                    System.out.println("Verificando dados...");
                    calc.realizarCalculo();
                    System.out.println();
                    break;
                
                case 4:
                    System.out.println("Imprimindo pilhas...");
                    calc.imprimePilhaOperadores();
                    System.out.println();
                    calc.imprimePilhaReal();
                    System.out.println();
                    
                    break;
                case 5:
                    System.out.println("Empilhamento de teste injetado!");
                    calc.teste();
                    
                    break;
                case 0:
                    System.out.println("Saindo...");

                    break;
                default:
                    break;
            }
            
        }
        sc.close();

        
    }
}
