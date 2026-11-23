package view.ecf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import view.ComponentesContabeis;

public class PainelValidacaoECF extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelValidacaoECF() {
    super(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(900, 550));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    add(new JLabel("Nenhuma validação executada"), BorderLayout.NORTH);
    JTable tabela = ComponentesContabeis.tabela("ecf.validacoes.tabela", ComponentesContabeis.modeloTabela(
        "Código", "Tipo", "Descrição", "Referência", "Resolvido"));
    add(ComponentesContabeis.lista("Erros e avisos", tabela), BorderLayout.CENTER);
    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.validacoes.detalhes", "Detalhes", () ->
            ComponentesContabeis.abrirDialogo(this, "Validação", new PainelDetalhesValidacao())),
        ComponentesContabeis.botao("ecf.validacoes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }
}
