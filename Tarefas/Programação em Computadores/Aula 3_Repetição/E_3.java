import java.util.Scanner;
//) Elabore um programa que leia um número N e imprimir todos os números inteiros de 1 até N. Considere
//que o N será sempre maior que UM)
public class E_3 {
    public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
   
    int N = 0;
    while (N <= 1)
    {
        System.out.println("Digite um número inteiro");
        N = sc.nextInt();
    }

    for (int i = 1; i <= N; i++)
    {
        System.out.printf("%d\n", i);
    }
    sc.close();

    }    
}