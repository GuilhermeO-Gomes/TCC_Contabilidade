package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class TelaConciliacaoContabil extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField txtInicio = new JTextField(10),
            txtFim = new JTextField(10),
            txtArq = new JTextField(25),
            txtTotalSis = new JTextField("0,00", 12),
            txtTotalExt = new JTextField("0,00", 12),
            txtDif = new JTextField("0,00", 12);

    private final JComboBox<String> cbConta = new JComboBox<>(new String[] {"Selecione uma conta"}),
            cbSit = new JComboBox<>(new String[] {"Todos", "Pendentes", "Conciliados"});

    // A importação, a conferência dos valores e a gravação ficam no controller.
    private final JButton btCarregar = new JButton("Carregar movimentos"),
            btImportar = new JButton("Importar extrato"),
            btConciliar = new JButton("Salvar conciliação"),
            btLimpar = new JButton("Limpar");

    private final DefaultTableModel modeloSis = new DefaultTableModel(
            new Object[] {"Código", "Data", "Documento", "Histórico", "Natureza", "Valor (R$)", "Situação"}, 0
    ) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };

    private final DefaultTableModel modeloExt = new DefaultTableModel(
            new Object[] {"Código", "Data", "Documento", "Descrição", "Natureza", "Valor (R$)", "Situação"}, 0
    ) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };

    private final JTable tabelaSis = new JTable(modeloSis),
            tabelaExt = new JTable(modeloExt);

    private final JButton btRevisar = new JButton("Conferir conciliação");
    private JDialog dialogo;
    private final JTabbedPane abas = new JTabbedPane();

    public TelaConciliacaoContabil() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
    }

    private void montar() {
        JPanel f = new JPanel(new GridBagLayout());
        f.setBorder(BorderFactory.createTitledBorder("Filtros e pesquisa"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        JPanel periodo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        periodo.add(txtInicio);
        periodo.add(new JLabel("  até  "));
        periodo.add(txtFim);
        componente(f, g, 0, "Período (dd/mm/aaaa):", periodo);
        componente(f, g, 1, "Conta contábil:", cbConta);
        componente(f, g, 2, "Situação:", cbSit);
        componente(f, g, 3, "Arquivo do extrato:", txtArq);
        txtArq.setEditable(false);

        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        b.add(btCarregar);
        b.add(btImportar);
        b.add(btLimpar);

        JPanel n = new JPanel(new BorderLayout(4, 4));
        n.add(f, BorderLayout.CENTER);
        n.add(b, BorderLayout.SOUTH);
        add(n, BorderLayout.NORTH);

        abas.addTab("Lançamentos do sistema", montarAba(tabelaSis, "Lançamentos do sistema"));
        abas.addTab("Extrato externo", montarAba(tabelaExt, "Movimentos do extrato"));
        add(abas, BorderLayout.CENTER);

        JPanel totais = new JPanel(new GridLayout(1, 3, 8, 0));
        totais.setBorder(BorderFactory.createTitledBorder("Totais dos movimentos selecionados"));
        totais.add(montarTotal("Sistema (R$)", txtTotalSis));
        totais.add(montarTotal("Extrato (R$)", txtTotalExt));
        totais.add(montarTotal("Diferença (R$)", txtDif));

        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        acoes.add(btRevisar);
        acoes.add(new JLabel("Selecione os movimentos correspondentes nas duas abas."));

        JPanel rodape = new JPanel(new BorderLayout(4, 4));
        rodape.add(totais, BorderLayout.CENTER);
        rodape.add(acoes, BorderLayout.SOUTH);
        add(rodape, BorderLayout.SOUTH);
        btRevisar.addActionListener(e -> conferir());

        btLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpar();
            }
        });
    }

    private JPanel montarAba(JTable tabela, String titulo) {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        painel.add(montarTabela(tabela, titulo), BorderLayout.CENTER);
        return painel;
    }

    private JPanel montarTotal(String titulo, JTextField campo) {
        JPanel painel = new JPanel(new BorderLayout(4, 4));
        painel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        painel.add(new JLabel(titulo), BorderLayout.NORTH);
        painel.add(campo, BorderLayout.CENTER);
        campo.setEditable(false);
        campo.setHorizontalAlignment(JTextField.RIGHT);
        return painel;
    }

    private void conferir() {
        if (tabelaSis.getSelectedRowCount() == 0 || tabelaExt.getSelectedRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Selecione os movimentos do sistema e do extrato.");
            return;
        }
        dialogo = new JDialog(SwingUtilities.getWindowAncestor(this),
                "Conferir conciliação", JDialog.ModalityType.APPLICATION_MODAL);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel dados = new JPanel(new GridLayout(2, 1, 4, 4));
        dados.add(new JLabel("Conta: " + cbConta.getSelectedItem()));
        dados.add(new JLabel("Período: " + txtInicio.getText() + " até " + txtFim.getText()));
        painel.add(dados, BorderLayout.NORTH);

        JPanel movimentos = new JPanel(new GridLayout(2, 1, 0, 8));
        movimentos.add(montarTabela(copiarSelecionados(tabelaSis), "Movimentos selecionados do sistema"));
        movimentos.add(montarTabela(copiarSelecionados(tabelaExt), "Movimentos selecionados do extrato"));
        painel.add(movimentos, BorderLayout.CENTER);

        JPanel resumo = new JPanel(new GridLayout(1, 3, 8, 0));
        resumo.setBorder(BorderFactory.createTitledBorder("Totais dos movimentos selecionados"));
        resumo.add(montarTotal("Sistema (R$)", new JTextField(txtTotalSis.getText(), 12)));
        resumo.add(montarTotal("Extrato (R$)", new JTextField(txtTotalExt.getText(), 12)));
        resumo.add(montarTotal("Diferença (R$)", new JTextField(txtDif.getText(), 12)));

        JButton btVoltar = new JButton("Voltar");
        btVoltar.addActionListener(e -> dialogo.dispose());
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        // O controller valida os valores e associa os movimentos pelo botão original.
        botoes.add(btConciliar);
        botoes.add(btVoltar);
        JPanel rodape = new JPanel(new BorderLayout(4, 4));
        rodape.add(resumo, BorderLayout.CENTER);
        rodape.add(botoes, BorderLayout.SOUTH);
        painel.add(rodape, BorderLayout.SOUTH);
        dialogo.add(painel);
        dialogo.setSize(900, 620);
        dialogo.setMinimumSize(new Dimension(760, 500));
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private JTable copiarSelecionados(JTable origem) {
        DefaultTableModel copia = new DefaultTableModel(
                new Object[] {"Código", "Data", "Documento", origem.getColumnName(3),
                        "Natureza", "Valor (R$)", "Situação"}, 0) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int l, int c) {
                return false;
            }
        };
        for (int linha : origem.getSelectedRows()) {
            Object[] dados = new Object[origem.getColumnCount()];
            for (int coluna = 0; coluna < dados.length; coluna++) {
                dados[coluna] = origem.getValueAt(linha, coluna);
            }
            copia.addRow(dados);
        }
        return new JTable(copia);
    }

    private JScrollPane montarTabela(JTable tab, String titulo) {
        tab.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tab.setRowHeight(24);
        tab.setFillsViewportHeight(true);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getColumnModel().getColumn(1).setPreferredWidth(90);
        tab.getColumnModel().getColumn(2).setPreferredWidth(100);
        tab.getColumnModel().getColumn(3).setPreferredWidth(220);
        tab.getColumnModel().getColumn(4).setPreferredWidth(90);
        tab.getColumnModel().getColumn(5).setPreferredWidth(100);
        DefaultTableCellRenderer moeda = new DefaultTableCellRenderer();
        moeda.setHorizontalAlignment(JLabel.RIGHT);
        tab.getColumnModel().getColumn(5).setCellRenderer(moeda);
        tab.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane sp = new JScrollPane(tab);
        sp.setBorder(BorderFactory.createTitledBorder(titulo));
        return sp;
    }

    private void componente(JPanel painel, GridBagConstraints organizador,
            int linha, String texto, Component campo) {
        organizador.gridx = 0;
        organizador.gridy = linha;
        organizador.gridwidth = 1;
        organizador.weightx = 0;
        organizador.fill = GridBagConstraints.NONE;
        painel.add(new JLabel(texto), organizador);

        organizador.gridx = 1;
        organizador.weightx = 1;
        organizador.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campo, organizador);
    }

    public void limpar() {
        txtInicio.setText("");
        txtFim.setText("");
        cbConta.setSelectedIndex(cbConta.getItemCount() > 0 ? 0 : -1);
        cbSit.setSelectedIndex(0);
        txtArq.setText("");
        abas.setSelectedIndex(0);
        tabelaSis.clearSelection();
        tabelaExt.clearSelection();
        modeloSis.setRowCount(0);
        modeloExt.setRowCount(0);
        txtTotalSis.setText("0,00");
        txtTotalExt.setText("0,00");
        txtDif.setText("0,00");
    }

    public JTextField getTxtInicio() {
        return txtInicio;
    }

    public JTextField getTxtFim() {
        return txtFim;
    }

    public JTextField getTxtArq() {
        return txtArq;
    }

    public JTextField getTxtTotalSis() {
        return txtTotalSis;
    }

    public JTextField getTxtTotalExt() {
        return txtTotalExt;
    }

    public JTextField getTxtDif() {
        return txtDif;
    }

    public JComboBox<String> getCbConta() {
        return cbConta;
    }

    public JComboBox<String> getCbSit() {
        return cbSit;
    }

    public JButton getBtCarregar() {
        return btCarregar;
    }

    public JButton getBtImportar() {
        return btImportar;
    }

    public JButton getBtConciliar() {
        return btConciliar;
    }

    public JButton getBtLimpar() {
        return btLimpar;
    }

    public JTable getTabSis() {
        return tabelaSis;
    }

    public JTable getTabExt() {
        return tabelaExt;
    }

    public JButton getBtRevisar() {
        return btRevisar;
    }

    public JTabbedPane getAbas() {
        return abas;
    }

    public JDialog getDialogo() {
        return dialogo;
    }
}
