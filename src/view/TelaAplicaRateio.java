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

public class TelaAplicaRateio extends JDialog {

	private static final long serialVersionUID = 1L;
	private final JTextField txt_conta = new JTextField(15), txt_codigo = new JTextField(15), 
			txt_nome = new JTextField(15);
	private final DefaultTableModel modeloTabela = new DefaultTableModel(
    new Object[] {"Código", "Nome do Rateio", "Descrição", "Critério de Destribuição", "Situação"}, 0);
	private JTable TabelaAplicaRateio;
            
    public TelaAplicaRateio(Window janela_pai) {
    	super(janela_pai, "Seleção de Rateios", ModalityType.APPLICATION_MODAL);
    	setLayout(new BorderLayout(8,8));
        montar_tela();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setLocationRelativeTo(janela_pai);
    }

    private void montar_tela() {
    	//Criar funções que criem automaticamente JPanels e GBCs, e JLabels
    	JPanel jp_selecao = new JPanel(new GridBagLayout());
    	jp_selecao.setBorder(BorderFactory.createTitledBorder("Pesquisa de Rateios"));
    	GridBagConstraints gbc_selecao = new GridBagConstraints();
    	gbc_selecao.insets = new Insets(5, 2, 5, 2);
    	gbc_selecao.anchor = GridBagConstraints.WEST;
    	gbc_selecao.weightx = 0;
    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	
    	JLabel lbl_conta = new JLabel("Conta:"), lbl_codigo = new JLabel("Código do Rateio*:"),
    			lbl_nome = new JLabel("Nome do Rateio*:");
    
    	JButton btn_aplicar = new JButton("Aplicar"), btn_pesquisar = new JButton("Pesquisar Rateio"), 
    	btn_cancelar = new JButton("Cancelar");
    	
    	txt_conta.setEnabled(false);
    	adicionar_componente(0, jp_selecao, gbc_selecao, lbl_conta, txt_conta);
    	adicionar_componente(1, jp_selecao, gbc_selecao, lbl_codigo,txt_codigo);
    	adicionar_componente(2, jp_selecao, gbc_selecao, lbl_nome, txt_nome);
    	gbc_selecao.gridx = 0;
    	gbc_selecao.gridy = 3;
    	gbc_selecao.weightx = 0;
    	gbc_selecao.fill = GridBagConstraints.NONE;
    	jp_selecao.add(btn_pesquisar, gbc_selecao);
    		    	
    	TabelaAplicaRateio = new JTable(modeloTabela);
        JScrollPane scrollPane =new JScrollPane(TabelaAplicaRateio);
    	
    	botoes.add(btn_cancelar);
    	botoes.add(btn_aplicar);
    	btn_cancelar.addActionListener(e -> dispose());
    	
    	add(jp_selecao, BorderLayout.NORTH);
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
