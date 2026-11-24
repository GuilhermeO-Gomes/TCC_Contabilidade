package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

public class TelaBalancete extends JPanel {
	private static final long serialVersionUID = 1L;
	private final DefaultTableModel modeloTabela = new DefaultTableModel(
    new Object[] {"Conta","Natureza(D/C)", "Saldo Anterior D", "Saldo Anterior C", "Movimentações D", "Movimentações C", "Saldo Atual D", "Saldo Atual C"}, 0);
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
    	
    
    	GridBagConstraints gbc_principal = new GridBagConstraints();
    	gbc_principal.insets = new Insets(10, 2, 10, 2);
    	gbc_principal.anchor = GridBagConstraints.WEST;
    	gbc_principal.weightx = 0;
    	JPanel jp_pesquisa = new JPanel(new GridBagLayout());
    	jp_pesquisa.setBorder(BorderFactory.createTitledBorder("Gerar Balancete"));
    	GridBagConstraints gbc_pesquisa = new GridBagConstraints();
    	gbc_pesquisa.insets = new Insets(5, 2, 5, 2);
    	gbc_pesquisa.anchor = GridBagConstraints.WEST;
    	gbc_pesquisa.weightx = 0;
    	JPanel jp_lancamento = new JPanel(new GridBagLayout());
    	jp_lancamento.setBorder(BorderFactory.createTitledBorder("Contas do Balancete"));
    	GridBagConstraints gbc_lancamento = new GridBagConstraints();
    	gbc_lancamento.insets = new Insets(10, 2, 10, 2);
    	gbc_lancamento.anchor = GridBagConstraints.WEST;
    	gbc_lancamento.weightx = 0;
    	JPanel botoes_pesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JPanel jp_dados = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JPanel jp_dados2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	
    	JLabel lbl_data_ini = new JLabel("Data inicial*:"), lbl_data_fim = new JLabel("Data Final*:"),
    	lbl_total_saldo_anterior_D = new JLabel("TOTAL SALDO ANTERIOR DEVEDOR:"), lbl_total_saldo_anterior_C = new JLabel("TOTAL SALDO ANTERIOR CREDOR:"),
    	lbl_total_movimentacoes_D = new JLabel("TOTAL MOVIMENTAÇÕES DE DÉBITO:"), lbl_total_movimentacoes_C = new JLabel ("TOTAL MOVIMENTAÇÕES DE CRÉDITO:"),
    	lbl_total_saldo_atual_D = new JLabel ("TOTAL SALDO ATUAL DEVEDOR:"), lbl_total_saldo_atual_C = new JLabel("TOTAL SALDO ATUAL CREDOR:"),
    	lbl_placeholder = new JLabel("R$Placholder"), lbl_placeholder2 = new JLabel("R$Placholder"),
    	lbl_situacao = new JLabel("SITUAÇÃO:"), lbl_placeholder3 = new JLabel("Placholders conferidos"),
    	lbl_placeholder4 = new JLabel("R$Placeholder"), 
    	lbl_placeholder5 = new JLabel("R$Placeholder"), lbl_placeholder6 = new JLabel("R$Placeholder"),
    	lbl_placeholder7 = new JLabel("R$Placeholder");
    	
    	JCheckBox chkBox_conta_movimento = new JCheckBox("Exibir contas sem movimento");
    	JButton btn_balancete = new JButton("Gerar Balancete"), btn_limpar = new JButton("Limpar"), 
    	btn_exportar = new JButton("Exportar"), btn_imprimir = new JButton("Imprimir");
    	//Adicionar Jchkbox
  
    	adicionar_componente(0, jp_pesquisa, gbc_pesquisa, lbl_data_ini, periodo_1);
    	adicionar_componente(1, jp_pesquisa, gbc_pesquisa, lbl_data_fim, periodo_2);

    	gbc_pesquisa.gridx = 1;
    	gbc_pesquisa.gridy = 4;
    	gbc_pesquisa.weightx = 0;
    	gbc_pesquisa.fill = GridBagConstraints.NONE;
    	gbc_pesquisa.gridwidth = 1;
    	jp_pesquisa.add(chkBox_conta_movimento, gbc_pesquisa);
    	
    	botoes_pesquisa.add(btn_balancete);
    	botoes_pesquisa.add(btn_limpar);
    	botoes_pesquisa.add(btn_exportar);
    	botoes_pesquisa.add(btn_imprimir);
    	
    	gbc_pesquisa.gridy = 5;
    	gbc_pesquisa.gridx = 1;
    	gbc_pesquisa.gridwidth = 2;
    	gbc_pesquisa.weightx = 1;
    	gbc_pesquisa.weighty = 0;
    	gbc_pesquisa.fill = GridBagConstraints.HORIZONTAL;
    	jp_pesquisa.add(botoes_pesquisa, gbc_pesquisa);
    	
    	gbc_principal.gridx = 0;
    	gbc_principal.gridy = 0;
    	gbc_principal.anchor = GridBagConstraints.WEST;
    	gbc_principal.weightx = 1;
    	gbc_principal.fill = GridBagConstraints.HORIZONTAL;
  
    	
    	TabelaBalancete = new JTable(modeloTabela);
        JScrollPane scrollPane =new JScrollPane(TabelaBalancete);
        gbc_lancamento.gridx = 0;
        gbc_lancamento.gridy = 0;
        gbc_lancamento.weightx = 1;
        gbc_lancamento.weighty = 1;
        gbc_lancamento.gridwidth = 1;
        gbc_lancamento.fill = GridBagConstraints.BOTH;
        jp_lancamento.add(scrollPane, gbc_lancamento);
        
        //Futuramente, trocar isso por uma row na tabela
        jp_dados.add(lbl_total_saldo_anterior_D);
        jp_dados.add(lbl_placeholder);
        jp_dados2.add(lbl_total_saldo_anterior_C);       
        jp_dados2.add(lbl_placeholder2);
        jp_dados.add(lbl_total_movimentacoes_D);
        jp_dados.add(lbl_placeholder4);
        jp_dados2.add(lbl_total_movimentacoes_C);
        jp_dados2.add(lbl_placeholder7);
        jp_dados.add(lbl_total_saldo_atual_D);
        jp_dados.add(lbl_placeholder5);
        jp_dados2.add(lbl_total_saldo_atual_C);
        jp_dados2.add(lbl_placeholder6);
        jp_dados.add(lbl_situacao);
        jp_dados.add(lbl_placeholder3);
        
        gbc_lancamento.gridx = 0;
        gbc_lancamento.gridy = 1;
        gbc_lancamento.weightx = 0;
        gbc_lancamento.weighty = 0;
        gbc_lancamento.gridwidth = 0;
        gbc_lancamento.fill = GridBagConstraints.BOTH;
        jp_lancamento.add(jp_dados, gbc_lancamento);
        
        gbc_lancamento.gridx = 0;
        gbc_lancamento.gridy = 2;
        gbc_lancamento.weightx = 0;
        gbc_lancamento.weighty = 0;
        gbc_lancamento.gridwidth = 0;
        gbc_lancamento.fill = GridBagConstraints.BOTH;
        jp_lancamento.add(jp_dados2, gbc_lancamento);
         
    

    	 	
        add(jp_pesquisa, BorderLayout.NORTH);
        add(jp_lancamento, BorderLayout.CENTER);
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
