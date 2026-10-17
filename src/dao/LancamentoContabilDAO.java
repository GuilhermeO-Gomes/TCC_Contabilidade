package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import model.*;

public class LancamentoContabilDAO {
  public List<LancamentoContabil> recentes(
      Connection c, long empresa, LocalDate inicio, LocalDate fim) throws SQLException {
    List<LancamentoContabil> lista =
        Jdbc.listar(
            c,
            "SELECT * FROM lancamento_contabil WHERE empresa_id=? AND data_lancamento BETWEEN ? AND"
                + " ? AND status IN ('CONTABILIZADO','ESTORNADO') ORDER BY data_lancamento DESC,id"
                + " DESC LIMIT 10",
            MapeamentoContabil::lancamentoContabil,
            empresa,
            inicio,
            fim);
    preencherPartidas(c, empresa, lista, false);
    return lista;
  }

  public List<LancamentoContabil> listar(
      Connection c,
      long empresa,
      LocalDate inicio,
      LocalDate fim,
      String documento,
      String descricao,
      String conta,
      StatusLancamento status,
      int limite)
      throws SQLException {
    String sql =
        "SELECT l.* FROM lancamento_contabil l WHERE l.empresa_id=? AND l.data_lancamento BETWEEN ?"
            + " AND ? AND l.numero_documento LIKE ? AND l.descricao LIKE ? AND (? IS NULL OR"
            + " l.status=?) AND (?='' OR EXISTS (SELECT 1 FROM partida_lancamento p JOIN"
            + " plano_contas pc ON pc.id=p.conta_id AND pc.empresa_id=l.empresa_id WHERE"
            + " p.lancamento_id=l.id AND CONCAT(pc.codigo,' ',pc.descricao) LIKE ?)) ORDER BY"
            + " l.data_lancamento DESC,l.id DESC";
    if (limite > 0) sql += " LIMIT " + limite;
    List<LancamentoContabil> lista =
        Jdbc.listar(
            c,
            sql,
            MapeamentoContabil::lancamentoContabil,
            empresa,
            inicio,
            fim,
            "%" + documento + "%",
            "%" + descricao + "%",
            status,
            status,
            conta,
            "%" + conta + "%");
    preencherPartidas(c, empresa, lista, false);
    return lista;
  }

  public LancamentoContabil buscar(Connection c, long empresa, long id, boolean bloquear)
      throws SQLException {
    LancamentoContabil l =
        Jdbc.unico(
            c,
            "SELECT * FROM lancamento_contabil WHERE empresa_id=? AND id=?"
                + (bloquear ? " FOR UPDATE" : ""),
            MapeamentoContabil::lancamentoContabil,
            empresa,
            id);
    if (l == null) throw new IllegalArgumentException("Lançamento não encontrado nesta empresa.");
    preencherPartidas(c, empresa, Collections.singletonList(l), bloquear);
    return l;
  }

  private void preencherPartidas(
      Connection c, long empresa, List<LancamentoContabil> lista, boolean bloquear)
      throws SQLException {
    if (lista.isEmpty()) return;
    Map<Long, LancamentoContabil> porId = new HashMap<>();
    List<Object> parametros = new ArrayList<>();
    parametros.add(empresa);
    for (LancamentoContabil l : lista) {
      porId.put(l.getId(), l);
      parametros.add(l.getId());
    }
    String marcadores = String.join(",", Collections.nCopies(lista.size(), "?"));
    String sql =
        "SELECT p.*,CONCAT(pc.codigo,' - ',pc.descricao) conta,CONCAT(cc.codigo,' - ',cc.nome)"
            + " centro FROM partida_lancamento p JOIN lancamento_contabil l ON l.id=p.lancamento_id"
            + " JOIN plano_contas pc ON pc.id=p.conta_id LEFT JOIN shared_centro_custos cc ON"
            + " cc.id=p.centro_custo_id WHERE l.empresa_id=? AND l.id IN ("
            + marcadores
            + ") ORDER BY p.id"
            + (bloquear ? " FOR UPDATE" : "");
    for (ItemLancamento item :
        Jdbc.listar(
            c,
            sql,
            rs -> {
              ItemLancamento p = MapeamentoContabil.itemLancamento(rs);
              p.setConta(rs.getString("conta"));
              p.setCentroCusto(rs.getString("centro"));
              return p;
            },
            parametros.toArray())) porId.get(item.getLancamentoId()).adicionarItem(item);
  }

  public long salvar(Connection c, LancamentoContabil l, long usuario) throws SQLException {
    if (l.getId() == 0)
      return Jdbc.inserir(
          c,
          "INSERT INTO lancamento_contabil"
              + " (empresa_id,exercicio_social_id,numero_documento,data_lancamento,descricao,origem,status,historico_padrao_id,lote_id,criado_por,estorno_de_id)"
              + " VALUES (?,?,?,?,?,?,'RASCUNHO',?,?,?,?)",
          l.getEmpresaId(),
          l.getExercicioSocialId(),
          l.getDocumento(),
          l.getData(),
          l.getHistorico(),
          l.getOrigem(),
          l.getHistoricoPadraoId(),
          l.getLoteId(),
          usuario,
          l.getEstornoDeId());
    int n =
        Jdbc.executar(
            c,
            "UPDATE lancamento_contabil SET"
                + " exercicio_social_id=?,numero_documento=?,data_lancamento=?,descricao=?,historico_padrao_id=?,lote_id=?"
                + " WHERE empresa_id=? AND id=? AND status='RASCUNHO'",
            l.getExercicioSocialId(),
            l.getDocumento(),
            l.getData(),
            l.getHistorico(),
            l.getHistoricoPadraoId(),
            l.getLoteId(),
            l.getEmpresaId(),
            l.getId());
    if (n != 1) throw new IllegalStateException("Somente rascunhos podem ser alterados.");
    return l.getId();
  }

