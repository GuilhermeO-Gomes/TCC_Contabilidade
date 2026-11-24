package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaApuracaoImpostos extends JPanel {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> cbMes;
    private JTextField txtAno;
    private JComboBox<String> cbRegimeTributario;
    private JComboBox<String> cbStatus;

    private JTextField txtTotalDebitos;
    private JTextField txtTotalCreditos;
    private JTextField txtTotalAjustes;
    private JTextField txtSaldoApurado;

    private JTextField txtTotalRetencoes;
    private JTextField txtValorARecolher;
    private JTextField txtSaldoCredor;
    private JTextField txtTotalDivergencias;

    private JTable tabelaApuracoes;
    private JTable tabelaDetalhamento;
    private JTable tabelaRetencoes;

    private DefaultTableModel modeloApuracoes;
    private DefaultTableModel modeloDetalhamento;
    private DefaultTableModel modeloRetencoes;

    private JTextField txtDescricaoDivergencia;
    private JTextField txtJustificativaCorrecao;
    private JComboBox<String> cbSituacaoCorrecao;

    private JButton btnNova;
    private JButton btnCalcular;
    private JButton btnSalvar;
    private JButton btnEditar;
    private JButton btnExcluir;
    private JButton btnLimpar;
    private JButton btnConsultar;
    private JButton btnFinalizar;
    private JButton btnGerarRelatorio;
    private JButton btnConsultarHistorico;
    private JButton btnCorrigirDivergencia;

    public TelaApuracaoImpostos() {
        montar();
        carregarDadosTeste();
    }

    private void montar() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(criarPainelFiltros(), BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Apurações", criarPainelApuracoes());
        abas.addTab("Detalhamento dos Documentos", criarPainelDetalhamento());
        abas.addTab("Retenções e Ajustes", criarPainelRetencoes());

        add(abas, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(5, 5));
        painelInferior.add(criarPainelResumo(), BorderLayout.CENTER);
        painelInferior.add(criarPainelBotoes(), BorderLayout.SOUTH);

        add(painelInferior, BorderLayout.SOUTH);
    }

    private JPanel criarPainelFiltros() {
        JPanel painel = new JPanel(new GridLayout(1, 4, 10, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Parâmetros da Apuração"));

        cbMes = new JComboBox<>(new String[] {
            "Janeiro", "Fevereiro", "Março", "Abril",
            "Maio", "Junho", "Julho", "Agosto",
            "Setembro", "Outubro", "Novembro", "Dezembro"
        });

        txtAno = new JTextField("2026");

        cbRegimeTributario = new JComboBox<>(new String[] {
            "Simples Nacional",
            "Lucro Presumido",
            "Lucro Real"
        });

        cbStatus = new JComboBox<>(new String[] {
            "Todas",
            "Pendente",
            "Em apuração",
            "Com divergência",
            "Calculada",
            "Encerrada"
        });

        adicionarCampo(painel, "Mês:", cbMes);
        adicionarCampo(painel, "Ano:", txtAno);
        adicionarCampo(painel, "Regime Tributário:", cbRegimeTributario);
        adicionarCampo(painel, "Situação:", cbStatus);

        return painel;
    }

    private JPanel criarPainelApuracoes() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));

        String[] colunas = {
            "Imposto",
            "Débitos",
            "Créditos",
            "Retenções",
            "Ajustes",
            "Saldo Apurado",
            "A Recolher",
            "Situação"
        };

        modeloApuracoes = criarModeloNaoEditavel(colunas);
        tabelaApuracoes = new JTable(modeloApuracoes);
        configurarTabela(tabelaApuracoes);

        painel.add(new JScrollPane(tabelaApuracoes), BorderLayout.CENTER);

        JLabel observacao = new JLabel(
            "Selecione um imposto para consultar seus documentos e valores de composição."
        );
        observacao.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painel.add(observacao, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelDetalhamento() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));

        String[] colunas = {
            "Origem",
            "Documento",
            "Data",
            "Imposto",
            "Valor Débito",
            "Valor Crédito",
            "Retenção",
            "Situação"
        };

        modeloDetalhamento = criarModeloNaoEditavel(colunas);
        tabelaDetalhamento = new JTable(modeloDetalhamento);
        configurarTabela(tabelaDetalhamento);

        painel.add(new JScrollPane(tabelaDetalhamento), BorderLayout.CENTER);

        JLabel observacao = new JLabel(
            "Detalhamento dos documentos fiscais considerados na apuração do período."
        );
        observacao.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        painel.add(observacao, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelRetencoes() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));

        String[] colunas = {
            "Imposto",
            "Tipo de Retenção",
            "Documento de Origem",
            "Data",
            "Valor Retido",
            "Situação"
        };

        modeloRetencoes = criarModeloNaoEditavel(colunas);
        tabelaRetencoes = new JTable(modeloRetencoes);
        configurarTabela(tabelaRetencoes);

        painel.add(new JScrollPane(tabelaRetencoes), BorderLayout.CENTER);

        JPanel painelCorrecao = new JPanel(new GridLayout(1, 3, 10, 5));
        painelCorrecao.setBorder(
            BorderFactory.createTitledBorder("Análise de Divergências e Correções")
        );

        txtDescricaoDivergencia = new JTextField();
        txtJustificativaCorrecao = new JTextField();
        cbSituacaoCorrecao = new JComboBox<>(new String[] {
            "Não analisada",
            "Em análise",
            "Corrigida",
            "Justificada"
        });

        adicionarCampo(painelCorrecao, "Divergência identificada:", txtDescricaoDivergencia);
        adicionarCampo(painelCorrecao, "Justificativa / Correção:", txtJustificativaCorrecao);
        adicionarCampo(painelCorrecao, "Situação da correção:", cbSituacaoCorrecao);

        painel.add(painelCorrecao, BorderLayout.SOUTH);

        return painel;
    }

    private DefaultTableModel criarModeloNaoEditavel(String[] colunas) {
        return new DefaultTableModel(colunas, 0) {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    private void configurarTabela(JTable tabela) {
        tabela.setRowHeight(25);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    }

    private JPanel criarPainelResumo() {
        JPanel painel = new JPanel(new GridLayout(2, 4, 10, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Resumo da Apuração"));

        txtTotalDebitos = criarCampoResumo();
        txtTotalCreditos = criarCampoResumo();
        txtTotalAjustes = criarCampoResumo();
        txtSaldoApurado = criarCampoResumo();
        txtTotalRetencoes = criarCampoResumo();
        txtValorARecolher = criarCampoResumo();
        txtSaldoCredor = criarCampoResumo();
        txtTotalDivergencias = criarCampoResumo();

        adicionarCampo(painel, "Total de Débitos (R$):", txtTotalDebitos);
        adicionarCampo(painel, "Total de Créditos (R$):", txtTotalCreditos);
        adicionarCampo(painel, "Total de Ajustes (R$):", txtTotalAjustes);
        adicionarCampo(painel, "Saldo Apurado (R$):", txtSaldoApurado);
        adicionarCampo(painel, "Total de Retenções (R$):", txtTotalRetencoes);
        adicionarCampo(painel, "Valor a Recolher (R$):", txtValorARecolher);
        adicionarCampo(painel, "Saldo Credor (R$):", txtSaldoCredor);
        adicionarCampo(painel, "Divergências:", txtTotalDivergencias);

        return painel;
    }

    private JTextField criarCampoResumo() {
        JTextField campo = new JTextField();
        campo.setHorizontalAlignment(JTextField.RIGHT);
        campo.setEditable(false);
        return campo;
    }

    private JPanel criarPainelBotoes() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));

        btnNova = new JButton("Nova Apuração");
        btnCalcular = new JButton("Calcular");
        btnSalvar = new JButton("Salvar");
        btnEditar = new JButton("Editar");
        btnExcluir = new JButton("Excluir");
        btnLimpar = new JButton("Limpar");
        btnConsultar = new JButton("Consultar");
        btnConsultarHistorico = new JButton("Consultar Histórico");
        btnCorrigirDivergencia = new JButton("Tratar Divergência");
        btnFinalizar = new JButton("Encerrar Apuração");
        btnGerarRelatorio = new JButton("Gerar Relatório");

        painel.add(btnNova);
        painel.add(btnCalcular);
        painel.add(btnSalvar);
        painel.add(btnEditar);
        painel.add(btnExcluir);
        painel.add(btnLimpar);
        painel.add(btnConsultar);
        painel.add(btnConsultarHistorico);
        painel.add(btnCorrigirDivergencia);
        painel.add(btnFinalizar);
        painel.add(btnGerarRelatorio);

        return painel;
    }

    private void adicionarCampo(JPanel painel, String texto, JComponent componente) {
        JPanel grupo = new JPanel(new BorderLayout(3, 3));
        grupo.add(new JLabel(texto), BorderLayout.NORTH);
        grupo.add(componente, BorderLayout.CENTER);
        painel.add(grupo);
    }

    /**
     * Versão ampliada: inclui retenções e valor a recolher.
     */
    public void adicionarApuracao(
        String imposto,
        String debitos,
        String creditos,
        String retencoes,
        String ajustes,
        String saldo,
        String valorARecolher,
        String situacao
    ) {
        modeloApuracoes.addRow(new Object[] {
            imposto, debitos, creditos, retencoes,
            ajustes, saldo, valorARecolher, situacao
        });
    }

    /**
     * Método anterior mantido para compatibilidade.
     */
    public void adicionarApuracao(
        String imposto,
        String debitos,
        String creditos,
        String ajustes,
        String saldo,
        String situacao
    ) {
        adicionarApuracao(
            imposto, debitos, creditos, "0,00",
            ajustes, saldo, saldo, situacao
        );
    }

    /**
     * Versão ampliada do detalhamento, com retenção e situação.
     */
    public void adicionarDetalhamento(
        String origem,
        String documento,
        String data,
        String imposto,
        String valorDebito,
        String valorCredito,
        String retencao,
        String situacao
    ) {
        modeloDetalhamento.addRow(new Object[] {
            origem, documento, data, imposto,
            valorDebito, valorCredito, retencao, situacao
        });
    }

    /**
     * Método anterior mantido para compatibilidade.
     */
    public void adicionarDetalhamento(
        String origem,
        String documento,
        String data,
        String imposto,
        String valorDebito,
        String valorCredito
    ) {
        adicionarDetalhamento(
            origem, documento, data, imposto,
            valorDebito, valorCredito, "0,00", "Considerado"
        );
    }

    public void adicionarRetencao(
        String imposto,
        String tipoRetencao,
        String documentoOrigem,
        String data,
        String valorRetido,
        String situacao
    ) {
        modeloRetencoes.addRow(new Object[] {
            imposto, tipoRetencao, documentoOrigem,
            data, valorRetido, situacao
        });
    }

    public void limparTabelaApuracoes() {
        modeloApuracoes.setRowCount(0);
    }

    public void limparTabelaDetalhamento() {
        modeloDetalhamento.setRowCount(0);
    }

    public void limparTabelaRetencoes() {
        modeloRetencoes.setRowCount(0);
    }

    private void carregarDadosTeste() {
        adicionarApuracao(
            "ICMS", "8.500,00", "3.200,00", "0,00",
            "0,00", "5.300,00", "5.300,00", "Calculada"
        );

        adicionarApuracao(
            "IPI", "1.200,00", "400,00", "0,00",
            "0,00", "800,00", "800,00", "Calculada"
        );

        adicionarApuracao(
            "PIS", "950,00", "250,00", "45,00",
            "0,00", "700,00", "655,00", "Calculada"
        );

        adicionarApuracao(
            "COFINS", "4.300,00", "1.100,00", "120,00",
            "0,00", "3.200,00", "3.080,00", "Calculada"
        );

        adicionarApuracao(
            "ISS", "600,00", "0,00", "80,00",
            "0,00", "600,00", "520,00", "Com divergência"
        );

        adicionarDetalhamento(
            "Nota de Saída", "000245", "18/11/2026",
            "ICMS", "5.000,00", "0,00", "0,00", "Considerado"
        );

        adicionarDetalhamento(
            "Nota de Entrada", "000125", "11/11/2026",
            "ICMS", "0,00", "2.000,00", "0,00", "Considerado"
        );

        adicionarDetalhamento(
            "NFS-e de Saída", "000246", "20/11/2026",
            "ISS", "600,00", "0,00", "80,00", "Com divergência"
        );

        adicionarRetencao(
            "PIS", "PIS retido na fonte", "NFS-e 000245",
            "18/11/2026", "45,00", "Considerada"
        );

        adicionarRetencao(
            "COFINS", "COFINS retida na fonte", "NFS-e 000245",
            "18/11/2026", "120,00", "Considerada"
        );

        adicionarRetencao(
            "ISS", "ISS retido pelo tomador", "NFS-e 000246",
            "20/11/2026", "80,00", "Pendente de conferência"
        );

        txtTotalDebitos.setText("15.550,00");
        txtTotalCreditos.setText("4.950,00");
        txtTotalAjustes.setText("0,00");
        txtSaldoApurado.setText("10.600,00");
        txtTotalRetencoes.setText("245,00");
        txtValorARecolher.setText("10.355,00");
        txtSaldoCredor.setText("0,00");
        txtTotalDivergencias.setText("1");
    }

    // Getters dos filtros

    public JComboBox<String> getCbMes() {
        return cbMes;
    }

    public JTextField getTxtAno() {
        return txtAno;
    }

    public JComboBox<String> getCbRegimeTributario() {
        return cbRegimeTributario;
    }

    public JComboBox<String> getCbStatus() {
        return cbStatus;
    }

    // Getters do resumo original

    public JTextField getTxtTotalDebitos() {
        return txtTotalDebitos;
    }

    public JTextField getTxtTotalCreditos() {
        return txtTotalCreditos;
    }

    public JTextField getTxtTotalAjustes() {
        return txtTotalAjustes;
    }

    public JTextField getTxtSaldoApurado() {
        return txtSaldoApurado;
    }

    // Getters do resumo adicional

    public JTextField getTxtTotalRetencoes() {
        return txtTotalRetencoes;
    }

    public JTextField getTxtValorARecolher() {
        return txtValorARecolher;
    }

    public JTextField getTxtSaldoCredor() {
        return txtSaldoCredor;
    }

    public JTextField getTxtTotalDivergencias() {
        return txtTotalDivergencias;
    }

    // Getters das tabelas

    public JTable getTabelaApuracoes() {
        return tabelaApuracoes;
    }

    public JTable getTabelaDetalhamento() {
        return tabelaDetalhamento;
    }

    public JTable getTabelaRetencoes() {
        return tabelaRetencoes;
    }

    // Getters dos campos de correção

    public JTextField getTxtDescricaoDivergencia() {
        return txtDescricaoDivergencia;
    }

    public JTextField getTxtJustificativaCorrecao() {
        return txtJustificativaCorrecao;
    }

    public JComboBox<String> getCbSituacaoCorrecao() {
        return cbSituacaoCorrecao;
    }

    // Getters dos botões

    public JButton getBtnNova() {
        return btnNova;
    }

    public JButton getBtnCalcular() {
        return btnCalcular;
    }

    public JButton getBtnSalvar() {
        return btnSalvar;
    }

    public JButton getBtnEditar() {
        return btnEditar;
    }

    public JButton getBtnExcluir() {
        return btnExcluir;
    }

    public JButton getBtnLimpar() {
        return btnLimpar;
    }

    public JButton getBtnConsultar() {
        return btnConsultar;
    }

    public JButton getBtnFinalizar() {
        return btnFinalizar;
    }

    public JButton getBtnGerarRelatorio() {
        return btnGerarRelatorio;
    }

    public JButton getBtnConsultarHistorico() {
        return btnConsultarHistorico;
    }

    public JButton getBtnCorrigirDivergencia() {
        return btnCorrigirDivergencia;
    }
}
