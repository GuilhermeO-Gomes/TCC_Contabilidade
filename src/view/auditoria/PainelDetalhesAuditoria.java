package view.auditoria;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.InconsistenciaAuditoria;
import view.ComponentesContabeis;

public class PainelDetalhesAuditoria extends JPanel {
    private static final long serialVersionUID = 1L;

    public PainelDetalhesAuditoria(InconsistenciaAuditoria achado, Runnable aoAlterar) {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(680, 480));
        JPanel jp_dados = new JPanel(new GridBagLayout());
        jp_dados.setBorder(BorderFactory.createTitledBorder("Detalhes da auditoria demonstrativa"));
        ComponentesContabeis.adicionar_componente(0, jp_dados, "Código:", new JLabel(achado.getCodigo()));
        ComponentesContabeis.adicionar_componente(1, jp_dados, "Severidade:", new JLabel(achado.getSeveridade().toString()));
        ComponentesContabeis.adicionar_componente(2, jp_dados, "Data:", new JLabel(achado.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        ComponentesContabeis.adicionar_componente(3, jp_dados, "Conta:", new JLabel(achado.getConta()));
        ComponentesContabeis.adicionar_componente(4, jp_dados, "Lançamento:", new JLabel(String.valueOf(achado.getLancamentoId())));
        ComponentesContabeis.adicionar_componente(5, jp_dados, "Descrição:", ComponentesContabeis.texto(achado.getDescricao()));
        ComponentesContabeis.adicionar_componente(6, jp_dados, "Recomendação:", ComponentesContabeis.texto(achado.getRecomendacao()));
        JLabel lbl_status = new JLabel(achado.getStatus().toString());
        lbl_status.setName("auditoria.detalhes.status");
        ComponentesContabeis.adicionar_componente(7, jp_dados, "Status:", lbl_status);
        add(jp_dados, BorderLayout.CENTER);
        add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("auditoria.detalhes.revisar", "Marcar revisado", () -> {
                    achado.marcarRevisado();
                    lbl_status.setText(achado.getStatus().toString());
                    aoAlterar.run();
                }),
                ComponentesContabeis.botao("auditoria.detalhes.ignorar", "Ignorar", () -> {
                    achado.ignorar();
                    lbl_status.setText(achado.getStatus().toString());
                    aoAlterar.run();
                }),
                ComponentesContabeis.botao("auditoria.detalhes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))), BorderLayout.SOUTH);
    }
}
