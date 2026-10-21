package controller;

import java.awt.Window;

import view.TelaAdicionarCentro;
import view.TelaBuscarConta;

public class RateioController {
	public void abrirBuscaConta (Window janela_pai) {
		TelaBuscarConta dialog = new TelaBuscarConta(janela_pai);
		dialog.setVisible(true);
	} 
	public void abrirAdicionarCentro (Window janela_pai) {
		TelaAdicionarCentro dialog = new TelaAdicionarCentro(janela_pai);
		dialog.setVisible(true);
	}
}
