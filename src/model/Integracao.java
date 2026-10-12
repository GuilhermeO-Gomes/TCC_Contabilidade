package model;

import java.util.Date;
import java.math.BigDecimal;

public class Integracao {
	public Date data;
	public String modulo;
	public String documento;
	public String transacao;
	public BigDecimal valor;
	public String status;
	
	public Integracao() {
		super();
	}
	
	public Integracao(Date data, String modulo, String documento, String transacao, BigDecimal valor, String status) {
		super();
		this.data = data;
		this.modulo = modulo;
		this.documento = documento;
		this.transacao = transacao;
		this.valor = valor;
		this.status = status;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getModulo() {
		return modulo;
	}

	public void setModulo(String modulo) {
		this.modulo = modulo;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getTransacao() {
		return transacao;
	}

	public void setTransacao(String transacao) {
		this.transacao = transacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
	
}