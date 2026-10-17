package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class CadastroContabilDAO {
  public List<ReferenciaContabil> aglutinacoes(Connection c, long empresa) throws SQLException {
    return Jdbc.listar(
        c,
        "SELECT id,CONCAT(valor,' - ',descricao) rotulo FROM codigo_aglutinacao WHERE empresa_id=?"
            + " AND ativo=TRUE ORDER BY valor",
        rs -> new ReferenciaContabil(rs.getLong(1), rs.getString(2)),
        empresa);
  }

  public String verificarAcesso(Connection c, long empresa, long usuario, String permissao)
      throws SQLException {
    String empresaNome =
        Jdbc.unico(
            c,
            "SELECT e.razao_social FROM shared_empresa e JOIN shared_usuario_empresa ue ON"
                + " ue.empresa_id=e.id JOIN shared_usuario u ON u.id=ue.usuario_id WHERE e.id=? AND"
                + " u.id=? AND e.ativo=TRUE AND u.ativo=TRUE AND ue.ativo=TRUE",
            rs -> rs.getString(1),
            empresa,
            usuario);
    if (empresaNome == null)
      throw new IllegalStateException("Usuário sem vínculo ativo com esta empresa.");
    Long autorizacoes =
        Jdbc.unico(
            c,
            "SELECT COUNT(*) FROM shared_usuario_empresa ue JOIN shared_usuario_empresa_perfil up"
                + " ON up.usuario_empresa_id=ue.id JOIN shared_perfil p ON p.id=up.perfil_id JOIN"
                + " shared_perfil_permissao pp ON pp.perfil_id=p.id JOIN shared_permissao pm ON"
                + " pm.id=pp.permissao_id WHERE ue.empresa_id=? AND ue.usuario_id=? AND"
                + " ue.ativo=TRUE AND p.ativo=TRUE AND pm.ativo=TRUE AND pm.codigo=?",
            rs -> rs.getLong(1),
            empresa,
            usuario,
            permissao);
    if (autorizacoes == 0) throw new IllegalStateException("Permissão necessária: " + permissao);
    return empresaNome;
  }

  public List<ExercicioSocial> exercicios(Connection c, long empresa) throws SQLException {
    return Jdbc.listar(
        c,
        "SELECT * FROM exercicio_social WHERE empresa_id=? ORDER BY ano_referencia DESC",
        MapeamentoContabil::exercicioSocial,
        empresa);
  }

  public ExercicioSocial exercicio(Connection c, long empresa, long id) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT * FROM exercicio_social WHERE empresa_id=? AND id=? FOR UPDATE",
        MapeamentoContabil::exercicioSocial,
        empresa,
        id);
  }

  public List<ReferenciaContabil> centros(Connection c, long empresa) throws SQLException {
    return Jdbc.listar(
        c,
        "SELECT id,CONCAT(codigo,' - ',nome) rotulo FROM shared_centro_custos WHERE empresa_id=?"
            + " AND ativo=TRUE ORDER BY codigo",
        rs -> new ReferenciaContabil(rs.getLong(1), rs.getString(2)),
        empresa);
  }

  public List<ReferenciaContabil> historicos(Connection c, long empresa) throws SQLException {
    return Jdbc.listar(
        c,
        "SELECT id,descricao FROM historico_padrao WHERE empresa_id=? AND ativo=TRUE ORDER BY"
            + " descricao",
        rs -> new ReferenciaContabil(rs.getLong(1), rs.getString(2)),
        empresa);
  }

  public List<ReferenciaContabil> lotes(Connection c, long empresa) throws SQLException {
    return Jdbc.listar(
        c,
        "SELECT id,nome FROM lote_lancamento WHERE empresa_id=? AND bloqueado=FALSE ORDER BY nome",
        rs -> new ReferenciaContabil(rs.getLong(1), rs.getString(2)),
        empresa);
  }

  public Long contaAnalitica(Connection c, Long contaId, long empresaId) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT id FROM plano_contas WHERE id=? AND empresa_id=? AND ativo=TRUE AND"
            + " tipo='ANALITICA' FOR UPDATE",
        rs -> rs.getLong(1),
        contaId,
        empresaId);
  }

  public Long centroAtivo(Connection c, Long centroId, long empresaId) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT id FROM shared_centro_custos WHERE id=? AND empresa_id=? AND ativo=TRUE FOR UPDATE",
        rs -> rs.getLong(1),
        centroId,
        empresaId);
  }

  public Long historicoAtivo(Connection c, Long historicoId, long empresaId) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT id FROM historico_padrao WHERE id=? AND empresa_id=? AND ativo=TRUE FOR UPDATE",
        rs -> rs.getLong(1),
        historicoId,
        empresaId);
  }

  public Long loteDisponivel(Connection c, Long loteId, long empresaId, java.time.LocalDate data)
      throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT id FROM lote_lancamento WHERE id=? AND empresa_id=? AND bloqueado=FALSE AND ?"
            + " BETWEEN data_inicial AND data_final FOR UPDATE",
        rs -> rs.getLong(1),
        loteId,
        empresaId,
        data);
  }

  public Long ecdDaEmpresa(Connection c, Long id, long empresaId) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT id FROM ecd_geracao WHERE id=? AND empresa_id=?",
        rs -> rs.getLong(1),
        id,
        empresaId);
  }

  public Long contaAtiva(Connection c, Long id, long empresaId) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT id FROM plano_contas WHERE id=? AND empresa_id=? AND ativo=TRUE",
        rs -> rs.getLong(1),
        id,
        empresaId);
  }

  public Long aglutinacaoAtiva(Connection c, Long id, long empresaId) throws SQLException {
    return Jdbc.unico(
        c,
        "SELECT id FROM codigo_aglutinacao WHERE id=? AND empresa_id=? AND ativo=TRUE FOR UPDATE",
        rs -> rs.getLong(1),
        id,
        empresaId);
  }
}
