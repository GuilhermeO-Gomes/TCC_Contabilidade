package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Bem {

    private long empresaId;
    private long categoriaId;
    private String codigoPatrimonial;
    private String descricao;
    private String documentoAquisicao;
    private LocalDate dataAquisicao;
    private LocalDate dataInicioUso;
    private BigDecimal valorAquisicao;
    private BigDecimal valorResidual;
    private int vidaUtilMeses;
    private String metodoDepreciacao;

    public long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(long empresaId) {
        this.empresaId = empresaId;
    }

    public long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getCodigoPatrimonial() {
        return codigoPatrimonial;
    }

    public void setCodigoPatrimonial(String codigoPatrimonial) {
        this.codigoPatrimonial = codigoPatrimonial;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDocumentoAquisicao() {
        return documentoAquisicao;
    }

    public void setDocumentoAquisicao(String documentoAquisicao) {
        this.documentoAquisicao = documentoAquisicao;
    }

    public LocalDate getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(LocalDate dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public LocalDate getDataInicioUso() {
        return dataInicioUso;
    }

    public void setDataInicioUso(LocalDate dataInicioUso) {
        this.dataInicioUso = dataInicioUso;
    }

    public BigDecimal getValorAquisicao() {
        return valorAquisicao;
    }

    public void setValorAquisicao(BigDecimal valorAquisicao) {
        this.valorAquisicao = valorAquisicao;
    }

    public BigDecimal getValorResidual() {
        return valorResidual;
    }

    public void setValorResidual(BigDecimal valorResidual) {
        this.valorResidual = valorResidual;
    }

    public int getVidaUtilMeses() {
        return vidaUtilMeses;
    }

    public void setVidaUtilMeses(int vidaUtilMeses) {
        this.vidaUtilMeses = vidaUtilMeses;
    }

    public String getMetodoDepreciacao() {
        return metodoDepreciacao;
    }

    public void setMetodoDepreciacao(String metodoDepreciacao) {
        this.metodoDepreciacao = metodoDepreciacao;
    }
}
