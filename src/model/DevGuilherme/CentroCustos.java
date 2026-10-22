package model.DevGuilherme;

public class CentroCustos {
	private Long codigo;
	private Long empresaID;
	private Long responsavelUsuarioID;
	private String descricao;
	private Long idCentroSuperior;
	private int nivel;
	private Situacao situacao;
	private Long idCentroCustos;
	private boolean ativo;
	
	public enum Situacao {ATIVO, INATIVO}
	public CentroCustos() {};
	
	public CentroCustos(Long empresaID, Long responsavelUsuarioID, Long codigo, String descricao, Long idCentroSuperior, int nivel, Situacao situacao, boolean ativo) {
		this.empresaID = empresaID;
		this.responsavelUsuarioID = responsavelUsuarioID;
		this.codigo = codigo;
		this.descricao = descricao;
		this.idCentroSuperior = idCentroSuperior;
		this.nivel = nivel;
		this.situacao = situacao;
		this.ativo = ativo;
	}
	
	public CentroCustos(Long empresaID, Long responsavelUsuarioID, Long codigo, String descricao, Long idCentroSuperior, int nivel, Situacao situacao,
			Long idCentroCustos, boolean ativo) {
		this.empresaID = empresaID;
		this.responsavelUsuarioID = responsavelUsuarioID;
		this.codigo = codigo;
		this.descricao = descricao;
		this.idCentroSuperior = idCentroSuperior;
		this.nivel = nivel;
		this.situacao = situacao;
		this.idCentroCustos = idCentroCustos;
		this.ativo = ativo;
	}
	
	public CentroCustos(Long empresaID, Long codigo, String descricao, Long idCentroSuperior, int nivel, Situacao situacao,
			 boolean ativo) {
		this.empresaID = empresaID;
		this.codigo = codigo;
		this.descricao = descricao;
		this.idCentroSuperior = idCentroSuperior;
		this.nivel = nivel;
		this.situacao = situacao;
		this.ativo = ativo;
	}
	
	public CentroCustos(Long empresaID, Long codigo, String descricao, Long idCentroSuperior, int nivel, Situacao situacao,
			Long idCentroCustos, boolean ativo) {
		this.empresaID = empresaID;
		this.codigo = codigo;
		this.descricao = descricao;
		this.idCentroSuperior = idCentroSuperior;
		this.nivel = nivel;
		this.situacao = situacao;
		this.idCentroCustos = idCentroCustos;
		this.ativo = ativo;
	}
	public Long getIdCentroCustos() {
		return idCentroCustos;
	}

	public void setIdCentroCustos(Long idCentroCustos) {
		this.idCentroCustos = idCentroCustos;
	}

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Long getIdCentroSuperior() {
		return idCentroSuperior;
	}

	public void setIdCentroSuperior(Long idCentroSuperior) {
		this.idCentroSuperior = idCentroSuperior;
	}

	public int getNivel() {
		return nivel;
	}

	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public Long getEmpresaID() {
		return empresaID;
	}

	public void setEmpresaID(Long empresaID) {
		this.empresaID = empresaID;
	}

	public Long getResponsavelUsuarioID() {
		return responsavelUsuarioID;
	}

	public void setResponsavelUsuarioID(Long responsavelUsuarioID) {
		this.responsavelUsuarioID = responsavelUsuarioID;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

}
