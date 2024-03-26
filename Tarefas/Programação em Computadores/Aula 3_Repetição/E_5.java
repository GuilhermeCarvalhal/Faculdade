//Elabore um programa para ler 10 números, calcular e escrever a média aritmética desses valores lidos
import java.util.Scanner;

public class E_5 {
    public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    float n[];
    n = new float[10];
    float media = 0;

    for (int i = 0; i < 10; i++)
    {
        System.out.printf("Digite um valor para a posição %d:\n", i+1);
        n[i] = sc.nextInt();
        media += n[i];
    }

    System.out.printf("A média aritmética desses números é: %.2f",(media /= 10));
    sc.close();    
    }
}