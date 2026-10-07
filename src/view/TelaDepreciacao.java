package view;

//import controller.DepreciacaoController;
//import model.Depreciacao;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import model.Depreciacao;
import javax.swing.table.DefaultTableModel;

public class TelaDepreciacao extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private final JTextField id = new JTextField(7),
    taxa = new JTextField(10),
    tempo = new JTextField(4);


    private final DefaultTableModel modelo = new DefaultTableModel(
        new Object[] {"Movimentação", "Débito", "Histórico Padrão", "Crédito", "Histórico Padrão", "Data de zeramento"},
        0
    ) {
        public boolean isCellEditable (int l, int c) {
            return false;
        }
    };

    private final JTable tabela = new JTable(modelo);
    //private final DepreciacaoController controller;
    
    public TelaDepreciacao() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        //controller = new DepreciacaoController(this);
        montar();
    }

    private void montar() {
        JPanel f = new JPanel(new GridBagLayout());
        f.setBorder(BorderFactory.createTitledBorder("Conta contábil"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4,4,4,4);
        componente(f, g, 0, "Número:", id);
        add(f);
        
        JPanel a = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
        componente(a, g, 0, "Taxa Depreciação:", taxa);
        componente(a, g, 0, "Tempo Depreciação(Meses):", tempo);
        f.add(a);

        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
        b.add(new JLabel("Tipo de Diminuição:"));
        JRadioButton bDepreciacao = new JRadioButton("Depreciação"),
          amortizacao = new JRadioButton("Amortização"),
          exaustao = new JRadioButton("Exaustão");
        bDepreciacao.setOpaque(false);
        amortizacao.setOpaque(false);
        exaustao.setOpaque(false);
        b.add(bDepreciacao);
        b.add(amortizacao);
        b.add(exaustao);
        f.add(b);

        ButtonGroup bg = new ButtonGroup();
        bg.add(bDepreciacao);
        bg.add(amortizacao);
        bg.add(exaustao);
        

        JPanel c = new JPanel(new BorderLayout());
        c.add(new JScrollPane(tabela));
        f.add(c);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton salvar = new JButton("Salvar");
        f.add(salvar);

        salvar.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //controller.salvar();
                }
            }
        );

        bDepreciacao.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //controller.bDepreciacao();
                }
            }
        );

        amortizacao.addActionListener(
            new ActionListener() {
               public void actionPerformed(ActionEvent e) {
                 //controller.amortizacao();
               } 
            }
        );

        exaustao.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //controller.exaustao();
                }
            }
        );
    }

    private void componente(
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

    public void mostrar(Depreciacao u) {
        id.setText(String.valueOf(u.getId()));
        taxa.setText(u.getTaxa());
        tempo.setText(u.getTempo());
    }

    public void preencher (List<Depreciacao> l) {
        modelo.setRowCount(0);
        int i;
        for (i = 0; i < l.size(); i++) {
           Depreciacao u = l.get(i);
           modelo.addRow(new Object[]{
            Integer.valueOf(u.getId()),
            u.getId(),
            u.getTaxa(),
            u.getTempo(),
            u.getDataDepreciacao(),
           });
        }
    }

    public int getId() {
        try {
            return Integer.parseInt(id.getText());
        }   catch (Exception e) {
            return 0;
        }
    }

    public JTextField getTxtTaxa() {
        return taxa;
    }

    public JTextField getTxtTempo() {
        return tempo;
    }

    
}
