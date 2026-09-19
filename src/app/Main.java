package app;

import javax.swing.SwingUtilities;

import view.TelaHistoricoPadrao;
import view.TelaPrincipal;

public class Main {
	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                TelaPrincipal tela = new TelaPrincipal();
	                tela.setVisible(true);
	            }
	        });
	    }
}
