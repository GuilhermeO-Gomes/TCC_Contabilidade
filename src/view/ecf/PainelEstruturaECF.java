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
    setPreferredSize(new Dimension(1000, 550));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JTabbedPane abas = new JTabbedPane();
    JPanel apuracoes = new JPanel(new BorderLayout());
    JTable tabelaApuracoes = ComponentesContabeis.tabela("ecf.apuracoes", ComponentesContabeis.modeloTabela(
        "ID", "Tributo", "Início", "Fim", "Base inicial", "Adições", "Exclusões",
        "Compensações", "Base informada", "Valor do tributo"));
    apuracoes.add(ComponentesContabeis.lista("Apurações", tabelaApuracoes));
    apuracoes.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.apuracao.nova", "Nova", this::novaApuracao),
        ComponentesContabeis.botaoInativo("ecf.apuracao.editar", "Editar"),
        ComponentesContabeis.botaoInativo("ecf.apuracao.remover", "Remover")), BorderLayout.SOUTH);
    abas.addTab("Apurações", apuracoes);

    JPanel ajustes = new JPanel(new BorderLayout());
    JTable tabelaAjustes = ComponentesContabeis.tabela("ecf.ajustes", ComponentesContabeis.modeloTabela(
        "ID", "Apuração", "Tipo", "Descrição", "Valor"));
    ajustes.add(ComponentesContabeis.lista("Ajustes", tabelaAjustes));
    ajustes.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.ajuste.novo", "Novo", this::novoAjuste),
        ComponentesContabeis.botaoInativo("ecf.ajuste.remover", "Remover")), BorderLayout.SOUTH);
    abas.addTab("Ajustes", ajustes);

    JPanel signatarios = new JPanel(new BorderLayout());
    JTable tabelaSignatarios = ComponentesContabeis.tabela("ecf.signatarios", ComponentesContabeis.modeloTabela(
        "ID", "Nome", "Tipo", "CPF", "CRC", "UF", "Email"));
    signatarios.add(ComponentesContabeis.lista("Signatários", tabelaSignatarios));
    signatarios.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.signatario.novo", "Novo", this::novoSignatario),
        ComponentesContabeis.botaoInativo("ecf.signatario.remover", "Remover")), BorderLayout.SOUTH);
    abas.addTab("Signatários", signatarios);

    add(abas, BorderLayout.CENTER);
    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("ecf.estrutura.atualizar", "Atualizar"),
        ComponentesContabeis.botao("ecf.estrutura.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }

  private void novaApuracao() {
    LocalDate hoje = LocalDate.now();
    JPanel formulario = ComponentesContabeis.campos(
        "Tributo", new JComboBox<>(new String[] {"IRPJ", "CSLL"}),
        "Início", ComponentesContabeis.criarData("apuracao.inicio", hoje.withDayOfYear(1)),
        "Fim", ComponentesContabeis.criarData("apuracao.fim", hoje.withMonth(12).withDayOfMonth(31)),
        "Base inicial", new JTextField(12), "Base de cálculo", new JTextField(12),
        "Alíquota (opcional)", new JTextField(8), "Valor do tributo", new JTextField(12));
    JOptionPane.showMessageDialog(this, formulario, "Apuração", JOptionPane.PLAIN_MESSAGE);
  }

  private void novoAjuste() {
    JPanel formulario = ComponentesContabeis.campos(
        "Apuração", new JComboBox<String>(),
        "Tipo", new JComboBox<>(new String[] {"Adição", "Exclusão", "Compensação"}),
        "Código (opcional)", new JTextField(20), "Descrição", new JTextField(30),
        "Valor", new JTextField(12), "Conta (opcional)", new JComboBox<String>(),
        "Documento", new JTextField(20), "Observação", new JTextField(30));
    JOptionPane.showMessageDialog(this, formulario, "Ajuste", JOptionPane.PLAIN_MESSAGE);
  }

  private void novoSignatario() {
    JPanel formulario = ComponentesContabeis.campos(
        "Nome", new JTextField(30), "CPF", new JTextField(14),
        "Tipo", new JComboBox<>(new String[] {"Responsável legal", "Contador", "Outro"}),
        "CRC", new JTextField(20), "UF do CRC", new JTextField(2), "Email", new JTextField(30));
    JOptionPane.showMessageDialog(this, formulario, "Signatário", JOptionPane.PLAIN_MESSAGE);
  }
}
