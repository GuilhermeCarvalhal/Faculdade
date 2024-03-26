import java.util.Scanner;
//Escreva um programa que leia três valores inteiros e diferentes e mostre-os em ordem decrescente.
public class Ex_7 {
    public static void main(String[] args) throws Exception {
        
        double peso, altura, imc;

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite a sua altura:");
        altura = sc.nextDouble();

        System.out.println("Digite o seu peso:");
        peso = sc.nextDouble();

        imc = peso / (altura * altura);

        System.out.println("Sua condição é:");


        if (imc < 18.5)
        {
            System.out.println("Abaixo de 18,5 Abaixo do Peso");
        }
        else if(imc >= 18.5 && imc <= 25)
        {
            System.out.println("Entre 18,5 e 25 Peso normal");
        }
        else if(imc >= 25 && imc <= 30)
        {
            System.out.println("Entre 25 e 30 Acima do peso");
        }
        else
        {
            System.out.println("Acima de 30 obeso");
        }



        sc.close();

    }
}