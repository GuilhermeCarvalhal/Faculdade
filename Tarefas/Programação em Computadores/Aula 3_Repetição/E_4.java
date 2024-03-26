import java.util.Scanner;

public class E_4 {
    public static int fatorial(int a){
    int result;
        if (a == 0)
        {
            return 1;
        }
        else
        {
            result = a * fatorial(a - 1);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        System.out.println("Digite um número inteiro:");
        int n = sc.nextInt();

        System.out.println("Digite outro número inteiro:");
        int n2 = sc.nextInt();

        int nf = fatorial(n);
        int n2f = fatorial(n2);

        System.out.printf("O fatorial de %d é: %d\n", n, nf);
        System.out.printf("O fatorial de %d é: %d\n", n2, n2f);

        sc.close();
    }
}
