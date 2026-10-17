package view.ecf;

import java.awt.*;
import java.math.BigDecimal;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import view.ComponentesContabeis;

public class PainelEstruturaECF extends JPanel {
  private static final long serialVersionUID = 1L;
  private final DadosContabeis dados;
  private final long ecfId;
  private final Runnable aoAlterar;
  private GeracaoECF geracao;
  private final DefaultTableModel apuracoes =
      ComponentesContabeis.modeloTabela(
          "ID",
          "Tributo",
          "Início",
          "Fim",
          "Base inicial",
          "Adições",
          "Exclusões",
          "Compensações",
          "Base informada",
          "Valor tributo");
  private final DefaultTableModel ajustes =
      ComponentesContabeis.modeloTabela("ID", "Apuração", "Tipo", "Descrição", "Valor");
  private final DefaultTableModel signatarios =
      ComponentesContabeis.modeloTabela("ID", "Nome", "Tipo", "CPF", "CRC", "UF", "Email");
  private final JTable tabelaApuracoes = ComponentesContabeis.tabela("ecf.apuracoes", apuracoes),
      tabelaAjustes = ComponentesContabeis.tabela("ecf.ajustes", ajustes),
      tabelaSignatarios = ComponentesContabeis.tabela("ecf.signatarios", signatarios);

