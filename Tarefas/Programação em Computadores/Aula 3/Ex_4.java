import java.util.Scanner;

public class Ex_4 {
    public static void main(String[] args) throws Exception {
        
        Boolean A, B;

        Scanner sc = new Scanner(System.in);

        System.out.println("A é 'true' ou 'false'?");

        A = sc.nextBoolean();

        System.out.println("B é 'true' ou 'false'?");

        B = sc.nextBoolean();

        System.out.println((A && B) ? "A e B são AMBOS verdadeiros" : "A e B são AMBOS falsos");

        sc.close();

    }
}