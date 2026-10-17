package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.HistoricoPadrao;

public class TelaHistoricoPadrao extends JPanel {

    private static final long serialVersionUID = 1L;


    private final JTextField id =
        new JTextField(5);

    private final JTextField descricao =
        new JTextField(28);

    private final JTextField pesquisa =
        new JTextField(22);


    private final JCheckBox ativo =
        new JCheckBox(
            "Histórico Padrão ativo",
            true
        );


    private final JButton novo =
        new JButton("Novo");

    private final JButton salvar =
        new JButton("Salvar");

    private final JButton inativar =
        new JButton("Inativar");

    private final JButton limpar =
        new JButton("Limpar");

    private final JButton buscar =
        new JButton("Buscar");


    private final DefaultTableModel modelo =
        new DefaultTableModel(
            new Object[] {
                "ID",
                "Descrição",
                "Ativo"
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


    private final JTable tabela =
        new JTable(modelo);


    public TelaHistoricoPadrao() {

        setLayout(
            new BorderLayout(8, 8)
        );

        setBorder(
            BorderFactory.createEmptyBorder(
                10,
                10,
                10,
                10
            )
        );

        montar();
    }


    private void montar() {

       

        JPanel formulario =
            new JPanel(
                new GridBagLayout()
            );

        formulario.setBorder(
            BorderFactory.createTitledBorder(
                "Históricos Padrões no Sistema"
            )
        );


        GridBagConstraints g =
            new GridBagConstraints();

        g.insets =
            new Insets(4, 4, 4, 4);


        componente(
            formulario,
            g,
            0,
            "Código",
            id
        );


        componente(
            formulario,
            g,
            1,
            "Descrição",
            descricao
        );


        g.gridx = 1;
        g.gridy = 2;

        formulario.add(
            ativo,
            g
        );


      

        id.setEditable(false);



        JPanel botoes =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );

        botoes.add(novo);
        botoes.add(salvar);
        botoes.add(inativar);
        botoes.add(limpar);


        JPanel superior =
            new JPanel(
                new BorderLayout()
            );

        superior.add(
            formulario,
            BorderLayout.CENTER
        );

        superior.add(
            botoes,
            BorderLayout.SOUTH
        );

        add(
            superior,
            BorderLayout.NORTH
        );



        JPanel painelPesquisa =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );

        painelPesquisa.add(
            new JLabel("Pesquisar:")
        );

        painelPesquisa.add(
            pesquisa
        );

        painelPesquisa.add(
            buscar
        );


      
        JPanel painelTabela =
            new JPanel(
                new BorderLayout()
            );

        painelTabela.setBorder(
            BorderFactory.createTitledBorder(
                "Históricos Padrões"
            )
        );


        tabela.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        tabela.setFillsViewportHeight(true);

        tabela.setAutoCreateRowSorter(true);

        tabela
            .getTableHeader()
            .setReorderingAllowed(false);


        painelTabela.add(
            painelPesquisa,
            BorderLayout.NORTH
        );

        painelTabela.add(
            new JScrollPane(tabela),
            BorderLayout.CENTER
        );


        add(
            painelTabela,
            BorderLayout.CENTER
        );
    }


    private void componente(
            JPanel painel,
            GridBagConstraints g,
            int y,
            String texto,
            Component componente) {

        g.gridx = 0;
        g.gridy = y;

        g.weightx = 0;

        g.fill =
            GridBagConstraints.NONE;

        painel.add(
            new JLabel(texto),
            g
        );


        g.gridx = 1;

        g.weightx = 1;

        g.fill =
            GridBagConstraints.HORIZONTAL;

        painel.add(
            componente,
            g
        );
    }



    public void limpar() {

        id.setText("");

        descricao.setText("");

        ativo.setSelected(true);
    }


   

    public void limparTabela() {

        modelo.setRowCount(0);
    }


    public void adicionarLinha(
            HistoricoPadrao historico) {

        modelo.addRow(
            new Object[] {
                historico.getId(),
                historico.getDescricao(),
                historico.isAtivo()
            }
        );
    }


    public long getIdSelecionado() {

        int linha =
            tabela.getSelectedRow();


        if (linha == -1) {

            return 0;
        }


        int linhaModelo =
            tabela.convertRowIndexToModel(
                linha
            );


        return Long.parseLong(
            modelo
                .getValueAt(
                    linhaModelo,
                    0
                )
                .toString()
        );
    }



    public long getId() {

        try {

            return Long.parseLong(
                id.getText()
            );

        } catch (NumberFormatException e) {

            return 0;
        }
    }


    public String getDescricao() {
        return descricao.getText();
    }


    public String getPesquisa() {
        return pesquisa.getText();
    }


    public boolean isAtivo() {
        return ativo.isSelected();
    }


    public JTable getTabela() {
        return tabela;
    }


    public JButton getNovo() {
        return novo;
    }


    public JButton getSalvar() {
        return salvar;
    }


    public JButton getInativar() {
        return inativar;
    }


    public JButton getLimpar() {
        return limpar;
    }


    public JButton getBuscar() {
        return buscar;
    }


 

    public void setId(long valor) {

        id.setText(
            String.valueOf(valor)
        );
    }


    public void setDescricao(
            String valor) {

        descricao.setText(valor);
    }


    public void setAtivo(
            boolean valor) {

        ativo.setSelected(valor);
    }
}