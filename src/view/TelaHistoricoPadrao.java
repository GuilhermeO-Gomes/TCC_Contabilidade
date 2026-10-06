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
			
			private final JButton novo =
		            new JButton("Novo"),
			salvar =new JButton("Salvar"),
			inativar =new JButton("Inativar"),
			limpar = new JButton("Limpar"),
			buscar = new JButton("Buscar");
			
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
			 
			 p.add(buscar);
			
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
		
		public void limparTabela() {
	        modelo.setRowCount(0);
	    }


	    public void adicionarLinha(
	            int id,
	            String descricao,
	            boolean ativo) {

	        modelo.addRow(
	                new Object[] {
	                        id,
	                        descricao,
	                        ativo
	                }
	        );
	    }

		
		
		
	    public int getId() {

	        try {
	            return Integer.parseInt(
	                    id.getText()
	            );

	        } catch (NumberFormatException e) {
	            return 0;
	        }
	    }


	    public String getDescricao() {
	        return descricao.getText();
	    }


	    public String getPesquisa() {
	        return pesquisa.getText();
	    }


	    public boolean isAtivo() {
	        return ativo.isSelected();
	    }


	    public JTable getTabela() {
	        return tabela;
	    }


	    public JButton getNovo() {
	        return novo;
	    }


	    public JButton getSalvar() {
	        return salvar;
	    }


	    public JButton getInativar() {
	        return inativar;
	    }


	    public JButton getLimpar() {
	        return limpar;
	    }


	    public JButton getBuscar() {
	        return buscar;
	    }



	    public void setId(int valor) {
	        id.setText(
	                String.valueOf(valor)
	        );
	    }


	    public void setDescricao(String valor) {
	        descricao.setText(valor);
	    }


	    public void setAtivo(boolean valor) {
	        ativo.setSelected(valor);
	    }
	}