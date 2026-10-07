package model;

public enum StatusAuditoria {
    PENDENTE("Pendente"),
    REVISADO("Revisado"),
    IGNORADO("Ignorado");

    private final String descricao;

    StatusAuditoria(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
