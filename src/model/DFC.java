package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class DFC {

    private LocalDate dataInicio;
    private LocalDate dataFim;
    private BigDecimal saldoInicial = BigDecimal.ZERO;
    private BigDecimal variacaoCaixa = BigDecimal.ZERO;
    private BigDecimal saldoFinal = BigDecimal.ZERO;
    private ArrayList<ItemDFC> itens = new ArrayList<>();

    public DFC() {
    }

    public DFC(LocalDate dataInicio, LocalDate dataFim, BigDecimal saldoInicial, BigDecimal variacaoCaixa,
            BigDecimal saldoFinal, ArrayList<ItemDFC> itens) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.saldoInicial = saldoInicial;
        this.variacaoCaixa = variacaoCaixa;
        this.saldoFinal = saldoFinal;
        this.itens = itens;
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

    public ArrayList<ItemDFC> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemDFC> itens) {
        this.itens = itens;
    }
}
