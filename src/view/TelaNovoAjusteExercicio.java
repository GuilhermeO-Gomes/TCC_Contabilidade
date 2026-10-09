package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TelaNovoAjusteExercicio extends JDialog {
	
	private final JTextField txtDataAjuste = new JTextField(10),
			txtDocumentoReferencia = new JTextField(10),
			txtValor = new JTextField(10),
			txtHistorico = new JTextField(50);
	
	private final JComboBox<String> cbxExercicio = new JComboBox<>(new String[]{"Selecione um Exercício"}),
			cbxTipoAjuste = new JComboBox<>(new String[]{"Ajuste Contábil"}),
			cbxContaDebito = new JComboBox<>(new String[]{"Selecione um Conta"}),
			cbxContaCredito = new JComboBox<>(new String[]{"Selecione um Conta"});
	
	
	private final JButton btnGravar = new JButton("Gravar"),
			btnCancelar = new JButton("Cancelar");	
	
	public TelaNovoAjusteExercicio(Window AjusteExercicio) {
        setLayout(new BorderLayout(8, 8));
        montar();
        setMinimumSize(new Dimension(400, 300));
        setSize(400, 200);
        setLocationRelativeTo(null);
    }
	
	private void montar() {
	    JPanel novoAjuste = new JPanel(new GridBagLayout());

	    GridBagConstraints g = new GridBagConstraints();
	    g.insets = new Insets(3, 4, 3, 4);
	    g.anchor = GridBagConstraints.WEST;

	    adicionar(novoAjuste, g, 0, "Exercício:", cbxExercicio);
	    adicionar(novoAjuste, g, 1, "Data do Ajuste:", txtDataAjuste);
	    adicionar(novoAjuste, g, 2, "Tipo de Ajuste:", cbxTipoAjuste);
	    adicionar(novoAjuste, g, 3, "Documento/Referência:", txtDocumentoReferencia);
	    adicionar(novoAjuste, g, 4, "Conta Débito:", cbxContaDebito);
	    adicionar(novoAjuste, g, 5, "Conta Crédito:", cbxContaCredito);
	    adicionar(novoAjuste, g, 6, "Valor:", txtValor);
	    adicionar(novoAjuste, g, 7, "Histórico:", txtHistorico);
	    novoAjuste.add(btnGravar);
	    novoAjuste.add(btnCancelar);
	    btnCancelar.addActionListener(
	    		new ActionListener() {
	    	        public void actionPerformed(ActionEvent e) {
	    	          sair();
	    	        }
	    	      }
	    	    );
	    
	    add(novoAjuste);
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
	
	private void sair() {
	      dispose();
	    
	  }
}
	
	

