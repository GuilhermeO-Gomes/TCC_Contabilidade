package model;

import service.ContabilidadeService;

public class DadosContabeis {
  private final ContabilidadeService servico;

  public DadosContabeis() {
    this(
        idConfigurado("erp.empresaId", "ERP_EMPRESA_ID"),
        idConfigurado("erp.usuarioId", "ERP_USUARIO_ID"));
  }

  public DadosContabeis(long empresaId, long usuarioId) {
    servico = new ContabilidadeService(empresaId, usuarioId);
  }

  private static long idConfigurado(String propriedade, String variavel) {
    String valor = System.getProperty(propriedade, System.getenv(variavel));
    if (valor == null || valor.trim().isEmpty()) return 0;
    try {
      return Long.parseLong(valor);
    } catch (NumberFormatException erro) {
      throw new IllegalArgumentException("ID inválido: " + variavel);
    }
  }

  public ContabilidadeService getServico() {
    return servico;
  }
}
