package dao;

import java.sql.*;
import java.util.*;
import model.*;

public class EcfDAO {
  public List<GeracaoECF> listar(Connection c, long empresa) throws SQLException {
    return carregar(c, empresa, null, false);
  }

  public GeracaoECF buscar(Connection c, long empresa, long id, boolean bloquear)
      throws SQLException {
    List<GeracaoECF> lista = carregar(c, empresa, id, bloquear);
    if (lista.isEmpty()) throw new IllegalArgumentException("ECF não encontrada nesta empresa.");
    return lista.get(0);
  }

  private List<GeracaoECF> carregar(Connection c, long empresa, Long id, boolean bloquear)
      throws SQLException {
    String filtro = " WHERE g.empresa_id=? AND (? IS NULL OR g.id=?)";
    String trava = bloquear ? " FOR UPDATE" : "";
    List<GeracaoECF> lista =
        Jdbc.listar(
            c,
            "SELECT g.*,e.razao_social empresa FROM ecf_geracao g JOIN shared_empresa e ON"
                + " e.id=g.empresa_id"
                + filtro
                + " ORDER BY g.id DESC"
                + trava,
            rs -> {
              GeracaoECF g = MapeamentoContabil.geracaoECF(rs);
              g.setEmpresa(rs.getString("empresa"));
              return g;
            },
            empresa,
            id,
            id);
    Map<Long, GeracaoECF> geracoes = new HashMap<>();
    for (GeracaoECF g : lista) geracoes.put(g.getId(), g);
    if (lista.isEmpty()) return lista;
    for (ValidacaoECF v :
        Jdbc.listar(
            c,
            "SELECT v.* FROM ecf_validacao v JOIN ecf_geracao g ON g.id=v.ecf_id"
                + filtro
                + " ORDER BY v.id"
                + trava,
            MapeamentoContabil::validacaoECF,
            empresa,
            id,
            id)) geracoes.get(v.getEcfId()).getValidacoes().add(v);
    Map<Long, EcfApuracao> apuracoes = new HashMap<>();
    for (EcfApuracao a :
        Jdbc.listar(
            c,
            "SELECT a.* FROM ecf_apuracao a JOIN ecf_geracao g ON g.id=a.ecf_id"
                + filtro
                + " ORDER BY a.id"
                + trava,
            MapeamentoContabil::ecfApuracao,
            empresa,
            id,
            id)) {
      geracoes.get(a.getEcfId()).getApuracoes().add(a);
      apuracoes.put(a.getId(), a);
    }
    for (EcfAjuste a :
        Jdbc.listar(
            c,
            "SELECT a.* FROM ecf_ajuste a JOIN ecf_apuracao p ON p.id=a.apuracao_id JOIN"
                + " ecf_geracao g ON g.id=p.ecf_id"
                + filtro
                + " ORDER BY a.id"
                + trava,
            MapeamentoContabil::ecfAjuste,
            empresa,
            id,
            id)) apuracoes.get(a.getApuracaoId()).getAjustes().add(a);
    for (EcfSignatario s :
        Jdbc.listar(
            c,
            "SELECT s.* FROM ecf_signatario s JOIN ecf_geracao g ON g.id=s.ecf_id"
                + filtro
                + " ORDER BY s.id"
                + trava,
            MapeamentoContabil::ecfSignatario,
            empresa,
            id,
            id)) geracoes.get(s.getEcfId()).getSignatarios().add(s);
    return lista;
  }

  public long preparar(Connection c, GeracaoECF g) throws SQLException {
    if (g.getId() == 0)
      return Jdbc.inserir(
          c,
          "INSERT INTO ecf_geracao"
              + " (empresa_id,exercicio_social_id,ecd_geracao_id,data_inicio,data_fim,finalidade,ecf_retificada_id,regime_tributacao,versao_layout,observacao)"
              + " VALUES (?,?,?,?,?,?,?,?,?,?)",
          g.getEmpresaId(),
          g.getExercicioSocialId(),
          g.getEcdGeracaoId(),
          g.getPeriodoInicial(),
          g.getPeriodoFinal(),
          g.getFinalidade(),
          g.getEcfRetificadaId(),
          g.getRegimeTributacao(),
          g.getVersaoLayout(),
          g.getObservacao());
    Jdbc.executar(
        c,
        "UPDATE ecf_geracao SET"
            + " exercicio_social_id=?,ecd_geracao_id=?,data_inicio=?,data_fim=?,finalidade=?,ecf_retificada_id=?,regime_tributacao=?,versao_layout=?,observacao=?,status='EM_PREPARACAO'"
            + " WHERE id=? AND empresa_id=?",
        g.getExercicioSocialId(),
        g.getEcdGeracaoId(),
        g.getPeriodoInicial(),
        g.getPeriodoFinal(),
        g.getFinalidade(),
        g.getEcfRetificadaId(),
        g.getRegimeTributacao(),
        g.getVersaoLayout(),
        g.getObservacao(),
        g.getId(),
        g.getEmpresaId());
    return g.getId();
  }

  public void validar(Connection c, GeracaoECF g) throws SQLException {
    Jdbc.executar(c, "DELETE FROM ecf_validacao WHERE ecf_id=?", g.getId());
    for (ValidacaoECF v : g.getValidacoes())
      Jdbc.inserir(
          c,
          "INSERT INTO ecf_validacao (ecf_id,tipo,codigo,descricao,referencia) VALUES (?,?,?,?,?)",
          g.getId(),
          v.getTipo(),
          v.getCodigo(),
          v.getDescricao(),
          v.getReferencia());
    Jdbc.executar(
        c,
        "UPDATE ecf_geracao SET status=? WHERE id=? AND empresa_id=?",
        g.getStatus(),
        g.getId(),
        g.getEmpresaId());
  }

