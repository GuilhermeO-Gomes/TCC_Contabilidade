package view.ecf;

import java.awt.*;
import java.util.List;
import javax.swing.*;
import model.*;
import view.ComponentesContabeis;

public class PainelConfiguracaoECF extends JPanel {
  private static final long serialVersionUID = 1L;
  private final GeracaoECF geracao;

  public PainelConfiguracaoECF(DadosContabeis dados, GeracaoECF original, Runnable aoSalvar) {
    geracao = original == null ? new GeracaoECF() : original;
    if (geracao.getStatus() != StatusECF.EM_PREPARACAO
        && geracao.getStatus() != StatusECF.COM_ERROS
        && geracao.getStatus() != StatusECF.VALIDADA)
      throw new IllegalStateException("Esta ECF não pode ser editada; prepare uma retificadora.");
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(750, 550));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JComboBox<ExercicioSocial> exercicio = new JComboBox<>();
    JComboBox<GeracaoECF> retificada = new JComboBox<>();
    JComboBox<FinalidadeECF> finalidade = new JComboBox<>(FinalidadeECF.values());
    JComboBox<RegimeTributacao> regime = new JComboBox<>(RegimeTributacao.values());
    JSpinner
        inicio = ComponentesContabeis.criarData("ecf.config.inicio", geracao.getPeriodoInicial()),
        fim = ComponentesContabeis.criarData("ecf.config.fim", geracao.getPeriodoFinal());
    JTextField layout = new JTextField(geracao.getVersaoLayout(), 20),
        ecd =
            new JTextField(
                geracao.getEcdGeracaoId() == null ? "" : geracao.getEcdGeracaoId().toString(), 20),
        observacao = new JTextField(geracao.getObservacao(), 30);
    finalidade.setSelectedItem(geracao.getFinalidade());
    regime.setSelectedItem(geracao.getRegimeTributacao());
    JPanel campos = new JPanel(new GridBagLayout());
    Object[][] linhas = {
      {"Empresa", new JLabel("#" + dados.getServico().getEmpresaId() + "")},
      {"Exercício social", exercicio},
      {"Início", inicio},
      {"Fim", fim},
      {"Finalidade", finalidade},
      {"ECF retificada", retificada},
      {"Regime tributário", regime},
      {"Versão layout (opcional)", layout},
      {"ECD ID (opcional)", ecd},
      {"Observação", observacao}
    };
    for (int i = 0; i < linhas.length; i++)
      ComponentesContabeis.adicionar_componente(
          i, campos, linhas[i][0] + ":", (JComponent) linhas[i][1]);
    add(campos);
    add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao(
                "ecf.config.salvar",
                "Salvar preparação",
                () -> {
                  ExercicioSocial e = (ExercicioSocial) exercicio.getSelectedItem();
                  if (e == null) throw new IllegalArgumentException("Selecione um exercício.");
                  GeracaoECF g = new GeracaoECF();
                  g.setId(geracao.getId());
                  g.setExercicioSocialId(e.getId());
                  g.setPeriodoInicial(ComponentesContabeis.data(inicio));
                  g.setPeriodoFinal(ComponentesContabeis.data(fim));
                  g.setFinalidade((FinalidadeECF) finalidade.getSelectedItem());
                  g.setRegimeTributacao((RegimeTributacao) regime.getSelectedItem());
                  g.setVersaoLayout(layout.getText().trim());
                  g.setEcdGeracaoId(ComponentesContabeis.idOpcional(ecd.getText()));
                  g.setObservacao(observacao.getText().trim());
                  GeracaoECF anterior = (GeracaoECF) retificada.getSelectedItem();
                  g.setEcfRetificadaId(
                      g.getFinalidade() == FinalidadeECF.RETIFICADORA && anterior != null
                          ? anterior.getId()
                          : null);
                  ComponentesContabeis.carregar(
                      this,
                      () -> dados.getServico().prepararECF(g),
                      id -> {
                        aoSalvar.run();
                        ComponentesContabeis.fechar(this);
                      });
                }),
            ComponentesContabeis.botao(
                "ecf.config.cancelar", "Cancelar", () -> ComponentesContabeis.fechar(this))),
        BorderLayout.SOUTH);
    finalidade.addActionListener(
        e -> retificada.setEnabled(finalidade.getSelectedItem() == FinalidadeECF.RETIFICADORA));
    ComponentesContabeis.carregar(
        this,
        () -> {
          Opcoes opcoes = new Opcoes();
          opcoes.exercicios = dados.getServico().exercicios();
          opcoes.geracoes = dados.getServico().geracoesECF();
          return opcoes;
        },
        listas -> {
          for (ExercicioSocial e : listas.exercicios) {
            exercicio.addItem(e);
            if (e.getId() == geracao.getExercicioSocialId()) exercicio.setSelectedItem(e);
          }
          retificada.addItem(null);
          for (GeracaoECF g : listas.geracoes)
            if (g.getId() != geracao.getId()) {
              retificada.addItem(g);
              if (geracao.getEcfRetificadaId() != null && g.getId() == geracao.getEcfRetificadaId())
                retificada.setSelectedItem(g);
            }
          retificada.setEnabled(finalidade.getSelectedItem() == FinalidadeECF.RETIFICADORA);
        });
  }

  private static class Opcoes {
    List<ExercicioSocial> exercicios;
    List<GeracaoECF> geracoes;
  }
}
