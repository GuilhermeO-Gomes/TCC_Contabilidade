package model;

import java.time.LocalDateTime;

public class EcdSignatario {

    public enum Tipo {
        RESPONSAVEL_LEGAL,
        CONTADOR,
        OUTRO
    }

    private Long id;
    private Long ecdId;

    private String nome;
    private String cpf;
    private Tipo tipo;

    private String crc;
    private String ufCrc;
    private String email;

    private boolean assinou;
    private LocalDateTime assinadoEm;

    public EcdSignatario() {
    }

    public void validar() {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException(
                "O nome do signatário é obrigatório."
            );
        }

        if (tipo == null) {
            throw new IllegalArgumentException(
                "Informe o tipo do signatário."
            );
        }

        if (ufCrc != null && !ufCrc.isBlank()
                && !ufCrc.matches(
                    "AC|AL|AP|AM|BA|CE|DF|ES|GO|MA|MT|MS|MG|PA|PB|PR|PE|PI|RJ|RN|RS|RO|RR|SC|SP|SE|TO")) {
            throw new IllegalArgumentException("UF do CRC inválida.");
        }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEcdId() { return ecdId; }
    public void setEcdId(Long ecdId) { this.ecdId = ecdId; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public Tipo getTipo() { return tipo; }
    public void setTipo(Tipo tipo) { this.tipo = tipo; }

    public String getCrc() { return crc; }
    public void setCrc(String crc) { this.crc = crc; }

    public String getUfCrc() { return ufCrc; }
    public void setUfCrc(String ufCrc) { this.ufCrc = ufCrc; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isAssinou() { return assinou; }
    public void setAssinou(boolean assinou) {
        this.assinou = assinou;
    }

    public LocalDateTime getAssinadoEm() { return assinadoEm; }
    public void setAssinadoEm(LocalDateTime assinadoEm) {
        this.assinadoEm = assinadoEm;
    }
}
