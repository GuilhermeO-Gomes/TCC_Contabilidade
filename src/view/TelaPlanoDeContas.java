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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.PlanoContasController;

public class TelaPlanoDeContas extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JTextField codigo = new JTextField(5), descricao = new JTextField(15);
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
            new Object[] {"Código", "Descrição", "Conta Superior", "Nível", "Grupo", "Natureza", "Tipo da Conta", "Situação"
            },
            0
    );

    private JTable tabelaPlanoDeContas;
    private PlanoContasController controller;

    public TelaPlanoDeContas() {
    	//Ver o que essas telas fazem no sentido de layout e painel
    	
    	setLayout(new BorderLayout(8,8));
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar_tela();
        controller = new PlanoContasController(this);
        controller.carregarTabela();
    }

    private void montar_tela() {
    	JPanel painel_principal = new JPanel(new GridBagLayout());
    	GridBagConstraints organizador_principal = new GridBagConstraints();
    	JPanel painel_formulario = new JPanel(new GridBagLayout());
    	painel_formulario.setBorder(BorderFactory.createTitledBorder("Filtros e Pesquisa"));
    	GridBagConstraints organizador_form = new GridBagConstraints();
    	organizador_form.insets = new Insets(4, 4, 4, 4);
    	organizador_form.anchor = GridBagConstraints.WEST;
    	organizador_form.weightx = 0;
    	JPanel botoes_filtro = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 5));
    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
    	JLabel lbl_codigo = new JLabel("Código:"), lbl_descricao = new JLabel("Descrição:"), 
    	lbl_grupo = new JLabel("Grupo:"), 
    	lbl_natureza = new JLabel("Natureza:"), 
    	lbl_situacao = new JLabel("Situação:");
    	String[] grupos= {"Ativos", "Passivos", "Custos", "Receita", "Despesa", "Patrimônio liquído"};
    	String[] naturezas = {"Devedora", "Credora"};
      	String[] situacoes = {"Ativo", "Inativo"};
    	JComboBox <String> grupo = new JComboBox<>(grupos);
    	JComboBox <String> natureza = new JComboBox<>(naturezas);
    	JComboBox <String> situacao = new JComboBox<>(situacoes);
    	JButton pesquisar = new JButton("Pesquisar conta"), mostrar_todos = new JButton("Mostrar todos"),
    	criar_conta = new JButton("Adicionar conta"), atualizar_conta = new JButton("Atualizar conta"),
    	inativar_conta = new JButton("Inativar conta");
    	
    	adicionar_componente(1, painel_formulario, organizador_form, lbl_codigo, codigo);
    	adicionar_componente(2, painel_formulario, organizador_form, lbl_descricao, descricao);
    	adicionar_componente(3, painel_formulario, organizador_form, lbl_grupo, grupo);
    	adicionar_componente(4, painel_formulario, organizador_form, lbl_natureza, natureza);
    	adicionar_componente(5, painel_formulario, organizador_form, lbl_situacao, situacao);
    	
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
    }


    public JTable getTabelaPlanoDeContas() {
        return tabelaPlanoDeContas;
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