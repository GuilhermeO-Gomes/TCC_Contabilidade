package model;

public class HistoricoPadrao {

    private long id;
    private long empresaId;

    private String descricao;

    private boolean ativo;


    public HistoricoPadrao() {
        this.ativo = true;
    }


    public HistoricoPadrao(
            long id,
            long empresaId,
            String descricao,
            boolean ativo) {

        this.id = id;
        this.empresaId = empresaId;
        this.descricao = descricao;
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


    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}