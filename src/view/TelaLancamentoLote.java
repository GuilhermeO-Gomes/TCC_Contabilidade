package view;

//import controller.LancamentoLoteController;
import model.LancamentoLote;
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


public class TelaLancamentoLote extends JPanel {
    
    private final JTextField id = new JTextField(10),
    nomeLote = new JTextField(30),
    descriLote = new JTextField(60),
    dataAbertura = new JTextField(12),
    dataInicial = new JTextField(12),
    dataFinal = new JTextField(12);
    private final JTextField debito = new JTextField(20);
    private final JTextField credito = new JTextField(20);
    private final JTextField diferenca = new JTextField(20);
    private final JCheckBox chkBloqueado = new JCheckBox("Lote bloqueado", true);
    //private final LancamentoLoteController controller;

    public TelaLancamentoLote() {
        setLayout(new BorderLayout(8,8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //controller = new LancamentoLoteController(this);
        montar();
        
    }

    private void montar() {
        JPanel f = new JPanel(new GridBagLayout());
        f.setBorder(
           BorderFactory.createTitledBorder("Identificação")
        );
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 4, 3, 4);
        g.anchor = GridBagConstraints.WEST;
        campo(f, g, 0, "Lote*:", nomeLote);
        campo(f, g, 1, "Descrição do lote*:", descriLote);
        campo(f, g, 2, "Débito:", debito);
        campo(f, g, 3, "Crédito:", credito);
        campo(f, g, 4, "Diferença:", diferenca);
    

  
        JPanel f2 = new JPanel(new GridBagLayout());
        f2.setBorder(
           BorderFactory.createTitledBorder("Dados")
        );
        GridBagConstraints g2 = new GridBagConstraints();
        g2.insets = new Insets(3, 4, 3, 4);
        g2.anchor = GridBagConstraints.WEST;
        campo(f2, g2, 0, "Abertura (dd/mm/aaaa)*:", dataAbertura);
        campo(f2, g2, 1, "Data Inicial (dd/mm/aaaa)*:", dataInicial);
        campo(f2, g2, 2, "Data Final (dd/mm/aaaa)*:", dataFinal);
        g.gridx = 1;
        g.gridy = 5;
        f.add (chkBloqueado, g);

        debito.setEditable(false);
        credito.setEditable(false);
        diferenca.setEditable(false);
    

    JPanel quadrado = new JPanel();

    quadrado.setLayout(
        new BoxLayout(
            quadrado,BoxLayout.Y_AXIS)
    );

    quadrado.add(f);
    quadrado.add(f2);

    JPanel botao = new JPanel(new FlowLayout(FlowLayout.LEFT));
    JButton salvar = new JButton("Salvar"), //opção utilizada para salvar a criação ou alterações no lote
      bloquear = new JButton("Bloquear Lote"),//bloquear o lote para que nenhum outro usuário faça qualquer modificação
      excluir = new JButton("Excluir"),//opção utilizada para deletar o lote e suas alterações
      historico = new JButton("Histórico"), //opção para visualizar todas as alterações feita nesse lote e por qual usuario a alteração foi feita.
      fechar = new JButton("Fechar");
    
    botao.add(salvar);
    botao.add(bloquear);
    botao.add(excluir);
    botao.add(historico);
    botao.add(fechar);

    salvar.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //controller.salvar();
            }
        }
    );

    bloquear.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //controller.bloquear();
            }
        }
    );

    excluir.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //controller.excluir();
            }
        }
    );

    historico.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //controller.historico();
            }
        }
    );

    fechar.addActionListener(
        new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //controller.fechar();
            }
        }
    );

    add(quadrado, BorderLayout.NORTH);
    add(botao, BorderLayout.SOUTH);
 

    }

    private void campo(
        JPanel p,
        GridBagConstraints g,
        int y,
        String r,
        JTextField t
    ) {
        componente(p, g, y, r, t);
    }

    private void componente(
        JPanel p,
        GridBagConstraints g,
        int y,
        String r,
        Component c
    ) {
        g.gridx = 0;
        g.gridy = y;
        g.weightx = 0;
        g.fill = GridBagConstraints.NONE;
        p.add(new JLabel(r), g);
        g.gridx = 1;
        g.weightx = 1;
        g.fill = GridBagConstraints.HORIZONTAL;
        p.add(c, g);
    }

    public void mostrar (LancamentoLote l) {
        id.setText(String.valueOf(l.getId()));
        nomeLote.setText(l.getNomeLote());
        descriLote.setText(l.getDescriLote());
        dataAbertura.setText(l.getDataAbertura());
        dataFinal.setText(l.getDataFinal());
        dataInicial.setText(l.getDataInicial());
        debito.setText(String.valueOf(l.getDebito()));
        credito.setText(String.valueOf(l.getCredito()));
        diferenca.setText(String.valueOf(l.getDiferenca()));
        chkBloqueado.setSelected(l.isBloqueado());
    }

    public JTextField getTxtId() {
        return id;
    }

    public JTextField getTxtNomeLote() {
        return nomeLote;
    }

    public JTextField getTxtDescriLote() {
        return descriLote;
    }

    public JTextField getTxtDataAbertura() {
        return dataAbertura;
    }

    public JTextField getTxtDataFinal() {
        return dataFinal;
    }

    public JTextField getTxtDataInicial() {
        return dataInicial;
    }

    public JTextField getTxtDebito() {
        return debito;
    }

    public JTextField getTxtCredito() {
        return credito;
    }

    public JTextField getTxtDiferenca() {
        return diferenca;
    }

    public JCheckBox getChkBloqueado(){
        return chkBloqueado;
    }
     

}
