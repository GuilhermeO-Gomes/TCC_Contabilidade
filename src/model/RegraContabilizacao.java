package model;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.ArrayList;

public class RegraContabilizacao {

    private long id;
    private long empresaId;
    private String moduloOrigem;
    private String eventoOrigem;
    private String descricaoEvento;
    private boolean ativo = true;
    private LocalDateTime criadoEm;
    private ArrayList<RegraContabilizacaoItem> itens = new ArrayList<>();

    public RegraContabilizacao() {
    }

    public RegraContabilizacao(
            long id,
            long empresaId,
            String moduloOrigem,
            String eventoOrigem,
            String descricaoEvento,
            boolean ativo,
            LocalDateTime criadoEm,
            ArrayList<RegraContabilizacaoItem> itens) {

        this.id = id;
        this.empresaId = empresaId;
        this.moduloOrigem = moduloOrigem;
        this.eventoOrigem = eventoOrigem;
        this.descricaoEvento = descricaoEvento;
        this.ativo = ativo;
        this.criadoEm = criadoEm;
        this.itens = itens;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(long empresaId) {
        this.empresaId = empresaId;
    }

    public String getModuloOrigem() {
        return moduloOrigem;
    }

    public void setModuloOrigem(String moduloOrigem) {
        this.moduloOrigem = moduloOrigem;
    }

    public String getEventoOrigem() {
        return eventoOrigem;
    }

    public void setEventoOrigem(String eventoOrigem) {
        this.eventoOrigem = eventoOrigem;
    }

    public String getDescricaoEvento() {
        return descricaoEvento;
    }

    public void setDescricaoEvento(String descricaoEvento) {
        this.descricaoEvento = descricaoEvento;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }

    public void setCriadoEm(LocalDateTime criadoEm) {
        this.criadoEm = criadoEm;
    }

    public ArrayList<RegraContabilizacaoItem> getItens() {
        return itens;
    }

    public void setItens(ArrayList<RegraContabilizacaoItem> itens) {
        this.itens = itens;
    }
    public boolean itensValidos() {
        if (itens == null || itens.isEmpty()) {
            return false;
        }
        HashSet<Integer> ordens = new HashSet<>();
        for (RegraContabilizacaoItem item : itens) {
            if (item == null || item.getContaId() <= 0 || item.getOrdem() < 0
                    || !ordens.add(item.getOrdem()) || item.getTipoMovimento() == null
                    || item.getPercentual() == null || item.getPercentual().signum() <= 0
                    || item.getPercentual().compareTo(new BigDecimal("100")) > 0
                    || item.getPercentual().stripTrailingZeros().scale() > 4
                    || (item.getDescricao() != null && item.getDescricao().length() > 255)) {
                return false;
            }
        }
        return true;
    }

    public boolean percentuaisValidos() {
        if (!itensValidos()) {
            return false;
        }
        BigDecimal debitos = BigDecimal.ZERO;
        BigDecimal creditos = BigDecimal.ZERO;
        for (RegraContabilizacaoItem item : itens) {
            if (item.getTipoMovimento() == TipoMovimento.DEBITO) {
                debitos = debitos.add(item.getPercentual());
            } else {
                creditos = creditos.add(item.getPercentual());
            }
        }
        BigDecimal cem = new BigDecimal("100");
        return debitos.compareTo(cem) == 0 && creditos.compareTo(cem) == 0;
    }
}
