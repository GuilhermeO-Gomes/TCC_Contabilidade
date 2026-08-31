package app;

import javax.swing.SwingUtilities;

import view.TelaPlanoDeContas;

public class Main {
	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                TelaPlanoDeContas tela = new TelaPlanoDeContas();
	                tela.setVisible(true);
	            }
	        });
	    }
}
