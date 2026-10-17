package service;

import dao.*;
import java.math.BigDecimal;
import java.nio.file.*;
import java.security.MessageDigest;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.logging.*;
import model.*;
import util.Conexao;

public class ContabilidadeService {
  private final long empresaId;
  private final long usuarioId;
  private final CadastroContabilDAO cadastro = new CadastroContabilDAO();
  private final LancamentoContabilDAO lancamentos = new LancamentoContabilDAO();
  private final AuditoriaContabilDAO auditoria = new AuditoriaContabilDAO();
  private final EcfDAO ecf = new EcfDAO();
  private final PlanoContasDAO contas = new PlanoContasDAO();

  public ContabilidadeService(long empresaId, long usuarioId) {
    this.empresaId = empresaId;
    this.usuarioId = usuarioId;
  }

  public long getEmpresaId() {
    return empresaId;
  }

  public long getUsuarioId() {
    return usuarioId;
  }

  private interface Operacao<T> {
    T executar(Connection c) throws SQLException;
  }

  private <T> T transacao(String permissao, Operacao<T> operacao) {
    if (empresaId <= 0 || usuarioId <= 0)
      throw new IllegalStateException(
          "Defina ERP_EMPRESA_ID e ERP_USUARIO_ID com o contexto da sessão ou use"
              + " DadosContabeis(empresaId, usuarioId).");
    try (Connection c = Conexao.conectar()) {
      c.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
      c.setAutoCommit(false);
      try {
        cadastro.verificarAcesso(c, empresaId, usuarioId, permissao);
        T resultado = operacao.executar(c);
        c.commit();
        return resultado;
      } catch (SQLException | RuntimeException erro) {
        try {
          c.rollback();
        } catch (SQLException rollback) {
          erro.addSuppressed(rollback);
        }
        throw erro;
      }
    } catch (SQLException erro) {
      Logger.getLogger(getClass().getName())
          .log(Level.WARNING, "Operação contábil revertida", erro);
      throw new IllegalStateException(
          "Não foi possível concluir a operação no banco. Verifique a conexão, os vínculos e as"
              + " restrições dos dados. Nenhuma alteração parcial foi gravada.",
          erro);
    }
  }

  public String empresa() {
    return transacao(
        "CONTABILIDADE_CONSULTAR",
        c -> cadastro.verificarAcesso(c, empresaId, usuarioId, "CONTABILIDADE_CONSULTAR"));
  }

  public List<ExercicioSocial> exercicios() {
    return transacao("CONTABILIDADE_CONSULTAR", c -> cadastro.exercicios(c, empresaId));
  }

  public List<PlanoContas> contas(boolean analiticas) {
    return transacao("CONTABILIDADE_CONSULTAR", c -> contas.listar(c, empresaId, analiticas));
  }

  public List<ReferenciaContabil> centros() {
    return transacao("CONTABILIDADE_CONSULTAR", c -> cadastro.centros(c, empresaId));
  }

  public List<ReferenciaContabil> historicos() {
    return transacao("CONTABILIDADE_CONSULTAR", c -> cadastro.historicos(c, empresaId));
  }

  public List<ReferenciaContabil> lotes() {
    return transacao("CONTABILIDADE_CONSULTAR", c -> cadastro.lotes(c, empresaId));
  }

  public List<ReferenciaContabil> aglutinacoes() {
    return transacao("CONTABILIDADE_CONSULTAR", c -> cadastro.aglutinacoes(c, empresaId));
  }

  public List<LancamentoContabil> listarLancamentos(
      LocalDate inicio,
      LocalDate fim,
      String documento,
      String descricao,
      String conta,
      StatusLancamento status) {
    RegrasContabeis.periodo(inicio, fim);
    return transacao(
        "CONTABILIDADE_CONSULTAR",
        c -> lancamentos.listar(c, empresaId, inicio, fim, documento, descricao, conta, status, 0));
  }

  public LancamentoContabil buscarLancamento(long id) {
    return transacao("CONTABILIDADE_CONSULTAR", c -> lancamentos.buscar(c, empresaId, id, false));
  }

