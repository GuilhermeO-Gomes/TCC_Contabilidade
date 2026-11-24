package view;

//import controller.ZeramentoContas
//import model.HistoricoPadrao;
//import model.ZeramentoContas;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.ZeramentoContas;

public class TelaZeramentoContas extends JPanel {
    
	private static final long serialVersionUID = 1L;
  private final JTextField id = new JTextField(7),
   contaReduzida = new JTextField(15),
   dataZeramento = new JTextField(8),
   destinoDebito = new JTextField (21),
   destinoCredito = new JTextField(32),
   complementoD = new JTextField (32),
   complementoC = new JTextField (32),
   prejuizoDebito = new JTextField (32),
   prejuizoCredito = new JTextField (32),
   saldoDebito = new JTextField (15),
   saldoCredito = new JTextField (15),
   resultado = new JTextField (15);
  private final JCheckBox zerarSemSaldo = new JCheckBox("Zerar saldo para conta sem saldo", true);
  private final JCheckBox transferencia = new JCheckBox("Tranferência de saldo para conta do Patrimônio Líquido", true);
  @SuppressWarnings("rawtypes")
private final JComboBox historicoPadraoC = new JComboBox();

  @SuppressWarnings("rawtypes")
private final JComboBox historicoPadraoD = new JComboBox();

  @SuppressWarnings({ "rawtypes", "unchecked" })
private final JComboBox opcao = new JComboBox(new String[] {
    "Totalizado",
    "Individual",
  });


  private final DefaultTableModel modelo = new DefaultTableModel(
    new Object[] {"ID","Conta Reduzida", "Descrição", "Início Zeramento", "Saldo do mês"},
    0
  ) {
    @Override
    public boolean isCellEditable(int l, int c) {
       return false;
    }
  };
  private final JTable tabela = new JTable(modelo);
  //private final UsuarioController controller;

  public TelaZeramentoContas() {
    setLayout(new BorderLayout(8,8));
    setBorder(BorderFactory.createEmptyBorder(10, 10,10, 10));
    //controller = new ZeramentoContasController(this);
    montar();
  }

