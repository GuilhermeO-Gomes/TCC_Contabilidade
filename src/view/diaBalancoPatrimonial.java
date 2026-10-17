package view;

import java.awt.*;

import javax.swing.*;

public class diaBalancoPatrimonial extends JDialog {

    private static final long serialVersionUID = 1L;


    private final JTextField id =
        new JTextField(5);

    private final JTextField descricao =
        new JTextField(28);


    private final JCheckBox favorito =
        new JCheckBox(
            "Favorito",
            false
        );


    private final JButton salvar =
        new JButton("Salvar");


    public diaBalancoPatrimonial(
            Frame parent,
            boolean modal) {

        super(
            parent,
            "Balanço Patrimonial",
            modal
        );


        setSize(
            400,
            250
        );

        setLocationRelativeTo(
            parent
        );

        setDefaultCloseOperation(
            DISPOSE_ON_CLOSE
        );


        JPanel painel =
            new JPanel(
                new GridBagLayout()
            );


        painel.setBorder(
            BorderFactory.createTitledBorder(
                "Dados do Balanço"
            )
        );


        GridBagConstraints g =
            new GridBagConstraints();

        g.insets =
            new Insets(
                4,
                4,
                4,
                4
            );


        componente(
            painel,
            g,
            0,
            "ID",
            id
        );


        componente(
            painel,
            g,
            1,
            "Descrição",
            descricao
        );


        id.setEditable(false);


        g.gridx = 1;

        g.gridy = 2;

        painel.add(
            favorito,
            g
        );


        JPanel botoes =
            new JPanel(
                new FlowLayout(
                    FlowLayout.LEFT
                )
            );


        botoes.add(
            salvar
        );


        add(
            painel,
            BorderLayout.CENTER
        );

        add(
            botoes,
            BorderLayout.SOUTH
        );
    }


    private void componente(
            JPanel painel,
            GridBagConstraints g,
            int linha,
            String texto,
            Component componente) {

        g.gridx = 0;

        g.gridy = linha;

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


    public boolean isFavorito() {
        return favorito.isSelected();
    }


    public JButton getSalvar() {
        return salvar;
    }


    public void setId(long valor) {

        id.setText(
            String.valueOf(valor)
        );
    }


    public void setDescricao(
            String valor) {

        descricao.setText(
            valor
        );
    }


    public void setFavorito(
            boolean valor) {

        favorito.setSelected(
            valor
        );
    }
}