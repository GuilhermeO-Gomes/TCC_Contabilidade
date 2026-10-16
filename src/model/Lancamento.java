package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Lancamento {

    private long id;
    private long empresaId;
    private String numeroDocumento;
    private LocalDate dataLancamento;
    private String descricao;
    private String origem = "MANUAL";
    private String status = "RASCUNHO";
    private long exercicioSocialId;
    private Long criadoPor;
    private Long contabilizadoPor;
    private LocalDateTime dataContabilizacao;
    private Long estornoDeId;
    private LocalDateTime criadoEm;
    private Long historicoPadraoId;
    private Long loteId;
    private ArrayList<PartidaLancamento> partidas = new ArrayList<>();

    public Lancamento() {
    }

    public Lancamento(
            long id,
            long empresaId,
            String numeroDocumento,
            LocalDate dataLancamento,
            String descricao,
            String origem,
            String status,
            long exercicioSocialId,
            Long criadoPor,
            Long contabilizadoPor,
            LocalDateTime dataContabilizacao,
            Long estornoDeId,
            LocalDateTime criadoEm,
            Long historicoPadraoId,
            Long loteId,
            ArrayList<PartidaLancamento> partidas) {

        this.id = id;
        this.empresaId = empresaId;
        this.numeroDocumento = numeroDocumento;
        this.dataLancamento = dataLancamento;
        this.descricao = descricao;
        this.origem = origem;
        this.status = status;
        this.exercicioSocialId = exercicioSocialId;
        this.criadoPor = criadoPor;
        this.contabilizadoPor = contabilizadoPor;
        this.dataContabilizacao = dataContabilizacao;
        this.estornoDeId = estornoDeId;
        this.criadoEm = criadoEm;
        this.historicoPadraoId = historicoPadraoId;
        this.loteId = loteId;
        this.partidas = partidas;
    }

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

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getExercicioSocialId() {
        return exercicioSocialId;
    }

    public void setExercicioSocialId(long exercicioSocialId) {
        this.exercicioSocialId = exercicioSocialId;
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

    public ArrayList<PartidaLancamento> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<PartidaLancamento> partidas) {
        this.partidas = partidas;
    }
    public BigDecimal getTotalDebitos() {
        BigDecimal total = BigDecimal.ZERO;
        for (PartidaLancamento partida : partidas) {
            if (partida.getTipoMovimento() == TipoMovimento.DEBITO) {
                total = total.add(partida.getValor());
            }
        }
        return total;
    }

    public BigDecimal getTotalCreditos() {
        BigDecimal total = BigDecimal.ZERO;
        for (PartidaLancamento partida : partidas) {
            if (partida.getTipoMovimento() == TipoMovimento.CREDITO) {
                total = total.add(partida.getValor());
            }
        }
        return total;
    }

    public boolean estaBalanceado() {
        if (partidas == null || partidas.size() < 2) {
            return false;
        }
        for (PartidaLancamento partida : partidas) {
            if (partida == null || partida.getTipoMovimento() == null || partida.getValor() == null
                    || partida.getValor().signum() <= 0 || partida.getContaId() <= 0
                    || partida.getValor().stripTrailingZeros().scale() > 2) {
                return false;
            }
        }
        return getTotalDebitos().compareTo(getTotalCreditos()) == 0;
    }

    public boolean podeEditar() {
        return "RASCUNHO".equals(status);
    }
}
