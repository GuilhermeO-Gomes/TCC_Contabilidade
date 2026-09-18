package view;

import javax.swing.JFrame;

public class TelaTeste extends JFrame {

    public TelaTeste() {
        setTitle("Teste");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new TelaPlanoDeContas());
    }
}