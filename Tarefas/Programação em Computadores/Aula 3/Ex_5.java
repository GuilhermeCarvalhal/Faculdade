import java.util.Scanner;

public class Ex_5 {
    public static void main(String[] args) throws Exception {
        
        int A;

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o valor de A:");

        A = sc.nextInt();

        if (A % 2 == 0)
        {
            A *= 5;
        }
        else
        {
            A *= 7;
        }

        System.out.printf("O valor final de A é %d", A);;

        sc.close();

    }
}