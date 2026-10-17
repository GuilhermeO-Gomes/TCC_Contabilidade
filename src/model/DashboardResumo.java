package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DashboardResumo {
  private BigDecimal debitos = BigDecimal.ZERO;
  private BigDecimal creditos = BigDecimal.ZERO;
  private long quantidade;
  private List<LancamentoContabil> recentes = new ArrayList<>();
  private List<InconsistenciaAuditoria> pendencias = new ArrayList<>();

  public BigDecimal getDebitos() {
    return debitos;
  }

  public void setDebitos(BigDecimal debitos) {
    this.debitos = debitos;
  }

  public BigDecimal getCreditos() {
    return creditos;
  }

  public void setCreditos(BigDecimal creditos) {
    this.creditos = creditos;
  }

  public long getQuantidade() {
    return quantidade;
  }

  public void setQuantidade(long quantidade) {
    this.quantidade = quantidade;
  }

  public List<LancamentoContabil> getRecentes() {
    return recentes;
  }

  public void setRecentes(List<LancamentoContabil> recentes) {
    this.recentes = recentes;
  }

  public List<InconsistenciaAuditoria> getPendencias() {
    return pendencias;
  }

  public void setPendencias(List<InconsistenciaAuditoria> pendencias) {
    this.pendencias = pendencias;
  }
}