  public void salvarPartidas(Connection c, LancamentoContabil l, List<ItemLancamento> antigas)
      throws SQLException {
    Set<Long> mantidas = new HashSet<>();
    for (ItemLancamento p : l.getItens()) {
      if (p.getId() == 0)
        p.setId(
            Jdbc.inserir(
                c,
                "INSERT INTO partida_lancamento"
                    + " (lancamento_id,conta_id,centro_custo_id,tipo_movimento,valor,descricao)"
                    + " VALUES (?,?,?,?,?,?)",
                l.getId(),
                p.getContaId(),
                p.getCentroCustoId(),
                p.getTipoMovimento(),
                p.getValor(),
                p.getDescricao()));
      else {
        if (antigas.stream().noneMatch(a -> a.getId() == p.getId()))
          throw new IllegalArgumentException("Partida não pertence ao lançamento.");
        Jdbc.executar(
            c,
            "UPDATE partida_lancamento SET"
                + " conta_id=?,centro_custo_id=?,tipo_movimento=?,valor=?,descricao=? WHERE id=?"
                + " AND lancamento_id=?",
            p.getContaId(),
            p.getCentroCustoId(),
            p.getTipoMovimento(),
            p.getValor(),
            p.getDescricao(),
            p.getId(),
            l.getId());
      }
      mantidas.add(p.getId());
    }
    for (ItemLancamento p : antigas)
      if (!mantidas.contains(p.getId())) {
        Jdbc.executar(
            c,
            "DELETE FROM auditoria_inconsistencia WHERE partida_id=? AND lancamento_id=?",
            p.getId(),
            l.getId());
        Jdbc.executar(
            c,
            "DELETE FROM partida_lancamento WHERE id=? AND lancamento_id=?",
            p.getId(),
            l.getId());
      }
  }

  public void evento(
      Connection c, LancamentoContabil l, long usuario, String tipo, String descricao)
      throws SQLException {
    Jdbc.inserir(
        c,
        "INSERT INTO auditoria (usuario_id,lancamento_id,exercicio_social_id,tipo_evento,descricao)"
            + " VALUES (?,?,?,?,?)",
        usuario,
        l.getId(),
        l.getExercicioSocialId(),
        tipo,
        descricao);
  }

  public DashboardResumo resumo(Connection c, long empresa, LocalDate inicio, LocalDate fim)
      throws SQLException {
    DashboardResumo resumo =
        Jdbc.unico(
            c,
            "SELECT COUNT(DISTINCT l.id) quantidade,COALESCE(SUM(CASE WHEN"
                + " p.tipo_movimento='DEBITO' THEN p.valor ELSE 0 END),0) debitos,COALESCE(SUM(CASE"
                + " WHEN p.tipo_movimento='CREDITO' THEN p.valor ELSE 0 END),0) creditos FROM"
                + " lancamento_contabil l LEFT JOIN partida_lancamento p ON p.lancamento_id=l.id"
                + " WHERE l.empresa_id=? AND l.data_lancamento BETWEEN ? AND ? AND l.status IN"
                + " ('CONTABILIZADO','ESTORNADO')",
            rs -> {
              DashboardResumo r = new DashboardResumo();
              r.setQuantidade(rs.getLong("quantidade"));
              r.setDebitos(rs.getBigDecimal("debitos"));
              r.setCreditos(rs.getBigDecimal("creditos"));
              return r;
            },
            empresa,
            inicio,
            fim);
    return resumo;
  }

  public int contabilizar(Connection c, long usuarioId, long id, long empresaId)
      throws SQLException {
    return Jdbc.executar(
        c,
        "UPDATE lancamento_contabil SET"
            + " status='CONTABILIZADO',contabilizado_por=?,data_contabilizacao=CURRENT_TIMESTAMP"
            + " WHERE id=? AND empresa_id=? AND status='RASCUNHO'",
        usuarioId,
        id,
        empresaId);
  }

  public int preservarEventosExclusao(Connection c, long id) throws SQLException {
    return Jdbc.executar(
        c,
        "UPDATE auditoria SET descricao=CONCAT(LEFT(descricao,440),' [rascunho excluído"
            + " #',?,']'),lancamento_id=NULL WHERE lancamento_id=?",
        id,
        id);
  }

  public int excluirPartidas(Connection c, long id) throws SQLException {
    return Jdbc.executar(c, "DELETE FROM partida_lancamento WHERE lancamento_id=?", id);
  }

  public int excluirRascunho(Connection c, long id, long empresaId) throws SQLException {
    return Jdbc.executar(
        c,
        "DELETE FROM lancamento_contabil WHERE id=? AND empresa_id=? AND status='RASCUNHO'",
        id,
        empresaId);
  }

  public int contabilizarEstorno(Connection c, long usuarioId, long id, long empresaId)
      throws SQLException {
    return Jdbc.executar(
        c,
        "UPDATE lancamento_contabil SET"
            + " status='CONTABILIZADO',contabilizado_por=?,data_contabilizacao=CURRENT_TIMESTAMP"
            + " WHERE id=? AND empresa_id=?",
        usuarioId,
        id,
        empresaId);
  }

  public int marcarEstornado(Connection c, long id, long empresaId) throws SQLException {
    return Jdbc.executar(
        c,
        "UPDATE lancamento_contabil SET status='ESTORNADO' WHERE id=? AND empresa_id=?",
        id,
        empresaId);
  }
}
