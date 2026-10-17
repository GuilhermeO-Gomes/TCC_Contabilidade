package model;

public class BalancoPatrimonial {

    private long id;
    private long empresaId;

    private String descricao;

    private boolean favorito;


    public BalancoPatrimonial() {
        this.favorito = false;
    }


    public BalancoPatrimonial(
            long id,
            long empresaId,
            String descricao,
            boolean favorito) {

        this.id = id;
        this.empresaId = empresaId;
        this.descricao = descricao;
        this.favorito = favorito;
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


    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }
}