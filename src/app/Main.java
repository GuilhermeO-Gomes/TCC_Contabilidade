package app;

import javax.swing.SwingUtilities;

import view.TelaPrincipalContabilidade;

public class Main {
	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                TelaPrincipalContabilidade tela = new TelaPrincipalContabilidade();
	                tela.setVisible(true);
	            }
	        });
	    }
}
