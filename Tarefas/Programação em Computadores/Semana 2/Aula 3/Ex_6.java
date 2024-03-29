import java.util.Scanner;
//Escreva um programa que leia três valores inteiros e diferentes e mostre-os em ordem decrescente.
public class Ex_6 {
    public static void main(String[] args) {
        
        int[] A;
        A = new int[3];

        Scanner sc = new Scanner(System.in);

        do{
            for (int i = 0; i < 3; i++)
            {  
            
                System.out.printf("Digite o valor para a posição %d\n", i+1);
                System.out.println("OBS: AS POSIÇÕES NÃO PODEM TER VALORES IGUAIS");
                A[i] = sc.nextInt();
           
            }
        } while ((A[0] == A[1]) || (A[0] == A[2]) || (A[1] == A[2]));

        int swapcounter = 0;

        do{
            swapcounter = 0;
            for (int i = 0; i < 2; i++)
            {
                
                if(A[i] < A[i+1])
                {
                    int buffer;
                    buffer = A[i];
                    A[i] = A[i+1];
                    A[i+1] = buffer;

                    swapcounter++;
                }
            }
            
        } while(swapcounter != 0);

        for (int i = 0; i < 3; i++)
        {
            System.out.printf("%d\n", A[i]);
        }

        sc.close();

    }
}