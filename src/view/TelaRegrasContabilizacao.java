package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
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
            btTodos = new JButton("Mostrar todos");

    private final DefaultTableModel mod = new DefaultTableModel(
            new Object[] {"Código", "Evento", "Conta de Débito", "Conta de Crédito", "Situação"},
            0
    ) {
        private static final long serialVersionUID = 1L;

        @Override
        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };

    private final JTable tab = new JTable(mod);

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

        comp(f, g, 0, "Código:", txtCod);
        comp(f, g, 1, "Descrição do evento:", txtDesc);
        comp(f, g, 2, "Conta de débito:", cbDeb);
        comp(f, g, 3, "Conta de crédito:", cbCred);
        txtCod.setEditable(false);

        g.gridx = 1;
        g.gridy = 4;
        f.add(chkAtiva, g);

        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
        b.add(btNovo);
        b.add(btSalvar);
        b.add(btAtual);
        b.add(btSit);
        b.add(btLimpar);

        JPanel n = new JPanel(new BorderLayout());
        n.add(f, BorderLayout.CENTER);
        n.add(b, BorderLayout.SOUTH);
        add(n, BorderLayout.NORTH);

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(new JLabel("Evento:"));
        p.add(txtPesq);
        p.add(new JLabel("Situação:"));
        p.add(cbSit);
        p.add(btBuscar);
        p.add(btTodos);

        JScrollPane sp = new JScrollPane(tab);
        sp.setBorder(BorderFactory.createTitledBorder("Regras Cadastradas"));

        JPanel c = new JPanel(new BorderLayout(5, 5));
        c.add(p, BorderLayout.NORTH);
        c.add(sp, BorderLayout.CENTER);
        add(c, BorderLayout.CENTER);
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void comp(JPanel p, GridBagConstraints g,
            int l, String txt, Component comp) {
        g.gridx = 0;
        g.gridy = l;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        p.add(new JLabel(txt), g);

        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        p.add(comp, g);
    }

    public void limpar() {
        txtCod.setText("");
        txtDesc.setText("");
        cbDeb.setSelectedIndex(0);
        cbCred.setSelectedIndex(0);
        chkAtiva.setSelected(true);
        tab.clearSelection();
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
        return tab;
    }
}
