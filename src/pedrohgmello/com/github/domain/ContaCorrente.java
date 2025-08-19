package pedrohgmello.com.github.domain;

import pedrohgmello.com.github.exceptions.ChaveInexistenteException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public final class ContaCorrente extends Conta {
    private BigDecimal limiteChequeEspecial;
    private BigDecimal chequeEspecialUsado = BigDecimal.ZERO;

    public ContaCorrente(Cliente cliente, String numero, BigDecimal saldo, BigDecimal limiteChequeEspecial) {
        super(cliente, numero, saldo);
        this.limiteChequeEspecial = limiteChequeEspecial;
    }


    @Override
    public boolean saque(BigDecimal valor) {
        if(this.getSaldo().compareTo(valor) >= 0){
            this.setSaldo(this.getSaldo().subtract(valor));
            this.historico.add(new Transacoes(TipoTransacao.SAQUE, valor));
            return true;
        } else if(this.getSaldo().compareTo(valor) <= 0 && this.getSaldo().add(limiteChequeEspecial.subtract(chequeEspecialUsado)).compareTo(valor) >= 0){
            this.chequeEspecialUsado = valor.subtract(this.getSaldo());
            this.setSaldo(BigDecimal.ZERO);
            this.historico.add(new Transacoes(TipoTransacao.SAQUE, valor));
            return true;
        } else return false;
    }

    @Override
    public void depositar(BigDecimal valor) {
        //TODO: Logs de Sucesso e erro
        if(this.chequeEspecialUsado.compareTo(BigDecimal.ZERO) > 0 && valor.compareTo(chequeEspecialUsado) >= 0){
            valor = valor.subtract(chequeEspecialUsado);
            this.chequeEspecialUsado = BigDecimal.ZERO;
            this.setSaldo(valor);
        } else if(this.chequeEspecialUsado.compareTo(BigDecimal.ZERO) > 0 && valor.compareTo(chequeEspecialUsado) < 0){
            this.chequeEspecialUsado = chequeEspecialUsado.subtract(valor);
        } else{
            this.setSaldo(valor);
        }
    }

    @Override
    public String toString() {
        return "ContaCorrente{" +
                "cliente=" + cliente.getNome() +
                ", numero='" + numero + '\'' +
                ", saldo=" + saldo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ContaCorrente that = (ContaCorrente) o;
        return Objects.equals(limiteChequeEspecial, that.limiteChequeEspecial) && Objects.equals(chequeEspecialUsado, that.chequeEspecialUsado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(limiteChequeEspecial, chequeEspecialUsado);
    }
}