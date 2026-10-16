package model;

import java.util.ArrayList;

public class ItemDFC {

    private long id;
    private long dfcId;
    private int ordem;
    private String descricao;
    private String grupo;
    private String tipoLinha = "DETALHE";
    private String efeito = "SOMA";
    private Long itemSuperiorId;
    private boolean ativo = true;
    private ArrayList<DfcItemAglutinacao> aglutinacoes = new ArrayList<>();

    public ItemDFC() {
    }

    public ItemDFC(
            long id,
            long dfcId,
            int ordem,
            String descricao,
            String grupo,
            String tipoLinha,
            String efeito,
            Long itemSuperiorId,
            boolean ativo,
            ArrayList<DfcItemAglutinacao> aglutinacoes) {

        this.id = id;
        this.dfcId = dfcId;
        this.ordem = ordem;
        this.descricao = descricao;
        this.grupo = grupo;
        this.tipoLinha = tipoLinha;
        this.efeito = efeito;
        this.itemSuperiorId = itemSuperiorId;
        this.ativo = ativo;
        this.aglutinacoes = aglutinacoes;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getDfcId() {
        return dfcId;
    }

    public void setDfcId(long dfcId) {
        this.dfcId = dfcId;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getTipoLinha() {
        return tipoLinha;
    }

    public void setTipoLinha(String tipoLinha) {
        this.tipoLinha = tipoLinha;
    }

    public String getEfeito() {
        return efeito;
    }

    public void setEfeito(String efeito) {
        this.efeito = efeito;
    }

    public Long getItemSuperiorId() {
        return itemSuperiorId;
    }

    public void setItemSuperiorId(Long itemSuperiorId) {
        this.itemSuperiorId = itemSuperiorId;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public ArrayList<DfcItemAglutinacao> getAglutinacoes() {
        return aglutinacoes;
    }

    public void setAglutinacoes(ArrayList<DfcItemAglutinacao> aglutinacoes) {
        this.aglutinacoes = aglutinacoes;
    }
}
