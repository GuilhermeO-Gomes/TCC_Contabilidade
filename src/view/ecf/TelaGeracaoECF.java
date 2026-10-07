package view.ecf;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DadosContabeis;
import model.GeracaoECF;
import view.ComponentesContabeis;

public class TelaGeracaoECF extends JPanel {
    private static final long serialVersionUID = 1L;
    private final DadosContabeis dados;
    private GeracaoECF configuracao = new GeracaoECF();
    private final JLabel lbl_empresa = new JLabel();
    private final JLabel lbl_ano = new JLabel();
    private final JLabel lbl_situacao = new JLabel();
    private final DefaultTableModel modeloTabela = ComponentesContabeis.modeloTabela("ID", "Ano", "Data", "Tipo", "Situação", "Erros", "Alertas");
    private final JTable tabelaHistorico = ComponentesContabeis.tabela("ecf.historico", modeloTabela);

    public TelaGeracaoECF() {
        this(new DadosContabeis());
    }

    public TelaGeracaoECF(DadosContabeis dados) {
        this.dados = dados;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar_tela();
        atualizar_tela();
    }

    private void montar_tela() {
        JPanel jp_form = new JPanel(new GridBagLayout());
        jp_form.setBorder(BorderFactory.createTitledBorder("Escrituração Contábil Fiscal"));
        lbl_empresa.setName("ecf.empresa");
        lbl_ano.setName("ecf.ano");
        lbl_situacao.setName("ecf.situacao");
        ComponentesContabeis.adicionar_componente(0, jp_form, "Empresa:", lbl_empresa);
        ComponentesContabeis.adicionar_componente(1, jp_form, "Ano-calendário:", lbl_ano);
        ComponentesContabeis.adicionar_componente(2, jp_form, "Situação:", lbl_situacao);
        JPanel jp_topo = new JPanel(new BorderLayout());
        jp_topo.add(jp_form, BorderLayout.CENTER);
        jp_topo.add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("ecf.configurar", "Configurar", () -> ComponentesContabeis.abrirDialogo(this,
                        "Configuração da ECF", new PainelConfiguracaoECF(configuracao, nova -> {
                            configuracao = nova;
                            atualizar_tela();
                        }))),
                ComponentesContabeis.botao("ecf.validar", "Validar Dados", () -> abrir_validacoes(configuracao)),
                ComponentesContabeis.botao("ecf.gerar", "Gerar ECF", () -> gerar_demonstracao(configuracao))), BorderLayout.SOUTH);
        add(jp_topo, BorderLayout.NORTH);
        add(ComponentesContabeis.lista("Histórico de gerações desta sessão", tabelaHistorico), BorderLayout.CENTER);
        JPanel jp_rodape = new JPanel(new BorderLayout());
        jp_rodape.add(new JLabel("Gerar ECF registra uma simulação em memória. Nenhum arquivo fiscal é produzido."), BorderLayout.NORTH);
        jp_rodape.add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("ecf.visualizar", "Visualizar", () -> ComponentesContabeis.abrirDialogo(this,
                        "Histórico: configuração e validações", new PainelValidacaoECF(selecionado(), null))),
                ComponentesContabeis.botao("ecf.revalidar", "Validar novamente", () -> abrir_validacoes(selecionado()))), BorderLayout.SOUTH);
        add(jp_rodape, BorderLayout.SOUTH);
    }

    private GeracaoECF selecionado() {
        return dados.getGeracoes().get(ComponentesContabeis.linhaSelecionada(tabelaHistorico));
    }

    private void abrir_validacoes(GeracaoECF geracao) {
        geracao.validarDados(dados.getLancamentos());
        atualizar_tela();
        ComponentesContabeis.abrirDialogo(this, "Validação da ECF", new PainelValidacaoECF(geracao,
                () -> gerar_demonstracao(geracao)));
    }

    private void gerar_demonstracao(GeracaoECF origem) {
        origem.validarDados(dados.getLancamentos());
        atualizar_tela();
        if (origem.possuiErros()) {
            throw new IllegalStateException("Existem " + origem.contarErros() + " erro(s). Use Validar Dados e corrija os lançamentos antes de gerar.");
        }
        GeracaoECF nova = new GeracaoECF(origem);
        nova.setId(dados.proximoIdGeracao());
        nova.gerar(dados.getLancamentos());
        dados.getGeracoes().add(nova);
        if (origem == configuracao) {
            configuracao.setSituacao(nova.getSituacao());
            configuracao.setDataGeracao(nova.getDataGeracao());
        }
        atualizar_tela();
    }

    public void atualizar_tela() {
        lbl_empresa.setText(configuracao.getEmpresa().isEmpty() ? "Preencha em Configurar" : configuracao.getEmpresa());
        lbl_ano.setText(String.valueOf(configuracao.getAnoCalendario()));
        lbl_situacao.setText(configuracao.getSituacao());
        modeloTabela.setRowCount(0);
        for (GeracaoECF geracao : dados.getGeracoes()) {
            modeloTabela.addRow(new Object[] {geracao.getId(), geracao.getAnoCalendario(), geracao.getDataGeracao(),
                    geracao.getTipoEscrituracao(), geracao.getSituacao(), geracao.contarErros(), geracao.contarAlertas()});
        }
    }

    public void atualizar_dados() {
        if (!configuracao.getValidacoes().isEmpty()) {
            configuracao.validarDados(dados.getLancamentos());
        }
        atualizar_tela();
    }
}
