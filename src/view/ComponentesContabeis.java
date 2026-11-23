package view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public final class ComponentesContabeis {
  private ComponentesContabeis() {}

  public static JPanel campos(Object... pares) {
    JPanel painel = new JPanel(new GridBagLayout());
    for (int i = 0; i < pares.length; i += 2) {
      adicionar_componente(i / 2, painel, pares[i] + ":", (JComponent) pares[i + 1]);
    }
    return painel;
  }

  public static void adicionar_componente(
      int linha, JPanel painel, String rotulo, JComponent componente) {
    GridBagConstraints posicao = new GridBagConstraints();
    posicao.insets = new Insets(4, 4, 4, 4);
    posicao.anchor = GridBagConstraints.WEST;
    posicao.gridx = 0;
    posicao.gridy = linha;
    JLabel label = new JLabel(rotulo);
    label.setLabelFor(componente);
    painel.add(label, posicao);
    posicao.gridx = 1;
    posicao.weightx = 1;
    posicao.fill = GridBagConstraints.HORIZONTAL;
    painel.add(componente, posicao);
  }

  public static JPanel botoes(JButton... botoes) {
    JPanel painel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    for (JButton botao : botoes) {
      painel.add(botao);
    }
    return painel;
  }

  public static JButton botao(String nome, String texto, Runnable acao) {
    JButton botao = new JButton(texto);
    botao.setName(nome);
    botao.addActionListener(e -> acao.run());
    return botao;
  }

  public static JButton botaoInativo(String nome, String texto) {
    JButton botao = new JButton(texto);
    botao.setName(nome);
    botao.setEnabled(false);
    return botao;
  }

  public static JSpinner criarData(String nome, LocalDate data) {
    Date valor = Date.from(data.atStartOfDay(ZoneId.systemDefault()).toInstant());
    JSpinner campo = new JSpinner(new SpinnerDateModel(valor, null, null, java.util.Calendar.DAY_OF_MONTH));
    campo.setName(nome);
    JSpinner.DateEditor editor = new JSpinner.DateEditor(campo, "dd/MM/yyyy");
    editor.getFormat().setLenient(false);
    editor.getTextField().setColumns(10);
    campo.setEditor(editor);
    return campo;
  }

  public static DefaultTableModel modeloTabela(String... colunas) {
    return new DefaultTableModel(colunas, 0) {
      private static final long serialVersionUID = 1L;

      @Override
      public boolean isCellEditable(int linha, int coluna) {
        return false;
      }
    };
  }

  public static JTable tabela(String nome, DefaultTableModel modelo) {
    JTable tabela = new JTable(modelo);
    tabela.setName(nome);
    tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    tabela.setFillsViewportHeight(true);
    tabela.setRowHeight(24);
    return tabela;
  }

  public static JScrollPane lista(String titulo, JTable tabela) {
    JScrollPane scroll = new JScrollPane(tabela);
    scroll.setBorder(BorderFactory.createTitledBorder(titulo));
    return scroll;
  }

  public static JScrollPane texto(String conteudo) {
    JTextArea area = new JTextArea(conteudo, 4, 35);
    area.setEditable(false);
    area.setLineWrap(true);
    area.setWrapStyleWord(true);
    area.setBackground(UIManager.getColor("Panel.background"));
    return new JScrollPane(area);
  }

  public static void abrirDialogo(Component origem, String titulo, JPanel conteudo) {
    Window janela = SwingUtilities.getWindowAncestor(origem);
    JDialog dialogo = new JDialog(janela, titulo, JDialog.ModalityType.APPLICATION_MODAL);
    dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialogo.setContentPane(conteudo);
    dialogo.pack();
    dialogo.setLocationRelativeTo(janela);
    dialogo.setVisible(true);
  }

  public static void fechar(Component componente) {
    Window janela = SwingUtilities.getWindowAncestor(componente);
    if (janela instanceof JDialog) {
      janela.dispose();
    }
  }
}
