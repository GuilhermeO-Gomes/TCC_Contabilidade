package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.DRE;

public class TelaDRE extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField pesquisa = new JTextField(22);

    private final DefaultTableModel modelo =
        new DefaultTableModel(
            new Object[] {
                "ID",
                "Descrição",
                "Favorito"
            },
            0
        ) {
            @Override
            public boolean isCellEditable(int linha, int coluna) {
                return false;
            }
        };

    private final JTable tabela = new JTable(modelo);

    private final JButton novo = new JButton("Novo");
    private final JButton editar = new JButton("Editar");
    private final JButton itens = new JButton("Configurar Itens");
    private final JButton buscar = new JButton("Buscar");

    public TelaDRE() {

        setLayout(new BorderLayout(8, 8));

        setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        montar();
    }

    private void montar() {

        JPanel topo = new JPanel(new BorderLayout(8, 8));

        JPanel botoes = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );

        botoes.add(novo);
        botoes.add(editar);

        botoes.add(Box.createHorizontalStrut(15));

        botoes.add(itens);

        JPanel busca = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );

        busca.add(new JLabel("Pesquisar:"));
        busca.add(pesquisa);


        topo.add(botoes, BorderLayout.NORTH);
        topo.add(busca, BorderLayout.SOUTH);

        add(topo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new BorderLayout());

        centro.setBorder(
            BorderFactory.createTitledBorder(
                "DREs cadastradas"
            )
        );

        centro.add(
            new JScrollPane(tabela),
            BorderLayout.CENTER
        );

        add(centro, BorderLayout.CENTER);

        tabela.setSelectionMode(
            ListSelectionModel.SINGLE_SELECTION
        );
    }

    public JTable getTabela() {
        return tabela;
    }

    public JTextField getPesquisa() {
        return pesquisa;
    }

    public JButton getNovo() {
        return novo;
    }

    public JButton getEditar() {
        return editar;
    }
    
    public JButton getItens() {
        return itens;
    }

    public void limparTabela() {
        modelo.setRowCount(0);
    }

    public void adicionarLinha(
            DRE dre) {

        modelo.addRow(
            new Object[] {
                dre.getId(),
                dre.getDescricao(),
                dre.isFavorito()
            }
        );
    
    }
}