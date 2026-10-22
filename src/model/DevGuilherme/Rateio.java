package model.DevGuilherme;




public class Rateio {
	private Long idRateio;
	private String nome;
	private Long idConta; //
	private CriterioRateio criterio;
	private Situacao situacao;
	private Long empresaID;
	private boolean ativo;
	
	public enum Situacao{ATIVO, INATIVO};
	public enum CriterioRateio{PERCENTUAL};
	
	
	public Rateio() {};
	public Rateio(Long idRateio, String nome, CriterioRateio criterio, Situacao situacao, Long empresaID, boolean ativo) {
		this.idRateio = idRateio;
		this.nome = nome;
		this.criterio = criterio;
		this.situacao = situacao;
		this.empresaID = empresaID;
		this.ativo = ativo;
	}
	
	public Rateio(String nome, CriterioRateio criterio, Situacao situacao, Long empresaID, boolean ativo) {
		this.nome = nome;
		this.criterio = criterio;
		this.situacao = situacao;
		this.empresaID = empresaID;
		this.ativo = ativo;
	}
	
	public Rateio(Long idRateio, String nome, CriterioRateio criterio, Situacao situacao, Long empresaID, boolean ativo, Long idConta) {
		this.idRateio = idRateio;
		this.nome = nome;
		this.criterio = criterio;
		this.situacao = situacao;
		this.empresaID = empresaID;
		this.ativo = ativo;
		this.idConta = idConta;
	}
	
	public Rateio(String nome, CriterioRateio criterio, Situacao situacao, Long empresaID, boolean ativo, Long idConta) {
		this.nome = nome;
		this.criterio = criterio;
		this.situacao = situacao;
		this.empresaID = empresaID;
		this.ativo = ativo;
		this.idConta = idConta;
	}
	public Long getIdRateio() {
		return idRateio;
	}
	public Long getEmpresaID() {
		return empresaID;
	}
	public void setEmpresaID(Long empresaID) {
		this.empresaID = empresaID;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	public void setIdRateio(Long idRateio) {
		this.idRateio = idRateio;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Long getIdConta() {
		return idConta;
	}
	public void setIdConta(Long idConta) {
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
