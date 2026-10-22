package controller;

import util.Validador;
import view.diaExerciciosSociais;

public class diaExerciciosSociaisController {
	 private final diaExerciciosSociais tela;
	 
	 public diaExerciciosSociaisController(diaExerciciosSociais t) {
		 tela = t;
	 }
	 
	 
	 private void validar() {
		 if (
		 Validador.vazio(tela.getAnoReferencia().getText())
		 ) throw new IllegalArgumentException("Informe o Ano Referencial");
		 
		 if (
		Validador.vazio(tela.getDataInicio().getText())
		) throw new IllegalArgumentException("Informe a data de inicio");
		 
		 if (
		Validador.vazio(tela.getDataFinal().getText())
		) throw new IllegalArgumentException("Informe a data final");
		 	 
	 }
	 
	
	 
	 
 }