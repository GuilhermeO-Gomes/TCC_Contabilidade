package view;

import javax.swing.JFrame;

public class TelaTeste extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TelaTeste() {
        setTitle("Teste");
        setSize(500, 425);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new TelaRateio());
    }
}