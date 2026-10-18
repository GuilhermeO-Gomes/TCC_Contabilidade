package controller;

import java.awt.Window;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import dao.PlanoContasDAO;
import model.PlanoContas;
import view.TelaAdicionarConta;
import view.TelaPlanoDeContas;

public class PlanoContasController {

	public void abrirAdicionarConta(Window janela_pai) {
		TelaAdicionarConta dialog = new TelaAdicionarConta(janela_pai);
	    dialog.setVisible(true);
	} 
	
}