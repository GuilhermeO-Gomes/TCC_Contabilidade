package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class DfcRelatorio {

    private long dfcId;
    private long empresaId;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal saldoInicial = BigDecimal.ZERO;
    private BigDecimal variacaoCaixa = BigDecimal.ZERO;
    private BigDecimal saldoFinal = BigDecimal.ZERO;
    private ArrayList<DfcLinhaRelatorio> linhas = new ArrayList<>();

    public DfcRelatorio() {
    }

    public DfcRelatorio(
            long dfcId,
            long empresaId,
            LocalDate dataInicio,
            LocalDate dataFim,
            BigDecimal saldoInicial,
            BigDecimal variacaoCaixa,
            BigDecimal saldoFinal,
            ArrayList<DfcLinhaRelatorio> linhas) {

        this.dfcId = dfcId;
        this.empresaId = empresaId;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.saldoInicial = saldoInicial;
        this.variacaoCaixa = variacaoCaixa;
        this.saldoFinal = saldoFinal;
        this.linhas = linhas;
    }

    public long getDfcId() {
        return dfcId;
    }

    public void setDfcId(long dfcId) {
        this.dfcId = dfcId;
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(long empresaId) {
        this.empresaId = empresaId;
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

    public BigDecimal getSaldoInicial() {
        return saldoInicial;
    }

    public void setSaldoInicial(BigDecimal saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getVariacaoCaixa() {
        return variacaoCaixa;
    }

    public void setVariacaoCaixa(BigDecimal variacaoCaixa) {
        this.variacaoCaixa = variacaoCaixa;
    }

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public ArrayList<DfcLinhaRelatorio> getLinhas() {
        return linhas;
    }

    public void setLinhas(ArrayList<DfcLinhaRelatorio> linhas) {
        this.linhas = linhas;
    }
}
