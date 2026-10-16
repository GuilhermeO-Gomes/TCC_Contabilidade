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
import java.util.ArrayList;
import java.util.List;

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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.ConciliacaoContabil;
import model.ConciliacaoItem;
import model.MovimentoConciliacao;

public class TelaConciliacaoContabil extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField txtInicio = new JTextField(10),
            txtFim = new JTextField(10),
            txtArq = new JTextField(25),
            txtTotalSis = new JTextField("0,00", 12),
            txtTotalExt = new JTextField("0,00", 12),
            txtDif = new JTextField("0,00", 12);

    private long empresaId;
    private ConciliacaoContabil selecionada;
    private final ArrayList<ConciliacaoItem> itens = new ArrayList<>();
    private final ArrayList<MovimentoConciliacao> movimentosSistema = new ArrayList<>();
    private final ArrayList<MovimentoConciliacao> movimentosExtrato = new ArrayList<>();
    private final JTextField txtObservacaoItem = new JTextField(25);
    private final JTextField txtEmpresa = new JTextField(25),
            txtObservacao = new JTextField(30);
    private final JComboBox<Long> cbConta = new JComboBox<>();
    private final JComboBox<String> cbSit = new JComboBox<>(
            new String[] {"Todos", "PENDENTE", "CONCILIADO", "DIVERGENTE", "IGNORADO"});
    private final JTextField txtStatus = new JTextField("PENDENTE", 15);
    private final JComboBox<String> cbStatusItem = new JComboBox<>(
            new String[] {"PENDENTE", "CONCILIADO", "DIVERGENTE", "IGNORADO"});
    private final JButton btFinalizar = new JButton("Finalizar conciliação"),
            btCancelar = new JButton("Cancelar conciliação");

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
        DocumentListener periodo = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                limparMovimentos();
            }

            public void removeUpdate(DocumentEvent e) {
                limparMovimentos();
            }

            public void changedUpdate(DocumentEvent e) {
                limparMovimentos();
            }
        };
        txtInicio.getDocument().addDocumentListener(periodo);
        txtFim.getDocument().addDocumentListener(periodo);
        txtEmpresa.setEditable(false);
        txtStatus.setEditable(false);
        setStatus("PENDENTE");
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
        componente(f, g, 1, "Conta bancária:", cbConta);
        componente(f, g, 2, "Situação do item:", cbSit);
        componente(f, g, 3, "Arquivo do extrato:", txtArq);
        componente(f, g, 4, "Empresa:", txtEmpresa);
        componente(f, g, 5, "Status da conciliação:", txtStatus);
        componente(f, g, 6, "Observação:", txtObservacao);
        txtArq.setEditable(false);

        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        b.add(btCarregar);
        b.add(btImportar);
        b.add(btLimpar);
        b.add(btFinalizar);
        b.add(btCancelar);

        JPanel n = new JPanel(new BorderLayout(4, 4));
        n.add(f, BorderLayout.CENTER);
        n.add(b, BorderLayout.SOUTH);
        add(n, BorderLayout.NORTH);

        abas.addTab("Movimentos financeiros do sistema", montarAba(tabelaSis, "Movimentos financeiros do sistema"));
        abas.addTab("Extrato externo", montarAba(tabelaExt, "Movimentos do extrato"));
        add(abas, BorderLayout.CENTER);

        JPanel totais = new JPanel(new GridLayout(1, 3, 8, 0));
        totais.setBorder(BorderFactory.createTitledBorder("Totais dos movimentos selecionados"));
        totais.add(montarTotal("Sistema (R$)", txtTotalSis));
        totais.add(montarTotal("Extrato (R$)", txtTotalExt));
        totais.add(montarTotal("Diferença (R$)", txtDif));

        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        acoes.add(btRevisar);
        acoes.add(new JLabel("Status do par:"));
        acoes.add(cbStatusItem);
        acoes.add(new JLabel("Selecione um movimento em cada aba."));

        JPanel rodape = new JPanel(new BorderLayout(4, 4));
        rodape.add(totais, BorderLayout.CENTER);
        rodape.add(acoes, BorderLayout.SOUTH);
        add(rodape, BorderLayout.SOUTH);
        btRevisar.addActionListener(e -> conferir());
        cbConta.addActionListener(e -> limparMovimentos());
        tabelaSis.getSelectionModel().addListSelectionListener(e -> atualizarSelecao());
        tabelaExt.getSelectionModel().addListSelectionListener(e -> atualizarSelecao());

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
        if (tabelaSis.getSelectedRowCount() != 1 || tabelaExt.getSelectedRowCount() != 1) {
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
        botoes.add(new JLabel("Observação do par:"));
        botoes.add(txtObservacaoItem);
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
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
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
        selecionada = null;
        itens.clear();
        movimentosSistema.clear();
        movimentosExtrato.clear();
        txtObservacaoItem.setText("");
        txtInicio.setText("");
        txtFim.setText("");
        cbConta.setSelectedIndex(-1);
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
        txtObservacao.setText("");
        cbStatusItem.setSelectedIndex(0);
        setStatus("PENDENTE");
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

    public JComboBox<Long> getCbConta() {
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

    private void limparMovimentos() {
        itens.clear();
        movimentosSistema.clear();
        movimentosExtrato.clear();
        txtArq.setText("");
        modeloSis.setRowCount(0);
        modeloExt.setRowCount(0);
        txtTotalSis.setText("0,00");
        txtTotalExt.setText("0,00");
        txtDif.setText("0,00");
        atualizarSelecao();
    }

    private void atualizarSelecao() {
        boolean editavel = "PENDENTE".equals(txtStatus.getText())
                || "EM_ANALISE".equals(txtStatus.getText());
        boolean par = cbConta.getSelectedItem() != null
                && tabelaSis.getSelectedRow() != -1 && tabelaExt.getSelectedRow() != -1;
        btRevisar.setEnabled(editavel && par);
        btConciliar.setEnabled(editavel && par);
        cbStatusItem.setSelectedItem("PENDENTE");
        txtObservacaoItem.setText("");
        if (par) {
            long financeiro = ((Number) tabelaSis.getValueAt(tabelaSis.getSelectedRow(), 0)).longValue();
            long extrato = ((Number) tabelaExt.getValueAt(tabelaExt.getSelectedRow(), 0)).longValue();
            for (ConciliacaoItem item : itens) {
                if (item.getMovimentoFinanceiroRefId() == financeiro && item.getMovimentoExtratoRefId() == extrato) {
                    cbStatusItem.setSelectedItem(item.getStatus());
                    txtObservacaoItem.setText(item.getObservacao());
                    break;
                }
            }
        }
        java.math.BigDecimal sistema = valorSelecionado(tabelaSis);
        java.math.BigDecimal extrato = valorSelecionado(tabelaExt);
        java.text.NumberFormat formato = java.text.NumberFormat.getNumberInstance(new java.util.Locale("pt", "BR"));
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        txtTotalSis.setText(formato.format(sistema));
        txtTotalExt.setText(formato.format(extrato));
        txtDif.setText(formato.format(sistema.subtract(extrato)));
    }

    private java.math.BigDecimal valorSelecionado(JTable tabela) {
        int linha = tabela.getSelectedRow();
        if (linha == -1 || tabela.getValueAt(linha, 5) == null) {
            return java.math.BigDecimal.ZERO;
        }
        return new java.math.BigDecimal(tabela.getValueAt(linha, 5).toString());
    }

    public void setContexto(long empresaId, String empresa) {
        this.empresaId = empresaId;
        txtEmpresa.setText(empresa);
        cbConta.removeAllItems();
        limpar();
    }

    public void setStatus(String status) {
        txtStatus.setText(status);
        boolean editavel = "PENDENTE".equals(status) || "EM_ANALISE".equals(status);
        txtInicio.setEditable(editavel);
        txtFim.setEditable(editavel);
        txtObservacao.setEditable(editavel);
        cbConta.setEnabled(editavel);
        cbStatusItem.setEnabled(editavel);
        btImportar.setEnabled(editavel);
        btFinalizar.setEnabled(editavel);
        btCancelar.setEnabled(editavel);
        atualizarSelecao();
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public JTextField getTxtStatus() {
        return txtStatus;
    }

    public JTextField getTxtObservacao() {
        return txtObservacao;
    }

    public JComboBox<String> getCbStatusItem() {
        return cbStatusItem;
    }

    public JButton getBtFinalizar() {
        return btFinalizar;
    }

    public JButton getBtCancelar() {
        return btCancelar;
    }

    public void carregarCombo(JComboBox<Long> combo, java.util.Map<Long, String> opcoes) {
        final java.util.Map<Long, String> descricoes = new java.util.LinkedHashMap<>(opcoes);
        combo.removeAllItems();
        combo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            public Component getListCellRendererComponent(javax.swing.JList<?> lista, Object valor,
                    int indice, boolean selecionado, boolean foco) {
                super.getListCellRendererComponent(lista, valor, indice, selecionado, foco);
                setText(valor == null ? "" : descricoes.get(valor));
                return this;
            }
        });
        for (Long id : descricoes.keySet()) {
            combo.addItem(id);
        }
        combo.setSelectedIndex(-1);
    }

    private java.time.LocalDate lerData(JTextField campo) {
        try {
            java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);
            return java.time.LocalDate.parse(campo.getText().trim(), formato);
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Informe datas válidas no formato dd/mm/aaaa.");
        }
    }

    public ConciliacaoContabil lerConciliacao() {
        Long conta = (Long) cbConta.getSelectedItem();
        if (empresaId <= 0 || conta == null || conta <= 0) {
            throw new IllegalArgumentException("Informe empresa e conta bancária.");
        }
        ConciliacaoContabil conciliacao = new ConciliacaoContabil();
        conciliacao.setEmpresaId(empresaId);
        conciliacao.setContaBancariaRefId(conta);
        conciliacao.setDataInicio(lerData(txtInicio));
        conciliacao.setDataFim(lerData(txtFim));
        conciliacao.setStatus(txtStatus.getText());
        conciliacao.setObservacao(txtObservacao.getText().trim());
        conciliacao.setItens(new ArrayList<>(itens));
        if (selecionada != null) {
            conciliacao.setId(selecionada.getId());
            conciliacao.setCriadoPor(selecionada.getCriadoPor());
            conciliacao.setCriadoEm(selecionada.getCriadoEm());
            conciliacao.setFinalizadoPor(selecionada.getFinalizadoPor());
            conciliacao.setFinalizadoEm(selecionada.getFinalizadoEm());
        }
        if (!conciliacao.periodoValido() || !conciliacao.itensUnicos()) {
            throw new IllegalArgumentException("Confira o período e os pares de movimentos da conciliação.");
        }
        if (conciliacao.getObservacao().length() > 500) {
            throw new IllegalArgumentException("A observação aceita até 500 caracteres.");
        }
        return conciliacao;
    }

    public void exibirConciliacao(ConciliacaoContabil conciliacao) {
        if (conciliacao.getEmpresaId() != empresaId) {
            throw new IllegalArgumentException("A conciliação não pertence à empresa selecionada.");
        }
        limpar();
        cbConta.setSelectedItem(conciliacao.getContaBancariaRefId());
        if (!Long.valueOf(conciliacao.getContaBancariaRefId()).equals(cbConta.getSelectedItem())) {
            throw new IllegalArgumentException("Carregue a conta bancária da conciliação antes de exibi-la.");
        }
        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtInicio.setText(conciliacao.getDataInicio().format(formato));
        txtFim.setText(conciliacao.getDataFim().format(formato));
        txtObservacao.setText(conciliacao.getObservacao());
        selecionada = conciliacao;
        for (ConciliacaoItem item : conciliacao.getItens()) {
            itens.add(new ConciliacaoItem(item.getId(), item.getConciliacaoId(),
                    item.getMovimentoFinanceiroRefId(), item.getMovimentoExtratoRefId(),
                    item.getStatus(), item.getConciliadoPor(), item.getConciliadoEm(), item.getObservacao()));
        }
        setStatus(conciliacao.getStatus());
    }

    public void carregarMovimentos(List<MovimentoConciliacao> sistema, List<MovimentoConciliacao> extrato) {
        ConciliacaoContabil conciliacao = lerConciliacao();
        movimentosSistema.clear();
        movimentosExtrato.clear();
        modeloSis.setRowCount(0);
        modeloExt.setRowCount(0);
        carregarLista(sistema, movimentosSistema, modeloSis, conciliacao);
        carregarLista(extrato, movimentosExtrato, modeloExt, conciliacao);
        atualizarSelecao();
    }

    private void carregarLista(List<MovimentoConciliacao> origem, ArrayList<MovimentoConciliacao> destino,
            DefaultTableModel modelo, ConciliacaoContabil conciliacao) {
        for (MovimentoConciliacao movimento : origem) {
            if (movimento.getEmpresaId() == empresaId
                    && movimento.getContaBancariaRefId() == conciliacao.getContaBancariaRefId()
                    && movimento.getDataMovimentacao() != null
                    && !movimento.getDataMovimentacao().isBefore(conciliacao.getDataInicio())
                    && !movimento.getDataMovimentacao().isAfter(conciliacao.getDataFim())) {
                destino.add(movimento);
                modelo.addRow(new Object[] {movimento.getId(), movimento.getDataMovimentacao(),
                        movimento.getDocumento(), movimento.getDescricao(), movimento.getNatureza(),
                        movimento.getValor(), movimento.getStatus()});
            }
        }
    }

    public ConciliacaoItem lerParSelecionado() {
        if (!"PENDENTE".equals(txtStatus.getText()) && !"EM_ANALISE".equals(txtStatus.getText())) {
            throw new IllegalArgumentException("Esta conciliação não permite alterar pares.");
        }
        ConciliacaoContabil conciliacao = lerConciliacao();
        if (tabelaSis.getSelectedRowCount() != 1 || tabelaExt.getSelectedRowCount() != 1) {
            throw new IllegalArgumentException("Selecione um movimento em cada tabela.");
        }
        long financeiro = ((Number) tabelaSis.getValueAt(tabelaSis.getSelectedRow(), 0)).longValue();
        long extrato = ((Number) tabelaExt.getValueAt(tabelaExt.getSelectedRow(), 0)).longValue();
        ConciliacaoItem par = new ConciliacaoItem();
        par.setConciliacaoId(conciliacao.getId());
        par.setMovimentoFinanceiroRefId(financeiro);
        par.setMovimentoExtratoRefId(extrato);
        par.setStatus((String) cbStatusItem.getSelectedItem());
        par.setObservacao(txtObservacaoItem.getText().trim());
        if (par.getObservacao().length() > 500) {
            throw new IllegalArgumentException("A observação do par aceita até 500 caracteres.");
        }
        for (ConciliacaoItem item : itens) {
            if (item.getMovimentoFinanceiroRefId() == financeiro || item.getMovimentoExtratoRefId() == extrato) {
                if (item.getMovimentoFinanceiroRefId() != financeiro || item.getMovimentoExtratoRefId() != extrato) {
                    throw new IllegalArgumentException("Um dos movimentos já pertence a outro par nesta conciliação.");
                }
                par.setId(item.getId());
                par.setConciliadoPor(item.getConciliadoPor());
                par.setConciliadoEm(item.getConciliadoEm());
            }
        }
        return par;
    }

    public JTextField getTxtObservacaoItem() {
        return txtObservacaoItem;
    }

}
