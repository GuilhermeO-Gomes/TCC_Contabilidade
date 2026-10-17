package model;

import java.math.BigDecimal;

public class EcfAjuste {
  private long id;
  private long apuracaoId;
  private TipoAjusteECF tipo = TipoAjusteECF.ADICAO;
  private String codigo = "";
  private String descricao = "";
  private BigDecimal valor = BigDecimal.ZERO;
  private Long contaId;
  private String documentoReferencia = "";
  private String observacao = "";

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getApuracaoId() {
    return apuracaoId;
  }

  public void setApuracaoId(long apuracaoId) {
    this.apuracaoId = apuracaoId;
  }

  public TipoAjusteECF getTipo() {
    return tipo;
  }

  public void setTipo(TipoAjusteECF tipo) {
    this.tipo = tipo;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public BigDecimal getValor() {
    return valor;
  }

  public void setValor(BigDecimal valor) {
    this.valor = valor;
  }

  public Long getContaId() {
    return contaId;
  }

  public void setContaId(Long contaId) {
    this.contaId = contaId;
  }

  public String getDocumentoReferencia() {
    return documentoReferencia;
  }

  public void setDocumentoReferencia(String documentoReferencia) {
    this.documentoReferencia = documentoReferencia;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }
}
