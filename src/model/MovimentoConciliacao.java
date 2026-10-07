package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MovimentoConciliacao {

    private int codigo;
    private LocalDate data;
    private String documento;
    private String descricao;
    private String natureza;
    private BigDecimal valor = BigDecimal.ZERO;
    private String situacao = "Pendente";

    public MovimentoConciliacao() {
    }

    public MovimentoConciliacao(int codigo, LocalDate data, String documento, String descricao,
            String natureza, BigDecimal valor, String situacao) {
        this.codigo = codigo;
        this.data = data;
        this.documento = documento;
        this.descricao = descricao;
        this.natureza = natureza;
        this.valor = valor;
        this.situacao = situacao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
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

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
}
