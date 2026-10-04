package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

public class TelaBalancete extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTextField conta = new JTextField(15);
	private final DefaultTableModel modeloTabela = new DefaultTableModel(
    new Object[] {"Conta", "Descrição", "Débitos", "Créditos", "Saldo Devedor"}, 0);
	private JTable TabelaBalancete;
            
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
	
	
	
    public TelaBalancete() {
    	setLayout(new BorderLayout(8,8));
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar_tela();
    }

    private void montar_tela() {
    	//Criar funções que criem automaticamente JPanels e GBCs, e JLabels
    	
    
    	GridBagConstraints organizador_principal = new GridBagConstraints();
    	organizador_principal.insets = new Insets(10, 2, 10, 2);
    	organizador_principal.anchor = GridBagConstraints.WEST;
    	organizador_principal.weightx = 0;
    	JPanel painel_pesquisa = new JPanel(new GridBagLayout());
    	painel_pesquisa.setBorder(BorderFactory.createTitledBorder("Filtros e pesquisa"));
    	GridBagConstraints organizador_pesquisa = new GridBagConstraints();
    	organizador_pesquisa.insets = new Insets(5, 2, 5, 2);
    	organizador_pesquisa.anchor = GridBagConstraints.WEST;
    	organizador_pesquisa.weightx = 0;
    	JPanel painel_lancamento = new JPanel(new GridBagLayout());
    	painel_lancamento.setBorder(BorderFactory.createTitledBorder("Lançamentos da conta"));
    	GridBagConstraints organizador_lancamento = new GridBagConstraints();
    	organizador_lancamento.insets = new Insets(10, 2, 10, 2);
    	organizador_lancamento.anchor = GridBagConstraints.WEST;
    	organizador_lancamento.weightx = 0;
    	JPanel botoes_pesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JPanel painel_dados = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	
    	JLabel lbl_conta = new JLabel("Conta:"), lbl_periodo = new JLabel("Período:"),
    	lbl_ate = new JLabel("até"), lbl_grupo = new JLabel("Grupo:"), lbl_nivel = new JLabel("Nível:"),
    	lbl_total = new JLabel("TOTAL: "), lbl_placeholder = new JLabel("R$Placholder/R$Placeholder"),
    	lbl_situacao = new JLabel("SITUAÇÃO:"), lbl_placeholder2 = new JLabel("Placholders conferidos");
    	
    	
    	
    
    	String[] grupos= {"Grupo 1", "Grupo 2"};
    	String[] niveis = {"Nível 1", "Nível 2"};

    	JComboBox <String> grupo = new JComboBox<>(grupos);
    	JComboBox <String> nivel = new JComboBox<>(niveis);
    	JCheckBox conta_movimento = new JCheckBox("Exibir contas sem movimento");
    	JButton balancete = new JButton("Gerar Balancete"), limpar = new JButton("Limpar"), 
    	exportar = new JButton("Exportar"), imprimir = new JButton("Imprimir");
    	//Adicionar Jchkbox
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
    	adicionar_componente(2, painel_pesquisa, organizador_pesquisa, lbl_grupo, grupo);
    	adicionar_componente(3, painel_pesquisa, organizador_pesquisa, lbl_nivel, nivel);
    	organizador_pesquisa.gridx = 1;
    	organizador_pesquisa.gridy = 4;
    	organizador_pesquisa.weightx = 0;
    	organizador_pesquisa.fill = GridBagConstraints.NONE;
    	organizador_pesquisa.gridwidth = 1;
    	painel_pesquisa.add(conta_movimento, organizador_pesquisa);
    	
    	botoes_pesquisa.add(balancete);
    	botoes_pesquisa.add(limpar);
    	botoes_pesquisa.add(exportar);
    	botoes_pesquisa.add(imprimir);
    	
    	organizador_pesquisa.gridy = 5;
    	organizador_pesquisa.gridx = 1;
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
  
    	
    	TabelaBalancete = new JTable(modeloTabela);
        JScrollPane scrollPane =new JScrollPane(TabelaBalancete);
        organizador_lancamento.gridx = 0;
        organizador_lancamento.gridy = 0;
        organizador_lancamento.weightx = 1;
        organizador_lancamento.weighty = 1;
        organizador_lancamento.gridwidth = 1;
        organizador_lancamento.fill = GridBagConstraints.BOTH;
        painel_lancamento.add(scrollPane, organizador_lancamento);
        
        
        painel_dados.add(lbl_total);
        painel_dados.add(lbl_placeholder);
        painel_dados.add(lbl_situacao);
        painel_dados.add(lbl_placeholder2);
        
        organizador_lancamento.gridx = 0;
        organizador_lancamento.gridy = 1;
        organizador_lancamento.weightx = 0;
        organizador_lancamento.weighty = 0;
        organizador_lancamento.gridwidth = 0;
        organizador_lancamento.fill = GridBagConstraints.BOTH;
        painel_lancamento.add(painel_dados, organizador_lancamento);
         
    	organizador_principal.gridy = 2;
    	organizador_principal.weighty = 0;
    	organizador_principal.fill = GridBagConstraints.BOTH;
    	organizador_principal.weighty = 1;

    	
    	organizador_principal.gridy = 3;
    	organizador_principal.weighty = 0;
    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
    	organizador_principal.weightx = 1;

    	 	
        add(painel_pesquisa, BorderLayout.NORTH);
        add(painel_lancamento, BorderLayout.CENTER);
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
