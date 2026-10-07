package model;

import java.math.BigDecimal;

public class ItemDFC {

    private String atividade;
    private String descricao;
    private BigDecimal entradas = BigDecimal.ZERO;
    private BigDecimal saidas = BigDecimal.ZERO;
    private BigDecimal fluxoLiquido = BigDecimal.ZERO;

    public ItemDFC() {
    }

    public ItemDFC(String atividade, String descricao, BigDecimal entradas, BigDecimal saidas,
            BigDecimal fluxoLiquido) {
        this.atividade = atividade;
        this.descricao = descricao;
        this.entradas = entradas;
        this.saidas = saidas;
        this.fluxoLiquido = fluxoLiquido;
    }

    public String getAtividade() {
        return atividade;
    }

    public void setAtividade(String atividade) {
        this.atividade = atividade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getEntradas() {
        return entradas;
    }

    public void setEntradas(BigDecimal entradas) {
        this.entradas = entradas;
    }

    public BigDecimal getSaidas() {
        return saidas;
    }

    public void setSaidas(BigDecimal saidas) {
        this.saidas = saidas;
    }

    public BigDecimal getFluxoLiquido() {
        return fluxoLiquido;
    }

    public void setFluxoLiquido(BigDecimal fluxoLiquido) {
        this.fluxoLiquido = fluxoLiquido;
    }
}
