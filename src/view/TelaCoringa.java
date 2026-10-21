package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
public class TelaCoringa extends JFrame {

	private static final long serialVersionUID = 1L;
	
	 public TelaCoringa() {
	        setTitle("Plano de Contas");
	        setSize(700, 450);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setLayout(new BorderLayout());

			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					sair();
				}
			});

			add(new TelaZeramentoContas());

	    }
	 
		private void sair() {
			if (JOptionPane.showConfirmDialog(this, "Deseja encerrar o sistema?", "Sair",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				dispose();
				System.exit(0);
			}
		}
	
}
