package pedrohgmello.com.github.domain;

import pedrohgmello.com.github.services.IConta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract sealed class Conta implements IConta permits ContaCorrente, ContaPoupanca {
    protected Cliente cliente;
    protected String numero;
    protected BigDecimal saldo;
    protected List<Transacoes> historico;

    public Conta(Cliente cliente, String numero, BigDecimal saldo) {
        this.cliente = cliente;
        this.numero = numero;
        this.saldo = saldo;
        this.historico = new ArrayList<Transacoes>();
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getNumero() {
        return numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public List<Transacoes> getHistorico() {
        return historico;
    }

    abstract boolean verificarSaldo(BigDecimal valorDaOperacao);

    abstract void retirarDinheiroSaldo(BigDecimal valorDaOperacao);

    abstract void inserirDinheiro(BigDecimal valorDaOperacao);


}
