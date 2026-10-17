package controller;

import java.util.List;
import model.DadosContabeis;
import model.PlanoContas;
import service.ContabilidadeService;

public class PlanoContasController {
  private final ContabilidadeService servico;

  public PlanoContasController(DadosContabeis dados) {
    servico = dados.getServico();
  }

  public List<PlanoContas> listar() {
    return servico.contas(false);
  }

  public long salvar(PlanoContas conta) {
    return servico.salvarConta(conta);
  }
}
