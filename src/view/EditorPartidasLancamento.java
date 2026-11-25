package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import model.PartidaLancamento;
import model.TipoMovimento;

final class EditorPartidasLancamento {

    private final JPanel tela;
    private final JTextField txtStatus;
    private final java.util.function.LongSupplier lancamentoId;
    private final Runnable atualizarTotais;
    private final JPanel dadosPartida;
    JDialog dialogoPartida;
    final JTextField txtValor = new JTextField(12),
            txtDescricaoPartida = new JTextField(25);
    final JComboBox<Long> cbHistPartida = new JComboBox<>(), cbConta = new JComboBox<>(), cbCC = new JComboBox<>();
    final JComboBox<TipoMovimento> cbTipo = new JComboBox<>(TipoMovimento.values());
    final JButton btAdicionar = new JButton("Adicionar partida"), btAlterar = new JButton("Alterar partida"),
            btRemover = new JButton("Remover partida"), btNovaPartida = new JButton("Nova partida"),
            btEditarPartida = new JButton("Editar partida");
    final ArrayList<PartidaLancamento> partidas = new ArrayList<>();
    final Set<Long> contasDespesa = new HashSet<>();
    Map<Long, String> historicosPadrao = java.util.Collections.emptyMap();
    final DefaultTableModel modeloPartidas = new DefaultTableModel(
            new Object[] {"Conta", "Centro de custos", "Débito (R$)", "Crédito (R$)", "Descrição"}, 0
    ) {
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };
    final JTable tabelaPartidas = new JTable(modeloPartidas);

    EditorPartidasLancamento(JPanel tela, JTextField txtStatus,
            java.util.function.LongSupplier lancamentoId, Runnable atualizarTotais) {
        this.tela = tela;
        this.txtStatus = txtStatus;
        this.lancamentoId = lancamentoId;
        this.atualizarTotais = atualizarTotais;
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;
        JPanel partida = new JPanel(new GridBagLayout());
        dadosPartida = new JPanel(new BorderLayout(8, 8));
        dadosPartida.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        partida.setBorder(BorderFactory.createTitledBorder("Dados da partida"));
        Componentes.componente(partida, g, 0, "Conta:", cbConta);
        Componentes.componente(partida, g, 1, "Tipo:", cbTipo);
        Componentes.componente(partida, g, 2, "Centro de custos:", cbCC);
        Componentes.componente(partida, g, 3, "Valor (R$):", txtValor);
        Componentes.componente(partida, g, 4, "Histórico padrão:", cbHistPartida);
        Componentes.componente(partida, g, 5, "Descrição:", txtDescricaoPartida);
        txtValor.setHorizontalAlignment(JTextField.RIGHT);
        txtValor.setToolTipText("Exemplo: 1500,00");
        cbCC.setToolTipText("Disponível para contas de despesa.");

        JPanel incluir = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        incluir.add(btAdicionar);
        incluir.add(btAlterar);
        JButton btFechar = new JButton("Fechar");
        incluir.add(btFechar);
        dadosPartida.add(partida, BorderLayout.NORTH);
        dadosPartida.add(incluir, BorderLayout.SOUTH);
        btFechar.addActionListener(e -> dialogoPartida.dispose());
        btAdicionar.addActionListener(e -> gravarPartida(false));
        btAlterar.addActionListener(e -> gravarPartida(true));
        btRemover.addActionListener(e -> {
            int linha = tabelaPartidas.getSelectedRow();
            if ("RASCUNHO".equals(txtStatus.getText()) && linha != -1) {
                partidas.remove(linha);
                modeloPartidas.removeRow(linha);
                atualizarTotais.run();
                limparPartida();
            }
        });
        cbConta.addActionListener(e -> atualizarCentroCusto());
        cbHistPartida.addActionListener(e -> {
            String descricao = historicosPadrao.get(cbHistPartida.getSelectedItem());
            if (descricao != null) {
                txtDescricaoPartida.setText(descricao);
            }
        });
        btNovaPartida.addActionListener(e -> {
            limparPartida();
            abrirPartida("Nova partida");
        });
        btEditarPartida.addActionListener(e -> editarPartida());
    }

    JScrollPane tabela() {
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
        return sp;
    }

    private void abrirPartida(String titulo) {
        if (dialogoPartida == null) {
            dialogoPartida = new JDialog(SwingUtilities.getWindowAncestor(tela),
                    titulo, JDialog.ModalityType.APPLICATION_MODAL);
            dialogoPartida.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialogoPartida.add(dadosPartida);
            dialogoPartida.setMinimumSize(new Dimension(540, 270));
            dialogoPartida.pack();
        }
        dialogoPartida.setTitle(titulo);
        dialogoPartida.setLocationRelativeTo(tela);
        dialogoPartida.setVisible(true);
    }

