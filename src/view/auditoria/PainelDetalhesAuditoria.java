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

    JTextArea justificativa = new JTextArea(4, 35);
    justificativa.setLineWrap(true);
    justificativa.setWrapStyleWord(true);
    JPanel campos = ComponentesContabeis.campos(
        "Código", new JLabel("—"), "Classificação", new JLabel("—"),
        "Lançamento / partida", new JLabel("—"), "Lançamento criado por", new JLabel("—"),
        "Detectado em", new JLabel("—"), "Status", new JLabel("—"),
        "Revisado por", new JLabel("—"), "Data da revisão", new JLabel("—"),
        "Descrição", ComponentesContabeis.texto(""), "Como corrigir", ComponentesContabeis.texto(""),
        "Justificativa (obrigatória para justificar alerta)", new JScrollPane(justificativa));
    add(new JScrollPane(campos), BorderLayout.CENTER);

    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("auditoria.detalhes.analisar", "Marcar em análise"),
        ComponentesContabeis.botaoInativo("auditoria.detalhes.justificar", "Justificar alerta"),
        ComponentesContabeis.botao("auditoria.detalhes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }
}
