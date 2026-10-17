package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.ExercicioSocial;

public class TelaExerciciosSociais extends JPanel {

    private static final long serialVersionUID = 1L;

    private final DefaultTableModel modelo =
        new DefaultTableModel(
            new Object[] {
                "ID",
                "Ano Referência",
                "Data de Início",
                "Data Final",
                "Status Contábil",
                "Ativo"
            },
            0
        ) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };


    private final JTable tabela =
        new JTable(modelo);


    
    private final JButton novo =
        new JButton("Novo");

    private final JButton editar =
        new JButton("Editar");

    private final JButton abrirExercicio =
        new JButton("Abrir exercício");

    private final JButton encerrarExercicio =
        new JButton("Encerrar exercício");

    private final JButton reabrirExercicio =
        new JButton("Reabrir exercício");


    public TelaExerciciosSociais() {

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

        botoes.add(Box.createHorizontalStrut(15));

        botoes.add(abrirExercicio);
        botoes.add(encerrarExercicio);
        botoes.add(reabrirExercicio);



        novo.addActionListener(
            new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    Window pai =
                        SwingUtilities.getWindowAncestor(
                            TelaExerciciosSociais.this
                        );

                    diaExerciciosSociais dialogo =
                        new diaExerciciosSociais(
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
                public void actionPerformed(ActionEvent e) {

                    int linhaSelecionada =
                        tabela.getSelectedRow();


                    if (linhaSelecionada == -1) {

                        JOptionPane.showMessageDialog(
                            TelaExerciciosSociais.this,
                            "Selecione um exercício social para editar.",
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


                    int anoReferencia =
                        Integer.parseInt(
                            modelo
                                .getValueAt(
                                    linhaModelo,
                                    1
                                )
                                .toString()
                        );


                    String dataInicio =
                        modelo
                            .getValueAt(
                                linhaModelo,
                                2
                            )
                            .toString();


                    String dataFim =
                        modelo
                            .getValueAt(
                                linhaModelo,
                                3
                            )
                            .toString();


                    ExercicioSocial.StatusContabil status =
                        ExercicioSocial.StatusContabil.valueOf(
                            modelo
                                .getValueAt(
                                    linhaModelo,
                                    4
                                )
                                .toString()
                        );


                    boolean ativo =
                        Boolean.parseBoolean(
                            modelo
                                .getValueAt(
                                    linhaModelo,
                                    5
                                )
                                .toString()
                        );



                    Window pai =
                        SwingUtilities.getWindowAncestor(
                            TelaExerciciosSociais.this
                        );


                    diaExerciciosSociais dialogo =
                        new diaExerciciosSociais(
                            (Frame) pai,
                            true
                        );


                   

                    dialogo.setId(id);

                    dialogo.setAnoReferencia(
                        String.valueOf(
                            anoReferencia
                        )
                    );

                    dialogo.setDataInicio(
                        dataInicio
                    );

                    dialogo.setDataFinal(
                        dataFim
                    );

                    dialogo.setAtivo(
                        ativo
                    );

                    dialogo.setStatusContabil(
                        status
                    );


                    dialogo.setVisible(true);
                }
            }
        );



        JPanel painelSuperior =
            new JPanel(
                new BorderLayout()
            );

        painelSuperior.add(
            botoes,
            BorderLayout.WEST
        );

        add(
            painelSuperior,
            BorderLayout.NORTH
        );


        

        JPanel painelTabela =
            new JPanel(
                new BorderLayout()
            );

        painelTabela.setBorder(
            BorderFactory.createTitledBorder(
                "Exercícios Sociais"
            )
        );


        tabela.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );

        tabela.setFillsViewportHeight(true);

        tabela.getTableHeader()
            .setReorderingAllowed(false);


      
        tabela.setAutoCreateRowSorter(true);


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
        ExercicioSocial exercicio
    ) {

        modelo.addRow(
            new Object[] {
                exercicio.getId(),
                exercicio.getAnoReferencia(),
                exercicio.getDataInicio(),
                exercicio.getDataFim(),
                exercicio.getStatusContabil(),
                exercicio.isAtivo()
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


    public ExercicioSocial.StatusContabil
        getStatusSelecionado() {

        int linha =
            tabela.getSelectedRow();


        if (linha == -1) {

            return null;
        }


        int linhaModelo =
            tabela.convertRowIndexToModel(
                linha
            );


        return ExercicioSocial.StatusContabil.valueOf(
            modelo
                .getValueAt(
                    linhaModelo,
                    4
                )
                .toString()
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


    public JButton getAbrirExercicio() {
        return abrirExercicio;
    }


    public JButton getEncerrarExercicio() {
        return encerrarExercicio;
    }


    public JButton getReabrirExercicio() {
        return reabrirExercicio;
    }
}