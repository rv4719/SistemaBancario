import java.time.LocalDateTime;
import java.util.ArrayList;

public class ContaCorrente extends Conta{

    private double limite = 1000.00;
    private double saldoDisponivel = getSaldo() + getLimite();
    private ArrayList<Extrato> extrato;

    public ContaCorrente(String cpf, String senha, TipoConta tipoConta) {
        super(cpf, senha, tipoConta);
        this.limite = getLimite();
        this.extrato = new ArrayList<>();
        this.saldoDisponivel = getSaldo() + getLimite();
    }


    public double getLimite() {
        return limite;
    }

    public double getSaldoDisponivel() {
        return saldoDisponivel;
    }

    @Override
    public ArrayList<Extrato> getExtratos() {
        return extrato;
    }

    @Override
    public void creditarConta(int numeroContaInformada, double valor) {
        if(getNumeroConta() == numeroContaInformada){

            if(valor > 0){
                this.saldoDisponivel += valor;
                Extrato extrato = new Extrato(LocalDateTime.now(), valor, "CRÉDITO", getSaldoDisponivel());
                getExtratos().add(extrato);
                System.out.printf("Valor creditado: R$ %.2f.%n", valor);
                System.out.printf("Novo saldo disponível (saldo + limite cheque especial): R$ %.2f.%n%n", getSaldoDisponivel());
                ordenarExtrato();
            } else {
                System.out.println("Valor informado inválido!\n\n");
            }
        } else {
            System.out.println("Conta não localizada!");
        }
    }

    @Override
    public void debitarConta(int numeroContaInformada, String senhaInformada, double valor) {
        if(getNumeroConta() == numeroContaInformada){

            if(this.senha.equals(senhaInformada)){
                if(valor <= 0){
                    System.out.println("Valor inválido! Igual ou menor que zero.\n\n");
                } else if (valor > getSaldoDisponivel() ) {
                    System.out.println("Valor solicitado excede o saldo disponível! Saldo disponível(saldo + limite cheque especial: R$ \n\n" + getSaldoDisponivel());
                } else {
                    this.saldoDisponivel -= valor;
                    Extrato extrato = new Extrato(LocalDateTime.now(), -valor, "DÉBITO", getSaldoDisponivel());
                    getExtratos().add(extrato);
                    System.out.printf("Valor debitado: R$ %.2f.%n", valor);
                    System.out.printf("Novo saldo disponível: R$ %.2f.%n%n", getSaldoDisponivel());
                    ordenarExtrato();
                }
            } else {
                System.out.println("Senha incorreta!");
            }
        } else {
            System.out.println("Conta não localizada!");
        }
    }

    @Override
    public String toString() {
        return "CPF ='" + getCpf() + '\'' +
                ", CONTA CORRENTE N° = " + getNumeroConta() +
                ", Limite Cheque Especial Pré-Aprovado = " + getLimite() +
                ", Saldo Disponível = " + getSaldoDisponivel();
    }
}
