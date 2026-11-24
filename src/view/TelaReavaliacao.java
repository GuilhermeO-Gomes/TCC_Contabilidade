package view;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.Reavaliacao;

public class TelaReavaliacao extends JPanel{

 
	private static final long serialVersionUID = 1L;

	private final JTextField idBem = new JTextField(45),
      situacaoAntes = new JTextField(45),
      situacaoLaudo = new JTextField(45),
      valorHistorico = new JTextField(45),
      novoValor = new JTextField(45),
      depreAcumulada = new JTextField(45),
      tempoRemanescente = new JTextField(45),
      valorContabil = new JTextField(45),
      valorReavaliado = new JTextField(45);

      private final DefaultTableModel modelo = new DefaultTableModel(
         new Object[] {"ID","Valor da nova avaliação", "Valor contábil", "Resultado da Reavaliação"},
        0
      ) {
        @Override
        public boolean isCellEditable(int l, int c) {
            return false;
        }
      };

      private final JTable tabela = new JTable(modelo);
      //private final ReavaliacaoController controller;
   

      public TelaReavaliacao() {
  

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

        situacaoAntes.setEditable(false);
        valorHistorico.setEditable(false);
        depreAcumulada.setEditable(false);
        tempoRemanescente.setEditable(false);
        valorContabil.setEditable(false); 
        
        g.anchor = GridBagConstraints.WEST;
        g.insets = new Insets(4, 4, 4, 4);
        componente(f, g, 0, "Código do bem:", idBem);
        componente(f, g, 1, "Situação antes laudo:", situacaoAntes);
        componente(f, g, 2, "Situação apresentada no laudo:", situacaoLaudo);
        componente(f, g, 3, "Valor histórico:", valorHistorico);
        componente(f, g, 4, "Novo valor do bem:", novoValor);
        componente(f, g, 5, "Depreciação acumulada:", depreAcumulada);
        componente(f, g, 6, "Tempo de vida remanescente:", tempoRemanescente);
        componente(f, g, 7, "Valor contábil líquido:", valorContabil);
        g.gridx = 1;
        g.gridy = 8;
        
        g.fill = GridBagConstraints.NONE;
        g.anchor = GridBagConstraints.WEST;
        g.insets = new Insets(4, 4, 4, 4);


     
        JButton salvar = new JButton ("Salvar");
        f.add(salvar, g);

        

        JPanel c = new JPanel(new BorderLayout());
        c.add(new JScrollPane(tabela));
        add(c);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
    
        salvar.addActionListener((ActionEvent e) -> {
            //controller.salvar();
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
        g.gridy = y;
        g.weightx = 1;
        p.add(t, g);
       

      }

      public void mostrar(Reavaliacao u) {
        idBem.setText(String.valueOf(u.getId()));
        situacaoAntes.setText(u.getSituacaoAntes());
        situacaoLaudo.setText(u.getSituacaoLaudo());
        valorHistorico.setText(String.valueOf(u.getValorHistorico()));
        novoValor.setText(String.valueOf(u.getNovoValor()));
        //depreAcumulada.setText(u.getDepreAcumulada());
        //tempoRemanescente.setText(u.getTempoRemanescente());
        //valorContabil.setText(u.getValorContabil());
      }

      public void preencher(List<Reavaliacao>l) {
        modelo.setRowCount(0);
        int i;
        for (i=0; i<l.size(); i++) {
            Reavaliacao u = l.get(i);
            modelo.addRow(new Object[]{
                u.getId(),
                u.getNovoValor(),
                //u.getValorContabil(),
                u.getValorReavaliado(),
            });
        }
      }

      public int getId() {
        try {
            return Integer.parseInt(idBem.getText());
        }  catch (NumberFormatException e) {
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
