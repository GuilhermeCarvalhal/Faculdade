import java.util.Scanner;

public class Ex_1 {
    public static void main(String[] args) {
        
        int A, B, C;

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o valor de A:");

        A = sc.nextInt();

        System.out.println("Digite o valor de B:");

        B = sc.nextInt();

        System.out.println("Digite o valor de C:");

        C = sc.nextInt();

        if ((A + B) < C)
        {
            System.out.printf("%d + %d é menor que %d", A, B, C);
        }

        sc.close();

    }
}