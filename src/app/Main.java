package app;

import javax.swing.SwingUtilities;

import view.TelaHistoricoPadrao;

public class Main {
	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                TelaHistoricoPadrao tela = new TelaHistoricoPadrao();
	                tela.setVisible(true);
	            }
	        });
	    }
}
