package model;

public class CentroCustos {
	private String codigo;
	private String descricao;
	private Integer idCentroSuperior;
	private Integer nivel;
	private Situacao situacao;
	private Integer idCentroCustos;
	
	public enum Situacao {ATIVO, INATIVO}
	public CentroCustos() {};
	
	public CentroCustos(String codigo, String descricao, Integer idCentroSuperior, Integer nivel, Situacao situacao) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.idCentroSuperior = idCentroSuperior;
		this.nivel = nivel;
		this.situacao = situacao;
	}
	
	public CentroCustos(String codigo, String descricao, Integer idCentroSuperior, Integer nivel, Situacao situacao,
			Integer idCentroCustos) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.idCentroSuperior = idCentroSuperior;
		this.nivel = nivel;
		this.situacao = situacao;
		this.idCentroCustos = idCentroCustos;
	}
	public Integer getIdCentroCustos() {
		return idCentroCustos;
	}

	public void setIdCentroCustos(Integer idCentroCustos) {
		this.idCentroCustos = idCentroCustos;
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

	public Integer getIdCentroSuperior() {
		return idCentroSuperior;
	}

	public void setIdCentroSuperior(Integer idCentroSuperior) {
		this.idCentroSuperior = idCentroSuperior;
	}

	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

}