  private ExercicioSocial exercicio(Connection c, long id, LocalDate data, boolean aberto)
      throws SQLException {
    ExercicioSocial e = cadastro.exercicio(c, empresaId, id);
    if (e == null
        || !e.isAtivo()
        || data.isBefore(e.getDataInicio())
        || data.isAfter(e.getDataFim()))
      throw new IllegalArgumentException(
          "O exercício deve estar ativo, pertencer à empresa e abranger a data informada.");
    if (aberto && e.getStatusContabil() != ExercicioSocial.StatusContabil.ABERTO)
      throw new IllegalStateException("O exercício social precisa estar ABERTO.");
    return e;
  }

  private void referencias(Connection c, LancamentoContabil l) throws SQLException {
    exercicio(c, l.getExercicioSocialId(), l.getData(), true);
    for (ItemLancamento p : l.getItens()) {
      if (cadastro.contaAnalitica(c, p.getContaId(), empresaId) == null)
        throw new IllegalArgumentException("A conta deve ser analítica, ativa e da mesma empresa.");
      if (p.getCentroCustoId() != null
          && cadastro.centroAtivo(c, p.getCentroCustoId(), empresaId) == null)
        throw new IllegalArgumentException("Centro de custo inválido para esta empresa.");
    }
    if (l.getHistoricoPadraoId() != null
        && cadastro.historicoAtivo(c, l.getHistoricoPadraoId(), empresaId) == null)
      throw new IllegalArgumentException("Histórico padrão inválido para esta empresa.");
    if (l.getLoteId() != null
        && cadastro.loteDisponivel(c, l.getLoteId(), empresaId, l.getData()) == null)
      throw new IllegalArgumentException("Lote bloqueado, fora do período ou de outra empresa.");
  }

  public long salvarRascunho(LancamentoContabil entrada) {
    return salvarLancamento(entrada, false);
  }

  public long salvarEContabilizar(LancamentoContabil entrada) {
    return salvarLancamento(entrada, true);
  }

  private long salvarLancamento(LancamentoContabil entrada, boolean postar) {
    LancamentoContabil l = new LancamentoContabil(entrada);
    return transacao(
        "LANCAMENTO_SALVAR",
        c -> {
          if (postar) cadastro.verificarAcesso(c, empresaId, usuarioId, "LANCAMENTO_CONTABILIZAR");
          List<ItemLancamento> antigas = Collections.emptyList();
          if (l.getId() != 0) {
            LancamentoContabil atual = lancamentos.buscar(c, empresaId, l.getId(), true);
            if (atual.getStatus() != StatusLancamento.RASCUNHO)
              throw new IllegalStateException(
                  "Somente RASCUNHO pode ser editado. Use Estornar para contabilizados.");
            antigas = atual.getItens();
          }
          l.setEmpresaId(empresaId);
          l.setStatus(StatusLancamento.RASCUNHO);
          l.setOrigem("MANUAL");
          l.setEstornoDeId(null);
          RegrasContabeis.lancamento(l, postar);
          referencias(c, l);
          boolean novo = l.getId() == 0;
          l.setId(lancamentos.salvar(c, l, usuarioId));
          lancamentos.salvarPartidas(c, l, antigas);
          lancamentos.evento(
              c,
              l,
              usuarioId,
              novo ? "CRIACAO" : "EDICAO",
              "Rascunho " + (novo ? "criado" : "alterado"));
          if (postar) {
            lancamentos.contabilizar(c, usuarioId, l.getId(), empresaId);
            lancamentos.evento(c, l, usuarioId, "CONTABILIZACAO", "Lançamento contabilizado");
          }
          return l.getId();
        });
  }

  public void contabilizar(long id) {
    transacao(
        "LANCAMENTO_CONTABILIZAR",
        c -> {
          LancamentoContabil l = lancamentos.buscar(c, empresaId, id, true);
          if (l.getStatus() != StatusLancamento.RASCUNHO)
            throw new IllegalStateException("Apenas RASCUNHO pode ser contabilizado.");
          RegrasContabeis.lancamento(l, true);
          referencias(c, l);
          lancamentos.contabilizar(c, usuarioId, id, empresaId);
          lancamentos.evento(c, l, usuarioId, "CONTABILIZACAO", "Lançamento contabilizado");
          return null;
        });
  }

