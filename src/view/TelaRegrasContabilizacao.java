package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.table.DefaultTableModel;

public class TelaRegrasContabilizacao extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField txtCod = new JTextField(8),
            txtDesc = new JTextField(30),
            txtPesq = new JTextField(22);

    private final JComboBox<String> cbDeb = new JComboBox<>(new String[] {"Selecione uma conta"}),
            cbCred = new JComboBox<>(new String[] {"Selecione uma conta"}),
            cbSit = new JComboBox<>(new String[] {"Todas", "Ativas", "Inativas"});

    private final JCheckBox chkAtiva = new JCheckBox("Regra ativa", true);

    private final JButton btNovo = new JButton("Novo"),
            btSalvar = new JButton("Salvar"),
            btAtual = new JButton("Atualizar"),
            btSit = new JButton("Inativar/Ativar"),
            btLimpar = new JButton("Limpar"),
            btBuscar = new JButton("Pesquisar"),
            btTodos = new JButton("Mostrar todos"),
            btExcluir = new JButton("Excluir");

    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[] {"Código", "Evento", "Conta de Débito", "Conta de Crédito", "Situação"},
            0
    ) {
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };

    private final JTable tabela = new JTable(modelo);
    private final JButton btEditar = new JButton("Editar regra");
    private final JPanel cadastro = new JPanel(new BorderLayout(8, 8));
    private JDialog dialogo;

    public TelaRegrasContabilizacao() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
    }

    private void montar() {
        JPanel f = new JPanel(new GridBagLayout());
        f.setBorder(BorderFactory.createTitledBorder("Cadastro da Regra de Contabilização"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        componente(f, g, 0, "Código:", txtCod);
        componente(f, g, 1, "Descrição do evento:", txtDesc);
        componente(f, g, 2, "Conta de débito:", cbDeb);
        componente(f, g, 3, "Conta de crédito:", cbCred);
        txtCod.setEditable(false);

        g.gridx = 1;
        g.gridy = 4;
        f.add(chkAtiva, g);

        JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        b.add(btSalvar);
        b.add(btAtual);
        b.add(btLimpar);

        JButton btFechar = new JButton("Fechar");
        b.add(btFechar);
        cadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cadastro.add(f, BorderLayout.NORTH);
        cadastro.add(b, BorderLayout.SOUTH);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Filtros e pesquisa"));
        componente(p, g, 0, "Evento:", txtPesq);
        componente(p, g, 1, "Situação:", cbSit);
        JPanel busca = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        busca.add(btBuscar);
        busca.add(btTodos);
        JPanel topo = new JPanel(new BorderLayout(4, 4));
        topo.add(p, BorderLayout.CENTER);
        topo.add(busca, BorderLayout.SOUTH);
        add(topo, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(tabela);
        sp.setBorder(BorderFactory.createTitledBorder("Regras Cadastradas"));

        JPanel c = new JPanel(new BorderLayout(5, 5));
        c.add(sp, BorderLayout.CENTER);
        add(c, BorderLayout.CENTER);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setRowHeight(24);
        tabela.setFillsViewportHeight(true);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(240);

        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        acoes.add(btNovo);
        acoes.add(btEditar);
        acoes.add(btSit);
        acoes.add(btExcluir);
        btExcluir.setEnabled(false);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            btExcluir.setEnabled(tabela.getSelectedRow() != -1);
        });
        add(acoes, BorderLayout.SOUTH);

        btNovo.addActionListener(e -> {
            limpar();
            abrirCadastro("Nova regra");
        });
        btEditar.addActionListener(e -> editarRegra());
        btLimpar.addActionListener(e -> limpar());
        btFechar.addActionListener(e -> dialogo.dispose());
    }

    private void abrirCadastro(String titulo) {
        if (dialogo == null) {
            dialogo = new JDialog(SwingUtilities.getWindowAncestor(this),
                    titulo, JDialog.ModalityType.APPLICATION_MODAL);
            dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialogo.add(cadastro);
            dialogo.setMinimumSize(new Dimension(560, 320));
            dialogo.pack();
        }
        dialogo.setTitle(titulo);
        dialogo.setLocationRelativeTo(this);
        dialogo.setVisible(true);
    }

    private void editarRegra() {
        int linha = tabela.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma regra para editar.");
            return;
        }
        txtCod.setText(String.valueOf(tabela.getValueAt(linha, 0)));
        txtDesc.setText(String.valueOf(tabela.getValueAt(linha, 1)));
        selecionarConta(cbDeb, tabela.getValueAt(linha, 2));
        selecionarConta(cbCred, tabela.getValueAt(linha, 3));
        chkAtiva.setSelected("Ativa".equalsIgnoreCase(String.valueOf(tabela.getValueAt(linha, 4))));
        btSalvar.setEnabled(false);
        btAtual.setEnabled(true);
        abrirCadastro("Editar regra");
    }

    private void selecionarConta(JComboBox<String> combo, Object valor) {
        String conta = valor == null ? "" : valor.toString();
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (conta.equals(combo.getItemAt(i))) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        if (!conta.isEmpty()) {
            combo.addItem(conta);
            combo.setSelectedItem(conta);
        } else {
            combo.setSelectedIndex(-1);
        }
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
        txtCod.setText("");
        txtDesc.setText("");
        cbDeb.setSelectedIndex(0);
        cbCred.setSelectedIndex(0);
        chkAtiva.setSelected(true);
        tabela.clearSelection();
        btSalvar.setEnabled(true);
        btAtual.setEnabled(false);
        if (dialogo != null) {
            dialogo.setTitle("Nova regra");
        }
    }

    public JTextField getTxtCod() {
        return txtCod;
    }

    public JTextField getTxtDesc() {
        return txtDesc;
    }

    public JTextField getTxtPesq() {
        return txtPesq;
    }

    public JComboBox<String> getCbDeb() {
        return cbDeb;
    }

    public JComboBox<String> getCbCred() {
        return cbCred;
    }

    public JComboBox<String> getCbSit() {
        return cbSit;
    }

    public JCheckBox getChkAtiva() {
        return chkAtiva;
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

    public JButton getBtSit() {
        return btSit;
    }

    public JButton getBtLimpar() {
        return btLimpar;
    }

    public JButton getBtBuscar() {
        return btBuscar;
    }

    public JButton getBtTodos() {
        return btTodos;
    }

    public JTable getTab() {
        return tabela;
    }

    public JButton getBtExcluir() {
        return btExcluir;
    }

    public JButton getBtEditar() {
        return btEditar;
    }

    public JDialog getDialogo() {
        return dialogo;
    }
}
