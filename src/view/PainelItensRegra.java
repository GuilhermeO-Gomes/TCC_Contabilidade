package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import model.RegraContabilizacaoItem;
import model.TipoMovimento;

final class PainelItensRegra extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JTextField txtCod;
    final ArrayList<RegraContabilizacaoItem> itens = new ArrayList<>();
    final JTextField txtOrdem = new JTextField(5), txtPercentual = new JTextField("100,0000", 10),
            txtDescricaoItem = new JTextField(25);
    final JComboBox<Long> cbConta = new JComboBox<>();
    final JComboBox<TipoMovimento> cbMovimento = new JComboBox<>(TipoMovimento.values());
    final JButton btAdicionarItem = new JButton("Adicionar item"), btAlterarItem = new JButton("Alterar item"),
            btRemoverItem = new JButton("Remover item");
    final DefaultTableModel modeloItens = new DefaultTableModel(
            new Object[] {"Ordem", "Conta", "Movimento", "Percentual", "Descrição"}, 0) {
        private static final long serialVersionUID = 1L;

        public boolean isCellEditable(int linha, int coluna) {
            return false;
        }
    };
    final JTable tabelaItens = new JTable(modeloItens);

    PainelItensRegra(JTextField txtCod) {
        super(new BorderLayout(6, 6));
        this.txtCod = txtCod;
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;
        setBorder(BorderFactory.createTitledBorder("Itens da regra"));
        JPanel campos = new JPanel(new GridBagLayout());
        Componentes.componente(campos, g, 0, "Ordem:", txtOrdem);
        Componentes.componente(campos, g, 1, "Conta:", cbConta);
        Componentes.componente(campos, g, 2, "Movimento:", cbMovimento);
        Componentes.componente(campos, g, 3, "Percentual:", txtPercentual);
        Componentes.componente(campos, g, 4, "Descrição:", txtDescricaoItem);
        JPanel acoesItens = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        acoesItens.add(btAdicionarItem);
        acoesItens.add(btAlterarItem);
        acoesItens.add(btRemoverItem);
        JPanel editor = new JPanel(new BorderLayout(4, 4));
        editor.add(campos, BorderLayout.CENTER);
        editor.add(acoesItens, BorderLayout.SOUTH);
        add(editor, BorderLayout.NORTH);
        add(new JScrollPane(tabelaItens), BorderLayout.CENTER);
        add(new JLabel("Para ativar: débitos 100% e créditos 100%."), BorderLayout.SOUTH);
        tabelaItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaItens.setRowHeight(24);
        tabelaItens.getTableHeader().setReorderingAllowed(false);
        tabelaItens.getSelectionModel().addListSelectionListener(e -> selecionarItem());
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
    }

    private Object[] dadosItem(RegraContabilizacaoItem item) {
        return new Object[] {item.getOrdem(), item.getContaId(), item.getTipoMovimento(),
                item.getPercentual(), item.getDescricao()};
    }

    void adicionarLinha(RegraContabilizacaoItem item) {
        modeloItens.addRow(dadosItem(item));
    }

    private void gravarItem(boolean alterar) {
        int linha = tabelaItens.getSelectedRow();
        if (alterar && linha == -1) {
            return;
        }
        Long conta = (Long) cbConta.getSelectedItem();
        TipoMovimento movimento = (TipoMovimento) cbMovimento.getSelectedItem();
        if (conta == null || conta <= 0 || movimento == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma conta e um tipo de movimento.");
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
            item.setTipoMovimento(movimento);
            String percentual = txtPercentual.getText().trim();
            if (!percentual.matches("[0-9]+([.,][0-9]{1,4})?")) {
                JOptionPane.showMessageDialog(this, "Informe um percentual com até quatro casas decimais.");
                return;
            }
            item.setPercentual(new BigDecimal(percentual.replace(',', '.')));
            if (item.getPercentual().signum() <= 0
                    || item.getPercentual().compareTo(BigDecimal.valueOf(100)) > 0) {
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
        txtPercentual.setText(itens.get(linha).getPercentual().stripTrailingZeros().toPlainString());
        Object descricao = tabelaItens.getValueAt(linha, 4);
        txtDescricaoItem.setText(descricao == null ? "" : descricao.toString());
    }

    void limparItem() {
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
}
