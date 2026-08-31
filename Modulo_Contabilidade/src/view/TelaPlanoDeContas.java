package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.PlanoContasController;

public class TelaPlanoDeContas extends JFrame {

    private static final long serialVersionUID = 1L;

    private JButton btnAtualizarTela;
    private JButton btnAdicionarConta;
    private JButton btnCancelarConta;

    private JTable tabelaPlanoDeContas;
    private DefaultTableModel modeloTabela;

    private PlanoContasController controller;

    public TelaPlanoDeContas() {

        setTitle("Plano de Contas");

        setSize(700, 450);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        criarComponentes();

        controller = new PlanoContasController(this);

        configurarEventos();

        controller.carregarTabela();
    }

    private void criarComponentes() {

        JPanel painelBotoes =
                new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnAtualizarTela = new JButton("Atualizar tela");
        btnAdicionarConta = new JButton("Adicionar conta");
        btnCancelarConta = new JButton("Cancelar conta");

        painelBotoes.add(btnAtualizarTela);
        painelBotoes.add(btnAdicionarConta);
        painelBotoes.add(btnCancelarConta);

        add(painelBotoes, BorderLayout.SOUTH);

        JPanel painelPlanoContas =
                new JPanel(new GridLayout(3, 2, 10, 10));

        painelPlanoContas.setBorder(
                BorderFactory.createTitledBorder("Plano de Contas")
        );

        add(painelPlanoContas, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel(
                new Object[] {
                        "Conta",
                        "Descrição",
                        "Reduzida",
                        "Superior",
                        "Nível",
                        "Saldo",
                        "Tipo",
                        "C.C.",
                        "Natureza",
                        "Situação",
                        "Cadastro",
                        "Movimentação"
                },
                0
        ) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaPlanoDeContas = new JTable(modeloTabela);

        JScrollPane scrollPane =
                new JScrollPane(tabelaPlanoDeContas);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Lista de Contas")
        );

        add(scrollPane, BorderLayout.CENTER);
    }

    private void configurarEventos() {

        btnAtualizarTela.addActionListener(e ->
                controller.AtualizarTela()
        );

        btnAdicionarConta.addActionListener(e ->
                controller.AdicionarConta()
        );

        btnCancelarConta.addActionListener(e ->
                controller.CancelarConta()
        );

        tabelaPlanoDeContas.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                controller.preencherTabela();
            }
        });
    }

    public JTable getTabelaPlanoDeContas() {
        return tabelaPlanoDeContas;
    }
}