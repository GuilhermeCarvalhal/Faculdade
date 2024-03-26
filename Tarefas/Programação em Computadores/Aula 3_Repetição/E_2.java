import java.util.Scanner;

public class E_2 {
    public static void main(String[] args) throws Exception {
//Elabore um programa que leia um número inteiro (aceitar somente valores entre 1 e 10) e escrever a
//tabuada de 1 a 10 do número lido
    Scanner sc = new Scanner(System.in);
    int number;

        do
            {
                System.out.println("Digite um número inteiro entre 1 e 10");
                number = sc.nextInt();
            }
        while ((number < 1) || (number > 10));

        for (int i = 1; i <= 10; i++)
        {
            System.out.printf("%d x %d = %d\n", number, i, (number * i));
        }
        sc.close();

    }
}