  public void excluirRascunho(long id) {
    transacao(
        "LANCAMENTO_EXCLUIR",
        c -> {
          LancamentoContabil l = lancamentos.buscar(c, empresaId, id, true);
          if (l.getStatus() != StatusLancamento.RASCUNHO)
            throw new IllegalStateException("Somente rascunhos podem ser excluídos.");
          exercicio(c, l.getExercicioSocialId(), l.getData(), true);

          lancamentos.evento(c, l, usuarioId, "EDICAO", "Exclusão do rascunho #" + id);
          lancamentos.preservarEventosExclusao(c, id);
          auditoria.excluirDoRascunho(c, id);
          lancamentos.excluirPartidas(c, id);
          lancamentos.excluirRascunho(c, id, empresaId);
          return null;
        });
  }

  public long estornar(long id, long exercicioId, LocalDate data, String motivo) {
    RegrasContabeis.texto(motivo, 180, true, "Motivo do estorno");
    return transacao(
        "LANCAMENTO_ESTORNAR",
        c -> {
          LancamentoContabil original = lancamentos.buscar(c, empresaId, id, true);
          if (original.getStatus() != StatusLancamento.CONTABILIZADO)
            throw new IllegalStateException(
                "Apenas CONTABILIZADO pode ser estornado uma única vez.");
          if (data == null || data.isBefore(original.getData()))
            throw new IllegalArgumentException(
                "A data do estorno não pode anteceder o lançamento original.");
          LancamentoContabil reverso = new LancamentoContabil(original);
          reverso.setId(0);
          reverso.setExercicioSocialId(exercicioId);
          reverso.setData(data);
          reverso.setEstornoDeId(id);
          reverso.setOrigem("ESTORNO");
          reverso.setLoteId(null);
          reverso.setHistoricoPadraoId(null);
          reverso.setDocumento("ESTORNO-" + id);
          reverso.setHistorico("Estorno #" + id + ": " + motivo);
          reverso.setStatus(StatusLancamento.RASCUNHO);
          for (ItemLancamento p : reverso.getItens()) {
            p.setId(0);
            p.setTipoMovimento(
                p.getTipoMovimento() == TipoMovimento.DEBITO
                    ? TipoMovimento.CREDITO
                    : TipoMovimento.DEBITO);
          }
          RegrasContabeis.lancamento(reverso, true);
          referencias(c, reverso);
          reverso.setId(lancamentos.salvar(c, reverso, usuarioId));
          lancamentos.salvarPartidas(c, reverso, Collections.emptyList());
          lancamentos.contabilizarEstorno(c, usuarioId, reverso.getId(), empresaId);
          lancamentos.marcarEstornado(c, id, empresaId);
          lancamentos.evento(
              c,
              original,
              usuarioId,
              "ESTORNO",
              "Estornado pelo lançamento #" + reverso.getId() + ": " + motivo);
          lancamentos.evento(c, reverso, usuarioId, "CRIACAO", "Contrapartida do estorno #" + id);
          lancamentos.evento(c, reverso, usuarioId, "CONTABILIZACAO", "Estorno contabilizado");
          return reverso.getId();
        });
  }

  public DashboardResumo dashboard(LocalDate inicio, LocalDate fim) {
    RegrasContabeis.periodo(inicio, fim);
    return transacao(
        "CONTABILIDADE_CONSULTAR",
        c -> {
          DashboardResumo r = lancamentos.resumo(c, empresaId, inicio, fim);
          r.setRecentes(lancamentos.recentes(c, empresaId, inicio, fim));
          for (InconsistenciaAuditoria a : auditoria.listar(c, empresaId, inicio, fim))
            if (a.getStatus() == StatusAuditoria.PENDENTE) r.getPendencias().add(a);
          return r;
        });
  }

  public List<InconsistenciaAuditoria> listarInconsistencias(LocalDate inicio, LocalDate fim) {
    RegrasContabeis.periodo(inicio, fim);
    return transacao("CONTABILIDADE_CONSULTAR", c -> auditoria.listar(c, empresaId, inicio, fim));
  }

