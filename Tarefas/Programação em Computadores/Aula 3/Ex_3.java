import java.util.Scanner;

public class Ex_3 {
    public static void main(String[] args) {

        int A, B, C;

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o valor de A:");

        A = sc.nextInt();

        System.out.println("Digite o valor de B:");

        B = sc.nextInt();

        if (A == B) {
            C = A + B;
            System.out.printf("%d + %d = %d", A, B, C);
        } else {
            C = A * B;
            System.out.printf("%d x %d = %d", A, B, C);
        }

        sc.close();

    }
}