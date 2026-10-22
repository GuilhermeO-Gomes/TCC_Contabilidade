package model;

public class BuscarConta {
	private String codigo;
	private String descricao;
	private Long idConta;
	
	public BuscarConta() {}
	
	public BuscarConta(Long idConta, String codigo, String descricao) {
		this.idConta = idConta;
		this.codigo = codigo;
		this.descricao = descricao;
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
	
}
