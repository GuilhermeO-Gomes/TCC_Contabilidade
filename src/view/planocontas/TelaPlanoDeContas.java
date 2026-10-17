package view.planocontas;

import controller.PlanoContasController;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import view.ComponentesContabeis;

public class TelaPlanoDeContas extends JPanel {
  private static final long serialVersionUID = 1L;
  private final DadosContabeis dados;
  private final PlanoContasController controller;
  private List<PlanoContas> exibidas = new ArrayList<>();
  private final DefaultTableModel modelo =
      ComponentesContabeis.modeloTabela(
          "ID",
          "Código",
          "Descrição",
          "Tipo",
          "Natureza",
          "Ativa",
          "Conta superior",
          "Aglutinação");
  private final JTable tabela = ComponentesContabeis.tabela("plano.tabela", modelo);

  public TelaPlanoDeContas() {
    this(new DadosContabeis());
  }

  public TelaPlanoDeContas(DadosContabeis dados) {
    this.dados = dados;
    controller = new PlanoContasController(dados);
    setLayout(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    add(
        new JLabel("Plano de Contas — empresa #" + dados.getServico().getEmpresaId()),
        BorderLayout.NORTH);
    add(ComponentesContabeis.lista("Contas persistidas", tabela));
    add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("plano.atualizar", "Atualizar", this::atualizar),
            ComponentesContabeis.botao("plano.nova", "Nova conta", () -> editar(null)),
            ComponentesContabeis.botao(
                "plano.editar",
                "Editar / ativar / inativar",
                () -> editar(exibidas.get(ComponentesContabeis.linhaSelecionada(tabela))))),
        BorderLayout.SOUTH);
    atualizar();
  }

  public JTable getTabelaPlanoDeContas() {
    return tabela;
  }

  public void atualizar() {
    ComponentesContabeis.carregar(
        this,
        controller::listar,
        lista -> {
          tabela.clearSelection();
          modelo.setRowCount(0);
          exibidas = lista;
          for (PlanoContas p : lista)
            modelo.addRow(
                new Object[] {
                  p.getId(),
                  p.getCodigo(),
                  p.getDescricao(),
                  p.getTipo(),
                  p.getNatureza(),
                  p.getAtivo(),
                  p.getContaPaiId(),
                  p.getCodigoAglutinacaoId()
                });
        });
  }

  private void editar(PlanoContas original) {
    ComponentesContabeis.carregar(
        this,
        () -> dados.getServico().aglutinacoes(),
        codigos -> {
          PlanoContas p = new PlanoContas();
          if (original != null) p.setId(original.getId());
          JTextField codigo = new JTextField(original == null ? "" : original.getCodigo(), 20),
              descricao = new JTextField(original == null ? "" : original.getDescricao(), 30);
          JComboBox<TipoConta> tipo = new JComboBox<>(TipoConta.values());
          JComboBox<NaturezaConta> natureza = new JComboBox<>(NaturezaConta.values());
          JCheckBox ativa = new JCheckBox("Ativa", original == null || original.getAtivo());
          JComboBox<PlanoContas> pai = new JComboBox<>();
          pai.addItem(null);
          for (PlanoContas c : exibidas)
            if (c.getTipo() == TipoConta.SINTETICA
                && c.getAtivo()
                && (original == null || c.getId() != original.getId())) pai.addItem(c);
          JComboBox<ReferenciaContabil> aglutinacao = new JComboBox<>();
          aglutinacao.addItem(null);
          for (ReferenciaContabil c : codigos) aglutinacao.addItem(c);
          tipo.setSelectedItem(original == null ? TipoConta.ANALITICA : original.getTipo());
          natureza.setSelectedItem(
              original == null ? NaturezaConta.DEVEDORA : original.getNatureza());
          if (original != null) {
            for (int i = 1; i < pai.getItemCount(); i++)
              if (Objects.equals(original.getContaPaiId(), pai.getItemAt(i).getId()))
                pai.setSelectedIndex(i);
            for (int i = 1; i < aglutinacao.getItemCount(); i++)
              if (Objects.equals(
                  original.getCodigoAglutinacaoId(), aglutinacao.getItemAt(i).getId()))
                aglutinacao.setSelectedIndex(i);
          }
          JPanel campos = new JPanel(new GridBagLayout());
          Object[][] linhas = {
            {"Código", codigo},
            {"Descrição", descricao},
            {"Tipo", tipo},
            {"Natureza", natureza},
            {"Situação", ativa},
            {"Conta superior", pai},
            {"Aglutinação opcional", aglutinacao}
          };
          for (int i = 0; i < linhas.length; i++)
            ComponentesContabeis.adicionar_componente(
                i, campos, linhas[i][0] + ":", (JComponent) linhas[i][1]);
          if (JOptionPane.showConfirmDialog(this, campos, "Conta", JOptionPane.OK_CANCEL_OPTION)
              != JOptionPane.OK_OPTION) return;
          p.setCodigo(codigo.getText().trim());
          p.setDescricao(descricao.getText().trim());
          p.setTipo((TipoConta) tipo.getSelectedItem());
          p.setNatureza((NaturezaConta) natureza.getSelectedItem());
          p.setAtivo(ativa.isSelected());
          p.setContaPaiId(
              pai.getSelectedItem() == null ? null : ((PlanoContas) pai.getSelectedItem()).getId());
          p.setCodigoAglutinacaoId(
              aglutinacao.getSelectedItem() == null
                  ? null
                  : ((ReferenciaContabil) aglutinacao.getSelectedItem()).getId());
          ComponentesContabeis.carregar(this, () -> controller.salvar(p), id -> atualizar());
        });
  }
}
