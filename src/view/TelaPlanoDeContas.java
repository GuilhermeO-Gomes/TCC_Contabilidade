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
    	GridBagConstraints organizador = new GridBagConstraints();
    	JPanel painel_formulario = new JPanel(new GridBagLayout());
    	JLabel lbl_codigo = new JLabel("Código:"), lbl_descricao = new JLabel("Descrição:"), 
    	titulo_filtro = new JLabel("Filtros/Pesquisa"), 
    	lbl_grupo = new JLabel("Grupo:"), 
    	lbl_natureza = new JLabel("Natureza:"), 
    	lbl_situacao = new JLabel("Situação:");
    	String[] grupos= {"Ativos", "Passivos", "Custos", "Receita", "Despesa", "Patrimônio liquído"};
    	JComboBox <String> grupo = new JComboBox<>(grupos);
    	String[] naturezas = {"Devedora", "Credora"};
    	JComboBox <String> natureza = new JComboBox<>(naturezas);
    	String[] situacoes = {"Ativo", "Inativo"};
    	JComboBox <String> situacao = new JComboBox<>(situacoes);
    	
    	organizador.gridx = 0;
    	organizador.gridy = 0;
    	painel_formulario.add(titulo_filtro, organizador);
    	add(painel_formulario, BorderLayout.NORTH);
    	
    	organizador.insets = new Insets(4, 4, 4, 4);
    	organizador.anchor = GridBagConstraints.WEST;
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