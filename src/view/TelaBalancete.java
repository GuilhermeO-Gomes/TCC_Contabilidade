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
	private final JTextField txt_conta = new JTextField(15);
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
    	
    
    	GridBagConstraints gbc_principal = new GridBagConstraints();
    	gbc_principal.insets = new Insets(10, 2, 10, 2);
    	gbc_principal.anchor = GridBagConstraints.WEST;
    	gbc_principal.weightx = 0;
    	JPanel jp_pesquisa = new JPanel(new GridBagLayout());
    	jp_pesquisa.setBorder(BorderFactory.createTitledBorder("Filtros e pesquisa"));
    	GridBagConstraints gbc_pesquisa = new GridBagConstraints();
    	gbc_pesquisa.insets = new Insets(5, 2, 5, 2);
    	gbc_pesquisa.anchor = GridBagConstraints.WEST;
    	gbc_pesquisa.weightx = 0;
    	JPanel jp_lancamento = new JPanel(new GridBagLayout());
    	jp_lancamento.setBorder(BorderFactory.createTitledBorder("Lançamentos da conta"));
    	GridBagConstraints gbc_lancamento = new GridBagConstraints();
    	gbc_lancamento.insets = new Insets(10, 2, 10, 2);
    	gbc_lancamento.anchor = GridBagConstraints.WEST;
    	gbc_lancamento.weightx = 0;
    	JPanel botoes_pesquisa = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JPanel jp_dados = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	
    	JLabel lbl_conta = new JLabel("Conta:"), lbl_periodo = new JLabel("Período:"),
    	lbl_ate = new JLabel("até"), lbl_grupo = new JLabel("Grupo:"), lbl_nivel = new JLabel("Nível:"),
    	lbl_total = new JLabel("TOTAL: "), lbl_placeholder = new JLabel("R$Placholder/R$Placeholder"),
    	lbl_situacao = new JLabel("SITUAÇÃO:"), lbl_placeholder2 = new JLabel("Placholders conferidos");
    	
    	
    	
    
    	String[] obj_grupos= {"Grupo 1", "Grupo 2"};
    	String[] obj_niveis = {"Nível 1", "Nível 2"};

    	JComboBox <String> cmbBox_grupo = new JComboBox<>(obj_grupos);
    	JComboBox <String> cmbBox_nivel = new JComboBox<>(obj_niveis);
    	JCheckBox chkBox_conta_movimento = new JCheckBox("Exibir contas sem movimento");
    	JButton btn_balancete = new JButton("Gerar Balancete"), btn_limpar = new JButton("Limpar"), 
    	btn_exportar = new JButton("Exportar"), btn_imprimir = new JButton("Imprimir");
    	//Adicionar Jchkbox
    	adicionar_componente(0, jp_pesquisa, gbc_pesquisa, lbl_conta, txt_conta);
    	adicionar_componente(1, jp_pesquisa, gbc_pesquisa, lbl_periodo, periodo_1);
    	gbc_pesquisa.gridx = 2;
    	gbc_pesquisa.gridy = 1;
    	gbc_pesquisa.weightx = 0;
    	gbc_pesquisa.fill = GridBagConstraints.NONE;
    	jp_pesquisa.add(lbl_ate, gbc_pesquisa);

    	gbc_pesquisa.gridx = 3;
    	gbc_pesquisa.gridy = 1;
    	gbc_pesquisa.weightx = 1;
    	gbc_pesquisa.fill = GridBagConstraints.HORIZONTAL;
    	jp_pesquisa.add(periodo_2, gbc_pesquisa);
    	adicionar_componente(2, jp_pesquisa, gbc_pesquisa, lbl_grupo, cmbBox_grupo);
    	adicionar_componente(3, jp_pesquisa, gbc_pesquisa, lbl_nivel, cmbBox_nivel);
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
        
        
        jp_dados.add(lbl_total);
        jp_dados.add(lbl_placeholder);
        jp_dados.add(lbl_situacao);
        jp_dados.add(lbl_placeholder2);
        
        gbc_lancamento.gridx = 0;
        gbc_lancamento.gridy = 1;
        gbc_lancamento.weightx = 0;
        gbc_lancamento.weighty = 0;
        gbc_lancamento.gridwidth = 0;
        gbc_lancamento.fill = GridBagConstraints.BOTH;
        jp_lancamento.add(jp_dados, gbc_lancamento);
         
    	gbc_principal.gridy = 2;
    	gbc_principal.weighty = 0;
    	gbc_principal.fill = GridBagConstraints.BOTH;
    	gbc_principal.weighty = 1;

    	
    	gbc_principal.gridy = 3;
    	gbc_principal.weighty = 0;
    	gbc_principal.fill = GridBagConstraints.HORIZONTAL;
    	gbc_principal.weightx = 1;

    	 	
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
