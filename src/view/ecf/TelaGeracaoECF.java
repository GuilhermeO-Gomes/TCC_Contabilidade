package view.ecf;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import model.*;
import view.ComponentesContabeis;

public class TelaGeracaoECF extends JPanel {
  private static final long serialVersionUID = 1L;
  private final DadosContabeis dados;
  private List<GeracaoECF> exibidas = new ArrayList<>();
  private final DefaultTableModel modelo =
      ComponentesContabeis.modeloTabela(
          "ID", "Início", "Fim", "Finalidade", "Regime", "Status", "Erros", "Avisos", "Arquivo");
  private final JTable tabela = ComponentesContabeis.tabela("ecf.historico", modelo);
  private JButton editar, validar, registrar;

  public TelaGeracaoECF() {
    this(new DadosContabeis());
  }

  public TelaGeracaoECF(DadosContabeis dados) {
    this.dados = dados;
    setLayout(new BorderLayout(8, 8));
    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    JPanel topo = new JPanel(new BorderLayout());
    topo.add(
        new JLabel("ECF - empresa #" + dados.getServico().getEmpresaId()),
        BorderLayout.NORTH);
    topo.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao("ecf.nova", "Preparar nova ECF", () -> configurar(null)),
            editar =
                ComponentesContabeis.botao(
                    "ecf.configurar", "Editar configuração", () -> configurar(selecionada())),
            ComponentesContabeis.botao("ecf.atualizar", "Atualizar", this::atualizar_dados)),
        BorderLayout.SOUTH);
    add(topo, BorderLayout.NORTH);
    add(ComponentesContabeis.lista("Histórico persistido", tabela));
    JPanel rodape = new JPanel(new BorderLayout());
    rodape.add(
        new JLabel(
            "Registro de arquivo. Geração de SPED, assinatura e transmissão não disponíveis."),
        BorderLayout.NORTH);
    rodape.add(
        ComponentesContabeis.botoes(
            ComponentesContabeis.botao(
                "ecf.estrutura",
                "Apurações / ajustes / signatários",
                () ->
                    ComponentesContabeis.abrirDialogo(
                        this,
                        "Estrutura da ECF",
                        new PainelEstruturaECF(
                            dados, selecionada().getId(), this::atualizar_dados))),
            validar = ComponentesContabeis.botao("ecf.validar", "Validar", this::validar),
            ComponentesContabeis.botao(
                "ecf.detalhes",
                "Detalhes",
                () ->
                    ComponentesContabeis.abrirDialogo(
                        this,
                        "Validações persistidas",
                        new PainelValidacaoECF(selecionada(), null))),
            registrar =
                ComponentesContabeis.botao("ecf.arquivo", "Registrar arquivo", this::registrar)),
        BorderLayout.SOUTH);
    add(rodape, BorderLayout.SOUTH);
    tabela.getSelectionModel().addListSelectionListener(e -> habilitar());
    habilitar();
    atualizar_dados();
  }

  private void habilitar() {
    GeracaoECF g = tabela.getSelectedRow() < 0 ? null : selecionada();
    boolean editavel =
        g != null
            && (g.getStatus() == StatusECF.EM_PREPARACAO
                || g.getStatus() == StatusECF.COM_ERROS
                || g.getStatus() == StatusECF.VALIDADA);
    editar.setEnabled(editavel);
    validar.setEnabled(editavel);
    registrar.setEnabled(editavel && g.getStatus() == StatusECF.VALIDADA && !g.possuiErros());
  }

  private GeracaoECF selecionada() {
    return exibidas.get(ComponentesContabeis.linhaSelecionada(tabela));
  }

  private void configurar(GeracaoECF original) {
    ComponentesContabeis.abrirDialogo(
        this,
        "Configuração da ECF",
        new PainelConfiguracaoECF(dados, original, this::atualizar_dados));
  }

  private void validar() {
    long id = selecionada().getId();
    ComponentesContabeis.carregar(
        this,
        () -> dados.getServico().validarECF(id),
        g -> {
          atualizar_dados();
          ComponentesContabeis.abrirDialogo(
              this, "Validação da ECF", new PainelValidacaoECF(g, null));
        });
  }

  private void registrar() {
    long id = selecionada().getId();
    JFileChooser arquivo = new JFileChooser();
    arquivo.setDialogTitle("Selecionar arquivo");
    if (arquivo.showOpenDialog(this) != JFileChooser.APPROVE_OPTION) return;
    java.nio.file.Path caminho = arquivo.getSelectedFile().toPath();
    ComponentesContabeis.executar(
        this,
        () -> dados.getServico().registrarArquivoECF(id, caminho),
        () -> {
          atualizar_dados();
          JOptionPane.showMessageDialog(
              this, "Arquivo registrado. O conteúdo fiscal não foi validado.");
        });
  }

  public void atualizar_tela() {
    atualizar_dados();
  }

  public void atualizar_dados() {
    ComponentesContabeis.carregar(
        this,
        () -> dados.getServico().geracoesECF(),
        lista -> {
          tabela.clearSelection();
          modelo.setRowCount(0);
          exibidas = lista;
          for (GeracaoECF g : lista)
            modelo.addRow(
                new Object[] {
                  g.getId(),
                  g.getPeriodoInicial(),
                  g.getPeriodoFinal(),
                  g.getFinalidade(),
                  g.getRegimeTributacao(),
                  g.getStatus(),
                  g.contarErros(),
                  g.contarAlertas(),
                  g.getNomeArquivo()
                });
          habilitar();
        });
  }
}
