package model;

public class BuscarCentro {
	private Long idCentroCustos;
	private String codigo;
	private String descricao;
	
	public BuscarCentro() {}
	
	public BuscarCentro(Long idCentroCustos, String codigo, String descricao) {
		this.idCentroCustos = idCentroCustos;
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Long getIdCentroCustos() {
		return idCentroCustos;
	}

	public void setIdCentroCustos(Long idCentroCustos) {
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
	
}
