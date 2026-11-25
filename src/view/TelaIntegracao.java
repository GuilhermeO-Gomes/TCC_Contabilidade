package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TelaIntegracao extends JPanel {
	
	
	private final JTextField txtPeriodoInicial = new JTextField(10),
			txtPeriodoFinal = new JTextField(10),
			txtPesquisa = new JTextField(30);
	
	private final JCheckBox chkVendas = new JCheckBox("Vendas", false),
			chkFinanceiro = new JCheckBox("Financeiro", false),
			chkEstoque = new JCheckBox("Estoque", false),
			chkRH = new JCheckBox("RH", false);;
			
	private final JComboBox<String> cbxEmpresa = new JComboBox<>(new String[]{"Selecione uma empresa"}),
			cbxStatus = new JComboBox<>(new String[]{"TODOS", "PENDENTE", "PROCESSADO", "ERRO", "IGNORADO"});
	
	private final DefaultTableModel modelo = new DefaultTableModel(
		    new Object[] { "Entidade", "Registro", "Evento", "Documento", "Data", "Valor", "Status", "Mensagem de Erro", "Lançamento Gerado" },
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
	    adicionar(integracao, g, 0, "Empresa:", cbxEmpresa);
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
	    g.gridx = 1;
	    g.gridy = 9;
	    integracao.add(chkRH, g);
	    g.gridx = 3;
	    g.gridy = 4;
	    adicionar(integracao, g, 3, "Status:", cbxStatus);
	    
	    
	    JPanel topo = new JPanel(new BorderLayout());
	    topo.add(integracao, BorderLayout.CENTER);
	    add(topo, BorderLayout.NORTH);
	    JPanel pesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    pesquisa.add(new JLabel("Pesquisar por Data:"));
	    pesquisa.add(txtPesquisa);
	    JButton btnBuscar = new JButton("Buscar"),
	      btnTodos = new JButton("Mostrar todos"),
	      btnReprocessar = new JButton("Reprocessar");
	    pesquisa.add(btnBuscar);
	    pesquisa.add(btnTodos);
	    pesquisa.add(btnReprocessar);
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