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
import javax.swing.JTextField;

public class TelaAdicionarCustos extends JPanel {
	 private static final long serialVersionUID = 1L;
	    private final JTextField codigo = new JTextField(5), descricao = new JTextField(15);
	    public TelaAdicionarCustos() {
	    	setLayout(new BorderLayout(8,8));
	    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        montar_tela();
	    }

	    private void montar_tela() {
	    	JPanel painel_principal = new JPanel(new GridBagLayout());
	    	painel_principal.setBorder(BorderFactory.createTitledBorder("Nova Conta"));
	    	GridBagConstraints organizador_principal = new GridBagConstraints();
	    	organizador_principal.insets = new Insets(10, 2, 10, 2);
	    	organizador_principal.anchor = GridBagConstraints.WEST;
	    	organizador_principal.weightx = 0;
	    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    	JLabel lbl_codigo = new JLabel("Código:"), lbl_descricao = new JLabel("Descrição:"), 
	    	lbl_centro_superior = new JLabel("Centro Superior:"),
	    	lbl_situacao = new JLabel("Situação:");
	    
	    	String[] centros= {"Administrativo", "Operacional", "Comercial", "Financeiro", "Logística", "Tecnologia", "Recursos Humanos", 
	    			"Manutenção","Projetos", "Atendimento"};
	    	String[] situacoes = {"Ativo", "Inativo"};
	
	    	JComboBox <String> centro_superior = new JComboBox<>(centros);
	    	JComboBox <String> situacao = new JComboBox<>(situacoes);

	    	JButton salvar = new JButton("Salvar"), cancelar = new JButton("Cancelar");
	    	
	    	adicionar_componente(0, painel_principal, organizador_principal, lbl_codigo, codigo);
	    	adicionar_componente(1, painel_principal, organizador_principal, lbl_descricao, descricao);
	    	adicionar_componente(2, painel_principal, organizador_principal, lbl_centro_superior, centro_superior);
	    	adicionar_componente(3, painel_principal, organizador_principal, lbl_situacao, situacao);
	    	
	    	botoes.add(cancelar);
	    	botoes.add(salvar);
	    	
	    	organizador_principal.gridy = 7;
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridwidth = 2;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.weighty = 0;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(botoes, organizador_principal);
	    	
	        add(painel_principal, BorderLayout.NORTH);
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
