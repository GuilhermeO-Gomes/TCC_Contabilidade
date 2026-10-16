package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    private final ArrayList<RegraContabilizacaoItem> itens = new ArrayList<>();
    private final JTextField txtEmpresa = new JTextField(25),
            txtModulo = new JTextField(20),
            txtEvento = new JTextField(25),
            txtOrdem = new JTextField(5),
            txtPercentual = new JTextField("100,0000", 10),
            txtDescricaoItem = new JTextField(25);
    private final JComboBox<Long> cbConta = new JComboBox<>();
    private final JComboBox<TipoMovimento> cbMovimento = new JComboBox<>(TipoMovimento.values());
    private final JComboBox<String> cbSit = new JComboBox<>(new String[] {"Todas", "Ativas", "Inativas"});
    private final JButton btAdicionarItem = new JButton("Adicionar item"),
            btAlterarItem = new JButton("Alterar item"),
            btRemoverItem = new JButton("Remover item");
    private final DefaultTableModel modeloItens = new DefaultTableModel(
            new Object[] {"Ordem", "Conta", "Movimento", "Percentual", "Descrição"}, 0) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };
    private final JTable tabelaItens = new JTable(modeloItens);

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

        componente(f, g, 0, "Código:", txtCod);
        componente(f, g, 1, "Descrição do evento:", txtDesc);
        componente(f, g, 2, "Empresa:", txtEmpresa);
        componente(f, g, 3, "Módulo de origem:", txtModulo);
        componente(f, g, 4, "Evento de origem:", txtEvento);
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
        JPanel painelItens = new JPanel(new BorderLayout(6, 6));
        painelItens.setBorder(BorderFactory.createTitledBorder("Itens da regra"));
        JPanel campos = new JPanel(new GridBagLayout());
        componente(campos, g, 0, "Ordem:", txtOrdem);
        componente(campos, g, 1, "Conta:", cbConta);
        componente(campos, g, 2, "Movimento:", cbMovimento);
        componente(campos, g, 3, "Percentual:", txtPercentual);
        componente(campos, g, 4, "Descrição:", txtDescricaoItem);
        JPanel acoesItens = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        acoesItens.add(btAdicionarItem);
        acoesItens.add(btAlterarItem);
        acoesItens.add(btRemoverItem);
        JPanel editor = new JPanel(new BorderLayout(4, 4));
        editor.add(campos, BorderLayout.CENTER);
        editor.add(acoesItens, BorderLayout.SOUTH);
        painelItens.add(editor, BorderLayout.NORTH);
        painelItens.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);
        painelItens.add(new JLabel("Para ativar: débitos 100% e créditos 100%."), BorderLayout.SOUTH);
        tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaItens.setRowHeight(24);
        tabelaItens.getTableHeader().setReorderingAllowed(false);
        tabelaItens.getSelectionModel().addListSelectionListener(e -> selecionarItem());
        cadastro.add(painelItens, BorderLayout.CENTER);
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
        btAdicionarItem.addActionListener(e -> gravarItem(false));
        btAlterarItem.addActionListener(e -> gravarItem(true));
        btRemoverItem.addActionListener(e -> {
            int linha = tabelaItens.getSelectedRow();
            if (linha != -1) {
                itens.remove(linha);
                modeloItens.removeRow(linha);
                limparItem();
            }
        });
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
            RegraContabilizacaoItem copia = new RegraContabilizacaoItem();
            copia.setId(item.getId());
            copia.setRegraId(item.getRegraId());
            copia.setOrdem(item.getOrdem());
            copia.setContaId(item.getContaId());
            copia.setTipoMovimento(item.getTipoMovimento());
            copia.setPercentual(item.getPercentual());
            copia.setDescricao(item.getDescricao());
            itens.add(copia);
            adicionarLinha(copia);
        }
        btSalvar.setEnabled(false);
        btAtual.setEnabled(true);
        limparItem();
    }

    private Object[] dadosItem(RegraContabilizacaoItem item) {
        return new Object[] {item.getOrdem(), item.getContaId(), item.getTipoMovimento(),
                item.getPercentual(), item.getDescricao()};
    }

    private void adicionarLinha(RegraContabilizacaoItem item) {
        modeloItens.addRow(dadosItem(item));
    }

    private void gravarItem(boolean alterar) {
        int linha = tabelaItens.getSelectedRow();
        if (alterar && linha == -1) {
            return;
        }
        Long conta = (Long) cbConta.getSelectedItem();
        if (conta == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma conta.");
            return;
        }
        try {
            RegraContabilizacaoItem item = new RegraContabilizacaoItem();
            item.setOrdem(Integer.parseInt(txtOrdem.getText().trim()));
            for (int i = 0; i < itens.size(); i++) {
                if ((!alterar || i != linha) && itens.get(i).getOrdem() == item.getOrdem()) {
                    JOptionPane.showMessageDialog(this, "Já existe um item com essa ordem.");
                    return;
                }
            }
            if (item.getOrdem() < 0) {
                JOptionPane.showMessageDialog(this, "A ordem não pode ser negativa.");
                return;
            }
            item.setContaId(conta);
            item.setTipoMovimento((TipoMovimento) cbMovimento.getSelectedItem());
            item.setPercentual(new BigDecimal(txtPercentual.getText().trim().replace(',', '.')));
            if (item.getPercentual().signum() <= 0
                    || item.getPercentual().compareTo(new BigDecimal("100")) > 0
                    || item.getPercentual().stripTrailingZeros().scale() > 4) {
                JOptionPane.showMessageDialog(this, "Informe um percentual maior que zero e até 100, com até quatro casas decimais.");
                return;
            }
            item.setDescricao(txtDescricaoItem.getText().trim());
            if (item.getDescricao().length() > 255) {
                JOptionPane.showMessageDialog(this, "A descrição do item aceita até 255 caracteres.");
                return;
            }
            if (!txtCod.getText().isEmpty()) {
                item.setRegraId(Long.parseLong(txtCod.getText()));
            }
            if (alterar) {
                item.setId(itens.get(linha).getId());
                itens.set(linha, item);
                Object[] dados = dadosItem(item);
                for (int coluna = 0; coluna < dados.length; coluna++) {
                    modeloItens.setValueAt(dados[coluna], linha, coluna);
                }
            } else {
                itens.add(item);
                adicionarLinha(item);
            }
            limparItem();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Informe uma ordem inteira e um percentual numérico.");
        }
    }

    public ArrayList<RegraContabilizacaoItem> getItens() {
        return new ArrayList<>(itens);
    }

    private void selecionarItem() {
        int linha = tabelaItens.getSelectedRow();
        btAlterarItem.setEnabled(linha != -1);
        btRemoverItem.setEnabled(linha != -1);
        if (linha == -1) {
            return;
        }
        txtOrdem.setText(String.valueOf(tabelaItens.getValueAt(linha, 0)));
        Long conta = ((Number) tabelaItens.getValueAt(linha, 1)).longValue();
        cbConta.setSelectedIndex(-1);
        for (int i = 0; i < cbConta.getItemCount(); i++) {
            if (cbConta.getItemAt(i).equals(conta)) {
                cbConta.setSelectedIndex(i);
                break;
            }
        }
        cbMovimento.setSelectedItem(tabelaItens.getValueAt(linha, 2));
        txtPercentual.setText(String.valueOf(tabelaItens.getValueAt(linha, 3)));
        Object descricao = tabelaItens.getValueAt(linha, 4);
        txtDescricaoItem.setText(descricao == null ? "" : descricao.toString());
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
        txtCod.setText("");
        txtDesc.setText("");
        txtModulo.setText("");
        txtEvento.setText("");
        itens.clear();
        modeloItens.setRowCount(0);
        limparItem();
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

    public void limparItem() {
        tabelaItens.clearSelection();
        int proximaOrdem = 1;
        for (RegraContabilizacaoItem item : itens) {
            if (item.getOrdem() >= proximaOrdem) {
                proximaOrdem = item.getOrdem() + 1;
            }
        }
        txtOrdem.setText(String.valueOf(proximaOrdem));
        cbConta.setSelectedIndex(-1);
        cbMovimento.setSelectedIndex(0);
        txtPercentual.setText("100,0000");
        txtDescricaoItem.setText("");
        btAlterarItem.setEnabled(false);
        btRemoverItem.setEnabled(false);
    }

    public void setContexto(long empresaId, String empresa) {
        this.empresaId = empresaId;
        txtEmpresa.setText(empresa);
        regras.clear();
        modelo.setRowCount(0);
        cbConta.removeAllItems();
        limpar();
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
        return txtOrdem;
    }

    public JTextField getTxtPercentual() {
        return txtPercentual;
    }

    public JTextField getTxtDescricaoItem() {
        return txtDescricaoItem;
    }

    public JComboBox<Long> getCbConta() {
        return cbConta;
    }

    public JComboBox<TipoMovimento> getCbMovimento() {
        return cbMovimento;
    }

    public JTable getTabItens() {
        return tabelaItens;
    }

    public JButton getBtAdicionarItem() {
        return btAdicionarItem;
    }

    public JButton getBtAlterarItem() {
        return btAlterarItem;
    }

    public JButton getBtRemoverItem() {
        return btRemoverItem;
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
