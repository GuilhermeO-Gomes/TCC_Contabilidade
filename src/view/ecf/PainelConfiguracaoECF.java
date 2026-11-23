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
    JComboBox<String> retificada = new JComboBox<>();
    retificada.setEnabled(false);
    finalidade.addActionListener(e -> retificada.setEnabled(finalidade.getSelectedIndex() == 1));
    JComboBox<String> regime = new JComboBox<>(new String[] {
        "Lucro real", "Lucro presumido", "Lucro arbitrado", "Imune / isenta", "Outro"
    });

    add(ComponentesContabeis.campos(
        "Empresa", new JLabel("—"),
        "Exercício social", new JComboBox<String>(),
        "Início", ComponentesContabeis.criarData("ecf.config.inicio", hoje.withDayOfYear(1)),
        "Fim", ComponentesContabeis.criarData("ecf.config.fim", hoje.withMonth(12).withDayOfMonth(31)),
        "Finalidade", finalidade,
        "ECF retificada", retificada,
        "Regime tributário", regime,
        "Versão do layout (opcional)", new JTextField(20),
        "ID da ECD (opcional)", new JTextField(20),
        "Observação", new JTextField(30)), BorderLayout.CENTER);

    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("ecf.config.salvar", "Salvar preparação"),
        ComponentesContabeis.botao("ecf.config.cancelar", "Cancelar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }
}
