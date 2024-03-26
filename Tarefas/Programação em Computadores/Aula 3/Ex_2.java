import java.util.Scanner;

public class Ex_2 {
    public static void main(String[] args) throws Exception {
        
        String nome, sexo, estado;

        Scanner sc = new Scanner(System.in);

        System.out.println("Qual seu nome?");

        nome = sc.nextLine();

        System.out.println("Você está casado(a)? (Y/N)");

        estado = sc.nextLine();

        System.out.println("Qual seu sexo?");
        System.out.println("'F' para Mulher e 'M' para homem" );

        sexo = sc.nextLine();

        if ((estado.equals("Y")) && (sexo.equals("F")))
        {
            System.out.println("Há quanto tempo você está casada?");

            int Casada = sc.nextInt();

            System.out.printf("Você é uma mulher casada há %d anos", Casada);
        }
        else
        {
            System.out.println("Obrigado!");;
        }

        sc.close();

    }
}