package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InconsistenciaAuditoria {
    private int id;
    private String codigo = "";
    private String descricao = "";
    private String recomendacao = "";
    private NivelSeveridade severidade = NivelSeveridade.INFORMACAO;
    private LocalDate data = LocalDate.now();
    private String conta = "";
    private int lancamentoId;
    private int itemId;
    private StatusAuditoria status = StatusAuditoria.PENDENTE;

    public InconsistenciaAuditoria() {
    }

    public InconsistenciaAuditoria(int id, String codigo, String descricao, String recomendacao,
            NivelSeveridade severidade, LocalDate data, String conta, int lancamentoId) {
        this.id = id;
        setCodigo(codigo);
        setDescricao(descricao);
        setRecomendacao(recomendacao);
        setSeveridade(severidade);
        setData(data);
        setConta(conta);
        this.lancamentoId = lancamentoId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo == null ? "" : codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao == null ? "" : descricao;
    }

    public String getRecomendacao() {
        return recomendacao;
    }

    public void setRecomendacao(String recomendacao) {
        this.recomendacao = recomendacao == null ? "" : recomendacao;
    }

    public NivelSeveridade getSeveridade() {
        return severidade;
    }

    public void setSeveridade(NivelSeveridade severidade) {
        this.severidade = severidade == null ? NivelSeveridade.INFORMACAO : severidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data == null ? LocalDate.now() : data;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta == null ? "" : conta;
    }

    public int getLancamentoId() {
        return lancamentoId;
    }

    public void setLancamentoId(int lancamentoId) {
        this.lancamentoId = lancamentoId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public StatusAuditoria getStatus() {
        return status;
    }

    public void setStatus(StatusAuditoria status) {
        this.status = status == null ? StatusAuditoria.PENDENTE : status;
    }

    public void marcarRevisado() {
        status = StatusAuditoria.REVISADO;
    }

    public void ignorar() {
        status = StatusAuditoria.IGNORADO;
    }

    public String getChave() {
        return lancamentoId + ":" + codigo + ":" + itemId;
    }

    // Checagens acadêmicas de preenchimento e balanceamento, sem regras fiscais.
    public static List<InconsistenciaAuditoria> analisarLancamentos(List<LancamentoContabil> lancamentos,
            LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null || inicio.isAfter(fim)) {
            throw new IllegalArgumentException("Informe um período válido para a auditoria.");
        }
        List<InconsistenciaAuditoria> resultado = new ArrayList<InconsistenciaAuditoria>();
        for (LancamentoContabil lancamento : lancamentos) {
            if (lancamento.getStatus() == StatusLancamento.CANCELADO || !lancamento.estaNoPeriodo(inicio, fim)) {
                continue;
            }
            if (lancamento.getItens().isEmpty()) {
                adicionarAchado(resultado, lancamento, null, "AUD001", "Lançamento sem partidas",
                        "Adicione ao menos uma partida de débito e uma de crédito.", NivelSeveridade.CRITICO);
            }
            if (!lancamento.estaBalanceado()) {
                adicionarAchado(resultado, lancamento, null, "AUD002", "Débito diferente de crédito",
                        "Revise as partidas. Diferença: " + lancamento.calcularDiferenca().toPlainString(), NivelSeveridade.CRITICO);
            }
            if (lancamento.getHistorico().trim().isEmpty()) {
                adicionarAchado(resultado, lancamento, null, "AUD003", "Histórico vazio",
                        "Descreva o fato contábil.", NivelSeveridade.ALERTA);
            }
            if (lancamento.getDocumento().trim().isEmpty()) {
                adicionarAchado(resultado, lancamento, null, "AUD004", "Documento vazio",
                        "Informe o documento que originou o lançamento.", NivelSeveridade.INFORMACAO);
            }
            for (ItemLancamento item : lancamento.getItens()) {
                if (item.getConta().trim().isEmpty()) {
                    adicionarAchado(resultado, lancamento, item, "AUD005", "Item sem conta",
                            "Preencha a conta da partida.", NivelSeveridade.CRITICO);
                }
                if (item.getValor().signum() <= 0) {
                    adicionarAchado(resultado, lancamento, item, "AUD006", "Valor da partida menor ou igual a zero",
                            "Informe um valor positivo.", NivelSeveridade.CRITICO);
                }
            }
        }
        return resultado;
    }

    private static void adicionarAchado(List<InconsistenciaAuditoria> resultado, LancamentoContabil lancamento,
            ItemLancamento item, String codigo, String descricao, String recomendacao, NivelSeveridade severidade) {
        InconsistenciaAuditoria achado = new InconsistenciaAuditoria(resultado.size() + 1, codigo, descricao,
                recomendacao, severidade, lancamento.getData(), item == null ? "" : item.getConta(), lancamento.getId());
        if (item != null) {
            achado.setItemId(item.getId());
        }
        resultado.add(achado);
    }

    @Override
    public String toString() {
        return codigo + " - " + descricao;
    }

}
