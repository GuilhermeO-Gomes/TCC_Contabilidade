package model;

import java.math.BigDecimal;



public class Balancete {
	private Integer idConta;
	private String conta;
	private String descricao;
	private BigDecimal debito;
	private BigDecimal credito;
	private BigDecimal saldo;
	private NaturezaSaldo naturezaDoSaldo;
	//D para Devedor, C para Credor. A ideia é juntar o Saldo com uma letra que 
	// represente a natureza do saldo, mas juntar SOMENTE na hora de mostrar os dados na JTable
	//No BD, é separado
	public enum NaturezaSaldo {DEVEDOR, CREDOR, ZERO}
	
	public Balancete() {}
	
	public Balancete(Integer idConta, String conta, String descricao, BigDecimal debito, BigDecimal credito, BigDecimal saldo, 
			NaturezaSaldo naturezaDoSaldo) {
		this.idConta = idConta;
		this.conta = conta;
		this.descricao = descricao;
		this.debito = debito;
		this.credito = credito;
		this.saldo = saldo;
		this.naturezaDoSaldo = naturezaDoSaldo;
	}

	public Integer getIdConta() {
		return idConta;
	}

	public void setIdConta(Integer idConta) {
		this.idConta = idConta;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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
	}
	
}
