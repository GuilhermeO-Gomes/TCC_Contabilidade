package view;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 * Interface de registro de eliminacoes para consolidacao contabil.
 * Sem acesso a banco, controller, DAO ou classes de model.
 */
public class TelaEliminacoes extends JDialog {

    private final JComboBox<String> cbxConsolidacao = new JComboBox<>();
    private final JTextField txtDescricao = new JTextField(28);
    private final JTextField txtData = new JTextField(12);
    private final JTextArea txtObservacao = new JTextArea(3, 35);

    private final JComboBox<String> cbxEmpresa = new JComboBox<>();
    private final JComboBox<String> cbxConta = new JComboBox<>();
    private final JComboBox<String> cbxNatureza = new JComboBox<>(new String[]{"DEBITO", "CREDITO"});
    private final JTextField txtValor = new JTextField(14);
    private final JTextField txtHistoricoItem = new JTextField(28);

    private final JButton btnAdicionarItem = new JButton("Adicionar item");
    private final JButton btnRemoverItem = new JButton("Remover item");
    private final JButton btnNovo = new JButton("Novo");
    private final JButton btnSalvar = new JButton("Salvar eliminação");
    private final JButton btnFechar = new JButton("Fechar");

    private final DefaultTableModel modeloItens = new DefaultTableModel(
            new Object[]{"Empresa", "Conta contábil", "Natureza", "Valor", "Histórico"}, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tabelaItens = new JTable(modeloItens);

    public TelaEliminacoes(Window ConsolidacaoBalanco) {
        super(ConsolidacaoBalanco, "Registrar eliminações da consolidação", Dialog.ModalityType.APPLICATION_MODAL);
        montarInterface();
        configurarEventosVisuais();
        setSize(980, 700);
        setMinimumSize(new java.awt.Dimension(780, 570));
        setLocationRelativeTo(ConsolidacaoBalanco);
    }

    private void montarInterface() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel conteudo = new JPanel(new BorderLayout(10, 10));
        conteudo.setBorder(BorderFactory.createEmptyBorder(12, 12, 8, 12));

        JPanel painelDados = new JPanel(new GridBagLayout());
        painelDados.setBorder(BorderFactory.createTitledBorder("Dados da eliminação"));
        GridBagConstraints g = constraints();
        adicionarCampo(painelDados, g, 0, "Consolidação:", cbxConsolidacao);
        adicionarCampo(painelDados, g, 1, "Descrição:", txtDescricao);
        adicionarCampo(painelDados, g, 2, "Data:", txtData);
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);
        adicionarCampo(painelDados, g, 3, "Observações:", new JScrollPane(txtObservacao));

        JPanel painelItem = new JPanel(new GridBagLayout());
        painelItem.setBorder(BorderFactory.createTitledBorder("Incluir item da eliminação"));
        GridBagConstraints gi = constraints();
        adicionarCampo(painelItem, gi, 0, "Empresa:", cbxEmpresa);
        adicionarCampo(painelItem, gi, 1, "Conta contábil:", cbxConta);
        adicionarCampo(painelItem, gi, 2, "Natureza:", cbxNatureza);
        adicionarCampo(painelItem, gi, 3, "Valor:", txtValor);
        adicionarCampo(painelItem, gi, 4, "Histórico:", txtHistoricoItem);

        JPanel botoesItens = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoesItens.add(btnAdicionarItem);
        botoesItens.add(btnRemoverItem);
        JPanel areaItens = new JPanel(new BorderLayout(4, 4));
        areaItens.add(painelItem, BorderLayout.CENTER);
        areaItens.add(botoesItens, BorderLayout.SOUTH);

        JPanel formularios = new JPanel(new BorderLayout(8, 8));
        formularios.add(painelDados, BorderLayout.NORTH);
        formularios.add(areaItens, BorderLayout.CENTER);

        tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaItens.setRowHeight(24);
        tabelaItens.setAutoCreateRowSorter(true);
        JScrollPane rolagemTabela = new JScrollPane(tabelaItens);
        rolagemTabela.setBorder(BorderFactory.createTitledBorder("Itens registrados na eliminação"));

