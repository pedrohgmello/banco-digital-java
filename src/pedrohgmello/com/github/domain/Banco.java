package pedrohgmello.com.github.domain;

import pedrohgmello.com.github.exceptions.ContaInexistenteException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Banco {
    //TODO: Implementar verificação de contas.
    private String nome;
    private Map<String, Conta> contas;
    private BigDecimal tax;

    public Banco(String nome, BigDecimal tax) {
        this.nome = nome;
        this.contas = new HashMap<>();
        this.tax = tax;
    }

    //TODO: implementar cadastrarConta, logarConta e outros métodos
    public void cadastrarConta(Conta conta) {
        this.contas.put(conta.getNumero(), conta);
    }


    /*TODO: Implementar realizarTransferencia (chama o saque de uma e o deposito da outra), realizarSaque e
        realizarDeposito para verificar se a conta existe e outros métodos que o gemini falou
        (para implementar alguns vamos ter que mudar uns métodos da Contas)
    */

    public void realizarDeposito(String numero, BigDecimal valor) {
        if(this.contas.containsKey(numero)) {
            Conta conta = this.contas.get(numero);
            conta.depositar(valor);
        } else{
            throw new ContaInexistenteException("Impossível encontrar conta.");
        }
    }

    public void realizarTransferencia(String numContaOrigem, String numContaDestino, BigDecimal valor) {
        Conta origem = this.contas.get(numContaOrigem);
        Conta destino = this.contas.get(numContaDestino);
        origem.saque(valor);
        destino.depositar(valor);

    }

    public void realizarSaque(String numConta, BigDecimal valor) {
        if(contas.containsKey(numConta)){
            Conta conta = contas.get(numConta);
            if(conta.saque(valor)){
                System.out.println(numConta + " saque realizado com sucesso!");
            } else{
                System.out.println("Impossível realizar saque.");
            }
        } else{
            throw new ContaInexistenteException("Impossível encontrar conta.");
        }
    }

    public void listarTodasAsContas() {
        this.contas.values().forEach(conta -> System.out.println(conta.toString()));
    }

    public void mostrarExtrato(String numConta){
        if(contas.containsKey(numConta)){
            Conta conta = contas.get(numConta);
            conta.getHistorico().stream()
                    .forEach(historico -> System.out.println(historico.toString()));
        } else{
            throw new ContaInexistenteException("Impossível encontrar conta.");
        }
    }

    public void aplicarRendimentoMensal(){
        this.contas.values().stream()
                .filter(conta -> conta instanceof ContaPoupanca)
                .forEach(conta -> ((ContaPoupanca) conta).renderJuros(tax));
        System.out.println("Rendimento mensal aplicado com sucesso!");
    }


}
