package model;

public enum StatusLancamento {
    RASCUNHO("Rascunho"),
    VALIDO("Válido"),
    INCONSISTENTE("Inconsistente"),
    CANCELADO("Cancelado");

    private final String descricao;

    StatusLancamento(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
