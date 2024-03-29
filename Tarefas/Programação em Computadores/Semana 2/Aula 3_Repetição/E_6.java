// Elabore um programa para ler 10 números. Todos os números lidos com valor inferior a 40 devem
//ser somados. Escreva o valor final da soma efetuada.
import java.util.Scanner;

public class E_6 {
    public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int n[];
    n = new int[10];
    int resultado = 0;

    for (int i = 0; i < 10; i++)
    {
        System.out.printf("Digite um valor para a posição %d:\n", i+1);
        n[i] = sc.nextInt();
        
        if (n[i] < 40)
        {
            resultado += n[i];
        }
        
    }
        System.out.printf("A soma dos números com o valor inferior a 40 é: %d", resultado);

    sc.close();
    }
}