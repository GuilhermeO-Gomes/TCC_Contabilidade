package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import model.RegraContabilizacao;
import model.RegraContabilizacaoItem;
import model.TipoMovimento;

public class TelaRegrasContabilizacao extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField txtCod = new JTextField(8),
            txtDesc = new JTextField(30),
            txtPesq = new JTextField(22);

    private long empresaId;
    private RegraContabilizacao selecionada;
    private final ArrayList<RegraContabilizacao> regras = new ArrayList<>();
    private final JTextField txtEmpresa = new JTextField(25),
            txtModulo = new JTextField(20),
            txtEvento = new JTextField(25);
    private final JComboBox<String> cbSit = new JComboBox<>(new String[] {"Todas", "Ativas", "Inativas"});

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
            new Object[] {"Código", "Módulo", "Evento", "Descrição", "Situação"},
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
    private final PainelItensRegra editorItens = new PainelItensRegra(txtCod);

    public TelaRegrasContabilizacao() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
        limpar();
    }

    private void montar() {
        JPanel f = new JPanel(new GridBagLayout());
        f.setBorder(BorderFactory.createTitledBorder("Cadastro da Regra de Contabilização"));

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        Componentes.componente(f, g, 0, "Código:", txtCod);
        Componentes.componente(f, g, 1, "Descrição do evento:", txtDesc);
        Componentes.componente(f, g, 2, "Empresa:", txtEmpresa);
        Componentes.componente(f, g, 3, "Módulo de origem:", txtModulo);
        Componentes.componente(f, g, 4, "Evento de origem:", txtEvento);
        txtEmpresa.setEditable(false);
        txtCod.setEditable(false);

        g.gridx = 1;
        g.gridy = 5;
        f.add(chkAtiva, g);

        JPanel b = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        b.add(btSalvar);
        b.add(btAtual);
        b.add(btLimpar);

        JButton btFechar = new JButton("Fechar");
        b.add(btFechar);
        cadastro.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cadastro.add(f, BorderLayout.NORTH);
        cadastro.add(editorItens, BorderLayout.CENTER);
        cadastro.add(b, BorderLayout.SOUTH);

        JPanel p = new JPanel(new GridBagLayout());
        p.setBorder(BorderFactory.createTitledBorder("Filtros e pesquisa"));
        Componentes.componente(p, g, 0, "Evento:", txtPesq);
        Componentes.componente(p, g, 1, "Situação:", cbSit);
        JPanel busca = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        busca.add(btBuscar);
        busca.add(btTodos);
        JPanel topo = new JPanel(new BorderLayout(4, 4));
        topo.add(p, BorderLayout.CENTER);
        topo.add(busca, BorderLayout.SOUTH);
        add(topo, BorderLayout.NORTH);

        JScrollPane sp = new JScrollPane(tabela);
        sp.setBorder(BorderFactory.createTitledBorder("Regras Cadastradas"));

        add(sp, BorderLayout.CENTER);
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
        btEditar.setEnabled(false);
        btSit.setEnabled(false);
        btExcluir.setEnabled(false);
        tabela.getSelectionModel().addListSelectionListener(e -> {
            boolean selecionou = tabela.getSelectedRow() != -1;
            btEditar.setEnabled(selecionou);
            btSit.setEnabled(selecionou);
            btExcluir.setEnabled(selecionou);
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

    public void abrirCadastro(String titulo) {
        if (dialogo == null) {
            dialogo = new JDialog(SwingUtilities.getWindowAncestor(this),
                    titulo, JDialog.ModalityType.APPLICATION_MODAL);
            dialogo.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialogo.add(cadastro);
            dialogo.setMinimumSize(new Dimension(760, 680));
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
        long id = ((Number) tabela.getValueAt(linha, 0)).longValue();
        for (RegraContabilizacao regra : regras) {
            if (regra.getId() == id) {
                exibirRegra(regra);
                abrirCadastro("Editar regra");
                return;
            }
        }
    }

    public void carregarRegras(List<RegraContabilizacao> registros) {
        regras.clear();
        modelo.setRowCount(0);
        for (RegraContabilizacao regra : registros) {
            if (empresaId > 0 && regra.getEmpresaId() == empresaId) {
                regras.add(regra);
                modelo.addRow(new Object[] {regra.getId(), regra.getModuloOrigem(),
                        regra.getEventoOrigem(), regra.getDescricaoEvento(),
                        regra.isAtivo() ? "Ativa" : "Inativa"});
            }
        }
    }

    public void exibirRegra(RegraContabilizacao regra) {
        if (regra.getEmpresaId() != empresaId) {
            throw new IllegalArgumentException("A regra não pertence à empresa selecionada.");
        }
        limpar();
        selecionada = regra;
        txtCod.setText(String.valueOf(regra.getId()));
        txtModulo.setText(regra.getModuloOrigem());
        txtEvento.setText(regra.getEventoOrigem());
        txtDesc.setText(regra.getDescricaoEvento());
        chkAtiva.setSelected(regra.isAtivo());
        for (RegraContabilizacaoItem item : regra.getItens()) {
            RegraContabilizacaoItem copia = new RegraContabilizacaoItem(item.getId(), item.getRegraId(),
                    item.getOrdem(), item.getContaId(), item.getTipoMovimento(),
                    item.getPercentual(), item.getDescricao());
            editorItens.itens.add(copia);
            editorItens.adicionarLinha(copia);
        }
        btSalvar.setEnabled(false);
        btAtual.setEnabled(true);
        editorItens.limparItem();
    }

    public ArrayList<RegraContabilizacaoItem> getItens() {
        return new ArrayList<>(editorItens.itens);
    }

    public void limpar() {
        selecionada = null;
        txtCod.setText("");
        txtDesc.setText("");
        txtModulo.setText("");
        txtEvento.setText("");
        editorItens.itens.clear();
        editorItens.modeloItens.setRowCount(0);
        editorItens.limparItem();
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

    public void setContexto(long empresaId, String empresa) {
        this.empresaId = empresaId;
        txtEmpresa.setText(empresa);
        regras.clear();
        modelo.setRowCount(0);
        editorItens.cbConta.removeAllItems();
        limpar();
    }

    public void limparItem() {
        editorItens.limparItem();
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public JTextField getTxtModulo() {
        return txtModulo;
    }

    public JTextField getTxtEvento() {
        return txtEvento;
    }

    public JTextField getTxtOrdem() {
        return editorItens.txtOrdem;
    }

    public JTextField getTxtPercentual() {
        return editorItens.txtPercentual;
    }

    public JTextField getTxtDescricaoItem() {
        return editorItens.txtDescricaoItem;
    }

    public JComboBox<Long> getCbConta() {
        return editorItens.cbConta;
    }

    public JComboBox<TipoMovimento> getCbMovimento() {
        return editorItens.cbMovimento;
    }

    public JTable getTabItens() {
        return editorItens.tabelaItens;
    }

    public JButton getBtAdicionarItem() {
        return editorItens.btAdicionarItem;
    }

    public JButton getBtAlterarItem() {
        return editorItens.btAlterarItem;
    }

    public JButton getBtRemoverItem() {
        return editorItens.btRemoverItem;
    }

    public void carregarCombo(JComboBox<Long> combo, java.util.Map<Long, String> opcoes) {
        Componentes.carregarCombo(combo, opcoes);
    }

    public RegraContabilizacao lerRegra() {
        if (empresaId <= 0 || txtModulo.getText().trim().isEmpty()
                || txtEvento.getText().trim().isEmpty() || txtDesc.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe empresa, módulo, evento e descrição.");
        }
        if (txtModulo.getText().trim().length() > 30 || txtEvento.getText().trim().length() > 100
                || txtDesc.getText().trim().length() > 150) {
            throw new IllegalArgumentException("Módulo, evento ou descrição excedem o tamanho permitido.");
        }
        RegraContabilizacao regra = new RegraContabilizacao();
        if (!txtCod.getText().isEmpty()) {
            regra.setId(Long.parseLong(txtCod.getText()));
        }
        if (selecionada != null) {
            regra.setCriadoEm(selecionada.getCriadoEm());
        }
        regra.setEmpresaId(empresaId);
        regra.setModuloOrigem(txtModulo.getText().trim());
        regra.setEventoOrigem(txtEvento.getText().trim());
        regra.setDescricaoEvento(txtDesc.getText().trim());
        regra.setAtivo(chkAtiva.isSelected());
        regra.setItens(getItens());
        if (!regra.itensValidos()) {
            throw new IllegalArgumentException("Confira contas, ordens, movimentos e percentuais dos itens.");
        }
        if (regra.isAtivo() && !regra.percentuaisValidos()) {
            throw new IllegalArgumentException("Para ativar a regra, débitos e créditos devem somar 100% cada.");
        }
        return regra;
    }

}
