package view;


import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TelaHistoricoPadrao extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTextField id = new JTextField(1),
		descricao = new JTextField(28),
		pesquisa = new JTextField(22);
		private final JCheckBox ativo = new JCheckBox("Historico Padrão ativo", true);
		private final DefaultTableModel modelo =  new DefaultTableModel(
				new Object[] {"ID", "Descrição", "Ativo"},
				0
				) { public boolean isCellEditable (int l, int c) {
					return false;
				}
			};
			
		private final JTable tabela = new JTable(modelo);
		
		
		public TelaHistoricoPadrao() {
			
			setLayout(new BorderLayout (8,8));
			setBorder(BorderFactory.createEmptyBorder (10,10,10,10));
			montar();
		}
				
		
		public void montar() {
			JPanel f = new JPanel(new GridBagLayout());
			f.setBorder(BorderFactory.createTitledBorder("Historicos Padrões no Sistema"));
			GridBagConstraints g = new GridBagConstraints();
			g.insets = new Insets(4,4,4,4);
			componente(f,g,0,"Codigo", id);
			componente(f,g,1,"Descrição", descricao);
			g.gridx = 1;
			g.gridy = 3;
			f.add(ativo, g);
			id.setEditable(false);
			JLabel aviso = new JLabel("");
			g.gridy = 4;
			f.add(aviso, g);
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
			JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
			p.add(new JLabel("Pesquisar:"));
			p.add(pesquisa);
			JButton buscar = new JButton("Buscar");
			 todos = new JButton ("Todos");
			 p.add(buscar);
			 p.add(todos);
			 JPanel c = new JPanel(new BorderLayout());
			 c.add(p, BorderLayout.NORTH);
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
			descricao.setText("");
			ativo.setSelected(true);
		}
		
		public void mostrar(Historico h) {
			id.setText(String.valueOf(h.getId()));
			descricao.setText(h.getDescricao());
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
		 public JTextField getTxtDescricao() {
			    return descricao;
			  }
		 
		 public JTextField getTxtPesquisa() {
			    return pesquisa;
			  }


			  public JCheckBox getChkAtivo() {
			    return ativo;
			  }

			  public JTable getTabela() {
			    return tabela;
			  }
		
}