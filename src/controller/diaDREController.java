package controller;

import util.Validador;
import view.diaDRE;

public class diaDREController {
	 private final diaDRE tela;
	 
	 public diaDREController(diaDRE t) {
		 tela = t;
	 }
	 
	 
	 private void validar() {
		 if (
		 Validador.vazio(tela.getDescricao().getText())
		 ) throw new IllegalArgumentException("Informe o valor");
		 	 
	 }
	 
	
	 
	 
 }