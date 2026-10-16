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
import java.math.BigDecimal;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Lancamento;
import model.PartidaLancamento;
import model.TipoMovimento;

public class TelaLancamentos extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField txtCod = new JTextField(8),
            txtData = new JTextField(10),
            txtDoc = new JTextField(15),
            txtHist = new JTextField(25),
            txtValor = new JTextField(12),
            txtDeb = new JTextField("0,00", 12),
            txtCred = new JTextField("0,00", 12),
            txtDif = new JTextField("0,00", 12),
            txtInicio = new JTextField(10),
            txtFim = new JTextField(10),
            txtPesq = new JTextField(25);

    private long empresaId;
    private Lancamento selecionado;
    private final ArrayList<PartidaLancamento> partidas = new ArrayList<>();
    private final ArrayList<Lancamento> lancamentos = new ArrayList<>();
    private final JTextField txtEmpresa = new JTextField(25),
            txtStatus = new JTextField("RASCUNHO", 15),
            txtOrigem = new JTextField("MANUAL", 15),
            txtDescricaoPartida = new JTextField(25);
    private final JComboBox<Long> cbExercicio = new JComboBox<>(),
            cbLote = new JComboBox<>(),
            cbHist = new JComboBox<>(),
            cbConta = new JComboBox<>(),
            cbCC = new JComboBox<>(),
            cbContaPesq = new JComboBox<>(),
            cbCCPesq = new JComboBox<>();
    private final JComboBox<TipoMovimento> cbTipo = new JComboBox<>(TipoMovimento.values());
    private final JComboBox<String> cbStatusPesq = new JComboBox<>(
            new String[] {"Todos", "RASCUNHO", "CONTABILIZADO", "ESTORNADO"});
    private final JButton btContabilizar = new JButton("Contabilizar");

    private final JButton btNovo = new JButton("Novo"),
            btSalvar = new JButton("Salvar"),
            btAtual = new JButton("Atualizar"),
            btLimpar = new JButton("Limpar"),
            btAdicionar = new JButton("Adicionar partida"),
            btAlterar = new JButton("Alterar partida"),
            btRemover = new JButton("Remover partida"),
            btBuscar = new JButton("Pesquisar"),
            btTodos = new JButton("Mostrar todos"),
            btAbrir = new JButton("Abrir lançamento"),
            btEstornar = new JButton("Estornar lançamento");

    private final DefaultTableModel modeloPartidas = new DefaultTableModel(
            new Object[] {"Conta", "Centro de custos", "Débito (R$)", "Crédito (R$)", "Descrição"}, 0
    ) {
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };

    private final DefaultTableModel modeloLancamentos = new DefaultTableModel(
            new Object[] {"Código", "Data", "Documento", "Lote", "Histórico", "Débito (R$)", "Crédito (R$)", "Status"}, 0
    ) {
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };

    private final JTable tabelaPartidas = new JTable(modeloPartidas),
            tabelaLancamentos = new JTable(modeloLancamentos);

    private final JTabbedPane abas = new JTabbedPane();
    private final JButton btNovaPartida = new JButton("Nova partida"),
            btEditarPartida = new JButton("Editar partida");
    private JPanel dadosPartida;
    private JDialog dialogoPartida;

    public TelaLancamentos() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
        btNovo.addActionListener(e -> limpar());
        btAdicionar.addActionListener(e -> gravarPartida(false));
        btAlterar.addActionListener(e -> gravarPartida(true));
        btRemover.addActionListener(e -> {
            int linha = tabelaPartidas.getSelectedRow();
            if ("RASCUNHO".equals(txtStatus.getText()) && linha != -1) {
                partidas.remove(linha);
                modeloPartidas.removeRow(linha);
                atualizarTotais();
                limparPartida();
            }
        });
        btAbrir.addActionListener(e -> {
            int linha = tabelaLancamentos.getSelectedRow();
            if (linha != -1) {
                long id = ((Number) tabelaLancamentos.getValueAt(linha, 0)).longValue();
                for (Lancamento lancamento : lancamentos) {
                    if (lancamento.getId() == id) {
                        exibirLancamento(lancamento);
                        abas.setSelectedIndex(0);
                        break;
                    }
                }
            }
        });
        txtEmpresa.setEditable(false);
        txtStatus.setEditable(false);
        txtOrigem.setEditable(false);
        setStatus("RASCUNHO");
    }

    private void montar() {
        abas.addTab("Lançamento", montarLancamento());
        abas.addTab("Consulta", montarConsulta());
        add(abas, BorderLayout.CENTER);

        btLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limpar();
            }
        });
    }

    private JPanel montarLancamento() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel dados = new JPanel(new GridBagLayout());
        dados.setBorder(BorderFactory.createTitledBorder("Dados do lançamento"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        componente(dados, g, 0, "Código:", txtCod);
        componente(dados, g, 1, "Data (dd/mm/aaaa):", txtData);
        componente(dados, g, 2, "Documento:", txtDoc);
        componente(dados, g, 3, "Lote:", cbLote);
        componente(dados, g, 4, "Histórico padrão:", cbHist);
        componente(dados, g, 5, "Complemento:", txtHist);
        componente(dados, g, 6, "Empresa:", txtEmpresa);
        componente(dados, g, 7, "Exercício:", cbExercicio);
        componente(dados, g, 8, "Origem:", txtOrigem);
        componente(dados, g, 9, "Status:", txtStatus);
        txtCod.setEditable(false);
        txtHist.setToolTipText("Descrição complementar do lançamento.");

        JPanel partida = new JPanel(new GridBagLayout());
        dadosPartida = new JPanel(new BorderLayout(8, 8));
        dadosPartida.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        partida.setBorder(BorderFactory.createTitledBorder("Dados da partida"));
        componente(partida, g, 0, "Conta:", cbConta);
        componente(partida, g, 1, "Tipo:", cbTipo);
        componente(partida, g, 2, "Centro de custos:", cbCC);
        componente(partida, g, 3, "Valor (R$):", txtValor);
        componente(partida, g, 4, "Descrição:", txtDescricaoPartida);
        txtValor.setHorizontalAlignment(JTextField.RIGHT);
        txtValor.setToolTipText("Exemplo: 1500,00");
        cbCC.setToolTipText("Informe quando a conta exigir centro de custos.");

        JPanel incluir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        incluir.add(btAdicionar);
        incluir.add(btAlterar);
        JButton btFechar = new JButton("Fechar");
        incluir.add(btFechar);
        dadosPartida.add(partida, BorderLayout.NORTH);
        dadosPartida.add(incluir, BorderLayout.SOUTH);
        btFechar.addActionListener(e -> dialogoPartida.dispose());

        JPanel acoesPartida = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        acoesPartida.add(btNovaPartida);
        acoesPartida.add(btEditarPartida);
        acoesPartida.add(btRemover);
        JPanel topo = new JPanel(new BorderLayout(4, 4));
        topo.add(dados, BorderLayout.CENTER);
        topo.add(acoesPartida, BorderLayout.SOUTH);

        btNovaPartida.addActionListener(e -> {
            limparPartida();
            abrirPartida("Nova partida");
        });
        btEditarPartida.addActionListener(e -> editarPartida());
        painel.add(topo, BorderLayout.NORTH);

        tabelaPartidas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaPartidas.setRowHeight(24);
        tabelaPartidas.setFillsViewportHeight(true);
        tabelaPartidas.getTableHeader().setReorderingAllowed(false);
        tabelaPartidas.getColumnModel().getColumn(0).setPreferredWidth(300);
        tabelaPartidas.getColumnModel().getColumn(1).setPreferredWidth(180);
        DefaultTableCellRenderer moeda = new DefaultTableCellRenderer();
        moeda.setHorizontalAlignment(JLabel.RIGHT);
        tabelaPartidas.getColumnModel().getColumn(2).setCellRenderer(moeda);
        tabelaPartidas.getColumnModel().getColumn(3).setCellRenderer(moeda);

        JScrollPane sp = new JScrollPane(tabelaPartidas);
        sp.setBorder(BorderFactory.createTitledBorder("Partidas do lançamento"));
        painel.add(sp, BorderLayout.CENTER);

        JPanel totais = new JPanel(new GridLayout(1, 3, 8, 0));
        totais.setBorder(BorderFactory.createTitledBorder("Totais do lançamento"));
        totais.add(montarTotal("Débito (R$)", txtDeb));
        totais.add(montarTotal("Crédito (R$)", txtCred));
        totais.add(montarTotal("Diferença (R$)", txtDif));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        botoes.add(btNovo);
        botoes.add(btSalvar);
        botoes.add(btAtual);
        botoes.add(btContabilizar);
        botoes.add(btLimpar);

        JPanel rodape = new JPanel(new BorderLayout(4, 4));
        rodape.add(totais, BorderLayout.NORTH);
        rodape.add(new JLabel("Contabilizar exige débitos e créditos iguais. O rascunho pode ficar desequilibrado."), BorderLayout.CENTER);
        rodape.add(botoes, BorderLayout.SOUTH);
        painel.add(rodape, BorderLayout.SOUTH);
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

    private void abrirPartida(String titulo) {
        if (dialogoPartida == null) {
            dialogoPartida = new JDialog(SwingUtilities.getWindowAncestor(this),
                    titulo, JDialog.ModalityType.APPLICATION_MODAL);
            dialogoPartida.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialogoPartida.add(dadosPartida);
            dialogoPartida.setMinimumSize(new Dimension(540, 270));
            dialogoPartida.pack();
        }
        dialogoPartida.setTitle(titulo);
        dialogoPartida.setLocationRelativeTo(this);
        dialogoPartida.setVisible(true);
    }

    private void editarPartida() {
        int linha = tabelaPartidas.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma partida para editar.");
            return;
        }
        selecionarItem(cbConta, tabelaPartidas.getValueAt(linha, 0));
        selecionarItem(cbCC, tabelaPartidas.getValueAt(linha, 1));
        Object debito = tabelaPartidas.getValueAt(linha, 2);
        String valor = debito == null ? "" : debito.toString().trim();
        boolean credito = valor.isEmpty() || valor.matches("0([,.]0+)?");
        cbTipo.setSelectedIndex(credito ? 1 : 0);
        Object selecionado = tabelaPartidas.getValueAt(linha, credito ? 3 : 2);
        txtValor.setText(selecionado == null ? "" : selecionado.toString());
        Object descricao = tabelaPartidas.getValueAt(linha, 4);
        txtDescricaoPartida.setText(descricao == null ? "" : descricao.toString());
        btAdicionar.setEnabled(false);
        btAlterar.setEnabled(true);
        abrirPartida("Editar partida");
    }

    private void selecionarItem(JComboBox<Long> combo, Object valor) {
        combo.setSelectedIndex(-1);
        if (valor instanceof Number) {
            long id = ((Number) valor).longValue();
            for (int i = 0; i < combo.getItemCount(); i++) {
                if (combo.getItemAt(i).longValue() == id) {
                    combo.setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    private JPanel montarConsulta() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel filtros = new JPanel(new GridBagLayout());
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros e pesquisa"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        JPanel periodo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        periodo.add(txtInicio);
        periodo.add(new JLabel("  até  "));
        periodo.add(txtFim);
        componente(filtros, g, 0, "Período (dd/mm/aaaa):", periodo);
        componente(filtros, g, 1, "Histórico / descrição:", txtPesq);
        componente(filtros, g, 2, "Conta:", cbContaPesq);
        componente(filtros, g, 3, "Centro de custos:", cbCCPesq);

        componente(filtros, g, 4, "Status:", cbStatusPesq);

        JPanel busca = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        busca.add(btBuscar);
        busca.add(btTodos);
        JPanel topo = new JPanel(new BorderLayout(4, 4));
        topo.add(filtros, BorderLayout.CENTER);
        topo.add(busca, BorderLayout.SOUTH);
        painel.add(topo, BorderLayout.NORTH);

        tabelaLancamentos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaLancamentos.setRowHeight(24);
        tabelaLancamentos.setFillsViewportHeight(true);
        tabelaLancamentos.getTableHeader().setReorderingAllowed(false);
        tabelaLancamentos.getColumnModel().getColumn(4).setPreferredWidth(250);
        DefaultTableCellRenderer moeda = new DefaultTableCellRenderer();
        moeda.setHorizontalAlignment(JLabel.RIGHT);
        tabelaLancamentos.getColumnModel().getColumn(5).setCellRenderer(moeda);
        tabelaLancamentos.getColumnModel().getColumn(6).setCellRenderer(moeda);

        JScrollPane sp = new JScrollPane(tabelaLancamentos);
        sp.setBorder(BorderFactory.createTitledBorder("Lançamentos cadastrados"));
        painel.add(sp, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        botoes.add(btAbrir);
        botoes.add(btEstornar);
        btEstornar.setEnabled(false);
        btEstornar.setToolTipText("O estorno gera um lançamento reverso e preserva o original.");
        tabelaLancamentos.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabelaLancamentos.getSelectedRow();
            btEstornar.setEnabled(linha != -1
                    && "CONTABILIZADO".equals(tabelaLancamentos.getValueAt(linha, 7)));
        });
        painel.add(botoes, BorderLayout.SOUTH);
        return painel;
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

    public void limparPartida() {
        cbConta.setSelectedIndex(-1);
        cbTipo.setSelectedIndex(0);
        cbCC.setSelectedIndex(-1);
        txtValor.setText("");
        txtDescricaoPartida.setText("");
        tabelaPartidas.clearSelection();
        btAdicionar.setEnabled("RASCUNHO".equals(txtStatus.getText()));
        btAlterar.setEnabled(false);
    }

    public void limpar() {
        selecionado = null;
        partidas.clear();
        txtCod.setText("");
        txtData.setText("");
        txtDoc.setText("");
        cbLote.setSelectedIndex(-1);
        cbHist.setSelectedIndex(-1);
        txtHist.setText("");
        limparPartida();
        modeloPartidas.setRowCount(0);
        txtDeb.setText("0,00");
        txtCred.setText("0,00");
        txtDif.setText("0,00");
        txtOrigem.setText("MANUAL");
        setStatus("RASCUNHO");
    }

    public JTextField getTxtCod() {
        return txtCod;
    }

    public JTextField getTxtData() {
        return txtData;
    }

    public JTextField getTxtDoc() {
        return txtDoc;
    }

    public JTextField getTxtHist() {
        return txtHist;
    }

    public JTextField getTxtValor() {
        return txtValor;
    }

    public JTextField getTxtDeb() {
        return txtDeb;
    }

    public JTextField getTxtCred() {
        return txtCred;
    }

    public JTextField getTxtDif() {
        return txtDif;
    }

    public JTextField getTxtInicio() {
        return txtInicio;
    }

    public JTextField getTxtFim() {
        return txtFim;
    }

    public JTextField getTxtPesq() {
        return txtPesq;
    }

    public JComboBox<Long> getCbLote() {
        return cbLote;
    }

    public JComboBox<Long> getCbHist() {
        return cbHist;
    }

    public JComboBox<Long> getCbConta() {
        return cbConta;
    }

    public JComboBox<TipoMovimento> getCbTipo() {
        return cbTipo;
    }

    public JComboBox<Long> getCbCC() {
        return cbCC;
    }

    public JComboBox<Long> getCbContaPesq() {
        return cbContaPesq;
    }

    public JComboBox<Long> getCbCCPesq() {
        return cbCCPesq;
    }

    public JButton getBtEstornar() {
        return btEstornar;
    }

    public JButton getBtNovo() {
        return btNovo;
    }

    public JButton getBtSalvar() {
        return btSalvar;
    }

    public JButton getBtAtual() {
        return btAtual;
    }

    public JButton getBtLimpar() {
        return btLimpar;
    }

    public JButton getBtAdicionar() {
        return btAdicionar;
    }

    public JButton getBtAlterar() {
        return btAlterar;
    }

    public JButton getBtRemover() {
        return btRemover;
    }

    public JButton getBtBuscar() {
        return btBuscar;
    }

    public JButton getBtTodos() {
        return btTodos;
    }

    public JButton getBtAbrir() {
        return btAbrir;
    }

    public JTable getTabPartidas() {
        return tabelaPartidas;
    }

    public JTable getTabLancamentos() {
        return tabelaLancamentos;
    }

    public JTabbedPane getAbas() {
        return abas;
    }

    public JButton getBtNovaPartida() {
        return btNovaPartida;
    }

    public JButton getBtEditarPartida() {
        return btEditarPartida;
    }

    public JDialog getDialogoPartida() {
        return dialogoPartida;
    }

    public void setContexto(long empresaId, String empresa) {
        this.empresaId = empresaId;
        txtEmpresa.setText(empresa);
        cbExercicio.removeAllItems();
        cbLote.removeAllItems();
        cbHist.removeAllItems();
        cbConta.removeAllItems();
        cbCC.removeAllItems();
        cbContaPesq.removeAllItems();
        cbCCPesq.removeAllItems();
        modeloLancamentos.setRowCount(0);
        lancamentos.clear();
        limpar();
    }

    public void setStatus(String status) {
        txtStatus.setText(status);
        boolean rascunho = "RASCUNHO".equals(status);
        txtData.setEditable(rascunho);
        txtDoc.setEditable(rascunho);
        txtHist.setEditable(rascunho);
        cbExercicio.setEnabled(rascunho);
        cbLote.setEnabled(rascunho);
        cbHist.setEnabled(rascunho);
        cbConta.setEnabled(rascunho);
        cbCC.setEnabled(rascunho);
        cbTipo.setEnabled(rascunho);
        txtValor.setEditable(rascunho);
        txtDescricaoPartida.setEditable(rascunho);
        btSalvar.setEnabled(rascunho);
        btAtual.setEnabled(rascunho && !txtCod.getText().isEmpty());
        btContabilizar.setEnabled(rascunho);
        btNovaPartida.setEnabled(rascunho);
        btEditarPartida.setEnabled(rascunho);
        btRemover.setEnabled(rascunho);
        btAdicionar.setEnabled(rascunho);
        btAlterar.setEnabled(rascunho && tabelaPartidas.getSelectedRow() != -1);
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public JComboBox<Long> getCbExercicio() {
        return cbExercicio;
    }

    public JTextField getTxtStatus() {
        return txtStatus;
    }

    public JTextField getTxtOrigem() {
        return txtOrigem;
    }

    public JTextField getTxtDescricaoPartida() {
        return txtDescricaoPartida;
    }

    public JComboBox<String> getCbStatusPesq() {
        return cbStatusPesq;
    }

    public JButton getBtContabilizar() {
        return btContabilizar;
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

    private void gravarPartida(boolean alterar) {
        if (!"RASCUNHO".equals(txtStatus.getText())) {
            return;
        }
        int linha = tabelaPartidas.getSelectedRow();
        if (alterar && linha == -1) {
            return;
        }
        try {
            PartidaLancamento partida = lerPartida();
            if (alterar) {
                partida.setId(partidas.get(linha).getId());
                partidas.set(linha, partida);
                modeloPartidas.removeRow(linha);
                modeloPartidas.insertRow(linha, dadosPartida(partida));
            } else {
                partidas.add(partida);
                modeloPartidas.addRow(dadosPartida(partida));
            }
            atualizarTotais();
            limparPartida();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public PartidaLancamento lerPartida() {
        Long conta = (Long) cbConta.getSelectedItem();
        if (conta == null || conta <= 0 || cbTipo.getSelectedItem() == null) {
            throw new IllegalArgumentException("Selecione conta e movimento da partida.");
        }
        String texto = txtValor.getText().trim();
        if (texto.contains(",")) {
            texto = texto.replace(".", "").replace(',', '.');
        }
        BigDecimal valor;
        try {
            valor = new BigDecimal(texto);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Informe um valor monetário válido.");
        }
        if (valor.signum() <= 0 || valor.stripTrailingZeros().scale() > 2 || valor.precision() - valor.scale() > 13) {
            throw new IllegalArgumentException("O valor deve ser positivo e caber em DECIMAL(15,2).");
        }
        if (txtDescricaoPartida.getText().length() > 255) {
            throw new IllegalArgumentException("A descrição da partida aceita até 255 caracteres.");
        }
        PartidaLancamento partida = new PartidaLancamento();
        partida.setLancamentoId(selecionado == null ? 0 : selecionado.getId());
        partida.setContaId(conta);
        partida.setCentroCustoId((Long) cbCC.getSelectedItem());
        partida.setTipoMovimento((TipoMovimento) cbTipo.getSelectedItem());
        partida.setValor(valor);
        partida.setDescricao(txtDescricaoPartida.getText().trim());
        return partida;
    }

    private Object[] dadosPartida(PartidaLancamento partida) {
        boolean debito = partida.getTipoMovimento() == TipoMovimento.DEBITO;
        return new Object[] {partida.getContaId(), partida.getCentroCustoId(),
                debito ? partida.getValor() : BigDecimal.ZERO,
                debito ? BigDecimal.ZERO : partida.getValor(), partida.getDescricao()};
    }

    private void atualizarTotais() {
        Lancamento lancamento = new Lancamento();
        lancamento.setPartidas(partidas);
        java.text.NumberFormat formato = java.text.NumberFormat.getNumberInstance(new java.util.Locale("pt", "BR"));
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        txtDeb.setText(formato.format(lancamento.getTotalDebitos()));
        txtCred.setText(formato.format(lancamento.getTotalCreditos()));
        txtDif.setText(formato.format(lancamento.getTotalDebitos().subtract(lancamento.getTotalCreditos())));
    }

    public Lancamento lerLancamento() {
        if (!"RASCUNHO".equals(txtStatus.getText())) {
            throw new IllegalArgumentException("Somente rascunhos podem ser alterados.");
        }
        if (empresaId <= 0 || cbExercicio.getSelectedItem() == null
                || txtDoc.getText().trim().isEmpty() || txtHist.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe empresa, exercício, documento e descrição.");
        }
        if (txtDoc.getText().trim().length() > 50 || txtHist.getText().trim().length() > 255) {
            throw new IllegalArgumentException("Documento ou descrição excedem o tamanho permitido.");
        }
        Lancamento lancamento = new Lancamento();
        try {
            java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);
            lancamento.setDataLancamento(java.time.LocalDate.parse(txtData.getText().trim(), formato));
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Informe uma data válida no formato dd/mm/aaaa.");
        }
        if (selecionado != null) {
            lancamento.setId(selecionado.getId());
            lancamento.setCriadoPor(selecionado.getCriadoPor());
            lancamento.setCriadoEm(selecionado.getCriadoEm());
            lancamento.setEstornoDeId(selecionado.getEstornoDeId());
        }
        lancamento.setEmpresaId(empresaId);
        lancamento.setExercicioSocialId((Long) cbExercicio.getSelectedItem());
        lancamento.setNumeroDocumento(txtDoc.getText().trim());
        lancamento.setDescricao(txtHist.getText().trim());
        lancamento.setOrigem(txtOrigem.getText());
        lancamento.setLoteId((Long) cbLote.getSelectedItem());
        lancamento.setHistoricoPadraoId((Long) cbHist.getSelectedItem());
        lancamento.setPartidas(new ArrayList<>(partidas));
        return lancamento;
    }

    public void exibirLancamento(Lancamento lancamento) {
        if (lancamento.getEmpresaId() != empresaId) {
            throw new IllegalArgumentException("O lançamento não pertence à empresa selecionada.");
        }
        limpar();
        selecionado = lancamento;
        txtCod.setText(String.valueOf(lancamento.getId()));
        txtData.setText(lancamento.getDataLancamento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtDoc.setText(lancamento.getNumeroDocumento());
        txtHist.setText(lancamento.getDescricao());
        txtOrigem.setText(lancamento.getOrigem());
        selecionarItem(cbExercicio, lancamento.getExercicioSocialId());
        selecionarItem(cbLote, lancamento.getLoteId());
        selecionarItem(cbHist, lancamento.getHistoricoPadraoId());
        for (PartidaLancamento partida : lancamento.getPartidas()) {
            PartidaLancamento copia = new PartidaLancamento(partida.getId(), partida.getLancamentoId(),
                    partida.getContaId(), partida.getCentroCustoId(), partida.getTipoMovimento(),
                    partida.getValor(), partida.getDescricao());
            partidas.add(copia);
            modeloPartidas.addRow(dadosPartida(copia));
        }
        atualizarTotais();
        setStatus(lancamento.getStatus());
    }

    public void carregarLancamentos(List<Lancamento> registros) {
        lancamentos.clear();
        modeloLancamentos.setRowCount(0);
        for (Lancamento lancamento : registros) {
            if (empresaId > 0 && lancamento.getEmpresaId() == empresaId) {
                lancamentos.add(lancamento);
                modeloLancamentos.addRow(new Object[] {lancamento.getId(), lancamento.getDataLancamento(),
                        lancamento.getNumeroDocumento(), lancamento.getLoteId(), lancamento.getDescricao(),
                        lancamento.getTotalDebitos(), lancamento.getTotalCreditos(), lancamento.getStatus()});
            }
        }
    }

}