  public void executarAuditoria(LocalDate inicio, LocalDate fim) {
    RegrasContabeis.periodo(inicio, fim);
    transacao(
        "AUDITORIA_EXECUTAR",
        c -> {
          for (LancamentoContabil cabecalho :
              lancamentos.listar(c, empresaId, inicio, fim, "", "", "", null, 0)) {

            LancamentoContabil l = lancamentos.buscar(c, empresaId, cabecalho.getId(), true);
            for (InconsistenciaAuditoria a : RegrasContabeis.analisar(l)) auditoria.registrar(c, a);
          }
          return null;
        });
  }

  public void revisarInconsistencia(long id, StatusAuditoria status, String observacao) {
    if (status != StatusAuditoria.REVISADO && status != StatusAuditoria.IGNORADO)
      throw new IllegalArgumentException("Escolha REVISADO ou IGNORADO.");
    RegrasContabeis.texto(observacao, 500, false, "Observação");
    transacao(
        "AUDITORIA_REVISAR",
        c -> {
          auditoria.revisar(c, empresaId, id, usuarioId, status, observacao);
          return null;
        });
  }

  public List<GeracaoECF> geracoesECF() {
    return transacao("CONTABILIDADE_CONSULTAR", c -> ecf.listar(c, empresaId));
  }

  public GeracaoECF buscarECF(long id) {
    return transacao("CONTABILIDADE_CONSULTAR", c -> ecf.buscar(c, empresaId, id, false));
  }

  private void editavel(GeracaoECF g) {
    if (g.getStatus() == StatusECF.GERADA
        || g.getStatus() == StatusECF.ASSINADA
        || g.getStatus() == StatusECF.TRANSMITIDA
        || g.getStatus() == StatusECF.CANCELADA)
      throw new IllegalStateException(
          "Esta ECF não pode ser alterada. Prepare uma retificadora quando necessário.");
  }

  public long prepararECF(GeracaoECF g) {
    return transacao(
        "ECF_PREPARAR",
        c -> {
          RegrasContabeis.periodo(g.getPeriodoInicial(), g.getPeriodoFinal());
          if (g.getId() != 0) {
            GeracaoECF anterior = ecf.buscar(c, empresaId, g.getId(), true);
            editavel(anterior);
            for (EcfApuracao apuracao : anterior.getApuracoes()) {
              if (apuracao.getDataInicio().isBefore(g.getPeriodoInicial())
                  || apuracao.getDataFim().isAfter(g.getPeriodoFinal()))
                throw new IllegalArgumentException(
                    "A configuração deixaria uma apuração fora do período. Ajuste as apurações"
                        + " primeiro.");
            }
          }
          g.setEmpresaId(empresaId);
          exercicio(c, g.getExercicioSocialId(), g.getPeriodoInicial(), false);
          exercicio(c, g.getExercicioSocialId(), g.getPeriodoFinal(), false);
          if (g.getFinalidade() == null || g.getRegimeTributacao() == null)
            throw new IllegalArgumentException("Informe finalidade e regime tributário.");
          if (g.getFinalidade() == FinalidadeECF.RETIFICADORA) {
            if (g.getEcfRetificadaId() == null || g.getEcfRetificadaId() == g.getId())
              throw new IllegalArgumentException("Selecione a ECF original retificada.");
            GeracaoECF anterior = ecf.buscar(c, empresaId, g.getEcfRetificadaId(), false);
            if (anterior.getStatus() != StatusECF.GERADA
                && anterior.getStatus() != StatusECF.ASSINADA
                && anterior.getStatus() != StatusECF.TRANSMITIDA)
              throw new IllegalArgumentException(
                  "A ECF retificada deve possuir um arquivo já registrado.");
            if (!anterior.getPeriodoInicial().equals(g.getPeriodoInicial())
                || !anterior.getPeriodoFinal().equals(g.getPeriodoFinal()))
              throw new IllegalArgumentException(
                  "A retificadora deve abranger o mesmo período da ECF anterior.");
          } else if (g.getEcfRetificadaId() != null)
            throw new IllegalArgumentException(
                "ECF original não possui referência de retificação.");
          if (g.getEcdGeracaoId() != null
              && cadastro.ecdDaEmpresa(c, g.getEcdGeracaoId(), empresaId) == null)
            throw new IllegalArgumentException("ECD de outra empresa ou inexistente.");
          RegrasContabeis.texto(g.getVersaoLayout(), 20, false, "Versão do layout");
          RegrasContabeis.texto(g.getObservacao(), 500, false, "Observação");
          long id = ecf.preparar(c, g);
          ecf.invalidar(c, id);
          return id;
        });
  }

