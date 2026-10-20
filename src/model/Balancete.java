package model;

import java.math.BigDecimal;



public class Balancete {
	private Long idConta;
	private String codigoConta;
	private String descricao;
	private BigDecimal debito;
	private BigDecimal credito;
	private BigDecimal saldo;
	private BigDecimal saldoInicial;
	private NaturezaSaldo naturezaDoSaldo;
	public enum NaturezaSaldo {DEVEDOR, CREDOR, ZERO}
	
	public Balancete() {}
	
	public Balancete(Long idConta, String codigoConta, String descricao, BigDecimal debito, BigDecimal credito, BigDecimal saldo, 
			BigDecimal saldoInicial, NaturezaSaldo naturezaDoSaldo) {
		this.idConta = idConta;
		this.codigoConta = codigoConta;
		this.descricao = descricao;
		this.debito = debito;
		this.credito = credito;
		this.saldo = saldo;
		this.saldoInicial = saldoInicial;
		this.naturezaDoSaldo = naturezaDoSaldo;
	}

	public Long getIdConta() {
		return idConta;
	}

	public BigDecimal getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(BigDecimal saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public void setIdConta(Long idConta) {
		this.idConta = idConta;
	}

	public String getCodigoConta() {
		return codigoConta;
	}

	public void setCodigoConta(String codigoConta) {
		this.codigoConta = codigoConta;
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
