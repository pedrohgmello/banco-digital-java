import pedrohgmello.com.github.domain.*;

import java.math.BigDecimal;

public class Main {

    public static void main(String[] args) {
        System.out.println("--- INICIANDO SIMULAÇÃO DO BANCO DIGITAL ---");

        // 1. CRIANDO O BANCO E OS CLIENTES
        System.out.println("\n[PASSO 1: CRIAÇÃO DE CLIENTES E CONTAS]");
        Banco meuBanco = new Banco("Meu Banco Digital", new BigDecimal("0.05"));
        Cliente clienteAna = new Cliente("Ana Souza", "123.456.789-00");
        Cliente clienteBruno = new Cliente("Bruno Costa", "987.654.321-99");

        // 2. CRIANDO AS CONTAS E ADICIONANDO AO BANCO
        // A Ana terá uma Conta Corrente com R$1000 de saldo e R$500 de cheque especial.
        Conta ccAna = new ContaCorrente(clienteAna, "1001-2", new BigDecimal("1000.00"), new BigDecimal("500.00"));

        // O Bruno terá uma Conta Poupança com R$2000 de saldo.
        Conta cpBruno = new ContaPoupanca(clienteBruno, "2002-3", new BigDecimal("2000.00"));

        meuBanco.cadastrarConta(ccAna);
        meuBanco.cadastrarConta(cpBruno);

        System.out.println("\n--- ESTADO INICIAL DAS CONTAS ---");
        meuBanco.listarTodasAsContas();

        // 3. REALIZANDO OPERAÇÕES BANCÁRIAS
        System.out.println("\n[PASSO 2: REALIZANDO OPERAÇÕES]");

        // Teste de Depósito
        System.out.println("\n-> Testando Depósito na conta do Bruno...");
        meuBanco.realizarDeposito("2002-3", new BigDecimal("300.00"));

        // Teste de Saque Simples
        System.out.println("\n-> Testando Saque Simples na conta da Ana...");
        meuBanco.realizarSaque("1001-2", new BigDecimal("200.00"));

        // Teste de Saque usando o Cheque Especial
        System.out.println("\n-> Testando Saque que utiliza o Cheque Especial na conta da Ana...");
        meuBanco.realizarSaque("1001-2", new BigDecimal("1000.00")); // Saldo era 800, vai ficar -200

        // Teste de Saque que falha (saldo + limite insuficientes)
        System.out.println("\n-> Testando Saque que deve falhar na conta da Ana...");
        meuBanco.realizarSaque("1001-2", new BigDecimal("400.00")); // Saldo é -200, limite 500. Disponível 300.

        // Teste de Transferência
        System.out.println("\n-> Testando Transferência da conta do Bruno para a da Ana...");
        // Isso vai "cobrir" o cheque especial da Ana e deixar o saldo dela positivo.
        meuBanco.realizarTransferencia("2002-3", "1001-2", new BigDecimal("500.00"));

        System.out.println("\n--- ESTADO DAS CONTAS APÓS OPERAÇÕES ---");
        meuBanco.listarTodasAsContas();

        // 4. PROCESSOS ADMINISTRATIVOS DO BANCO
        System.out.println("\n[PASSO 3: PROCESSOS ADMINISTRATIVOS MENSAIS]");
        System.out.println("\n-> Aplicando rendimento mensal da poupança...");
        meuBanco.aplicarRendimentoMensal(); // Supondo uma taxa interna no método

        System.out.println("\n--- ESTADO FINAL DAS CONTAS ---");
        meuBanco.listarTodasAsContas();
    }
}