  public void registrarArquivo(Connection c, GeracaoECF g, long usuario, String nome, String hash)
      throws SQLException {
    Jdbc.executar(
        c,
        "UPDATE ecf_geracao SET"
            + " status='GERADA',nome_arquivo=?,hash_arquivo=?,gerado_por=?,gerado_em=CURRENT_TIMESTAMP"
            + " WHERE id=? AND empresa_id=?",
        nome,
        hash,
        usuario,
        g.getId(),
        g.getEmpresaId());
  }

  public long salvarApuracao(Connection c, EcfApuracao a) throws SQLException {
    if (a.getId() != 0) {
      Jdbc.executar(
          c,
          "UPDATE ecf_apuracao SET"
              + " tributo=?,data_inicio=?,data_fim=?,base_inicial=?,base_calculo=?,aliquota=?,valor_tributo=?,status='EM_CALCULO'"
              + " WHERE id=? AND ecf_id=?",
          a.getTributo(),
          a.getDataInicio(),
          a.getDataFim(),
          a.getBaseInicial(),
          a.getBaseCalculo(),
          a.getAliquota(),
          a.getValorTributo(),
          a.getId(),
          a.getEcfId());
      return a.getId();
    }
    return Jdbc.inserir(
        c,
        "INSERT INTO ecf_apuracao"
            + " (ecf_id,tributo,data_inicio,data_fim,base_inicial,total_adicoes,total_exclusoes,total_compensacoes,base_calculo,aliquota,valor_tributo,status)"
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
        a.getEcfId(),
        a.getTributo(),
        a.getDataInicio(),
        a.getDataFim(),
        a.getBaseInicial(),
        a.getTotalAdicoes(),
        a.getTotalExclusoes(),
        a.getTotalCompensacoes(),
        a.getBaseCalculo(),
        a.getAliquota(),
        a.getValorTributo(),
        a.getStatus());
  }

  public long salvarAjuste(Connection c, EcfAjuste a) throws SQLException {
    return Jdbc.inserir(
        c,
        "INSERT INTO ecf_ajuste"
            + " (apuracao_id,tipo,codigo,descricao,valor,conta_id,documento_referencia,observacao)"
            + " VALUES (?,?,?,?,?,?,?,?)",
        a.getApuracaoId(),
        a.getTipo(),
        a.getCodigo(),
        a.getDescricao(),
        a.getValor(),
        a.getContaId(),
        a.getDocumentoReferencia(),
        a.getObservacao());
  }

  public long salvarSignatario(Connection c, EcfSignatario s) throws SQLException {
    return Jdbc.inserir(
        c,
        "INSERT INTO ecf_signatario (ecf_id,nome,cpf,tipo,crc,uf_crc,email) VALUES (?,?,?,?,?,?,?)",
        s.getEcfId(),
        s.getNome(),
        s.getCpf(),
        s.getTipo(),
        s.getCrc(),
        s.getUfCrc(),
        s.getEmail());
  }

  public void invalidar(Connection c, long id) throws SQLException {
    Jdbc.executar(c, "DELETE FROM ecf_validacao WHERE ecf_id=?", id);
    Jdbc.executar(
        c,
        "UPDATE ecf_geracao SET"
            + " status='EM_PREPARACAO',nome_arquivo=NULL,hash_arquivo=NULL,gerado_por=NULL,gerado_em=NULL"
            + " WHERE id=?",
        id);
  }

  public int recalcularAjustes(Connection c, long id) throws SQLException {
    return Jdbc.executar(
        c,
        "UPDATE ecf_apuracao SET total_adicoes=(SELECT COALESCE(SUM(valor),0) FROM ecf_ajuste WHERE"
            + " apuracao_id=? AND tipo='ADICAO'),total_exclusoes=(SELECT COALESCE(SUM(valor),0)"
            + " FROM ecf_ajuste WHERE apuracao_id=? AND tipo='EXCLUSAO'),total_compensacoes=(SELECT"
            + " COALESCE(SUM(valor),0) FROM ecf_ajuste WHERE apuracao_id=? AND tipo='COMPENSACAO')"
            + " WHERE id=?",
        id,
        id,
        id,
        id);
  }

  public int excluirSignatario(Connection c, long ecfId, long id) throws SQLException {
    return Jdbc.executar(
        c, "DELETE FROM ecf_signatario WHERE ecf_id=? AND id=? AND assinou=FALSE", ecfId, id);
  }

  public int excluirAjustesApuracao(Connection c, long id) throws SQLException {
    return Jdbc.executar(c, "DELETE FROM ecf_ajuste WHERE apuracao_id=?", id);
  }

  public int excluirApuracao(Connection c, long id, long ecfId) throws SQLException {
    return Jdbc.executar(c, "DELETE FROM ecf_apuracao WHERE id=? AND ecf_id=?", id, ecfId);
  }

  public int excluirAjuste(Connection c, long id, long apuracaoId) throws SQLException {
    return Jdbc.executar(c, "DELETE FROM ecf_ajuste WHERE id=? AND apuracao_id=?", id, apuracaoId);
  }
}
