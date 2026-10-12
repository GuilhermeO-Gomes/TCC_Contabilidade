package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TelaAjusteExercicio extends JPanel {
	
	private final JTextField txtPeriodoInicial = new JTextField(10),
			txtPeriodoFinal = new JTextField(10);
	
	private final JComboBox<String> cbxExercicio = new JComboBox<>(new String[]{"Selecione um Exercício"}),
			cbxStatus = new JComboBox<>(new String[]{"Processado", "Pendente", "Cancelado"});
	
	
	private final JButton btnPesquisar = new JButton("Pesquisar"),
			btnLimpar = new JButton("Limpar"),
			btnNovo = new JButton("Novo"),
			btnAlterar = new JButton("Alterar");	
	
	private final DefaultTableModel modelo = new DefaultTableModel(
		    new Object[] { "Data", "Exercício", "Período", "Documento", "Conta Débito", "Conta Crédito", "Valor", "Histórico", "Status" },
		    0
	) {
		    public boolean isCellEditable(int l, int c) {
		      return false;
		   }
	};
	private final JTable tabela = new JTable(modelo);
	
	public TelaAjusteExercicio() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
    }
	
	private void montar() {
	    JPanel filtroPesquisa = new JPanel(new GridBagLayout());
	    filtroPesquisa.setBorder(
	        BorderFactory.createTitledBorder("Filtros de Pesquisa")
	    );

	    GridBagConstraints g = new GridBagConstraints();
	    g.insets = new Insets(3, 4, 3, 4);
	    g.anchor = GridBagConstraints.WEST;

	    adicionar(filtroPesquisa, g, 0, "Exercício:", cbxExercicio);
	    JPanel periodo = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
	    periodo.add(txtPeriodoInicial);
	    periodo.add(new JLabel("até"));
	    periodo.add(txtPeriodoFinal);
	    adicionar(filtroPesquisa, g, 1, "Período:", periodo);
	    adicionar(filtroPesquisa, g, 2, "Status:", cbxStatus);

	    JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    botoes.add(btnPesquisar);
	    botoes.add(btnLimpar);
	    botoes.add(btnNovo);
	    botoes.add(btnAlterar);
	    btnNovo.addActionListener(e -> {
        	Window AjusteExercicio = SwingUtilities.getWindowAncestor(this);
        	TelaNovoAjusteExercicio dialog = new TelaNovoAjusteExercicio(AjusteExercicio);
            dialog.setVisible(true);
        });

	    JPanel topo = new JPanel(new BorderLayout());
	    topo.add(filtroPesquisa, BorderLayout.CENTER);
	    topo.add(botoes, BorderLayout.SOUTH);
	    add(topo, BorderLayout.NORTH);

	    JPanel centro = new JPanel(new BorderLayout());
	    centro.add(new JScrollPane(tabela), BorderLayout.CENTER);
	    add(centro, BorderLayout.CENTER);

	    tabela.setSelectionMode(
	        javax.swing.ListSelectionModel.SINGLE_SELECTION
	    );
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
	
	

}