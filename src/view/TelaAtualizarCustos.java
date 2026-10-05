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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

public class TelaAtualizarCustos extends JDialog {
	private static final long serialVersionUID = 1L;
	SpinnerDateModel modelo_data= new SpinnerDateModel();
	JSpinner periodo_1 = new JSpinner(modelo_data);
	JSpinner.DateEditor editor_1 = new JSpinner.DateEditor(periodo_1, "dd/MM/yyyy");
	//Para pegar valores desses campos: periodo_1.getValue(); 
	{periodo_1.setEditor(editor_1);}
    private final JTextField txt_centro_custo = new JTextField(15), txt_custo_atual = new JTextField(15),
    txt_novo_custo = new JTextField(15), txt_variacao = new JTextField(15);
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
    	    new Object[] {"Data", "Período", "Custo Anterior", "Novo Custo", "Variação"}, 0);
    		private JTable TabelaHistorico;
    public TelaAtualizarCustos(Window janela_pai) {
    	super(janela_pai, "Atualizar Custo", ModalityType.APPLICATION_MODAL);
    	setLayout(new BorderLayout(8,8));
        montar_tela();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();                      
        setLocationRelativeTo(janela_pai);
    }

    private void montar_tela() {
    	JPanel jp_form = new JPanel(new GridBagLayout());
    	jp_form.setBorder(BorderFactory.createTitledBorder("Nova Conta"));
    	GridBagConstraints gbc_form = new GridBagConstraints();
    	gbc_form.insets = new Insets(10, 2, 10, 2);
    	gbc_form.anchor = GridBagConstraints.WEST;
    	gbc_form.weightx = 0;
    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JLabel lbl_centro_custo = new JLabel("Centro de Custo:"), lbl_periodo = new JLabel("Período:"), 
    	lbl_custo_atual = new JLabel("Custo Atual:"),lbl_novo_custo = new JLabel("Novo Custo:"), 
    	lbl_variacao = new JLabel("Variação"); 
    
    	
    	JButton btn_atualizar = new JButton("Atualizar"), btn_cancelar = new JButton("Cancelar"), 
    			btn_limpar = new JButton("Limpar");
    	
    	btn_cancelar.addActionListener(e -> dispose());
    	
    	adicionar_componente(0, jp_form, gbc_form, lbl_centro_custo, txt_centro_custo);
    	adicionar_componente(1, jp_form, gbc_form, lbl_periodo, periodo_1);
    	adicionar_componente(2, jp_form, gbc_form, lbl_custo_atual, txt_custo_atual);
    	adicionar_componente(3, jp_form, gbc_form, lbl_novo_custo, txt_novo_custo);
    	adicionar_componente(4, jp_form, gbc_form, lbl_variacao, txt_variacao);
 
    	botoes.add(btn_atualizar);
    	botoes.add(btn_limpar);
    	botoes.add(btn_cancelar);
    	
    	TabelaHistorico = new JTable(modeloTabela);
        JScrollPane scrollPane =new JScrollPane(TabelaHistorico);
        gbc_form.gridy = 5;
        gbc_form.gridx = 0;
        gbc_form.gridwidth = 2;
        jp_form.add(botoes, gbc_form);
    	
    	
    	
        add(jp_form, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
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
organizador.fill = GridBagConstraints.NONE;
painel.add(lbl, organizador);

organizador.gridx = 1;
organizador.gridy = y;
organizador.weightx = 1;
organizador.fill = GridBagConstraints.HORIZONTAL;
painel.add(componente, organizador);
};	    
}
