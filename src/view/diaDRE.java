package view;

import javax.swing.*;
import java.awt.*;

public class diaDRE extends JDialog {

    private static final long serialVersionUID = 1L;

    private final JTextField id = new JTextField(5);
    private final JTextField descricao = new JTextField(28);
    private final JCheckBox favorito = new JCheckBox("Favorito", true);

    private final JButton salvar = new JButton("Salvar");

    public diaDRE(Frame parent, boolean modal) {

        super(parent, "DRE", modal);

        setSize(400, 250);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel dados = new JPanel(new GridBagLayout());
        dados.setBorder(
            BorderFactory.createTitledBorder("Dados da DRE")
        );

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);

        componente(dados, g, 0, "ID", id);
        componente(dados, g, 1, "Descrição", descricao);

        g.gridx = 1;
        g.gridy = 2;
        g.anchor = GridBagConstraints.WEST;
        dados.add(favorito, g);

        id.setEditable(false);

        JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.RIGHT)
        );

        botoes.add(salvar);

        setLayout(new BorderLayout(8, 8));
        add(dados, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
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
        g.fill = GridBagConstraints.NONE;

        painel.add(new JLabel(texto), g);

        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;

        painel.add(componente, g);
    }

    public int getId() {
        try {
            return Integer.parseInt(id.getText());
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

    public void setId(int id) {
        this.id.setText(String.valueOf(id));
    }

    public void setDescricao(String descricao) {
        this.descricao.setText(descricao);
    }

    public void setFavorito(boolean favorito) {
        this.favorito.setSelected(favorito);
    }
}