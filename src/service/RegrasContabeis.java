package service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import model.*;

public final class RegrasContabeis {
  private RegrasContabeis() {}

  public static void texto(String valor, int limite, boolean obrigatorio, String nome) {
    if (valor == null && !obrigatorio) return;
    if (valor == null || (obrigatorio && valor.trim().isEmpty()) || valor.length() > limite)
      throw new IllegalArgumentException(
          nome
              + ": informe "
              + (obrigatorio ? "um valor de " : "no máximo ")
              + limite
              + " caracteres.");
  }

  public static void dinheiro(BigDecimal valor, boolean positivo, String nome) {
    if (valor == null
        || valor.scale() > 2
        || valor.abs().compareTo(new BigDecimal("9999999999999.99")) > 0
        || (positivo && valor.signum() <= 0))
      throw new IllegalArgumentException(nome + ": valor inválido para DECIMAL(15,2).");
  }

  public static void periodo(LocalDate inicio, LocalDate fim) {
    if (inicio == null
        || fim == null
        || inicio.getYear() < 1000
        || fim.getYear() > 9999
        || inicio.isAfter(fim))
      throw new IllegalArgumentException("Informe um período válido entre os anos 1000 e 9999.");
  }

  public static void lancamento(LancamentoContabil l, boolean contabilizar) {
    texto(l.getDocumento(), 50, true, "Documento");
    texto(l.getHistorico(), 255, true, "Descrição");
    if (l.getData() == null || l.getExercicioSocialId() <= 0)
      throw new IllegalArgumentException("Informe data e exercício social.");
    Set<Long> ids = new HashSet<>();
    for (ItemLancamento p : l.getItens()) {
      if (p == null) throw new IllegalArgumentException("A partida não pode ser nula.");
      if (p.getId() != 0 && !ids.add(p.getId()))
        throw new IllegalArgumentException(
            "Uma partida não pode aparecer duas vezes no lançamento.");
      if (p.getContaId() <= 0 || p.getTipoMovimento() == null)
        throw new IllegalArgumentException("Selecione a conta e o movimento da partida.");
      dinheiro(p.getValor(), true, "Valor da partida");
      texto(p.getDescricao(), 255, false, "Descrição da partida");
    }
    if (contabilizar && (l.getItens().isEmpty() || !l.estaBalanceado()))
      throw new IllegalStateException(
          "Para contabilizar, informe partidas e iguale os totais de débito e crédito.");
  }

  public static List<InconsistenciaAuditoria> analisar(LancamentoContabil l) {
    List<InconsistenciaAuditoria> achados = new ArrayList<>();
    if (l.getStatus() == StatusLancamento.ESTORNADO) return achados;
    if (l.getItens().isEmpty())
      achado(
          achados,
          l,
          null,
          "AUD001",
          "Lançamento sem partidas",
          "Inclua partidas antes de contabilizar.",
          NivelSeveridade.CRITICA);
    if (!l.estaBalanceado())
      achado(
          achados,
          l,
          null,
          "AUD002",
          "Débito diferente de crédito",
          "Revise as partidas. Diferença: " + l.calcularDiferenca(),
          NivelSeveridade.CRITICA);
    if (l.getHistorico() == null || l.getHistorico().trim().isEmpty())
      achado(
          achados,
          l,
          null,
          "AUD003",
          "Descrição vazia",
          "Descreva o fato contábil.",
          NivelSeveridade.MEDIA);
    if (l.getDocumento() == null || l.getDocumento().trim().isEmpty())
      achado(
          achados,
          l,
          null,
          "AUD004",
          "Documento vazio",
          "Informe o documento de origem.",
          NivelSeveridade.BAIXA);
    for (ItemLancamento p : l.getItens()) {
      if (p.getContaId() <= 0)
        achado(
            achados,
            l,
            p,
            "AUD005",
            "Partida sem conta",
            "Vincule uma conta analítica ativa.",
            NivelSeveridade.ALTA);
      if (p.getValor() == null || p.getValor().signum() <= 0)
        achado(
            achados,
            l,
            p,
            "AUD006",
            "Valor não positivo",
            "Informe valor positivo.",
            NivelSeveridade.CRITICA);
    }
    return achados;
  }

  private static void achado(
      List<InconsistenciaAuditoria> lista,
      LancamentoContabil l,
      ItemLancamento p,
      String codigo,
      String descricao,
      String recomendacao,
      NivelSeveridade severidade) {
    InconsistenciaAuditoria a = new InconsistenciaAuditoria();
    a.setCodigo(codigo);
    a.setDescricao(descricao);
    a.setRecomendacao(recomendacao);
    a.setSeveridade(severidade);
    a.setLancamentoId(l.getId());
    a.setPartidaId(p == null ? null : p.getId());
    lista.add(a);
  }
}
