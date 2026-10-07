package view.ecf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.GeracaoECF;
import model.ValidacaoECF;
import view.ComponentesContabeis;

public class PainelValidacaoECF extends JPanel {
    private static final long serialVersionUID = 1L;
    private final GeracaoECF geracao;
    private final Runnable aoGerar;
    private final DefaultTableModel modeloTabela = ComponentesContabeis.modeloTabela("Código", "Categoria", "Descrição", "Tipo", "Situação");
    private final JTable tabelaValidacoes = ComponentesContabeis.tabela("ecf.validacoes.tabela", modeloTabela);
    private final JLabel lbl_resumo = new JLabel();
    private final JButton btn_gerar;

    public PainelValidacaoECF(GeracaoECF geracao, Runnable aoGerar) {
        this.geracao = geracao;
        this.aoGerar = aoGerar;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(850, 550));
        JPanel jp_dados = new JPanel(new GridBagLayout());
        jp_dados.setBorder(BorderFactory.createTitledBorder("Configuração analisada"));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        ComponentesContabeis.adicionar_componente(0, jp_dados, "Empresa / ano:", new JLabel(geracao.getEmpresa() + " / " + geracao.getAnoCalendario()));
        ComponentesContabeis.adicionar_componente(1, jp_dados, "Período:", new JLabel(geracao.getPeriodoInicial().format(formato) + " até " + geracao.getPeriodoFinal().format(formato)));
        ComponentesContabeis.adicionar_componente(2, jp_dados, "Escrituração / regime:", new JLabel(geracao.getTipoEscrituracao() + " / " + geracao.getRegimeTributario() + " / " + geracao.getFormaTributacao()));
        ComponentesContabeis.adicionar_componente(3, jp_dados, "Responsável:", new JLabel(geracao.getResponsavel()));
        add(jp_dados, BorderLayout.NORTH);
        add(ComponentesContabeis.lista("Validações", tabelaValidacoes), BorderLayout.CENTER);
        btn_gerar = ComponentesContabeis.botao("ecf.validacoes.gerar", "Gerar ECF", () -> {
            try {
                aoGerar.run();
                ComponentesContabeis.fechar(this);
            } finally {
                atualizar_validacoes();
            }
        });
        JPanel jp_rodape = new JPanel(new BorderLayout());
        lbl_resumo.setName("ecf.validacoes.resumo");
        jp_rodape.add(lbl_resumo, BorderLayout.NORTH);
        jp_rodape.add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("ecf.validacoes.detalhes", "Detalhes", () -> {
                    ValidacaoECF validacao = geracao.getValidacoes().get(ComponentesContabeis.linhaSelecionada(tabelaValidacoes));
                    ComponentesContabeis.abrirDialogo(this, "Detalhes da validação", new PainelDetalhesValidacao(validacao));
                }),
                ComponentesContabeis.botao("ecf.validacoes.fechar", "Fechar", () -> ComponentesContabeis.fechar(this)), btn_gerar), BorderLayout.SOUTH);
        add(jp_rodape, BorderLayout.SOUTH);
        atualizar_validacoes();
    }

    public void atualizar_validacoes() {
        modeloTabela.setRowCount(0);
        for (ValidacaoECF validacao : geracao.getValidacoes()) {
            modeloTabela.addRow(new Object[] {validacao.getCodigo(), validacao.getCategoria(), validacao.getDescricao(),
                    validacao.getTipo(), validacao.isAprovado() ? "Aprovada" : "Requer atenção"});
        }
        lbl_resumo.setText("Erros: " + geracao.contarErros() + "   Alertas: " + geracao.contarAlertas()
                + "   " + geracao.getSituacao());
        btn_gerar.setEnabled(aoGerar != null && !geracao.getValidacoes().isEmpty() && !geracao.possuiErros());
    }
}
