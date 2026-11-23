package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.DRE;

public class TelaDRE extends JPanel {

    private static final long serialVersionUID = 1L;

  


private final DefaultTableModel modelo =
    new DefaultTableModel(
        new Object[] {
            "Descrição",
            "Total geral"
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


    private final JTable tabela = new JTable(modelo);
    
    private final JComboBox<String> dre =
    	    new JComboBox<>(
    	        new String[] {
    	            "DRE"
    	        }
    	    );

    	private final JComboBox<String> tipoAgrupamento =
    	    new JComboBox<>(
    	        new String[] {
    	            "Sem agrupamento"
    	        }
    	    );

    	private final JTextField dataInicial =
    	    new JTextField(10);

    	private final JTextField dataFinal =
    	    new JTextField(10);

    	private final JComboBox<String> empresa =
    	    new JComboBox<>(
    	        new String[] {
    	            "Filial 01"
    	        }
    	    );

    	private final JCheckBox exibirTotalGeral =
    	    new JCheckBox("Exibir total geral", true);

    	private final JButton filtrar =
    	    new JButton("Filtrar");


    public TelaDRE() {

        setLayout(new BorderLayout(8, 8));

        setBorder(
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );

        montar();
        
        carregarDadosTeste();
    }


private void montar() {

    JPanel topo = new JPanel(
        new BorderLayout(8, 8)
    );

   

    JPanel filtros = new JPanel(
        new FlowLayout(FlowLayout.LEFT, 8, 5)
    );

    filtros.setBorder(
        BorderFactory.createTitledBorder("Filtros")
    );

    filtros.add(new JLabel("DRE *"));
    filtros.add(dre);

    filtros.add(new JLabel("Tipo de agrupamento"));
    filtros.add(tipoAgrupamento);

    filtros.add(new JLabel("Período *"));
    filtros.add(dataInicial);

    filtros.add(new JLabel("até"));
    filtros.add(dataFinal);

    filtros.add(new JLabel("Minha empresa *"));
    filtros.add(empresa);

    JPanel opcoes = new JPanel(
        new FlowLayout(FlowLayout.LEFT)
    );

    opcoes.add(exibirTotalGeral);

    opcoes.add(filtrar);

    topo.add(filtros, BorderLayout.CENTER);
    topo.add(opcoes, BorderLayout.SOUTH);

    add(topo, BorderLayout.NORTH);

   

    JPanel centro = new JPanel(
        new BorderLayout()
    );

    centro.setBorder(
        BorderFactory.createTitledBorder(
            "Demonstrativo de Resultado"
        )
    );

    tabela.setSelectionMode(
        ListSelectionModel.SINGLE_SELECTION
    );

    tabela.setFillsViewportHeight(true);

    tabela.setAutoCreateRowSorter(false);

    tabela.getTableHeader()
        .setReorderingAllowed(false);
    
    RendererDRE renderer = new RendererDRE();

    for (int i = 0; i < tabela.getColumnCount(); i++) {
        tabela.getColumnModel().getColumn(i).setCellRenderer(renderer);
    }

    centro.add(
        new JScrollPane(tabela),
        BorderLayout.CENTER
    );

    add(centro, BorderLayout.CENTER);
}


public void adicionarLinha(
        String descricao,
        String totalGeral) {

    modelo.addRow(
        new Object[] {
            descricao,
            totalGeral
        }
    );
}

public void limparTabela() {
    modelo.setRowCount(0);
}



private void carregarDadosTeste() {

    adicionarLinha("RECEITA BRUTA", "2.972,50");
    adicionarLinha("  Receita de Vendas", "2.972,50");

    adicionarLinha("DEDUÇÕES DAS RECEITAS", "0,00");

    adicionarLinha("RECEITA LÍQUIDA", "2.972,50");

    adicionarLinha("CUSTOS", "-5,56");
    adicionarLinha("  Custos dos Produtos Vendidos", "-5,56");

    adicionarLinha("LUCRO BRUTO", "2.966,94");

    adicionarLinha("DESPESAS OPERACIONAIS", "-2,60");
    adicionarLinha("  Despesas Administrativas", "-2,60");

    adicionarLinha("LUCRO OPERACIONAL", "2.964,34");

    adicionarLinha("RESULTADO DO EXERCÍCIO", "2.964,34");
}



public JComboBox<String> getDre() {
    return dre;
}

public JComboBox<String> getTipoAgrupamento() {
    return tipoAgrupamento;
}

public String getDataInicial() {
    return dataInicial.getText();
}

public String getDataFinal() {
    return dataFinal.getText();
}

public JComboBox<String> getEmpresa() {
    return empresa;
}

public boolean isExibirTotalGeral() {
    return exibirTotalGeral.isSelected();
}

public JButton getFiltrar() {
    return filtrar;
}

}