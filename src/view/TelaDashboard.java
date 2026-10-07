package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import model.ResumoContabil;

public class TelaDashboard extends JFrame {

    private static final long serialVersionUID = 1L;

    private ResumoContabil resumo;

    public TelaDashboard() {
        setTitle("Dashboard Contábil - Visão Geral");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        resumo = new ResumoContabil();
        criarComponentes();
    }

    private void criarComponentes() {
        JLabel titulo = new JLabel(
                "Dashboard Contábil - Visão Geral",
                SwingConstants.CENTER
        );
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        titulo.setBorder(BorderFactory.createEmptyBorder(12, 10, 5, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        JPanel painelResumo = new JPanel(new GridLayout(1, 4, 10, 10));
        painelResumo.add(criarCartao(
                "Lançamentos no mês",
                resumo.getLancamentosNoMes() == null
                        ? "—"
                        : String.valueOf(resumo.getLancamentosNoMes())
        ));
        painelResumo.add(criarCartao("Receitas", formatarMoeda(resumo.getReceitas())));
        painelResumo.add(criarCartao("Despesas", formatarMoeda(resumo.getDespesas())));
        painelResumo.add(criarCartao("Resultado", formatarMoeda(resumo.getResultado())));
        painelPrincipal.add(painelResumo, BorderLayout.NORTH);

        DefaultTableModel modeloTabela = new DefaultTableModel(
                new Object[] { "Data", "Histórico", "Débito", "Crédito", "Valor" },
                0
        ) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        for (Object[] movimentacao : resumo.getMovimentacoesRecentes()) {
            modeloTabela.addRow(movimentacao);
        }

        JTable tabela = new JTable(modeloTabela);
        JScrollPane lista = new JScrollPane(tabela);
        lista.setBorder(BorderFactory.createTitledBorder("Movimentações recentes"));
        painelPrincipal.add(lista, BorderLayout.CENTER);

        add(painelPrincipal, BorderLayout.CENTER);
    }

    private JPanel criarCartao(String titulo, String valor) {
        JPanel cartao = new JPanel(new BorderLayout());
        cartao.setBorder(BorderFactory.createTitledBorder(titulo));

        JLabel textoValor = new JLabel(valor, SwingConstants.CENTER);
        textoValor.setFont(textoValor.getFont().deriveFont(Font.BOLD, 16f));
        cartao.add(textoValor, BorderLayout.CENTER);

        return cartao;
    }

    private String formatarMoeda(BigDecimal valor) {
        if (valor == null) {
            return "—";
        }
        NumberFormat formato = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("pt-BR"));
        return formato.format(valor);
    }

}
