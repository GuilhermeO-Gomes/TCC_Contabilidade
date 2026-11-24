package view;

//import controller.GerenciamentoLoteController;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.LancamentoLote;

public class TelaGerenciamentoLote extends JPanel {


	private static final long serialVersionUID = 1L;

	private final DefaultTableModel modelo = new DefaultTableModel(
        new Object[] {"ID", "Nome", "Descrição", "Data abertura", "Data inicial", "Data final", "Débito", "Crédito", "Diferença", "Bloqueado"},
        0
    ) {
       @Override
       public boolean isCellEditable(int l, int c) {
        return false;
       }
    };

    private final JTable tabela = new JTable(modelo);
    private final JTextField pesquisa = new JTextField(30);
    //private final GerenciamentoLoteController controller;

    public TelaGerenciamentoLote() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //controller = new UsuarioController(this);
        montar();
        //controller.listar();

    }

    private void montar() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setBorder(BorderFactory.createTitledBorder("Gerenciamento de lote"));
        p.add(new JLabel("Pesquisar Lote:"));
        p.add(pesquisa);
        JButton buscar = new JButton("Buscar"),
          todos = new JButton("Todos");
        p.add(buscar);
        p.add(todos);
        JPanel c = new JPanel(new BorderLayout());
        c.add(p, BorderLayout.NORTH);
        c.add(new JScrollPane(tabela));
        add(c);
       
        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton novo = new JButton("Novo"),
            excluir = new JButton("Excluir"),
            bloquear = new JButton("Bloquear");
        b.add(novo);
        b.add(excluir);
        b.add(bloquear);
        add(b, BorderLayout.SOUTH);
        
        excluir.addActionListener((ActionEvent e) -> {
            //controller.excluir();
        });

        novo.addActionListener((ActionEvent e) -> {
            // controller.novo();  -> para ir para a tela Lancamento de lote
        });

        


        buscar.addActionListener((ActionEvent e) -> {
            //controller.buscar();
        });

        todos.addActionListener((ActionEvent e) -> {
            pesquisa.setText("");
            //controller.listar();
        });

        tabela.addMouseListener(
            new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    //controller.selecionar();
                }
            }
        );

    }

    public void preencherTabela(List<LancamentoLote> l) {
        modelo.setRowCount(0);
        int i;
        for (i=0; i<l.size(); i++){
          LancamentoLote m = l.get(i);
        modelo.addRow(new Object[]{
            m.getId(),
            m.getNomeLote(),
            m.getDescriLote(),
            m.getDataAbertura(),
            m.getDataInicial(),
            m.getDataFinal(),
            m.getDebito(),
            m.getCredito(),
            m.getDiferenca(),
            m.isBloqueado() ? "Sim" : "Não",
          
        });
      }
    }

    public JTable getTabela() {
        return tabela;
    }


}
