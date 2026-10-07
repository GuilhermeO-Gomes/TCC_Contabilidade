package model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LancamentoContabil {
    private int id;
    private LocalDate data = LocalDate.now();
    private String documento = "";
    private String historico = "";
    private StatusLancamento status = StatusLancamento.RASCUNHO;

    public LancamentoContabil() {
    }

    private final List<ItemLancamento> itens = new ArrayList<ItemLancamento>();

    public LancamentoContabil(int id, LocalDate data, String documento, String historico, StatusLancamento status) {
        this.id = id;
        setData(data);
        setDocumento(documento);
        setHistorico(historico);
        setStatus(status);
    }

    // A edição trabalha sobre uma cópia; cancelar não altera o lançamento salvo.
    public LancamentoContabil(LancamentoContabil original) {
        this(original.id, original.data, original.documento, original.historico, original.status);
        setItens(original.itens);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data == null ? LocalDate.now() : data;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento == null ? "" : documento;
    }

    public String getHistorico() {
        return historico;
    }

    public void setHistorico(String historico) {
        this.historico = historico == null ? "" : historico;
    }

    public StatusLancamento getStatus() {
        return status;
    }

    public void setStatus(StatusLancamento status) {
        this.status = status == null ? StatusLancamento.RASCUNHO : status;
    }

    public List<ItemLancamento> getItens() {
        return Collections.unmodifiableList(itens);
    }

    public void setItens(List<ItemLancamento> novosItens) {
        List<ItemLancamento> copias = new ArrayList<ItemLancamento>();
        if (novosItens != null) {
            for (ItemLancamento item : novosItens) {
                copias.add(new ItemLancamento(item));
            }
        }
        itens.clear();
        itens.addAll(copias);
    }

    public void adicionarItem(ItemLancamento item) {
        if (item == null) {
            throw new IllegalArgumentException("A partida não pode ser nula.");
        }
        itens.add(item);
    }

    public void removerItem(ItemLancamento item) {
        itens.remove(item);
    }

    private BigDecimal calcularTotal(TipoMovimento movimento) {
        BigDecimal total = BigDecimal.ZERO;
        for (ItemLancamento item : itens) {
            if (item.getTipoMovimento() == movimento) {
                total = total.add(item.getValor());
            }
        }
        return total;
    }

    public BigDecimal calcularTotalDebitos() {
        return calcularTotal(TipoMovimento.DEBITO);
    }

    public BigDecimal calcularTotalCreditos() {
        return calcularTotal(TipoMovimento.CREDITO);
    }

    public BigDecimal calcularDiferenca() {
        return calcularTotalDebitos().subtract(calcularTotalCreditos());
    }

    public boolean estaBalanceado() {
        return calcularTotalDebitos().compareTo(calcularTotalCreditos()) == 0;
    }

    public boolean estaNoPeriodo(LocalDate inicio, LocalDate fim) {
        return !data.isBefore(inicio) && !data.isAfter(fim);
    }

    @Override
    public String toString() {
        return id + " - " + documento + " - " + historico;
    }

}
