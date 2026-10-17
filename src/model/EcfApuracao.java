package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EcfApuracao {
  private long id;
  private long ecfId;
  private TributoECF tributo = TributoECF.IRPJ;
  private LocalDate dataInicio = LocalDate.now().withDayOfYear(1);
  private LocalDate dataFim = LocalDate.now().withMonth(12).withDayOfMonth(31);
  private BigDecimal baseInicial = BigDecimal.ZERO;
  private BigDecimal totalAdicoes = BigDecimal.ZERO;
  private BigDecimal totalExclusoes = BigDecimal.ZERO;
  private BigDecimal totalCompensacoes = BigDecimal.ZERO;
  private BigDecimal baseCalculo = BigDecimal.ZERO;
  private BigDecimal aliquota;
  private BigDecimal valorTributo = BigDecimal.ZERO;
  private StatusApuracaoECF status = StatusApuracaoECF.EM_CALCULO;
  private LocalDateTime criadoEm;
  private List<EcfAjuste> ajustes = new ArrayList<>();

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

  public TributoECF getTributo() {
    return tributo;
  }

  public void setTributo(TributoECF tributo) {
    this.tributo = tributo;
  }

  public LocalDate getDataInicio() {
    return dataInicio;
  }

  public void setDataInicio(LocalDate dataInicio) {
    this.dataInicio = dataInicio;
  }

  public LocalDate getDataFim() {
    return dataFim;
  }

  public void setDataFim(LocalDate dataFim) {
    this.dataFim = dataFim;
  }

  public BigDecimal getBaseInicial() {
    return baseInicial;
  }

  public void setBaseInicial(BigDecimal baseInicial) {
    this.baseInicial = baseInicial;
  }

  public BigDecimal getTotalAdicoes() {
    return totalAdicoes;
  }

  public void setTotalAdicoes(BigDecimal totalAdicoes) {
    this.totalAdicoes = totalAdicoes;
  }

  public BigDecimal getTotalExclusoes() {
    return totalExclusoes;
  }

  public void setTotalExclusoes(BigDecimal totalExclusoes) {
    this.totalExclusoes = totalExclusoes;
  }

  public BigDecimal getTotalCompensacoes() {
    return totalCompensacoes;
  }

  public void setTotalCompensacoes(BigDecimal totalCompensacoes) {
    this.totalCompensacoes = totalCompensacoes;
  }

  public BigDecimal getBaseCalculo() {
    return baseCalculo;
  }

  public void setBaseCalculo(BigDecimal baseCalculo) {
    this.baseCalculo = baseCalculo;
  }

  public BigDecimal getAliquota() {
    return aliquota;
  }

  public void setAliquota(BigDecimal aliquota) {
    this.aliquota = aliquota;
  }

  public BigDecimal getValorTributo() {
    return valorTributo;
  }

  public void setValorTributo(BigDecimal valorTributo) {
    this.valorTributo = valorTributo;
  }

  public StatusApuracaoECF getStatus() {
    return status;
  }

  public void setStatus(StatusApuracaoECF status) {
    this.status = status;
  }

  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }

  public void setCriadoEm(LocalDateTime criadoEm) {
    this.criadoEm = criadoEm;
  }

  public List<EcfAjuste> getAjustes() {
    return ajustes;
  }

  public void setAjustes(List<EcfAjuste> ajustes) {
    this.ajustes = ajustes;
  }

  @Override
  public String toString() {
    return id + " - " + tributo + " - " + dataInicio + " / " + dataFim;
  }
}
