import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

public abstract class Conta{
    private static int proximoNumero = 1;
    private String cpf;
    private int numeroConta;
    private TipoConta tipo;
    protected String senha;
    private double saldo = 0.00;
    private String tipoTransacao;
    private LocalDateTime dataMovimento;
    private ArrayList<Extrato> extratos;


    public Conta(){

    }

    public Conta(String cpf, String senha, TipoConta tipoConta) {
        this.extratos = new ArrayList<>();
        this.cpf = cpf;
        this.senha = senha;
        this.numeroConta = proximoNumero++;
        this.saldo = getSaldo();
        this.tipo = tipoConta;

    }


    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getNumeroConta() {
        return numeroConta;
    }

    public TipoConta getTipo() {
        return tipo;
    }

    public void setTipo(TipoConta tipo) {
        this.tipo = tipo;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }


    public ArrayList<Extrato> getExtratos() {
        return extratos;
    }

    @Override
    public String toString() {

        return "CPF ='" + cpf + '\'' +
                ", Conta POUPANÇA N° = " + numeroConta +
                ", Saldo Inicial = " + saldo ;
    }

    public boolean validarSenha(String senhaFornecida) {
        if(this.senha.equals(senhaFornecida)){
            System.out.println(toString());
        } else {
            System.out.println("Senha incorreta!\n\n");
        }
        return this.senha.equals(senhaFornecida);
    }



    public void creditarConta(int numeroContaInformada, double valor){

            if(numeroConta == numeroContaInformada){

                if(valor > 0){
                    this.saldo += valor;
                    Extrato extrato = new Extrato(LocalDateTime.now(), valor, "CRÉDITO", getSaldo());
                    extratos.add(extrato);
                    System.out.printf("Valor creditado R$ %.2f. Novo saldo: R$ %.2f.%n%n", valor, getSaldo());
                    ordenarExtrato();
                } else {
                    System.out.println("Valor informado inválido!\n\n");
                }
            } else {
                System.out.println("Conta não localizada!");
            }
    }

    public void debitarConta(int numeroContaInformada, String senhaInformada, double valor) {
        if (numeroConta == numeroContaInformada && this.senha.equals(senhaInformada)) {
            if (valor <= 0) {
                System.out.println("Valor inválido! Igual ou menor que zero.\n\n");
            } else if (valor > getSaldo()) {
                System.out.println("Valor solicitado excede o saldo disponível!\n\n");
            } else {
                this.saldo -= valor;
                Extrato extrato = new Extrato(LocalDateTime.now(), valor, "CRÉDITO", getSaldo());
                extratos.add(extrato);
                System.out.printf("Valor debitado: R$ %.2f. Novo saldo: R$ %.2f.%n%n", valor, getSaldo());

                ordenarExtrato();
            }
        } else {
            System.out.println("Conta não localizada ou senha incorreta!");
        }
    }


    public void emitirExtrato(Conta conta){

        ordenarExtrato();
    }

    public void ordenarExtrato() {
        System.out.printf("\n--- EXTRATO CONTA %s N° %d ---\n", getTipo(), getNumeroConta());
        ArrayList<Extrato> extratosOrdenados = new ArrayList<>(getExtratos());
        extratosOrdenados.sort(Comparator.comparing(Extrato::getDataMovimento).reversed());
        extratosOrdenados.forEach(System.out::println);
    }


}
