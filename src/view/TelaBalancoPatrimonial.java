package view;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.BalancoPatrimonial;

public class TelaBalancoPatrimonial extends JPanel {

    private static final long serialVersionUID = 1L;


  


    private final DefaultTableModel modelo =
        new DefaultTableModel(
            new Object[] {
                "Descrição",
                "Valor inicial",
                "Valor final"
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


    private final JButton buscar =
    	    new JButton("Buscar");

    	private final JButton exportar =
    	    new JButton("Exportar para CSV");
    	
    	private final JComboBox<String> tipoBalanco =
    		    new JComboBox<>(
    		        new String[] {
    		            "Balanço Patrimonial"
    		        }
    		    );

    		private final JTextField dataInicial =
    		    new JTextField("01/10/2026", 10);

    		private final JTextField dataFinal =
    		    new JTextField("31/10/2026", 10);


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
        
      
        
        carregarDadosTeste();
        
        
    }
    


private JPanel montarCabecalho() {

    JPanel cabecalho = new JPanel(
        new BorderLayout(8, 0)
    );

    cabecalho.setBackground(
        new Color(52, 85, 114)
    );

    cabecalho.setBorder(
        BorderFactory.createEmptyBorder(
            8, 10, 8, 10
        )
    );

    // TÍTULO

    JLabel titulo = new JLabel(
        "Balanço Patrimonial"
    );

    titulo.setFont(
        new Font("Arial", Font.BOLD, 20)
    );

    titulo.setForeground(Color.WHITE);

  

    exportar.setForeground(Color.WHITE);

    exportar.setBackground(
        new Color(52, 85, 114)
    );

    exportar.setFocusPainted(false);
    exportar.setBorderPainted(false);
    exportar.setContentAreaFilled(false);

  

    cabecalho.add(
        titulo,
        BorderLayout.WEST
    );

    cabecalho.add(
        exportar,
        BorderLayout.EAST
    );

    return cabecalho;
}




private JPanel montarFiltros() {

    JPanel filtros = new JPanel(
        new FlowLayout(FlowLayout.LEFT, 8, 5)
    );

    filtros.setBorder(
        BorderFactory.createTitledBorder(
            "Filtros"
        )
    );

    filtros.add(
        new JLabel("Balanço Patrimonial:")
    );

    filtros.add(tipoBalanco);

    filtros.add(
        new JLabel("Data inicial:")
    );

    filtros.add(dataInicial);

    filtros.add(
        new JLabel("até")
    );

    filtros.add(
        new JLabel("Data final:")
    );

    filtros.add(dataFinal);

    filtros.add(buscar);

    return filtros;
}


private DefaultTableCellRenderer criarRendererCabecalho(
        int alinhamento) {

    DefaultTableCellRenderer renderer =
        new DefaultTableCellRenderer();

    renderer.setHorizontalAlignment(alinhamento);

    renderer.setBackground(
        new Color(52, 85, 114)
    );

    renderer.setForeground(Color.WHITE);

    renderer.setFont(
        new Font("Arial", Font.BOLD, 13)
    );

    renderer.setOpaque(true);

    renderer.setBorder(
        BorderFactory.createEmptyBorder(0, 8, 0, 8)
    );

    return renderer;
}




private JPanel montarTabela() {

    JPanel centro = new JPanel(
        new BorderLayout()
    );

    centro.setBorder(
        BorderFactory.createTitledBorder(
            "Demonstrativo Contábil"
        )
    );

    tabela.setSelectionMode(
        ListSelectionModel.SINGLE_SELECTION
    );

    tabela.setFillsViewportHeight(true);

    tabela.setAutoCreateRowSorter(false);

    tabela.getTableHeader()
        .setReorderingAllowed(false);

    tabela.setRowHeight(30);

    tabela.setShowGrid(true);

    tabela.setGridColor(
        new Color(220, 220, 220)
    );

    tabela.setIntercellSpacing(
        new Dimension(1, 1)
    );

    tabela.getTableHeader().setFont(
        new Font("Arial", Font.BOLD, 13)
    );

    tabela.getTableHeader().setPreferredSize(
        new Dimension(0, 32)
    );
    


    tabela.getTableHeader().setBackground(
        new Color(52, 85, 114)
    );

    tabela.getTableHeader().setForeground(
        Color.WHITE
    );

    tabela.getTableHeader().setOpaque(true);
    


    tabela.getColumnModel()
        .getColumn(0)
        .setHeaderRenderer(
            criarRendererCabecalho(SwingConstants.LEFT)
        );

    tabela.getColumnModel()
        .getColumn(1)
        .setHeaderRenderer(
            criarRendererCabecalho(SwingConstants.RIGHT)
        );

    tabela.getColumnModel()
        .getColumn(2)
        .setHeaderRenderer(
            criarRendererCabecalho(SwingConstants.RIGHT)
        );

    RendererBalanco renderer = new RendererBalanco();

    for (int i = 0; i < tabela.getColumnCount(); i++) {

        tabela.getColumnModel()
            .getColumn(i)
            .setCellRenderer(renderer);
    }
   

    tabela.getColumnModel()
        .getColumn(0)
        .setPreferredWidth(500);

    tabela.getColumnModel()
        .getColumn(1)
        .setPreferredWidth(180);

    tabela.getColumnModel()
        .getColumn(2)
        .setPreferredWidth(180);

    centro.add(
        new JScrollPane(tabela),
        BorderLayout.CENTER
    );

    return centro;
}





    private void montar() {

        JPanel topo = new JPanel(
            new BorderLayout(8, 8)
        );

        topo.add(
        	    montarCabecalho(),
        	    BorderLayout.NORTH
        	);

        topo.add(
        	    montarFiltros(),
        	    BorderLayout.CENTER
        	);

        	add(
        		topo,
        		BorderLayout.NORTH
        );

        
        	add(
        	   montarTabela(),
        	   BorderLayout.CENTER
        );
        
    
    }
    

private void carregarDadosTeste() {

    // =========================
    // ATIVO
    // =========================

    adicionarLinha(
        new LinhaBalanco(
            "ATIVO",
            "74.799,06 D",
            "122.435,62 D",
            LinhaBalanco.Tipo.GRUPO
        )
    );

    // ATIVO CIRCULANTE

    adicionarLinha(
        new LinhaBalanco(
            "ATIVO CIRCULANTE",
            "54.799,06 D",
            "97.435,62 D",
            LinhaBalanco.Tipo.SUBGRUPO
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Disponibilidades",
            "10.000,00 D",
            "15.000,00 D",
            LinhaBalanco.Tipo.CONTA
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Contas a Receber",
            "35.000,00 D",
            "62.500,00 D",
            LinhaBalanco.Tipo.CONTA
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Estoques",
            "4.619,06 D",
            "13.321,87 D",
            LinhaBalanco.Tipo.CONTA
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Impostos a Recuperar",
            "5.180,00 D",
            "6.613,75 D",
            LinhaBalanco.Tipo.CONTA
        )
    );

    // ATIVO NÃO CIRCULANTE

    adicionarLinha(
        new LinhaBalanco(
            "ATIVO NÃO CIRCULANTE",
            "20.000,00 D",
            "25.000,00 D",
            LinhaBalanco.Tipo.SUBGRUPO
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Imobilizado",
            "20.000,00 D",
            "25.000,00 D",
            LinhaBalanco.Tipo.CONTA
        )
    );


    // =========================
    // PASSIVO
    // =========================

    adicionarLinha(
        new LinhaBalanco(
            "PASSIVO",
            "30.000,00 C",
            "42.000,00 C",
            LinhaBalanco.Tipo.GRUPO
        )
    );

    // PASSIVO CIRCULANTE

    adicionarLinha(
        new LinhaBalanco(
            "PASSIVO CIRCULANTE",
            "18.000,00 C",
            "25.000,00 C",
            LinhaBalanco.Tipo.SUBGRUPO
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Fornecedores",
            "12.000,00 C",
            "17.000,00 C",
            LinhaBalanco.Tipo.CONTA
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Obrigações Trabalhistas",
            "6.000,00 C",
            "8.000,00 C",
            LinhaBalanco.Tipo.CONTA
        )
    );

    // PASSIVO NÃO CIRCULANTE

    adicionarLinha(
        new LinhaBalanco(
            "PASSIVO NÃO CIRCULANTE",
            "12.000,00 C",
            "17.000,00 C",
            LinhaBalanco.Tipo.SUBGRUPO
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Empréstimos",
            "12.000,00 C",
            "17.000,00 C",
            LinhaBalanco.Tipo.CONTA
        )
    );


    // =========================
    // PATRIMÔNIO LÍQUIDO
    // =========================

    adicionarLinha(
        new LinhaBalanco(
            "PATRIMÔNIO LÍQUIDO",
            "44.799,06 C",
            "80.435,62 C",
            LinhaBalanco.Tipo.GRUPO
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Capital Social",
            "40.000,00 C",
            "40.000,00 C",
            LinhaBalanco.Tipo.SUBGRUPO
        )
    );

    adicionarLinha(
        new LinhaBalanco(
            "Lucros Acumulados",
            "4.799,06 C",
            "40.435,62 C",
            LinhaBalanco.Tipo.SUBGRUPO
        )
    );
}

    
   


 

    public void limparTabela() {

        modelo.setRowCount(0);
    }


    public void adicionarLinha(LinhaBalanco item) {

        modelo.addRow(
            new Object[] {
                item,
                item.getValorInicial(),
                item.getValorFinal()
            }
        );
    }

 



    /*
     * GETTERS
     */

    public JTable getTabela() {
        return tabela;
    }


    public JButton getExportar() {
        return exportar;
    }

    public String getDataInicial() {
        return dataInicial.getText();
    }

    public String getDataFinal() {
        return dataFinal.getText();
    }

    public String getTipoBalanco() {
        return tipoBalanco.getSelectedItem().toString();
    }


    public JButton getBuscar() {
        return buscar;
    }
}