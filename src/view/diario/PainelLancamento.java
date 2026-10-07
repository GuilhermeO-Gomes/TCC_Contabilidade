package view.diario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.ItemLancamento;
import model.LancamentoContabil;
import model.StatusLancamento;
import model.TipoMovimento;
import view.ComponentesContabeis;

public class PainelLancamento extends JPanel {
    private static final long serialVersionUID = 1L;
    private final LancamentoContabil lancamento;
    private final Consumer<LancamentoContabil> aoSalvar;
    private final JSpinner txt_data;
    private final JTextField txt_documento = new JTextField(20);
    private final JTextField txt_historico = new JTextField(35);
    private final JComboBox<StatusLancamento> cmb_status = new JComboBox<StatusLancamento>(StatusLancamento.values());
    private final JTextField txt_conta = new JTextField(20);
    private final JTextField txt_centro = new JTextField(20);
    private final JComboBox<TipoMovimento> cmb_movimento = new JComboBox<TipoMovimento>(TipoMovimento.values());
    private final JTextField txt_valor = new JTextField(12);
    private final DefaultTableModel modeloTabela = ComponentesContabeis.modeloTabela("Conta", "Centro de custo", "Movimento", "Valor");
    private final JTable tabelaPartidas = ComponentesContabeis.tabela("lancamento.partidas", modeloTabela);
    private final JLabel lbl_totais = new JLabel();

