package model;

import java.math.BigDecimal;

public class ItemLancamento {
  private long id;
  private long lancamentoId;
  private long contaId;
  private Long centroCustoId;
  private String conta = "";
  private String centroCusto = "";
  private String descricao = "";
  private TipoMovimento tipoMovimento = TipoMovimento.DEBITO;
  private BigDecimal valor = BigDecimal.ZERO;

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

  public String getConta() {
    return conta;
  }

  public void setConta(String conta) {
    this.conta = conta;
  }

  public String getCentroCusto() {
    return centroCusto;
  }

  public void setCentroCusto(String centroCusto) {
    this.centroCusto = centroCusto;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
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

  public ItemLancamento() {}

  public ItemLancamento(ItemLancamento o) {
    id = o.id;
    lancamentoId = o.lancamentoId;
    contaId = o.contaId;
    centroCustoId = o.centroCustoId;
    conta = o.conta;
    centroCusto = o.centroCusto;
    descricao = o.descricao;
    tipoMovimento = o.tipoMovimento;
    valor = o.valor;
  }
}
