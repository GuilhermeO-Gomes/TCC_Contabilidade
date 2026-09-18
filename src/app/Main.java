package app;

import javax.swing.SwingUtilities;

import view.TelaPlanoDeContas;
import view.TelaTeste;

public class Main {
	
	 public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                TelaTeste tela = new TelaTeste();
	                tela.setVisible(true);
	            }
	        });
	    }
}
