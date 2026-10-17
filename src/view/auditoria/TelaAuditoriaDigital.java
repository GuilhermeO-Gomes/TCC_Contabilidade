package view.auditoria;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import view.ComponentesContabeis;

public class TelaAuditoriaDigital extends JPanel {
  private static final long serialVersionUID = 1L;
  private final DadosContabeis dados;
  private final Runnable aoAlterar;
  private final JSpinner
      inicio = ComponentesContabeis.criarData("auditoria.inicio", LocalDate.now().withDayOfYear(1)),
      fim =
          ComponentesContabeis.criarData(
              "auditoria.fim", LocalDate.now().withMonth(12).withDayOfMonth(31));
  private final JComboBox<Object> severidade =
      new JComboBox<>(
          new Object[] {
            "Todas",
            NivelSeveridade.BAIXA,
            NivelSeveridade.MEDIA,
            NivelSeveridade.ALTA,
            NivelSeveridade.CRITICA
          });
  private final JComboBox<Object> status =
      new JComboBox<>(
          new Object[] {
            "Todos", StatusAuditoria.PENDENTE, StatusAuditoria.REVISADO, StatusAuditoria.IGNORADO
          });
  private final DefaultTableModel modelo =
      ComponentesContabeis.modeloTabela(
          "Severidade", "Código", "Data lançamento", "Conta", "Lançamento", "Descrição", "Status");
  private final JTable tabela = ComponentesContabeis.tabela("auditoria.tabela", modelo);
  private final JLabel resumo = new JLabel();
  private List<InconsistenciaAuditoria> carregados = new ArrayList<>(),
      exibidos = new ArrayList<>();

  public TelaAuditoriaDigital() {
    this(new DadosContabeis(), () -> {});
  }

  public TelaAuditoriaDigital(DadosContabeis dados, Runnable aoAlterar) {
    this.dados = dados;
    this.aoAlterar = aoAlterar;
    setLayout(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JPanel campos = new JPanel(new GridBagLayout());
    ComponentesContabeis.adicionar_componente(0, campos, "Início:", inicio);
    ComponentesContabeis.adicionar_componente(1, campos, "Fim:", fim);
    ComponentesContabeis.adicionar_componente(2, campos, "Severidade:", severidade);
    ComponentesContabeis.adicionar_componente(3, campos, "Status:", status);
    JPanel topo = new JPanel(new BorderLayout());
    topo.add(campos);
    topo.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("auditoria.consultar", "Consultar", this::atualizar_tabela),
            ComponentesContabeis.botao(
                "auditoria.executar", "Executar auditoria", this::executar_auditoria)),
        BorderLayout.SOUTH);
    add(topo, BorderLayout.NORTH);
    JPanel meio = new JPanel(new BorderLayout());
    meio.add(resumo, BorderLayout.NORTH);
    meio.add(
        ComponentesContabeis.lista(
            "Achados persistidos — empresa #" + dados.getServico().getEmpresaId(), tabela));
    add(meio);
    add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("auditoria.detalhes", "Detalhes / revisão", this::detalhes),
            ComponentesContabeis.botao(
                "auditoria.revisar", "Marcar revisado", () -> tratar(StatusAuditoria.REVISADO)),
            ComponentesContabeis.botao(
                "auditoria.ignorar", "Ignorar", () -> tratar(StatusAuditoria.IGNORADO))),
        BorderLayout.SOUTH);
    severidade.addActionListener(e -> exibir());
    status.addActionListener(e -> exibir());
    atualizar_tabela();
  }

  public void executar_auditoria() {
    LocalDate de = ComponentesContabeis.data(inicio), ate = ComponentesContabeis.data(fim);
    ComponentesContabeis.verificarPeriodo(de, ate);
    ComponentesContabeis.executar(
        this,
        () -> dados.getServico().executarAuditoria(de, ate),
        () -> {
          atualizar_tabela();
          aoAlterar.run();
        });
  }

  public void atualizar_tabela() {
    LocalDate de = ComponentesContabeis.data(inicio), ate = ComponentesContabeis.data(fim);
    ComponentesContabeis.verificarPeriodo(de, ate);
    ComponentesContabeis.carregar(
        this,
        () -> dados.getServico().listarInconsistencias(de, ate),
        lista -> {
          carregados = lista;
          exibir();
        });
  }

  private void exibir() {
    tabela.clearSelection();
    modelo.setRowCount(0);
    exibidos = new ArrayList<>();
    int[] contagem = new int[NivelSeveridade.values().length];
    for (InconsistenciaAuditoria a : carregados) {
      if (severidade.getSelectedIndex() > 0 && severidade.getSelectedItem() != a.getSeveridade()
          || status.getSelectedIndex() > 0 && status.getSelectedItem() != a.getStatus()) continue;
      exibidos.add(a);
      contagem[a.getSeveridade().ordinal()]++;
      modelo.addRow(
          new Object[] {
            a.getSeveridade(),
            a.getCodigo(),
            a.getData(),
            a.getConta(),
            a.getLancamentoId(),
            a.getDescricao(),
            a.getStatus()
          });
    }
    resumo.setText(
        "Baixa: "
            + contagem[0]
            + " | Média: "
            + contagem[1]
            + " | Alta: "
            + contagem[2]
            + " | Crítica: "
            + contagem[3]);
  }

  private InconsistenciaAuditoria selecionado() {
    return exibidos.get(ComponentesContabeis.linhaSelecionada(tabela));
  }

  private void detalhes() {
    ComponentesContabeis.abrirDialogo(
        this,
        "Detalhes da inconsistência",
        new PainelDetalhesAuditoria(
            dados,
            selecionado(),
            () -> {
              atualizar_tabela();
              aoAlterar.run();
            }));
  }

  private void tratar(StatusAuditoria estado) {
    long id = selecionado().getId();
    String observacao = JOptionPane.showInputDialog(this, "Observação da revisão:");
    if (observacao == null) return;
    if (JOptionPane.showConfirmDialog(
            this,
            "Confirmar " + estado + " para #" + id + "?",
            "Revisão",
            JOptionPane.YES_NO_OPTION)
        != JOptionPane.YES_OPTION) return;
    ComponentesContabeis.executar(
        this,
        () -> dados.getServico().revisarInconsistencia(id, estado, observacao),
        () -> {
          atualizar_tabela();
          aoAlterar.run();
        });
  }
}
