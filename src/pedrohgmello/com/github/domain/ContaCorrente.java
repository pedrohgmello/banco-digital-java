package pedrohgmello.com.github.domain;

import pedrohgmello.com.github.exceptions.ChaveInexistenteException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
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
        if(verificarSaldo(valor)){
            retirarDinheiroSaldo(valor);
            this.historico.add(new Transacoes(TipoTransacao.SAQUE, valor));
            return true;
        } else if(verificarLimiteChequeEspecial(valor)){
            retirarDinheiroChequeEspecial(valor);
            this.historico.add(new Transacoes(TipoTransacao.SAQUE, valor));
            return true;
        }
        return false;
    }

    @Override
    public void depositar(BigDecimal valor) {
        //TODO: Logs de Sucesso e erro
        if(verificarSeChequeEspecialFoiUsado() && valor.compareTo(chequeEspecialUsado) >= 0){
            inserirDinheiroEDescontar(valor);
        } else if(this.chequeEspecialUsado.compareTo(BigDecimal.ZERO) > 0 && valor.compareTo(chequeEspecialUsado) < 0){
            pagarChequeEspecial(valor);
        } else{
            inserirDinheiro(valor);
        }
    }

    @Override
    public boolean verificarSaldo(BigDecimal valorDaOperacao){
        return this.saldo.compareTo(valorDaOperacao) >= 0;
    }

    public boolean verificarLimiteChequeEspecial(BigDecimal valorDaOperacao){
        return this.getSaldo().add(limiteChequeEspecial.subtract(chequeEspecialUsado)).compareTo(valorDaOperacao) >= 0;
    }

    @Override
    public void retirarDinheiroSaldo(BigDecimal valor){
        this.setSaldo(this.getSaldo().subtract(valor));
    }

    public boolean verificarSeChequeEspecialFoiUsado(){
        return this.chequeEspecialUsado.compareTo(BigDecimal.ZERO) > 0;
    }

    public void retirarDinheiroChequeEspecial(BigDecimal valorDaOperacao){
        this.chequeEspecialUsado = valorDaOperacao.subtract(this.saldo);
        this.setSaldo(BigDecimal.ZERO);
    }

    public void inserirDinheiro(BigDecimal valorDaOperacao){
        this.setSaldo(this.getSaldo().add(valorDaOperacao));
    }

    public void inserirDinheiroEDescontar(BigDecimal valorDaOperacao){
        this.setSaldo(this.getSaldo().add(valorDaOperacao.subtract(chequeEspecialUsado)));
        this.chequeEspecialUsado = BigDecimal.ZERO;
    }

    public void pagarChequeEspecial(BigDecimal valor){
        this.chequeEspecialUsado = chequeEspecialUsado.subtract(valor);
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        return "ContaCorrente{" +
                "cliente=" + cliente.getNome() +
                ", numero='" + numero + '\'' +
                ", saldo=" + df.format(saldo) +
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