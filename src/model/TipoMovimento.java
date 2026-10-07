package model;

public enum TipoMovimento {
    DEBITO("Débito"),
    CREDITO("Crédito");

    private final String descricao;

    TipoMovimento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
