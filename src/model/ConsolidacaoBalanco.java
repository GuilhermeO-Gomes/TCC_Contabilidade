package model;

import java.util.Date;
import java.math.BigDecimal;

public class ConsolidacaoBalanco {
	public String periodo;
	public int ano;
	public String planoContas;
	
	public String conta;
	public String descricao;
	public String controladora;
	public String controlada;
	public BigDecimal eliminacao;
	public BigDecimal saldoConsolidado;
	
	public ConsolidacaoBalanco() {
		super();
	}
	
	public ConsolidacaoBalanco(String periodo, int ano, String planoContas, String conta, String descricao,
			String controladora, String controlada, BigDecimal eliminacao, BigDecimal saldoConsolidado) {
		super();
		this.periodo = periodo;
		this.ano = ano;
		this.planoContas = planoContas;
		this.conta = conta;
		this.descricao = descricao;
		this.controladora = controladora;
		this.controlada = controlada;
		this.eliminacao = eliminacao;
		this.saldoConsolidado = saldoConsolidado;
	}

	public String getPeriodo() {
		return periodo;
	}

	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}

	public String getPlanoContas() {
		return planoContas;
	}

	public void setPlanoContas(String planoContas) {
		this.planoContas = planoContas;
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

	public String getControladora() {
		return controladora;
	}

	public void setControladora(String controladora) {
		this.controladora = controladora;
	}

	public String getControlada() {
		return controlada;
	}

	public void setControlada(String controlada) {
		this.controlada = controlada;
	}

	public BigDecimal getEliminacao() {
		return eliminacao;
	}

	public void setEliminacao(BigDecimal eliminacao) {
		this.eliminacao = eliminacao;
	}

	public BigDecimal getSaldoConsolidado() {
		return saldoConsolidado;
	}

	public void setSaldoConsolidado(BigDecimal saldoConsolidado) {
		this.saldoConsolidado = saldoConsolidado;
	}
	
	
	
}