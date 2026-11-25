
package model;

import java.time.LocalDateTime;

public class EcdValidacao {

    public enum Tipo {
        ERRO,
        AVISO
    }

    private Long id;
    private Long ecdId;

    private Tipo tipo;
    private String codigo;
    private String descricao;
    private String referencia;

    private boolean resolvido;
    private Long resolvidoPor;
    private LocalDateTime resolvidoEm;
    private LocalDateTime detectadoEm;

    public EcdValidacao() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEcdId() { return ecdId; }
    public void setEcdId(Long ecdId) { this.ecdId = ecdId; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getReferencia() { return referencia; }
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public boolean isResolvido() { return resolvido; }
    public void setResolvido(boolean resolvido) {
        this.resolvido = resolvido;
    }

    public Long getResolvidoPor() { return resolvidoPor; }
    public void setResolvidoPor(Long resolvidoPor) {
        this.resolvidoPor = resolvidoPor;
    }

    public LocalDateTime getResolvidoEm() { return resolvidoEm; }
    public void setResolvidoEm(LocalDateTime resolvidoEm) {
        this.resolvidoEm = resolvidoEm;
    }

    public LocalDateTime getDetectadoEm() { return detectadoEm; }
    public void setDetectadoEm(LocalDateTime detectadoEm) {
        this.detectadoEm = detectadoEm;
    }
}
