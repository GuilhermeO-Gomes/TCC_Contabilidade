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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.AdicionarContaController;
import controller.PlanoContasController;

public class TelaAdicionarConta extends JPanel {

	    private static final long serialVersionUID = 1L;
	    private final JTextField codigo = new JTextField(5), descricao = new JTextField(15);
	    private AdicionarContaController controller;

	    public TelaAdicionarContas() {
	    	//Ver o que essas telas fazem no sentido de layout e painel
	    	
	    	setLayout(new BorderLayout(8,8));
	    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        montar_tela();
	        controller = new AdicionarContaController(this);
	    }

	    private void montar_tela() {
	    	//Aqui começa a ter tela
	    	//Grid é layout flexível
	    	JPanel painel_principal = new JPanel(new GridBagLayout());
	    	painel_principal.setBorder(BorderFactory.createTitledBorder("Nova Conta"));
	    	GridBagConstraints organizador_principal = new GridBagConstraints();
	    	//Cima, esquerda, baixo, direita
	    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
	    	JLabel lbl_codigo = new JLabel("Código:"), lbl_descricao = new JLabel("Descrição:"), 
	    	lbl_conta_superior = new JLabel("Conta Superior:"),
	    	lbl_grupo = new JLabel("Grupo: "),
	    	lbl_natureza = new JLabel("Natureza:"), 
	    	lbl_situacao = new JLabel("Situação:"),
	    	lbl_tipo = new JLabel("Tipo:");
	    	String[] grupos= {"Ativos", "Passivos", "Custos", "Receita", "Despesa", "Patrimônio liquído"};
	    	JComboBox <String> grupo = new JComboBox<>(grupos);
	    	String[] naturezas = {"Devedora", "Credora"};
	    	JComboBox <String> natureza = new JComboBox<>(naturezas);
	    	String[] situacoes = {"Ativo", "Inativo"};
	    	JComboBox <String> situacao = new JComboBox<>(situacoes);
	    	String[] tipos = {"Analítica", "Sintética"};
	    	JComboBox <String> tipo = new JComboBox<>(tipos);
	    	JButton salvar = new JButton("Salvar"), cancelar = new JButton("Cancelar");
	    	
	    	//Para o Guilherme de amanhã, tem que montar a tela, fazer o controller e melhorar o código
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridy = 1;
	    	painel_principal.add(lbl_codigo, organizador_principal);
	    	
	    	organizador_principal.gridx = 1;
	    	organizador_principal.gridy = 1;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(codigo, organizador_principal);
	    	
	    	organizador_form.gridx = 0;
	    	organizador_form.gridy = 2;
	    	organizador_form.weightx = 0;
	    	organizador_form.fill = GridBagConstraints.NONE;
	    	painel_formulario.add(lbl_descricao, organizador_form);
	    	
	    	organizador_form.gridx = 1;
	    	organizador_form.gridy = 2;
	    	organizador_form.weightx = 1;
	    	organizador_form.fill = GridBagConstraints.HORIZONTAL;
	    	painel_formulario.add(descricao, organizador_form);
	    	
	    	organizador_form.gridx = 0;
	    	organizador_form.gridy = 3;
	    	organizador_form.weightx = 0;
	    	organizador_form.fill = GridBagConstraints.NONE;
	    	painel_formulario.add(lbl_grupo, organizador_form);
	    	
	    	organizador_form.gridx = 1;
	    	organizador_form.gridy = 3;
	    	organizador_form.weightx = 1;
	    	organizador_form.fill = GridBagConstraints.HORIZONTAL;
	    	painel_formulario.add(grupo, organizador_form);
	    	
	    	organizador_form.gridx = 0;
	    	organizador_form.gridy = 4;
	    	organizador_form.weightx = 0;
	    	organizador_form.fill = GridBagConstraints.NONE;
	    	painel_formulario.add(lbl_natureza, organizador_form);
	    	
	    	organizador_form.gridx = 1;
	    	organizador_form.gridy = 4;
	    	organizador_form.weightx = 1;
	    	organizador_form.fill = GridBagConstraints.HORIZONTAL;
	    	painel_formulario.add(natureza, organizador_form);
	    	
	    	organizador_form.gridx = 0;
	    	organizador_form.gridy = 5;
	    	organizador_form.weightx = 0;
	    	organizador_form.fill = GridBagConstraints.NONE;
	    	painel_formulario.add(lbl_situacao, organizador_form);
	    	
	    	organizador_form.gridx = 1;
	    	organizador_form.gridy = 5;
	    	organizador_form.weightx = 1;
	    	organizador_form.fill = GridBagConstraints.HORIZONTAL;
	    	painel_formulario.add(situacao, organizador_form);
	    	
	    	botoes_filtro.add(pesquisar);
	    	botoes_filtro.add(mostrar_todos);
	    	
	    	
	    	organizador_form.gridx = 1;
	    	organizador_form.gridy = 6;
	    	organizador_form.weightx = 0;
	    	organizador_form.fill = GridBagConstraints.NONE;
	    	organizador_form.anchor = GridBagConstraints.WEST;
	    	painel_formulario.add(botoes_filtro, organizador_form);
	    	
	    	organizador_principal.gridx = 0;
	    	organizador_principal.gridy = 0;
	    	organizador_principal.anchor = GridBagConstraints.WEST;
	    	organizador_principal.weightx = 1;
	    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
	    	painel_principal.add(painel_formulario, organizador_principal);
	    	
	    	
	    	//Fazer JPanel para todo o container de filtro/pesquisa
	        tabelaPlanoDeContas = new JTable(modeloTabela);
	        JScrollPane scrollPane =
	                new JScrollPane(tabelaPlanoDeContas);

	        scrollPane.setBorder(
	                BorderFactory.createTitledBorder("Lista de Contas")
	        );
	        
	        organizador_principal.gridy = 1;
	    	organizador_principal.weighty = 1;
	    	organizador_principal.fill = GridBagConstraints.BOTH;
	        painel_principal.add(scrollPane, organizador_principal);
	        botoes.add(criar_conta);
	        botoes.add(atualizar_conta);
	        botoes.add(inativar_conta);
	        
	        organizador_principal.gridy = 2;
	        painel_principal.add(botoes, organizador_principal);
	        add(painel_principal);
	    }}