    public PainelLancamento(LancamentoContabil original, Consumer<LancamentoContabil> aoSalvar) {
        lancamento = original == null ? new LancamentoContabil() : new LancamentoContabil(original);
        this.aoSalvar = aoSalvar;
        txt_data = ComponentesContabeis.criarData("lancamento.data", lancamento.getData());
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(800, 650));
        montar_tela();
        txt_documento.setText(lancamento.getDocumento());
        txt_historico.setText(lancamento.getHistorico());
        cmb_status.setSelectedItem(lancamento.getStatus());
        atualizar_partidas();
    }

    private void montar_tela() {
        JPanel jp_form = new JPanel(new GridBagLayout());
        jp_form.setBorder(BorderFactory.createTitledBorder("Lançamento contábil"));
        txt_documento.setName("lancamento.documento");
        txt_historico.setName("lancamento.historico");
        cmb_status.setName("lancamento.status");
        txt_conta.setName("partida.conta");
        txt_centro.setName("partida.centro");
        cmb_movimento.setName("partida.movimento");
        txt_valor.setName("partida.valor");
        ComponentesContabeis.adicionar_componente(0, jp_form, "Data:", txt_data);
        ComponentesContabeis.adicionar_componente(1, jp_form, "Documento:", txt_documento);
        ComponentesContabeis.adicionar_componente(2, jp_form, "Histórico:", txt_historico);
        ComponentesContabeis.adicionar_componente(3, jp_form, "Status:", cmb_status);
        add(jp_form, BorderLayout.NORTH);

        JPanel jp_partidas = new JPanel(new BorderLayout(8, 8));
        JPanel jp_item = new JPanel(new GridBagLayout());
        jp_item.setBorder(BorderFactory.createTitledBorder("Dados da partida selecionada / nova partida"));
        ComponentesContabeis.adicionar_componente(0, jp_item, "Conta:", txt_conta);
        ComponentesContabeis.adicionar_componente(1, jp_item, "Centro de custo:", txt_centro);
        ComponentesContabeis.adicionar_componente(2, jp_item, "Movimento:", cmb_movimento);
        ComponentesContabeis.adicionar_componente(3, jp_item, "Valor:", txt_valor);
        JPanel jp_edicao = new JPanel(new BorderLayout());
        jp_edicao.add(jp_item, BorderLayout.CENTER);
        jp_edicao.add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("partida.adicionar", "Adicionar partida", () -> adicionar_partida()),
                ComponentesContabeis.botao("partida.atualizar", "Atualizar partida", () -> atualizar_partida()),
                ComponentesContabeis.botao("partida.remover", "Remover partida", () -> remover_partida())), BorderLayout.SOUTH);
        jp_partidas.add(jp_edicao, BorderLayout.NORTH);
        jp_partidas.add(ComponentesContabeis.lista("Partidas", tabelaPartidas), BorderLayout.CENTER);
        add(jp_partidas, BorderLayout.CENTER);
        tabelaPartidas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabelaPartidas.getSelectedRow() >= 0) {
                ItemLancamento item = lancamento.getItens().get(ComponentesContabeis.linhaSelecionada(tabelaPartidas));
                txt_conta.setText(item.getConta());
                txt_centro.setText(item.getCentroCusto());
                cmb_movimento.setSelectedItem(item.getTipoMovimento());
                txt_valor.setText(item.getValor().toPlainString());
            }
        });
        JPanel jp_rodape = new JPanel(new BorderLayout());
        lbl_totais.setName("lancamento.totais");
        jp_rodape.add(lbl_totais, BorderLayout.NORTH);
        jp_rodape.add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("lancamento.salvar", "Salvar", () -> salvar()),
                ComponentesContabeis.botao("lancamento.cancelar", "Cancelar", () -> ComponentesContabeis.fechar(this))), BorderLayout.SOUTH);
        add(jp_rodape, BorderLayout.SOUTH);
    }

    private ItemLancamento ler_partida(int id) {
        BigDecimal valor = ComponentesContabeis.valor(txt_valor.getText());
        if (txt_conta.getText().trim().isEmpty() || valor.signum() <= 0) {
            throw new IllegalArgumentException("Preencha a conta e um valor positivo para a partida.");
        }
        return new ItemLancamento(id, txt_conta.getText().trim(), txt_centro.getText().trim(),
                (TipoMovimento) cmb_movimento.getSelectedItem(), valor);
    }

    private void adicionar_partida() {
        int maior = 0;
        for (ItemLancamento item : lancamento.getItens()) {
            maior = Math.max(maior, item.getId());
        }
        lancamento.adicionarItem(ler_partida(maior + 1));
        atualizar_partidas();
        txt_conta.setText("");
        txt_centro.setText("");
        txt_valor.setText("");
        txt_conta.requestFocusInWindow();
    }

    private void atualizar_partida() {
        int linha = ComponentesContabeis.linhaSelecionada(tabelaPartidas);
        ItemLancamento atual = lancamento.getItens().get(linha);
        ItemLancamento novo = ler_partida(atual.getId());
        atual.setConta(novo.getConta());
        atual.setCentroCusto(novo.getCentroCusto());
        atual.setTipoMovimento(novo.getTipoMovimento());
        atual.setValor(novo.getValor());
        atualizar_partidas();
    }

    private void remover_partida() {
        lancamento.removerItem(lancamento.getItens().get(ComponentesContabeis.linhaSelecionada(tabelaPartidas)));
        atualizar_partidas();
    }

    private void atualizar_partidas() {
        modeloTabela.setRowCount(0);
        for (ItemLancamento item : lancamento.getItens()) {
            modeloTabela.addRow(new Object[] {item.getConta(), item.getCentroCusto(), item.getTipoMovimento(), item.getValor()});
        }
        lbl_totais.setText("Débitos: " + ComponentesContabeis.moeda(lancamento.calcularTotalDebitos())
                + "   Créditos: " + ComponentesContabeis.moeda(lancamento.calcularTotalCreditos())
                + "   Diferença: " + ComponentesContabeis.moeda(lancamento.calcularDiferenca())
                + "   " + (lancamento.getItens().isEmpty() ? "Sem partidas" : lancamento.estaBalanceado() ? "Balanceado" : "Inconsistente"));
    }

    private void salvar() {
        lancamento.setData(ComponentesContabeis.data(txt_data));
        lancamento.setDocumento(txt_documento.getText().trim());
        lancamento.setHistorico(txt_historico.getText().trim());
        lancamento.setStatus((StatusLancamento) cmb_status.getSelectedItem());
        if (lancamento.getStatus() == StatusLancamento.VALIDO && (lancamento.getItens().isEmpty()
                || !lancamento.estaBalanceado() || lancamento.getDocumento().isEmpty() || lancamento.getHistorico().isEmpty())) {
            throw new IllegalArgumentException("Um lançamento válido precisa de documento, histórico e partidas balanceadas.\n"
                    + "Corrija os dados ou salve como rascunho/inconsistente.");
        }
        aoSalvar.accept(new LancamentoContabil(lancamento));
        ComponentesContabeis.fechar(this);
    }
}
