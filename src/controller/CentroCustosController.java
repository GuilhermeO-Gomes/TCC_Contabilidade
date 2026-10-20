package controller;

import java.awt.Window;


import view.TelaBuscarCentro;

public class CentroCustosController {
	public void abrirBuscaCentro(Window janela_pai) {
		TelaBuscarCentro dialog = new TelaBuscarCentro(janela_pai);
	    dialog.setVisible(true);
	};
}
