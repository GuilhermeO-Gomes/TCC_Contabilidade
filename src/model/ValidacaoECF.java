package model;

import java.time.LocalDateTime;

public class ValidacaoECF {
  private long id;
  private long ecfId;
  private TipoValidacao tipo = TipoValidacao.ERRO;
  private String codigo = "";
  private String descricao = "";
  private String referencia = "";
  private boolean resolvido = false;
  private Long resolvidoPor;
  private LocalDateTime resolvidoEm;
  private LocalDateTime detectadoEm;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getEcfId() {
    return ecfId;
  }

  public void setEcfId(long ecfId) {
    this.ecfId = ecfId;
  }

  public TipoValidacao getTipo() {
    return tipo;
  }

  public void setTipo(TipoValidacao tipo) {
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

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public boolean getResolvido() {
    return resolvido;
  }

  public void setResolvido(boolean resolvido) {
    this.resolvido = resolvido;
  }

  public Long getResolvidoPor() {
    return resolvidoPor;
  }

  public void setResolvidoPor(Long resolvidoPor) {
    this.resolvidoPor = resolvidoPor;
  }

  public LocalDateTime getResolvidoEm() {
    return resolvidoEm;
  }

  public void setResolvidoEm(LocalDateTime resolvidoEm) {
    this.resolvidoEm = resolvidoEm;
  }

  public LocalDateTime getDetectadoEm() {
    return detectadoEm;
  }

  public void setDetectadoEm(LocalDateTime detectadoEm) {
    this.detectadoEm = detectadoEm;
  }
}
