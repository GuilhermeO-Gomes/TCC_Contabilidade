package model;

public class DRE {

    private int id;
    private String descricao;
    private boolean favorito;

    public DRE() {
    }

    public DRE(int id, String descricao, boolean favorito) {
        this.id = id;
        this.descricao = descricao;
        this.favorito = favorito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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