  public PainelEstruturaECF(DadosContabeis dados, long ecfId, Runnable aoAlterar) {
    this.dados = dados;
    this.ecfId = ecfId;
    this.aoAlterar = aoAlterar;
    setLayout(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(1050, 570));
    add(
        new JLabel(
            "ECF #"
                + ecfId
                + " — valores informados pelo usuário; não calcula regras fiscais nem"
                + " assinaturas."),
        BorderLayout.NORTH);
    JTabbedPane abas = new JTabbedPane();
    JPanel p = new JPanel(new BorderLayout());
    p.add(ComponentesContabeis.lista("Apurações", tabelaApuracoes));
    p.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("ecf.apuracao.nova", "Nova", () -> editarApuracao(null)),
            ComponentesContabeis.botao(
                "ecf.apuracao.editar", "Editar", () -> editarApuracao(apuracaoSelecionada())),
            ComponentesContabeis.botao(
                "ecf.apuracao.remover",
                "Remover",
                () -> remover("apuracao", apuracaoSelecionada().getId()))),
        BorderLayout.SOUTH);
    abas.addTab("Apurações", p);
    JPanel a = new JPanel(new BorderLayout());
    a.add(ComponentesContabeis.lista("Ajustes", tabelaAjustes));
    a.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("ecf.ajuste.novo", "Novo", this::novoAjuste),
            ComponentesContabeis.botao(
                "ecf.ajuste.remover",
                "Remover",
                () ->
                    remover(
                        "ajuste",
                        ((Number)
                                ajustes.getValueAt(
                                    ComponentesContabeis.linhaSelecionada(tabelaAjustes), 0))
                            .longValue()))),
        BorderLayout.SOUTH);
    abas.addTab("Ajustes", a);
    JPanel s = new JPanel(new BorderLayout());
    s.add(ComponentesContabeis.lista("Signatários", tabelaSignatarios));
    s.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("ecf.signatario.novo", "Novo", this::novoSignatario),
            ComponentesContabeis.botao(
                "ecf.signatario.remover",
                "Remover",
                () ->
                    remover(
                        "signatario",
                        ((Number)
                                signatarios.getValueAt(
                                    ComponentesContabeis.linhaSelecionada(tabelaSignatarios), 0))
                            .longValue()))),
        BorderLayout.SOUTH);
    abas.addTab("Signatários", s);
    add(abas);
    add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("ecf.estrutura.atualizar", "Atualizar", this::atualizar),
            ComponentesContabeis.botao(
                "ecf.estrutura.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
    atualizar();
  }

  private JPanel campos(Object... pares) {
    JPanel p = new JPanel(new GridBagLayout());
    for (int i = 0; i < pares.length; i += 2)
      ComponentesContabeis.adicionar_componente(
          i / 2, p, pares[i] + ":", (JComponent) pares[i + 1]);
    return p;
  }

  private BigDecimal decimal(JTextField campo) {
    String s = campo.getText().trim().replace(',', '.');
    if (!s.matches("-?\\d+(?:\\.\\d{1,2})?"))
      throw new IllegalArgumentException("Informe um valor como 1000,00, sem separador de milhar.");
    return new BigDecimal(s);
  }

  private void atualizar() {
    ComponentesContabeis.carregar(
        this,
        () -> dados.getServico().buscarECF(ecfId),
        g -> {
          geracao = g;
          boolean podeEditar =
              g.getStatus() == StatusECF.EM_PREPARACAO
                  || g.getStatus() == StatusECF.COM_ERROS
                  || g.getStatus() == StatusECF.VALIDADA;
          habilitarEdicao(this, podeEditar);
          apuracoes.setRowCount(0);
          ajustes.setRowCount(0);
          signatarios.setRowCount(0);
          for (EcfApuracao p : g.getApuracoes()) {
            apuracoes.addRow(
                new Object[] {
                  p.getId(),
                  p.getTributo(),
                  p.getDataInicio(),
                  p.getDataFim(),
                  p.getBaseInicial(),
                  p.getTotalAdicoes(),
                  p.getTotalExclusoes(),
                  p.getTotalCompensacoes(),
                  p.getBaseCalculo(),
                  p.getValorTributo()
                });
            for (EcfAjuste a : p.getAjustes())
              ajustes.addRow(
                  new Object[] {a.getId(), p.getId(), a.getTipo(), a.getDescricao(), a.getValor()});
          }
          for (EcfSignatario s : g.getSignatarios())
            signatarios.addRow(
                new Object[] {
                  s.getId(),
                  s.getNome(),
                  s.getTipo(),
                  s.getCpf(),
                  s.getCrc(),
                  s.getUfCrc(),
                  s.getEmail()
                });
        });
  }

  private void habilitarEdicao(Component componente, boolean habilitado) {
    String nome = componente.getName();
    if (componente instanceof JButton
        && nome != null
        && (nome.endsWith(".nova")
            || nome.endsWith(".novo")
            || nome.endsWith(".editar")
            || nome.endsWith(".remover"))) componente.setEnabled(habilitado);
    if (componente instanceof Container)
      for (Component filho : ((Container) componente).getComponents())
        habilitarEdicao(filho, habilitado);
  }

  private void concluido() {
    atualizar();
    aoAlterar.run();
  }

  private EcfApuracao apuracaoSelecionada() {
    return geracao.getApuracoes().get(ComponentesContabeis.linhaSelecionada(tabelaApuracoes));
  }

  private void editarApuracao(EcfApuracao original) {
    EcfApuracao p = new EcfApuracao();
    JComboBox<TributoECF> tributo = new JComboBox<>(TributoECF.values());
    JSpinner
        inicio =
            ComponentesContabeis.criarData(
                "apuracao.inicio",
                original == null ? geracao.getPeriodoInicial() : original.getDataInicio()),
        fim =
            ComponentesContabeis.criarData(
                "apuracao.fim",
                original == null ? geracao.getPeriodoFinal() : original.getDataFim());
    JTextField
        base =
            new JTextField(original == null ? "0,00" : original.getBaseInicial().toPlainString()),
        calculo =
            new JTextField(original == null ? "0,00" : original.getBaseCalculo().toPlainString()),
        valor =
            new JTextField(original == null ? "0,00" : original.getValorTributo().toPlainString()),
        aliquota =
            new JTextField(
                original == null || original.getAliquota() == null
                    ? ""
                    : original.getAliquota().toPlainString());
    if (original != null) {
      p.setId(original.getId());
      tributo.setSelectedItem(original.getTributo());
    }
    JPanel form =
        campos(
            "Tributo",
            tributo,
            "Início",
            inicio,
            "Fim",
            fim,
            "Base inicial",
            base,
            "Base de cálculo informada",
            calculo,
            "Alíquota opcional (até 4 casas)",
            aliquota,
            "Valor do tributo informado",
            valor);
    if (JOptionPane.showConfirmDialog(
            this, form, "Apuração — valores manuais", JOptionPane.OK_CANCEL_OPTION)
        != JOptionPane.OK_OPTION) return;
    p.setTributo((TributoECF) tributo.getSelectedItem());
    p.setDataInicio(ComponentesContabeis.data(inicio));
    p.setDataFim(ComponentesContabeis.data(fim));
    p.setBaseInicial(decimal(base));
    p.setBaseCalculo(decimal(calculo));
    p.setValorTributo(decimal(valor));
    try {
      p.setAliquota(
          aliquota.getText().trim().isEmpty()
              ? null
              : new BigDecimal(aliquota.getText().trim().replace(',', '.')));
    } catch (NumberFormatException erro) {
      throw new IllegalArgumentException("Alíquota inválida.");
    }
    ComponentesContabeis.carregar(
        this, () -> dados.getServico().salvarApuracao(ecfId, p), id -> concluido());
  }

  private void novoAjuste() {
    JComboBox<EcfApuracao> apuracao =
        new JComboBox<>(geracao.getApuracoes().toArray(new EcfApuracao[0]));
    JComboBox<TipoAjusteECF> tipo = new JComboBox<>(TipoAjusteECF.values());
    JTextField codigo = new JTextField(20),
        descricao = new JTextField(30),
        valor = new JTextField("0,00"),
        documento = new JTextField(20),
        observacao = new JTextField(30);
    ComponentesContabeis.carregar(
        this,
        () -> dados.getServico().contas(true),
        contas -> {
          JComboBox<PlanoContas> conta = new JComboBox<>();
          conta.addItem(null);
          for (PlanoContas c : contas) conta.addItem(c);
          JPanel form =
              campos(
                  "Apuração",
                  apuracao,
                  "Tipo",
                  tipo,
                  "Código opcional",
                  codigo,
                  "Descrição",
                  descricao,
                  "Valor",
                  valor,
                  "Conta opcional",
                  conta,
                  "Documento",
                  documento,
                  "Observação",
                  observacao);
          if (JOptionPane.showConfirmDialog(this, form, "Ajuste", JOptionPane.OK_CANCEL_OPTION)
              != JOptionPane.OK_OPTION) return;
          EcfApuracao p = (EcfApuracao) apuracao.getSelectedItem();
          if (p == null) throw new IllegalArgumentException("Cadastre uma apuração primeiro.");
          EcfAjuste a = new EcfAjuste();
          a.setTipo((TipoAjusteECF) tipo.getSelectedItem());
          a.setCodigo(codigo.getText().trim());
          a.setDescricao(descricao.getText().trim());
          a.setValor(decimal(valor));
          a.setContaId(
              conta.getSelectedItem() == null
                  ? null
                  : ((PlanoContas) conta.getSelectedItem()).getId());
          a.setDocumentoReferencia(documento.getText().trim());
          a.setObservacao(observacao.getText().trim());
          ComponentesContabeis.carregar(
              this,
              () -> dados.getServico().adicionarAjuste(ecfId, p.getId(), a),
              id -> concluido());
        });
  }

  private void novoSignatario() {
    JTextField nome = new JTextField(30),
        cpf = new JTextField(14),
        crc = new JTextField(20),
        uf = new JTextField(2),
        email = new JTextField(30);
    JComboBox<TipoSignatarioECF> tipo = new JComboBox<>(TipoSignatarioECF.values());
    if (JOptionPane.showConfirmDialog(
            this,
            campos(
                "Nome", nome, "CPF", cpf, "Tipo", tipo, "CRC", crc, "UF CRC", uf, "Email", email),
            "Signatário (sem assinatura digital)",
            JOptionPane.OK_CANCEL_OPTION)
        != JOptionPane.OK_OPTION) return;
    EcfSignatario s = new EcfSignatario();
    s.setNome(nome.getText().trim());
    s.setCpf(cpf.getText().trim());
    s.setTipo((TipoSignatarioECF) tipo.getSelectedItem());
    s.setCrc(crc.getText().trim());
    s.setUfCrc(uf.getText().trim().toUpperCase(java.util.Locale.ROOT));
    s.setEmail(email.getText().trim());
    ComponentesContabeis.carregar(
        this, () -> dados.getServico().adicionarSignatario(ecfId, s), id -> concluido());
  }

  private void remover(String tipo, long id) {
    if (JOptionPane.showConfirmDialog(
            this, "Remover " + tipo + " #" + id + "?", "Remover", JOptionPane.YES_NO_OPTION)
        == JOptionPane.YES_OPTION)
      ComponentesContabeis.executar(
          this, () -> dados.getServico().removerFilhoECF(ecfId, tipo, id), this::concluido);
  }
}
