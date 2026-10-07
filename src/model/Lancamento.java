package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Lancamento {

    private int idLancamento;
    private Integer idLote;
    private int idExercicio;
    private LocalDate data;
    private String documento;
    private Integer idHistorico;
    private String complementoHistorico;
    private String tipoLancamento = "Normal";
    private ArrayList<PartidaLancamento> partidas = new ArrayList<>();

    public Lancamento() {
    }

    public Lancamento(int idLancamento, Integer idLote, int idExercicio, LocalDate data, String documento,
            Integer idHistorico, String complementoHistorico, String tipoLancamento,
            ArrayList<PartidaLancamento> partidas) {
        this.idLancamento = idLancamento;
        this.idLote = idLote;
        this.idExercicio = idExercicio;
        this.data = data;
        this.documento = documento;
        this.idHistorico = idHistorico;
        this.complementoHistorico = complementoHistorico;
        this.tipoLancamento = tipoLancamento;
        this.partidas = partidas;
    }

    public int getIdLancamento() {
        return idLancamento;
    }

    public void setIdLancamento(int idLancamento) {
        this.idLancamento = idLancamento;
    }

    public Integer getIdLote() {
        return idLote;
    }

    public void setIdLote(Integer idLote) {
        this.idLote = idLote;
    }

    public int getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(int idExercicio) {
        this.idExercicio = idExercicio;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Integer getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }

    public String getComplementoHistorico() {
        return complementoHistorico;
    }

    public void setComplementoHistorico(String complementoHistorico) {
        this.complementoHistorico = complementoHistorico;
    }

    public String getTipoLancamento() {
        return tipoLancamento;
    }

    public void setTipoLancamento(String tipoLancamento) {
        this.tipoLancamento = tipoLancamento;
    }

    public ArrayList<PartidaLancamento> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<PartidaLancamento> partidas) {
        this.partidas = partidas;
    }
}
