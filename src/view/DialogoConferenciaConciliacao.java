package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

final class DialogoConferenciaConciliacao extends JDialog {

    private static final long serialVersionUID = 1L;

    DialogoConferenciaConciliacao(TelaConciliacaoContabil tela) {
        super(SwingUtilities.getWindowAncestor(tela), "Conferir conciliação", ModalityType.APPLICATION_MODAL);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel dados = new JPanel(new GridLayout(2, 1, 4, 4));
        dados.add(new JLabel("Conta: " + tela.getCbConta().getSelectedItem()));
        dados.add(new JLabel("Período: " + tela.getTxtInicio().getText() + " até " + tela.getTxtFim().getText()));
        painel.add(dados, BorderLayout.NORTH);

        JPanel movimentos = new JPanel(new GridLayout(2, 1, 0, 8));
        movimentos.add(montarTabela(copiarSelecionados(tela.getTabSis()), "Movimentos selecionados do sistema"));
        movimentos.add(montarTabela(copiarSelecionados(tela.getTabExt()), "Movimentos selecionados do extrato"));
        painel.add(movimentos, BorderLayout.CENTER);

        JPanel resumo = new JPanel(new GridLayout(1, 3, 8, 0));
        resumo.setBorder(BorderFactory.createTitledBorder("Totais dos movimentos selecionados"));
        resumo.add(Componentes.montarTotal("Sistema (R$)", new JTextField(tela.getTxtTotalSis().getText(), 12)));
        resumo.add(Componentes.montarTotal("Extrato (R$)", new JTextField(tela.getTxtTotalExt().getText(), 12)));
        resumo.add(Componentes.montarTotal("Diferença (R$)", new JTextField(tela.getTxtDif().getText(), 12)));

        JButton btVoltar = new JButton("Voltar");
        btVoltar.addActionListener(e -> dispose());
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        botoes.add(new JLabel("Status do par:"));
        botoes.add(tela.getCbStatusItem());
        botoes.add(new JLabel("Observação:"));
        botoes.add(tela.getTxtObservacaoItem());
        botoes.add(tela.getBtConciliar());
        botoes.add(btVoltar);
        JPanel rodape = new JPanel(new BorderLayout(4, 4));
        rodape.add(resumo, BorderLayout.CENTER);
        rodape.add(botoes, BorderLayout.SOUTH);
        painel.add(rodape, BorderLayout.SOUTH);
        add(painel);
        setSize(900, 620);
        setMinimumSize(new Dimension(760, 500));
        setLocationRelativeTo(tela);
    }

    private JTable copiarSelecionados(JTable origem) {
        DefaultTableModel copia = new DefaultTableModel(
                new Object[] {"Código", "Data", "Documento", origem.getColumnName(3),
                        "Natureza", "Valor (R$)", "Situação"}, 0) {
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int l, int c) {
                return false;
            }
        };
        for (int linha : origem.getSelectedRows()) {
            Object[] dados = new Object[origem.getColumnCount()];
            for (int coluna = 0; coluna < dados.length; coluna++) {
                dados[coluna] = origem.getValueAt(linha, coluna);
            }
            copia.addRow(dados);
        }
        return new JTable(copia);
    }

    static JScrollPane montarTabela(JTable tab, String titulo) {
        tab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tab.setRowHeight(24);
        tab.setFillsViewportHeight(true);
        tab.getTableHeader().setReorderingAllowed(false);
        tab.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tab.getColumnModel().getColumn(1).setPreferredWidth(90);
        tab.getColumnModel().getColumn(2).setPreferredWidth(100);
        tab.getColumnModel().getColumn(3).setPreferredWidth(220);
        tab.getColumnModel().getColumn(4).setPreferredWidth(90);
        tab.getColumnModel().getColumn(5).setPreferredWidth(100);
        DefaultTableCellRenderer moeda = new DefaultTableCellRenderer();
        moeda.setHorizontalAlignment(JLabel.RIGHT);
        tab.getColumnModel().getColumn(5).setCellRenderer(moeda);
        tab.getColumnModel().getColumn(6).setPreferredWidth(100);

        JScrollPane sp = new JScrollPane(tab);
        sp.setBorder(BorderFactory.createTitledBorder(titulo));
        return sp;
    }
}
