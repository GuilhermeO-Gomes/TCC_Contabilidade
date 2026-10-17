package model;

import java.time.LocalDate;

public class ExercicioSocial {

    public enum StatusContabil {
        NAO_INICIADO,
        ABERTO,
        ENCERRADO
    }

    private long id;
    private long empresaId;

    private int anoReferencia;

    private LocalDate dataInicio;
    private LocalDate dataFim;

    private boolean ativo;

    private StatusContabil statusContabil;


    public ExercicioSocial() {
        this.ativo = true;
        this.statusContabil = StatusContabil.NAO_INICIADO;
    }


    public ExercicioSocial(
            long id,
            long empresaId,
            int anoReferencia,
            LocalDate dataInicio,
            LocalDate dataFim,
            boolean ativo,
            StatusContabil statusContabil) {

        this.id = id;
        this.empresaId = empresaId;
        this.anoReferencia = anoReferencia;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativo = ativo;
        this.statusContabil = statusContabil;
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


    public int getAnoReferencia() {
        return anoReferencia;
    }

    public void setAnoReferencia(int anoReferencia) {
        this.anoReferencia = anoReferencia;
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


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }


    public StatusContabil getStatusContabil() {
        return statusContabil;
    }

    public void setStatusContabil(StatusContabil statusContabil) {
        this.statusContabil = statusContabil;
    }
}