package model;

import java.math.BigDecimal;

public class ItemLancamento {
    private int id;
    private String conta = "";
    private String centroCusto = "";
    private TipoMovimento tipoMovimento = TipoMovimento.DEBITO;
    private BigDecimal valor = BigDecimal.ZERO;

    public ItemLancamento() {
    }

    public ItemLancamento(int id, String conta, String centroCusto, TipoMovimento tipoMovimento, BigDecimal valor) {
        this.id = id;
        setConta(conta);
        setCentroCusto(centroCusto);
        setTipoMovimento(tipoMovimento);
        setValor(valor);
    }

    public ItemLancamento(ItemLancamento original) {
        this(original.id, original.conta, original.centroCusto, original.tipoMovimento, original.valor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta == null ? "" : conta;
    }

    public String getCentroCusto() {
        return centroCusto;
    }

    public void setCentroCusto(String centroCusto) {
        this.centroCusto = centroCusto == null ? "" : centroCusto;
    }

    public TipoMovimento getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento == null ? TipoMovimento.DEBITO : tipoMovimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor == null ? BigDecimal.ZERO : valor;
    }

    @Override
    public String toString() {
        return conta + " - " + tipoMovimento + ": " + valor.toPlainString();
    }

}
