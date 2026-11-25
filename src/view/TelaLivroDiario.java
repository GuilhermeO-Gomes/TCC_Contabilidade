package view.diario;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.*;
import view.ComponentesContabeis;

public class TelaLivroDiario extends JPanel {
  private static final long serialVersionUID = 1L;

  public TelaLivroDiario() {
    super(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    LocalDate hoje = LocalDate.now();
    JSpinner inicio = ComponentesContabeis.criarData("diario.inicio", hoje.withDayOfYear(1));
    JSpinner fim = ComponentesContabeis.criarData("diario.fim", hoje.withMonth(12).withDayOfMonth(31));
    JComboBox<String> origem = new JComboBox<>(new String[] {
        "Todas", "Manual", "Financeiro", "Compras", "Vendas", "Recursos Humanos", "Fiscal"
    });
    JPanel campos = ComponentesContabeis.campos(
        "Data inicial", inicio, "Data final", fim, "Conta contábil", new JTextField(20),
        "Origem", origem, "Número do documento", new JTextField(20));
    campos.setBorder(BorderFactory.createTitledBorder("Filtros"));
    JPanel topo = new JPanel(new BorderLayout());
    topo.add(campos);
    topo.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("diario.pesquisar", "Pesquisar")), BorderLayout.SOUTH);
    add(topo, BorderLayout.NORTH);

    JTable tabela = ComponentesContabeis.tabela("diario.tabela", ComponentesContabeis.modeloTabela(
        "Data", "Nº lançamento", "Documento", "Origem", "Conta de débito", "Conta de crédito",
        "Histórico", "Débito", "Crédito"));
    add(ComponentesContabeis.lista("Lançamentos por data e número", tabela), BorderLayout.CENTER);

    JPanel totais = new JPanel(new GridLayout(2, 1));
    totais.add(new JLabel("Total do dia: Débitos — | Créditos —"));
    totais.add(new JLabel("Total do período: Débitos — | Créditos —"));

    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(totais, BorderLayout.NORTH);
    rodape.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("diario.novo", "Novo", () ->
            ComponentesContabeis.abrirDialogo(this, "Novo lançamento", new PainelLancamento())),
        ComponentesContabeis.botao("diario.editar", "Editar", () -> abrirSelecionado(tabela, true)),
        ComponentesContabeis.botao("diario.visualizar", "Visualizar", () -> abrirSelecionado(tabela, false)),
        ComponentesContabeis.botaoInativo("diario.excluir", "Excluir"),
        ComponentesContabeis.botaoInativo("diario.estornar", "Estornar"),
        ComponentesContabeis.botaoInativo("diario.complementar", "Complementar"),
        ComponentesContabeis.botaoInativo("diario.transferir", "Transferir")), BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
  }

  private void abrirSelecionado(JTable tabela, boolean editar) {
    if (tabela.getSelectedRow() < 0) {
      JOptionPane.showMessageDialog(this, "Selecione um lançamento na tabela.");
      return;
    }

    if (editar) {
      ComponentesContabeis.abrirDialogo(this, "Editar lançamento", new PainelLancamento());
    } else {
      ComponentesContabeis.abrirDialogo(this, "Detalhes do lançamento", new PainelDetalhesLancamento());
    }
  }
}
