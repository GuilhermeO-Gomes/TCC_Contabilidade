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

    SwingUtilities.invokeLater(
        () -> {
          model.DadosContabeis dados = new model.DadosContabeis();
          ComponentesInicio.abrir(dados);
        });
  }

  private static class ComponentesInicio {
    static void abrir(model.DadosContabeis dados) {
      javax.swing.JLabel aviso = new javax.swing.JLabel("Conectando ao banco...");
      JFrame janela = new JFrame("Contabilidade");
      janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      janela.setContentPane(aviso);
      janela.setSize(1100, 800);
      janela.setMinimumSize(new java.awt.Dimension(950, 740));
      janela.setLocationRelativeTo(null);
      janela.setVisible(true);
      new javax.swing.SwingWorker<String, Void>() {
        protected String doInBackground() {
          return dados.getServico().empresa();
        }

        protected void done() {
          try {
            janela.setTitle("Contabilidade — " + get());
            janela.setContentPane(new TelaPrincipal(dados));
            janela.revalidate();
          } catch (Exception erro) {
            Throwable causa =
                erro instanceof java.util.concurrent.ExecutionException ? erro.getCause() : erro;
            aviso.setText(
                "Contexto não disponível. Configure conexão, empresa e usuário e reabra.");
            javax.swing.JOptionPane.showMessageDialog(
                janela,
                causa.getMessage(),
                "Não foi possível iniciar",
                javax.swing.JOptionPane.WARNING_MESSAGE);
          }
        }
      }.execute();
    }
  }
}
