package model;

public class PlanoContas {
    private String conta;
    private String descricao;
    private int reduzida;
    private String saldo;
    private String tipo;
    private String cc;
    private String situacao;

    public PlanoContas() {
    }

    public PlanoContas(String conta, String descricao, int reduzida, String saldo, String tipo, String cc, String situacao) {
        this.conta = conta;
        this.descricao = descricao;
        this.reduzida = reduzida;
        this.saldo = saldo;
        this.tipo = tipo;
        this.cc = cc;
        this.situacao = situacao;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getReduzida() {
        return reduzida;
    }

    public void setReduzida(int reduzida) {
        this.reduzida = reduzida;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}