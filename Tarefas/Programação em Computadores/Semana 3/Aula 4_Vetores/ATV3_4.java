import java.util.Scanner;

public class ATV3_4 {

    public static int leitura(int n[], Scanner sc) {

        for (int i = 0; i < 10; i++) {

            System.out.println("Digite um valor para a posição: " + (i + 1));
            n[i] = sc.nextInt();

            int count = 0;

            do {
                count = 0;
                for (int b = 0; b < i; b++) {
                    if (n[i] == n[b]) {
                        System.out.println("Valor repetido encontrado, tente novamente: ");
                        n[i] = sc.nextInt();
                        count++;
                    }
                }
            } while (count != 0);
        }
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] A = new int[10];
        int[] B = new int[10];
        int[] C = new int[10];

        System.out.println("Vetor A:\n" + "------------------------------");
        leitura(A, sc);
        System.out.println("Vetor B:\n" + "------------------------------");
        leitura(B, sc);
        System.out.println("Vetor C:\n" + "------------------------------");

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (A[i] == B[j]) {
                    C[i] = B[j];
                    System.out.println(C[i]);
                }
            }
        }
        sc.close();
    }
}