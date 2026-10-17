package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LancamentoContabil {
  private long id;
  private long empresaId;
  private long exercicioSocialId;
  private String numeroDocumento = "";
  private LocalDate data = LocalDate.now();
  private String descricao = "";
  private String origem = "MANUAL";
  private StatusLancamento status = StatusLancamento.RASCUNHO;
  private Long historicoPadraoId;
  private Long loteId;
  private Long criadoPor;
  private Long contabilizadoPor;
  private LocalDateTime dataContabilizacao;
  private Long estornoDeId;
  private LocalDateTime criadoEm;
  private List<ItemLancamento> itens = new ArrayList<>();

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

  public long getExercicioSocialId() {
    return exercicioSocialId;
  }

  public void setExercicioSocialId(long exercicioSocialId) {
    this.exercicioSocialId = exercicioSocialId;
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public LocalDate getData() {
    return data;
  }

  public void setData(LocalDate data) {
    this.data = data;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public String getOrigem() {
    return origem;
  }

  public void setOrigem(String origem) {
    this.origem = origem;
  }

  public StatusLancamento getStatus() {
    return status;
  }

  public void setStatus(StatusLancamento status) {
    this.status = status;
  }

  public Long getHistoricoPadraoId() {
    return historicoPadraoId;
  }

  public void setHistoricoPadraoId(Long historicoPadraoId) {
    this.historicoPadraoId = historicoPadraoId;
  }

  public Long getLoteId() {
    return loteId;
  }

  public void setLoteId(Long loteId) {
    this.loteId = loteId;
  }

  public Long getCriadoPor() {
    return criadoPor;
  }

  public void setCriadoPor(Long criadoPor) {
    this.criadoPor = criadoPor;
  }

  public Long getContabilizadoPor() {
    return contabilizadoPor;
  }

  public void setContabilizadoPor(Long contabilizadoPor) {
    this.contabilizadoPor = contabilizadoPor;
  }

  public LocalDateTime getDataContabilizacao() {
    return dataContabilizacao;
  }

  public void setDataContabilizacao(LocalDateTime dataContabilizacao) {
    this.dataContabilizacao = dataContabilizacao;
  }

  public Long getEstornoDeId() {
    return estornoDeId;
  }

  public void setEstornoDeId(Long estornoDeId) {
    this.estornoDeId = estornoDeId;
  }

  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }

  public void setCriadoEm(LocalDateTime criadoEm) {
    this.criadoEm = criadoEm;
  }

  public List<ItemLancamento> getItens() {
    return itens;
  }

  public void setItens(List<ItemLancamento> itens) {
    this.itens = itens;
  }

  public LancamentoContabil() {}

  public LancamentoContabil(LancamentoContabil o) {
    id = o.id;
    empresaId = o.empresaId;
    exercicioSocialId = o.exercicioSocialId;
    numeroDocumento = o.numeroDocumento;
    data = o.data;
    descricao = o.descricao;
    origem = o.origem;
    status = o.status;
    historicoPadraoId = o.historicoPadraoId;
    loteId = o.loteId;
    criadoPor = o.criadoPor;
    contabilizadoPor = o.contabilizadoPor;
    dataContabilizacao = o.dataContabilizacao;
    estornoDeId = o.estornoDeId;
    criadoEm = o.criadoEm;
    for (ItemLancamento item : o.itens) itens.add(new ItemLancamento(item));
  }

  public String getDocumento() {
    return numeroDocumento;
  }

  public void setDocumento(String valor) {
    numeroDocumento = valor;
  }

  public String getHistorico() {
    return descricao;
  }

  public void setHistorico(String valor) {
    descricao = valor;
  }

  public void adicionarItem(ItemLancamento item) {
    itens.add(java.util.Objects.requireNonNull(item));
  }

  public void removerItem(ItemLancamento item) {
    itens.remove(item);
  }

  private BigDecimal total(TipoMovimento tipo) {
    BigDecimal total = BigDecimal.ZERO;
    for (ItemLancamento item : itens)
      if (item.getTipoMovimento() == tipo) total = total.add(item.getValor());
    return total;
  }

  public BigDecimal calcularTotalDebitos() {
    return total(TipoMovimento.DEBITO);
  }

  public BigDecimal calcularTotalCreditos() {
    return total(TipoMovimento.CREDITO);
  }

  public BigDecimal calcularDiferenca() {
    return calcularTotalDebitos().subtract(calcularTotalCreditos());
  }

  public boolean estaBalanceado() {
    return calcularDiferenca().compareTo(BigDecimal.ZERO) == 0;
  }

  public boolean estaNoPeriodo(LocalDate inicio, LocalDate fim) {
    return !data.isBefore(inicio) && !data.isAfter(fim);
  }
}
