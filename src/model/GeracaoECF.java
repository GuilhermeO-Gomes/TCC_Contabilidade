package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GeracaoECF {
  private long id;
  private long empresaId;
  private String empresa = "";
  private long exercicioSocialId;
  private Long ecdGeracaoId;
  private LocalDate periodoInicial = LocalDate.now().withDayOfYear(1);
  private LocalDate periodoFinal = LocalDate.now().withMonth(12).withDayOfMonth(31);
  private FinalidadeECF finalidade = FinalidadeECF.ORIGINAL;
  private Long ecfRetificadaId;
  private RegimeTributacao regimeTributacao = RegimeTributacao.LUCRO_PRESUMIDO;
  private String versaoLayout = "";
  private StatusECF status = StatusECF.EM_PREPARACAO;
  private String nomeArquivo;
  private String hashArquivo;
  private String numeroRecibo;
  private Long geradoPor;
  private LocalDateTime geradoEm;
  private LocalDateTime transmitidoEm;
  private String observacao = "";
  private LocalDateTime criadoEm;
  private List<ValidacaoECF> validacoes = new ArrayList<>();
  private List<EcfApuracao> apuracoes = new ArrayList<>();
  private List<EcfSignatario> signatarios = new ArrayList<>();

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getEmpresaId() {
    return empresaId;
  }

  public void setEmpresaId(long empresaId) {
    this.empresaId = empresaId;
  }

  public String getEmpresa() {
    return empresa;
  }

  public void setEmpresa(String empresa) {
    this.empresa = empresa;
  }

  public long getExercicioSocialId() {
    return exercicioSocialId;
  }

  public void setExercicioSocialId(long exercicioSocialId) {
    this.exercicioSocialId = exercicioSocialId;
  }

  public Long getEcdGeracaoId() {
    return ecdGeracaoId;
  }

  public void setEcdGeracaoId(Long ecdGeracaoId) {
    this.ecdGeracaoId = ecdGeracaoId;
  }

  public LocalDate getPeriodoInicial() {
    return periodoInicial;
  }

  public void setPeriodoInicial(LocalDate periodoInicial) {
    this.periodoInicial = periodoInicial;
  }

  public LocalDate getPeriodoFinal() {
    return periodoFinal;
  }

  public void setPeriodoFinal(LocalDate periodoFinal) {
    this.periodoFinal = periodoFinal;
  }

  public FinalidadeECF getFinalidade() {
    return finalidade;
  }

  public void setFinalidade(FinalidadeECF finalidade) {
    this.finalidade = finalidade;
  }

  public Long getEcfRetificadaId() {
    return ecfRetificadaId;
  }

  public void setEcfRetificadaId(Long ecfRetificadaId) {
    this.ecfRetificadaId = ecfRetificadaId;
  }

  public RegimeTributacao getRegimeTributacao() {
    return regimeTributacao;
  }

  public void setRegimeTributacao(RegimeTributacao regimeTributacao) {
    this.regimeTributacao = regimeTributacao;
  }

  public String getVersaoLayout() {
    return versaoLayout;
  }

  public void setVersaoLayout(String versaoLayout) {
    this.versaoLayout = versaoLayout;
  }

  public StatusECF getStatus() {
    return status;
  }

  public void setStatus(StatusECF status) {
    this.status = status;
  }

  public String getNomeArquivo() {
    return nomeArquivo;
  }

  public void setNomeArquivo(String nomeArquivo) {
    this.nomeArquivo = nomeArquivo;
  }

  public String getHashArquivo() {
    return hashArquivo;
  }

  public void setHashArquivo(String hashArquivo) {
    this.hashArquivo = hashArquivo;
  }

  public String getNumeroRecibo() {
    return numeroRecibo;
  }

  public void setNumeroRecibo(String numeroRecibo) {
    this.numeroRecibo = numeroRecibo;
  }

  public Long getGeradoPor() {
    return geradoPor;
  }

  public void setGeradoPor(Long geradoPor) {
    this.geradoPor = geradoPor;
  }

  public LocalDateTime getGeradoEm() {
    return geradoEm;
  }

  public void setGeradoEm(LocalDateTime geradoEm) {
    this.geradoEm = geradoEm;
  }

  public LocalDateTime getTransmitidoEm() {
    return transmitidoEm;
  }

  public void setTransmitidoEm(LocalDateTime transmitidoEm) {
    this.transmitidoEm = transmitidoEm;
  }

  public String getObservacao() {
    return observacao;
  }

  public void setObservacao(String observacao) {
    this.observacao = observacao;
  }

  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }

  public void setCriadoEm(LocalDateTime criadoEm) {
    this.criadoEm = criadoEm;
  }

  public List<ValidacaoECF> getValidacoes() {
    return validacoes;
  }

  public void setValidacoes(List<ValidacaoECF> validacoes) {
    this.validacoes = validacoes;
  }

  public List<EcfApuracao> getApuracoes() {
    return apuracoes;
  }

  public void setApuracoes(List<EcfApuracao> apuracoes) {
    this.apuracoes = apuracoes;
  }

  public List<EcfSignatario> getSignatarios() {
    return signatarios;
  }

  public void setSignatarios(List<EcfSignatario> signatarios) {
    this.signatarios = signatarios;
  }

  public long contarErros() {
    return validacoes.stream()
        .filter(v -> v.getTipo() == TipoValidacao.ERRO && !v.getResolvido())
        .count();
  }

  public long contarAlertas() {
    return validacoes.stream()
        .filter(v -> v.getTipo() == TipoValidacao.AVISO && !v.getResolvido())
        .count();
  }

  public boolean possuiErros() {
    return contarErros() > 0;
  }

  public int getAnoCalendario() {
    return periodoInicial.getYear();
  }

  @Override
  public String toString() {
    return id + " - " + getAnoCalendario() + " - " + finalidade;
  }
}
