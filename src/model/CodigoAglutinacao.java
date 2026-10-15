package model;

public class CodigoAglutinacao {

    private long id;
    private long empresaId;

    private String valor;
    private String descricao;
    private String observacao;

    private boolean ativo;


    public CodigoAglutinacao() {
        this.ativo = true;
    }


    public CodigoAglutinacao(
            long id,
            long empresaId,
            String valor,
            String descricao,
            String observacao,
            boolean ativo) {

        this.id = id;
        this.empresaId = empresaId;
        this.valor = valor;
        this.descricao = descricao;
        this.observacao = observacao;
        this.ativo = ativo;
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


    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}