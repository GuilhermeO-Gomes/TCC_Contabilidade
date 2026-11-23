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
    JComboBox<String> classificacao = new JComboBox<>(new String[] {"Todas", "Erro bloqueante", "Alerta"});
    JComboBox<String> status = new JComboBox<>(new String[] {
        "Todos", "Pendente", "Em análise", "Resolvido", "Justificado"
    });
    JPanel filtros = ComponentesContabeis.campos(
        "Exercício social", new JComboBox<String>(), "Data inicial", inicio, "Data final", fim,
        "Classificação", classificacao, "Status", status);
    JPanel topo = new JPanel(new BorderLayout());
    topo.add(filtros);
    topo.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("auditoria.consultar", "Consultar"),
        ComponentesContabeis.botaoInativo("auditoria.executar", "Executar auditoria")), BorderLayout.SOUTH);
    add(topo, BorderLayout.NORTH);

    JTable tabela = ComponentesContabeis.tabela("auditoria.tabela", ComponentesContabeis.modeloTabela(
        "Classificação", "Ocorrência", "Data do lançamento", "Conta", "Lançamento", "Status"));
    JPanel meio = new JPanel(new BorderLayout());
    meio.add(new JLabel("Erros bloqueantes abertos: — | Alertas abertos: —"), BorderLayout.NORTH);
    meio.add(ComponentesContabeis.lista("Inconsistências", tabela));
    add(meio, BorderLayout.CENTER);

    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("auditoria.detalhes", "Detalhes e revisão", () ->
            ComponentesContabeis.abrirDialogo(this, "Detalhes e revisão", new PainelDetalhesAuditoria())),
        ComponentesContabeis.botaoInativo("auditoria.analisar", "Marcar em análise"),
        ComponentesContabeis.botaoInativo("auditoria.justificar", "Justificar alerta")), BorderLayout.SOUTH);
  }
}
