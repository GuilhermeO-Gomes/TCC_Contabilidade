package model;


public class ValidacaoECF {
    private int id;
    private String codigo = "";
    private String categoria = "";
    private String descricao = "";
    private String mensagem = "";
    private TipoValidacao tipo = TipoValidacao.SUCESSO;
    private boolean aprovado;

    public ValidacaoECF() {
    }

    public ValidacaoECF(int id, String codigo, String categoria, String descricao, String mensagem, TipoValidacao tipo) {
        this.id = id;
        setCodigo(codigo);
        setCategoria(categoria);
        setDescricao(descricao);
        setMensagem(mensagem);
        setTipo(tipo);
        aprovado = tipo == TipoValidacao.SUCESSO;
    }

    public ValidacaoECF(ValidacaoECF original) {
        this(original.id, original.codigo, original.categoria, original.descricao, original.mensagem, original.tipo);
        aprovado = original.aprovado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo == null ? "" : codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria == null ? "" : categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao == null ? "" : descricao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem == null ? "" : mensagem;
    }

    public TipoValidacao getTipo() {
        return tipo;
    }

    public void setTipo(TipoValidacao tipo) {
        this.tipo = tipo == null ? TipoValidacao.SUCESSO : tipo;
    }

    public boolean isAprovado() {
        return aprovado;
    }

    public void setAprovado(boolean aprovado) {
        this.aprovado = aprovado;
    }

    @Override
    public String toString() {
        return codigo + " - " + descricao + " (" + tipo + ")";
    }

}
