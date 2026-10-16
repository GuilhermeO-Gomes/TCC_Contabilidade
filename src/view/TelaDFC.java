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
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.DFC;
import model.DfcLinhaRelatorio;
import model.DfcRelatorio;

public class TelaDFC extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField txtInicio = new JTextField(10),
            txtFim = new JTextField(10),
            txtSaldoIni = new JTextField("0,00", 12),
            txtVar = new JTextField("0,00", 12),
            txtSaldoFim = new JTextField("0,00", 12);

    private long empresaId;
    private final JTextField txtEmpresa = new JTextField(25),
            txtMetodo = new JTextField(15);
    private final JComboBox<DFC> cbDFC = new JComboBox<>();
    private final JButton btGerar = new JButton("Gerar DFC"),
            btPDF = new JButton("Exportar PDF"),
            btExcel = new JButton("Exportar Excel"),
            btLimpar = new JButton("Limpar");

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[] {"Atividade", "Descrição", "Entradas (R$)", "Saídas (R$)", "Fluxo líquido (R$)"}, 0
    ) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };

    private final JTable tabela = new JTable(modelo);
    private final JButton btVisualizar = new JButton("Visualizar demonstrativo");
    private JDialog dialogo;

    public TelaDFC() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
        DocumentListener periodo = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                limparResultado();
            }

            public void removeUpdate(DocumentEvent e) {
                limparResultado();
            }

            public void changedUpdate(DocumentEvent e) {
                limparResultado();
            }
        };
        txtInicio.getDocument().addDocumentListener(periodo);
        txtFim.getDocument().addDocumentListener(periodo);
        txtEmpresa.setEditable(false);
        txtMetodo.setEditable(false);
        limparResultado();
        btGerar.setEnabled(false);
        cbDFC.addActionListener(e -> {
            DFC configuracao = (DFC) cbDFC.getSelectedItem();
            txtMetodo.setText(configuracao == null ? "" : configuracao.getMetodo());
            limparResultado();
            btGerar.setEnabled(configuracao != null);
        });
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

        componente(f, g, 1, "Empresa:", txtEmpresa);
        componente(f, g, 2, "Configuração DFC:", cbDFC);
        componente(f, g, 3, "Método:", txtMetodo);

        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        b.add(btGerar);
        b.add(btLimpar);

        JPanel n = new JPanel(new BorderLayout(4, 4));
        n.add(f, BorderLayout.CENTER);
        n.add(b, BorderLayout.SOUTH);
        add(n, BorderLayout.NORTH);

        add(montarTabela(tabela,
                "Atividades operacionais, de investimento e de financiamento"), BorderLayout.CENTER);

        JPanel totais = new JPanel(new GridLayout(1, 3, 8, 0));
        totais.setBorder(BorderFactory.createTitledBorder("Caixa e equivalentes de caixa"));
        totais.add(montarTotal("Saldo inicial (R$)", txtSaldoIni));
        totais.add(montarTotal("Variação do período (R$)", txtVar));
        totais.add(montarTotal("Saldo final (R$)", txtSaldoFim));
        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        acoes.add(btVisualizar);
        acoes.add(btPDF);
        acoes.add(btExcel);
        JPanel rodape = new JPanel(new BorderLayout(4, 4));
        rodape.add(totais, BorderLayout.CENTER);
        rodape.add(acoes, BorderLayout.SOUTH);
        add(rodape, BorderLayout.SOUTH);
        btVisualizar.addActionListener(e -> visualizar());

        btLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpar();
            }
        });
    }

    private JScrollPane montarTabela(JTable tab, String titulo) {
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tab.setRowHeight(24);
        tab.setFillsViewportHeight(true);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.getColumnModel().getColumn(0).setPreferredWidth(150);
        tab.getColumnModel().getColumn(1).setPreferredWidth(260);
        DefaultTableCellRenderer moeda = new DefaultTableCellRenderer();
        moeda.setHorizontalAlignment(JLabel.RIGHT);
        for (int coluna = 2; coluna < tab.getColumnCount(); coluna++) {
            tab.getColumnModel().getColumn(coluna).setCellRenderer(moeda);
        }
        JScrollPane sp = new JScrollPane(tab);
        sp.setBorder(BorderFactory.createTitledBorder(titulo));
        return sp;
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

    private void visualizar() {
        if (modelo.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Gere a DFC antes de visualizar o demonstrativo.");
            return;
        }
        dialogo = new JDialog(SwingUtilities.getWindowAncestor(this),
                "Demonstração dos Fluxos de Caixa", JDialog.ModalityType.APPLICATION_MODAL);
        dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painel.add(new JLabel("Período: " + txtInicio.getText() + " até " + txtFim.getText()),
                BorderLayout.NORTH);

        JTable tab = new JTable(modelo);
        painel.add(montarTabela(tab, "Demonstração dos Fluxos de Caixa"), BorderLayout.CENTER);

        JPanel resumo = new JPanel(new GridLayout(1, 3, 8, 0));
        resumo.setBorder(BorderFactory.createTitledBorder("Caixa e equivalentes de caixa"));
        resumo.add(montarTotal("Saldo inicial (R$)", new JTextField(txtSaldoIni.getText(), 12)));
        resumo.add(montarTotal("Variação do período (R$)", new JTextField(txtVar.getText(), 12)));
        resumo.add(montarTotal("Saldo final (R$)", new JTextField(txtSaldoFim.getText(), 12)));

        JButton btFechar = new JButton("Fechar");
        btFechar.addActionListener(e -> dialogo.dispose());
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        botoes.add(btFechar);
        JPanel rodape = new JPanel(new BorderLayout(4, 4));
        rodape.add(resumo, BorderLayout.CENTER);
        rodape.add(botoes, BorderLayout.SOUTH);
        painel.add(rodape, BorderLayout.SOUTH);
        dialogo.add(painel);
        dialogo.setSize(860, 560);
        dialogo.setMinimumSize(new Dimension(700, 420));
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
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
        cbDFC.setSelectedIndex(-1);
        limparResultado();
    }

    public void limparResultado() {
        tabela.clearSelection();
        modelo.setRowCount(0);
        txtSaldoIni.setText("0,00");
        txtVar.setText("0,00");
        txtSaldoFim.setText("0,00");
        btVisualizar.setEnabled(false);
        btPDF.setEnabled(false);
        btExcel.setEnabled(false);
    }

    public void setContexto(long empresaId, String empresa) {
        this.empresaId = empresaId;
        txtEmpresa.setText(empresa);
        cbDFC.removeAllItems();
        limpar();
    }

    public void carregarConfiguracoes(List<DFC> configuracoes) {
        cbDFC.removeAllItems();
        for (DFC configuracao : configuracoes) {
            if (configuracao.getEmpresaId() == empresaId && configuracao.isAtivo()) {
                cbDFC.addItem(configuracao);
            }
        }
        cbDFC.setSelectedIndex(-1);
    }

    public void exibirRelatorio(DfcRelatorio relatorio) {
        DFC configuracao = (DFC) cbDFC.getSelectedItem();
        if (configuracao == null || relatorio.getDfcId() != configuracao.getId()
                || relatorio.getEmpresaId() != empresaId) {
            throw new IllegalArgumentException("O relatório não pertence à configuração selecionada.");
        }
        if (relatorio.getDataInicio() == null || relatorio.getDataFim() == null
                || relatorio.getDataInicio().isAfter(relatorio.getDataFim())
                || relatorio.getSaldoInicial().add(relatorio.getVariacaoCaixa()).compareTo(relatorio.getSaldoFinal()) != 0) {
            throw new IllegalArgumentException("Confira o período e a consistência dos saldos da DFC.");
        }
        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtInicio.setText(relatorio.getDataInicio().format(formato));
        txtFim.setText(relatorio.getDataFim().format(formato));
        limparResultado();
        java.util.ArrayList<DfcLinhaRelatorio> linhas = new java.util.ArrayList<>(relatorio.getLinhas());
        linhas.sort(java.util.Comparator.comparingInt(DfcLinhaRelatorio::getOrdem));
        java.util.Map<Long, DfcLinhaRelatorio> porId = new java.util.HashMap<>();
        for (DfcLinhaRelatorio linha : linhas) {
            porId.put(linha.getItemId(), linha);
        }
        for (DfcLinhaRelatorio linha : linhas) {
            String descricao = linha.getDescricao();
            Long superior = linha.getItemSuperiorId();
            java.util.HashSet<Long> visitados = new java.util.HashSet<>();
            visitados.add(linha.getItemId());
            while (superior != null && porId.containsKey(superior) && visitados.add(superior)) {
                descricao = "    " + descricao;
                superior = porId.get(superior).getItemSuperiorId();
            }
            modelo.addRow(new Object[] {linha.getGrupo(), descricao,
                    linha.getEntradas(), linha.getSaidas(), linha.getFluxoLiquido()});
        }
        java.text.NumberFormat moeda = java.text.NumberFormat.getNumberInstance(
                new java.util.Locale("pt", "BR"));
        moeda.setMinimumFractionDigits(2);
        moeda.setMaximumFractionDigits(2);
        txtSaldoIni.setText(moeda.format(relatorio.getSaldoInicial()));
        txtVar.setText(moeda.format(relatorio.getVariacaoCaixa()));
        txtSaldoFim.setText(moeda.format(relatorio.getSaldoFinal()));
        boolean temLinhas = modelo.getRowCount() > 0;
        btVisualizar.setEnabled(temLinhas);
        btPDF.setEnabled(temLinhas);
        btExcel.setEnabled(temLinhas);
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public JComboBox<DFC> getCbDFC() {
        return cbDFC;
    }

    public JTextField getTxtMetodo() {
        return txtMetodo;
    }

    public JTextField getTxtInicio() {
        return txtInicio;
    }

    public JTextField getTxtFim() {
        return txtFim;
    }

    public JTextField getTxtSaldoIni() {
        return txtSaldoIni;
    }

    public JTextField getTxtVar() {
        return txtVar;
    }

    public JTextField getTxtSaldoFim() {
        return txtSaldoFim;
    }

    public JButton getBtGerar() {
        return btGerar;
    }

    public JButton getBtPDF() {
        return btPDF;
    }

    public JButton getBtExcel() {
        return btExcel;
    }

    public JButton getBtLimpar() {
        return btLimpar;
    }

    public JTable getTab() {
        return tabela;
    }

    public JButton getBtVisualizar() {
        return btVisualizar;
    }

    public JDialog getDialogo() {
        return dialogo;
    }
}
