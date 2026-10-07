package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.resumoDashboard;

public class DashboardView extends JFrame {

    private static final long serialVersionUID = 1L;

    private final resumoDashboard resumo;

    public DashboardView() {
        setTitle("Dashboard Contábil - Visão Geral");
        setSize(850, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        resumo = new resumoDashboard();
        criarComponentes();
    }

    private void criarComponentes() {
        JLabel titulo = new JLabel("Dashboard Contábil - Visão Geral", JLabel.CENTER);
        add(titulo, BorderLayout.NORTH);

        JPanel painelResumo = new JPanel(new GridLayout(2, 2, 10, 10));
        painelResumo.add(criarCartao("Lançamentos no mês", resumo.getLancamentosNoMes()));
        painelResumo.add(criarCartao("Receitas", resumo.getReceitas()));
        painelResumo.add(criarCartao("Despesas", resumo.getDespesas()));
        painelResumo.add(criarCartao("Resultado", resumo.getResultado()));

        add(painelResumo, BorderLayout.CENTER);
    }

    private JPanel criarCartao(String titulo, String valor) {
        JPanel cartao = new JPanel(new BorderLayout());
        cartao.setBorder(BorderFactory.createTitledBorder(titulo));

        String texto = valor == null ? "" : valor;
        cartao.add(new JLabel(texto, JLabel.CENTER), BorderLayout.CENTER);

        return cartao;
    }
}
