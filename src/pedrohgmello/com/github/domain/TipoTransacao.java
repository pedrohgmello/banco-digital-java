package pedrohgmello.com.github.domain;

public enum TipoTransacao {
    SAQUE("Saque"), DEPOSITO("Deposito");
    private String descricao;
    private TipoTransacao(String descricao){
        this.descricao = descricao;
    }
}
