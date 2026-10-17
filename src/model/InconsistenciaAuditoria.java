package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InconsistenciaAuditoria {
  private long id;
  private String codigo = "";
  private String descricao = "";
  private String recomendacao = "";
  private NivelSeveridade severidade = NivelSeveridade.MEDIA;
  private StatusAuditoria status = StatusAuditoria.PENDENTE;
  private Long lancamentoId;
  private Long partidaId;
  private LocalDateTime detectadoEm;
  private Long revisadoPor;
  private LocalDateTime revisadoEm;
  private String observacaoRevisao = "";
  private LocalDate data;
  private String conta = "";

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
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

  public String getRecomendacao() {
    return recomendacao;
  }

  public void setRecomendacao(String recomendacao) {
    this.recomendacao = recomendacao;
  }

  public NivelSeveridade getSeveridade() {
    return severidade;
  }

  public void setSeveridade(NivelSeveridade severidade) {
    this.severidade = severidade;
  }

  public StatusAuditoria getStatus() {
    return status;
  }

  public void setStatus(StatusAuditoria status) {
    this.status = status;
  }

  public Long getLancamentoId() {
    return lancamentoId;
  }

  public void setLancamentoId(Long lancamentoId) {
    this.lancamentoId = lancamentoId;
  }

  public Long getPartidaId() {
    return partidaId;
  }

  public void setPartidaId(Long partidaId) {
    this.partidaId = partidaId;
  }

  public LocalDateTime getDetectadoEm() {
    return detectadoEm;
  }

  public void setDetectadoEm(LocalDateTime detectadoEm) {
    this.detectadoEm = detectadoEm;
  }

  public Long getRevisadoPor() {
    return revisadoPor;
  }

  public void setRevisadoPor(Long revisadoPor) {
    this.revisadoPor = revisadoPor;
  }

  public LocalDateTime getRevisadoEm() {
    return revisadoEm;
  }

  public void setRevisadoEm(LocalDateTime revisadoEm) {
    this.revisadoEm = revisadoEm;
  }

  public String getObservacaoRevisao() {
    return observacaoRevisao;
  }

  public void setObservacaoRevisao(String observacaoRevisao) {
    this.observacaoRevisao = observacaoRevisao;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public String getConta() {
    return conta;
  }

  public void setConta(String conta) {
    this.conta = conta;
  }

  public String getChave() {
    return lancamentoId + ":" + codigo + ":" + partidaId;
  }
}
