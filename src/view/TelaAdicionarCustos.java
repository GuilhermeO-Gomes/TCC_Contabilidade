package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaAdicionarCustos extends JDialog {
	 private static final long serialVersionUID = 1L;
	    private final JTextField txt_codigo = new JTextField(5), txt_descricao = new JTextField(15);
	    public TelaAdicionarCustos(Window janela_pai) {
	    	super(janela_pai, "Adicionar Novo Custo", ModalityType.APPLICATION_MODAL);
	    	setLayout(new BorderLayout(8,8));
	        montar_tela();
	        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	        pack();                      
	        setLocationRelativeTo(janela_pai);
	    }

	    private void montar_tela() {
	    	JPanel jp_principal = new JPanel(new GridBagLayout());
	    	jp_principal.setBorder(BorderFactory.createTitledBorder("Novo Custo"));
	    	GridBagConstraints gbc_principal = new GridBagConstraints();
	    	gbc_principal.insets = new Insets(10, 2, 10, 2);
	    	gbc_principal.anchor = GridBagConstraints.WEST;
	    	gbc_principal.weightx = 0;
	    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    	JLabel lbl_codigo = new JLabel("Código:"), lbl_descricao = new JLabel("Descrição:"), 
	    	lbl_centro_superior = new JLabel("Centro Superior:"),lbl_situacao = new JLabel("Situação:");
	    
	    	String[] obj_centros= {"Todos", "Administrativo", "Operacional", "Comercial", "Financeiro", "Logística", "Tecnologia", "Recursos Humanos", 
	    			"Manutenção","Projetos", "Atendimento"};
	    	String[] obj_situacoes = {"Todos", "Ativo", "Inativo"};
	
	    	JComboBox <String> cmbBox_centro_superior = new JComboBox<>(obj_centros);
	    	JComboBox <String> cmbBox_situacao = new JComboBox<>(obj_situacoes);

	    	JButton btn_salvar = new JButton("Salvar"), btn_cancelar = new JButton("Cancelar");
	    	
	    	btn_cancelar.addActionListener(e -> dispose());
	    	
	    	adicionar_componente(0, jp_principal, gbc_principal, lbl_codigo, txt_codigo);
	    	adicionar_componente(1, jp_principal, gbc_principal, lbl_descricao, txt_descricao);
	    	adicionar_componente(2, jp_principal, gbc_principal, lbl_centro_superior, cmbBox_centro_superior);
	    	adicionar_componente(3, jp_principal, gbc_principal, lbl_situacao, cmbBox_situacao);
	    	
	    	botoes.add(btn_cancelar);
	    	botoes.add(btn_salvar);
	    	
	    	
	    	gbc_principal.gridy = 7;
	    	gbc_principal.gridx = 0;
	    	gbc_principal.gridwidth = 2;
	    	gbc_principal.weightx = 1;
	    	gbc_principal.weighty = 0;
	    	gbc_principal.fill = GridBagConstraints.HORIZONTAL;
	    	jp_principal.add(botoes, gbc_principal);
	    	
	        add(jp_principal, BorderLayout.NORTH);
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
