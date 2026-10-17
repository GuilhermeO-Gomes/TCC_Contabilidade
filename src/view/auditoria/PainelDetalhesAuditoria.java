package view.auditoria;

import java.awt.*;
import javax.swing.*;
import model.*;
import view.ComponentesContabeis;

public class PainelDetalhesAuditoria extends JPanel {
  private static final long serialVersionUID = 1L;

  public PainelDetalhesAuditoria(
      DadosContabeis dados, InconsistenciaAuditoria a, Runnable aoAlterar) {
    setLayout(new BorderLayout(8, 8));
    setPreferredSize(new Dimension(750, 600));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JPanel campos = new JPanel(new GridBagLayout());
    String[] rotulos = {
      "Código", "Severidade", "Lançamento / partida", "Detectado em", "Status", "Revisado por / em"
    };
    Object[] valores = {
      a.getCodigo(),
      a.getSeveridade(),
      a.getLancamentoId() + " / " + a.getPartidaId(),
      a.getDetectadoEm(),
      a.getStatus(),
      a.getRevisadoPor() + " / " + a.getRevisadoEm()
    };
    for (int i = 0; i < rotulos.length; i++)
      ComponentesContabeis.adicionar_componente(
          i, campos, rotulos[i] + ":", new JLabel(String.valueOf(valores[i])));
    ComponentesContabeis.adicionar_componente(
        6, campos, "Descrição:", ComponentesContabeis.texto(a.getDescricao()));
    ComponentesContabeis.adicionar_componente(
        7, campos, "Recomendação:", ComponentesContabeis.texto(a.getRecomendacao()));
    JTextArea observacao = new JTextArea(a.getObservacaoRevisao(), 4, 35);
    observacao.setLineWrap(true);
    observacao.setWrapStyleWord(true);
    ComponentesContabeis.adicionar_componente(
        8, campos, "Observação da revisão:", new JScrollPane(observacao));
    add(new JScrollPane(campos));
    java.util.function.Consumer<StatusAuditoria> salvar =
        estado -> {
          String texto = observacao.getText();
          if (JOptionPane.showConfirmDialog(
                  this, "Confirmar status " + estado + "?", "Revisar", JOptionPane.YES_NO_OPTION)
              == JOptionPane.YES_OPTION)
            ComponentesContabeis.executar(
                this,
                () -> dados.getServico().revisarInconsistencia(a.getId(), estado, texto),
                () -> {
                  aoAlterar.run();
                  ComponentesContabeis.fechar(this);
                });
        };
    add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao(
                "auditoria.detalhes.revisar",
                "Marcar revisado",
                () -> salvar.accept(StatusAuditoria.REVISADO)),
            ComponentesContabeis.botao(
                "auditoria.detalhes.ignorar",
                "Ignorar",
                () -> salvar.accept(StatusAuditoria.IGNORADO)),
            ComponentesContabeis.botao(
                "auditoria.detalhes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
  }
}
