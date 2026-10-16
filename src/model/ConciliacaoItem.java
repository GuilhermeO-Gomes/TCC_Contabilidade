package model;

import java.time.LocalDateTime;
import java.time.LocalDate;

public class ConciliacaoItem {

    private long id;
    private long conciliacaoId;
    private long movimentoFinanceiroRefId;
    private long movimentoExtratoRefId;
    private String status = "PENDENTE";
    private Long conciliadoPor;
    private LocalDateTime conciliadoEm;
    private String observacao;

    public ConciliacaoItem() {
    }

    public ConciliacaoItem(
            long id,
            long conciliacaoId,
            long movimentoFinanceiroRefId,
            long movimentoExtratoRefId,
            String status,
            Long conciliadoPor,
            LocalDateTime conciliadoEm,
            String observacao) {

        this.id = id;
        this.conciliacaoId = conciliacaoId;
        this.movimentoFinanceiroRefId = movimentoFinanceiroRefId;
        this.movimentoExtratoRefId = movimentoExtratoRefId;
        this.status = status;
        this.conciliadoPor = conciliadoPor;
        this.conciliadoEm = conciliadoEm;
        this.observacao = observacao;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getConciliacaoId() {
        return conciliacaoId;
    }

    public void setConciliacaoId(long conciliacaoId) {
        this.conciliacaoId = conciliacaoId;
    }

    public long getMovimentoFinanceiroRefId() {
        return movimentoFinanceiroRefId;
    }

    public void setMovimentoFinanceiroRefId(long movimentoFinanceiroRefId) {
        this.movimentoFinanceiroRefId = movimentoFinanceiroRefId;
    }

    public long getMovimentoExtratoRefId() {
        return movimentoExtratoRefId;
    }

    public void setMovimentoExtratoRefId(long movimentoExtratoRefId) {
        this.movimentoExtratoRefId = movimentoExtratoRefId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getConciliadoPor() {
        return conciliadoPor;
    }

    public void setConciliadoPor(Long conciliadoPor) {
        this.conciliadoPor = conciliadoPor;
    }

    public LocalDateTime getConciliadoEm() {
        return conciliadoEm;
    }

    public void setConciliadoEm(LocalDateTime conciliadoEm) {
        this.conciliadoEm = conciliadoEm;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
