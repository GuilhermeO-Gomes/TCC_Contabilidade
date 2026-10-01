package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

public class TelaLivroRazao extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField historico = new JTextField(15);
	private final DefaultTableModel modeloTabela = new DefaultTableModel(
    new Object[] {"Data", "Documento", "Histórico", "Crédito", "Débito", "Saldo"}, 0);
	private JTable TabelaLivroRazao;
            
	//JSpinner serve para ser um campo de formulário de data sem precisar converter
	SpinnerDateModel modelo_data_1= new SpinnerDateModel();
	SpinnerDateModel modelo_data_2= new SpinnerDateModel();
	JSpinner periodo_1 = new JSpinner(modelo_data_1);
	JSpinner periodo_2 = new JSpinner(modelo_data_2);
	JSpinner.DateEditor editor_1 = new JSpinner.DateEditor(periodo_1, "dd/MM/yyyy");
	JSpinner.DateEditor editor_2 = new JSpinner.DateEditor(periodo_2, "dd/MM/yyyy");
	//Para pegar valores desses campos: periodo_1.getValue(); 
	{periodo_1.setEditor(editor_1);
	periodo_2.setEditor(editor_2);}
	
	
	
    public TelaLivroRazao() {
    	setLayout(new BorderLayout(8,8));
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar_tela();
    }

    private void montar_tela() {
    	//Criar funções que criem automaticamente JPanels e GBCs, e JLabels
    	
    	JPanel painel_principal = new JPanel(new GridBagLayout());
    	painel_principal.setBorder(BorderFactory.createTitledBorder("Razão"));
    	GridBagConstraints organizador_principal = new GridBagConstraints();
    	organizador_principal.insets = new Insets(10, 2, 10, 2);
    	organizador_principal.anchor = GridBagConstraints.WEST;
    	organizador_principal.weightx = 0;
    	JScrollPane scroll = new JScrollPane(painel_principal);
    	JPanel painel_pesquisa = new JPanel(new GridBagLayout());
    	painel_pesquisa.setBorder(BorderFactory.createTitledBorder("Filtros e pesquisa"));
    	GridBagConstraints organizador_pesquisa = new GridBagConstraints();
    	organizador_pesquisa.insets = new Insets(10, 2, 10, 2);
    	organizador_pesquisa.anchor = GridBagConstraints.WEST;
    	organizador_pesquisa.weightx = 0;
    	JPanel painel_conta = new JPanel(new GridBagLayout());
    	painel_conta.setBorder(BorderFactory.createTitledBorder("Informações da conta selecionada"));
    	GridBagConstraints organizador_conta = new GridBagConstraints();
    	organizador_conta.insets = new Insets(10, 2, 10, 2);
    	organizador_conta.anchor = GridBagConstraints.WEST;
    	organizador_conta.weightx = 0;
    	JPanel painel_credito = new JPanel(new GridBagLayout());
    	painel_credito.setBorder(BorderFactory.createTitledBorder("Créditos e débitos"));
    	GridBagConstraints organizador_credito = new GridBagConstraints();
    	organizador_credito.insets = new Insets(10, 2, 10, 2);
    	organizador_credito.anchor = GridBagConstraints.WEST;
    	organizador_credito.weightx = 0;
    	JPanel painel_lancamento = new JPanel(new BorderLayout(5,5));
    	painel_lancamento.setBorder(BorderFactory.createTitledBorder("Lançamentos da conta"));
    	JPanel botoes_pesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JPanel botao_lancamento = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JLabel lbl_conta = new JLabel("Conta:"), lbl_conta1 = new JLabel("Conta:"), lbl_periodo = new JLabel("Período:"),
    	lbl_ate = new JLabel("até"), lbl_historico = new JLabel("Histórico"),
    	lbl_centro_custo = new JLabel("Centro de Custo:"), lbl_descricao = new JLabel("Descrição:"),
    	lbl_natureza = new JLabel("Natureza:"), lbl_saldo_ini = new JLabel("Saldo inicial:"),
    	lbl_debito = new JLabel("Total Débitos:"), lbl_credito = new JLabel("Total Créditos:"),
    	lbl_saldo_final = new JLabel("Saldo Final:"), lbl_placeholder1 = new JLabel("Placholder"),
    	lbl_placeholder2 = new JLabel("Placholder"), lbl_placeholder3 = new JLabel("Placholder"),
    	lbl_placeholder4 = new JLabel("Placholder"), lbl_placeholder5 = new JLabel("Placholder"),
    	lbl_placeholder6 = new JLabel("Placholder"), lbl_placeholder7 = new JLabel("Placholder");
    	
    	
    
    	String[] contas= {"1.1.01 - Caixa"};
    	String[] centro_custo = {"Todos"};

    	JComboBox <String> conta = new JComboBox<>(contas);
    	JComboBox <String> centro = new JComboBox<>(centro_custo);

    	JButton pesquisar = new JButton("Pesquisar"), limpar = new JButton("Limpar"), 
    	exportar = new JButton("Exportar"), imprimir = new JButton("Imprimir"), 
    	lancamento = new JButton("Visualizar lançamento selecionado");
    	
    	adicionar_componente(0, painel_pesquisa, organizador_pesquisa, lbl_conta, conta);
    	adicionar_componente(1, painel_pesquisa, organizador_pesquisa, lbl_periodo, periodo_1);
    	organizador_pesquisa.gridx = 2;
    	organizador_pesquisa.gridy = 1;
    	organizador_pesquisa.weightx = 0;
    	organizador_pesquisa.fill = GridBagConstraints.NONE;
    	painel_pesquisa.add(lbl_ate, organizador_pesquisa);

    	organizador_pesquisa.gridx = 3;
    	organizador_pesquisa.gridy = 1;
    	organizador_pesquisa.weightx = 1;
    	organizador_pesquisa.fill = GridBagConstraints.HORIZONTAL;
    	painel_pesquisa.add(periodo_2, organizador_pesquisa);
    	adicionar_componente(2, painel_pesquisa, organizador_pesquisa, lbl_historico, historico);
    	adicionar_componente(3, painel_pesquisa, organizador_pesquisa, lbl_centro_custo, centro);
    	
    	botoes_pesquisa.add(pesquisar);
    	botoes_pesquisa.add(limpar);
    	botoes_pesquisa.add(exportar);
    	botoes_pesquisa.add(imprimir);
    	
    	organizador_pesquisa.gridy = 4;
    	organizador_pesquisa.gridx = 0;
    	organizador_pesquisa.gridwidth = 2;
    	organizador_pesquisa.weightx = 1;
    	organizador_pesquisa.weighty = 0;
    	organizador_pesquisa.fill = GridBagConstraints.HORIZONTAL;
    	painel_pesquisa.add(botoes_pesquisa, organizador_pesquisa);
    	
    	organizador_principal.gridx = 0;
    	organizador_principal.gridy = 0;
    	organizador_principal.anchor = GridBagConstraints.WEST;
    	organizador_principal.weightx = 1;
    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
    	painel_principal.add(painel_pesquisa, organizador_principal);
    	
    	adicionar_componente(0, painel_conta, organizador_conta, lbl_conta1, lbl_placeholder1);
    	adicionar_componente(1, painel_conta, organizador_conta, lbl_descricao, lbl_placeholder2);
    	adicionar_componente(2, painel_conta, organizador_conta, lbl_natureza, lbl_placeholder3);
    	adicionar_componente(3, painel_conta, organizador_conta, lbl_saldo_ini, lbl_placeholder4);
    	
    	organizador_principal.gridx = 0;
    	organizador_principal.gridy = 1;
    	organizador_principal.anchor = GridBagConstraints.WEST;
    	organizador_principal.weightx = 1;
    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
    	painel_principal.add(painel_conta, organizador_principal);
    	
    	TabelaLivroRazao = new JTable(modeloTabela);
        JScrollPane scrollPane =new JScrollPane(TabelaLivroRazao);
        botao_lancamento.add(lancamento);
        painel_lancamento.add(scrollPane, BorderLayout.CENTER);
        painel_lancamento.add(botao_lancamento, BorderLayout.SOUTH);
         
    	organizador_principal.gridy = 2;
    	organizador_principal.weighty = 0;
    	organizador_principal.fill = GridBagConstraints.BOTH;
    	painel_principal.add(painel_lancamento, organizador_principal);
    	
    	adicionar_componente(0, painel_credito, organizador_credito, lbl_credito, lbl_placeholder5);
    	adicionar_componente(1, painel_credito, organizador_credito, lbl_debito, lbl_placeholder6);
    	adicionar_componente(2, painel_credito, organizador_credito, lbl_saldo_final, lbl_placeholder7);
    	

    	organizador_principal.gridy = 3;
    	organizador_principal.anchor = GridBagConstraints.WEST;
    	organizador_principal.weightx = 1;
    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
    	painel_principal.add(painel_credito, organizador_principal);
    	
        add(scroll, BorderLayout.CENTER);
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