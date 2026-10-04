package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;

public class TelaRateio extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JTextField nome_rateio = new JTextField(15);
	private final JTextField conta_contabil = new JTextField(15);
	private final JTextField centro_custo = new JTextField(15);
	private final DefaultTableModel modeloTabela = new DefaultTableModel(
    new Object[] {"Centro de Custo", "Percentual(%)", "Valor" }, 0);
	private JTable TabelaRateio;
            
	
	
	
	
    public TelaRateio() {
    	setLayout(new BorderLayout(8,8));
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar_tela();
    }

    private void montar_tela() {
    	//Criar funções que criem automaticamente JPanels e GBCs, e JLabels
    	
    	JPanel painel_principal = new JPanel(new GridBagLayout());
    	painel_principal.setBorder(BorderFactory.createTitledBorder("Rateio"));
    	GridBagConstraints organizador_principal = new GridBagConstraints();
    	organizador_principal.anchor = GridBagConstraints.WEST;
    	organizador_principal.weightx = 0;
    	JPanel painel_config = new JPanel(new GridBagLayout());
    	painel_config.setBorder(BorderFactory.createTitledBorder("Configuração de Rateio"));
    	GridBagConstraints organizador_config = new GridBagConstraints();
    	organizador_config.insets = new Insets(5, 2, 5, 2);
    	organizador_config.anchor = GridBagConstraints.WEST;
    	organizador_config.weightx = 0;
    	JPanel painel_distribuicao = new JPanel(new GridBagLayout());
    	painel_distribuicao.setBorder(BorderFactory.createTitledBorder("Distribuição"));
    	GridBagConstraints organizador_distribuicao = new GridBagConstraints();
    	organizador_distribuicao.insets = new Insets(5, 2, 5, 2);
    	organizador_distribuicao.anchor = GridBagConstraints.WEST;
    	organizador_distribuicao.weightx = 0;
    	JPanel botoes_distribuicao = new JPanel(new FlowLayout(FlowLayout.LEFT));
    	JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER));
    	
    	
    	JLabel lbl_nome_rateio = new JLabel("Nome do Rateio:"), lbl_conta_contabil = new JLabel("Conta Contábil:"),
    	lbl_centro_custo = new JLabel("Centro de Custo Origem"), lbl_criterio = new JLabel("Critério:"), lbl_situacao = new JLabel("Situação:"),
    	lbl_total = new JLabel("TOTAL: "), lbl_placeholder = new JLabel("100%");
    	
    	String[] criterios= {"Critério 1", "Critério 2"};
    	String[] situacoes = {"Situação 1", "Situação 2"};

    	JComboBox <String> criterio = new JComboBox<>(criterios);
    	JComboBox <String> situacao = new JComboBox<>(situacoes);
    	JButton adicionar_centro = new JButton("Adicionar Centro"), atualizar = new JButton("Atualizar"), 
    	remover = new JButton("Remover"), salvar = new JButton("Salvar"), limpar = new JButton("Limpar"), 
    	cancelar = new JButton("Cancelar"), buscar_conta1 = new JButton ("Buscar Conta"), 
    	buscar_conta2 = new JButton ("Buscar Conta");
    	adicionar_componente(0, painel_config, organizador_config, lbl_nome_rateio, nome_rateio);
    	adicionar_componente(1, painel_config, organizador_config, lbl_conta_contabil, conta_contabil);
    	
    	organizador_config.gridx = 2;
    	organizador_config.gridy = 1;
    	organizador_config.weightx = 0;
    	organizador_config.fill = GridBagConstraints.NONE;
    	painel_config.add(buscar_conta1, organizador_config);
    	
    	adicionar_componente(2, painel_config, organizador_config, lbl_centro_custo, centro_custo);
    	
    	organizador_config.gridx = 2;
    	organizador_config.gridy = 2;
    	organizador_config.weightx = 0;
    	organizador_config.fill = GridBagConstraints.NONE;
    	painel_config.add(buscar_conta2, organizador_config);
    	
    	adicionar_componente(3, painel_config, organizador_config, lbl_criterio, criterio);
    	adicionar_componente(4, painel_config, organizador_config, lbl_situacao, situacao);
    	organizador_principal.gridx = 0;
    	organizador_principal.gridy = 0;
    	organizador_principal.anchor = GridBagConstraints.WEST;
    	organizador_principal.weightx = 1;
    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
    	painel_principal.add(painel_config, organizador_principal);
    	
    	botoes_distribuicao.add(adicionar_centro);
    	botoes_distribuicao.add(atualizar);
    	botoes_distribuicao.add(remover);
    	
    	organizador_principal.gridx = 0;
    	organizador_principal.gridy = 1;
    	organizador_principal.anchor = GridBagConstraints.WEST;
    	organizador_principal.weightx = 1;
    	organizador_principal.weighty = 0;
    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
    	painel_principal.add(botoes_distribuicao, organizador_principal);
    	

    	
    	TabelaRateio = new JTable(modeloTabela);
        JScrollPane scrollPane =new JScrollPane(TabelaRateio);
        organizador_distribuicao.gridx = 0;
        organizador_distribuicao.gridy = 0;
        organizador_distribuicao.weightx = 1;
        organizador_distribuicao.weighty = 1;
        organizador_distribuicao.gridwidth = 1;
        organizador_distribuicao.fill = GridBagConstraints.BOTH;
        painel_distribuicao.add(scrollPane, organizador_distribuicao);
         
    	organizador_principal.gridy = 2;
    	organizador_principal.fill = GridBagConstraints.BOTH;
    	organizador_principal.weighty = 1;
    	painel_principal.add(painel_distribuicao, organizador_principal);
    	
    	botoes.add(salvar);
    	botoes.add(limpar);
    	botoes.add(cancelar);
    	organizador_principal.gridy = 3;
    	organizador_principal.weighty = 0;
    	organizador_principal.fill = GridBagConstraints.HORIZONTAL;
    	organizador_principal.anchor = GridBagConstraints.WEST;
    	organizador_principal.weightx = 1;
    	painel_principal.add(botoes, organizador_principal);
    	 	
        add(painel_principal, BorderLayout.CENTER);
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
