public class ATV1_4 {

    public static void main(String[] args) {

        int[] A = { 1, 0, 5, -2, -5, 7 };
        int B = A[0] + A[1] + A[5];

        A[4] = 100;

        System.out.println("A soma de " + A[0] + " + " + A[1] + " + " + A[5] + " = " + B);

        for (int i = 0; i < 6; i++) {
            System.out.println(A[i]);
        }

    }
}