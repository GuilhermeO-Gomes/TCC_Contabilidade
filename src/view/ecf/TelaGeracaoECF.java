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
        "ID", "Início", "Fim", "Finalidade", "Regime", "Status", "Erros", "Avisos", "Arquivo"));
    add(ComponentesContabeis.lista("Histórico da ECF", tabela), BorderLayout.CENTER);

    add(ComponentesContabeis.botoes(
        ComponentesContabeis.botao("ecf.estrutura", "Apurações / ajustes / signatários", () ->
            ComponentesContabeis.abrirDialogo(this, "Estrutura da ECF", new PainelEstruturaECF())),
        ComponentesContabeis.botaoInativo("ecf.validar", "Validar"),
        ComponentesContabeis.botao("ecf.detalhes", "Detalhes da validação", () ->
            ComponentesContabeis.abrirDialogo(this, "Validações da ECF", new PainelValidacaoECF())),
        ComponentesContabeis.botaoInativo("ecf.arquivo", "Registrar arquivo")), BorderLayout.SOUTH);
  }
}
