package controller;

import java.awt.Window;
import view.TelaAdicionarConta;
import view.TelaAtualizarConta;
import view.TelaLivroRazao;


public class PlanoContasController {

	public void abrirAdicionarConta(Window janela_pai) {
		TelaAdicionarConta dialog = new TelaAdicionarConta(janela_pai);
	    dialog.setVisible(true);
	} 
	
	public void abrirAtualizarConta(Window janela_pai) {
		TelaAtualizarConta dialog = new TelaAtualizarConta(janela_pai);
	    dialog.setVisible(true);
	} 
	
	public void abrirLivroRazao(Window janela_pai) {
		TelaLivroRazao dialog = new TelaLivroRazao(janela_pai);
	    dialog.setVisible(true);
	} 
}