package view;

import javax.swing.*;
import java.awt.*;


public class diaBalancoPatrimonial extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField id = new JTextField(1),
			ordem = new JTextField(28),
			codigoAglutinacao = new JTextField(28),
			grupo = new JTextField(28),
			codigoAglutinacaoSuperior = new JTextField(28);
	private final JCheckBox ativo = new JCheckBox("Ativo", true);
	
	 private final JButton salvar =
	            new JButton("Salvar");
	
	
	public diaBalancoPatrimonial(Frame parent, boolean modal) {
		super(parent, "Inserir", modal);
		
		setSize(400,380);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		
	
		
		 JPanel p = new JPanel(new GridBagLayout());
		 p.setBorder(BorderFactory.createTitledBorder("Dados"));
	     GridBagConstraints g = new GridBagConstraints();
	     g.insets = new Insets(4,4,4,4);
	     
	     componente(p,g,0,"ID", id);
	     componente(p,g,1,"Ordem", ordem);
	     componente(p,g,2,"Código de Aglutinacao", codigoAglutinacao);
	     componente(p,g,3,"grupo", grupo);
	     componente(p,g,4,"Código de Aglutinação Superior", codigoAglutinacaoSuperior);
		
	     g.gridx = 1;
	     g.gridy =6;
	     p.add(ativo, g);
	     id.setEditable(false);
	     
	     JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    
	     
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
	public String getId() {
        return id.getText();
    }

    public String getOrdem() {
        return ordem.getText();
    }

    public String getCodigoAglutinacao() {
        return codigoAglutinacao.getText();
    }

    public String getGrupo() {
        return grupo.getText();
    }

    public String getCodigoAglutinacaoSuperior() {
        return codigoAglutinacaoSuperior.getText();
    }

    public boolean isAtivo() {
        return ativo.isSelected();
    }

    public JButton getSalvar() {
        return salvar;
    }


    // SETTERS

    public void setId(String valor) {
        id.setText(valor);
    }

    public void setOrdem(String valor) {
        ordem.setText(valor);
    }

    public void setCodigoAglutinacao(String valor) {
        codigoAglutinacao.setText(valor);
    }

    public void setGrupo(String valor) {
        grupo.setText(valor);
    }

    public void setCodigoAglutinacaoSuperior(String valor) {
        codigoAglutinacaoSuperior.setText(valor);
    }

    public void setAtivo(boolean valor) {
        ativo.setSelected(valor);
    }
}