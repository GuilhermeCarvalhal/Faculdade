package modelos;

public class ContaInvestimento extends Conta {

    public ContaInvestimento(String titular, String numeroConta, String numeroAgencia, float saldo, String status,
            float percentual) {
        super(titular, numeroConta, numeroAgencia, saldo, status, percentual);
    }

    @Override
    public void atualizar() {
        this.saldo += this.saldo * 1.01f;
    }

}
