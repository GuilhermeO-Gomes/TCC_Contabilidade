package view;

import javax.swing.JFrame;

public class TelaTeste extends JFrame {

    public TelaTeste() {
        setTitle("Teste");
        setSize(500, 425);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new TelaAdicionarConta());
    }
}