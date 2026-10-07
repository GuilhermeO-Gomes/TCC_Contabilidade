package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class TelaRateio extends JDialog {
	private static final long serialVersionUID = 1L;
	private final JTextField txt_nome_rateio = new JTextField(15);
	private final JTextField txt_conta_contabil = new JTextField(15);
	private final DefaultTableModel modeloTabela = new DefaultTableModel(
    new Object[] {"Centro de Custo", "Percentual(%)" }, 0);
	private JTable TabelaRateio;
            
    public TelaRateio(Window janela_pai) {
    	super(janela_pai, "Rateio", ModalityType.APPLICATION_MODAL);
    	setLayout(new BorderLayout(8,8));
        montar_tela();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(850, 600));
        setMinimumSize(new Dimension(700, 500)); 
        pack();
        setLocationRelativeTo(janela_pai);
    }

    private void montar_tela() {
    	//Criar funções que criem automaticamente JPanels e GBCs, e JLabels
    	JPanel jp_config = new JPanel(new GridBagLayout());
    	jp_config.setBorder(BorderFactory.createTitledBorder("Configuração de Rateio"));
    	GridBagConstraints gbc_config = new GridBagConstraints();
    	gbc_config.insets = new Insets(5, 2, 5, 2);
    	gbc_config.anchor = GridBagConstraints.WEST;
    	gbc_config.weightx = 0;
    	JPanel jp_distribuicao = new JPanel(new GridBagLayout());
    	jp_distribuicao.setBorder(BorderFactory.createTitledBorder("Distribuição"));
    	GridBagConstraints gbc_distribuicao = new GridBagConstraints();
    	gbc_distribuicao.insets = new Insets(5, 2, 5, 2);
    	gbc_distribuicao.anchor = GridBagConstraints.WEST;
    	gbc_distribuicao.weightx = 0;
    	JPanel botoes_distribuicao = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	
    	
    	JLabel lbl_nome_rateio = new JLabel("Nome do Rateio:"), lbl_conta_contabil = new JLabel("Conta Contábil:"),
    	lbl_criterio = new JLabel("Critério:"), lbl_situacao = new JLabel("Situação:"), lbl_total_percentual = new JLabel("Total Percentual(%): 100%");
    
    	
    	String[] obj_criterios= {"Critério 1", "Critério 2"};
    	String[] obj_situacoes = {"Situação 1", "Situação 2"};

    	JComboBox <String> criterio = new JComboBox<>(obj_criterios);
    	JComboBox <String> situacao = new JComboBox<>(obj_situacoes);
    	JButton btn_adicionar_centro = new JButton("Adicionar Centro"), btn_atualizar = new JButton("Atualizar"), 
    	btn_remover = new JButton("Remover"), btn_salvar = new JButton("Salvar"), btn_limpar = new JButton("Limpar"), 
    	btn_cancelar = new JButton("Cancelar"), btn_buscar_conta1 = new JButton ("Buscar Conta");
    	adicionar_componente(0, jp_config, gbc_config, lbl_nome_rateio, txt_nome_rateio);
    	adicionar_componente(1, jp_config, gbc_config, lbl_conta_contabil, txt_conta_contabil);
    	
    	gbc_config.gridx = 2;
    	gbc_config.gridy = 1;
    	gbc_config.weightx = 0;
    	gbc_config.fill = GridBagConstraints.NONE;
    	jp_config.add(btn_buscar_conta1, gbc_config);
    	
    	adicionar_componente(2, jp_config, gbc_config, lbl_criterio, criterio);
    	adicionar_componente(3, jp_config, gbc_config, lbl_situacao, situacao);
    	
    	botoes_distribuicao.add(btn_adicionar_centro);
    	botoes_distribuicao.add(btn_atualizar);
    	botoes_distribuicao.add(btn_remover);
    	
    	btn_adicionar_centro.addActionListener(e -> {
        	Window janela_pai = SwingUtilities.getWindowAncestor(this);
        	TelaAdicionarCentro dialog = new TelaAdicionarCentro(janela_pai);
            dialog.setVisible(true);});
  
    	TabelaRateio = new JTable(modeloTabela);
        JScrollPane scrollPane =new JScrollPane(TabelaRateio);
        gbc_distribuicao.gridx = 0;
        gbc_distribuicao.gridy = 0;
        gbc_distribuicao.weightx = 1;
        gbc_distribuicao.weighty = 1;
        gbc_distribuicao.gridwidth = 1;
        gbc_distribuicao.fill = GridBagConstraints.BOTH;
        jp_distribuicao.add(scrollPane, gbc_distribuicao);
        gbc_distribuicao.gridx = 0;
        gbc_distribuicao.gridy = 1;
        gbc_distribuicao.weightx = 0;
        gbc_distribuicao.weighty = 0;
        gbc_distribuicao.fill = GridBagConstraints.HORIZONTAL;
        jp_distribuicao.add(botoes_distribuicao, gbc_distribuicao);
        gbc_distribuicao.gridx = 0;
        gbc_distribuicao.gridy = 2;
        gbc_distribuicao.weightx = 0;
        gbc_distribuicao.weighty = 0;
        gbc_distribuicao.fill = GridBagConstraints.HORIZONTAL;
        jp_distribuicao.add(lbl_total_percentual, gbc_distribuicao);
        
        
         
    	botoes.add(btn_salvar);
    	botoes.add(btn_limpar);
    	botoes.add(btn_cancelar);
    	btn_cancelar.addActionListener(e -> dispose());
    	
    	add(jp_config, BorderLayout.NORTH);
    	add(jp_distribuicao, BorderLayout.CENTER);
        add(botoes, BorderLayout.SOUTH);
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
organizador.gridwidth = 1;
organizador.fill = GridBagConstraints.NONE;
painel.add(lbl, organizador);

organizador.gridx = 1;
organizador.gridy = y;
organizador.weightx = 1;
organizador.fill = GridBagConstraints.HORIZONTAL;
painel.add(componente, organizador);
};
}