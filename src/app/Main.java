package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.TelaPrincipal;

public class Main {
    public static void main(String[] args) {
    	
		 try {
		        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		 
        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Teste");
            janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            janela.setContentPane(new TelaPrincipal());
            janela.setSize(1100, 800);
            janela.setMinimumSize(new java.awt.Dimension(950, 740));
            janela.setLocationRelativeTo(null);
            janela.setVisible(true);
        });
    }
}
