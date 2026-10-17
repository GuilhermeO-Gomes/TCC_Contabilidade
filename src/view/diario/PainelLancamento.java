package view.diario;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import view.ComponentesContabeis;

public class PainelLancamento extends JPanel {
  private static final long serialVersionUID = 1L;
  private final DadosContabeis dados;
  private final LancamentoContabil lancamento;
  private final Consumer<LancamentoContabil> aoSalvar;
  private final JSpinner data;
  private final JTextField documento = new JTextField(20),
      descricao = new JTextField(30),
      valor = new JTextField(12),
      descricaoPartida = new JTextField(25);
  private final JComboBox<ExercicioSocial> exercicio = new JComboBox<>();
  private final JComboBox<PlanoContas> conta = new JComboBox<>();
  private final JComboBox<ReferenciaContabil> centro = new JComboBox<>(),
      historico = new JComboBox<>(),
      lote = new JComboBox<>();
  private final JComboBox<TipoMovimento> movimento = new JComboBox<>(TipoMovimento.values());
  private final DefaultTableModel modelo =
      ComponentesContabeis.modeloTabela("Conta", "Centro", "Movimento", "Valor", "Descrição");
  private final JTable tabela = ComponentesContabeis.tabela("lancamento.partidas", modelo);
  private final JLabel totais = new JLabel();
  private final JButton contabilizar;

