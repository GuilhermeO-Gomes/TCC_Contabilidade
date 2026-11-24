
package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaNaturezaOperacao extends JPanel {

    private static final long serialVersionUID = 1L;


  

    private final JTextField codigo = new JTextField(10);

    private final JTextField descricao = new JTextField(25);

    private final JComboBox<String> tipoOperacao =
        new JComboBox<>(
            new String[] {
                "Entrada",
                "Saída"
            }
        );

    private final JComboBox<String> finalidade =
        new JComboBox<>(
            new String[] {
                "Normal",
                "Devolução",
                "Complementar",
                "Ajuste"
            }
        );




    private final JTextField cfopEstadual = new JTextField(10);

    private final JTextField cfopInterestadual = new JTextField(10);

    private final JTextField cfopExterior = new JTextField(10);

    private final JComboBox<String> modeloDocumento =
        new JComboBox<>(
            new String[] {
                "NF-e",
                "NFC-e",
                "Ambos"
            }
        );


 

    private final JCheckBox movimentaEstoque =
        new JCheckBox("Movimenta estoque");

    private final JCheckBox geraFinanceiro =
        new JCheckBox("Gera financeiro");



    private final JComboBox<String> status =
        new JComboBox<>(
            new String[] {
                "Ativo",
                "Inativo"
            }
        );




    private final JButton novo = new JButton("Novo");

    private final JButton salvar = new JButton("Salvar");

    private final JButton editar = new JButton("Editar");

    private final JButton excluir = new JButton("Excluir");

    private final JButton limpar = new JButton("Limpar");


 

    private final JTextField pesquisa = new JTextField(20);

    private final JButton pesquisar = new JButton("Pesquisar");



    private final DefaultTableModel modelo =
        new DefaultTableModel(
            new Object[] {
                "Código",
                "Descrição",
                "Tipo",
                "Finalidade",
                "CFOP Estadual",
                "CFOP Interestadual",
                "Movimenta Estoque",
                "Status"
            },
            0
        ) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(
                    int linha,
                    int coluna) {

                return false;
            }
        };

    private final JTable tabela = new JTable(modelo);



    public TelaNaturezaOperacao() {

        setLayout(new BorderLayout(8, 8));

        setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        montar();

        carregarDadosTeste();
    }


  

    private void montar() {

        JPanel topo = new JPanel(
            new BorderLayout(8, 8)
        );


     
 

        JPanel dados = new JPanel(
            new GridBagLayout()
        );

        dados.setBorder(
            BorderFactory.createTitledBorder(
                "Dados da Natureza de Operação"
            )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


     

        gbc.gridx = 0;
        gbc.gridy = 0;
        dados.add(new JLabel("Código"), gbc);

        gbc.gridx = 1;
        dados.add(codigo, gbc);

        gbc.gridx = 2;
        dados.add(new JLabel("Descrição *"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        dados.add(descricao, gbc);


    

        gbc.weightx = 0;

        gbc.gridx = 0;
        gbc.gridy = 1;
        dados.add(new JLabel("Tipo de Operação *"), gbc);

        gbc.gridx = 1;
        dados.add(tipoOperacao, gbc);

        gbc.gridx = 2;
        dados.add(new JLabel("Finalidade *"), gbc);

        gbc.gridx = 3;
        dados.add(finalidade, gbc);


    

        JPanel fiscal = new JPanel(
            new GridBagLayout()
        );

        fiscal.setBorder(
            BorderFactory.createTitledBorder(
                "Configuração Fiscal"
            )
        );


        gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


      

        gbc.gridx = 0;
        gbc.gridy = 0;
        fiscal.add(new JLabel("CFOP Estadual *"), gbc);

        gbc.gridx = 1;
        fiscal.add(cfopEstadual, gbc);

        gbc.gridx = 2;
        fiscal.add(new JLabel("CFOP Interestadual"), gbc);

        gbc.gridx = 3;
        fiscal.add(cfopInterestadual, gbc);


       

        gbc.gridx = 0;
        gbc.gridy = 1;
        fiscal.add(new JLabel("CFOP Exterior"), gbc);

        gbc.gridx = 1;
        fiscal.add(cfopExterior, gbc);

        gbc.gridx = 2;
        fiscal.add(new JLabel("Modelo do Documento"), gbc);

        gbc.gridx = 3;
        fiscal.add(modeloDocumento, gbc);


      

        JPanel comportamento = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 15, 5)
        );

        comportamento.setBorder(
            BorderFactory.createTitledBorder(
                "Comportamento da Operação"
            )
        );

        comportamento.add(movimentaEstoque);

        comportamento.add(geraFinanceiro);

        comportamento.add(new JLabel("Status:"));

        comportamento.add(status);


  

        JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 5)
        );

        botoes.add(novo);
        botoes.add(salvar);
        botoes.add(editar);
        botoes.add(excluir);
        botoes.add(limpar);


    

        JPanel formulario = new JPanel(
            new GridLayout(3, 1, 0, 8)
        );

        formulario.add(dados);
        formulario.add(fiscal);
        formulario.add(comportamento);

        topo.add(formulario, BorderLayout.CENTER);

        topo.add(botoes, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);


     

        JPanel centro = new JPanel(
            new BorderLayout(8, 8)
        );

        centro.setBorder(
            BorderFactory.createTitledBorder(
                "Naturezas de Operação Cadastradas"
            )
        );


      
        JPanel painelPesquisa = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 5)
        );

        painelPesquisa.add(
            new JLabel("Pesquisar natureza:")
        );

        painelPesquisa.add(pesquisa);

        painelPesquisa.add(pesquisar);


      
        tabela.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        tabela.setFillsViewportHeight(true);

        tabela.setAutoCreateRowSorter(true);

        tabela.getTableHeader()
            .setReorderingAllowed(false);


        centro.add(
            painelPesquisa,
            BorderLayout.NORTH
        );

        centro.add(
            new JScrollPane(tabela),
            BorderLayout.CENTER
        );

        add(centro, BorderLayout.CENTER);
    }


 

    public void adicionarLinha(
            String codigo,
            String descricao,
            String tipo,
            String finalidade,
            String cfopEstadual,
            String cfopInterestadual,
            String movimentaEstoque,
            String status) {

        modelo.addRow(
            new Object[] {
                codigo,
                descricao,
                tipo,
                finalidade,
                cfopEstadual,
                cfopInterestadual,
                movimentaEstoque,
                status
            }
        );
    }


    public void limparTabela() {

        modelo.setRowCount(0);
    }


 

    private void carregarDadosTeste() {

        adicionarLinha(
            "001",
            "Compra de Mercadorias",
            "Entrada",
            "Normal",
            "1102",
            "2102",
            "Sim",
            "Ativo"
        );

        adicionarLinha(
            "002",
            "Venda de Mercadorias",
            "Saída",
            "Normal",
            "5102",
            "6102",
            "Sim",
            "Ativo"
        );

        adicionarLinha(
            "003",
            "Devolução de Compra",
            "Saída",
            "Devolução",
            "5202",
            "6202",
            "Sim",
            "Ativo"
        );

        adicionarLinha(
            "004",
            "Remessa para Conserto",
            "Saída",
            "Normal",
            "5915",
            "6915",
            "Não",
            "Ativo"
        );
    }


  

    public JTextField getCodigo() {
        return codigo;
    }

    public JTextField getDescricao() {
        return descricao;
    }

    public JComboBox<String> getTipoOperacao() {
        return tipoOperacao;
    }

    public JComboBox<String> getFinalidade() {
        return finalidade;
    }

    public JTextField getCfopEstadual() {
        return cfopEstadual;
    }

    public JTextField getCfopInterestadual() {
        return cfopInterestadual;
    }

    public JTextField getCfopExterior() {
        return cfopExterior;
    }

    public JComboBox<String> getModeloDocumento() {
        return modeloDocumento;
    }

    public JCheckBox getMovimentaEstoque() {
        return movimentaEstoque;
    }

    public JCheckBox getGeraFinanceiro() {
        return geraFinanceiro;
    }

    public JComboBox<String> getStatus() {
        return status;
    }

    public JButton getNovo() {
        return novo;
    }

    public JButton getSalvar() {
        return salvar;
    }

    public JButton getEditar() {
        return editar;
    }

    public JButton getExcluir() {
        return excluir;
    }

    public JButton getLimpar() {
        return limpar;
    }

    public JTextField getPesquisa() {
        return pesquisa;
    }

    public JButton getPesquisar() {
        return pesquisar;
    }

    public JTable getTabela() {
        return tabela;
    }

}
