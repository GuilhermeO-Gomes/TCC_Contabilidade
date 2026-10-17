package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class PlanoContasDAO {
  public List<PlanoContas> listar(Connection c, long empresa, boolean somenteAnaliticas)
      throws SQLException {
    return Jdbc.listar(
        c,
        "SELECT * FROM plano_contas WHERE empresa_id=?"
            + (somenteAnaliticas ? " AND tipo='ANALITICA' AND ativo=TRUE" : "")
            + " ORDER BY codigo",
        MapeamentoContabil::planoContas,
        empresa);
  }

  public long salvar(Connection c, PlanoContas p) throws SQLException {
    if (p.getId() == 0)
      return Jdbc.inserir(
          c,
          "INSERT INTO plano_contas"
              + " (empresa_id,codigo,descricao,tipo,natureza,ativo,conta_pai_id,codigo_aglutinacao_id)"
              + " VALUES (?,?,?,?,?,?,?,?)",
          p.getEmpresaId(),
          p.getCodigo(),
          p.getDescricao(),
          p.getTipo(),
          p.getNatureza(),
          p.getAtivo(),
          p.getContaPaiId(),
          p.getCodigoAglutinacaoId());
    if (Jdbc.executar(
            c,
            "UPDATE plano_contas SET"
                + " codigo=?,descricao=?,tipo=?,natureza=?,ativo=?,conta_pai_id=?,codigo_aglutinacao_id=?"
                + " WHERE id=? AND empresa_id=?",
            p.getCodigo(),
            p.getDescricao(),
            p.getTipo(),
            p.getNatureza(),
            p.getAtivo(),
            p.getContaPaiId(),
            p.getCodigoAglutinacaoId(),
            p.getId(),
            p.getEmpresaId())
        != 1) throw new IllegalArgumentException("Conta não encontrada.");
    return p.getId();
  }

  public Long bloquearConta(Connection c, long id, long empresaId) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT id FROM plano_contas WHERE id=? AND empresa_id=? FOR UPDATE",
        rs -> rs.getLong(1),
        id,
        empresaId);
  }

  public PlanoContas buscarBloqueada(Connection c, long empresaId, long id) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT * FROM plano_contas WHERE empresa_id=? AND id=? FOR UPDATE",
        MapeamentoContabil::planoContas,
        empresaId,
        id);
  }

  public Long contarPartidas(Connection c, long id) throws SQLException {
    return Jdbc.unico(
        c, "SELECT COUNT(*) FROM partida_lancamento WHERE conta_id=?", rs -> rs.getLong(1), id);
  }

  public Long contarSubcontas(Connection c, long id, long empresaId) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT COUNT(*) FROM plano_contas WHERE conta_pai_id=? AND empresa_id=?",
        rs -> rs.getLong(1),
        id,
        empresaId);
  }
}
