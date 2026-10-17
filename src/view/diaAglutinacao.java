package view;

import java.awt.*;

import javax.swing.*;

public class diaAglutinacao extends JDialog {

    private static final long serialVersionUID = 1L;


    private final JTextField id =
        new JTextField(5);

    private final JTextField valor =
        new JTextField(28);

    private final JTextField descricao =
        new JTextField(28);

    private final JTextField observacao =
        new JTextField(28);


    private final JCheckBox ativo =
        new JCheckBox(
            "Ativo",
            true
        );


    private final JButton salvar =
        new JButton("Salvar");


    public diaAglutinacao(
            Frame parent,
            boolean modal) {

        super(
            parent,
            "Código de Aglutinação",
            modal
        );


        setSize(
            430,
            350
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
                "Dados"
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
            "Valor",
            valor
        );


        componente(
            painel,
            g,
            2,
            "Descrição",
            descricao
        );


        componente(
            painel,
            g,
            3,
            "Observação",
            observacao
        );


        id.setEditable(false);


        g.gridx = 1;
        g.gridy = 4;

        painel.add(
            ativo,
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


        JPanel principal =
            new JPanel(
                new BorderLayout()
            );


        principal.add(
            painel,
            BorderLayout.CENTER
        );

        principal.add(
            botoes,
            BorderLayout.SOUTH
        );


        add(
            principal,
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


 

    public long getId() {

        try {

            return Long.parseLong(
                id.getText()
            );

        } catch (NumberFormatException e) {

            return 0;
        }
    }


    public String getValor() {
        return valor.getText();
    }


    public String getDescricao() {
        return descricao.getText();
    }


    public String getObservacao() {
        return observacao.getText();
    }


    public boolean isAtivo() {
        return ativo.isSelected();
    }


    public JButton getSalvar() {
        return salvar;
    }


  

    public void setId(long valor) {

        id.setText(
            String.valueOf(valor)
        );
    }


    public void setValor(
            String valor) {

        this.valor.setText(
            valor
        );
    }


    public void setDescricao(
            String descricao) {

        this.descricao.setText(
            descricao
        );
    }


    public void setObservacao(
            String observacao) {

        this.observacao.setText(
            observacao
        );
    }


    public void setAtivo(
            boolean ativo) {

        this.ativo.setSelected(
            ativo
        );
    }
}