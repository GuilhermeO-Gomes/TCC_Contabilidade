
package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaCadastroServico extends JPanel {

    private static final long serialVersionUID = 1L;



    private final JTextField codigo = new JTextField(10);

    private final JTextField descricao = new JTextField(25);

    private final JTextField codigoMunicipal = new JTextField(15);

    private final JTextField nbs = new JTextField(15);

    private final JTextField aliquotaISS = new JTextField(10);

    private final JTextField valorServico = new JTextField(10);


 

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
                "Código Municipal",
                "NBS",
                "Alíquota ISS (%)",
                "Valor do Serviço"
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


   

    public TelaCadastroServico() {

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

        JPanel cadastro = new JPanel(
            new GridBagLayout()
        );

        cadastro.setBorder(
            BorderFactory.createTitledBorder(
                "Dados do Serviço"
            )
        );

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;


     

        gbc.gridx = 0;
        gbc.gridy = 0;
        cadastro.add(new JLabel("Código"), gbc);

        gbc.gridx = 1;
        cadastro.add(codigo, gbc);

        gbc.gridx = 2;
        cadastro.add(new JLabel("Descrição *"), gbc);

        gbc.gridx = 3;
        gbc.weightx = 1.0;
        cadastro.add(descricao, gbc);


      

        gbc.weightx = 0;

        gbc.gridx = 0;
        gbc.gridy = 1;
        cadastro.add(new JLabel("Código Municipal"), gbc);

        gbc.gridx = 1;
        cadastro.add(codigoMunicipal, gbc);

        gbc.gridx = 2;
        cadastro.add(new JLabel("NBS"), gbc);

        gbc.gridx = 3;
        cadastro.add(nbs, gbc);



        gbc.gridx = 0;
        gbc.gridy = 2;
        cadastro.add(new JLabel("Alíquota ISS (%)"), gbc);

        gbc.gridx = 1;
        cadastro.add(aliquotaISS, gbc);

        gbc.gridx = 2;
        cadastro.add(new JLabel("Valor do Serviço (R$)"), gbc);

        gbc.gridx = 3;
        cadastro.add(valorServico, gbc);


 

        JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 5)
        );

        botoes.add(novo);
        botoes.add(salvar);
        botoes.add(editar);
        botoes.add(excluir);
        botoes.add(limpar);


        topo.add(cadastro, BorderLayout.CENTER);
        topo.add(botoes, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);


  

        JPanel centro = new JPanel(
            new BorderLayout(8, 8)
        );

        centro.setBorder(
            BorderFactory.createTitledBorder(
                "Serviços Cadastrados"
            )
        );


 

        JPanel painelPesquisa = new JPanel(
            new FlowLayout(FlowLayout.LEFT, 8, 5)
        );

        painelPesquisa.add(
            new JLabel("Pesquisar serviço:")
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
            String codigoMunicipal,
            String nbs,
            String aliquotaISS,
            String valorServico) {

        modelo.addRow(
            new Object[] {
                codigo,
                descricao,
                codigoMunicipal,
                nbs,
                aliquotaISS,
                valorServico
            }
        );
    }


    public void limparTabela() {

        modelo.setRowCount(0);
    }


   

    private void carregarDadosTeste() {

        adicionarLinha(
            "001",
            "Consultoria Empresarial",
            "0101",
            "1.0101.10.00",
            "5,00",
            "1.500,00"
        );

        adicionarLinha(
            "002",
            "Desenvolvimento de Software",
            "0102",
            "1.0102.20.00",
            "3,00",
            "2.500,00"
        );

        adicionarLinha(
            "003",
            "Manutenção de Equipamentos",
            "0103",
            "1.0103.30.00",
            "2,00",
            "350,00"
        );
    }




    public JTextField getCodigo() {
        return codigo;
    }

    public JTextField getDescricao() {
        return descricao;
    }

    public JTextField getCodigoMunicipal() {
        return codigoMunicipal;
    }

    public JTextField getNbs() {
        return nbs;
    }

    public JTextField getAliquotaISS() {
        return aliquotaISS;
    }

    public JTextField getValorServico() {
        return valorServico;
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
