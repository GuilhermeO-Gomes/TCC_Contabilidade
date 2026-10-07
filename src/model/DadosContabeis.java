package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Coleções da sessão, preenchidas pelo usuário. Nenhuma operação acessa banco ou arquivo.
public class DadosContabeis {
    private final List<LancamentoContabil> lancamentos = new ArrayList<LancamentoContabil>();
    private final List<GeracaoECF> geracoes = new ArrayList<GeracaoECF>();
    private final List<InconsistenciaAuditoria> inconsistencias = new ArrayList<InconsistenciaAuditoria>();

    public List<LancamentoContabil> getLancamentos() {
        return lancamentos;
    }

    public List<GeracaoECF> getGeracoes() {
        return geracoes;
    }

    public List<InconsistenciaAuditoria> getInconsistencias() {
        return inconsistencias;
    }

    public int proximoIdLancamento() {
        int maior = 0;
        for (LancamentoContabil lancamento : lancamentos) {
            maior = Math.max(maior, lancamento.getId());
        }
        return maior + 1;
    }

    public int proximoIdGeracao() {
        int maior = 0;
        for (GeracaoECF geracao : geracoes) {
            maior = Math.max(maior, geracao.getId());
        }
        return maior + 1;
    }

    public void atualizarInconsistencias(LocalDate inicio, LocalDate fim) {
        List<InconsistenciaAuditoria> novos = InconsistenciaAuditoria.analisarLancamentos(lancamentos, inicio, fim);
        for (InconsistenciaAuditoria novo : novos) {
            for (InconsistenciaAuditoria anterior : inconsistencias) {
                if (novo.getChave().equals(anterior.getChave())) {
                    novo.setStatus(anterior.getStatus());
                    break;
                }
            }
        }
        inconsistencias.clear();
        inconsistencias.addAll(novos);
    }
}
