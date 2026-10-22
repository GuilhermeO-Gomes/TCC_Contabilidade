package controller;

import util.Validador;
import view.diaBalancoPatrimonial;

public class diaBalancoPatrimonialController {
	 private final diaBalancoPatrimonial tela;
	 
	 public diaBalancoPatrimonialController(diaBalancoPatrimonial t) {
		 tela = t;
	 }
	 
	 
	 private void validar() {
		 if (
		 Validador.vazio(tela.getDescricao().getText())
		 ) throw new IllegalArgumentException("Informe o valor");
		 	 
	 }
	 
	
	 
	 
 }