package view;

//import controller.DepreciacaoController;
//import model.Depreciacao;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Depreciacao;

public class TelaDepreciacao extends JPanel{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private final JTextField id = new JTextField(45),
    taxa = new JTextField(45),
    tempo = new JTextField(45),
    tipoBem = new JTextField(45);


    private final DefaultTableModel modelo = new DefaultTableModel(
        new Object[] {"Movimentação", "Débito", "Histórico Padrão", "Crédito", "Histórico Padrão", "Data de zeramento"},
        0
    ) {
        @Override
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
        add(f, BorderLayout.NORTH);
        f.setBorder(
            BorderFactory.createTitledBorder("Conta contábil")
        );
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3,4,3,4);
        tipoBem.setEditable(false);

        g.anchor = GridBagConstraints.WEST;
        componente(f, g, 0, "Número do bem:", id);
        componente(f, g, 1, "Tipo do bem:", tipoBem);
        componente(f, g, 2, "Taxa Depreciação:", taxa);
        componente(f, g, 3, "Tempo depreciação meses:", tempo);
        g.gridx = 0;
        g.gridy = 1;
        g.gridwidth = 2;
        g.fill = GridBagConstraints.HORIZONTAL;
      

        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
        b.add(new JLabel("Tipo de Diminuição:"));
        JRadioButton bDepreciacao = new JRadioButton("Depreciação"),
          amortizacao = new JRadioButton("Amortização");
        bDepreciacao.setOpaque(false);
        amortizacao.setOpaque(false);
      
        b.add(bDepreciacao);
        b.add(amortizacao);

        ButtonGroup bg = new ButtonGroup();
        bg.add(bDepreciacao);
        bg.add(amortizacao);

        g.gridx = 0;
        g.gridy = 4;
        f.add(b, g);
       
        

        JPanel c = new JPanel(new BorderLayout());
        c.add(new JScrollPane(tabela));
        
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(f, BorderLayout.NORTH);
        add(c, BorderLayout.CENTER);

        JButton salvar = new JButton("Salvar");
        g.gridy =5;
        g.gridwidth = 2;
        f.add(salvar, g);

        salvar.addActionListener((ActionEvent e) -> {
            //controller.salvar();
        });

        bDepreciacao.addActionListener((ActionEvent e) -> {
            //controller.bDepreciacao();
        });

        amortizacao.addActionListener((ActionEvent e) -> {
            //controller.amortizacao();
        });

       
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
        taxa.setText(String.valueOf(u.getTaxa()));
        tempo.setText(u.getTempo());
    }

    public void preencher (List<Depreciacao> l) {
        modelo.setRowCount(0);
        int i;
        for (i = 0; i < l.size(); i++) {
           Depreciacao u = l.get(i);
           modelo.addRow(new Object[]{
               u.getId(),
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
        }   catch (NumberFormatException e) {
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
