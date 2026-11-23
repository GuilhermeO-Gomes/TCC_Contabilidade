package view.diario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import view.ComponentesContabeis;

public class PainelDetalhesLancamento extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelDetalhesLancamento() {
    super(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    setPreferredSize(new Dimension(780, 500));

    JPanel campos = ComponentesContabeis.campos(
        "ID", new JLabel("—"), "Data", new JLabel("—"), "Documento", new JLabel("—"),
        "Histórico", ComponentesContabeis.texto(""), "Status", new JLabel("—"));
    campos.setBorder(BorderFactory.createTitledBorder("Dados do lançamento"));
    add(campos, BorderLayout.NORTH);
    JTable tabela = ComponentesContabeis.tabela("detalhes.partidas", ComponentesContabeis.modeloTabela(
        "Conta", "Centro de custo", "Movimento", "Valor"));
    add(ComponentesContabeis.lista("Partidas", tabela), BorderLayout.CENTER);
    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(new JLabel("Débitos: R$ 0,00   Créditos: R$ 0,00   Diferença: R$ 0,00"), BorderLayout.NORTH);
    rodape.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("detalhes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
  }
}
