package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.BalancoPatrimonial;

public class TelaBalancoPatrimonial extends JPanel {

    private static final long serialVersionUID = 1L;


    private final JTextField pesquisa =
        new JTextField(22);


    private final DefaultTableModel modelo =
        new DefaultTableModel(
            new Object[] {
                "ID",
                "Descrição",
                "Favorito"
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


    private final JButton novo =
        new JButton("Novo");

    private final JButton editar =
        new JButton("Editar");

    private final JButton configurarItens =
        new JButton("Configurar Itens");

    private final JButton buscar =
        new JButton("Buscar");


    public TelaBalancoPatrimonial() {

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

        JPanel topo =
            new JPanel(
                new BorderLayout(8, 8)
            );


        /*
         * BOTÕES
         */

        JPanel botoes =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );

        botoes.add(novo);

        botoes.add(editar);

        botoes.add(
            Box.createHorizontalStrut(15)
        );

        botoes.add(
            configurarItens
        );


        /*
         * PESQUISA
         */

        JPanel busca =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );

        busca.add(
            new JLabel("Pesquisar:")
        );

        busca.add(
            pesquisa
        );

        busca.add(
            buscar
        );


        topo.add(
            botoes,
            BorderLayout.NORTH
        );

        topo.add(
            busca,
            BorderLayout.SOUTH
        );


        add(
            topo,
            BorderLayout.NORTH
        );


        /*
         * TABELA
         */

        JPanel centro =
            new JPanel(
                new BorderLayout()
            );

        centro.setBorder(
            BorderFactory.createTitledBorder(
                "Balanços Patrimoniais cadastrados"
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


        centro.add(
            new JScrollPane(tabela),
            BorderLayout.CENTER
        );


        add(
            centro,
            BorderLayout.CENTER
        );
    }


    /*
     * TABELA
     */

    public void limparTabela() {

        modelo.setRowCount(0);
    }


    public void adicionarLinha(
            BalancoPatrimonial balanco) {

        modelo.addRow(
            new Object[] {
                balanco.getId(),
                balanco.getDescricao(),
                balanco.isFavorito()
            }
        );
    }


    /*
     * SELEÇÃO
     */

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


    /*
     * GETTERS
     */

    public JTable getTabela() {
        return tabela;
    }


    public String getPesquisa() {
        return pesquisa.getText();
    }


    public JButton getNovo() {
        return novo;
    }


    public JButton getEditar() {
        return editar;
    }


    public JButton getConfigurarItens() {
        return configurarItens;
    }


    public JButton getBuscar() {
        return buscar;
    }
}