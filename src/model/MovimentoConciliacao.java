package model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDate;

public class MovimentoConciliacao {

    private long id;
    private long empresaId;
    private long contaBancariaRefId;
    private String origem;
    private LocalDate dataMovimentacao;
    private String documento;
    private String descricao;
    private String natureza;
    private BigDecimal valor = BigDecimal.ZERO;
    private String status;
    private String identificadorExterno;
    private String arquivoOrigem;
    private LocalDateTime importadoEm;

    public MovimentoConciliacao() {
    }

    public MovimentoConciliacao(
            long id,
            long empresaId,
            long contaBancariaRefId,
            String origem,
            LocalDate dataMovimentacao,
            String documento,
            String descricao,
            String natureza,
            BigDecimal valor,
            String status,
            String identificadorExterno,
            String arquivoOrigem,
            LocalDateTime importadoEm) {

        this.id = id;
        this.empresaId = empresaId;
        this.contaBancariaRefId = contaBancariaRefId;
        this.origem = origem;
        this.dataMovimentacao = dataMovimentacao;
        this.documento = documento;
        this.descricao = descricao;
        this.natureza = natureza;
        this.valor = valor;
        this.status = status;
        this.identificadorExterno = identificadorExterno;
        this.arquivoOrigem = arquivoOrigem;
        this.importadoEm = importadoEm;
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

    public long getContaBancariaRefId() {
        return contaBancariaRefId;
    }

    public void setContaBancariaRefId(long contaBancariaRefId) {
        this.contaBancariaRefId = contaBancariaRefId;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public LocalDate getDataMovimentacao() {
        return dataMovimentacao;
    }

    public void setDataMovimentacao(LocalDate dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNatureza() {
        return natureza;
    }

    public void setNatureza(String natureza) {
        this.natureza = natureza;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentificadorExterno() {
        return identificadorExterno;
    }

    public void setIdentificadorExterno(String identificadorExterno) {
        this.identificadorExterno = identificadorExterno;
    }

    public String getArquivoOrigem() {
        return arquivoOrigem;
    }

    public void setArquivoOrigem(String arquivoOrigem) {
        this.arquivoOrigem = arquivoOrigem;
    }

    public LocalDateTime getImportadoEm() {
        return importadoEm;
    }

    public void setImportadoEm(LocalDateTime importadoEm) {
        this.importadoEm = importadoEm;
    }
}
