import java.util.Scanner;

public class ATV2 {
    public static void main(String[] args) {

        int[] A = new int[10];
        Scanner sc = new Scanner(System.in);
        int count = 0;

        for (int i = 0; i < 10; i++) {
            System.out.println("Digite um valor para a posição: " + (i + 1));
            A[i] = sc.nextInt();

            do {
                count = 0;
                for (int b = 0; b < i; b++) {
                    if (A[i] == A[b]) {
                        System.out.println("Valor repetido encontrado, tente novamente: ");
                        A[i] = sc.nextInt();
                        count++;
                    }
                }
            } while (count != 0);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(A[i]);
        }
        sc.close();
    }
}