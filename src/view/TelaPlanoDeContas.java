package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
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
    	//Aqui começa a ter tela
    	//Grid é layout flexível
    	JPanel painel_principal = new JPanel(new GridBagLayout());
    	painel_principal.setBorder(BorderFactory.createTitledBorder("Plano de Contas"));
    	GridBagConstraints organizador_form = new GridBagConstraints();
    	//Cima, esquerda, baixo, direita
    	organizador_form.insets = new Insets(4, 4, 4, 4);
    	organizador_form.anchor = GridBagConstraints.WEST;
    	organizador_form.weightx = 0;
    	JPanel painel_formulario = new JPanel(new GridBagLayout());
    	painel_formulario.setBorder(BorderFactory.createTitledBorder("Filtros e Pesquisa"));
    	JPanel filtros = new JPanel(new FlowLayout(FlowLayout.LEFT,4,2));
    	JLabel lbl_codigo = new JLabel("Código:"), lbl_descricao = new JLabel("Descrição:"), 
    	lbl_grupo = new JLabel("Grupo:"), 
    	lbl_natureza = new JLabel("Natureza:"), 
    	lbl_situacao = new JLabel("Situação:");
    	String[] grupos= {"Ativos", "Passivos", "Custos", "Receita", "Despesa", "Patrimônio liquído"};
    	JComboBox <String> grupo = new JComboBox<>(grupos);
    	String[] naturezas = {"Devedora", "Credora"};
    	JComboBox <String> natureza = new JComboBox<>(naturezas);
    	String[] situacoes = {"Ativo", "Inativo"};
    	JComboBox <String> situacao = new JComboBox<>(situacoes);
    	
    	
    	organizador_form.gridx = 0;
    	organizador_form.gridy = 1;
    	
    	painel_formulario.add(lbl_codigo, organizador_form);
    	
    	organizador_form.gridx = 1;
    	organizador_form.gridy = 1;
    	organizador_form.weightx = 1;
    	organizador_form.fill = GridBagConstraints.HORIZONTAL;
    	painel_formulario.add(codigo, organizador_form);
    	
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
    	//Fazer campos grandes para todos e esquecer FlowLayout
    	filtros.add(lbl_grupo);
    	filtros.add(grupo);
    	filtros.add(lbl_natureza);
    	filtros.add(natureza);
    	filtros.add(lbl_situacao);
    	filtros.add(situacao);
    	
    	organizador_form.gridx = 1;
    	organizador_form.gridy = 3;
    	organizador_form.weightx = 1;
    	//Este componente ocupa 2 colunas
    	organizador_form.gridwidth = 2;
    	organizador_form.fill = GridBagConstraints.HORIZONTAL;
    	painel_formulario.add(filtros, organizador_form);
    	add(painel_formulario, BorderLayout.NORTH);
    	
    	
    	//Fazer JPanel para todo o container de filtro/pesquisa
        tabelaPlanoDeContas = new JTable(modeloTabela);
        JScrollPane scrollPane =
                new JScrollPane(tabelaPlanoDeContas);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Lista de Contas")
        );

        add(scrollPane, BorderLayout.CENTER);
    }


    public JTable getTabelaPlanoDeContas() {
        return tabelaPlanoDeContas;
    }
}