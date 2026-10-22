package model;

import java.math.BigDecimal;
import java.time.LocalDate;



public class AtualizarCustos {
	private LocalDate data;
	//Mês e Ano
	private LocalDate periodo;
	private BigDecimal custoAnterior;
	private BigDecimal custoNovo;
	//Variação = custoNovo - CustoAnterior
	private BigDecimal variacao;
	private Long idCentroCustos;
	private Long idAtualizacao;
	
	 public AtualizarCustos() {
	    }

	    public AtualizarCustos(Long idCentroCustos, LocalDate data, LocalDate periodo, BigDecimal custoAnterior, BigDecimal custoNovo) {
	        this.idCentroCustos = idCentroCustos;
	    	this.data = data;
	        this.periodo = periodo;
	        this.custoAnterior = custoAnterior;
	        this.custoNovo = custoNovo;
	        this.variacao = custoNovo.subtract(custoAnterior);
	    }
	    public AtualizarCustos(Long idCentroCustos, Long idAtualizacao, LocalDate data, LocalDate periodo, BigDecimal custoAnterior, BigDecimal custoNovo, BigDecimal variacao) {
	        this.idCentroCustos = idCentroCustos;
	        this.idAtualizacao = idAtualizacao;
	    	this.data = data;
	        this.periodo = periodo;
	        this.custoAnterior = custoAnterior;
	        this.custoNovo = custoNovo;
	        this.variacao = variacao;
	    }
	public Long getIdCentroCusto() {
			return idCentroCustos;
		}

		public void setIdCentroCusto(Long idCentroCusto) {
			this.idCentroCustos = idCentroCusto;
		}

		public Long getIdAtualizacao() {
			return idAtualizacao;
		}

		public void setIdAtualizacao(Long idAtualizacao) {
			this.idAtualizacao = idAtualizacao;
		}

	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public LocalDate getPeriodo() {
		return periodo;
	}
	public void setPeriodo(LocalDate periodo) {
		this.periodo = periodo;
	}
	public BigDecimal getCustoAnterior() {
		return custoAnterior;
	}
	public void setCustoAnterior(BigDecimal custoAnterior) {
		this.custoAnterior = custoAnterior;
	}
	public BigDecimal getCustoNovo() {
		return custoNovo;
	}
	public void setCustoNovo(BigDecimal custoNovo) {
		this.custoNovo = custoNovo;
	}
	public BigDecimal getVariacao() {
		return variacao;
	}
	public void setVariacao(BigDecimal variacao) {
		this.variacao = variacao;
	}
	
	
}
