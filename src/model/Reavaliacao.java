package model;

public class Reavaliacao {
    
    public int id;
    public String situacaoAntes;
    public String situacaoLaudo;
    public String valorHistorico;
    public String novoValor;
    public String valorReavaliado;
    

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

    public String getValorHistorico() {
        return valorHistorico;
    }

    public void setValorHistorico(String valorHistorico) {
        this.valorHistorico = valorHistorico;
    }

    public String getNovoValor() {
        return novoValor;
    }

    public void setNovoValor(String novoValor) {
        this.novoValor = novoValor;
    }

    public String getValorReavaliado() {
        return valorReavaliado;
    }

    public void setValorReavaliado(String valorReavaliado) {
        this.valorReavaliado = valorReavaliado;
    }
}


