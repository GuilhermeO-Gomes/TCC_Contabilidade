package view.DevGuilherme;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import controller.CentroCustosController;



public class TelaCentroCustos extends JPanel {
	    private static final long serialVersionUID = 1L;
	    private final JTextField txt_codigo = new JTextField(5), txt_descricao = new JTextField(15),
	    		txt_centro_superior = new JTextField(15);
	    private final DefaultTableModel modeloTabela = new DefaultTableModel(
	            new Object[] {"Código", "Descrição", "Nome", "Responsável", "Centro Superior", "Nível", "Situação"},
	            0
	    );

	    private JTable tabelaCentroCustos;
	    private CentroCustosController controller;

	    public TelaCentroCustos() {
	    	setLayout(new BorderLayout(8,8));
	    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        montar_tela();
	        controller = new CentroCustosController();

	    }

	    private void montar_tela() {
	    	JPanel jp_inferior = new JPanel(new BorderLayout(0,5));
	    	JPanel jp_form = new JPanel(new GridBagLayout());
	    	jp_form.setBorder(BorderFactory.createTitledBorder("Filtros e Pesquisa"));
	    	GridBagConstraints gbc_form = new GridBagConstraints();
	    	gbc_form.insets = new Insets(4, 4, 4, 4);
	    	gbc_form.anchor = GridBagConstraints.WEST;
	    	gbc_form.weightx = 0;
	    	JPanel botoes_filtro = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 5));
	    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    	JLabel lbl_codigo = new JLabel("Código:"), lbl_descricao = new JLabel("Descrição:"), 
	    	lbl_situacao = new JLabel("Situação:"), lbl_centro_superior = new JLabel("Centro Superior:");
	      	String[] obj_situacoes = {"Todas", "Ativo", "Inativo"};
	    	JComboBox <String> cmbBox_situacao = new JComboBox<>(obj_situacoes);
	    	JButton btn_pesquisar = new JButton("Pesquisar centro de custo"), btn_mostrar_todos = new JButton("Mostrar todos"),
	    	btn_criar_centro = new JButton("Adicionar centro de custo"), btn_atualizar_centro = new JButton("Atualizar centro de custo"),
	    	btn_inativar_centro = new JButton("Inativar centro"), btn_rateio = new JButton("Configurar Rateio"), 
	    	btn_razao = new JButton("Consultar razão"), btn_busca_centro = new JButton("Buscar Centro");
	    	
	    	adicionar_componente(1, jp_form, gbc_form, lbl_codigo, txt_codigo);
	    	adicionar_componente(2, jp_form, gbc_form, lbl_descricao, txt_descricao);
	    	adicionar_componente(3, jp_form, gbc_form, lbl_centro_superior, txt_centro_superior);
	    	gbc_form.gridx = 3;
	    	gbc_form.gridy = 3;
	    	gbc_form.weightx = 0;
	    	gbc_form.fill = GridBagConstraints.NONE;
	    	gbc_form.anchor = GridBagConstraints.WEST;
	    	
	    	btn_busca_centro.addActionListener(e -> {
	    		Window janela_pai = SwingUtilities.getWindowAncestor(this);
	    		controller.abrirBuscaCentro(janela_pai);
	    	});
	    	
	    	jp_form.add(btn_busca_centro, gbc_form);
	    	
	    	adicionar_componente(4, jp_form, gbc_form, lbl_situacao, cmbBox_situacao);
	    	
	    	botoes_filtro.add(btn_pesquisar);
	    	botoes_filtro.add(btn_mostrar_todos);
	    	
	    	gbc_form.gridx = 1;
	    	gbc_form.gridy = 6;
	    	gbc_form.weightx = 0;
	    	gbc_form.fill = GridBagConstraints.NONE;
	    	gbc_form.anchor = GridBagConstraints.WEST;
	    	jp_form.add(botoes_filtro, gbc_form);
	    	
	    	
	        tabelaCentroCustos = new JTable(modeloTabela);
	        JScrollPane scrollPane =
	                new JScrollPane(tabelaCentroCustos);

	        scrollPane.setBorder(
	                BorderFactory.createTitledBorder("Lista de centro de custo")
	        );
	        btn_criar_centro.addActionListener(e -> {
	        	Window janela_pai = SwingUtilities.getWindowAncestor(this);
	        	controller.abrirAdicionarCustos(janela_pai);
	        });
	        
	        btn_atualizar_centro.addActionListener(e -> {
	        	Window janela_pai = SwingUtilities.getWindowAncestor(this);
	        	controller.abrirAtualizarCustos(janela_pai);
	        	});
	        btn_rateio.addActionListener(e -> {
	        	Window janela_pai = SwingUtilities.getWindowAncestor(this);
	        	controller.abrirRateio(janela_pai);
	        });
	        btn_razao.addActionListener(e -> {
	        	Window janela_pai = SwingUtilities.getWindowAncestor(this);
	        	controller.abrirLivroRazao(janela_pai);
	        	});
	        
	        botoes.add(btn_criar_centro);
	        botoes.add(btn_atualizar_centro);
	        botoes.add(btn_inativar_centro);
	        botoes.add(btn_rateio);
	        botoes.add(btn_razao);
	        

	        jp_inferior.add(scrollPane, BorderLayout.CENTER);
	        jp_inferior.add(botoes, BorderLayout.NORTH);
	        add(jp_form, BorderLayout.NORTH);
	        add(jp_inferior, BorderLayout.CENTER);
	    }


	    public JTable getTabelaCentroCustos() {
	        return tabelaCentroCustos;
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
