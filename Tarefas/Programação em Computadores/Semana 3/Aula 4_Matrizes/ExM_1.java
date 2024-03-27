import java.util.Scanner;

public class ExM_1 {

    public static boolean find(int N[][], int v) {

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (v == N[i][j]) {
                    System.out.printf("O valor de X foi encontrado na linha %d / coluna %d", (i + 1), (j + 1));
                    return false;
                }
            }
        }

        return true;
    }

    public static void main(String[] args) {
        int[][] A = new int[5][5];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.println("Digite um valor para: \n" + "Linha: " + (i + 1) + " Coluna: " + (j + 1));
                A[i][j] = sc.nextInt();

            }
        }

        System.out.println("Insira o valor de X: ");
        int X = sc.nextInt();

        if (find(A, X)) {
            System.out.println("Não encontrado");
        }
        sc.close();
    }
}