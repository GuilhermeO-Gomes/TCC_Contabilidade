package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import model.*;

public class AuditoriaContabilDAO {
  public List<InconsistenciaAuditoria> listar(
      Connection c, long empresa, LocalDate inicio, LocalDate fim) throws SQLException {
    return Jdbc.listar(
        c,
        "SELECT a.*,l.data_lancamento,CONCAT(pc.codigo,' - ',pc.descricao) conta FROM"
            + " auditoria_inconsistencia a JOIN lancamento_contabil l ON l.id=a.lancamento_id LEFT"
            + " JOIN partida_lancamento p ON p.id=a.partida_id LEFT JOIN plano_contas pc ON"
            + " pc.id=p.conta_id WHERE l.empresa_id=? AND l.data_lancamento BETWEEN ? AND ? ORDER"
            + " BY a.detectado_em DESC,a.id DESC",
        rs -> {
          InconsistenciaAuditoria a = MapeamentoContabil.inconsistenciaAuditoria(rs);
          a.setData(rs.getDate("data_lancamento").toLocalDate());
          a.setConta(rs.getString("conta"));
          return a;
        },
        empresa,
        inicio,
        fim);
  }

  public void registrar(Connection c, InconsistenciaAuditoria a) throws SQLException {
    Long id =
        Jdbc.unico(
            c,
            "SELECT id FROM auditoria_inconsistencia WHERE lancamento_id=? AND codigo=? AND"
                + " (partida_id=? OR (partida_id IS NULL AND ? IS NULL)) ORDER BY id LIMIT 1 FOR"
                + " UPDATE",
            rs -> rs.getLong(1),
            a.getLancamentoId(),
            a.getCodigo(),
            a.getPartidaId(),
            a.getPartidaId());
    if (id == null)
      Jdbc.inserir(
          c,
          "INSERT INTO auditoria_inconsistencia"
              + " (codigo,descricao,recomendacao,severidade,lancamento_id,partida_id) VALUES"
              + " (?,?,?,?,?,?)",
          a.getCodigo(),
          a.getDescricao(),
          a.getRecomendacao(),
          a.getSeveridade(),
          a.getLancamentoId(),
          a.getPartidaId());
  }

  public void revisar(
      Connection c, long empresa, long id, long usuario, StatusAuditoria status, String observacao)
      throws SQLException {
    int n =
        Jdbc.executar(
            c,
            "UPDATE auditoria_inconsistencia a JOIN lancamento_contabil l ON l.id=a.lancamento_id"
                + " SET a.status=?,a.revisado_por=?,a.revisado_em=CURRENT_TIMESTAMP,a.observacao_revisao=?"
                + " WHERE a.id=? AND l.empresa_id=?",
            status,
            usuario,
            observacao,
            id,
            empresa);
    if (n != 1) throw new IllegalArgumentException("Inconsistência não encontrada nesta empresa.");
  }

  public int excluirDoRascunho(Connection c, long id) throws SQLException {
    return Jdbc.executar(c, "DELETE FROM auditoria_inconsistencia WHERE lancamento_id=?", id);
  }
}
