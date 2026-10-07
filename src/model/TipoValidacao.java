package model;

public enum TipoValidacao {
    SUCESSO("Sucesso"),
    ALERTA("Alerta"),
    ERRO("Erro");

    private final String descricao;

    TipoValidacao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
