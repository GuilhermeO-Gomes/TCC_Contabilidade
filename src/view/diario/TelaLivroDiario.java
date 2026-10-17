package view.diario;

import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import view.ComponentesContabeis;

public class TelaLivroDiario extends JPanel {
  private static final long serialVersionUID = 1L;
  private final DadosContabeis dados;
  private final Runnable aoAlterar;
  private final JSpinner inicio =
      ComponentesContabeis.criarData("diario.inicio", LocalDate.now().withDayOfYear(1));
  private final JSpinner fim =
      ComponentesContabeis.criarData(
          "diario.fim", LocalDate.now().withMonth(12).withDayOfMonth(31));
  private final JTextField conta = new JTextField(20),
      documento = new JTextField(20),
      descricao = new JTextField(30);
  private final JComboBox<Object> status =
      new JComboBox<>(
          new Object[] {
            "Livro Diário (contabilizados e estornados)",
            "Todos",
            StatusLancamento.RASCUNHO,
            StatusLancamento.CONTABILIZADO,
            StatusLancamento.ESTORNADO
          });
  private final DefaultTableModel modelo =
      ComponentesContabeis.modeloTabela(
          "ID", "Data", "Documento", "Descrição", "Débitos", "Créditos", "Status", "Estorno de");
  private final JTable tabela = ComponentesContabeis.tabela("diario.tabela", modelo);
  private List<LancamentoContabil> exibidos = new ArrayList<>();
  private final JButton editar, excluir, contabilizar, estornar;

  public TelaLivroDiario() {
    this(new DadosContabeis(), () -> {});
  }

