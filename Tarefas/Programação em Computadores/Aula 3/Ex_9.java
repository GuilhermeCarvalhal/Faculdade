import java.util.Scanner;
//Escreva um programa que leia três valores inteiros e diferentes e mostre-os em ordem decrescente.
public class Ex_9 {
    public static void main(String[] args) throws Exception {
        
        double[] notas;
        notas = new double[3];
        
        Scanner sc = new Scanner(System.in);

        System.out.println("Olá aluno, digite seu código de verificação:");
        String id = sc.nextLine();

        for (int i = 0; i < 3; i++)
        {  
            System.out.printf("Digite o valor para a nota número %d\n", i+1);
            notas[i] = sc.nextDouble();
        }

        System.out.println("Digite a nota equivalente a'Média de Exercícios'");    
        double me = sc.nextFloat();

        double MA = (notas[0] + notas[1] * 2 + notas[2] * 3 + me) / 7; 

        System.out.printf("Seu id é: %s\n", id);

        for (int i = 0; i < 3; i++)
        {
            System.out.printf("A sua nota da prova %d é: %.0f\n", i+1, notas[i]);
        }
        System.out.printf("A média dos exercícios é: %.1f\n", me);
        System.out.printf("A média de aproveitamento é %.1f\n", MA);

        String Status;
        if (MA >= 90)
        {
            System.out.println("Seu conceito é A");
            Status = "Aprovado";
        }
        else if (MA >= 75 && MA < 90)
        {
            System.out.println("Seu conceito é B");
            Status = "Aprovado";
        }
        else if (MA >= 60 && MA < 75)
        {
            System.out.println("Seu conceito é C");
            Status = "Aprovado";
        }
        else if (MA >= 40 && MA < 60)
        {
            System.out.println("Seu conceito é D");
            Status = "Reprovado";
        }
        else
        {
            System.out.println("Seu conceito é E");
            Status = "Reprovado";
        }

        System.out.printf("%s", Status);

        sc.close();
    }
}