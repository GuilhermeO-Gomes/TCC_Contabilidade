package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TelaIntegracao extends JPanel {
	
	
	private final JTextField txtEmpresa = new JTextField(50),
			txtPeriodoInicial = new JTextField(10),
			txtPeriodoFinal = new JTextField(10),
			txtPesquisa = new JTextField(30);
	
	private final JCheckBox chkVendas = new JCheckBox("Vendas", false),
			chkFinanceiro = new JCheckBox("Financeiro", false),
			chkEstoque = new JCheckBox("Estoque", false);
	
	private final DefaultTableModel modelo = new DefaultTableModel(
		    new Object[] { "Data", "Módulo", "Documento", "Transação", "Valor", "Status" },
		    0
	) {
		    public boolean isCellEditable(int l, int c) {
		      return false;
		   }
	};
	private final JTable tabela = new JTable(modelo);
	
	public TelaIntegracao() {
	    setLayout(new BorderLayout(8, 8));
	    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    montar();
	  }
	
	private void montar() {
		JPanel integracao = new JPanel(new GridBagLayout());
	    integracao.setBorder(
	      BorderFactory.createTitledBorder("Integração Contábil")
	    );
	    GridBagConstraints g = new GridBagConstraints();
	    g.insets = new Insets(3, 4, 3, 4);
	    g.anchor = GridBagConstraints.WEST;
	    adicionar(integracao, g, 0, "Empresa:", txtEmpresa);
	    adicionar(integracao, g, 1, "Período inicial:", txtPeriodoInicial);
	    adicionar(integracao, g, 2, "Período final:", txtPeriodoFinal);
	    g.gridx = 1;
	    g.gridy = 6;
	    integracao.add(chkVendas, g);
	    g.gridx = 1;
	    g.gridy = 7;
	    integracao.add(chkFinanceiro, g);
	    g.gridx = 1;
	    g.gridy = 8;
	    integracao.add(chkEstoque, g);
	    g.gridx = 3;
	    g.gridy = 4;
	    String[] status = {"Integrado", "Não integrado", "Falha de integração"};
	    JComboBox <String> cbxStatus = new JComboBox<>(status);
	    adicionar(integracao, g, 3, "Status:", cbxStatus);
	    
	    JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    JButton btnIntegrar = new JButton("Integrar"),
	    		btnReprocessar = new JButton("Reprocessar");
	    botoes.add(btnIntegrar);
	    botoes.add(btnReprocessar);
	    
	    JPanel topo = new JPanel(new BorderLayout());
	    topo.add(integracao, BorderLayout.CENTER);
	    topo.add(botoes, BorderLayout.SOUTH);
	    add(topo, BorderLayout.NORTH);
	    JPanel pesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pesquisa.add(new JLabel("Pesquisar por Data:"));
	    pesquisa.add(txtPesquisa);
	    JButton buscar = new JButton("Buscar"),
	      todos = new JButton("Mostrar todos");
	    pesquisa.add(buscar);
	    pesquisa.add(todos);
	    JPanel centro = new JPanel(new BorderLayout());
	    centro.add(pesquisa, BorderLayout.NORTH);
	    centro.add(new JScrollPane(tabela), BorderLayout.CENTER);
	    add(centro, BorderLayout.CENTER);
	    tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	}
	    
	private void adicionar(
		    JPanel p,
		    GridBagConstraints g,
		    int y,
		    String rotulo,
		    JComponent campo
		  ) {
		    g.gridx = 0;
		    g.gridy = y;
		    g.weightx = 0;
		    g.fill = GridBagConstraints.NONE;
		    p.add(new JLabel(rotulo), g);
		    g.gridx = 1;
		    g.weightx = 1;
		    g.fill = GridBagConstraints.HORIZONTAL;
		    p.add(campo, g);
		  }
	
	

}