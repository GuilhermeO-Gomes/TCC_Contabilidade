package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeracaoECF {
    private int id;
    private String empresa = "";
    private int anoCalendario = LocalDate.now().getYear();
    private LocalDate periodoInicial = LocalDate.now().withDayOfYear(1);
    private LocalDate periodoFinal = LocalDate.now().withMonth(12).withDayOfMonth(31);
    private String tipoEscrituracao = "Original";
    private String regimeTributario = "Lucro Presumido";
    private String formaTributacao = "Trimestral";
    private String situacao = "Não validada";
    private LocalDateTime dataGeracao;
    private String responsavel = "";

    public GeracaoECF() {
    }

    private final List<ValidacaoECF> validacoes = new ArrayList<ValidacaoECF>();

    public GeracaoECF(int id, int anoCalendario, LocalDate periodoInicial, LocalDate periodoFinal,
            String tipoEscrituracao, String regimeTributario, String responsavel) {
        this.id = id;
        this.anoCalendario = anoCalendario;
        setPeriodoInicial(periodoInicial);
        setPeriodoFinal(periodoFinal);
        setTipoEscrituracao(tipoEscrituracao);
        setRegimeTributario(regimeTributario);
        setResponsavel(responsavel);
    }

    public GeracaoECF(GeracaoECF original) {
        this(original.id, original.anoCalendario, original.periodoInicial, original.periodoFinal,
                original.tipoEscrituracao, original.regimeTributario, original.responsavel);
        empresa = original.empresa;
        formaTributacao = original.formaTributacao;
        situacao = original.situacao;
        dataGeracao = original.dataGeracao;
        setValidacoes(original.validacoes);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa == null ? "" : empresa;
    }

    public int getAnoCalendario() {
        return anoCalendario;
    }

    public void setAnoCalendario(int anoCalendario) {
        this.anoCalendario = anoCalendario;
    }

    public LocalDate getPeriodoInicial() {
        return periodoInicial;
    }

    public void setPeriodoInicial(LocalDate periodoInicial) {
        this.periodoInicial = periodoInicial == null ? LocalDate.now().withDayOfYear(1) : periodoInicial;
    }

    public LocalDate getPeriodoFinal() {
        return periodoFinal;
    }

    public void setPeriodoFinal(LocalDate periodoFinal) {
        this.periodoFinal = periodoFinal == null ? LocalDate.now().withMonth(12).withDayOfMonth(31) : periodoFinal;
    }

    public String getTipoEscrituracao() {
        return tipoEscrituracao;
    }

    public void setTipoEscrituracao(String tipoEscrituracao) {
        this.tipoEscrituracao = tipoEscrituracao == null ? "Original" : tipoEscrituracao;
    }

    public String getRegimeTributario() {
        return regimeTributario;
    }

    public void setRegimeTributario(String regimeTributario) {
        this.regimeTributario = regimeTributario == null ? "Lucro Presumido" : regimeTributario;
    }

    public String getFormaTributacao() {
        return formaTributacao;
    }

    public void setFormaTributacao(String formaTributacao) {
        this.formaTributacao = formaTributacao == null ? "Trimestral" : formaTributacao;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao == null ? "Não validada" : situacao;
    }

    public LocalDateTime getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(LocalDateTime dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel == null ? "" : responsavel;
    }

    public List<ValidacaoECF> getValidacoes() {
        return Collections.unmodifiableList(validacoes);
    }

    public void setValidacoes(List<ValidacaoECF> novasValidacoes) {
        List<ValidacaoECF> copias = new ArrayList<ValidacaoECF>();
        if (novasValidacoes != null) {
            for (ValidacaoECF validacao : novasValidacoes) {
                copias.add(new ValidacaoECF(validacao));
            }
        }
        validacoes.clear();
        validacoes.addAll(copias);
    }

    public void adicionarValidacao(ValidacaoECF validacao) {
        if (validacao == null) {
            throw new IllegalArgumentException("A validação não pode ser nula.");
        }
        validacoes.add(validacao);
    }

    private int contarTipo(TipoValidacao tipo) {
        int quantidade = 0;
        for (ValidacaoECF validacao : validacoes) {
            if (validacao.getTipo() == tipo) {
                quantidade++;
            }
        }
        return quantidade;
    }

    public int contarErros() {
        return contarTipo(TipoValidacao.ERRO);
    }

    public int contarAlertas() {
        return contarTipo(TipoValidacao.ALERTA);
    }

    public boolean possuiErros() {
        return contarErros() > 0;
    }

    public String verificarConfiguracao() {
        if (empresa.trim().isEmpty() || responsavel.trim().isEmpty()) {
            return "Preencha empresa e responsável.";
        }
        if (anoCalendario < 1900 || anoCalendario > 9999) {
            return "Informe um ano entre 1900 e 9999.";
        }
        if (periodoInicial.isAfter(periodoFinal)) {
            return "O período inicial deve ser anterior ou igual ao final.";
        }
        if (periodoInicial.getYear() != anoCalendario || periodoFinal.getYear() != anoCalendario) {
            return "As datas precisam pertencer ao ano-calendário informado.";
        }
        if (tipoEscrituracao.trim().isEmpty() || regimeTributario.trim().isEmpty() || formaTributacao.trim().isEmpty()) {
            return "Preencha tipo, regime e forma de tributação.";
        }
        return "";
    }

    public void validarDados(List<LancamentoContabil> lancamentos) {
        validacoes.clear();
        String problema = verificarConfiguracao();
        adicionarResultado("ECF001", "Configuração", "Preenchimento da configuração",
                problema.isEmpty() ? "Configuração preenchida para a demonstração." : problema,
                problema.isEmpty() ? TipoValidacao.SUCESSO : TipoValidacao.ERRO);
        if (!problema.isEmpty()) {
            situacao = "Com erros";
            return;
        }
        List<LancamentoContabil> ativos = new ArrayList<LancamentoContabil>();
        for (LancamentoContabil lancamento : lancamentos) {
            if (lancamento.getStatus() != StatusLancamento.CANCELADO && lancamento.estaNoPeriodo(periodoInicial, periodoFinal)) {
                ativos.add(lancamento);
            }
        }
        boolean semPartidas = false, desequilibrado = false, contaInvalida = false;
        boolean valorInvalido = false, centroVazio = false, documentoVazio = false;
        for (LancamentoContabil lancamento : ativos) {
            semPartidas |= lancamento.getItens().isEmpty();
            desequilibrado |= !lancamento.estaBalanceado();
            documentoVazio |= lancamento.getDocumento().trim().isEmpty() || lancamento.getHistorico().trim().isEmpty();
            for (ItemLancamento item : lancamento.getItens()) {
                contaInvalida |= item.getConta().trim().isEmpty();
                valorInvalido |= item.getValor().signum() <= 0;
                centroVazio |= item.getCentroCusto().trim().isEmpty();
            }
        }
        adicionarResultado("ECF002", "Movimentação", "Lançamentos no período",
                ativos.isEmpty() ? "Nenhum lançamento ativo no período." : ativos.size() + " lançamentos analisados.",
                ativos.isEmpty() ? TipoValidacao.ERRO : TipoValidacao.SUCESSO);
        adicionarResultado("ECF003", "Partidas", "Lançamentos com partidas",
                semPartidas ? "Há lançamento sem partidas." : "Todos os lançamentos têm partidas.",
                semPartidas ? TipoValidacao.ERRO : TipoValidacao.SUCESSO);
        adicionarResultado("ECF004", "Lançamentos", "Balanceamento de débitos e créditos",
                desequilibrado ? "Revise as partidas: existem lançamentos desequilibrados." : "Os totais estão balanceados.",
                desequilibrado ? TipoValidacao.ERRO : TipoValidacao.SUCESSO);
        adicionarResultado("ECF005", "Contas", "Preenchimento das contas",
                contaInvalida ? "Há partida sem conta." : "As contas estão preenchidas.",
                contaInvalida ? TipoValidacao.ERRO : TipoValidacao.SUCESSO);
        adicionarResultado("ECF006", "Valores", "Valores positivos nas partidas",
                valorInvalido ? "Há partida com valor menor ou igual a zero." : "Os valores são positivos.",
                valorInvalido ? TipoValidacao.ERRO : TipoValidacao.SUCESSO);
        adicionarResultado("ECF007", "Centros de custo", "Preenchimento dos centros de custo",
                centroVazio ? "Há partida sem centro de custo; confira sua necessidade." : "Centros de custo preenchidos.",
                centroVazio ? TipoValidacao.ALERTA : TipoValidacao.SUCESSO);
        adicionarResultado("ECF008", "Documentação", "Documento e histórico",
                documentoVazio ? "Há lançamento com documento ou histórico vazio." : "Documentação preenchida.",
                documentoVazio ? TipoValidacao.ALERTA : TipoValidacao.SUCESSO);
        situacao = possuiErros() ? "Com erros" : contarAlertas() > 0 ? "Validada com alertas" : "Validada";
    }

    private void adicionarResultado(String codigo, String categoria, String descricao, String mensagem, TipoValidacao tipo) {
        adicionarValidacao(new ValidacaoECF(validacoes.size() + 1, codigo, categoria, descricao, mensagem, tipo));
    }

    // Sempre revalidar: uma validação anterior pode ter ficado desatualizada.
    public void gerar(List<LancamentoContabil> lancamentos) {
        validarDados(lancamentos);
        if (possuiErros()) {
            throw new IllegalStateException("Corrija os erros antes de gerar a ECF demonstrativa.");
        }
        dataGeracao = LocalDateTime.now();
        situacao = "Gerada (demonstração)";
    }

    @Override
    public String toString() {
        return anoCalendario + " - " + empresa + " - " + situacao;
    }

}
