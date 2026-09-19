package view;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;



public class TelaPrincipal extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout;
    private JPanel painelPrincipal;

    public TelaPrincipal() {

        configurarJanela();
        configurarTelas();
        configurarMenu();

        setVisible(true);
    }

    private void configurarJanela() {

        setTitle("Sistema");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void configurarTelas() {

        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

 
        painelPrincipal.add(new TelaClientes(), "clientes");


        add(painelPrincipal);
    }

    private void configurarMenu() {

        JMenuBar barraMenu = new JMenuBar();

        JMenu menuCadastro = new JMenu("Cadastro");

        JMenuItem itemClientes = new JMenuItem("Clientes");
       

        menuCadastro.add(itemClientes);
       

        barraMenu.add(menuCadastro);

        setJMenuBar(barraMenu);

        itemClientes.addActionListener(e ->
            abrirTela("clientes")
        );

        
    }

    private void abrirTela(String tela) {
        cardLayout.show(painelPrincipal, tela);
    }
}