package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class TelaBuscarConta extends JDialog{

		private static final long serialVersionUID = 1L;
		private final JTextField txt_pesquisa = new JTextField(15);
		private final DefaultTableModel modeloTabela = new DefaultTableModel(
	    new Object[] {"Código", "Descrição" }, 0);
		private JTable TabelaBuscaConta;
	            
	    public TelaBuscarConta(Window janela_pai) {
	    	super(janela_pai, "Selecionar Conta", ModalityType.APPLICATION_MODAL);
	    	setLayout(new BorderLayout(8,8));
	        montar_tela();
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        pack();
	        setLocationRelativeTo(janela_pai);
	    }

	    private void montar_tela() {
	    	//Criar funções que criem automaticamente JPanels e GBCs, e JLabels
	    	JPanel jp_pesquisa = new JPanel(new GridBagLayout());
	    	jp_pesquisa.setBorder(BorderFactory.createTitledBorder("Pesquisa de Conta"));
	    	GridBagConstraints gbc_pesquisa = new GridBagConstraints();
	    	gbc_pesquisa.insets = new Insets(5, 2, 5, 2);
	    	gbc_pesquisa.anchor = GridBagConstraints.WEST;
	    	gbc_pesquisa.weightx = 0;
	    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    	
	    	JLabel lbl_pesquisar = new JLabel("Pesquisar:");
	    
	    	JButton btn_pesquisar = new JButton("Pesquisar"), btn_selecionar = new JButton("Selecionar"), 
	    	btn_cancelar = new JButton("Cancelar");
	    	
	    	adicionar_componente(0, jp_pesquisa, gbc_pesquisa, lbl_pesquisar, txt_pesquisa);
	    	gbc_pesquisa.gridx = 2;
	    	gbc_pesquisa.gridy = 0;
	    	gbc_pesquisa.weightx = 0;
	    	gbc_pesquisa.fill = GridBagConstraints.NONE;
	    	jp_pesquisa.add(btn_pesquisar, gbc_pesquisa);
	    		    	
	    	TabelaBuscaConta = new JTable(modeloTabela);
	        JScrollPane scrollPane =new JScrollPane(TabelaBuscaConta);
	    	
	    	botoes.add(btn_cancelar);
	    	botoes.add(btn_selecionar);
	    	btn_cancelar.addActionListener(e -> dispose());
	    	
	    	add(jp_pesquisa, BorderLayout.NORTH);
	    	add(scrollPane, BorderLayout.CENTER);
	        add(botoes, BorderLayout.SOUTH);
	    }
	public void adicionar_componente( 
		int y, 
		JPanel painel, 
		GridBagConstraints organizador, 
		JLabel lbl, 
		JComponent componente  
		) {
	organizador.gridx = 0;
	organizador.gridy = y;
	organizador.weightx = 0;
	organizador.gridwidth = 1;
	organizador.fill = GridBagConstraints.NONE;
	painel.add(lbl, organizador);

	organizador.gridx = 1;
	organizador.gridy = y;
	organizador.weightx = 1;
	organizador.fill = GridBagConstraints.HORIZONTAL;
	painel.add(componente, organizador);
	};
	}