package view;

import model.Reavaliacao;
import model.Depreciacao;
//import controller.ReavaliacaoController
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TelaReavaliacao extends JPanel{

    

    private final JTextField idBem = new JTextField(7),
      situacaoAntes = new JTextField(21),
      situacaoLaudo = new JTextField(21),
      valorHistorico = new JTextField(21),
      novoValor = new JTextField(21),
      depreAcumulada = new JTextField(21),
      tempoRemanescente = new JTextField(7),
      valorContabil = new JTextField(21),
      valorReavaliado = new JTextField(21);

      private final DefaultTableModel modelo = new DefaultTableModel(
         new Object[] {"Id bem","Valor da nova avaliação", "Valor contábil", "Resultado da Reavaliação"},
        0
      ) {
        public boolean isCellEditable(int l, int c) {
            return false;
        }
      };

      private final JTable tabela = new JTable(modelo);
      //private final ReavaliacaoController controller;
   

      public TelaReavaliacao(int idBem) {
        this.idBem.setText(String.valueOf(idBem));

        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //controller = new ReavaliacaoController(this);
        montar();
         
      }

      private void montar() {
        JPanel f = new JPanel(new GridBagLayout());
        f.setBorder(BorderFactory.createTitledBorder(""));
        GridBagConstraints g = new GridBagConstraints();
        add(f, BorderLayout.NORTH);

        g.insets = new Insets(4, 4, 4,4 );
        componente(f, g, 0, "Código:", idBem);
        componente(f, g, 1, "Situação do bem antes do laudo:", situacaoAntes);
        componente(f, g, 2, "Situação do bem apresentada no laudo:", situacaoLaudo);
        componente(f, g, 3, "Valor histórico:", valorHistorico);
        componente(f, g, 4, "Novo valor do bem:", novoValor);
        componente(f, g, 5, "Depreciação acumulada:", depreAcumulada);
        componente(f, g, 6, "Tempo de vida remanescente:", tempoRemanescente);
        componente(f, g, 7, "Valor contábil líquido:", valorContabil);
        g.gridx = 1;
        g.gridy = 8;
        

        JPanel c = new JPanel(new BorderLayout());
        c.add(new JScrollPane(tabela));
        add(c);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton salvar = new JButton ("Salvar");
        b.add(salvar);
        f.add(b);

        salvar.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    //controller.salvar();
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
        g.gridy = y;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        p.add(t, g);

      }

      public void mostrar(Reavaliacao u) {
        idBem.setText(String.valueOf(u.getId()));
        situacaoAntes.setText(u.getSituacaoAntes());
        situacaoLaudo.setText(u.getSituacaoLaudo());
        valorHistorico.setText(u.getValorHistorico());
        novoValor.setText(u.getNovoValor());
        depreAcumulada.setText(u.getDepreAcumulada());
        tempoRemanescente.setText(u.getTempoRemanescente());
        valorContabil.setText(u.getValorContabil());
      }

      public void preencher(List<Reavaliacao>l) {
        modelo.setRowCount(0);
        int i;
        for (i=0; i<l.size(); i++) {
            Reavaliacao u = l.get(i);
            modelo.addRow(new Object[]{
                Integer.valueOf(u.getId()),
                u.getNovoValor(),
                u.getValorContabil(),
                u.getValorReavaliado(),
            });
        }
      }

      public int getId() {
        try {
            return Integer.parseInt(idBem.getText());
        }  catch (Exception e) {
            return  0;
        }
      }

      public JTextField getTxtSituacaoAntes() {
        return situacaoAntes;
      }

      public JTextField getTxtSituacaoLaudo() {
        return situacaoLaudo;

      }

      public JTextField getValorHistorico() {
        return valorHistorico;
      }

      public JTextField getNovoValor() {
        return novoValor;
      }

      public JTextField getValorReavaliado() {
        return valorReavaliado;
      }

      public JTextField getDepreAcumulada() {
        return depreAcumulada;
      }

      public JTextField getTempoRemanescente() {
        return tempoRemanescente;
      }

      public JTextField getValorContabil() {
        return valorContabil;
      }


}
