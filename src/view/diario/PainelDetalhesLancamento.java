package view.diario;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.ItemLancamento;
import model.LancamentoContabil;
import view.ComponentesContabeis;

public class PainelDetalhesLancamento extends JPanel {
    private static final long serialVersionUID = 1L;

    public PainelDetalhesLancamento(LancamentoContabil lancamento) {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(780, 500));
        JPanel jp_form = new JPanel(new GridBagLayout());
        jp_form.setBorder(BorderFactory.createTitledBorder("Dados do lançamento"));
        ComponentesContabeis.adicionar_componente(0, jp_form, "ID:", new JLabel(String.valueOf(lancamento.getId())));
        ComponentesContabeis.adicionar_componente(1, jp_form, "Data:", new JLabel(lancamento.getData().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        ComponentesContabeis.adicionar_componente(2, jp_form, "Documento:", new JLabel(lancamento.getDocumento()));
        ComponentesContabeis.adicionar_componente(3, jp_form, "Histórico:", ComponentesContabeis.texto(lancamento.getHistorico()));
        ComponentesContabeis.adicionar_componente(4, jp_form, "Status:", new JLabel(lancamento.getStatus().toString()));
        add(jp_form, BorderLayout.NORTH);
        DefaultTableModel modelo = ComponentesContabeis.modeloTabela("Conta", "Centro de custo", "Movimento", "Valor");
        for (ItemLancamento item : lancamento.getItens()) {
            modelo.addRow(new Object[] {item.getConta(), item.getCentroCusto(), item.getTipoMovimento(), item.getValor()});
        }
        JTable tabela = ComponentesContabeis.tabela("detalhes.partidas", modelo);
        add(ComponentesContabeis.lista("Partidas (somente leitura)", tabela), BorderLayout.CENTER);
        JPanel jp_rodape = new JPanel(new BorderLayout());
        jp_rodape.add(new JLabel("Débitos: " + ComponentesContabeis.moeda(lancamento.calcularTotalDebitos())
                + "   Créditos: " + ComponentesContabeis.moeda(lancamento.calcularTotalCreditos())
                + "   Diferença: " + ComponentesContabeis.moeda(lancamento.calcularDiferenca())), BorderLayout.NORTH);
        jp_rodape.add(ComponentesContabeis.botoes(ComponentesContabeis.botao("detalhes.fechar", "Fechar",
                () -> ComponentesContabeis.fechar(this))), BorderLayout.SOUTH);
        add(jp_rodape, BorderLayout.SOUTH);
    }
}