  private GeracaoECF validar(Connection c, long id) throws SQLException {
    GeracaoECF g = ecf.buscar(c, empresaId, id, true);
    editavel(g);
    g.getValidacoes().clear();
    List<LancamentoContabil> lista =
        lancamentos.listar(
            c, empresaId, g.getPeriodoInicial(), g.getPeriodoFinal(), "", "", "", null, 0);
    long contabilizados = 0;
    for (LancamentoContabil l : lista) {
      if (l.getStatus() == StatusLancamento.RASCUNHO) {
        validacao(
            g,
            TipoValidacao.AVISO,
            "ECF_RASCUNHO",
            "Rascunho não incluído nos movimentos fiscais.",
            "Lançamento #" + l.getId());
        continue;
      }
      contabilizados++;
      for (InconsistenciaAuditoria a : RegrasContabeis.analisar(l))
        validacao(
            g,
            a.getSeveridade() == NivelSeveridade.BAIXA || a.getSeveridade() == NivelSeveridade.MEDIA
                ? TipoValidacao.AVISO
                : TipoValidacao.ERRO,
            a.getCodigo(),
            a.getDescricao(),
            "Lançamento #" + l.getId());
    }
    if (contabilizados == 0)
      validacao(
          g,
          TipoValidacao.AVISO,
          "ECF_SEM_MOVIMENTO",
          "Nenhum lançamento contabilizado no período.",
          "Período");
    if (g.getSignatarios().isEmpty())
      validacao(
          g,
          TipoValidacao.ERRO,
          "ECF_SIGNATARIO",
          "Informe pelo menos um responsável ou contador.",
          "Signatários");
    if (g.getApuracoes().isEmpty())
      validacao(
          g,
          TipoValidacao.AVISO,
          "ECF_APURACAO",
          "Não há apurações cadastradas; confira se são necessárias ao regime escolhido.",
          "Apurações");
    for (EcfApuracao a : g.getApuracoes()) {
      BigDecimal base =
          a.getBaseInicial()
              .add(a.getTotalAdicoes())
              .subtract(a.getTotalExclusoes())
              .subtract(a.getTotalCompensacoes());
      if (base.compareTo(a.getBaseCalculo()) != 0)
        validacao(
            g,
            TipoValidacao.ERRO,
            "ECF_BASE",
            "A base informada difere da base inicial mais ajustes.",
            "Apuração #" + a.getId());
    }
    g.setStatus(g.possuiErros() ? StatusECF.COM_ERROS : StatusECF.VALIDADA);
    ecf.validar(c, g);
    return g;
  }

  private void validacao(
      GeracaoECF g, TipoValidacao tipo, String codigo, String descricao, String referencia) {
    ValidacaoECF v = new ValidacaoECF();
    v.setEcfId(g.getId());
    v.setTipo(tipo);
    v.setCodigo(codigo);
    v.setDescricao(descricao);
    v.setReferencia(referencia);
    g.getValidacoes().add(v);
  }

  public GeracaoECF validarECF(long id) {
    return transacao(
        "ECF_VALIDAR",
        c -> {
          validar(c, id);
          return ecf.buscar(c, empresaId, id, false);
        });
  }

  public void registrarArquivoECF(long id, Path arquivo) {
    final String hash;
    try {
      if (!Files.isRegularFile(arquivo) || Files.size(arquivo) > 20_000_000)
        throw new IllegalArgumentException("Selecione um arquivo existente de até 20 MB.");
      byte[] digest = MessageDigest.getInstance("SHA-256").digest(Files.readAllBytes(arquivo));
      StringBuilder texto = new StringBuilder();
      for (byte b : digest) texto.append(String.format("%02x", b & 255));
      hash = texto.toString();
    } catch (java.io.IOException | java.security.NoSuchAlgorithmException erro) {
      throw new IllegalStateException("Não foi possível ler o arquivo.", erro);
    }
    String nome = arquivo.getFileName().toString();
    RegrasContabeis.texto(nome, 255, true, "Nome do arquivo");

    GeracaoECF validada = validarECF(id);
    if (validada.possuiErros())
      throw new IllegalStateException("Corrija os erros da ECF antes de registrar o arquivo.");
    transacao(
        "ECF_GERAR",
        c -> {
          GeracaoECF g = validar(c, id);
          if (g.possuiErros())
            throw new IllegalStateException("A ECF mudou e precisa ser corrigida.");
          ecf.registrarArquivo(c, g, usuarioId, nome, hash);
          return null;
        });
  }