        JSplitPane divisao = new JSplitPane(JSplitPane.VERTICAL_SPLIT, formularios, rolagemTabela);
        divisao.setResizeWeight(0.65);
        divisao.setBorder(null);
        conteudo.add(divisao, BorderLayout.CENTER);
        add(conteudo, BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 8));
        rodape.add(btnNovo);
        rodape.add(btnSalvar);
        rodape.add(btnFechar);
        add(rodape, BorderLayout.SOUTH);
    }

    private GridBagConstraints constraints() {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5, 8, 5, 8);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        return g;
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints g, int linha,
                               String rotulo, java.awt.Component componente) {
        g.gridx = 0;
        g.gridy = linha;
        g.weightx = 0;
        painel.add(new JLabel(rotulo), g);
        g.gridx = 1;
        g.weightx = 1;
        painel.add(componente, g);
    }

    private void configurarEventosVisuais() {
        btnAdicionarItem.addActionListener(e -> adicionarItemNaTabela());
        btnRemoverItem.addActionListener(e -> removerItemDaTabela());
        btnNovo.addActionListener(e -> limparFormulario());
        btnFechar.addActionListener(e -> dispose());
        // btnSalvar: evento a ser associado pela camada responsavel pela persistencia.
    }

    private void adicionarItemNaTabela() {
        Object empresa = cbxEmpresa.getSelectedItem();
        Object conta = cbxConta.getSelectedItem();
        String valorTexto = txtValor.getText().trim().replace(".", "").replace(',', '.');
        if (empresa == null || conta == null || valorTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecione a empresa e a conta e informe o valor.",
                    "Campos obrigatórios", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            BigDecimal valor = new BigDecimal(valorTexto);
            if (valor.signum() <= 0) {
                throw new NumberFormatException();
            }
            modeloItens.addRow(new Object[]{empresa, conta,
                    cbxNatureza.getSelectedItem(), valor, txtHistoricoItem.getText().trim()});
            cbxConta.setSelectedIndex(cbxConta.getItemCount() > 0 ? 0 : -1);
            txtValor.setText("");
            txtHistoricoItem.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Informe um valor positivo válido (ex.: 1500,00).",
                    "Valor inválido", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void removerItemDaTabela() {
        int linha = tabelaItens.getSelectedRow();
        if (linha < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela.");
            return;
        }
        modeloItens.removeRow(tabelaItens.convertRowIndexToModel(linha));
    }

    public void limparFormulario() {
        txtDescricao.setText("");
        txtData.setText("");
        txtObservacao.setText("");
        txtValor.setText("");
        txtHistoricoItem.setText("");
        modeloItens.setRowCount(0);
        if (cbxEmpresa.getItemCount() > 0) cbxEmpresa.setSelectedIndex(0);
        if (cbxConta.getItemCount() > 0) cbxConta.setSelectedIndex(0);
        cbxNatureza.setSelectedIndex(0);
    }

    // Metodos para preencher os combos a partir de outra camada, sem dependencia de Model.
    public void setConsolidacoes(List<String> valores) { preencher(cbxConsolidacao, valores); }
    public void setEmpresas(List<String> valores) { preencher(cbxEmpresa, valores); }
    public void setContas(List<String> valores) { preencher(cbxConta, valores); }

    private void preencher(JComboBox<String> combo, List<String> valores) {
        combo.removeAllItems();
        if (valores != null) {
            for (String valor : valores) combo.addItem(valor);
        }
    }

    public JButton getBtnSalvar() { return btnSalvar; }
    public JButton getBtnNovo() { return btnNovo; }
    public JTable getTabelaItens() { return tabelaItens; }
    public DefaultTableModel getModeloItens() { return modeloItens; }
    public JComboBox<String> getCbxConsolidacao() { return cbxConsolidacao; }
    public JComboBox<String> getCbxEmpresa() { return cbxEmpresa; }
    public JComboBox<String> getCbxConta() { return cbxConta; }
    public JTextField getTxtDescricao() { return txtDescricao; }
    public JTextField getTxtData() { return txtData; }
    public JTextArea getTxtObservacao() { return txtObservacao; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaEliminacoes dialog = new TelaEliminacoes((Frame) null);
            dialog.setConsolidacoes(java.util.Arrays.asList("Consolidação de exemplo"));
            dialog.setEmpresas(java.util.Arrays.asList("Empresa A", "Empresa B"));
            dialog.setContas(java.util.Arrays.asList("1.1.01 - Caixa", "2.1.01 - Contas a pagar"));
            dialog.setVisible(true);
        });
    }
}
