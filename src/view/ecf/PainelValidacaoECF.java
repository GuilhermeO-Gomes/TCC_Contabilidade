package view.ecf;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import view.ComponentesContabeis;

public class PainelValidacaoECF extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelValidacaoECF(GeracaoECF g, Runnable aoGerar) {
    setLayout(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(900, 550));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    add(
        new JLabel(
            "ECF #"
                + g.getId()
                + " | Empresa "
                + g.getEmpresa()
                + " | "
                + g.getPeriodoInicial()
                + " a "
                + g.getPeriodoFinal()
                + " | "
                + g.getStatus()),
        BorderLayout.NORTH);
    DefaultTableModel modelo =
        ComponentesContabeis.modeloTabela("Código", "Tipo", "Descrição", "Referência", "Resolvido");
    JTable tabela = ComponentesContabeis.tabela("ecf.validacoes.tabela", modelo);
    for (ValidacaoECF v : g.getValidacoes())
      modelo.addRow(
          new Object[] {
            v.getCodigo(), v.getTipo(), v.getDescricao(), v.getReferencia(), v.getResolvido()
          });
    add(
        ComponentesContabeis.lista(
            "Erros e avisos",
            tabela));
    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(
        new JLabel(
            "Erros abertos: "
                + g.contarErros()
                + " | Avisos: "
                + g.contarAlertas()
                + " | "
                + (g.possuiErros()
                    ? "Registro do arquivo bloqueado"
                    : "Confira a situação antes de registrar o arquivo")),
        BorderLayout.NORTH);
    rodape.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao(
                "ecf.validacoes.detalhes",
                "Detalhes",
                () ->
                    ComponentesContabeis.abrirDialogo(
                        this,
                        "Validação",
                        new PainelDetalhesValidacao(
                            g.getValidacoes().get(ComponentesContabeis.linhaSelecionada(tabela))))),
            ComponentesContabeis.botao(
                "ecf.validacoes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
  }
}
