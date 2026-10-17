package model;

public class PlanoContas {
  private long id;
  private long empresaId;
  private String codigo = "";
  private String descricao = "";
  private TipoConta tipo = TipoConta.ANALITICA;
  private NaturezaConta natureza = NaturezaConta.DEVEDORA;
  private boolean ativo = true;
  private Long contaPaiId;
  private Long codigoAglutinacaoId;

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

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDescricao() {
    return descricao;
  }

  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  public TipoConta getTipo() {
    return tipo;
  }

  public void setTipo(TipoConta tipo) {
    this.tipo = tipo;
  }

  public NaturezaConta getNatureza() {
    return natureza;
  }

  public void setNatureza(NaturezaConta natureza) {
    this.natureza = natureza;
  }

  public boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(boolean ativo) {
    this.ativo = ativo;
  }

  public Long getContaPaiId() {
    return contaPaiId;
  }

  public void setContaPaiId(Long contaPaiId) {
    this.contaPaiId = contaPaiId;
  }

  public Long getCodigoAglutinacaoId() {
    return codigoAglutinacaoId;
  }

  public void setCodigoAglutinacaoId(Long codigoAglutinacaoId) {
    this.codigoAglutinacaoId = codigoAglutinacaoId;
  }

  @Override
  public String toString() {
    return codigo + " - " + descricao;
  }
}
