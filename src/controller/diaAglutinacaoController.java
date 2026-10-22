package controller;

import util.Validador;
import view.diaAglutinacao;

public class diaAglutinacaoController {
	 private final diaAglutinacao tela;
	 
	 public diaAglutinacaoController(diaAglutinacao t) {
		 tela = t;
	 }
	 
	 
	 private void validar() {
		 if (
		 Validador.vazio(tela.getValor().getText())
		 ) throw new IllegalArgumentException("Informe o valor");
		 
		 if (
		Validador.vazio(tela.getDescricao().getText())
		) throw new IllegalArgumentException("Informe a descrição");
				 
	 }
	 
	
	 
	 
 }