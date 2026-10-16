package model;

import java.math.BigDecimal;

public class PartidaLancamento {

    private long id;
    private long lancamentoId;
    private long contaId;
    private Long centroCustoId;
    private TipoMovimento tipoMovimento;
    private BigDecimal valor = BigDecimal.ZERO;
    private String descricao;

    public PartidaLancamento() {
    }

    public PartidaLancamento(
            long id,
            long lancamentoId,
            long contaId,
            Long centroCustoId,
            TipoMovimento tipoMovimento,
            BigDecimal valor,
            String descricao) {

        this.id = id;
        this.lancamentoId = lancamentoId;
        this.contaId = contaId;
        this.centroCustoId = centroCustoId;
        this.tipoMovimento = tipoMovimento;
        this.valor = valor;
        this.descricao = descricao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(long lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    public long getContaId() {
        return contaId;
    }

    public void setContaId(long contaId) {
        this.contaId = contaId;
    }

    public Long getCentroCustoId() {
        return centroCustoId;
    }

    public void setCentroCustoId(Long centroCustoId) {
        this.centroCustoId = centroCustoId;
    }

    public TipoMovimento getTipoMovimento() {
        return tipoMovimento;
    }

    public void setTipoMovimento(TipoMovimento tipoMovimento) {
        this.tipoMovimento = tipoMovimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
