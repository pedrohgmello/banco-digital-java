package pedrohgmello.com.github.domain;

import pedrohgmello.com.github.exceptions.ChaveInexistenteException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public final class ContaPoupanca extends Conta {

    public ContaPoupanca(Cliente cliente, String numero, BigDecimal saldo) {
        super(cliente, numero, saldo);
    }


    @Override
    public boolean saque(BigDecimal valor) {
        if(this.saldo.compareTo(valor) >= 0) {
            this.saldo = this.saldo.subtract(valor);
            this.historico.add(new Transacoes(TipoTransacao.SAQUE, valor));
            return true;
        }
        return false;
    }

    @Override
    public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
        this.historico.add(new Transacoes(TipoTransacao.DEPOSITO, valor));
    }

    public void renderJuros(BigDecimal taxaAnual){
        BigDecimal taxaMensal = taxaAnual.divide(new BigDecimal("12"), 8, RoundingMode.HALF_UP);
        BigDecimal rendimento = this.saldo.multiply(taxaMensal);
        inserirDinheiro(rendimento);
    }

    @Override
    public void retirarDinheiroSaldo(BigDecimal valorDaOperacao){
        this.setSaldo(saldo.subtract(valorDaOperacao));
    }

    @Override
    public boolean verificarSaldo(BigDecimal valorDaOperacao){
        return this.saldo.compareTo(valorDaOperacao) >= 0;
    }
    @Override
    public void inserirDinheiro(BigDecimal valorDaOperacao){
        this.setSaldo(this.getSaldo().add(valorDaOperacao));
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "ContaPoupanca{" +
                "cliente=" + cliente.getNome() +
                ", numero='" + numero + '\'' +
                ", saldo=" + df.format(saldo) +
                '}';
    }
}
