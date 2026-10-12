package model;

import java.util.Date;

public class Ecd {
	private Date periodoGeracaoInicial;
	private Date periodoGeracaoFinal;
	private String formaEscrituracaoContabil;
	private int numeroOrdemDAL;
	private Date dataArquivamento;
	private Date dataConversao;
	private int numeroOrdemIA;
	private int codigoHash;
	
	private String destino;
	private String nomeEmpresarialDE;
	private String cnpjDE;
	private String cpfDE;
	private String 	inscricaoEstadualDE;
	private String inscricaoMunicipalDE;
	private String inscricaoSuframaDE;
	private String enderecoDE;
	private int numeroDE;
	private String complementoDE;
	private String bairroDE;
	private String municipioDE;
	private String cepDE;
	private String ufDE;
	private String telefoneDE;
	private String faxDE;
	
	private String nomeSE;
	private String cpf_cnpjSE;
	private String codigoQualificacaoSE;
	private String qualificacaoSE;
	private String crcSE;
	private String ufCrcSE;
	private int numeroSequencialSE;
	private Date validadeCrcSE;
	private String emailSE;
	private String telefoneSE;
	private Boolean responsavelAssinaturaSE;
	
	
	public Ecd() {
		super();
	}
	
	public Ecd(Date periodoGeracaoInicial, Date periodoGeracaoFinal, String formaEscrituracaoContabil,
			int numeroOrdemDAL, Date dataArquivamento, Date dataConversao, int numeroOrdemIA, int codigoHash,
			String destino, String nomeEmpresarialDE, String cnpjDE, String cpfDE, String inscricaoEstadualDE,
			String inscricaoMunicipalDE, String inscricaoSuframaDE, String enderecoDE, int numeroDE,
			String complementoDE, String bairroDE, String municipioDE, String cepDE, String ufDE, String telefoneDE,
			String faxDE, String nomeSE, String cpf_cnpjSE, String codigoQualificacaoSE, String qualificacaoSE,
			String crcSE, String ufCrcSE, int numeroSequencialSE, Date validadeCrcSE, String emailSE, String telefoneSE,
			Boolean responsavelAssinaturaSE) {
		super();
		this.periodoGeracaoInicial = periodoGeracaoInicial;
		this.periodoGeracaoFinal = periodoGeracaoFinal;
		this.formaEscrituracaoContabil = formaEscrituracaoContabil;
		this.numeroOrdemDAL = numeroOrdemDAL;
		this.dataArquivamento = dataArquivamento;
		this.dataConversao = dataConversao;
		this.numeroOrdemIA = numeroOrdemIA;
		this.codigoHash = codigoHash;
		this.destino = destino;
		this.nomeEmpresarialDE = nomeEmpresarialDE;
		this.cnpjDE = cnpjDE;
		this.cpfDE = cpfDE;
		this.inscricaoEstadualDE = inscricaoEstadualDE;
		this.inscricaoMunicipalDE = inscricaoMunicipalDE;
		this.inscricaoSuframaDE = inscricaoSuframaDE;
		this.enderecoDE = enderecoDE;
		this.numeroDE = numeroDE;
		this.complementoDE = complementoDE;
		this.bairroDE = bairroDE;
		this.municipioDE = municipioDE;
		this.cepDE = cepDE;
		this.ufDE = ufDE;
		this.telefoneDE = telefoneDE;
		this.faxDE = faxDE;
		this.nomeSE = nomeSE;
		this.cpf_cnpjSE = cpf_cnpjSE;
		this.codigoQualificacaoSE = codigoQualificacaoSE;
		this.qualificacaoSE = qualificacaoSE;
		this.crcSE = crcSE;
		this.ufCrcSE = ufCrcSE;
		this.numeroSequencialSE = numeroSequencialSE;
		this.validadeCrcSE = validadeCrcSE;
		this.emailSE = emailSE;
		this.telefoneSE = telefoneSE;
		this.responsavelAssinaturaSE = responsavelAssinaturaSE;
	}

	public Date getPeriodoGeracaoInicial() {
		return periodoGeracaoInicial;
	}

	public void setPeriodoGeracaoInicial(Date periodoGeracaoInicial) {
		this.periodoGeracaoInicial = periodoGeracaoInicial;
	}

	public Date getPeriodoGeracaoFinal() {
		return periodoGeracaoFinal;
	}

	public void setPeriodoGeracaoFinal(Date periodoGeracaoFinal) {
		this.periodoGeracaoFinal = periodoGeracaoFinal;
	}

	public String getFormaEscrituracaoContabil() {
		return formaEscrituracaoContabil;
	}

	public void setFormaEscrituracaoContabil(String formaEscrituracaoContabil) {
		this.formaEscrituracaoContabil = formaEscrituracaoContabil;
	}

	public int getNumeroOrdemDAL() {
		return numeroOrdemDAL;
	}

	public void setNumeroOrdemDAL(int numeroOrdemDAL) {
		this.numeroOrdemDAL = numeroOrdemDAL;
	}

	public Date getDataArquivamento() {
		return dataArquivamento;
	}

	public void setDataArquivamento(Date dataArquivamento) {
		this.dataArquivamento = dataArquivamento;
	}

	public Date getDataConversao() {
		return dataConversao;
	}

	public void setDataConversao(Date dataConversao) {
		this.dataConversao = dataConversao;
	}

