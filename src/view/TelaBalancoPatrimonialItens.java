package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.BalancoPatrimonialItem;

public class TelaBalancoPatrimonialItens extends JPanel {

    private static final long serialVersionUID = 1L;


    private long balancoPatrimonialId;


    private final JLabel tituloBalanco =
        new JLabel("Balanço Patrimonial");


    private final DefaultTableModel modelo =
        new DefaultTableModel(
            new Object[] {
                "ID",
                "Ordem",
                "Código de Aglutinação",
                "Grupo",
                "Item Superior",
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


    private final JButton novo =
        new JButton("Novo Item");

    private final JButton editar =
        new JButton("Editar Item");


    public TelaBalancoPatrimonialItens() {

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


        JPanel cabecalho =
            new JPanel(
                new BorderLayout()
            );


        tituloBalanco.setFont(
            tituloBalanco
                .getFont()
                .deriveFont(
                    Font.BOLD,
                    16f
                )
        );


        cabecalho.add(
            tituloBalanco,
            BorderLayout.WEST
        );



        JPanel botoes =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );


        botoes.add(novo);
        botoes.add(editar);


        JPanel superior =
            new JPanel(
                new BorderLayout()
            );


        superior.add(
            cabecalho,
            BorderLayout.NORTH
        );

        superior.add(
            botoes,
            BorderLayout.SOUTH
        );


        add(
            superior,
            BorderLayout.NORTH
        );


     

        JPanel painelTabela =
            new JPanel(
                new BorderLayout()
            );


        painelTabela.setBorder(
            BorderFactory.createTitledBorder(
                "Itens do Balanço Patrimonial"
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
            new JScrollPane(tabela),
            BorderLayout.CENTER
        );


        add(
            painelTabela,
            BorderLayout.CENTER
        );
    }



    public void setBalanco(
            long balancoPatrimonialId,
            String descricao) {

        this.balancoPatrimonialId =
            balancoPatrimonialId;


        tituloBalanco.setText(
            "Balanço Patrimonial: "
            + descricao
        );
    }


    public long getBalancoPatrimonialId() {

        return balancoPatrimonialId;
    }


    

    public void limparTabela() {

        modelo.setRowCount(0);
    }


    public void adicionarLinha(
            BalancoPatrimonialItem item) {

        modelo.addRow(
            new Object[] {
                item.getId(),
                item.getOrdem(),
                item.getCodigoAglutinacaoId(),
                item.getGrupo(),
                item.getItemSuperiorId(),
                item.isAtivo()
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


    public int getLinhaSelecionadaModelo() {

        int linha =
            tabela.getSelectedRow();


        if (linha == -1) {

            return -1;
        }


        return tabela.convertRowIndexToModel(
            linha
        );
    }


 

    public JTable getTabela() {
        return tabela;
    }


    public JButton getNovo() {
        return novo;
    }


    public JButton getEditar() {
        return editar;
    }
}