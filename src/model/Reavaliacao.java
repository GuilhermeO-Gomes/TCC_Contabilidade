package model;

public class Reavaliacao {
    
    public int id;
    public String situacaoAntes;
    public String situacaoLaudo;
    public double valorHistorico;
    public double novoValor;
    public double valorReavaliado;
    

    public int getId() {
       return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSituacaoAntes() {
        return situacaoAntes;
    }

    public void setSituacaoAntes(String situacaoAntes) {
        this.situacaoAntes = situacaoAntes;
    }

    public String getSituacaoLaudo() {
        return situacaoLaudo;
    }

    public void setSituacaoLaudo(String situacaoLaudo) {
        this.situacaoLaudo = situacaoLaudo;
    }

    public double getValorHistorico() {
        return valorHistorico;
    }

    public void setValorHistorico(double valorHistorico) {
        this.valorHistorico = valorHistorico;
    }

    public double getNovoValor() {
        return novoValor;
    }

    public void setNovoValor(double novoValor) {
        this.novoValor = novoValor;
    }

    public double getValorReavaliado() {
        return valorReavaliado;
    }

    public void setValorReavaliado(double valorReavaliado) {
        this.valorReavaliado = valorReavaliado;
    }
}


