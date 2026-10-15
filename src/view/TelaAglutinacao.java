package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.CodigoAglutinacao;

public class TelaAglutinacao extends JPanel {

    private static final long serialVersionUID = 1L;


    private final JTextField pesquisa =
        new JTextField(22);


    private final DefaultTableModel modelo =
        new DefaultTableModel(
            new Object[] {
                "ID",
                "Valor",
                "Descrição",
                "Observação",
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
        new JButton("Novo");

    private final JButton editar =
        new JButton("Editar");

    private final JButton buscar =
        new JButton("Buscar");


    public TelaAglutinacao() {

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

      

        JPanel botoes =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );

        botoes.add(novo);
        botoes.add(editar);


        novo.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(
                        ActionEvent e) {

                    Window pai =
                        SwingUtilities.getWindowAncestor(
                            TelaAglutinacao.this
                        );


                    diaAglutinacao dialogo =
                        new diaAglutinacao(
                            (Frame) pai,
                            true
                        );


                    dialogo.setVisible(true);
                }
            }
        );



        editar.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(
                        ActionEvent e) {

                    int linhaSelecionada =
                        tabela.getSelectedRow();


                    if (linhaSelecionada == -1) {

                        JOptionPane.showMessageDialog(
                            TelaAglutinacao.this,
                            "Selecione um código de aglutinação para editar.",
                            "Aviso",
                            JOptionPane.WARNING_MESSAGE
                        );

                        return;
                    }


                    int linhaModelo =
                        tabela.convertRowIndexToModel(
                            linhaSelecionada
                        );


                    long id =
                        Long.parseLong(
                            modelo
                                .getValueAt(
                                    linhaModelo,
                                    0
                                )
                                .toString()
                        );


                    String valor =
                        modelo
                            .getValueAt(
                                linhaModelo,
                                1
                            )
                            .toString();


                    String descricao =
                        modelo
                            .getValueAt(
                                linhaModelo,
                                2
                            )
                            .toString();


                    Object observacaoObj =
                        modelo.getValueAt(
                            linhaModelo,
                            3
                        );


                    String observacao =
                        observacaoObj == null
                            ? ""
                            : observacaoObj.toString();


                    boolean ativo =
                        Boolean.parseBoolean(
                            modelo
                                .getValueAt(
                                    linhaModelo,
                                    4
                                )
                                .toString()
                        );


                    Window pai =
                        SwingUtilities.getWindowAncestor(
                            TelaAglutinacao.this
                        );


                    diaAglutinacao dialogo =
                        new diaAglutinacao(
                            (Frame) pai,
                            true
                        );


                    dialogo.setId(id);

                    dialogo.setValor(valor);

                    dialogo.setDescricao(
                        descricao
                    );

                    dialogo.setObservacao(
                        observacao
                    );

                    dialogo.setAtivo(
                        ativo
                    );


                    dialogo.setVisible(true);
                }
            }
        );


     

        JPanel superior =
            new JPanel(
                new BorderLayout()
            );

        superior.add(
            botoes,
            BorderLayout.WEST
        );

        add(
            superior,
            BorderLayout.NORTH
        );



        JPanel pesquisaPanel =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );


        pesquisaPanel.add(
            new JLabel("Pesquisar:")
        );

        pesquisaPanel.add(
            pesquisa
        );

        pesquisaPanel.add(
            buscar
        );



        JPanel painelTabela =
            new JPanel(
                new BorderLayout()
            );


        painelTabela.setBorder(
            BorderFactory.createTitledBorder(
                "Códigos de Aglutinação"
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
            pesquisaPanel,
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




    public void limparTabela() {

        modelo.setRowCount(0);
    }


    public void adicionarLinha(
            CodigoAglutinacao codigo) {

        modelo.addRow(
            new Object[] {
                codigo.getId(),
                codigo.getValor(),
                codigo.getDescricao(),
                codigo.getObservacao(),
                codigo.isAtivo()
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


 

    public String getPesquisa() {
        return pesquisa.getText();
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


    public JButton getBuscar() {
        return buscar;
    }
}