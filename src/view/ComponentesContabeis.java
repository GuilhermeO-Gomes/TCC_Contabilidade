package view;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Window;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public final class ComponentesContabeis {
    private ComponentesContabeis() {
    }

    public static void adicionar_componente(int linha, JPanel painel, String rotulo, JComponent componente) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = linha;
        JLabel lbl = new JLabel(rotulo);
        lbl.setLabelFor(componente);
        painel.add(lbl, gbc);
        gbc.gridx = 1;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        painel.add(componente, gbc);
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
        botao.addActionListener(e -> {
            try {
                acao.run();
            } catch (IllegalArgumentException | IllegalStateException problema) {
                JOptionPane.showMessageDialog(botao, problema.getMessage(), "Atenção", JOptionPane.WARNING_MESSAGE);
            }
        });
        return botao;
    }

    public static JSpinner criarData(String nome, LocalDate valor) {
        JSpinner campo = new JSpinner(new SpinnerDateModel());
        campo.setName(nome);
        JSpinner.DateEditor editor = new JSpinner.DateEditor(campo, "dd/MM/yyyy");
        editor.getFormat().setLenient(false);
        editor.getTextField().setColumns(10);
        campo.setEditor(editor);
        definirData(campo, valor);
        return campo;
    }

    public static void definirData(JSpinner campo, LocalDate valor) {
        campo.setValue(Date.from(valor.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        // Mesmo valor não dispara ChangeEvent; limpar também precisa corrigir o texto inválido.
        ((JSpinner.DateEditor) campo.getEditor()).getTextField().setValue(campo.getValue());
    }

    public static LocalDate data(JSpinner campo) {
        try {
            campo.commitEdit();
        } catch (ParseException problema) {
            throw new IllegalArgumentException("Informe uma data válida no formato dd/MM/aaaa.");
        }
        return ((Date) campo.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static void verificarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio.isAfter(fim)) {
            throw new IllegalArgumentException("O início do período deve ser anterior ou igual ao fim.");
        }
    }

    public static BigDecimal valor(String texto) {
        String limpo = texto.trim();
        if (limpo.contains(",")) {
            if (!limpo.matches("(?:\\d+|\\d{1,3}(?:\\.\\d{3})+),\\d{1,2}")) {
                throw new IllegalArgumentException("Informe um valor como 1500,00 ou 1.500,00.");
            }
            limpo = limpo.replace(".", "").replace(',', '.');
        } else if (!limpo.matches("\\d+(?:\\.\\d{1,2})?")) {
            throw new IllegalArgumentException("Informe um valor com até duas casas decimais.");
        }
        return new BigDecimal(limpo).setScale(2, RoundingMode.UNNECESSARY);
    }

    public static String moeda(BigDecimal valor) {
        return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR")).format(valor);
    }

    public static DefaultTableModel modeloTabela(String... colunas) {
        return new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }

            @Override
            public Class<?> getColumnClass(int coluna) {
                if (getRowCount() > 0 && getValueAt(0, coluna) != null) {
                    return getValueAt(0, coluna).getClass();
                }
                return Object.class;
            }
        };
    }

    public static JTable tabela(String nome, DefaultTableModel modelo) {
        JTable tabela = new JTable(modelo);
        tabela.setName(nome);
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoCreateRowSorter(true);
        tabela.setFillsViewportHeight(true);
        tabela.setRowHeight(24);
        DefaultTableCellRenderer formato = new DefaultTableCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void setValue(Object valor) {
                if (valor instanceof BigDecimal) {
                    super.setValue(moeda((BigDecimal) valor));
                } else if (valor instanceof LocalDate) {
                    super.setValue(((LocalDate) valor).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                } else if (valor instanceof LocalDateTime) {
                    super.setValue(((LocalDateTime) valor).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
                } else {
                    super.setValue(valor);
                }
            }
        };
        tabela.setDefaultRenderer(LocalDate.class, formato);
        tabela.setDefaultRenderer(LocalDateTime.class, formato);
        tabela.setDefaultRenderer(BigDecimal.class, formato);
        return tabela;
    }

    public static int linhaSelecionada(JTable tabela) {
        if (tabela.getSelectedRow() < 0) {
            throw new IllegalArgumentException("Selecione uma linha na tabela.");
        }
        return tabela.convertRowIndexToModel(tabela.getSelectedRow());
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
        area.setBackground(javax.swing.UIManager.getColor("Panel.background"));
        return new JScrollPane(area);
    }

    public static void abrirDialogo(Component origem, String titulo, JPanel conteudo) {
        Window janela_pai = SwingUtilities.getWindowAncestor(origem);
        JDialog dialog = new JDialog(janela_pai, titulo, JDialog.ModalityType.APPLICATION_MODAL);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setContentPane(conteudo);
        dialog.pack();
        dialog.setLocationRelativeTo(janela_pai);
        dialog.setVisible(true);
    }

    public static void fechar(Component componente) {
        Window janela = SwingUtilities.getWindowAncestor(componente);
        if (janela instanceof JDialog) {
            janela.dispose();
        }
    }
}
