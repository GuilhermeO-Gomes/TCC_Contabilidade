package view.ecf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import javax.swing.*;
import view.ComponentesContabeis;

public class PainelEstruturaECF extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelEstruturaECF() {
    super(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(920, 550));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JTabbedPane abas = new JTabbedPane();
    abas.addTab("Saldos contábeis", lista("ecf.saldos", "Saldos contábeis",
        "Conta", "Natureza", "Saldo inicial", "Débitos", "Créditos", "Saldo final"));
    abas.addTab("Balanço Patrimonial", lista("ecf.balanco", "Balanço Patrimonial",
        "Grupo", "Conta", "Saldo"));
    abas.addTab("DRE", lista("ecf.dre", "Demonstração do Resultado",
        "Grupo", "Conta", "Valor"));
    abas.addTab("De-Para", lista("ecf.depara", "Mapeamento para o Plano Referencial",
        "Conta analítica", "Código referencial", "Conta referencial"));

    JPanel apuracoes = new JPanel(new BorderLayout());
    apuracoes.add(lista("ecf.apuracoes", "Apuração de IRPJ e CSLL",
        "Tributo", "Início", "Fim", "Base inicial", "Adições", "Exclusões",
        "Compensações", "Base de cálculo", "Valor do tributo"));
    apuracoes.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.apuracao.nova", "Nova apuração", this::novaApuracao),
        ComponentesContabeis.botaoInativo("ecf.apuracao.editar", "Editar"),
        ComponentesContabeis.botaoInativo("ecf.apuracao.remover", "Remover")), BorderLayout.SOUTH);
    abas.addTab("Apurações", apuracoes);

    JPanel ajustes = new JPanel(new BorderLayout());
    ajustes.add(lista("ecf.ajustes", "Ajustes do Lucro Real", "Apuração", "Tipo", "Descrição", "Valor"));
    ajustes.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.ajuste.novo", "Novo ajuste", this::novoAjuste),
        ComponentesContabeis.botaoInativo("ecf.ajuste.remover", "Remover")), BorderLayout.SOUTH);
    abas.addTab("Ajustes", ajustes);

    JPanel signatarios = new JPanel(new BorderLayout());
    signatarios.add(new JLabel("Obrigatórios: responsável legal (nome e CPF) e contador (nome, CPF e CRC)."),
        BorderLayout.NORTH);
    signatarios.add(lista("ecf.signatarios", "Signatários", "Função", "Nome", "CPF", "CRC", "UF", "E-mail"));
    signatarios.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.signatario.novo", "Novo signatário", this::novoSignatario),
        ComponentesContabeis.botaoInativo("ecf.signatario.remover", "Remover")), BorderLayout.SOUTH);
    abas.addTab("Signatários", signatarios);

    add(new JLabel("Regime tributário: —"), BorderLayout.NORTH);
    add(abas, BorderLayout.CENTER);
    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.estrutura.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }

  private JScrollPane lista(String nome, String titulo, String... colunas) {
    JTable tabela = ComponentesContabeis.tabela(nome, ComponentesContabeis.modeloTabela(colunas));
    return ComponentesContabeis.lista(titulo, tabela);
  }

  private void novaApuracao() {
    LocalDate hoje = LocalDate.now();
    JPanel formulario = ComponentesContabeis.campos(
        "Tributo", new JComboBox<>(new String[] {"IRPJ", "CSLL"}),
        "Data inicial", ComponentesContabeis.criarData("apuracao.inicio", hoje.withDayOfYear(1)),
        "Data final", ComponentesContabeis.criarData("apuracao.fim", hoje.withMonth(12).withDayOfMonth(31)),
        "Base inicial", new JTextField(12), "Base de cálculo", new JLabel("—"),
        "Alíquota", new JTextField(8), "Valor do tributo", new JLabel("—"));
    JOptionPane.showMessageDialog(this, formulario, "Apuração", JOptionPane.PLAIN_MESSAGE);
  }

  private void novoAjuste() {
    JPanel formulario = ComponentesContabeis.campos(
        "Apuração", new JComboBox<String>(),
        "Tipo", new JComboBox<>(new String[] {"Adição", "Exclusão", "Compensação de prejuízo fiscal"}),
        "Descrição", new JTextField(30), "Valor", new JTextField(12),
        "Conta", new JComboBox<String>(), "Documento", new JTextField(20));
    JOptionPane.showMessageDialog(this, formulario, "Ajuste do Lucro Real", JOptionPane.PLAIN_MESSAGE);
  }

  private void novoSignatario() {
    JComboBox<String> funcao = new JComboBox<>(new String[] {"Responsável legal", "Contador"});
    JTextField crc = new JTextField(20);
    crc.setEnabled(false);
    funcao.addActionListener(e -> crc.setEnabled(funcao.getSelectedIndex() == 1));
    JPanel formulario = ComponentesContabeis.campos(
        "Função", funcao, "Nome", new JTextField(30), "CPF", new JTextField(14),
        "CRC (obrigatório para contador)", crc, "UF do CRC", new JTextField(2),
        "E-mail", new JTextField(30));
    JOptionPane.showMessageDialog(this, formulario, "Signatário", JOptionPane.PLAIN_MESSAGE);
  }
}
