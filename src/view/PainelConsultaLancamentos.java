package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Lancamento;

final class PainelConsultaLancamentos extends JPanel {

    private static final long serialVersionUID = 1L;

    final JTextField txtInicio = new JTextField(10),
            txtFim = new JTextField(10),
            txtPesq = new JTextField(25);
    final JComboBox<Long> cbContaPesq = new JComboBox<>(),
            cbCCPesq = new JComboBox<>();
    final JComboBox<String> cbStatusPesq = new JComboBox<>(
            new String[] {"Todos", "RASCUNHO", "CONTABILIZADO", "ESTORNADO"});
    final JButton btBuscar = new JButton("Pesquisar"),
            btTodos = new JButton("Mostrar todos"),
            btAbrir = new JButton("Abrir lançamento"),
            btEstornar = new JButton("Estornar lançamento");

    private final ArrayList<Lancamento> lancamentos = new ArrayList<>();
    private final DefaultTableModel modeloLancamentos = new DefaultTableModel(
            new Object[] {"Código", "Data", "Documento", "Lote", "Histórico", "Débito (R$)", "Crédito (R$)", "Status"}, 0
    ) {
        private static final long serialVersionUID = 1L;
        public boolean isCellEditable(int l, int c) {
            return false;
        }
    };

    final JTable tabelaLancamentos = new JTable(modeloLancamentos);

    PainelConsultaLancamentos() {
        super(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel filtros = new JPanel(new GridBagLayout());
        filtros.setBorder(BorderFactory.createTitledBorder("Filtros e pesquisa"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        JPanel periodo = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        periodo.add(txtInicio);
        periodo.add(new JLabel("  até  "));
        periodo.add(txtFim);
        Componentes.componente(filtros, g, 0, "Período (dd/mm/aaaa):", periodo);
        Componentes.componente(filtros, g, 1, "Histórico / descrição:", txtPesq);
        Componentes.componente(filtros, g, 2, "Conta:", cbContaPesq);
        Componentes.componente(filtros, g, 3, "Centro de custos:", cbCCPesq);

        Componentes.componente(filtros, g, 4, "Status:", cbStatusPesq);

        JPanel busca = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        busca.add(btBuscar);
        busca.add(btTodos);
        JPanel topo = new JPanel(new BorderLayout(4, 4));
        topo.add(filtros, BorderLayout.CENTER);
        topo.add(busca, BorderLayout.SOUTH);
        add(topo, BorderLayout.NORTH);

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
        add(sp, BorderLayout.CENTER);

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        botoes.add(btAbrir);
        botoes.add(btEstornar);
        btEstornar.setEnabled(false);
        btEstornar.setToolTipText("O estorno gera um lançamento reverso e preserva o original.");
        tabelaLancamentos.getSelectionModel().addListSelectionListener(e -> {
            int linha = tabelaLancamentos.getSelectedRow();
            btEstornar.setEnabled(linha != -1
                    && "CONTABILIZADO".equals(tabelaLancamentos.getValueAt(linha, 7)));
        });
        add(botoes, BorderLayout.SOUTH);
    }

    void carregar(long empresaId, List<Lancamento> registros) {
        lancamentos.clear();
        modeloLancamentos.setRowCount(0);
        for (Lancamento lancamento : registros) {
            if (empresaId > 0 && lancamento.getEmpresaId() == empresaId) {
                lancamentos.add(lancamento);
                modeloLancamentos.addRow(new Object[] {lancamento.getId(), lancamento.getDataLancamento(),
                        lancamento.getNumeroDocumento(), lancamento.getLoteId(), lancamento.getDescricao(),
                        lancamento.getTotalDebitos(), lancamento.getTotalCreditos(), lancamento.getStatus()});
            }
        }
    }

    Lancamento lancamentoSelecionado() {
        int linha = tabelaLancamentos.getSelectedRow();
        if (linha == -1) {
            return null;
        }
        return lancamentos.get(tabelaLancamentos.convertRowIndexToModel(linha));
    }
}
