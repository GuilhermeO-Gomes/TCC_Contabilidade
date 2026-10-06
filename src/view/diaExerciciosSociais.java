package view;


import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.text.ParseException;


public class diaExerciciosSociais extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField id = new JTextField(5),
			anoReferencia = new  JTextField(4);
					 private final JFormattedTextField dataInicio =
			            criarCampoData();

			    private final JFormattedTextField dataFinal =
			            criarCampoData();
	private final JCheckBox ativo = new JCheckBox("Ativo", true);
	
	private final JButton salvar =
            new JButton("Salvar");
	
	
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
	     
	     b.add(salvar);
	     
	     JPanel n = new JPanel(new BorderLayout());
	     n.add(p);
	     n.add(b, BorderLayout.SOUTH);
	     add(n, BorderLayout.NORTH);
	}
	
	 private JFormattedTextField criarCampoData() {

	        try {

	            MaskFormatter mascara =
	                    new MaskFormatter(
	                            "##/##/####"
	                    );

	            mascara.setPlaceholderCharacter('_');

	            return new JFormattedTextField(
	                    mascara
	            );

	        } catch (ParseException e) {

	            e.printStackTrace();

	            return new JFormattedTextField();
	        }
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
	
	public String getId() {
        return id.getText();
    }

    public String getAnoReferencia() {
        return anoReferencia.getText();
    }

    public String getDataInicio() {
        return dataInicio.getText();
    }

    public String getDataFinal() {
        return dataFinal.getText();
    }

    public boolean isAtivo() {
        return ativo.isSelected();
    }

    public JButton getSalvar() {
        return salvar;
    }

    public void setId(String valor) {
        id.setText(valor);
    }

    public void setAnoReferencia(String valor) {
        anoReferencia.setText(valor);
    }

    public void setDataInicio(String valor) {
        dataInicio.setText(valor);
    }

    public void setDataFinal(String valor) {
        dataFinal.setText(valor);
    }

    public void setAtivo(boolean valor) {
        ativo.setSelected(valor);
    }
}