import java.util.Scanner;

public class Ex_4 {
    public static void main(String[] args) {

        Boolean A, B;

        Scanner sc = new Scanner(System.in);

        System.out.println("A é 'true' ou 'false'?");

        A = sc.nextBoolean();

        System.out.println("B é 'true' ou 'false'?");

        B = sc.nextBoolean();

        if (A && B) {
            System.out.println("A e B são AMBOS verdadeiros");
        } else if ((A == false) && (B == false)) {
            System.out.println("A e B são AMBOS falsos");
        } else {
            System.out.println("A e B são opostos");
        }

        sc.close();

    }
}