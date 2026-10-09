package model;

public class PlanoContas {
    private String codigo;
    private String descricao;
    private Integer idContaSuperior; //Hierarquia de contas
    private Integer nivel; //
    private Integer idConta;
    public enum Grupo{ATIVO, PASSIVO, PATRIMONIO_LIQUIDO, RECEITA, CUSTO, DESPESA};
    public enum Natureza{DEVEDORA, CREDORA};
    public enum TipoConta{SINTETICA, ANALITICA};
    public enum Situacao{ATIVO, INATIVO};
    
    private Grupo grupo;
    private Natureza natureza;
    private TipoConta tipoConta;
    private Situacao situacao;

    public PlanoContas() {
    }

    public PlanoContas(String codigo, String descricao, Integer idContaSuperior,Integer nivel, Grupo grupo, 
    		Natureza natureza, TipoConta tipoConta, Situacao situacao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.idContaSuperior = idContaSuperior;
        this.nivel = nivel;
        this.grupo = grupo;
        this.tipoConta = tipoConta;
        this.natureza = natureza;
        this.situacao = situacao;
    }
    public PlanoContas(String codigo, String descricao, Integer idContaSuperior,Integer nivel, Integer idConta, Grupo grupo, 
    		Natureza natureza, TipoConta tipoConta, Situacao situacao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.idContaSuperior = idContaSuperior;
        this.nivel = nivel;
        this.idConta = idConta;
        this.grupo = grupo;
        this.tipoConta = tipoConta;
        this.natureza = natureza;
        this.situacao = situacao;
    }

	public Integer getIdConta() {
		return idConta;
	}

	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
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

	public Integer getIdContaSuperior() {
		return idContaSuperior;
	}

	public void setIdContaSuperior(Integer idContaSuperior) {
		this.idContaSuperior = idContaSuperior;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Grupo getGrupo() {
		return grupo;
	}

	public void setGrupo(Grupo grupo) {
		this.grupo = grupo;
	}

	public Natureza getNatureza() {
		return natureza;
	}

	public void setNatureza(Natureza natureza) {
		this.natureza = natureza;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}}
    

