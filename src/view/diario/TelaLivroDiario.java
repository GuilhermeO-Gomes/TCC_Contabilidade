package view.diario;

import java.awt.BorderLayout;
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
    JComboBox<String> status = new JComboBox<>(new String[] {
        "Contabilizados e estornados", "Todos", "Rascunho", "Contabilizado", "Estornado"
    });
    JPanel campos = ComponentesContabeis.campos(
        "Início", inicio, "Fim", fim, "Conta", new JTextField(20),
        "Documento", new JTextField(20), "Descrição", new JTextField(30), "Status", status);
    campos.setBorder(BorderFactory.createTitledBorder("Livro Diário"));
    JPanel topo = new JPanel(new BorderLayout());
    topo.add(campos);
    topo.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("diario.pesquisar", "Pesquisar")), BorderLayout.SOUTH);
    add(topo, BorderLayout.NORTH);

    JTable tabela = ComponentesContabeis.tabela("diario.tabela", ComponentesContabeis.modeloTabela(
        "ID", "Data", "Documento", "Descrição", "Débitos", "Créditos", "Status", "Estorno de"));
    add(ComponentesContabeis.lista("Lançamentos", tabela), BorderLayout.CENTER);
    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("diario.novo", "Novo", () ->
            ComponentesContabeis.abrirDialogo(this, "Novo lançamento", new PainelLancamento())),
        ComponentesContabeis.botao("diario.visualizar", "Visualizar", () ->
            ComponentesContabeis.abrirDialogo(this, "Detalhes do lançamento", new PainelDetalhesLancamento())),
        ComponentesContabeis.botaoInativo("diario.editar", "Editar rascunho"),
        ComponentesContabeis.botaoInativo("diario.excluir", "Excluir rascunho"),
        ComponentesContabeis.botaoInativo("diario.contabilizar", "Contabilizar"),
        ComponentesContabeis.botaoInativo("diario.estornar", "Estornar")), BorderLayout.SOUTH);
  }
}
