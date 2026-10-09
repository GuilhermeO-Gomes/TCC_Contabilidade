package model;




public class Rateio {
	private Integer idRateio;
	private String nome;
	private Integer idConta;
	private CriterioRateio criterio;
	private Situacao situacao;
	
	public enum Situacao{ATIVO, INATIVO};
	public enum CriterioRateio{PERCENTUAL};
	
	
	public Rateio() {};
	public Rateio(Integer idRateio, String nome, CriterioRateio criterio, Situacao situacao) {
		this.idRateio = idRateio;
		this.nome = nome;
		this.criterio = criterio;
		this.situacao = situacao;
	}
	
	public Rateio(String nome, CriterioRateio criterio, Situacao situacao) {
		this.nome = nome;
		this.criterio = criterio;
		this.situacao = situacao;
	}
	public Integer getIdRateio() {
		return idRateio;
	}
	public void setIdRateio(Integer idRateio) {
		this.idRateio = idRateio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getIdConta() {
		return idConta;
	}
	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}
	public CriterioRateio getCriterio() {
		return criterio;
	}
	public void setCriterio(CriterioRateio criterio) {
		this.criterio = criterio;
	}
	public Situacao getSituacao() {
		return situacao;
	}
	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}
	
	
	
}
