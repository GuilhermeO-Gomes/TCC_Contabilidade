package dao;

import java.sql.*;
import model.*;

public final class MapeamentoContabil {
  private MapeamentoContabil() {}

  public static LancamentoContabil lancamentoContabil(ResultSet rs) throws SQLException {
    LancamentoContabil obj = new LancamentoContabil();
    obj.setId(rs.getLong("id"));
    obj.setEmpresaId(rs.getLong("empresa_id"));
    obj.setExercicioSocialId(rs.getLong("exercicio_social_id"));
    obj.setNumeroDocumento(rs.getString("numero_documento"));
    obj.setData(
        rs.getDate("data_lancamento") == null ? null : rs.getDate("data_lancamento").toLocalDate());
    obj.setDescricao(rs.getString("descricao"));
    obj.setOrigem(rs.getString("origem"));
    obj.setStatus(StatusLancamento.valueOf(rs.getString("status")));
    obj.setHistoricoPadraoId(Jdbc.idOpcional(rs, "historico_padrao_id"));
    obj.setLoteId(Jdbc.idOpcional(rs, "lote_id"));
    obj.setCriadoPor(Jdbc.idOpcional(rs, "criado_por"));
    obj.setContabilizadoPor(Jdbc.idOpcional(rs, "contabilizado_por"));
    obj.setDataContabilizacao(
        rs.getTimestamp("data_contabilizacao") == null
            ? null
            : rs.getTimestamp("data_contabilizacao").toLocalDateTime());
    obj.setEstornoDeId(Jdbc.idOpcional(rs, "estorno_de_id"));
    obj.setCriadoEm(
        rs.getTimestamp("criado_em") == null
            ? null
            : rs.getTimestamp("criado_em").toLocalDateTime());
    return obj;
  }

  public static ItemLancamento itemLancamento(ResultSet rs) throws SQLException {
    ItemLancamento obj = new ItemLancamento();
    obj.setId(rs.getLong("id"));
    obj.setLancamentoId(rs.getLong("lancamento_id"));
    obj.setContaId(rs.getLong("conta_id"));
    obj.setCentroCustoId(Jdbc.idOpcional(rs, "centro_custo_id"));
    obj.setTipoMovimento(TipoMovimento.valueOf(rs.getString("tipo_movimento")));
    obj.setValor(rs.getBigDecimal("valor"));
    obj.setDescricao(rs.getString("descricao"));
    return obj;
  }

  public static PlanoContas planoContas(ResultSet rs) throws SQLException {
    PlanoContas obj = new PlanoContas();
    obj.setId(rs.getLong("id"));
    obj.setEmpresaId(rs.getLong("empresa_id"));
    obj.setCodigo(rs.getString("codigo"));
    obj.setDescricao(rs.getString("descricao"));
    obj.setTipo(TipoConta.valueOf(rs.getString("tipo")));
    obj.setNatureza(NaturezaConta.valueOf(rs.getString("natureza")));
    obj.setAtivo(rs.getBoolean("ativo"));
    obj.setContaPaiId(Jdbc.idOpcional(rs, "conta_pai_id"));
    obj.setCodigoAglutinacaoId(Jdbc.idOpcional(rs, "codigo_aglutinacao_id"));
    return obj;
  }

  public static ExercicioSocial exercicioSocial(ResultSet rs) throws SQLException {
    ExercicioSocial obj = new ExercicioSocial();
    obj.setId(rs.getLong("id"));
    obj.setEmpresaId(rs.getLong("empresa_id"));
    obj.setAnoReferencia(rs.getInt("ano_referencia"));
    obj.setDataInicio(
        rs.getDate("data_inicio") == null ? null : rs.getDate("data_inicio").toLocalDate());
    obj.setDataFim(rs.getDate("data_fim") == null ? null : rs.getDate("data_fim").toLocalDate());
    obj.setAtivo(rs.getBoolean("ativo"));
    obj.setStatusContabil(ExercicioSocial.StatusContabil.valueOf(rs.getString("status_contabil")));
    return obj;
  }

  public static InconsistenciaAuditoria inconsistenciaAuditoria(ResultSet rs) throws SQLException {
    InconsistenciaAuditoria obj = new InconsistenciaAuditoria();
    obj.setId(rs.getLong("id"));
    obj.setCodigo(rs.getString("codigo"));
    obj.setDescricao(rs.getString("descricao"));
    obj.setRecomendacao(rs.getString("recomendacao"));
    obj.setSeveridade(NivelSeveridade.valueOf(rs.getString("severidade")));
    obj.setStatus(StatusAuditoria.valueOf(rs.getString("status")));
    obj.setLancamentoId(Jdbc.idOpcional(rs, "lancamento_id"));
    obj.setPartidaId(Jdbc.idOpcional(rs, "partida_id"));
    obj.setDetectadoEm(
        rs.getTimestamp("detectado_em") == null
            ? null
            : rs.getTimestamp("detectado_em").toLocalDateTime());
    obj.setRevisadoPor(Jdbc.idOpcional(rs, "revisado_por"));
    obj.setRevisadoEm(
        rs.getTimestamp("revisado_em") == null
            ? null
            : rs.getTimestamp("revisado_em").toLocalDateTime());
    obj.setObservacaoRevisao(rs.getString("observacao_revisao"));
    return obj;
  }

