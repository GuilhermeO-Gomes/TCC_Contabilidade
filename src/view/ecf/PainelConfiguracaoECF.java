package view.ecf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import javax.swing.*;
import view.ComponentesContabeis;

public class PainelConfiguracaoECF extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelConfiguracaoECF() {
    super(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(750, 550));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    LocalDate hoje = LocalDate.now();
    JComboBox<String> finalidade = new JComboBox<>(new String[] {"Original", "Retificadora"});
    JTextField recibo = new JTextField(24);
    recibo.setEnabled(false);
    finalidade.addActionListener(e -> {
      boolean retificadora = finalidade.getSelectedIndex() == 1;
      recibo.setEnabled(retificadora);
      if (!retificadora) {
        recibo.setText("");
      }
    });

    add(ComponentesContabeis.campos(
        "CNPJ", new JLabel("—"),
        "Razão social", new JLabel("—"),
        "Regime tributário", new JLabel("—"),
        "Exercício social", new JComboBox<String>(),
        "Data inicial", ComponentesContabeis.criarData("ecf.config.inicio", hoje.withDayOfYear(1)),
        "Data final", ComponentesContabeis.criarData("ecf.config.fim", hoje.withMonth(12).withDayOfMonth(31)),
        "Tipo de escrituração", finalidade,
        "Recibo anterior (obrigatório para retificadora)", recibo,
        "Versão do layout", new JTextField(20),
        "ID da ECD (opcional)", new JTextField(20),
        "Observação", new JTextField(30)), BorderLayout.CENTER);

    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("ecf.config.salvar", "Salvar preparação"),
        ComponentesContabeis.botao("ecf.config.cancelar", "Cancelar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }
}