  public long salvarApuracao(long ecfId, EcfApuracao a) {
    return transacao(
        "ECF_PREPARAR",
        c -> {
          GeracaoECF g = ecf.buscar(c, empresaId, ecfId, true);
          editavel(g);
          RegrasContabeis.periodo(a.getDataInicio(), a.getDataFim());
          if (a.getDataInicio().isBefore(g.getPeriodoInicial())
              || a.getDataFim().isAfter(g.getPeriodoFinal())
              || a.getTributo() == null)
            throw new IllegalArgumentException("Apuração deve estar dentro do período da ECF.");
          for (BigDecimal v :
              Arrays.asList(a.getBaseInicial(), a.getBaseCalculo(), a.getValorTributo()))
            RegrasContabeis.dinheiro(v, false, "Apuração");
          if (a.getAliquota() != null
              && (a.getAliquota().scale() > 4
                  || a.getAliquota().signum() < 0
                  || a.getAliquota().compareTo(new BigDecimal("100")) > 0))
            throw new IllegalArgumentException(
                "Alíquota deve estar entre 0 e 100 com até quatro casas.");
          a.setEcfId(ecfId);
          a.setStatus(StatusApuracaoECF.EM_CALCULO);
          EcfApuracao existente =
              g.getApuracoes().stream()
                  .filter(p -> p.getId() == a.getId())
                  .findFirst()
                  .orElse(null);
          if (a.getId() != 0 && existente == null)
            throw new IllegalArgumentException("Apuração não pertence à ECF.");
          a.setTotalAdicoes(existente == null ? BigDecimal.ZERO : existente.getTotalAdicoes());
          a.setTotalExclusoes(existente == null ? BigDecimal.ZERO : existente.getTotalExclusoes());
          a.setTotalCompensacoes(
              existente == null ? BigDecimal.ZERO : existente.getTotalCompensacoes());
          long id = ecf.salvarApuracao(c, a);
          ecf.invalidar(c, ecfId);
          return id;
        });
  }

  public long adicionarAjuste(long ecfId, long apuracaoId, EcfAjuste a) {
    return transacao(
        "ECF_PREPARAR",
        c -> {
          GeracaoECF g = ecf.buscar(c, empresaId, ecfId, true);
          editavel(g);
          EcfApuracao p =
              g.getApuracoes().stream()
                  .filter(x -> x.getId() == apuracaoId)
                  .findFirst()
                  .orElseThrow(() -> new IllegalArgumentException("Apuração não pertence à ECF."));
          RegrasContabeis.dinheiro(a.getValor(), true, "Ajuste");
          RegrasContabeis.texto(a.getDescricao(), 255, true, "Descrição");
          RegrasContabeis.texto(a.getCodigo(), 50, false, "Código");
          RegrasContabeis.texto(a.getDocumentoReferencia(), 100, false, "Documento");
          RegrasContabeis.texto(a.getObservacao(), 500, false, "Observação");
          if (a.getTipo() == null) throw new IllegalArgumentException("Informe o tipo do ajuste.");
          if (a.getContaId() != null && cadastro.contaAtiva(c, a.getContaId(), empresaId) == null)
            throw new IllegalArgumentException("Conta inválida para esta empresa.");
          a.setApuracaoId(apuracaoId);
          long id = ecf.salvarAjuste(c, a);
          recalcularAjustes(c, p.getId());
          ecf.invalidar(c, ecfId);
          return id;
        });
  }

  private void recalcularAjustes(Connection c, long id) throws SQLException {
    ecf.recalcularAjustes(c, id);
  }

