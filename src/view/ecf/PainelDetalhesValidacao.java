package view.ecf;

import java.awt.*;
import javax.swing.*;
import model.*;
import view.ComponentesContabeis;

public class PainelDetalhesValidacao extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelDetalhesValidacao(ValidacaoECF v) {
    setLayout(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(700, 400));
    JPanel campos = new JPanel(new GridBagLayout());
    Object[][] linhas = {
      {"Código", new JLabel(v.getCodigo())},
      {"Tipo", new JLabel(String.valueOf(v.getTipo()))},
      {"Descrição", ComponentesContabeis.texto(v.getDescricao())},
      {"Referência", new JLabel(v.getReferencia())},
      {"Detectado em", new JLabel(String.valueOf(v.getDetectadoEm()))},
      {"Resolvido", new JLabel(String.valueOf(v.getResolvido()))},
      {"Resolvido por / em", new JLabel(v.getResolvidoPor() + " / " + v.getResolvidoEm())}
    };
    for (int i = 0; i < linhas.length; i++)
      ComponentesContabeis.adicionar_componente(
          i, campos, linhas[i][0] + ":", (JComponent) linhas[i][1]);
    add(campos);
    add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao(
                "ecf.detalhes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }
}
