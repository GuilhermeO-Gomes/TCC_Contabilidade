package controller;

import util.Validador;
import view.diaBalancoPatrimonialItem;

public class diaBalancoPatrimonialItemController {
	 private final diaBalancoPatrimonialItem tela;
	 
	 public diaBalancoPatrimonialItemController(diaBalancoPatrimonialItem t) {
		 tela = t;
	 }
	 
	 
	 private void validar() {
		 if (
		 Validador.vazio(tela.getOrdem().getText())
		 ) throw new IllegalArgumentException("Informe a ordem");
		 
		 if (
		Validador.vazio(tela.getCodigoAglutinacaoSelecionado().getSelectedItem())
		) throw new IllegalArgumentException("Informe o id do codigo aglutinacao");
		 
		 if (
		Validador.vazio(tela.getItemSuperiorSelecionado().getSelectedItem())
		) throw new IllegalArgumentException("Informe o id do item superior");
		 	 
	 }
	 
	
	 
	 
 }