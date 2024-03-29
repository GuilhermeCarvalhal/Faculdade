import java.util.Scanner;

public class ExM_2 {
    public static void main(String[] args) {
        float[][] A = new float[10][3];
        int m1 = 0, m2 = 0, m3 = 0, menor;
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < 10; i++) {
            menor = 1;
            for (int j = 0; j < 3; j++) {
                System.out.println("Insira a nota referente à: \n" + "Aluno: " + (i + 1) + " Prova: " + (j + 1));
                A[i][j] = sc.nextFloat();
            }
            for (int j = 1; j < 3; j++) {
                if (A[i][j] < A[i][j - 1]) {
                    menor = (j + 1);
                    break;
                }

            }
            System.out.printf("Menor: %d\n", menor);
            switch (menor) {
                case 1:
                    m1++;
                    break;
                case 2:
                    m2++;
                    break;
                case 3:
                    m3++;
                    break;
            }
        }

        System.out.println("Número de alunos que foram pior na prova 1: " + m1);
        System.out.println("Número de alunos que foram pior na prova 2: " + m2);
        System.out.println("Número de alunos que foram pior na prova 3: " + m3);

        sc.close();
    }
}