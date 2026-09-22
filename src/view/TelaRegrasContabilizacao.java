package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

public class TelaRegrasContabilizacao extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField txtCodigo = new JTextField(8);
    private final JTextField txtDescricaoEvento = new JTextField(30);
    private final JTextField txtPesquisa = new JTextField(22);

    private final JComboBox<String> cmbContaDebito =
            new JComboBox<>(new String[] {"Selecione uma conta"});
    private final JComboBox<String> cmbContaCredito =
            new JComboBox<>(new String[] {"Selecione uma conta"});
    private final JComboBox<String> cmbFiltroSituacao =
            new JComboBox<>(new String[] {"Todas", "Ativas", "Inativas"});

    private final JCheckBox checkAtiva =
            new JCheckBox("Regra ativa", true);

    private final JButton btnNovo = new JButton("Novo");
    private final JButton btnSalvar = new JButton("Salvar");
    private final JButton btnAtualizar = new JButton("Atualizar");
    private final JButton btnInativarAtivar =
            new JButton("Inativar/Ativar");
    private final JButton btnLimpar = new JButton("Limpar");
    private final JButton btnPesquisar = new JButton("Pesquisar");
    private final JButton btnMostrarTodos =
            new JButton("Mostrar todos");

    private final DefaultTableModel modeloTabela =
            new DefaultTableModel(
                    new Object[] {
                            "Código",
                            "Evento",
                            "Conta de Débito",
                            "Conta de Crédito",
                            "Situação"
                    },
                    0
            ) {
                private static final long serialVersionUID = 1L;

                public boolean isCellEditable(int linha, int coluna) {
                    return false;
                }
            };

    private final JTable tabelaRegras =
            new JTable(modeloTabela);

    public TelaRegrasContabilizacao() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montarTela();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame janela = new JFrame(
                        "Regras de Contabilização"
                );

                janela.setSize(900, 650);
                janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                janela.setLocationRelativeTo(null);
                janela.add(new TelaRegrasContabilizacao());
                janela.setVisible(true);
            }
        });
    }

    private void montarTela() {
        JPanel painelCadastro = new JPanel(new GridBagLayout());
        painelCadastro.setBorder(
                BorderFactory.createTitledBorder(
                        "Cadastro da Regra de Contabilização"
                )
        );

        txtCodigo.setEditable(false);

        GridBagConstraints organizador = new GridBagConstraints();
        organizador.insets = new Insets(4, 4, 4, 4);
        organizador.anchor = GridBagConstraints.WEST;

        adicionarComponente(
                painelCadastro, organizador, 0,
                "Código:", txtCodigo
        );
        adicionarComponente(
                painelCadastro, organizador, 1,
                "Descrição do evento:", txtDescricaoEvento
        );
        adicionarComponente(
                painelCadastro, organizador, 2,
                "Conta de débito:", cmbContaDebito
        );
        adicionarComponente(
                painelCadastro, organizador, 3,
                "Conta de crédito:", cmbContaCredito
        );

        organizador.gridx = 1;
        organizador.gridy = 4;
        organizador.weightx = 1;
        organizador.fill = GridBagConstraints.HORIZONTAL;
        painelCadastro.add(checkAtiva, organizador);

        JPanel painelBotoes = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 5, 5)
        );
        painelBotoes.add(btnNovo);
        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnInativarAtivar);
        painelBotoes.add(btnLimpar);

        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(painelCadastro, BorderLayout.CENTER);
        painelSuperior.add(painelBotoes, BorderLayout.SOUTH);
        add(painelSuperior, BorderLayout.NORTH);

        JPanel painelPesquisa = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 5, 5)
        );
        painelPesquisa.add(new JLabel("Evento:"));
        painelPesquisa.add(txtPesquisa);
        painelPesquisa.add(new JLabel("Situação:"));
        painelPesquisa.add(cmbFiltroSituacao);
        painelPesquisa.add(btnPesquisar);
        painelPesquisa.add(btnMostrarTodos);

        tabelaRegras.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        JScrollPane scrollPane = new JScrollPane(tabelaRegras);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Regras Cadastradas")
        );

        JPanel painelLista = new JPanel(new BorderLayout(5, 5));
        painelLista.add(painelPesquisa, BorderLayout.NORTH);
        painelLista.add(scrollPane, BorderLayout.CENTER);
        add(painelLista, BorderLayout.CENTER);
    }

    private void adicionarComponente(
            JPanel painel,
            GridBagConstraints organizador,
            int linha,
            String texto,
            Component componente
    ) {
        organizador.gridx = 0;
        organizador.gridy = linha;
        organizador.weightx = 0;
        organizador.fill = GridBagConstraints.NONE;
        painel.add(new JLabel(texto), organizador);

        organizador.gridx = 1;
        organizador.weightx = 1;
        organizador.fill = GridBagConstraints.HORIZONTAL;
        painel.add(componente, organizador);
    }

    public void limparCampos() {
        txtCodigo.setText("");
        txtDescricaoEvento.setText("");
        cmbContaDebito.setSelectedIndex(0);
        cmbContaCredito.setSelectedIndex(0);
        checkAtiva.setSelected(true);
        tabelaRegras.clearSelection();
    }

    public JTextField getTxtCodigo() {
        return txtCodigo;
    }

    public JTextField getTxtDescricaoEvento() {
        return txtDescricaoEvento;
    }

    public JTextField getTxtPesquisa() {
        return txtPesquisa;
    }

    public JComboBox<String> getCmbContaDebito() {
        return cmbContaDebito;
    }

    public JComboBox<String> getCmbContaCredito() {
        return cmbContaCredito;
    }

    public JComboBox<String> getCmbFiltroSituacao() {
        return cmbFiltroSituacao;
    }

    public JCheckBox getCheckAtiva() {
        return checkAtiva;
    }

    public JButton getBtnNovo() {
        return btnNovo;
    }

    public JButton getBtnSalvar() {
        return btnSalvar;
    }

    public JButton getBtnAtualizar() {
        return btnAtualizar;
    }

    public JButton getBtnInativarAtivar() {
        return btnInativarAtivar;
    }

    public JButton getBtnLimpar() {
        return btnLimpar;
    }

    public JButton getBtnPesquisar() {
        return btnPesquisar;
    }

    public JButton getBtnMostrarTodos() {
        return btnMostrarTodos;
    }

    public JTable getTabelaRegras() {
        return tabelaRegras;
    }
}
