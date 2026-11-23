package view.ecf;

import java.awt.BorderLayout;
import javax.swing.*;
import view.ComponentesContabeis;

public class TelaGeracaoECF extends JPanel {
  private static final long serialVersionUID = 1L;

  public TelaGeracaoECF() {
    super(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.nova", "Preparar nova ECF", () ->
            ComponentesContabeis.abrirDialogo(this, "Configuração da ECF", new PainelConfiguracaoECF())),
        ComponentesContabeis.botaoInativo("ecf.configurar", "Editar configuração"),
        ComponentesContabeis.botaoInativo("ecf.atualizar", "Atualizar")), BorderLayout.NORTH);

    JTable tabela = ComponentesContabeis.tabela("ecf.historico", ComponentesContabeis.modeloTabela(
        "Data", "Horário", "Usuário", "Exercício", "Tipo de escrituração", "Arquivo"));
    add(ComponentesContabeis.lista("Histórico de gerações", tabela), BorderLayout.CENTER);

    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(new JLabel("Geração disponível após a aprovação das validações."), BorderLayout.NORTH);
    rodape.add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.estrutura", "Estrutura da ECF", () ->
            ComponentesContabeis.abrirDialogo(this, "Estrutura da ECF", new PainelEstruturaECF())),
        ComponentesContabeis.botao("ecf.detalhes", "Ver validações", () ->
            ComponentesContabeis.abrirDialogo(this, "Validações da ECF", new PainelValidacaoECF())),
        ComponentesContabeis.botaoInativo("ecf.arquivo", "Gerar arquivo ECF (.txt)")), BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
  }
}