    private void editarPartida() {
        int linha = tabelaPartidas.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(tela, "Selecione uma partida para editar.");
            return;
        }
        Componentes.selecionarItem(cbConta, tabelaPartidas.getValueAt(linha, 0));
        if (cbCC.isEnabled()) {
            Componentes.selecionarItem(cbCC, tabelaPartidas.getValueAt(linha, 1));
        }
        Object debito = tabelaPartidas.getValueAt(linha, 2);
        String valor = debito == null ? "" : debito.toString().trim();
        boolean credito = valor.isEmpty() || valor.matches("0([,.]0+)?");
        cbTipo.setSelectedIndex(credito ? 1 : 0);
        Object selecionado = tabelaPartidas.getValueAt(linha, credito ? 3 : 2);
        txtValor.setText(selecionado == null ? "" : selecionado.toString());
        Object descricao = tabelaPartidas.getValueAt(linha, 4);
        cbHistPartida.setSelectedIndex(-1);
        txtDescricaoPartida.setText(descricao == null ? "" : descricao.toString());
        btAdicionar.setEnabled(false);
        btAlterar.setEnabled(true);
        abrirPartida("Editar partida");
    }

    void limparPartida() {
        cbConta.setSelectedIndex(-1);
        cbTipo.setSelectedIndex(0);
        cbCC.setSelectedIndex(-1);
        cbHistPartida.setSelectedIndex(-1);
        txtValor.setText("");
        txtDescricaoPartida.setText("");
        tabelaPartidas.clearSelection();
        btAdicionar.setEnabled("RASCUNHO".equals(txtStatus.getText()));
        btAlterar.setEnabled(false);
    }

    void setContasDespesa(Collection<Long> ids) {
        contasDespesa.clear();
        contasDespesa.addAll(ids);
        atualizarCentroCusto();
    }

    void atualizarCentroCusto() {
        boolean despesa = contasDespesa.contains(cbConta.getSelectedItem());
        if (!despesa) {
            cbCC.setSelectedIndex(-1);
        }
        cbCC.setEnabled(despesa && "RASCUNHO".equals(txtStatus.getText()));
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
            atualizarTotais.run();
            limparPartida();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(tela, e.getMessage());
        }
    }

    PartidaLancamento lerPartida() {
        Long conta = (Long) cbConta.getSelectedItem();
        if (conta == null || conta <= 0 || cbTipo.getSelectedItem() == null) {
            throw new IllegalArgumentException("Selecione conta e movimento da partida.");
        }
        String texto = txtValor.getText().trim();
        if (texto.matches("[0-9]{1,3}(\\.[0-9]{3})+,[0-9]+")) {
            texto = texto.replace(".", "");
        }
        if (!texto.matches("[0-9]+([.,][0-9]+)?")) {
            throw new IllegalArgumentException("Informe um valor monetário válido.");
        }
        BigDecimal valor = new BigDecimal(texto.replace(',', '.'));
        if (valor.signum() <= 0 || valor.stripTrailingZeros().scale() > 2 || valor.precision() - valor.scale() > 13) {
            throw new IllegalArgumentException("O valor deve ser positivo e caber em DECIMAL(15,2).");
        }
        if (txtDescricaoPartida.getText().length() > 255) {
            throw new IllegalArgumentException("A descrição da partida aceita até 255 caracteres.");
        }
        PartidaLancamento partida = new PartidaLancamento();
        partida.setLancamentoId(lancamentoId.getAsLong());
        partida.setContaId(conta);
        partida.setCentroCustoId(contasDespesa.contains(conta) ? (Long) cbCC.getSelectedItem() : null);
        partida.setTipoMovimento((TipoMovimento) cbTipo.getSelectedItem());
        partida.setValor(valor);
        partida.setDescricao(txtDescricaoPartida.getText().trim());
        return partida;
    }

    Object[] dadosPartida(PartidaLancamento partida) {
        boolean debito = partida.getTipoMovimento() == TipoMovimento.DEBITO;
        return new Object[] {partida.getContaId(), partida.getCentroCustoId(),
                debito ? partida.getValor() : BigDecimal.ZERO,
                debito ? BigDecimal.ZERO : partida.getValor(), partida.getDescricao()};
    }
}
