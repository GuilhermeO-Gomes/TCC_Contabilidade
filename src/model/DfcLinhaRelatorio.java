package model;

import java.math.BigDecimal;

public class DfcLinhaRelatorio {

    private long itemId;
    private int ordem;
    private Long itemSuperiorId;
    private String grupo;
    private String descricao;
    private String tipoLinha;
    private BigDecimal entradas = BigDecimal.ZERO;
    private BigDecimal saidas = BigDecimal.ZERO;
    private BigDecimal fluxoLiquido = BigDecimal.ZERO;

    public DfcLinhaRelatorio() {
    }

    public DfcLinhaRelatorio(
            long itemId,
            int ordem,
            Long itemSuperiorId,
            String grupo,
            String descricao,
            String tipoLinha,
            BigDecimal entradas,
            BigDecimal saidas,
            BigDecimal fluxoLiquido) {

        this.itemId = itemId;
        this.ordem = ordem;
        this.itemSuperiorId = itemSuperiorId;
        this.grupo = grupo;
        this.descricao = descricao;
        this.tipoLinha = tipoLinha;
        this.entradas = entradas;
        this.saidas = saidas;
        this.fluxoLiquido = fluxoLiquido;
    }

    public long getItemId() {
        return itemId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public Long getItemSuperiorId() {
        return itemSuperiorId;
    }

    public void setItemSuperiorId(Long itemSuperiorId) {
        this.itemSuperiorId = itemSuperiorId;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipoLinha() {
        return tipoLinha;
    }

    public void setTipoLinha(String tipoLinha) {
        this.tipoLinha = tipoLinha;
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
