package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class TelaAdicionarConta extends JPanel {

	    private static final long serialVersionUID = 1L;
	    private final JTextField codigo = new JTextField(5), descricao = new JTextField(15);
	    public TelaAdicionarConta() {
	    	//Ver o que essas telas fazem no sentido de layout e painel
	    	setLayout(new BorderLayout(8,8));
	    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        montar_tela();
	    }

	    private void montar_tela() {
	    	//Aqui começa a ter tela
	    	//Grid é layout flexível
	    	JPanel painel_principal = new JPanel(new GridBagLayout());
	    	painel_principal.setBorder(BorderFactory.createTitledBorder("Nova Conta"));
	    	GridBagConstraints organizador_principal = new GridBagConstraints();
	    	//Cima, esquerda, baixo, direita
	    	organizador_principal.insets = new Insets(10, 2, 10, 2);
	    	organizador_principal.anchor = GridBagConstraints.WEST;
	    	organizador_principal.weightx = 0;
	    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    	JLabel lbl_codigo = new JLabel("Código:"), lbl_descricao = new JLabel("Descrição:"), 
	    	lbl_conta_superior = new JLabel("Conta Superior:"),
	    	lbl_grupo = new JLabel("Grupo: "),
	    	lbl_natureza = new JLabel("Natureza:"), 
	    	lbl_situacao = new JLabel("Situação:"),
	    	lbl_tipo = new JLabel("Tipo:");
	    	String[] grupos= {"Ativos", "Passivos", "Custos", "Receita", "Despesa", "Patrimônio liquído"};
	    	JComboBox <String> grupo = new JComboBox<>(grupos);
	    	JComboBox <String> conta_superior = new JComboBox<>(grupos);
	    	String[] naturezas = {"Devedora", "Credora"};
	    	JComboBox <String> natureza = new JComboBox<>(naturezas);
	    	String[] situacoes = {"Ativo", "Inativo"};
	    	JComboBox <String> situacao = new JComboBox<>(situacoes);
	    	String[] tipos = {"Analítica", "Sintética"};
	    	JComboBox <String> tipo = new JComboBox<>(tipos);
	    	JButton salvar = new JButton("Salvar"), cancelar = new JButton("Cancelar");
	    	
	    	//Para o Guilherme de amanhã, tem que montar a tela, fazer o controller e melhorar o código
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridy = 0;
	    	organizador_principal.weightx = 0;
	    	organizador_principal.fill = GridBagConstraints.NONE;
	    	painel_principal.add(lbl_codigo, organizador_principal);
	    	
	    	organizador_principal.gridx = 1;
	    	organizador_principal.gridy = 0;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(codigo, organizador_principal);
	    	
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridy = 1;
	    	organizador_principal.weightx = 0;
	    	organizador_principal.fill = GridBagConstraints.NONE;
	    	painel_principal.add(lbl_descricao, organizador_principal);
	    	
	    	organizador_principal.gridx = 1;
	    	organizador_principal.gridy = 1;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(descricao, organizador_principal);
	    	
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridy = 2;
	    	organizador_principal.weightx = 0;
	    	organizador_principal.fill = GridBagConstraints.NONE;
	    	painel_principal.add(lbl_conta_superior, organizador_principal);
	    	
	    	organizador_principal.gridx = 1;
	    	organizador_principal.gridy = 2;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(conta_superior, organizador_principal);
	    	
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridy = 3;
	    	organizador_principal.weightx = 0;
	    	organizador_principal.fill = GridBagConstraints.NONE;
	    	painel_principal.add(lbl_grupo, organizador_principal);
	    	
	    	organizador_principal.gridx = 1;
	    	organizador_principal.gridy = 3;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(grupo, organizador_principal);
	    	
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridy = 4;
	    	organizador_principal.weightx = 0;
	    	organizador_principal.fill = GridBagConstraints.NONE;
	    	painel_principal.add(lbl_natureza, organizador_principal);
	    	
	    	organizador_principal.gridx = 1;
	    	organizador_principal.gridy = 4;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(natureza, organizador_principal);
	    	
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridy = 5;
	    	organizador_principal.weightx = 0;
	    	organizador_principal.fill = GridBagConstraints.NONE;
	    	painel_principal.add(lbl_tipo, organizador_principal);
	    	
	    	organizador_principal.gridx = 1;
	    	organizador_principal.gridy = 5;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(tipo, organizador_principal);
	    	
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridy = 6;
	    	organizador_principal.weightx = 0;
	    	organizador_principal.fill = GridBagConstraints.NONE;
	    	painel_principal.add(lbl_situacao, organizador_principal);
	    	
	    	organizador_principal.gridx = 1;
	    	organizador_principal.gridy = 6;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(situacao, organizador_principal);
	    	
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
	    }}


