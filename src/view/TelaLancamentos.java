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

    private final JComboBox<String> cbLote = new JComboBox<>(new String[] {"Selecione um lote"}),
            cbHist = new JComboBox<>(new String[] {"Selecione um histórico"}),
            cbConta = new JComboBox<>(new String[] {"Selecione uma conta"}),
            cbTipo = new JComboBox<>(new String[] {"Débito", "Crédito"}),
            cbCC = new JComboBox<>(new String[] {"Não informado"}),
            cbContaPesq = new JComboBox<>(new String[] {"Todas as contas"}),
            cbCCPesq = new JComboBox<>(new String[] {"Todos os centros de custos"});

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
            new Object[] {"Conta", "Centro de custos", "Débito (R$)", "Crédito (R$)"}, 0
    ) {
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };

    private final DefaultTableModel modeloLancamentos = new DefaultTableModel(
            new Object[] {"Código", "Data", "Documento", "Lote", "Histórico", "Débito (R$)", "Crédito (R$)"}, 0
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
        botoes.add(btLimpar);

        JPanel rodape = new JPanel(new BorderLayout(4, 4));
        rodape.add(totais, BorderLayout.NORTH);
        rodape.add(new JLabel("O total de débitos deve ser igual ao total de créditos."), BorderLayout.CENTER);
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
        btAdicionar.setEnabled(false);
        btAlterar.setEnabled(true);
        abrirPartida("Editar partida");
    }

    private void selecionarItem(JComboBox<String> combo, Object valor) {
        String item = valor == null ? "" : valor.toString();
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (item.equals(combo.getItemAt(i))) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        if (!item.isEmpty()) {
            combo.addItem(item);
            combo.setSelectedItem(item);
        } else {
            combo.setSelectedIndex(-1);
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
            btEstornar.setEnabled(tabelaLancamentos.getSelectedRow() != -1);
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
        cbConta.setSelectedIndex(0);
        cbTipo.setSelectedIndex(0);
        cbCC.setSelectedIndex(0);
        txtValor.setText("");
        tabelaPartidas.clearSelection();
        btAdicionar.setEnabled(true);
        btAlterar.setEnabled(false);
    }

    public void limpar() {
        txtCod.setText("");
        txtData.setText("");
        txtDoc.setText("");
        cbLote.setSelectedIndex(0);
        cbHist.setSelectedIndex(0);
        txtHist.setText("");
        limparPartida();
        modeloPartidas.setRowCount(0);
        txtDeb.setText("0,00");
        txtCred.setText("0,00");
        txtDif.setText("0,00");
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

    public JComboBox<String> getCbLote() {
        return cbLote;
    }

    public JComboBox<String> getCbHist() {
        return cbHist;
    }

    public JComboBox<String> getCbConta() {
        return cbConta;
    }

    public JComboBox<String> getCbTipo() {
        return cbTipo;
    }

    public JComboBox<String> getCbCC() {
        return cbCC;
    }

    public JComboBox<String> getCbContaPesq() {
        return cbContaPesq;
    }

    public JComboBox<String> getCbCCPesq() {
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
}
