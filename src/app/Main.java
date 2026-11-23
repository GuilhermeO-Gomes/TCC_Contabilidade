package app;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import view.TelaPrincipal;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      JFrame janela = new JFrame("Contabilidade");
      janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      janela.setContentPane(new TelaPrincipal());
      janela.setSize(1100, 800);
      janela.setMinimumSize(new Dimension(950, 740));
      janela.setLocationRelativeTo(null);
      janela.setVisible(true);
    });
  }
}
