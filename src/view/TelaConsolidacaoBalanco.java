package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.sql.DataSource;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import view.TelaConsolidacaoBalanco.Opcao;


public class TelaConsolidacaoBalanco extends JPanel {
	
	private final JComboBox<Opcao<Long>> cbxGrupo = new JComboBox<>();
	
	private final JComboBox<String> cbxStatus = new JComboBox<>(new String[] {});
	
	private final JTextField txtDescricao = new JTextField(30),
			txtDataInicial= new JTextField(30),
			txtDataFinal= new JTextField(30);
	
	private final JCheckBox chkContaSaldo = new JCheckBox("Ignorar Contas Sem Saldo", false);
	
	private final JButton btnLimpar = new JButton("Limpar"),
		    btnEliminacoes = new JButton("Registrar eliminações"),
		    btnExecutar = new JButton("Executar consolidação");
	
	private final DefaultListModel<Opcao<Long>> modeloEmpresas =
            new DefaultListModel<>();
	
	private List<Opcao<Long>> empresasParticipantes =
            new ArrayList<>();
	
	private final JList<Opcao<Long>> listaEmpresas =
            new JList<>(modeloEmpresas);
	
	private final DefaultTableModel modeloTabela = new DefaultTableModel() {
		    public boolean isCellEditable(int l, int c) {
		      return false;
		   }
	};
	private final JTable tabela = new JTable(modeloTabela);
	
	public TelaConsolidacaoBalanco() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
        montarColunas(Collections.emptyList());
    }
	
	private void montar() {
		JPanel filtroPesquisa = new JPanel(new GridBagLayout());
        filtroPesquisa.setBorder(
      	      BorderFactory.createTitledBorder("Consolidar balanços")
      	    );
        
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 4, 3, 4);
        g.anchor = GridBagConstraints.WEST;
        
        adicionar(filtroPesquisa, g, 0, "Período", cbxGrupo);   
        adicionar(filtroPesquisa, g, 1, "Ano:", txtDescricao);
        adicionar(filtroPesquisa, g, 2, "Data Inicial:", txtDataInicial);
        adicionar(filtroPesquisa, g, 3, "Data Final:", txtDataFinal);
        adicionar(filtroPesquisa, g, 4, "Plano de Contas:", cbxStatus);
        g.gridx = 1;
        g.gridy = 6;
        filtroPesquisa.add(chkContaSaldo, g);     
        
        JPanel painelEmpresas = new JPanel(new BorderLayout());
        painelEmpresas.setBorder(BorderFactory.createTitledBorder(
                "Empresas participantes"));
        listaEmpresas.setVisibleRowCount(3);
        listaEmpresas.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        painelEmpresas.add(
                new JScrollPane(listaEmpresas),
                BorderLayout.CENTER);
        
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        botoes.add(btnLimpar);
        botoes.add(btnEliminacoes);
        botoes.add(btnExecutar);
        
        
        btnEliminacoes.addActionListener(e -> {
        	Window ConsolidacaoBalanco = SwingUtilities.getWindowAncestor(this);
        	TelaEliminacoes dialog = new TelaEliminacoes(ConsolidacaoBalanco);
            dialog.setVisible(true);
        });
        
        JPanel topo = new JPanel(new BorderLayout());
	    topo.add(filtroPesquisa, BorderLayout.NORTH);
	    topo.add(painelEmpresas);
	    topo.add(botoes, BorderLayout.SOUTH);
	    add(topo, BorderLayout.NORTH);
        
	    JPanel centro = new JPanel(new BorderLayout());
	    centro.add(new JScrollPane(tabela), BorderLayout.CENTER);
	    add(centro, BorderLayout.CENTER);
	    tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
	}
	
	private void adicionar(
            JPanel p,
            GridBagConstraints g,
            int y,
            String rotulo,
            JComponent componente
    ) {
        g.gridx = 0;
        g.gridy = y;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        p.add(new JLabel(rotulo), g);

        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        p.add(componente, g);
    }
	
	public static class Opcao<T> {

        private final T id;
        private final String descricao;

        public Opcao(T id, String descricao) {
            this.id = Objects.requireNonNull(id);
            this.descricao = Objects.requireNonNull(descricao);
        }

        public T getId() {
            return id;
        }

        public String getDescricao() {
            return descricao;
        }

        public String toString() {
            return descricao;
        }
    }
	
	private void montarColunas(
            List<Opcao<Long>> empresas) {

        modeloTabela.setRowCount(0);
        modeloTabela.setColumnCount(0);

        modeloTabela.addColumn("Código da rubrica");
        modeloTabela.addColumn("Descrição");
        modeloTabela.addColumn("Tipo");
        modeloTabela.addColumn("Natureza");

        for (Opcao<Long> empresa : empresas) {
            modeloTabela.addColumn(empresa.getDescricao());
        }

        modeloTabela.addColumn("Eliminações");
        modeloTabela.addColumn("Saldo consolidado");
    }
	
	public void setEmpresasParticipantes(
            List<Opcao<Long>> empresas) {

        empresasParticipantes = empresas == null
                ? new ArrayList<>()
                : new ArrayList<>(empresas);

        modeloEmpresas.clear();

        for (Opcao<Long> empresa : empresasParticipantes) {
            modeloEmpresas.addElement(empresa);
        }

        montarColunas(empresasParticipantes);
    }

}