
package view;

public class LinhaBalanco {

    public enum Tipo {
        GRUPO,
        SUBGRUPO,
        CONTA
    }

    private final String descricao;
    private final String valorInicial;
    private final String valorFinal;
    private final Tipo tipo;

    public LinhaBalanco(
            String descricao,
            String valorInicial,
            String valorFinal,
            Tipo tipo) {

        this.descricao = descricao;
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getValorInicial() {
        return valorInicial;
    }

    public String getValorFinal() {
        return valorFinal;
    }

    public Tipo getTipo() {
        return tipo;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}
