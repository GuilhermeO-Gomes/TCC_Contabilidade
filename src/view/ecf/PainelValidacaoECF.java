package view.ecf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import view.ComponentesContabeis;

public class PainelValidacaoECF extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelValidacaoECF() {
    super(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(900, 550));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    JPanel verificacoes = new JPanel(new GridLayout(0, 1, 0, 4));
    verificacoes.setBorder(BorderFactory.createTitledBorder("Verificações para gerar a ECF"));
    verificacoes.add(new JLabel("Sem erros bloqueantes abertos na Auditoria Digital"));
    verificacoes.add(new JLabel("De-Para completo das contas analíticas com saldo ou movimento"));
    verificacoes.add(new JLabel("Balanço fechado e resultado da DRE conciliado com o patrimônio líquido"));
    verificacoes.add(new JLabel("Dados da empresa, recibo da retificadora e signatários válidos"));
    verificacoes.add(new JLabel("Exercício encerrado ou pronto para apuração anual"));
    add(verificacoes, BorderLayout.NORTH);

    JTable tabela = ComponentesContabeis.tabela("ecf.validacoes.tabela", ComponentesContabeis.modeloTabela(
        "Verificação", "Situação", "Motivo do bloqueio", "O que precisa ser corrigido"));
    add(ComponentesContabeis.lista("Pendências da validação", tabela), BorderLayout.CENTER);

    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(new JLabel("Nenhuma validação executada."), BorderLayout.NORTH);
    rodape.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botaoInativo("ecf.validar", "Validar"),
        ComponentesContabeis.botao("ecf.validacoes.detalhes", "Detalhes", () ->
            ComponentesContabeis.abrirDialogo(this, "Detalhes da validação", new PainelDetalhesValidacao())),
        ComponentesContabeis.botao("ecf.validacoes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
  }
}
