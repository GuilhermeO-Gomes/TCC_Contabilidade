package view.ecf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import view.ComponentesContabeis;

public class PainelDetalhesValidacao extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelDetalhesValidacao() {
    super(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(700, 400));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    add(ComponentesContabeis.campos(
        "Verificação", new JLabel("—"), "Situação", new JLabel("—"),
        "Referência", new JLabel("—"), "Motivo do bloqueio", ComponentesContabeis.texto(""),
        "O que precisa ser corrigido", ComponentesContabeis.texto("")), BorderLayout.CENTER);

    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.detalhes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }
}
