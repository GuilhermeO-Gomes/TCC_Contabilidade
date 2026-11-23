package view;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import view.auditoria.TelaAuditoriaDigital;
import view.dashboard.TelaDashboard;
import view.diario.TelaLivroDiario;
import view.ecf.TelaGeracaoECF;

public class TelaPrincipal extends JPanel {
  private static final long serialVersionUID = 1L;

  public TelaPrincipal() {
    super(new BorderLayout(8, 8));
    JLabel titulo = new JLabel("Contabilidade | Protótipo de telas");
    titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
    add(titulo, BorderLayout.NORTH);

    JTabbedPane paginas = new JTabbedPane();
    paginas.setName("principal.paginas");
    paginas.addTab("Visão Contábil", new TelaDashboard());
    paginas.addTab("Livro Diário", new TelaLivroDiario());
    paginas.addTab("Auditoria Digital", new TelaAuditoriaDigital());
    paginas.addTab("Geração da ECF", new TelaGeracaoECF());
    add(paginas, BorderLayout.CENTER);
  }
}
