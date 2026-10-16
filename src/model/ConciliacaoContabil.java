package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ConciliacaoContabil {

    private long id;
    private long empresaId;
    private long contaBancariaRefId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String status = "PENDENTE";
    private Long criadoPor;
    private LocalDateTime criadoEm;
    private Long finalizadoPor;
    private LocalDateTime finalizadoEm;
    private String observacao;
    private ArrayList<ConciliacaoItem> itens = new ArrayList<>();

    public ConciliacaoContabil() {
    }

    public ConciliacaoContabil(
            long id,
            long empresaId,
            long contaBancariaRefId,
            LocalDate dataInicio,
            LocalDate dataFim,
            String status,
            Long criadoPor,
            LocalDateTime criadoEm,
            Long finalizadoPor,
            LocalDateTime finalizadoEm,
            String observacao,
            ArrayList<ConciliacaoItem> itens) {

        this.id = id;
        this.empresaId = empresaId;
        this.contaBancariaRefId = contaBancariaRefId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.criadoPor = criadoPor;
        this.criadoEm = criadoEm;
        this.finalizadoPor = finalizadoPor;
        this.finalizadoEm = finalizadoEm;
        this.observacao = observacao;
        this.itens = itens;
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

    public long getContaBancariaRefId() {
        return contaBancariaRefId;
    }

    public void setContaBancariaRefId(long contaBancariaRefId) {
        this.contaBancariaRefId = contaBancariaRefId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCriadoPor() {
        return criadoPor;
    }

    public void setCriadoPor(Long criadoPor) {
        this.criadoPor = criadoPor;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public Long getFinalizadoPor() {
        return finalizadoPor;
    }

    public void setFinalizadoPor(Long finalizadoPor) {
        this.finalizadoPor = finalizadoPor;
    }

    public LocalDateTime getFinalizadoEm() {
        return finalizadoEm;
    }

    public void setFinalizadoEm(LocalDateTime finalizadoEm) {
        this.finalizadoEm = finalizadoEm;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public ArrayList<ConciliacaoItem> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ConciliacaoItem> itens) {
        this.itens = itens;
    }
    public boolean periodoValido() {
        return dataInicio != null && dataFim != null && !dataInicio.isAfter(dataFim);
    }

    public boolean itensUnicos() {
        java.util.HashSet<Long> financeiros = new java.util.HashSet<>();
        java.util.HashSet<Long> extratos = new java.util.HashSet<>();
        if (itens == null) {
            return false;
        }
        for (ConciliacaoItem item : itens) {
            if (item == null || item.getMovimentoFinanceiroRefId() <= 0
                    || item.getMovimentoExtratoRefId() <= 0
                    || !financeiros.add(item.getMovimentoFinanceiroRefId())
                    || !extratos.add(item.getMovimentoExtratoRefId())) {
                return false;
            }
        }
        return true;
    }
}