  private void montar() {
    JPanel f = new JPanel();
    f.setLayout(new BoxLayout(f, BoxLayout.Y_AXIS));
    f.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
    add(f, BorderLayout.NORTH);

    JPanel f2 = new JPanel(new GridBagLayout());
    f2.setBorder(BorderFactory.createTitledBorder("Parâmentros"));
    GridBagConstraints g = new GridBagConstraints();
    

    g.anchor = GridBagConstraints.WEST;
    g.insets = new Insets(4, 4, 4, 4);
    componente(f2, g, 0, "Data do Zeramento:", dataZeramento);
    componente(f2, g, 1, "Conta Reduzida:", contaReduzida);
    componente(f2, g, 2, "Opção:", opcao);
    f.add(f2);

    JPanel f3 = new JPanel(new GridBagLayout());
     f3.setBorder(BorderFactory.createTitledBorder("Destino zeramento"));
     GridBagConstraints g2 = new GridBagConstraints();
     g2.anchor = GridBagConstraints.WEST;
     g2.insets = new Insets(4, 4, 4, 4);
     componente(f3, g2, 4, "Destino(Débito):", destinoDebito);
     componente(f3, g2, 5, "Histórico:", historicoPadraoD);
     componente(f3, g2, 6, "Complemento:", complementoD);
     componente(f3, g2, 7, "Destino(Débito):", destinoCredito);
     componente(f3, g2, 8, "Histórico:", historicoPadraoC);
     componente(f3, g2, 9, "Complemento:", complementoC);
     f.add(f3);
    
    JPanel f4 = new JPanel(new GridBagLayout()); 
    f4.setBorder(BorderFactory.createTitledBorder(""));
    f4.setBorder(BorderFactory.createEmptyBorder(4, 4, 10, 4));
    
    JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
    b.add(zerarSemSaldo);
    b.add(transferencia);
    f.add(b);
 
     componente(f4, g2, 11, "Prejuízo (Débito):", prejuizoDebito);
     componente(f4, g2, 12, "Prejuízo (Crédito):", prejuizoCredito);
    
    f.add(f4);

    JPanel c = new JPanel(new BorderLayout());
     c.add(new JScrollPane(tabela));
     add(c, BorderLayout.CENTER);
     c.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
     tabela.setPreferredScrollableViewportSize(new Dimension(900, 255));
     
     f.add(c);

     JPanel f5 = new JPanel(new GridBagLayout());
     f5.setBorder(BorderFactory.createTitledBorder(""));
     GridBagConstraints g5 = new GridBagConstraints();
     g5.insets = new Insets(4, 8, 4, 8);
     g5.anchor = GridBagConstraints.WEST;
     saldoDebito.setEditable(false);
     saldoCredito.setEditable(false);
     resultado.setEditable(false);

     g5.gridx = 0;
     g5.gridy = 0;

     f5.add(new JLabel("Saldo (Débito):"), g5);
     g5.gridx = 1;
     f5.add(saldoDebito, g5);

     g5.gridx = 2;
     f5.add(new JLabel("Saldo (Crédito):"), g5);
     g5.gridx = 3;
     f5.add(saldoCredito, g5);
     
     g5.gridx=4;
     f5.add(new JLabel("Resultado:"), g5);
     g5.gridx = 5;
     f5.add(resultado, g5);
     
    f.add(f5);

    JPanel b2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton bmostrar = new JButton("Mostrar"),
     validar = new JButton("Validar"),
     processar = new JButton("Processar");
    b2.add(bmostrar);
    b2.add(validar);
    b2.add(processar);
    f.add(b2);
   

    zerarSemSaldo.addActionListener((ActionEvent e) -> {
        //controller.zerarSemSaldo();
    });

    transferencia.addActionListener((ActionEvent e) -> {
        //controller.transferir();
    });

    bmostrar.addActionListener((ActionEvent e) -> {
        //controller.bmostrar();
    });

    validar.addActionListener((ActionEvent e) -> {
        //controller.validar();
    });

    processar.addActionListener((ActionEvent e) -> {
        //controller.processar();
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

   private void componente(
    JPanel f,
    GridBagConstraints g,
    int y,
    String r,
    Component t
   ) {
    g.gridx = 0;
    g.gridy = y;
    g.weightx = 0;
    g.fill = GridBagConstraints.NONE;
    f.add(new JLabel(r), g);
    g.gridx = 1;
    g.weightx = 1;
    g.fill = GridBagConstraints.HORIZONTAL;
    f.add(t, g);

   }

     public void mostrar(ZeramentoContas u) {
      id.setText(String.valueOf(u.getId()));
      contaReduzida.setText(u.getContaReduzida());
      dataZeramento.setText(u.getDataZeramento());
      destinoDebito.setText(u.getDestinoDebito());
      destinoCredito.setText(u.getDestinoCredito());
      complementoD.setText(u.getComplementoD());
      complementoC.setText(u.getComplementoC());
      prejuizoDebito.setText(u.getPrejuizoDebito());
      prejuizoCredito.setText(u.getPrejuizoCredito());
      saldoDebito.setText(u.getSaldoDebito());
      saldoCredito.setText(u.getSaldoCredito());
      resultado.setText(u.getResultado());
      zerarSemSaldo.setSelected(u.isZerarSemSaldo());
      transferencia.setSelected(u.isTransferencia());
      opcao.setSelectedItem(u.getOpcao());
      //selecionarCombo(historicoPadrao, u.getHistoricoPadrao().getId());
    }

    public void preencher(List<ZeramentoContas> l) {
      modelo.setRowCount(0);
      int i;
      for (i = 0; i<l.size(); i++) {
      ZeramentoContas u = l.get(i);
      modelo.addRow(new Object[] {
          u.getId(),
        u.getContaReduzida(),
        //u.getDescricao(),
        u.getDataZeramento(),
        u.getResultado(),

      });
      
      }
    }



    /*private void selecionarCombo(JComboBox combo, int codigo) {
      int i;
      for (i = 0; i<combo.getItemCount(); i++) {
        Object o = combo.getItemAt(i);
        if (o instanceof HistoricoPadrao && ((HistoricoPadrao o).getId() == codigo)){
          combo.setSelectedIndex(i);
          return;
        }
      }
    }
    */
    
 public int getId() {
    try {
      return Integer.parseInt(id.getText());
    } catch (NumberFormatException e) {
      return 0;
    }
  }

  public JTextField getTxtContaReduzida() {
    return contaReduzida;
  }

  public JTextField getTxtDataZeramento() {
    return dataZeramento;
  }

  public JTextField getTxtDestinoDebito() {
    return destinoDebito;
  }

  public JTextField getTxtDestinoCredito() {
    return destinoCredito;
  }

  public JTextField getTxtComplementoD() {
    return complementoD;
  }

  public JTextField getTxtComplementoC() {
	    return complementoC;
	  }
  
  public JTextField getTxtPrejuizoDebito() {
    return prejuizoDebito;
  }

  public JTextField getTxtPrejuizoCredito() {
    return prejuizoCredito;
  }

  public JTextField getTxtSaldoDebito() {
    return saldoDebito;
  }

  public JTextField getTxtSaldoCredito() {
    return saldoCredito;
  }

  public JTextField getTxtResultado() {
    return resultado;
  }

  @SuppressWarnings("rawtypes")
public JComboBox getCmbHistoricoPadraoC() {
    return historicoPadraoC;
  }

    @SuppressWarnings("rawtypes")
public JComboBox getCmbHistoricoPadraoD() {
    return historicoPadraoD;
  }

  @SuppressWarnings("rawtypes")
public JComboBox getCmbOpcao() {
    return opcao;
  }

  public JCheckBox getChkZerarSemSaldo() {
    return zerarSemSaldo;
  }

  public JCheckBox getChkTransferencia() {
    return transferencia;
  }

  public JTable getTabela() {
    return tabela;
  }

  
}
