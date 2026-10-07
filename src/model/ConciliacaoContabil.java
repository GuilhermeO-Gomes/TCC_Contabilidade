package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class ConciliacaoContabil {

    private int idConciliacao;
    private String conta;
    private LocalDate dataConciliacao;
    private String origemExtrato;
    private String status = "Pendente";
    private ArrayList<MovimentoConciliacao> movimentosSistema = new ArrayList<>();
    private ArrayList<MovimentoConciliacao> movimentosExtrato = new ArrayList<>();

    public ConciliacaoContabil() {
    }

    public ConciliacaoContabil(int idConciliacao, String conta, LocalDate dataConciliacao,
            String origemExtrato, String status, ArrayList<MovimentoConciliacao> movimentosSistema,
            ArrayList<MovimentoConciliacao> movimentosExtrato) {
        this.idConciliacao = idConciliacao;
        this.conta = conta;
        this.dataConciliacao = dataConciliacao;
        this.origemExtrato = origemExtrato;
        this.status = status;
        this.movimentosSistema = movimentosSistema;
        this.movimentosExtrato = movimentosExtrato;
    }

    public int getIdConciliacao() {
        return idConciliacao;
    }

    public void setIdConciliacao(int idConciliacao) {
        this.idConciliacao = idConciliacao;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public LocalDate getDataConciliacao() {
        return dataConciliacao;
    }

    public void setDataConciliacao(LocalDate dataConciliacao) {
        this.dataConciliacao = dataConciliacao;
    }

    public String getOrigemExtrato() {
        return origemExtrato;
    }

    public void setOrigemExtrato(String origemExtrato) {
        this.origemExtrato = origemExtrato;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<MovimentoConciliacao> getMovimentosSistema() {
        return movimentosSistema;
    }

    public void setMovimentosSistema(ArrayList<MovimentoConciliacao> movimentosSistema) {
        this.movimentosSistema = movimentosSistema;
    }

    public ArrayList<MovimentoConciliacao> getMovimentosExtrato() {
        return movimentosExtrato;
    }

    public void setMovimentosExtrato(ArrayList<MovimentoConciliacao> movimentosExtrato) {
        this.movimentosExtrato = movimentosExtrato;
    }
}
