package model;

import java.time.LocalDateTime;

public class EcfSignatario {
  private long id;
  private long ecfId;
  private String nome = "";
  private String cpf = "";
  private TipoSignatarioECF tipo = TipoSignatarioECF.RESPONSAVEL_LEGAL;
  private String crc = "";
  private String ufCrc = "";
  private String email = "";
  private boolean assinou = false;
  private LocalDateTime assinadoEm;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getEcfId() {
    return ecfId;
  }

  public void setEcfId(long ecfId) {
    this.ecfId = ecfId;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getCpf() {
    return cpf;
  }

  public void setCpf(String cpf) {
    this.cpf = cpf;
  }

  public TipoSignatarioECF getTipo() {
    return tipo;
  }

  public void setTipo(TipoSignatarioECF tipo) {
    this.tipo = tipo;
  }

  public String getCrc() {
    return crc;
  }

  public void setCrc(String crc) {
    this.crc = crc;
  }

  public String getUfCrc() {
    return ufCrc;
  }

  public void setUfCrc(String ufCrc) {
    this.ufCrc = ufCrc;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public boolean getAssinou() {
    return assinou;
  }

  public void setAssinou(boolean assinou) {
    this.assinou = assinou;
  }

  public LocalDateTime getAssinadoEm() {
    return assinadoEm;
  }

  public void setAssinadoEm(LocalDateTime assinadoEm) {
    this.assinadoEm = assinadoEm;
  }
}
