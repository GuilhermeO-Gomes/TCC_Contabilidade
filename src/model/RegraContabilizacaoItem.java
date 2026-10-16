package model;

import java.math.BigDecimal;

public class RegraContabilizacaoItem {

    private long id;
    private long regraId;
    private int ordem;
    private long contaId;
    private TipoMovimento tipoMovimento;
    private BigDecimal percentual = new BigDecimal("100.0000");
    private String descricao;

    public RegraContabilizacaoItem() {
    }

    public RegraContabilizacaoItem(
            long id,
            long regraId,
            int ordem,
            long contaId,
            TipoMovimento tipoMovimento,
            BigDecimal percentual,
            String descricao) {

        this.id = id;
        this.regraId = regraId;
        this.ordem = ordem;
        this.contaId = contaId;
        this.tipoMovimento = tipoMovimento;
        this.percentual = percentual;
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRegraId() {
        return regraId;
    }

    public void setRegraId(long regraId) {
        this.regraId = regraId;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public long getContaId() {
        return contaId;
    }

    public void setContaId(long contaId) {
        this.contaId = contaId;
    }

    public TipoMovimento getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
