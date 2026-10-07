package model;

public enum NivelSeveridade {
    INFORMACAO("Informação"),
    ALERTA("Alerta"),
    CRITICO("Crítico");

    private final String descricao;

    NivelSeveridade(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
