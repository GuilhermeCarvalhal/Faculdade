import modelos.*;

public class TesteConta {
    public static void main(String[] args) throws Exception {

        ContaCorrente c1 = new ContaCorrente("Gabriel", "8787", "999", 2345, "positivo", 0.005f);
        ContaPoupanca c2 = new ContaPoupanca("Roger", "999", "090", 3412, "positivo", 1.005f);
        ContaInvestimento c3 = new ContaInvestimento("Marcia", "545", "323", 6877, "positivo", 1.01f);

        c1.depósito(1000);
        c2.transferePara(c3, 500);
        c3.Saque(1000);

        c3.transferePara(c1, 5000);

        System.out.println("Saldo da Conta Corrente:");
        System.out.println(c1.toString() + "\n");
        System.out.println("Saldo da Conta Poupança:");
        System.out.println(c2.toString() + "\n");
        System.out.println("Saldo da Conta Investimento:");
        System.out.println(c3.toString());

    }
}
