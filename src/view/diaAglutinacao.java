package view;

import javax.swing.*;
import java.awt.*;


public class diaAglutinacao extends JDialog {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JTextField id = new JTextField(1),
			valor = new JTextField(28),
			descricao = new JTextField(28),
			observacao = new JTextField(28);
	private final JCheckBox ativo = new JCheckBox("Ativo", true);
	
	 private final JButton salvar = new JButton("Salvar");
	
	public diaAglutinacao(Frame parent, boolean modal) {
		super(parent, "código de aglutinação", modal);
		
		setSize(400,380);
		setLocationRelativeTo(parent);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		
		
	
		
		 JPanel p = new JPanel(new GridBagLayout());
		 p.setBorder(BorderFactory.createTitledBorder("Dados"));
	     GridBagConstraints g = new GridBagConstraints();
	     g.insets = new Insets(4,4,4,4);
	     
	     componente(p,g,0,"Código", id);
	     componente(p,g,1,"Valor", valor);
	     componente(p,g,2,"Descrição", descricao);
	     componente(p,g,3,"Observação", observacao);
		
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
	
	 public int getId() {
	        try {
	            return Integer.parseInt(id.getText());
	        } catch (NumberFormatException e) {
	            return 0;
	        }
	    }

	    public String getValor() {
	        return valor.getText();
	    }

	    public String getDescricao() {
	        return descricao.getText();
	    }

	    public String getObservacao() {
	        return observacao.getText();
	    }

	    public boolean isAtivo() {
	        return ativo.isSelected();
	    }

	    public JButton getSalvar() {
	        return salvar;
	    }

	    public void setId(int id) {
	        this.id.setText(String.valueOf(id));
	    }

	    public void setValor(String valor) {
	        this.valor.setText(valor);
	    }

	    public void setDescricao(String descricao) {
	        this.descricao.setText(descricao);
	    }

	    public void setObservacao(String observacao) {
	        this.observacao.setText(observacao);
	    }

	    public void setAtivo(boolean ativo) {
	        this.ativo.setSelected(ativo);
	    }
}