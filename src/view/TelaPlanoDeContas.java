package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.PlanoContasController;

public class TelaPlanoDeContas extends JPanel {

    private static final long serialVersionUID = 1L;
    private final JTextField codigo = new JTextField(5), descricao = new JTextField(15);
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
            new Object[] {"Código", "Descrição", "Conta Superior", "Nível", "Grupo", "Natureza", "Tipo da Conta", "Situação"
            },
            0
    );

    private JTable tabelaPlanoDeContas;
    private PlanoContasController controller;

    public TelaPlanoDeContas() {
    	//Ver o que essas telas fazem no sentido de layout e painel
    	//Grid é layout flexível
    	setLayout(new BorderLayout(8,8));
    	setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar_tela();
        controller = new PlanoContasController(this);
        controller.carregarTabela();
    }

    private void montar_tela() {
    	//Aqui começa a ter tela
    	JPanel layout = new JPanel(new GridBagLayout());
    	layout.setBorder(BorderFactory.createTitledBorder("Plano de Contas"));
    	GridBagConstraints organizador = new GridBagConstraints();
    	//Margem dos componentes dentro de uma célula
    	organizador.insets = new Insets(4, 4, 4, 4);
    	organizador.anchor = GridBagConstraints.CENTER;
    	//Fazer JPanel para todo o container de filtro/pesquisa
        tabelaPlanoDeContas = new JTable(modeloTabela);
        JScrollPane scrollPane =
                new JScrollPane(tabelaPlanoDeContas);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Lista de Contas")
        );

        add(scrollPane, BorderLayout.CENTER);
    }


    public JTable getTabelaPlanoDeContas() {
        return tabelaPlanoDeContas;
    }
}