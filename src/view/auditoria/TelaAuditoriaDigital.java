package view.auditoria;

import java.awt.BorderLayout;
import java.time.LocalDate;
import javax.swing.*;
import view.ComponentesContabeis;

public class TelaAuditoriaDigital extends JPanel {
  private static final long serialVersionUID = 1L;

  public TelaAuditoriaDigital() {
    super(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    LocalDate hoje = LocalDate.now();
    JSpinner inicio = ComponentesContabeis.criarData("auditoria.inicio", hoje.withDayOfYear(1));
    JSpinner fim = ComponentesContabeis.criarData("auditoria.fim", hoje.withMonth(12).withDayOfMonth(31));
    JComboBox<String> severidade = new JComboBox<>(new String[] {"Todas", "Baixa", "Média", "Alta", "Crítica"});
    JComboBox<String> status = new JComboBox<>(new String[] {"Todos", "Pendente", "Revisado", "Ignorado"});
    JPanel topo = new JPanel(new BorderLayout());
    topo.add(ComponentesContabeis.campos("Início", inicio, "Fim", fim, "Severidade", severidade, "Status", status));
    topo.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("auditoria.consultar", "Consultar"),
        ComponentesContabeis.botaoInativo("auditoria.executar", "Executar auditoria")), BorderLayout.SOUTH);
    add(topo, BorderLayout.NORTH);

    JTable tabela = ComponentesContabeis.tabela("auditoria.tabela", ComponentesContabeis.modeloTabela(
        "Severidade", "Código", "Data lançamento", "Conta", "Lançamento", "Descrição", "Status"));
    JPanel meio = new JPanel(new BorderLayout());
    meio.add(new JLabel("Baixa: 0 | Média: 0 | Alta: 0 | Crítica: 0"), BorderLayout.NORTH);
    meio.add(ComponentesContabeis.lista("Inconsistências", tabela));
    add(meio, BorderLayout.CENTER);
    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("auditoria.detalhes", "Detalhes / revisão", () ->
            ComponentesContabeis.abrirDialogo(this, "Detalhes da inconsistência", new PainelDetalhesAuditoria())),
        ComponentesContabeis.botaoInativo("auditoria.revisar", "Marcar revisado"),
        ComponentesContabeis.botaoInativo("auditoria.ignorar", "Ignorar")), BorderLayout.SOUTH);
  }
}
