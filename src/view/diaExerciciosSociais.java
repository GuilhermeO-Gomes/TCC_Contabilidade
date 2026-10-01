package view;


import javax.swing.*;
import java.awt.*;


public class diaExerciciosSociais extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField id = new JTextField(1),
			anoReferencia = new  JTextField(4),
			dataInicio = new JFormattedTextField(),
			dataFinal = new JFormattedTextField();
	private final JCheckBox ativo = new JCheckBox("Ativo", true);
	
	
	public diaExerciciosSociais(Frame parent, boolean modal) {
		super(parent, "Exercícios Sociais", modal);
		
		setSize(400,380);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		
	
		
		 JPanel p = new JPanel(new GridBagLayout());
		 p.setBorder(BorderFactory.createTitledBorder("Dados"));
	     GridBagConstraints g = new GridBagConstraints();
	     g.insets = new Insets(4,4,4,4);
	     
	     componente(p,g,0,"Código", id);
	     componente(p,g,1,"Ano de Referencia", anoReferencia);
	     componente(p,g,2,"Data de Inicio", dataInicio);
	     componente(p,g,3,"Data FInal", dataFinal);
		
	     g.gridx = 1;
	     g.gridy = 5;
	     p.add(ativo, g);
	     id.setEditable(false);
	     
	     JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
	     JButton salvar = new JButton("Salvar");
	     
	     b.add(salvar);
	     
	     JPanel n = new JPanel(new BorderLayout());
	     n.add(p);
	     n.add(b, BorderLayout.SOUTH);
	     add(n, BorderLayout.NORTH);
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
}