package model;

import java.math.BigDecimal;

public class PartidaLancamento {

    private int idItem;
    private int idLancamento;
    private String conta;
    private Integer idCentroCusto;
    private String tipoPartida;
    private BigDecimal valor = BigDecimal.ZERO;

    public PartidaLancamento() {
    }

    public PartidaLancamento(int idItem, int idLancamento, String conta, Integer idCentroCusto,
            String tipoPartida, BigDecimal valor) {
        this.idItem = idItem;
        this.idLancamento = idLancamento;
        this.conta = conta;
        this.idCentroCusto = idCentroCusto;
        this.tipoPartida = tipoPartida;
        this.valor = valor;
    }

    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(int idLancamento) {
        this.idLancamento = idLancamento;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public Integer getIdCentroCusto() {
        return idCentroCusto;
    }

    public void setIdCentroCusto(Integer idCentroCusto) {
        this.idCentroCusto = idCentroCusto;
    }

    public String getTipoPartida() {
        return tipoPartida;
    }

    public void setTipoPartida(String tipoPartida) {
        this.tipoPartida = tipoPartida;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
