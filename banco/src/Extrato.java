import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Extrato {
    private LocalDateTime dataMovimento;
    private double valor;
    private String tipoTransacao;
    private double saldoAcumulado;

    public Extrato(LocalDateTime now, double valor, String tipoTransacao, double saldo) {
        this.dataMovimento = LocalDateTime.now();
        this.valor = valor;
        this.tipoTransacao = tipoTransacao;
        this.saldoAcumulado = saldo;

    }

    public LocalDateTime getDataMovimento() {

        return dataMovimento;
    }

    public double getValor() {

        return valor;
    }

    public String getTipoTransacao() {

        return tipoTransacao;
    }

    public double getSaldoAcumulado() {

        return saldoAcumulado;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dataFormatada = getDataMovimento().format(formatter);
        return String.format(
                "Data/hora: %s - Valor: R$ %.2f - Transação: %s - Saldo: R$ %.2f",
                dataFormatada, valor, tipoTransacao, saldoAcumulado

        );

    }
}

