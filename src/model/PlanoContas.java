package model;

public class PlanoContas {
    private String codigo;
    private String descricao;
    private Long idContaSuperior; //Hierarquia de contas
    private int nivel; //
    private Long idConta;
    public enum Grupo{ATIVO, PASSIVO, PATRIMONIO_LIQUIDO, RECEITA, CUSTO, DESPESA};
    public enum Natureza{DEVEDORA, CREDORA};
    public enum TipoConta{SINTETICA, ANALITICA};
    public enum Situacao{ATIVO, INATIVO};
    
    private Grupo grupo;
    private Natureza natureza;
    private TipoConta tipoConta;
    private Situacao situacao;
    private Long contaAglutinacaoID;

    public PlanoContas() {
    }

    public PlanoContas(String codigo, String descricao, Long idContaSuperior, int nivel, Grupo grupo, 
    		Natureza natureza, TipoConta tipoConta, Situacao situacao, Long contaAglutinacaoID) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.idContaSuperior = idContaSuperior;
        this.nivel = nivel;
        this.grupo = grupo;
        this.tipoConta = tipoConta;
        this.natureza = natureza;
        this.situacao = situacao;
        this.contaAglutinacaoID = contaAglutinacaoID;
    }
    public PlanoContas(String codigo, String descricao, Long idContaSuperior, int nivel, Long idConta, Grupo grupo, 
    		Natureza natureza, TipoConta tipoConta, Situacao situacao, Long contaAglutinacaoID) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.idContaSuperior = idContaSuperior;
        this.nivel = nivel;
        this.idConta = idConta;
        this.grupo = grupo;
        this.tipoConta = tipoConta;
        this.natureza = natureza;
        this.situacao = situacao;
        this.contaAglutinacaoID = contaAglutinacaoID;
    }

	public Long getContaAglutinacaoID() {
		return contaAglutinacaoID;
	}

	public void setContaAglutinacaoID(Long contaAglutinacaoID) {
		this.contaAglutinacaoID = contaAglutinacaoID;
	}

	public Long getIdConta() {
		return idConta;
	}

	public void setIdConta(Long idConta) {
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

	public Long getIdContaSuperior() {
		return idContaSuperior;
	}

	public void setIdContaSuperior(Long idContaSuperior) {
		this.idContaSuperior = idContaSuperior;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
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
    

