package view;


import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel
;


public class TelaExerciciosSociais extends JPanel {
	private static final long serialVersionUID = 1L;
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
		GridBagConstraints g = new GridBagConstraints();
		g.insets = new Insets(4,4,4,4);
		
		
		g.gridy = 1;

		JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JButton novo = new JButton("Novo"),
				editar = new JButton("Editar");

		
		
		novo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Window pai = SwingUtilities.getWindowAncestor(TelaExerciciosSociais.this);
				
				diaExerciciosSociais dialogo = new diaExerciciosSociais((Frame) pai, true);
				
				dialogo.setVisible(true);
			}
		});
		
		
		editar.addActionListener(new ActionListener() {
			 @Override
	            public void actionPerformed(ActionEvent e) {

	                int linhaSelecionada =
	                        tabela.getSelectedRow();

	                if (linhaSelecionada == -1) {

	                    JOptionPane.showMessageDialog(
	                            TelaExerciciosSociais.this,
	                            "Selecione um exercício social para editar.",
	                            "Aviso",
	                            JOptionPane.WARNING_MESSAGE
	                    );

	                    return;
	                }

	        
	                int linhaModelo =
	                        tabela.convertRowIndexToModel(
	                                linhaSelecionada
	                        );

	               
	                String id =
	                        modelo.getValueAt(linhaModelo, 0).toString();

	                String anoReferencia =
	                        modelo.getValueAt(linhaModelo, 1).toString();

	                String dataInicio =
	                        modelo.getValueAt(linhaModelo, 2).toString();

	                String dataFinal =
	                        modelo.getValueAt(linhaModelo, 3).toString();

	                boolean ativo =
	                        Boolean.parseBoolean(
	                                modelo.getValueAt(linhaModelo, 4).toString()
	                        );

	                Window pai =
	                        SwingUtilities.getWindowAncestor(
	                                TelaExerciciosSociais.this
	                        );

	                diaExerciciosSociais dialogo =
	                        new diaExerciciosSociais((Frame) pai, true);

	                // Preenchendo o diálogo
	                dialogo.setId(id);
	                dialogo.setAnoReferencia(anoReferencia);
	                dialogo.setDataInicio(dataInicio);
	                dialogo.setDataFinal(dataFinal);
	                dialogo.setAtivo(ativo);

	                dialogo.setVisible(true);
	            }
	        });
		
		b.add(novo);
		b.add(editar);
		
		JPanel n = new JPanel(new BorderLayout());
		n.add(b, BorderLayout.SOUTH);
		add(n, BorderLayout.NORTH);
		
		 JPanel c = new JPanel(new BorderLayout());
		 c.setBorder(BorderFactory.createTitledBorder("Tabela Exercícios Sociais"));
		 c.add(new JScrollPane(tabela));
		 add(c);
		 tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}
	
	
	
	

	



    public JTable getTabela() {
        return tabela;
    }

    public void limparTabela() {
        modelo.setRowCount(0);
    }

    public void adicionarLinha(
            int id,
            int anoReferencia,
            String dataInicio,
            String dataFinal,
            boolean ativo
    ) {

        modelo.addRow(
                new Object[] {
                        id,
                        anoReferencia,
                        dataInicio,
                        dataFinal,
                        ativo
                }
        );
    }
	
	
}