  public PainelLancamento(
      DadosContabeis dados, LancamentoContabil original, Consumer<LancamentoContabil> aoSalvar) {
    this.dados = dados;
    this.aoSalvar = aoSalvar;
    lancamento = original == null ? new LancamentoContabil() : new LancamentoContabil(original);
    if (lancamento.getStatus() != StatusLancamento.RASCUNHO)
      throw new IllegalStateException("Somente rascunhos podem ser editados.");
    data = ComponentesContabeis.criarData("lancamento.data", lancamento.getData());
    documento.setText(lancamento.getDocumento());
    descricao.setText(lancamento.getHistorico());
    setLayout(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(900, 720));
    setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
    JPanel campos = new JPanel(new GridBagLayout());
    ComponentesContabeis.adicionar_componente(
        0,
        campos,
        "Empresa:",
        new JLabel("#" + dados.getServico().getEmpresaId() + ""));
    ComponentesContabeis.adicionar_componente(1, campos, "Exercício:", exercicio);
    ComponentesContabeis.adicionar_componente(2, campos, "Data:", data);
    ComponentesContabeis.adicionar_componente(3, campos, "Documento:", documento);
    ComponentesContabeis.adicionar_componente(4, campos, "Descrição:", descricao);
    ComponentesContabeis.adicionar_componente(5, campos, "Histórico padrão (opcional):", historico);
    ComponentesContabeis.adicionar_componente(6, campos, "Lote (opcional):", lote);
    ComponentesContabeis.adicionar_componente(
        7, campos, "Estado:", new JLabel("RASCUNHO — salvar não contabiliza"));
    add(campos, BorderLayout.NORTH);
    JPanel item = new JPanel(new GridBagLayout());
    ComponentesContabeis.adicionar_componente(0, item, "Conta analítica:", conta);
    ComponentesContabeis.adicionar_componente(1, item, "Centro de custo (opcional):", centro);
    ComponentesContabeis.adicionar_componente(2, item, "Movimento:", movimento);
    ComponentesContabeis.adicionar_componente(3, item, "Valor:", valor);
    ComponentesContabeis.adicionar_componente(4, item, "Descrição da partida:", descricaoPartida);
    JPanel partidas = new JPanel(new BorderLayout());
    JPanel editor = new JPanel(new BorderLayout());
    editor.add(item);
    editor.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao(
                "partida.adicionar",
                "Adicionar",
                () -> {
                  lancamento.adicionarItem(lerPartida(0));
                  atualizar();
                }),
            ComponentesContabeis.botao(
                "partida.atualizar",
                "Atualizar selecionada",
                () -> {
                  int i = ComponentesContabeis.linhaSelecionada(tabela);
                  lancamento.getItens().set(i, lerPartida(lancamento.getItens().get(i).getId()));
                  atualizar();
                }),
            ComponentesContabeis.botao(
                "partida.remover",
                "Remover",
                () -> {
                  lancamento.getItens().remove(ComponentesContabeis.linhaSelecionada(tabela));
                  atualizar();
                })),
        BorderLayout.SOUTH);
    partidas.add(editor, BorderLayout.NORTH);
    partidas.add(ComponentesContabeis.lista("Partidas", tabela));
    add(partidas);
    contabilizar =
        ComponentesContabeis.botao(
            "lancamento.contabilizar", "Salvar e contabilizar", () -> salvar(true));
    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(totais, BorderLayout.NORTH);
    rodape.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("lancamento.salvar", "Salvar rascunho", () -> salvar(false)),
            contabilizar,
            ComponentesContabeis.botao(
                "lancamento.cancelar", "Cancelar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
    tabela
        .getSelectionModel()
        .addListSelectionListener(
            e -> {
              if (!e.getValueIsAdjusting() && tabela.getSelectedRow() >= 0) {
                ItemLancamento p =
                    lancamento.getItens().get(ComponentesContabeis.linhaSelecionada(tabela));
                selecionarConta(p.getContaId());
                selecionar(centro, p.getCentroCustoId());
                movimento.setSelectedItem(p.getTipoMovimento());
                valor.setText(p.getValor().toPlainString());
                descricaoPartida.setText(p.getDescricao());
              }
            });
    exercicio.addActionListener(e -> atualizarHabilitacao());
    atualizar();
    carregarCadastros();
  }

  private static class Opcoes {
    List<ExercicioSocial> exercicios;
    List<PlanoContas> contas;
    List<ReferenciaContabil> centros, historicos, lotes;
  }

  private void carregarCadastros() {
    ComponentesContabeis.carregar(
        this,
        () -> {
          Opcoes opcoes = new Opcoes();
          opcoes.exercicios = dados.getServico().exercicios();
          opcoes.contas = dados.getServico().contas(true);
          opcoes.centros = dados.getServico().centros();
          opcoes.historicos = dados.getServico().historicos();
          opcoes.lotes = dados.getServico().lotes();
          return opcoes;
        },
        opcoes -> {
          for (ExercicioSocial e : opcoes.exercicios) {
            exercicio.addItem(e);
            if (e.getId() == lancamento.getExercicioSocialId()) exercicio.setSelectedItem(e);
          }
          for (PlanoContas c : opcoes.contas) conta.addItem(c);
          preencher(centro, opcoes.centros);
          preencher(historico, opcoes.historicos);
          preencher(lote, opcoes.lotes);
          selecionar(historico, lancamento.getHistoricoPadraoId());
          selecionar(lote, lancamento.getLoteId());
          atualizarHabilitacao();
        });
  }

  private void preencher(JComboBox<ReferenciaContabil> combo, List<ReferenciaContabil> itens) {
    combo.addItem(null);
    for (ReferenciaContabil item : itens) combo.addItem(item);
  }

  private void selecionarConta(long id) {
    for (int i = 0; i < conta.getItemCount(); i++)
      if (conta.getItemAt(i).getId() == id) conta.setSelectedIndex(i);
  }

  private void selecionar(JComboBox<ReferenciaContabil> combo, Long id) {
    combo.setSelectedIndex(-1);
    if (id != null)
      for (int i = 0; i < combo.getItemCount(); i++)
        if (combo.getItemAt(i) != null && combo.getItemAt(i).getId() == id)
          combo.setSelectedIndex(i);
  }

  private Long id(JComboBox<ReferenciaContabil> combo) {
    ReferenciaContabil r = (ReferenciaContabil) combo.getSelectedItem();
    return r == null ? null : r.getId();
  }

  private ItemLancamento lerPartida(long id) {
    PlanoContas c = (PlanoContas) conta.getSelectedItem();
    if (c == null) throw new IllegalArgumentException("Selecione uma conta analítica ativa.");
    ItemLancamento p = new ItemLancamento();
    p.setId(id);
    p.setContaId(c.getId());
    p.setConta(c.toString());
    p.setCentroCustoId(id(centro));
    p.setCentroCusto(centro.getSelectedItem() == null ? "" : centro.getSelectedItem().toString());
    p.setTipoMovimento((TipoMovimento) movimento.getSelectedItem());
    p.setValor(ComponentesContabeis.valor(valor.getText()));
    p.setDescricao(descricaoPartida.getText().trim());
    if (p.getValor().signum() <= 0) throw new IllegalArgumentException("Informe valor positivo.");
    return p;
  }

  private void atualizar() {
    modelo.setRowCount(0);
    for (ItemLancamento p : lancamento.getItens())
      modelo.addRow(
          new Object[] {
            p.getConta(), p.getCentroCusto(), p.getTipoMovimento(), p.getValor(), p.getDescricao()
          });
    totais.setText(
        "Débitos: "
            + ComponentesContabeis.moeda(lancamento.calcularTotalDebitos())
            + " | Créditos: "
            + ComponentesContabeis.moeda(lancamento.calcularTotalCreditos())
            + " | Diferença: "
            + ComponentesContabeis.moeda(lancamento.calcularDiferenca()));
    atualizarHabilitacao();
  }

  private void atualizarHabilitacao() {
    ExercicioSocial e = (ExercicioSocial) exercicio.getSelectedItem();
    if (contabilizar != null)
      contabilizar.setEnabled(
          e != null
              && e.isAtivo()
              && e.getStatusContabil() == ExercicioSocial.StatusContabil.ABERTO
              && !lancamento.getItens().isEmpty()
              && lancamento.estaBalanceado());
  }

  private void salvar(boolean postar) {
    ExercicioSocial e = (ExercicioSocial) exercicio.getSelectedItem();
    if (e == null) throw new IllegalArgumentException("Selecione um exercício.");
    lancamento.setExercicioSocialId(e.getId());
    lancamento.setData(ComponentesContabeis.data(data));
    lancamento.setDocumento(documento.getText().trim());
    lancamento.setHistorico(descricao.getText().trim());
    lancamento.setHistoricoPadraoId(id(historico));
    lancamento.setLoteId(id(lote));
    LancamentoContabil copia = new LancamentoContabil(lancamento);
    ComponentesContabeis.carregar(
        this,
        () -> {
          long salvo =
              postar
                  ? dados.getServico().salvarEContabilizar(copia)
                  : dados.getServico().salvarRascunho(copia);
          copia.setId(salvo);
          copia.setStatus(postar ? StatusLancamento.CONTABILIZADO : StatusLancamento.RASCUNHO);
          return copia;
        },
        salvo -> {
          aoSalvar.accept(salvo);
          ComponentesContabeis.fechar(this);
        });
  }
}
