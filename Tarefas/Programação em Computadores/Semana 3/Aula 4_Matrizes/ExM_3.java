import java.util.Scanner;

public class ExM_3 {

    public static void main(String[] args) {
        float a = 0, b = 0;
        float[][] A = new float[3][6];
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.println("Digite um valor para: \n" + "Linha: " + (i + 1) + " Coluna: " + (j + 1));
                A[i][j] = sc.nextFloat();
                if (j % 2 == 0) {
                    a += A[i][j];
                }
                if ((j == 1) || (j == 3)) {
                    b += A[i][j];
                }
            }
        }

        System.out.printf("A soma dos elementos das colunas impáres é de: %.2f\n", a);
        System.out.printf("A soma dos elementos das colunas 2 e 4 é de: %.2f\n", b);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 6; j++) {
                if (j == 5) {
                    A[i][j] = (A[i][0] + A[i][1]);
                }
                System.out.printf("%.2f | ", A[i][j]);
            }
            System.out.println("");
        }

        sc.close();
    }
}