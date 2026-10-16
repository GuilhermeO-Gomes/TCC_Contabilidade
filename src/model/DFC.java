package model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;

public class DFC {

    private long id;
    private long empresaId;
    private String descricao;
    private String metodo = "DIRETO";
    private boolean favorito = false;
    private boolean ativo = true;
    private LocalDateTime criadoEm;
    private ArrayList<ItemDFC> itens = new ArrayList<>();

    public DFC() {
    }

    public DFC(
            long id,
            long empresaId,
            String descricao,
            String metodo,
            boolean favorito,
            boolean ativo,
            LocalDateTime criadoEm,
            ArrayList<ItemDFC> itens) {

        this.id = id;
        this.empresaId = empresaId;
        this.descricao = descricao;
        this.metodo = metodo;
        this.favorito = favorito;
        this.ativo = ativo;
        this.criadoEm = criadoEm;
        this.itens = itens;
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

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public ArrayList<ItemDFC> getItens() {
        return itens;
    }

    public void setItens(ArrayList<ItemDFC> itens) {
        this.itens = itens;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
