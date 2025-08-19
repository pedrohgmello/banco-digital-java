package pedrohgmello.com.github.domain;

import pedrohgmello.com.github.exceptions.ChaveInexistenteException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class ContaPoupanca extends Conta {

    //TODO: implementar escritas do console na verificação do retorno da operaçao booleana
    //TODO: implementar log de sucesso apenas em depositar

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
    }

    @Override
    public String toString() {
        return "ContaPoupanca{" +
                "cliente=" + cliente.getNome() +
                ", numero='" + numero + '\'' +
                ", saldo=" + saldo +
                '}';
    }
}