	public int getNumeroOrdemIA() {
		return numeroOrdemIA;
	}

	public void setNumeroOrdemIA(int numeroOrdemIA) {
		this.numeroOrdemIA = numeroOrdemIA;
	}

	public int getCodigoHash() {
		return codigoHash;
	}

	public void setCodigoHash(int codigoHash) {
		this.codigoHash = codigoHash;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getNomeEmpresarialDE() {
		return nomeEmpresarialDE;
	}

	public void setNomeEmpresarialDE(String nomeEmpresarialDE) {
		this.nomeEmpresarialDE = nomeEmpresarialDE;
	}

	public String getCnpjDE() {
		return cnpjDE;
	}

	public void setCnpjDE(String cnpjDE) {
		this.cnpjDE = cnpjDE;
	}

	public String getCpfDE() {
		return cpfDE;
	}

	public void setCpfDE(String cpfDE) {
		this.cpfDE = cpfDE;
	}

	public String getInscricaoEstadualDE() {
		return inscricaoEstadualDE;
	}

	public void setInscricaoEstadualDE(String inscricaoEstadualDE) {
		this.inscricaoEstadualDE = inscricaoEstadualDE;
	}

	public String getInscricaoMunicipalDE() {
		return inscricaoMunicipalDE;
	}

	public void setInscricaoMunicipalDE(String inscricaoMunicipalDE) {
		this.inscricaoMunicipalDE = inscricaoMunicipalDE;
	}

	public String getInscricaoSuframaDE() {
		return inscricaoSuframaDE;
	}

	public void setInscricaoSuframaDE(String inscricaoSuframaDE) {
		this.inscricaoSuframaDE = inscricaoSuframaDE;
	}

	public String getEnderecoDE() {
		return enderecoDE;
	}

	public void setEnderecoDE(String enderecoDE) {
		this.enderecoDE = enderecoDE;
	}

	public int getNumeroDE() {
		return numeroDE;
	}

	public void setNumeroDE(int numeroDE) {
		this.numeroDE = numeroDE;
	}

	public String getComplementoDE() {
		return complementoDE;
	}

	public void setComplementoDE(String complementoDE) {
		this.complementoDE = complementoDE;
	}

	public String getBairroDE() {
		return bairroDE;
	}

	public void setBairroDE(String bairroDE) {
		this.bairroDE = bairroDE;
	}

	public String getMunicipioDE() {
		return municipioDE;
	}

	public void setMunicipioDE(String municipioDE) {
		this.municipioDE = municipioDE;
	}

	public String getCepDE() {
		return cepDE;
	}

	public void setCepDE(String cepDE) {
		this.cepDE = cepDE;
	}

	public String getUfDE() {
		return ufDE;
	}

	public void setUfDE(String ufDE) {
		this.ufDE = ufDE;
	}

	public String getTelefoneDE() {
		return telefoneDE;
	}

	public void setTelefoneDE(String telefoneDE) {
		this.telefoneDE = telefoneDE;
	}

	public String getFaxDE() {
		return faxDE;
	}

	public void setFaxDE(String faxDE) {
		this.faxDE = faxDE;
	}

	public String getNomeSE() {
		return nomeSE;
	}

	public void setNomeSE(String nomeSE) {
		this.nomeSE = nomeSE;
	}

	public String getCpf_cnpjSE() {
		return cpf_cnpjSE;
	}

	public void setCpf_cnpjSE(String cpf_cnpjSE) {
		this.cpf_cnpjSE = cpf_cnpjSE;
	}

	public String getCodigoQualificacaoSE() {
		return codigoQualificacaoSE;
	}

	public void setCodigoQualificacaoSE(String codigoQualificacaoSE) {
		this.codigoQualificacaoSE = codigoQualificacaoSE;
	}

	public String getQualificacaoSE() {
		return qualificacaoSE;
	}

	public void setQualificacaoSE(String qualificacaoSE) {
		this.qualificacaoSE = qualificacaoSE;
	}

	public String getCrcSE() {
		return crcSE;
	}

	public void setCrcSE(String crcSE) {
		this.crcSE = crcSE;
	}

	public String getUfCrcSE() {
		return ufCrcSE;
	}

	public void setUfCrcSE(String ufCrcSE) {
		this.ufCrcSE = ufCrcSE;
	}

	public int getNumeroSequencialSE() {
		return numeroSequencialSE;
	}

	public void setNumeroSequencialSE(int numeroSequencialSE) {
		this.numeroSequencialSE = numeroSequencialSE;
	}

	public Date getValidadeCrcSE() {
		return validadeCrcSE;
	}

	public void setValidadeCrcSE(Date validadeCrcSE) {
		this.validadeCrcSE = validadeCrcSE;
	}

	public String getEmailSE() {
		return emailSE;
	}

	public void setEmailSE(String emailSE) {
		this.emailSE = emailSE;
	}

	public String getTelefoneSE() {
		return telefoneSE;
	}

	public void setTelefoneSE(String telefoneSE) {
		this.telefoneSE = telefoneSE;
	}

	public Boolean getResponsavelAssinaturaSE() {
		return responsavelAssinaturaSE;
	}

	public void setResponsavelAssinaturaSE(Boolean responsavelAssinaturaSE) {
		this.responsavelAssinaturaSE = responsavelAssinaturaSE;
	}
	
	

}