package pedrohgmello.com.github.domain;

import java.math.BigDecimal;
import java.util.Optional;

public class Transacoes {
    private TipoTransacao tipo;
    private BigDecimal valor;

    public Transacoes(TipoTransacao tipo, BigDecimal valor, Conta destinatario) {
        this.tipo = tipo;
        this.valor = valor;
    }

    public Transacoes(TipoTransacao tipo, BigDecimal valor) {
        this.tipo = tipo;
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Transacoes{" +
                "tipo=" + tipo +
                ", valor=" + valor +
                '}';
    }
}
