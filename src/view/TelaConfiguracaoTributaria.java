
package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaConfiguracaoTributaria extends JPanel {

    private static final long serialVersionUID = 1L;



    private final JTextField codigo = new JTextField(10);

    private final JTextField descricao = new JTextField(25);

    private final JComboBox<String> tipoImposto =
        new JComboBox<>(
            new String[] {
                "ICMS",
                "ISS",
                "IPI",
                "PIS",
                "COFINS"
            }
        );

    private final JComboBox<String> regimeTributario =
        new JComboBox<>(
            new String[] {
                "Simples Nacional",
                "Lucro Presumido",
                "Lucro Real"
            }
        );

    private final JComboBox<String> aplicacao =
        new JComboBox<>(
            new String[] {
                "Produtos",
                "Serviços",
                "Ambos"
            }
        );




    private final JTextField situacaoTributaria =
        new JTextField(12);

    private final JTextField aliquota =
        new JTextField(10);

    private final JTextField reducaoBase =
        new JTextField(10);




    private final JTextField dataInicial =
        new JTextField(10);

    private final JTextField dataFinal =
        new JTextField(10);

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



    private final JTextField pesquisa =
        new JTextField(20);

    private final JButton pesquisar =
        new JButton("Pesquisar");




    private final DefaultTableModel modelo =
        new DefaultTableModel(
            new Object[] {
                "Código",
                "Descrição",
                "Imposto",
                "Regime Tributário",
                "Aplicação",
                "Situação Tributária",
                "Alíquota (%)",
                "Vigência Inicial",
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


 

    public TelaConfiguracaoTributaria() {

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



        JPanel dadosGerais = new JPanel(
            new GridBagLayout()
        );

        dadosGerais.setBorder(
            BorderFactory.createTitledBorder(
                "Dados Gerais"
            )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;




        gbc.gridx = 0;
        gbc.gridy = 0;
        dadosGerais.add(new JLabel("Código"), gbc);

        gbc.gridx = 1;
        dadosGerais.add(codigo, gbc);

        gbc.gridx = 2;
        dadosGerais.add(new JLabel("Descrição *"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        dadosGerais.add(descricao, gbc);




        gbc.weightx = 0;

        gbc.gridx = 0;
        gbc.gridy = 1;
        dadosGerais.add(new JLabel("Tipo de Imposto *"), gbc);

        gbc.gridx = 1;
        dadosGerais.add(tipoImposto, gbc);

        gbc.gridx = 2;
        dadosGerais.add(new JLabel("Regime Tributário *"), gbc);

        gbc.gridx = 3;
        dadosGerais.add(regimeTributario, gbc);


 

        gbc.gridx = 0;
        gbc.gridy = 2;
        dadosGerais.add(new JLabel("Aplicação *"), gbc);

        gbc.gridx = 1;
        dadosGerais.add(aplicacao, gbc);


  

        JPanel parametros = new JPanel(
            new GridBagLayout()
        );

        parametros.setBorder(
            BorderFactory.createTitledBorder(
                "Parâmetros Tributários"
            )
        );

        gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;




        gbc.gridx = 0;
        gbc.gridy = 0;
        parametros.add(
            new JLabel("Situação Tributária (CST/CSOSN)"),
            gbc
        );

        gbc.gridx = 1;
        parametros.add(situacaoTributaria, gbc);

        gbc.gridx = 2;
        parametros.add(new JLabel("Alíquota (%)"), gbc);

        gbc.gridx = 3;
        parametros.add(aliquota, gbc);


     
        gbc.gridx = 0;
        gbc.gridy = 1;
        parametros.add(
            new JLabel("Redução da Base de Cálculo (%)"),
            gbc
        );

        gbc.gridx = 1;
        parametros.add(reducaoBase, gbc);


   

        JPanel vigencia = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 10, 5)
        );

        vigencia.setBorder(
            BorderFactory.createTitledBorder(
                "Vigência e Status"
            )
        );

        vigencia.add(new JLabel("Data Inicial *"));

        vigencia.add(dataInicial);

        vigencia.add(new JLabel("Data Final"));

        vigencia.add(dataFinal);

        vigencia.add(new JLabel("Status"));

        vigencia.add(status);


  

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

        formulario.add(dadosGerais);
        formulario.add(parametros);
        formulario.add(vigencia);

        topo.add(formulario, BorderLayout.CENTER);

        topo.add(botoes, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);




        JPanel centro = new JPanel(
            new BorderLayout(8, 8)
        );

        centro.setBorder(
            BorderFactory.createTitledBorder(
                "Configurações Tributárias Cadastradas"
            )
        );



        JPanel painelPesquisa = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 5)
        );

        painelPesquisa.add(
            new JLabel("Pesquisar configuração:")
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
            String imposto,
            String regimeTributario,
            String aplicacao,
            String situacaoTributaria,
            String aliquota,
            String dataInicial,
            String status) {

        modelo.addRow(
            new Object[] {
                codigo,
                descricao,
                imposto,
                regimeTributario,
                aplicacao,
                situacaoTributaria,
                aliquota,
                dataInicial,
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
            "ICMS - Venda de Mercadorias",
            "ICMS",
            "Simples Nacional",
            "Produtos",
            "CSOSN 102",
            "0,00",
            "01/01/2026",
            "Ativo"
        );

        adicionarLinha(
            "002",
            "ISS - Prestação de Serviços",
            "ISS",
            "Simples Nacional",
            "Serviços",
            "Tributado",
            "5,00",
            "01/01/2026",
            "Ativo"
        );

        adicionarLinha(
            "003",
            "PIS - Regime Cumulativo",
            "PIS",
            "Lucro Presumido",
            "Produtos",
            "CST 01",
            "0,65",
            "01/01/2026",
            "Ativo"
        );

        adicionarLinha(
            "004",
            "COFINS - Regime Cumulativo",
            "COFINS",
            "Lucro Presumido",
            "Produtos",
            "CST 01",
            "3,00",
            "01/01/2026",
            "Ativo"
        );

        adicionarLinha(
            "005",
            "IPI - Produtos Industrializados",
            "IPI",
            "Lucro Real",
            "Produtos",
            "CST 50",
            "5,00",
            "01/01/2026",
            "Inativo"
        );
    }


  

    public JTextField getCodigo() {
        return codigo;
    }

    public JTextField getDescricao() {
        return descricao;
    }

    public JComboBox<String> getTipoImposto() {
        return tipoImposto;
    }

    public JComboBox<String> getRegimeTributario() {
        return regimeTributario;
    }

    public JComboBox<String> getAplicacao() {
        return aplicacao;
    }

    public JTextField getSituacaoTributaria() {
        return situacaoTributaria;
    }

    public JTextField getAliquota() {
        return aliquota;
    }

    public JTextField getReducaoBase() {
        return reducaoBase;
    }

    public JTextField getDataInicial() {
        return dataInicial;
    }

    public JTextField getDataFinal() {
        return dataFinal;
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