  public TelaLivroDiario(DadosContabeis dados, Runnable aoAlterar) {
    this.dados = dados;
    this.aoAlterar = aoAlterar;
    setLayout(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JPanel campos = new JPanel(new GridBagLayout());
    campos.setBorder(
        BorderFactory.createTitledBorder(
            "Livro Diário — empresa #" + dados.getServico().getEmpresaId()));
    ComponentesContabeis.adicionar_componente(0, campos, "Início:", inicio);
    ComponentesContabeis.adicionar_componente(1, campos, "Fim:", fim);
    ComponentesContabeis.adicionar_componente(2, campos, "Conta:", conta);
    ComponentesContabeis.adicionar_componente(3, campos, "Documento:", documento);
    ComponentesContabeis.adicionar_componente(4, campos, "Descrição:", descricao);
    ComponentesContabeis.adicionar_componente(5, campos, "Status:", status);
    JPanel topo = new JPanel(new BorderLayout());
    topo.add(campos);
    topo.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("diario.pesquisar", "Pesquisar", this::atualizar_tabela)),
        BorderLayout.SOUTH);
    add(topo, BorderLayout.NORTH);
    add(ComponentesContabeis.lista("Lançamentos persistidos", tabela));
    editar =
        ComponentesContabeis.botao(
            "diario.editar", "Editar rascunho", () -> abrirEditor(selecionado()));
    excluir = ComponentesContabeis.botao("diario.excluir", "Excluir rascunho", this::excluir);
    contabilizar =
        ComponentesContabeis.botao("diario.contabilizar", "Contabilizar", this::contabilizar);
    estornar = ComponentesContabeis.botao("diario.estornar", "Estornar", this::estornar);
    add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("diario.novo", "Novo", () -> abrirEditor(null)),
            ComponentesContabeis.botao(
                "diario.visualizar",
                "Visualizar",
                () ->
                    ComponentesContabeis.abrirDialogo(
                        this, "Lançamento", new PainelDetalhesLancamento(selecionado()))),
            editar,
            excluir,
            contabilizar,
            estornar),
        BorderLayout.SOUTH);
    tabela.getSelectionModel().addListSelectionListener(e -> habilitar());
    habilitar();
    atualizar_tabela();
  }

  private void habilitar() {
    LancamentoContabil l = tabela.getSelectedRow() < 0 ? null : selecionado();
    boolean rascunho = l != null && l.getStatus() == StatusLancamento.RASCUNHO;
    editar.setEnabled(rascunho);
    excluir.setEnabled(rascunho);
    contabilizar.setEnabled(rascunho);
    estornar.setEnabled(l != null && l.getStatus() == StatusLancamento.CONTABILIZADO);
  }

  public void atualizar_tabela() {
    LocalDate de = ComponentesContabeis.data(inicio), ate = ComponentesContabeis.data(fim);
    ComponentesContabeis.verificarPeriodo(de, ate);
    Object filtro = status.getSelectedItem();
    String doc = documento.getText(), hist = descricao.getText(), ct = conta.getText();
    ComponentesContabeis.carregar(
        this,
        () ->
            dados
                .getServico()
                .listarLancamentos(
                    de,
                    ate,
                    doc,
                    hist,
                    ct,
                    filtro instanceof StatusLancamento ? (StatusLancamento) filtro : null),
        lista -> {
          tabela.clearSelection();
          modelo.setRowCount(0);
          exibidos = new ArrayList<>();
          for (LancamentoContabil l : lista) {
            if (status.getItemAt(0).equals(filtro) && l.getStatus() == StatusLancamento.RASCUNHO)
              continue;
            exibidos.add(l);
            modelo.addRow(
                new Object[] {
                  l.getId(),
                  l.getData(),
                  l.getDocumento(),
                  l.getHistorico(),
                  l.calcularTotalDebitos(),
                  l.calcularTotalCreditos(),
                  l.getStatus(),
                  l.getEstornoDeId()
                });
          }
          habilitar();
        });
  }

  private LancamentoContabil selecionado() {
    return exibidos.get(ComponentesContabeis.linhaSelecionada(tabela));
  }

  private void abrirEditor(LancamentoContabil l) {
    if (l != null && l.getStatus() != StatusLancamento.RASCUNHO)
      throw new IllegalStateException("Edite apenas rascunhos.");
    ComponentesContabeis.abrirDialogo(
        this,
        l == null ? "Novo rascunho" : "Editar rascunho",
        new PainelLancamento(
            dados,
            l,
            salvo -> {
              status.setSelectedIndex(1);
              atualizar_tabela();
              aoAlterar.run();
            }));
  }

  private void contabilizar() {
    long id = selecionado().getId();
    if (JOptionPane.showConfirmDialog(
            this,
            "Contabilizar #" + id + "? Depois, a correção exige estorno.",
            "Contabilizar",
            JOptionPane.YES_NO_OPTION)
        != JOptionPane.YES_OPTION) return;
    ComponentesContabeis.executar(
        this,
        () -> dados.getServico().contabilizar(id),
        () -> {
          atualizar_tabela();
          aoAlterar.run();
        });
  }

  private void excluir() {
    long id = selecionado().getId();
    if (JOptionPane.showConfirmDialog(
            this, "Excluir o rascunho #" + id + "?", "Excluir", JOptionPane.YES_NO_OPTION)
        != JOptionPane.YES_OPTION) return;
    ComponentesContabeis.executar(
        this,
        () -> dados.getServico().excluirRascunho(id),
        () -> {
          atualizar_tabela();
          aoAlterar.run();
        });
  }

  private void estornar() {
    long id = selecionado().getId();
    ComponentesContabeis.carregar(
        this,
        () -> dados.getServico().exercicios(),
        lista -> {
          JComboBox<ExercicioSocial> exercicio =
              new JComboBox<>(
                  lista.stream()
                      .filter(
                          e ->
                              e.isAtivo()
                                  && e.getStatusContabil() == ExercicioSocial.StatusContabil.ABERTO)
                      .toArray(ExercicioSocial[]::new));
          JSpinner data = ComponentesContabeis.criarData("estorno.data", LocalDate.now());
          JTextField motivo = new JTextField(25);
          JPanel formulario = new JPanel(new GridBagLayout());
          ComponentesContabeis.adicionar_componente(0, formulario, "Exercício aberto:", exercicio);
          ComponentesContabeis.adicionar_componente(1, formulario, "Data:", data);
          ComponentesContabeis.adicionar_componente(2, formulario, "Motivo:", motivo);
          if (JOptionPane.showConfirmDialog(
                  this,
                  formulario,
                  "Criar lançamento reverso de #" + id,
                  JOptionPane.OK_CANCEL_OPTION)
              != JOptionPane.OK_OPTION) return;
          ExercicioSocial e = (ExercicioSocial) exercicio.getSelectedItem();
          if (e == null) throw new IllegalStateException("Cadastre um exercício aberto.");
          LocalDate dia = ComponentesContabeis.data(data);
          String texto = motivo.getText().trim();
          ComponentesContabeis.carregar(
              this,
              () -> dados.getServico().estornar(id, e.getId(), dia, texto),
              novo -> {
                atualizar_tabela();
                aoAlterar.run();
                JOptionPane.showMessageDialog(
                    this, "Estorno #" + novo + " criado. O original foi preservado.");
              });
        });
  }

  public JTable getTabelaLancamentos() {
    return tabela;
  }
}
