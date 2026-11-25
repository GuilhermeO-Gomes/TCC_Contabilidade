package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class EcdGeracao {

    public enum Finalidade {
        ORIGINAL,
        SUBSTITUTA
    }

    public enum Status {
        EM_PREPARACAO,
        GERADA,
        COM_ERROS,
        VALIDADA,
        ASSINADA,
        TRANSMITIDA,
        CANCELADA
    }

    private LocalDate dataInicio;
    private LocalDate dataFim;

    private Finalidade finalidade = Finalidade.ORIGINAL;
    private Long ecdSubstituidaId;

    private String tipoLivro;
    private String numeroLivro;
    private String versaoLayout;

    private String formaEscrituracao;
    private String naturezaLivro;
    private String numeroOrdemLivro;
    private LocalDate dataArquivamento;
    private LocalDate dataConversao;
    private String numeroOrdemInstrumentoAssociado;
    private String codigoHashInstrumentoAssociado;
    private String hashEcdSubstituida;

    private String codigoMunicipio;
    private String nire;
    private Boolean possuiNire;

    private String situacaoEspecial;
    private String situacaoInicioPeriodo;
    private String auditoriaIndependente;
    private String tipoEcd;
    private String codigoPlanoReferencial;
    private String cnpjCentralizadora;
    private String cnpjScp;
    private String moedaFuncional;
    private String escrituracaoConsolidada;
    private String modalidadeEscrituracao;
    private String mudancaPlanoContas;

    private Status status = Status.EM_PREPARACAO;

    private String nomeArquivo;
    private String hashArquivo;
    private String numeroRecibo;

    private Long geradoPor;
    private LocalDateTime geradoEm;
    private LocalDateTime transmitidoEm;

    private String observacao;
    private LocalDateTime criadoEm;
    
    
	public EcdGeracao(LocalDate dataInicio, LocalDate dataFim, Finalidade finalidade, Long ecdSubstituidaId,
			String tipoLivro, String numeroLivro, String versaoLayout, String formaEscrituracao, String naturezaLivro,
			String numeroOrdemLivro, LocalDate dataArquivamento, LocalDate dataConversao,
			String numeroOrdemInstrumentoAssociado, String codigoHashInstrumentoAssociado, String hashEcdSubstituida,
			String codigoMunicipio, String nire, Boolean possuiNire, String situacaoEspecial,
			String situacaoInicioPeriodo, String auditoriaIndependente, String tipoEcd, String codigoPlanoReferencial,
			String cnpjCentralizadora, String cnpjScp, String moedaFuncional, String escrituracaoConsolidada,
			String modalidadeEscrituracao, String mudancaPlanoContas, Status status, String nomeArquivo,
			String hashArquivo, String numeroRecibo, Long geradoPor, LocalDateTime geradoEm,
			LocalDateTime transmitidoEm, String observacao, LocalDateTime criadoEm) {
		super();
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.finalidade = finalidade;
		this.ecdSubstituidaId = ecdSubstituidaId;
		this.tipoLivro = tipoLivro;
		this.numeroLivro = numeroLivro;
		this.versaoLayout = versaoLayout;
		this.formaEscrituracao = formaEscrituracao;
		this.naturezaLivro = naturezaLivro;
		this.numeroOrdemLivro = numeroOrdemLivro;
		this.dataArquivamento = dataArquivamento;
		this.dataConversao = dataConversao;
		this.numeroOrdemInstrumentoAssociado = numeroOrdemInstrumentoAssociado;
		this.codigoHashInstrumentoAssociado = codigoHashInstrumentoAssociado;
		this.hashEcdSubstituida = hashEcdSubstituida;
		this.codigoMunicipio = codigoMunicipio;
		this.nire = nire;
		this.possuiNire = possuiNire;
		this.situacaoEspecial = situacaoEspecial;
		this.situacaoInicioPeriodo = situacaoInicioPeriodo;
		this.auditoriaIndependente = auditoriaIndependente;
		this.tipoEcd = tipoEcd;
		this.codigoPlanoReferencial = codigoPlanoReferencial;
		this.cnpjCentralizadora = cnpjCentralizadora;
		this.cnpjScp = cnpjScp;
		this.moedaFuncional = moedaFuncional;
		this.escrituracaoConsolidada = escrituracaoConsolidada;
		this.modalidadeEscrituracao = modalidadeEscrituracao;
		this.mudancaPlanoContas = mudancaPlanoContas;
		this.status = status;
		this.nomeArquivo = nomeArquivo;
		this.hashArquivo = hashArquivo;
		this.numeroRecibo = numeroRecibo;
		this.geradoPor = geradoPor;
		this.geradoEm = geradoEm;
		this.transmitidoEm = transmitidoEm;
		this.observacao = observacao;
		this.criadoEm = criadoEm;
	}



	public LocalDate getDataInicio() {
		return dataInicio;
	}


	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}


	public LocalDate getDataFim() {
		return dataFim;
	}


	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}


	public Finalidade getFinalidade() {
		return finalidade;
	}


	public void setFinalidade(Finalidade finalidade) {
		this.finalidade = finalidade;
	}


	public Long getEcdSubstituidaId() {
		return ecdSubstituidaId;
	}


	public void setEcdSubstituidaId(Long ecdSubstituidaId) {
		this.ecdSubstituidaId = ecdSubstituidaId;
	}


	public String getTipoLivro() {
		return tipoLivro;
	}


	public void setTipoLivro(String tipoLivro) {
		this.tipoLivro = tipoLivro;
	}


	public String getNumeroLivro() {
		return numeroLivro;
	}


	public void setNumeroLivro(String numeroLivro) {
		this.numeroLivro = numeroLivro;
	}


	public String getVersaoLayout() {
		return versaoLayout;
	}


	public void setVersaoLayout(String versaoLayout) {
		this.versaoLayout = versaoLayout;
	}


	public String getFormaEscrituracao() {
		return formaEscrituracao;
	}


	public void setFormaEscrituracao(String formaEscrituracao) {
		this.formaEscrituracao = formaEscrituracao;
	}


	public String getNaturezaLivro() {
		return naturezaLivro;
	}


	public void setNaturezaLivro(String naturezaLivro) {
		this.naturezaLivro = naturezaLivro;
	}


	public String getNumeroOrdemLivro() {
		return numeroOrdemLivro;
	}


	public void setNumeroOrdemLivro(String numeroOrdemLivro) {
		this.numeroOrdemLivro = numeroOrdemLivro;
	}


	public LocalDate getDataArquivamento() {
		return dataArquivamento;
	}


	public void setDataArquivamento(LocalDate dataArquivamento) {
		this.dataArquivamento = dataArquivamento;
	}


	public LocalDate getDataConversao() {
		return dataConversao;
	}


	public void setDataConversao(LocalDate dataConversao) {
		this.dataConversao = dataConversao;
	}


	public String getNumeroOrdemInstrumentoAssociado() {
		return numeroOrdemInstrumentoAssociado;
	}


	public void setNumeroOrdemInstrumentoAssociado(String numeroOrdemInstrumentoAssociado) {
		this.numeroOrdemInstrumentoAssociado = numeroOrdemInstrumentoAssociado;
	}


	public String getCodigoHashInstrumentoAssociado() {
		return codigoHashInstrumentoAssociado;
	}


	public void setCodigoHashInstrumentoAssociado(String codigoHashInstrumentoAssociado) {
		this.codigoHashInstrumentoAssociado = codigoHashInstrumentoAssociado;
	}


	public String getHashEcdSubstituida() {
		return hashEcdSubstituida;
	}


	public void setHashEcdSubstituida(String hashEcdSubstituida) {
		this.hashEcdSubstituida = hashEcdSubstituida;
	}


	public String getCodigoMunicipio() {
		return codigoMunicipio;
	}


	public void setCodigoMunicipio(String codigoMunicipio) {
		this.codigoMunicipio = codigoMunicipio;
	}


	public String getNire() {
		return nire;
	}


	public void setNire(String nire) {
		this.nire = nire;
	}


	public Boolean getPossuiNire() {
		return possuiNire;
	}


	public void setPossuiNire(Boolean possuiNire) {
		this.possuiNire = possuiNire;
	}


	public String getSituacaoEspecial() {
		return situacaoEspecial;
	}


	public void setSituacaoEspecial(String situacaoEspecial) {
		this.situacaoEspecial = situacaoEspecial;
	}


	public String getSituacaoInicioPeriodo() {
		return situacaoInicioPeriodo;
	}


	public void setSituacaoInicioPeriodo(String situacaoInicioPeriodo) {
		this.situacaoInicioPeriodo = situacaoInicioPeriodo;
	}


	public String getAuditoriaIndependente() {
		return auditoriaIndependente;
	}


	public void setAuditoriaIndependente(String auditoriaIndependente) {
		this.auditoriaIndependente = auditoriaIndependente;
	}


	public String getTipoEcd() {
		return tipoEcd;
	}


	public void setTipoEcd(String tipoEcd) {
		this.tipoEcd = tipoEcd;
	}


	public String getCodigoPlanoReferencial() {
		return codigoPlanoReferencial;
	}


	public void setCodigoPlanoReferencial(String codigoPlanoReferencial) {
		this.codigoPlanoReferencial = codigoPlanoReferencial;
	}


	public String getCnpjCentralizadora() {
		return cnpjCentralizadora;
	}


	public void setCnpjCentralizadora(String cnpjCentralizadora) {
		this.cnpjCentralizadora = cnpjCentralizadora;
	}


	public String getCnpjScp() {
		return cnpjScp;
	}


	public void setCnpjScp(String cnpjScp) {
		this.cnpjScp = cnpjScp;
	}


	public String getMoedaFuncional() {
		return moedaFuncional;
	}


	public void setMoedaFuncional(String moedaFuncional) {
		this.moedaFuncional = moedaFuncional;
	}


	public String getEscrituracaoConsolidada() {
		return escrituracaoConsolidada;
	}


	public void setEscrituracaoConsolidada(String escrituracaoConsolidada) {
		this.escrituracaoConsolidada = escrituracaoConsolidada;
	}


	public String getModalidadeEscrituracao() {
		return modalidadeEscrituracao;
	}


	public void setModalidadeEscrituracao(String modalidadeEscrituracao) {
		this.modalidadeEscrituracao = modalidadeEscrituracao;
	}


	public String getMudancaPlanoContas() {
		return mudancaPlanoContas;
	}


	public void setMudancaPlanoContas(String mudancaPlanoContas) {
		this.mudancaPlanoContas = mudancaPlanoContas;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public String getNomeArquivo() {
		return nomeArquivo;
	}


	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}


	public String getHashArquivo() {
		return hashArquivo;
	}


	public void setHashArquivo(String hashArquivo) {
		this.hashArquivo = hashArquivo;
	}


	public String getNumeroRecibo() {
		return numeroRecibo;
	}


	public void setNumeroRecibo(String numeroRecibo) {
		this.numeroRecibo = numeroRecibo;
	}


	public Long getGeradoPor() {
		return geradoPor;
	}


	public void setGeradoPor(Long geradoPor) {
		this.geradoPor = geradoPor;
	}


	public LocalDateTime getGeradoEm() {
		return geradoEm;
	}


	public void setGeradoEm(LocalDateTime geradoEm) {
		this.geradoEm = geradoEm;
	}


	public LocalDateTime getTransmitidoEm() {
		return transmitidoEm;
	}


	public void setTransmitidoEm(LocalDateTime transmitidoEm) {
		this.transmitidoEm = transmitidoEm;
	}


	public String getObservacao() {
		return observacao;
	}


	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}


	public LocalDateTime getCriadoEm() {
		return criadoEm;
	}


	public void setCriadoEm(LocalDateTime criadoEm) {
		this.criadoEm = criadoEm;
	}
    
	
    
    
}

