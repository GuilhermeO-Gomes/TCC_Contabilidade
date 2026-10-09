package model;

import java.math.BigDecimal;
import java.sql.Date;

public class LivroRazao {
	private Date data;
	private String documento;
	private String historico;
	private BigDecimal debito;
	private BigDecimal credito;
	private BigDecimal saldo;
	private NaturezaSaldo naturezaDoSaldo;
	private Integer idLancamento;
	//D para Devedor, C para Credor. A ideia é juntar o Saldo com uma letra que 
	// represente a natureza do saldo, mas juntar SOMENTE na hora de mostrar os dados na JTable
	//No BD, é separado
	public enum NaturezaSaldo {DEVEDOR, CREDOR, ZERO}
	
	 public LivroRazao() {
	    }

	    public LivroRazao(Integer idLancamento, Date data, String documento, String historico, BigDecimal debito, BigDecimal credito, BigDecimal saldo, 
	    		NaturezaSaldo naturezaDoSaldo) {
	        this.idLancamento = idLancamento;
	    	this.data = data;
	        this.documento = documento;
	        this.historico = historico;
	        this.debito = debito;
	        this.credito = credito;
	        this.saldo = saldo;
	        this.naturezaDoSaldo = naturezaDoSaldo;
	    }
	public Integer getIdLancamento() {
			return idLancamento;
		}

		public void setIdLancamento(Integer idLancamento) {
			this.idLancamento = idLancamento;
		}

	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getHistorico() {
		return historico;
	}
	public void setHistorico(String historico) {
		this.historico = historico;
	}
	public BigDecimal getDebito() {
		return debito;
	}
	public void setDebito(BigDecimal debito) {
		this.debito = debito;
	}
	public BigDecimal getCredito() {
		return credito;
	}
	public void setCredito(BigDecimal credito) {
		this.credito = credito;
	}
	public BigDecimal getSaldo() {
		return saldo;
	}
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	public NaturezaSaldo getNaturezaDoSaldo() {
		return naturezaDoSaldo;
	}
	public void setNaturezaDoSaldo(NaturezaSaldo naturezaDoSaldo) {
		this.naturezaDoSaldo = naturezaDoSaldo;
	};
	
	
}
