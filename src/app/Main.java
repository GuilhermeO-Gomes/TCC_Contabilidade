package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.TelaTeste;

public class Main {
	
	 public static void main(String[] args) {
		 
		 try {
		        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		 
	        SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                TelaTeste tela = new TelaTeste();
	                tela.setVisible(true);
	            }
	        });
	    }
}
