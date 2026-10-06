package model;

public class BalancoPatrimonial {

    private int id;
    private int ordem;
    private String codigoAglutinacao;
    private String grupo;
    private String codigoAglutinacaoSuperior;
    private boolean ativo;

    public BalancoPatrimonial() {
    }

    public BalancoPatrimonial(
            int id,
            int ordem,
            String codigoAglutinacao,
            String grupo,
            String codigoAglutinacaoSuperior,
            boolean ativo) {

        this.id = id;
        this.ordem = ordem;
        this.codigoAglutinacao = codigoAglutinacao;
        this.grupo = grupo;
        this.codigoAglutinacaoSuperior =
                codigoAglutinacaoSuperior;
        this.ativo = ativo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public String getCodigoAglutinacao() {
        return codigoAglutinacao;
    }

    public void setCodigoAglutinacao(
            String codigoAglutinacao) {

        this.codigoAglutinacao =
                codigoAglutinacao;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getCodigoAglutinacaoSuperior() {
        return codigoAglutinacaoSuperior;
    }

    public void setCodigoAglutinacaoSuperior(
            String codigo) {

        this.codigoAglutinacaoSuperior =
                codigo;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}