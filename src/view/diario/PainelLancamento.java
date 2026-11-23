package view.diario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import javax.swing.*;
import view.ComponentesContabeis;

public class PainelLancamento extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelLancamento() {
    super(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(850, 660));
    setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    add(ComponentesContabeis.campos(
        "Empresa", new JLabel("—"),
        "Exercício", new JComboBox<String>(),
        "Data", ComponentesContabeis.criarData("lancamento.data", LocalDate.now()),
        "Documento", new JTextField(20),
        "Descrição", new JTextField(30),
        "Histórico padrão (opcional)", new JComboBox<String>(),
        "Lote (opcional)", new JComboBox<String>(),
        "Estado", new JLabel("Rascunho")), BorderLayout.NORTH);

    JPanel partidas = new JPanel(new BorderLayout());
    JPanel editor = new JPanel(new BorderLayout());
    editor.add(ComponentesContabeis.campos(
        "Conta analítica", new JComboBox<String>(),
        "Centro de custo (opcional)", new JComboBox<String>(),
        "Movimento", new JComboBox<>(new String[] {"Débito", "Crédito"}),
        "Valor", new JTextField(12),
        "Descrição da partida", new JTextField(25)));
    editor.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("partida.adicionar", "Adicionar"),
        ComponentesContabeis.botaoInativo("partida.atualizar", "Atualizar selecionada"),
        ComponentesContabeis.botaoInativo("partida.remover", "Remover")), BorderLayout.SOUTH);
    partidas.add(editor, BorderLayout.NORTH);
    JTable tabela = ComponentesContabeis.tabela("lancamento.partidas", ComponentesContabeis.modeloTabela(
        "Conta", "Centro", "Movimento", "Valor", "Descrição"));
    partidas.add(ComponentesContabeis.lista("Partidas", tabela));
    add(partidas, BorderLayout.CENTER);

    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(new JLabel("Débitos: R$ 0,00   Créditos: R$ 0,00   Diferença: R$ 0,00"), BorderLayout.NORTH);
    rodape.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("lancamento.salvar", "Salvar rascunho"),
        ComponentesContabeis.botaoInativo("lancamento.contabilizar", "Salvar e contabilizar"),
        ComponentesContabeis.botao("lancamento.cancelar", "Cancelar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
  }
}
