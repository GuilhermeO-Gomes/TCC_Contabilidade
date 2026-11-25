package view.dashboard;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import javax.swing.*;
import view.ComponentesContabeis;

public class TelaDashboard extends JPanel {
  private static final long serialVersionUID = 1L;

  public TelaDashboard() {
    super(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    LocalDate hoje = LocalDate.now();
    JComboBox<String> filtro = new JComboBox<>(new String[] {"Intervalo de datas", "Mês", "Exercício social"});
    JComboBox<String> mes = new JComboBox<>(new String[] {
        "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
    });
    mes.setSelectedIndex(hoje.getMonthValue() - 1);
    JComboBox<String> exercicio = new JComboBox<>();
    JSpinner inicio = ComponentesContabeis.criarData("dashboard.inicio", hoje.withDayOfYear(1));
    JSpinner fim = ComponentesContabeis.criarData("dashboard.fim", hoje.withMonth(12).withDayOfMonth(31));
    mes.setEnabled(false);
    exercicio.setEnabled(false);
    filtro.addActionListener(e -> {
      int modo = filtro.getSelectedIndex();
      inicio.setEnabled(modo == 0);
      fim.setEnabled(modo == 0);
      mes.setEnabled(modo == 1);
      exercicio.setEnabled(modo != 0);
    });

    JPanel filtros = ComponentesContabeis.campos(
        "Filtrar por", filtro, "Mês", mes, "Exercício social", exercicio,
        "Data inicial", inicio, "Data final", fim);
    filtros.setBorder(BorderFactory.createTitledBorder("Período"));
    JPanel topo = new JPanel(new BorderLayout());
    topo.add(filtros);
    topo.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("dashboard.atualizar", "Atualizar")), BorderLayout.SOUTH);
    add(topo, BorderLayout.NORTH);

    JPanel indicadores = new JPanel(new GridLayout(1, 3, 8, 8));
    indicadores.add(indicador("Resultado do período", "Receitas", "Despesas", "Lucro ou prejuízo"));
    indicadores.add(indicador("Posição patrimonial", "Ativo", "Passivo", "Patrimônio líquido"));
    indicadores.add(indicador("Saldo de caixa", "Caixa e equivalentes"));

    JPanel graficos = new JPanel(new GridLayout(1, 2, 8, 8));
    graficos.add(grafico("Receitas e despesas"));
    graficos.add(grafico("Evolução do saldo de caixa"));

    JPanel centro = new JPanel(new BorderLayout(8, 8));
    centro.add(indicadores, BorderLayout.NORTH);
    centro.add(graficos, BorderLayout.CENTER);
    add(centro, BorderLayout.CENTER);

    JLabel pendencias = new JLabel("Pendências abertas na Auditoria Digital: —");
    pendencias.setName("dashboard.inconsistencias");
    add(pendencias, BorderLayout.SOUTH);
  }

  private JPanel indicador(String titulo, String... rotulos) {
    JPanel painel = new JPanel(new GridLayout(0, 1, 0, 4));
    painel.setBorder(BorderFactory.createTitledBorder(titulo));
    for (String rotulo : rotulos) {
      painel.add(new JLabel(rotulo + ": —"));
    }
    return painel;
  }

  private JPanel grafico(String titulo) {
    JPanel painel = new JPanel(new BorderLayout());
    painel.setBorder(BorderFactory.createTitledBorder(titulo));
    painel.add(new JLabel("Sem dados para exibir", SwingConstants.CENTER));
    return painel;
  }
}