  public long adicionarSignatario(long ecfId, EcfSignatario s) {
    return transacao(
        "ECF_PREPARAR",
        c -> {
          editavel(ecf.buscar(c, empresaId, ecfId, true));
          RegrasContabeis.texto(s.getNome(), 150, true, "Nome");
          RegrasContabeis.texto(s.getCpf(), 14, false, "CPF");
          RegrasContabeis.texto(s.getCrc(), 30, false, "CRC");
          RegrasContabeis.texto(s.getUfCrc(), 2, false, "UF CRC");
          RegrasContabeis.texto(s.getEmail(), 254, false, "Email");
          if (s.getTipo() == null)
            throw new IllegalArgumentException("Informe o tipo do signatário.");
          s.setEcfId(ecfId);
          long id = ecf.salvarSignatario(c, s);
          ecf.invalidar(c, ecfId);
          return id;
        });
  }

  public void removerFilhoECF(long ecfId, String tipo, long id) {
    transacao(
        "ECF_PREPARAR",
        c -> {
          GeracaoECF g = ecf.buscar(c, empresaId, ecfId, true);
          editavel(g);
          if ("signatario".equals(tipo)) {
            if (ecf.excluirSignatario(c, ecfId, id) != 1)
              throw new IllegalArgumentException("Signatário inexistente ou já assinado.");
          } else if ("apuracao".equals(tipo)) {
            if (g.getApuracoes().stream().noneMatch(a -> a.getId() == id))
              throw new IllegalArgumentException("Apuração inválida.");
            ecf.excluirAjustesApuracao(c, id);
            ecf.excluirApuracao(c, id, ecfId);
          } else if ("ajuste".equals(tipo)) {
            EcfApuracao p =
                g.getApuracoes().stream()
                    .filter(a -> a.getAjustes().stream().anyMatch(x -> x.getId() == id))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Ajuste inválido."));
            ecf.excluirAjuste(c, id, p.getId());
            recalcularAjustes(c, p.getId());
          } else throw new IllegalArgumentException("Tipo de registro inválido.");
          ecf.invalidar(c, ecfId);
          return null;
        });
  }

  public long salvarConta(PlanoContas p) {
    return transacao(
        "PLANO_CONTAS_MANTER",
        c -> {
          if (p.getId() != 0 && contas.bloquearConta(c, p.getId(), empresaId) == null)
            throw new IllegalArgumentException("Conta não encontrada nesta empresa.");
          p.setEmpresaId(empresaId);
          RegrasContabeis.texto(p.getCodigo(), 20, true, "Código");
          RegrasContabeis.texto(p.getDescricao(), 150, true, "Descrição");
          if (p.getTipo() == null || p.getNatureza() == null)
            throw new IllegalArgumentException("Informe tipo e natureza.");
          if (p.getContaPaiId() != null) {
            if (p.getContaPaiId() == p.getId())
              throw new IllegalArgumentException("Uma conta não pode ser superior de si mesma.");
            Set<Long> vistos = new HashSet<>();
            long atual = p.getContaPaiId();
            while (atual != 0) {
              if (!vistos.add(atual) || atual == p.getId())
                throw new IllegalArgumentException("A hierarquia de contas não pode ter ciclos.");
              PlanoContas pai = contas.buscarBloqueada(c, empresaId, atual);
              if (pai == null || pai.getTipo() != TipoConta.SINTETICA || !pai.getAtivo())
                throw new IllegalArgumentException(
                    "Conta superior deve ser sintética, ativa e da mesma empresa.");
              atual = pai.getContaPaiId() == null ? 0 : pai.getContaPaiId();
            }
          }
          if (p.getCodigoAglutinacaoId() != null
              && cadastro.aglutinacaoAtiva(c, p.getCodigoAglutinacaoId(), empresaId) == null)
            throw new IllegalArgumentException("Código de aglutinação inválido para esta empresa.");
          if (p.getId() != 0 && p.getTipo() != TipoConta.ANALITICA) {
            Long uso = contas.contarPartidas(c, p.getId());
            if (uso > 0)
              throw new IllegalStateException(
                  "Conta com partidas não pode ser transformada em sintética.");
          }
          if (p.getId() != 0
              && p.getTipo() == TipoConta.ANALITICA
              && contas.contarSubcontas(c, p.getId(), empresaId) > 0)
            throw new IllegalArgumentException("Conta com subcontas não pode ser analítica.");
          return contas.salvar(c, p);
        });
  }
}
