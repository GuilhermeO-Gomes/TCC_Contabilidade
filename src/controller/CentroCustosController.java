package controller;

import java.awt.Window;

import view.TelaAdicionarCustos;
import view.TelaAtualizarCustos;
import view.TelaBuscarCentro;
import view.TelaRateio;

public class CentroCustosController {
	public void abrirBuscaCentro(Window janela_pai) {
		TelaBuscarCentro dialog = new TelaBuscarCentro(janela_pai);
	    dialog.setVisible(true);
	};
	
	public void abrirAdicionarCustos(Window janela_pai) {
		TelaAdicionarCustos dialog = new TelaAdicionarCustos(janela_pai);
	    dialog.setVisible(true);
	};
	
	public void abrirAtualizarCustos(Window janela_pai) {
		TelaAtualizarCustos dialog = new TelaAtualizarCustos(janela_pai);
	    dialog.setVisible(true);
	};
	
	public void abrirRateio(Window janela_pai) {
		TelaRateio dialog = new TelaRateio(janela_pai);
	    dialog.setVisible(true);
	};
	
	public void abrirLivroRazao(Window janela_pai) {
		TelaBuscarCentro dialog = new TelaBuscarCentro(janela_pai);
	    dialog.setVisible(true);
	};
}
