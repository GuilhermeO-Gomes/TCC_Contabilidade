package model;

import java.math.BigDecimal;
import java.sql.Date;



public class AtualizarCustos {
	private Date data;
	//Mês e Ano
	private Date periodo;
	private BigDecimal custoAnterior;
	private BigDecimal custoNovo;
	//Variação = custoNovo - CustoAnterior
	private BigDecimal variacao;
	private Integer idCentroCustos;
	private Integer idAtualizacao;
	
	 public AtualizarCustos() {
	    }

	    public AtualizarCustos(Integer idCentroCustos, Date data, Date periodo, BigDecimal custoAnterior, BigDecimal custoNovo) {
	        this.idCentroCustos = idCentroCustos;
	    	this.data = data;
	        this.periodo = periodo;
	        this.custoAnterior = custoAnterior;
	        this.custoNovo = custoNovo;
	        this.variacao = custoNovo.subtract(custoAnterior);
	    }
	    public AtualizarCustos(Integer idCentroCustos,Integer idAtualizacao, Date data, Date periodo, BigDecimal custoAnterior, BigDecimal custoNovo, BigDecimal variacao) {
	        this.idCentroCustos = idCentroCustos;
	        this.idAtualizacao = idAtualizacao;
	    	this.data = data;
	        this.periodo = periodo;
	        this.custoAnterior = custoAnterior;
	        this.custoNovo = custoNovo;
	        this.variacao = variacao;
	    }
	public Integer getIdCentroCusto() {
			return idCentroCustos;
		}

		public void setIdCentroCusto(Integer idCentroCusto) {
			this.idCentroCustos = idCentroCusto;
		}

		public Integer getIdAtualizacao() {
			return idAtualizacao;
		}

		public void setIdAtualizacao(Integer idAtualizacao) {
			this.idAtualizacao = idAtualizacao;
		}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public Date getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Date periodo) {
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
