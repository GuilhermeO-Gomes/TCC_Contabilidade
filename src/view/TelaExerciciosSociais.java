package view;


import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel
;


public class TelaExerciciosSociais extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTextField id = new JTextField(1),
			anoReferencia = new JTextField(28),
			dataInicio = new JTextField(28),
			dataFinal = new JTextField(28);
	private final JCheckBox ativo = new JCheckBox("Exercicio Social ativo", true);
	private final DefaultTableModel modelo = new DefaultTableModel(
			new Object[] {"ID", "Ano Referencia", "Data de inicio", "Data Final", "Ativo"},
			0
			) {public boolean isCellEditable (int l, int c) {
				return false;
			}
	};
	
	private final JTable tabela = new JTable(modelo);
	
	public TelaExerciciosSociais() {
		
		setLayout(new BorderLayout (8,8));
		setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		montar();
	}
	
	public void montar() {
		JPanel f = new JPanel(new GridBagLayout());
		f.setBorder(BorderFactory.createTitledBorder("Exercicios Sociais"));
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(4,4,4,4);
		componente(f,g,0, "Codigo", id);
		componente(f,g,1, "Ano de Referencia", anoReferencia);
		componente(f,g,2, "Data de inicio", dataInicio);
		componente(f,g,3, "Data final", dataFinal);
		g.gridx = 1;
		g.gridy = 5;
		f.add(ativo, g);
		JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton novo = new JButton("Novo"),
				salvar = new JButton("Salvar"),
				inativar = new JButton("Inativar"),
				limpar = new JButton("Limpar");
		
		b.add(novo);
		b.add(salvar);
		b.add(inativar);
		b.add(limpar);
		JPanel n = new JPanel(new BorderLayout());
		n.add(f);
		n.add(b, BorderLayout.SOUTH);
		add(n, BorderLayout.NORTH);
		JPanel c = new JPanel(new BorderLayout());
		c.add(new JScrollPane(tabela));
		add(c);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		
	}
	
	private void componente (
			 JPanel p,
			    GridBagConstraints g,
			    int y,
			    String r,
			    Component t
			) {
		 g.gridx = 0;
		    g.gridy = y;
		    g.weightx = 0;
		    g.fill = GridBagConstraints.NONE;
		    p.add(new JLabel(r), g);
		    g.gridx = 1;
		    g.weightx = 1;
		    g.fill = GridBagConstraints.HORIZONTAL;
		    p.add(t, g);
	}
	
	public void limpar() {
		id.setText("");
		anoReferencia.setText("");
		dataInicio.setText("");
		dataFinal.setText("");
		ativo.setSelected(true);
	}
	
	
	public void mostrar(Historico h) {
		id.setText(String.valueOf(h.getId()));
		anoReferencia.setText(h.getAnoReferencia());
		dataInicio.setText(h.getDataInicio());
		dataFinal.setText(h.getDataFinal());
		ativo.setSelected(h.isAtivo());
	}
	
	public void preencher(List<Historico> l) {
		int i;
		for(i=0; i < l.size(); i++) {
			Historico h = l.get(i);
			modelo.addRow(new Object[] {
					h.getDescricao(),
					h.isAtivo() ? "Sim" : "Não",
			});
			
		}
	}
	
	public int getId() {
	    try {
	      return Integer.parseInt(id.getText());
	    } catch (Exception e) {
	      return 0;
	    }
	  }
 public JTextField getTxtAnoReferencia() {
	    return anoReferencia;
	  }
 
 public JTextField getTxtDataInicio() {
	    return dataInicio;
	  }
 
 public JTextField getTxtDataFinal() {
	    return dataFinal;
	  }


	  public JCheckBox getChkAtivo() {
	    return ativo;
	  }

	  public JTable getTabela() {
	    return tabela;
	  }

	
	
}