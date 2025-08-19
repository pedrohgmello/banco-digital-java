package pedrohgmello.com.github.services;

import java.math.BigDecimal;

public interface IConta {
    boolean saque(BigDecimal valor);
    void depositar(BigDecimal valor);
}
