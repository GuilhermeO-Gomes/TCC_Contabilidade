package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TelaConsolidacaoBalanco extends JPanel {
	
	private final JComboBox<String> cbxPeriodo = new JComboBox<>(
			new String[]{
					"Janeiro",
					"Fevereiro",
					"Março", "Abril",
					"Maio",
					"Junho",
					"Julho",
					"Agosto",
					"Setembro",
					"Outubro",
					"Novembro",
					"Dezembro"
					});
	
	private final JTextField txtPlanoContas = new JTextField(30);
	
	private final JCheckBox chkContaSaldo = new JCheckBox("Ignorar Contas Sem Saldo", false);
	
	private final JButton btnPesquisar = new JButton("Pesquisar"),
			btnLimpar = new JButton("Limpar");
	
	private final DefaultTableModel modelo = new DefaultTableModel(
		    new Object[] { "Conta", "Descrição", "Controladora", "Controlada", "Eliminação", "Saldo Consolidado" },
		    0
	) {
		    public boolean isCellEditable(int l, int c) {
		      return false;
		   }
	};
	private final JTable tabela = new JTable(modelo);
	
	public TelaConsolidacaoBalanco() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
    }
	
	private void montar() {
		JPanel filtroPesquisa = new JPanel(new GridBagLayout());
        filtroPesquisa.setBorder(
      	      BorderFactory.createTitledBorder("Filtros de Pesquisa")
      	    );
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 4, 3, 4);
        g.anchor = GridBagConstraints.WEST;
        
        adicionar(filtroPesquisa, g, 0, "Período", cbxPeriodo);        
        JTextField txtAno = new JTextField(10);
        adicionar(filtroPesquisa, g, 1, "Ano:", txtAno);
        adicionar(filtroPesquisa, g, 2, "Plano de Contas:", txtPlanoContas);
        g.gridx = 1;
        g.gridy = 6;
        filtroPesquisa.add(chkContaSaldo, g);       
        
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(btnPesquisar);
        botoes.add(btnLimpar);
        
        JPanel topo = new JPanel(new BorderLayout());
	    topo.add(filtroPesquisa, BorderLayout.NORTH);
	    topo.add(botoes, BorderLayout.SOUTH);
	    add(topo, BorderLayout.NORTH);
        
	    JPanel centro = new JPanel(new BorderLayout());
	    centro.add(new JScrollPane(tabela), BorderLayout.CENTER);
	    add(centro, BorderLayout.CENTER);
	    tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void adicionar(
            JPanel p,
            GridBagConstraints g,
            int y,
            String rotulo,
            JComponent componente
    ) {
        g.gridx = 0;
        g.gridy = y;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        p.add(new JLabel(rotulo), g);

        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        p.add(componente, g);
    }
	
	

}