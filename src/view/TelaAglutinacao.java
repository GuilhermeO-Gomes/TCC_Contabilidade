package view;


import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TelaAglutinacao extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTextField pesquisa = new JTextField(22);
		
		private final DefaultTableModel modelo =  new DefaultTableModel(
				new Object[] {"ID","Valor", "Descrição", "Observações", "ativo"},
				0
				) { public boolean isCellEditable (int l, int c) {
					return false;
				}
			};
			
		private final JTable tabela = new JTable(modelo);
		
		
		public TelaAglutinacao() {
			
			setLayout(new BorderLayout (8,8));
			setBorder(BorderFactory.createEmptyBorder (10,10,10,10));
			montar();
			
			
		}
		
		
		
		public void montar() {
			
			GridBagConstraints g = new GridBagConstraints();
			g.insets = new Insets(4,4,4,4);
			
			
			g.gridy = 1;
	
			JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));;
			JButton novo = new JButton("Novo"),
					editar = new JButton("Editar");

			
			
			novo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Window pai = SwingUtilities.getWindowAncestor(TelaAglutinacao.this);
					
					diaAglutinacao dialogo = new diaAglutinacao((Frame) pai, true);
					
					dialogo.setVisible(true);
				}
			});
			
			
			editar.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Window pai = SwingUtilities.getWindowAncestor(TelaAglutinacao.this);
					
					diaAglutinacao dialogo = new diaAglutinacao((Frame) pai, true);
					
					dialogo.setVisible(true);
				}
			});
			
			b.add(novo);
			b.add(editar);
			
			JPanel n = new JPanel(new BorderLayout());
			n.add(b, BorderLayout.SOUTH);
			add(n, BorderLayout.NORTH);
			
			b.add(new JLabel("Pesquisar:"));
			b.add(pesquisa);
			JButton buscar = new JButton("Buscar");
			
			 b.add(buscar);
			
			 JPanel c = new JPanel(new BorderLayout());
			 c.add(b, BorderLayout.NORTH);
			 c.add(new JScrollPane(tabela));
			 add(c);
			 tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			
			 
		}
		
		
		
		
	
		
		 
		 public JTextField getTxtPesquisa() {
			    return pesquisa;
			  }


			
			  public JTable getTabela() {
			    return tabela;
			  }
		
}