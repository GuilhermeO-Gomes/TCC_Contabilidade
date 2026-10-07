package view;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import model.DadosContabeis;
import view.auditoria.TelaAuditoriaDigital;
import view.dashboard.TelaDashboard;
import view.diario.TelaLivroDiario;
import view.ecf.TelaGeracaoECF;

public class TelaPrincipal extends JPanel {
    private static final long serialVersionUID = 1L;
    private final DadosContabeis dados;
    private final TelaDashboard dashboard;
    private final TelaLivroDiario diario;
    private final TelaAuditoriaDigital auditoria;
    private final TelaGeracaoECF ecf;
    private final JTabbedPane paginas = new JTabbedPane();

    public TelaPrincipal() {
        this(new DadosContabeis());
    }

    public TelaPrincipal(DadosContabeis dados) {
        this.dados = dados;
        setLayout(new BorderLayout(8, 8));
        JLabel lbl_titulo = new JLabel("Testando as telas");
        lbl_titulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        add(lbl_titulo, BorderLayout.NORTH);
        dashboard = new TelaDashboard(dados);
        ecf = new TelaGeracaoECF(dados);
        auditoria = new TelaAuditoriaDigital(dados, () -> dashboard.atualizar_tela());
        diario = new TelaLivroDiario(dados, () -> atualizar_modulo());
        paginas.setName("principal.paginas");
        paginas.addTab("Visão Contábil", dashboard);
        paginas.addTab("Livro Diário", diario);
        paginas.addTab("Auditoria Digital", auditoria);
        paginas.addTab("Geração da ECF", ecf);
        paginas.addChangeListener(e -> {
            if (paginas.getSelectedComponent() == dashboard) {
                dashboard.atualizar_tela();
            } else if (paginas.getSelectedComponent() == diario) {
                diario.atualizar_tabela();
            } else if (paginas.getSelectedComponent() == auditoria) {
                auditoria.atualizar_tabela();
            } else if (paginas.getSelectedComponent() == ecf) {
                ecf.atualizar_dados();
            }
        });
        add(paginas, BorderLayout.CENTER);
    }

    private void atualizar_modulo() {
        dados.atualizarInconsistencias(java.time.LocalDate.MIN, java.time.LocalDate.MAX);
        dashboard.atualizar_tela();
        auditoria.atualizar_tabela();
        ecf.atualizar_dados();
    }
}
