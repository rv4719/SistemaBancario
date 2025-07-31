import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class SistemaBancario {

    static Scanner scanner = new Scanner(System.in);
    static ArrayList<Object> contas = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("Olá! Seja bem-vindo(a) ao Banco R!");
        System.out.println("Em que podemos ajudar hoje?");
        boolean atendimento = true;
        int opcao;

        while (atendimento) {
            System.out.println("\nEscolha uma das opções abaixo: ");
            System.out.println("1 - Cadastrar conta");
            System.out.println("2 - Depositar");
            System.out.println("3 - Sacar");
            System.out.println("4 - Emitir extrato");
            System.out.println("5 - Sair");

            opcao = scanner.nextInt();
            switch (opcao) {
                case 1:
                    Conta conta = cadastrarConta();
                    System.out.println(conta);
                    atendimento = continuarAtendimento();
                    break;
                case 2:
                    ArrayList<Number> dadosCredito = informarDados(opcao);
                    int contaCreditoInformada = dadosCredito.get(0).intValue();
                    double valorCredito = dadosCredito.get(1).doubleValue();
                    depositar(contaCreditoInformada, valorCredito);
                    atendimento = continuarAtendimento();
                    break;
                case 3:
                    ArrayList<Number> dadosDebito = informarDados(opcao);
                    int contaDebitoInformada = dadosDebito.get(0).intValue();
                    double valorDebito = dadosDebito.get(1).doubleValue();
                    sacar(contaDebitoInformada, valorDebito);
                    atendimento = continuarAtendimento();
                    break;
                case 4:
                    emitirExtrato();
                    atendimento = continuarAtendimento();
                    break;
                case 5:
                    atendimento = false;
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente! \n ");

            }
        }
        System.out.println("Atendimento encerrado! ");
    }

    private static Conta cadastrarConta() {
        String cpf = validarCpf();

        System.out.println("Crie uma senha: ");
        String senha = scanner.next();

        int tipo;

        while (true) {
            System.out.println("Informe o tipo de conta:");
            System.out.println("1 - Conta Poupança");
            System.out.println("2 - Conta Corrente");

            try {
                tipo = scanner.nextInt();

                if (tipo == 1) {
                    ContaPoupanca conta = new ContaPoupanca(cpf, senha, TipoConta.POUPANCA);
                    contas.add(conta);
                    return conta;
                } else if (tipo == 2) {
                    ContaCorrente conta = new ContaCorrente(cpf, senha, TipoConta.CORRENTE);
                    contas.add(conta);
                    return conta;
                } else {
                    System.out.println("Opção inválida! Digite 1 ou 2.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida! Digite um número (1 ou 2).");
                scanner.next();
            }
        }
    }


    private static String validarCpf() {
        while (true) {
            System.out.println("Digite seu CPF (somente números com 11 dígitos):");
            String cpf = scanner.next();
            cpf = cpf.replaceAll("[^0-9]", "");

            if (cpf.length() == 11) {
                return cpf;
            }

            System.out.println("CPF inválido! Deve conter exatamente 11 dígitos numéricos. \n");
        }
    }

    private static boolean continuarAtendimento() {

        System.out.println("\nDeseja continuar atendimento? ");
        System.out.println("1 - Sim");
        System.out.println("2 - Não");

        int decisao = scanner.nextInt();

        return decisao == 1;
    }

    private static void emitirExtrato() {
        System.out.println("Informe o número da conta:");
        int numeroContaInformada = scanner.nextInt();

        for (Object contaCadastrada : contas) {
            if (contaCadastrada instanceof Conta conta) {
                if (conta.getNumeroConta() == numeroContaInformada) {
                    conta.emitirExtrato(conta);
                    return;
                }
            }
        }
    }


    private static ArrayList<Number> informarDados(int opcao) {
        ArrayList<Number> dados = new ArrayList<>();
        while (true) {
            int numeroContaInformada = 0;
            double valor = 0.00;
            int confirmacao = 0;

            while (true) {
                try {
                    System.out.println("Informe o número da conta:");
                    numeroContaInformada = scanner.nextInt();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada de dados inválida! Digite somente caracteres numéricos.");
                    scanner.next();
                }
            }

            while (true) {
                try {
                    if (opcao == 2) {
                        System.out.println("Informe o valor do depósito em R$:");
                    } else {
                        System.out.println("Informe o valor do saque em R$:");
                    }
                    valor = scanner.nextDouble();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Entrada de dados inválida! Digite somente caracteres numéricos. Ex.: 10,00.");
                    scanner.next();
                }
            }
            if (opcao == 2) {
                System.out.println("Conta a ser CREDITADA N°: " + numeroContaInformada);
            } else {
                System.out.println("Conta a ser DEBITADA N°: " + numeroContaInformada);
            }
            System.out.println("Valor: R$ " + valor);

            while (true) {
                try {
                    System.out.println("Confirmar? 1 (Sim) ou 2 (Não): ");
                    confirmacao = scanner.nextInt();
                    if (confirmacao == 1 || confirmacao == 2) {
                        break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Opção inválida! Digite 1 ou 2.");
                }

            }
            if (confirmacao == 1) {
                dados.add(0, numeroContaInformada);
                dados.add(1, valor);
                return dados;
            } else {
                System.out.println("Operação cancelada. Tente novamente.");
            }
        }
    }


    private static void depositar(int contaCreditoInformada, double valorCredito) {
        for (Object contaCadastrada : contas) {
            if (contaCadastrada instanceof Conta conta) {
                if (conta.getNumeroConta() == contaCreditoInformada) {
                    conta.creditarConta(contaCreditoInformada, valorCredito);
                    return;
                }
            }
        }
        System.out.println("Conta informada não localizada!");
    }


    private static void sacar(int contaDebitoInformada, double valorDebito) {
        System.out.println("Digite a senha cadastrada: ");
        String senhaInformada = scanner.next();


        for (Object contaCadastrada : contas) {
            if (contaCadastrada instanceof Conta conta) {
                if (conta.getNumeroConta() == contaDebitoInformada) {
                    conta.debitarConta(contaDebitoInformada, senhaInformada, valorDebito);
                    return;
                }
            }
        }
        System.out.println("Conta informada não localizada!");
    }

}


