package model;

public class RegraContabilizacao {

    private int idRegra;
    private String descricaoEvento;
    private String contaDebito;
    private String contaCredito;
    private boolean ativa = true;

    public RegraContabilizacao() {
    }

    public RegraContabilizacao(int idRegra, String descricaoEvento, String contaDebito,
            String contaCredito, boolean ativa) {
        this.idRegra = idRegra;
        this.descricaoEvento = descricaoEvento;
        this.contaDebito = contaDebito;
        this.contaCredito = contaCredito;
        this.ativa = ativa;
    }

    public int getIdRegra() {
        return idRegra;
    }

    public void setIdRegra(int idRegra) {
        this.idRegra = idRegra;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public String getContaDebito() {
        return contaDebito;
    }

    public void setContaDebito(String contaDebito) {
        this.contaDebito = contaDebito;
    }

    public String getContaCredito() {
        return contaCredito;
    }

    public void setContaCredito(String contaCredito) {
        this.contaCredito = contaCredito;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }
}
