package view.auditoria;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import view.ComponentesContabeis;

public class PainelDetalhesAuditoria extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelDetalhesAuditoria() {
    super(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(750, 530));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JTextArea observacao = new JTextArea(4, 35);
    observacao.setLineWrap(true);
    observacao.setWrapStyleWord(true);
    JPanel campos = ComponentesContabeis.campos(
        "Código", new JLabel("—"), "Severidade", new JLabel("—"),
        "Lançamento / partida", new JLabel("—"), "Detectado em", new JLabel("—"),
        "Status", new JLabel("—"), "Revisado por / em", new JLabel("—"),
        "Descrição", ComponentesContabeis.texto(""), "Recomendação", ComponentesContabeis.texto(""),
        "Observação da revisão", new JScrollPane(observacao));
    add(new JScrollPane(campos), BorderLayout.CENTER);
    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("auditoria.detalhes.revisar", "Marcar revisado"),
        ComponentesContabeis.botaoInativo("auditoria.detalhes.ignorar", "Ignorar"),
        ComponentesContabeis.botao("auditoria.detalhes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }
}
