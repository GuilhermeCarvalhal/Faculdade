import java.util.Scanner;

public class Ex_8 {
    public static void main(String[] args) {
        
        double pre, resultado = 0.0;

        Scanner sc = new Scanner(System.in);

        System.out.println("Digite o valor do produto:");
        pre = sc.nextDouble();

        System.out.println("Digite o número equivalente a sua forma de pagameto:");
        System.out.println("1. À vista em dinheiro ou cheque, recebe 10% de desconto\r\n" + //
                        "2. À vista no cartão de crédito, recebe 15% de desconto\r\n" + //
                        "3. Em duas vezes, preço normal de etiqueta sem juros\r\n" + //
                        "4. Em duas vezes, preço normal de etiqueta mais juros de 10%");
        int pagamento = sc.nextInt();
        int parcelas = 1;

        switch (pagamento)
        {
            case 1:
                resultado = pre - (pre * 0.10);
                break;
            case 2:
                resultado = pre - (pre * 0.15);
                break;
            case 3:
                resultado = pre / 2;
                parcelas++;
                break;
            case 4:
                resultado = (pre + (pre * 0.10)) / 2;
                parcelas++; 
        }

        System.out.println("Total a se pagar:");
        System.out.printf("%d parcela(s) de R$ %.2f", parcelas, resultado);
        sc.close();

    }
}