  public static GeracaoECF geracaoECF(ResultSet rs) throws SQLException {
    GeracaoECF obj = new GeracaoECF();
    obj.setId(rs.getLong("id"));
    obj.setEmpresaId(rs.getLong("empresa_id"));
    obj.setExercicioSocialId(rs.getLong("exercicio_social_id"));
    obj.setEcdGeracaoId(Jdbc.idOpcional(rs, "ecd_geracao_id"));
    obj.setPeriodoInicial(
        rs.getDate("data_inicio") == null ? null : rs.getDate("data_inicio").toLocalDate());
    obj.setPeriodoFinal(
        rs.getDate("data_fim") == null ? null : rs.getDate("data_fim").toLocalDate());
    obj.setFinalidade(FinalidadeECF.valueOf(rs.getString("finalidade")));
    obj.setEcfRetificadaId(Jdbc.idOpcional(rs, "ecf_retificada_id"));
    obj.setRegimeTributacao(RegimeTributacao.valueOf(rs.getString("regime_tributacao")));
    obj.setVersaoLayout(rs.getString("versao_layout"));
    obj.setStatus(StatusECF.valueOf(rs.getString("status")));
    obj.setNomeArquivo(rs.getString("nome_arquivo"));
    obj.setHashArquivo(rs.getString("hash_arquivo"));
    obj.setNumeroRecibo(rs.getString("numero_recibo"));
    obj.setGeradoPor(Jdbc.idOpcional(rs, "gerado_por"));
    obj.setGeradoEm(
        rs.getTimestamp("gerado_em") == null
            ? null
            : rs.getTimestamp("gerado_em").toLocalDateTime());
    obj.setTransmitidoEm(
        rs.getTimestamp("transmitido_em") == null
            ? null
            : rs.getTimestamp("transmitido_em").toLocalDateTime());
    obj.setObservacao(rs.getString("observacao"));
    obj.setCriadoEm(
        rs.getTimestamp("criado_em") == null
            ? null
            : rs.getTimestamp("criado_em").toLocalDateTime());
    return obj;
  }

  public static ValidacaoECF validacaoECF(ResultSet rs) throws SQLException {
    ValidacaoECF obj = new ValidacaoECF();
    obj.setId(rs.getLong("id"));
    obj.setEcfId(rs.getLong("ecf_id"));
    obj.setTipo(TipoValidacao.valueOf(rs.getString("tipo")));
    obj.setCodigo(rs.getString("codigo"));
    obj.setDescricao(rs.getString("descricao"));
    obj.setReferencia(rs.getString("referencia"));
    obj.setResolvido(rs.getBoolean("resolvido"));
    obj.setResolvidoPor(Jdbc.idOpcional(rs, "resolvido_por"));
    obj.setResolvidoEm(
        rs.getTimestamp("resolvido_em") == null
            ? null
            : rs.getTimestamp("resolvido_em").toLocalDateTime());
    obj.setDetectadoEm(
        rs.getTimestamp("detectado_em") == null
            ? null
            : rs.getTimestamp("detectado_em").toLocalDateTime());
    return obj;
  }

  public static EcfApuracao ecfApuracao(ResultSet rs) throws SQLException {
    EcfApuracao obj = new EcfApuracao();
    obj.setId(rs.getLong("id"));
    obj.setEcfId(rs.getLong("ecf_id"));
    obj.setTributo(TributoECF.valueOf(rs.getString("tributo")));
    obj.setDataInicio(
        rs.getDate("data_inicio") == null ? null : rs.getDate("data_inicio").toLocalDate());
    obj.setDataFim(rs.getDate("data_fim") == null ? null : rs.getDate("data_fim").toLocalDate());
    obj.setBaseInicial(rs.getBigDecimal("base_inicial"));
    obj.setTotalAdicoes(rs.getBigDecimal("total_adicoes"));
    obj.setTotalExclusoes(rs.getBigDecimal("total_exclusoes"));
    obj.setTotalCompensacoes(rs.getBigDecimal("total_compensacoes"));
    obj.setBaseCalculo(rs.getBigDecimal("base_calculo"));
    obj.setAliquota(rs.getBigDecimal("aliquota"));
    obj.setValorTributo(rs.getBigDecimal("valor_tributo"));
    obj.setStatus(StatusApuracaoECF.valueOf(rs.getString("status")));
    obj.setCriadoEm(
        rs.getTimestamp("criado_em") == null
            ? null
            : rs.getTimestamp("criado_em").toLocalDateTime());
    return obj;
  }

  public static EcfAjuste ecfAjuste(ResultSet rs) throws SQLException {
    EcfAjuste obj = new EcfAjuste();
    obj.setId(rs.getLong("id"));
    obj.setApuracaoId(rs.getLong("apuracao_id"));
    obj.setTipo(TipoAjusteECF.valueOf(rs.getString("tipo")));
    obj.setCodigo(rs.getString("codigo"));
    obj.setDescricao(rs.getString("descricao"));
    obj.setValor(rs.getBigDecimal("valor"));
    obj.setContaId(Jdbc.idOpcional(rs, "conta_id"));
    obj.setDocumentoReferencia(rs.getString("documento_referencia"));
    obj.setObservacao(rs.getString("observacao"));
    return obj;
  }

  public static EcfSignatario ecfSignatario(ResultSet rs) throws SQLException {
    EcfSignatario obj = new EcfSignatario();
    obj.setId(rs.getLong("id"));
    obj.setEcfId(rs.getLong("ecf_id"));
    obj.setNome(rs.getString("nome"));
    obj.setCpf(rs.getString("cpf"));
    obj.setTipo(TipoSignatarioECF.valueOf(rs.getString("tipo")));
    obj.setCrc(rs.getString("crc"));
    obj.setUfCrc(rs.getString("uf_crc"));
    obj.setEmail(rs.getString("email"));
    obj.setAssinou(rs.getBoolean("assinou"));
    obj.setAssinadoEm(
        rs.getTimestamp("assinado_em") == null
            ? null
            : rs.getTimestamp("assinado_em").toLocalDateTime());
    return obj;
  }
}
