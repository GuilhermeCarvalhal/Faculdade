package modelos;

public class Conta {

    protected String titular;
    protected String numeroConta;
    protected String numeroAgencia;
    protected float saldo;
    protected String status;
    protected float percentual;

    public Conta(String titular, String numeroConta, String numeroAgencia, float saldo, String status,
            float percentual) {
        this.titular = titular;
        this.numeroConta = numeroConta;
        this.numeroAgencia = numeroAgencia;
        this.saldo = saldo;
        this.status = status;
        this.percentual = percentual;
    }

    public void Saque(float saque) {

        this.saldo -= saque;
        System.out.println("Você executou um saque de R$ " + saque);
        atualizar();
        this.AlteraStatus();
    }

    public void AlteraStatus() {
        if (this.saldo < 0) {
            this.status = "negativo";
        } else {
            this.status = "positivo";
        }
    }

    public void depósito(float deposito) {

        this.saldo += deposito;
        System.out.println("Você executou um depósito de R$ " + deposito);
        atualizar();
        this.AlteraStatus();
    }

    public void atualizar() {
        this.saldo = this.saldo * this.percentual;
        System.out.println("Saldo atualizado");
    }

    public void transferePara(Conta destino, float valor) {
        this.saldo -= valor;
        destino.saldo += valor;
        atualizar();
        destino.atualizar();
        this.AlteraStatus();
        destino.AlteraStatus();
    }

    @Override
    public String toString() {
        return "Conta [titular=" + titular + ", numeroConta=" + numeroConta + ", numeroAgencia=" + numeroAgencia
                + ", saldo=" + saldo + ", status=" + status + ", percentual=" + percentual + "]";
    }

}
