package model;

import java.util.Date;
import java.math.BigDecimal;

public class AjusteExercicio {
	public Date data;
	public String exercicio;
	public String periodo;
	public String documento;
	public String contaDebito;
	public String contaCredito;
	public BigDecimal valor;
	public String historico;
	public String status;
	
	public AjusteExercicio() {
		super();
	}
	
	public AjusteExercicio(Date data, String exercicio, String periodo, String documento, String contaDebito,
			String contaCredito, BigDecimal valor, String historico, String status) {
		super();
		this.data = data;
		this.exercicio = exercicio;
		this.periodo = periodo;
		this.documento = documento;
		this.contaDebito = contaDebito;
		this.contaCredito = contaCredito;
		this.valor = valor;
		this.historico = historico;
		this.status = status;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getExercicio() {
		return exercicio;
	}

	public void setExercicio(String exercicio) {
		this.exercicio = exercicio;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getContaDebito() {
		return contaDebito;
	}

	public void setContaDebito(String contaDebito) {
		this.contaDebito = contaDebito;
	}

	public String getContaCredito() {
		return contaCredito;
	}

	public void setContaCredito(String contaCredito) {
		this.contaCredito = contaCredito;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}