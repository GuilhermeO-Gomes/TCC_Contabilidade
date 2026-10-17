package view;

import java.awt.*;

import javax.swing.*;

import model.BalancoPatrimonialItem;

public class diaBalancoPatrimonialItem
        extends JDialog {

    private static final long serialVersionUID = 1L;


    private final JTextField id =
        new JTextField(5);

    private final JTextField ordem =
        new JTextField(10);


    private final JComboBox<String>
        codigoAglutinacao =
            new JComboBox<>();


    private final JComboBox<
        BalancoPatrimonialItem.GrupoBalanco
    > grupo =
        new JComboBox<>(
            BalancoPatrimonialItem
                .GrupoBalanco
                .values()
        );


    private final JComboBox<String>
        itemSuperior =
            new JComboBox<>();


    private final JCheckBox ativo =
        new JCheckBox(
            "Ativo",
            true
        );


    private final JButton salvar =
        new JButton("Salvar");


    public diaBalancoPatrimonialItem(
            Frame parent,
            boolean modal) {

        super(
            parent,
            "Item do Balanço Patrimonial",
            modal
        );


        setSize(
            450,
            380
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
                "Dados do Item"
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
            "Ordem",
            ordem
        );


        componente(
            painel,
            g,
            2,
            "Código de Aglutinação",
            codigoAglutinacao
        );


        componente(
            painel,
            g,
            3,
            "Grupo",
            grupo
        );


        componente(
            painel,
            g,
            4,
            "Item Superior",
            itemSuperior
        );


        id.setEditable(false);


        g.gridx = 1;

        g.gridy = 5;

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
    
    public void limparCodigosAglutinacao() {

        codigoAglutinacao.removeAllItems();
    }


    public void adicionarCodigoAglutinacao(
            String valor) {

        codigoAglutinacao.addItem(valor);
    }


    public void limparItensSuperiores() {

        itemSuperior.removeAllItems();

        itemSuperior.addItem(
            "Nenhum"
        );
    }


    public void adicionarItemSuperior(
            String valor) {

        itemSuperior.addItem(valor);
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


    public String getOrdem() {
        return ordem.getText();
    }
    
    public String getCodigoAglutinacaoSelecionado() {

        Object selecionado =
            codigoAglutinacao.getSelectedItem();

        if (selecionado == null) {
            return null;
        }

        return selecionado.toString();
    }


    public String getItemSuperiorSelecionado() {

        Object selecionado =
            itemSuperior.getSelectedItem();

        if (selecionado == null) {
            return null;
        }

        return selecionado.toString();
    }


    public BalancoPatrimonialItem
        .GrupoBalanco getGrupo() {

        return (BalancoPatrimonialItem
            .GrupoBalanco)
            grupo.getSelectedItem();
    }


    public boolean isAtivo() {
        return ativo.isSelected();
    }


    public JButton getSalvar() {
        return salvar;
    }


    public JComboBox<String>
        getCodigoAglutinacao() {

        return codigoAglutinacao;
    }


    public JComboBox<String>
        getItemSuperior() {

        return itemSuperior;
    }


    public void setId(long valor) {

        id.setText(
            String.valueOf(valor)
        );
    }


    public void setOrdem(int valor) {

        ordem.setText(
            String.valueOf(valor)
        );
    }


    public void setGrupo(
            BalancoPatrimonialItem
                .GrupoBalanco valor) {

        grupo.setSelectedItem(
            valor
        );
    }


    public void setAtivo(
            boolean valor) {

        ativo.setSelected(
            valor
        );
    }
}