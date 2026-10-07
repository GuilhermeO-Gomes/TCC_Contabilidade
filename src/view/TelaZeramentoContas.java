package view;

//import controller.ZeramentoContas
//import model.HistoricoPadrao;
//import model.ZeramentoContas;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

public class TelaZeramentoContas extends JPanel {
    
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
  private final JTextField id = new JTextField(7),
   contaReduzida = new JTextField(15),
   dataZeramento = new JTextField(8),
   destinoDebito = new JTextField (32),
   destinoCredito = new JTextField(32),
   complemento = new JTextField (32),
   prejuizoDebito = new JTextField (15),
   prejuizoCredito = new JTextField (15),
   saldoDebito = new JTextField (15),
   saldoCredito = new JTextField (15),
   resultado = new JTextField (15);
  private final JCheckBox zerarSemSaldo = new JCheckBox("Zerar saldo para conta sem saldo", true);
  private final JCheckBox transferencia = new JCheckBox("Tranferência de saldo para conta do Patrimônio Líquido", true);
  private final JComboBox historicoPadrao = new JComboBox();

  private final JComboBox opcao = new JComboBox(new String[] {
    "Totalizado",
    "Individual",
  });


  private final DefaultTableModel modelo = new DefaultTableModel(
    new Object[] {"ID","Conta Reduzida", "Descrição", "Início Zeramento", "Saldo do mês"},
    0
  ) {
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
    JPanel f = new JPanel(new BorderLayout(8,8));
    f.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

    JPanel f2 = new JPanel(new GridBagLayout());
    f2.setBorder(BorderFactory.createTitledBorder("Parâmetros"));
    GridBagConstraints g = new GridBagConstraints();
    g.insets = new Insets(4,4,4,4);
    g.anchor = GridBagConstraints.WEST;

    componente(f2, g, 0, "Data do Zeramento(dd/MM/yyyy):", dataZeramento);
    componente(f2, g, 1, "Conta reduzida:", contaReduzida);
    componente(f2, g, 2, "Opção:", opcao);


    JPanel f3 = new JPanel(new GridBagLayout());
    f3.setBorder(BorderFactory.createTitledBorder("Destino zeramento"));
    f3.setLayout(new BoxLayout(f3, BoxLayout.Y_AXIS));

    JPanel destinos = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
    destinos.add(new JLabel("Destino (Débito): "));
    destinos.add(destinoDebito);
    destinos.add(new JLabel("Destino (Crédito): "));
    destinos.add(destinoCredito);
    f3.add(destinos);
  

    JPanel historicos = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
    historicos.add(new JLabel("Historico Padrão:"));
    historicos.add(historicoPadrao);
    historicos.add(new JLabel("Historico Padrão:"));
    historicos.add(historicoPadrao);
    f3.add(historicos);

    JPanel complementos = new JPanel(new FlowLayout(FlowLayout.LEFT, 2,2));  
    complementos.add(new JLabel("Complemento:"));
    complementos.add(complemento);
    complementos.add(new JLabel("Complemento:"));
    complementos.add(complemento);
    f3.add(complementos);

    JPanel fs = new JPanel(new GridLayout(1,2,8,8));
    fs.add(f2);
    fs.add(f3);
    
    f.add(fs, BorderLayout.CENTER);

    JPanel b = new JPanel(new FlowLayout(FlowLayout.LEFT));
    b.add(zerarSemSaldo);
    b.add(transferencia);
    f.add(b);


    JPanel prejuizos = new JPanel(new FlowLayout(FlowLayout.LEFT));
     prejuizos.add(new JLabel("Prejuízo (Débito):"));
     prejuizos.add(prejuizoDebito);
     prejuizos.add(new JLabel("Prejuízo (Crédito):"));
     prejuizos.add(prejuizoCredito);
     f.add(prejuizos);

    JPanel c = new JPanel(new BorderLayout());
     c.add(f, BorderLayout.NORTH);
     c.add(new JScrollPane(tabela));
     f.add(c);


    JPanel saldo = new JPanel(new FlowLayout(FlowLayout.LEFT));
     saldo.add(new JLabel("Saldo (Débito):"));
     saldo.add(saldoDebito);
     saldo.add(new JLabel("Saldo (Crédito)"));
     saldo.add(saldoCredito);
     saldo.add(new JLabel("Resultado:"));
     saldo.add(resultado);
     f.add(saldo);

    JPanel b2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton bmostrar = new JButton("Mostrar"),
     validar = new JButton("Validar"),
     processar = new JButton("Processar");
    b2.add(bmostrar);
    b2.add(validar);
    b2.add(processar);
    f.add(b2);

    zerarSemSaldo.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
           //controller.zerarSemSaldo();
        }
      }
    );

    transferencia.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //controller.transferir();
        }
      }
    );

    bmostrar.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //controller.bmostrar();
        }
      }
    );

    validar.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          //controller.validar();
        }
      }
    );

    processar.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e){
          //controller.processar();
        }
      }
    );

    tabela.addMouseListener(
      new MouseAdapter() {
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
      complemento.setText(u.getComplemento());
      prejuizoDebito.setText(u.getPrejuizoDebito());
      prejuizoCredito.setText(u.getPrejuizoCredito());
      saldoDebito.setText(u.getSaldoDebito());
      saldoCredito.setText(u.getSaldoCredito());
      resultado.setText(u.getResultado());
      zerarSemSaldo.setSelected(u.isZerarSemSaldo());
      transferencia.setSelected(u.isTransferencia());
      opcao.setSelectedItem(u.getOpcao());
      selecionarCombo(historicoPadrao, u.getHistoricoPadrao().getId());
    }

    public void preencher(List<ZeramentoContas> l) {
      modelo.setRowCount(0);
      int i;
      for (i = 0; i<l.size(); i++) {
      ZeramentoContas u = l.get(i);
      modelo.addRow(new Object[] {
        Integer.valueOf(u.getId()),
        u.getContaReduzida(),
        //u.getDescricao(),
        u.getDataZeramento(),
        u.getResultado(),

      });
      
      }
    }



    private void selecionarCombo(JComboBox combo, int codigo) {
      int i;
      for (i = 0; i<combo.getItemCount(); i++) {
        Object o = combo.getItemAt(i);
        if (o instanceof HistoricoPadrao && ((HistoricoPadrao o).getId() == codigo)){
          combo.setSelectedIndex(i);
          return;
        }
      }
    }

 public int getId() {
    try {
      return Integer.parseInt(id.getText());
    } catch (Exception e) {
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

  public JTextField getTxtComplemento() {
    return complemento;
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

  public JComboBox getCmbHistoricoPadrao() {
    return historicoPadrao;
  }

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
