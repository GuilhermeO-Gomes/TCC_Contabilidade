package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;


import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class TelaAdicionarCentro extends JDialog {
	private static final long serialVersionUID = 1L;
    private final JTextField txt_centro_custo = new JTextField(5), txt_percentual = new JTextField(15);
    public TelaAdicionarCentro(Window janela_pai) {
    	super(janela_pai, "Adicionar Novo Centro", ModalityType.APPLICATION_MODAL);
    	setLayout(new BorderLayout(8,8));
        montar_tela();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();                      
        setLocationRelativeTo(janela_pai);
    }

    private void montar_tela() {
    	JPanel jp_principal = new JPanel(new GridBagLayout());
    	jp_principal.setBorder(BorderFactory.createTitledBorder("Dados de distribuição"));
    	GridBagConstraints gbc_principal = new GridBagConstraints();
    	gbc_principal.insets = new Insets(10, 2, 10, 2);
    	gbc_principal.anchor = GridBagConstraints.WEST;
    	gbc_principal.weightx = 0;
    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JLabel lbl_codigo = new JLabel("Centro de Custo:"), lbl_descricao = new JLabel("Percentual(%):");
    	
    	JButton btn_adicionar = new JButton("Adicionar"), btn_cancelar = new JButton("Cancelar"), btn_buscar_centro = new JButton("Buscar Centro");
    	
    	btn_cancelar.addActionListener(e -> dispose());
    	btn_buscar_centro.addActionListener(e -> {
        	Window janela_pai = SwingUtilities.getWindowAncestor(this);
        	TelaBuscarCentro dialog = new TelaBuscarCentro(janela_pai);
            dialog.setVisible(true);});
    	
    	adicionar_componente(0, jp_principal, gbc_principal, lbl_codigo, txt_centro_custo);
    	gbc_principal.gridy = 0;
    	gbc_principal.gridx = 2;
    	gbc_principal.gridwidth = 0;
    	gbc_principal.weightx = 0;
    	gbc_principal.weighty = 0;
    	gbc_principal.fill = GridBagConstraints.HORIZONTAL;
    	jp_principal.add(btn_buscar_centro, gbc_principal);
    	adicionar_componente(1, jp_principal, gbc_principal, lbl_descricao, txt_percentual);   	
    	botoes.add(btn_cancelar);
    	botoes.add(btn_adicionar);
    	
    	gbc_principal.gridy = 7;
    	gbc_principal.gridx = 0;
    	gbc_principal.gridwidth = 2;
    	gbc_principal.weightx = 1;
    	gbc_principal.weighty = 0;
    	gbc_principal.fill = GridBagConstraints.HORIZONTAL;
    	jp_principal.add(botoes, gbc_principal);
    	
        add(jp_principal, BorderLayout.NORTH);
    }
public void adicionar_componente( 
	int y, 
	JPanel painel, 
	GridBagConstraints organizador, 
	JLabel lbl, 
	JComponent componente  
	) {
organizador.gridx = 0;
organizador.gridy = y;
organizador.weightx = 0;
organizador.fill = GridBagConstraints.NONE;
painel.add(lbl, organizador);

organizador.gridx = 1;
organizador.gridy = y;
organizador.weightx = 1;
organizador.fill = GridBagConstraints.HORIZONTAL;
painel.add(componente, organizador);
};	 
}
