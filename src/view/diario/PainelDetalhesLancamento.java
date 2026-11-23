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
        "Nº do lançamento", new JLabel("—"), "Data", new JLabel("—"),
        "Número do documento", new JLabel("—"), "Origem", new JLabel("—"),
        "Histórico", new JLabel("—"), "Cadastrado por", new JLabel("—"),
        "Data do registro", new JLabel("—"), "Estorno ou ajuste vinculado", new JLabel("—"));
    campos.setBorder(BorderFactory.createTitledBorder("Dados do lançamento"));
    add(campos, BorderLayout.NORTH);

    JTable tabela = ComponentesContabeis.tabela("detalhes.partidas", ComponentesContabeis.modeloTabela(
        "Conta", "Centro de custos", "Movimento", "Valor", "Descrição"));
    add(ComponentesContabeis.lista("Partidas", tabela), BorderLayout.CENTER);

    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(new JLabel("Débitos: —   Créditos: —   Diferença: —"), BorderLayout.NORTH);
    rodape.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("detalhes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
  }
}
