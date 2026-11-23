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
        "Exercício social aberto", new JComboBox<String>(),
        "Data", ComponentesContabeis.criarData("lancamento.data", LocalDate.now()),
        "Número do documento", new JTextField(20),
        "Origem", new JLabel("Manual"),
        "Histórico padrão", new JComboBox<String>(),
        "Histórico", new JTextField(30)), BorderLayout.NORTH);

    JComboBox<String> centro = new JComboBox<>();
    centro.setEnabled(false);
    JPanel editor = new JPanel(new BorderLayout());
    editor.add(ComponentesContabeis.campos(
        "Conta analítica ativa (código e descrição)", new JComboBox<String>(),
        "Centro de custos (despesa ou custo)", centro,
        "Movimento", new JComboBox<>(new String[] {"Débito", "Crédito"}),
        "Valor", new JTextField(12),
        "Descrição da partida", new JTextField(25)));
    editor.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("partida.adicionar", "Adicionar"),
        ComponentesContabeis.botaoInativo("partida.atualizar", "Atualizar partida"),
        ComponentesContabeis.botaoInativo("partida.remover", "Remover")), BorderLayout.SOUTH);

    JPanel partidas = new JPanel(new BorderLayout());
    partidas.add(editor, BorderLayout.NORTH);
    JTable tabela = ComponentesContabeis.tabela("lancamento.partidas", ComponentesContabeis.modeloTabela(
        "Conta", "Centro de custos", "Movimento", "Valor", "Descrição"));
    partidas.add(ComponentesContabeis.lista("Partidas", tabela));
    add(partidas, BorderLayout.CENTER);

    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(new JLabel("Débitos: —   Créditos: —   Diferença: —"), BorderLayout.NORTH);
    rodape.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("lancamento.salvar", "Salvar lançamento"),
        ComponentesContabeis.botao("lancamento.cancelar", "Cancelar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
  }
}
