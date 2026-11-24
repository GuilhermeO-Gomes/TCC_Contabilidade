package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaIntegracaoFiscalContabil extends JPanel {

    private static final long serialVersionUID = 1L;

    private JComboBox<String> cbMes;
    private JTextField txtAno;
    private JComboBox<String> cbSituacao;
    private JTextField txtDocumentoOrigem;

    private JTextField txtTotalFiscal;
    private JTextField txtTotalContabil;
    private JTextField txtTotalDiferenca;
    private JTextField txtDocumentosPendentes;
    private JTextField txtDocumentosIntegrados;
    private JTextField txtIntegracoesComErro;

    private JTable tabelaConciliacao;
    private JTable tabelaLancamentos;
    private JTable tabelaHistorico;

    private DefaultTableModel modeloConciliacao;
    private DefaultTableModel modeloLancamentos;
    private DefaultTableModel modeloHistorico;

    private JTextField txtDataHoraIntegracao;
    private JTextField txtIdentificadorLancamento;
    private JTextField txtQuantidadeTentativas;
    private JTextArea txtMensagemErro;

    private JButton btnConsultar;
    private JButton btnIntegrar;
    private JButton btnReprocessar;
    private JButton btnVisualizar;
    private JButton btnGerarRelatorio;
    private JButton btnLimpar;
    private JButton btnConsultarHistorico;
    private JButton btnVisualizarOrigem;

    public TelaIntegracaoFiscalContabil() {
        montar();
        carregarDadosTeste();
    }

    private void montar() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(criarPainelFiltros(), BorderLayout.NORTH);

        JTabbedPane abas = new JTabbedPane();
        abas.addTab("Conciliação Fiscal x Contábil", criarPainelConciliacao());
        abas.addTab("Lançamentos Contábeis", criarPainelLancamentos());
        abas.addTab("Histórico e Ocorrências", criarPainelHistorico());
        add(abas, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new BorderLayout(5, 5));
        painelInferior.add(criarPainelResumo(), BorderLayout.CENTER);
        painelInferior.add(criarPainelBotoes(), BorderLayout.SOUTH);
        add(painelInferior, BorderLayout.SOUTH);
    }

    private JPanel criarPainelFiltros() {
        JPanel painel = new JPanel(new GridLayout(2, 3, 10, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Parâmetros da Integração"));

        cbMes = new JComboBox<>(new String[] {
            "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
            "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        });
        txtAno = new JTextField("2026");
        cbSituacao = new JComboBox<>(new String[] {
            "Todas", "Pendente", "Integrada", "Conciliada",
            "Com Divergência", "Com Erro", "Reprocessamento"
        });
        txtDocumentoOrigem = new JTextField();

        adicionarCampo(painel, "Mês:", cbMes);
        adicionarCampo(painel, "Ano:", txtAno);
        adicionarCampo(painel, "Situação:", cbSituacao);
        adicionarCampo(painel, "Documento de Origem:", txtDocumentoOrigem);

        JLabel dica = new JLabel("Consulte por período, situação ou documento fiscal.");
        dica.setForeground(Color.DARK_GRAY);
        painel.add(dica);
        painel.add(new JLabel());

        return painel;
    }

    private JPanel criarPainelConciliacao() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        String[] colunas = {
            "Imposto / Operação", "Documento de Origem", "Conta Contábil",
            "Valor Fiscal", "Valor Contábil", "Diferença", "Situação"
        };
        modeloConciliacao = criarModeloTabela(colunas);
        tabelaConciliacao = new JTable(modeloConciliacao);
        configurarTabela(tabelaConciliacao);
        painel.add(new JScrollPane(tabelaConciliacao), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelLancamentos() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        String[] colunas = {
            "Documento de Origem", "ID Lançamento Contábil", "Origem",
            "Conta Contábil", "Data/Hora", "Débito", "Crédito", "Situação"
        };
        modeloLancamentos = criarModeloTabela(colunas);
        tabelaLancamentos = new JTable(modeloLancamentos);
        configurarTabela(tabelaLancamentos);
        painel.add(new JScrollPane(tabelaLancamentos), BorderLayout.CENTER);
        return painel;
    }

    private JPanel criarPainelHistorico() {
        JPanel painel = new JPanel(new BorderLayout(5, 8));

        String[] colunas = {
            "Data/Hora", "Documento de Origem", "Operação",
            "Tentativas", "ID Lançamento", "Situação", "Ocorrência"
        };
        modeloHistorico = criarModeloTabela(colunas);
        tabelaHistorico = new JTable(modeloHistorico);
        configurarTabela(tabelaHistorico);

        painel.add(new JScrollPane(tabelaHistorico), BorderLayout.CENTER);

        JPanel detalhes = new JPanel(new GridBagLayout());
        detalhes.setBorder(BorderFactory.createTitledBorder("Detalhes da Integração / Tratamento de Erro"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 5, 4, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        txtDataHoraIntegracao = new JTextField();
        txtDataHoraIntegracao.setEditable(false);
        txtIdentificadorLancamento = new JTextField();
        txtIdentificadorLancamento.setEditable(false);
        txtQuantidadeTentativas = new JTextField();
        txtQuantidadeTentativas.setEditable(false);
        txtMensagemErro = new JTextArea(3, 30);
        txtMensagemErro.setLineWrap(true);
        txtMensagemErro.setWrapStyleWord(true);
        txtMensagemErro.setEditable(false);

        gbc.gridx = 0; gbc.gridy = 0;
        detalhes.add(new JLabel("Data/Hora da Integração:"), gbc);
        gbc.gridx = 1;
        detalhes.add(txtDataHoraIntegracao, gbc);
        gbc.gridx = 2;
        detalhes.add(new JLabel("ID do Lançamento Contábil:"), gbc);
        gbc.gridx = 3;
        detalhes.add(txtIdentificadorLancamento, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        detalhes.add(new JLabel("Quantidade de Tentativas:"), gbc);
        gbc.gridx = 1;
        detalhes.add(txtQuantidadeTentativas, gbc);
        gbc.gridx = 2;
        detalhes.add(new JLabel("Mensagem / Motivo do Erro:"), gbc);
        gbc.gridx = 3;
        gbc.gridwidth = 1;
        detalhes.add(new JScrollPane(txtMensagemErro), gbc);

        painel.add(detalhes, BorderLayout.SOUTH);
        return painel;
    }

    private DefaultTableModel criarModeloTabela(String[] colunas) {
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
        JPanel painel = new JPanel(new GridLayout(2, 3, 10, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Resumo da Integração"));

        txtTotalFiscal = criarCampoResumo();
        txtTotalContabil = criarCampoResumo();
        txtTotalDiferenca = criarCampoResumo();
        txtDocumentosPendentes = criarCampoResumo();
        txtDocumentosIntegrados = criarCampoResumo();
        txtIntegracoesComErro = criarCampoResumo();

        adicionarCampo(painel, "Total Fiscal:", txtTotalFiscal);
        adicionarCampo(painel, "Total Contábil:", txtTotalContabil);
        adicionarCampo(painel, "Diferença:", txtTotalDiferenca);
        adicionarCampo(painel, "Documentos Pendentes:", txtDocumentosPendentes);
        adicionarCampo(painel, "Documentos Integrados:", txtDocumentosIntegrados);
        adicionarCampo(painel, "Integrações com Erro:", txtIntegracoesComErro);
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
        btnConsultar = new JButton("Consultar");
        btnIntegrar = new JButton("Integrar");
        btnReprocessar = new JButton("Reprocessar");
        btnVisualizar = new JButton("Visualizar Lançamento");
        btnVisualizarOrigem = new JButton("Documento de Origem");
        btnConsultarHistorico = new JButton("Consultar Histórico");
        btnGerarRelatorio = new JButton("Gerar Relatório");
        btnLimpar = new JButton("Limpar");

        painel.add(btnConsultar);
        painel.add(btnIntegrar);
        painel.add(btnReprocessar);
        painel.add(btnVisualizar);
        painel.add(btnVisualizarOrigem);
        painel.add(btnConsultarHistorico);
        painel.add(btnGerarRelatorio);
        painel.add(btnLimpar);
        return painel;
    }

    private void adicionarCampo(JPanel painel, String texto, JComponent componente) {
        JPanel grupo = new JPanel(new BorderLayout(3, 3));
        grupo.add(new JLabel(texto), BorderLayout.NORTH);
        grupo.add(componente, BorderLayout.CENTER);
        painel.add(grupo);
    }

    // Métodos mantidos para compatibilidade com a futura camada Controller.
    public void adicionarConciliacao(String impostoOperacao, String contaContabil,
            String valorFiscal, String valorContabil, String diferenca, String situacao) {
        adicionarConciliacao(impostoOperacao, "-", contaContabil, valorFiscal,
                valorContabil, diferenca, situacao);
    }

    public void adicionarConciliacao(String impostoOperacao, String documentoOrigem,
            String contaContabil, String valorFiscal, String valorContabil,
            String diferenca, String situacao) {
        modeloConciliacao.addRow(new Object[] {
            impostoOperacao, documentoOrigem, contaContabil, valorFiscal,
            valorContabil, diferenca, situacao
        });
    }

    public void adicionarLancamento(String documento, String origem, String contaContabil,
            String data, String debito, String credito, String situacao) {
        adicionarLancamento(documento, "-", origem, contaContabil, data,
                debito, credito, situacao);
    }

    public void adicionarLancamento(String documento, String idLancamento, String origem,
            String contaContabil, String dataHora, String debito, String credito,
            String situacao) {
        modeloLancamentos.addRow(new Object[] {
            documento, idLancamento, origem, contaContabil, dataHora,
            debito, credito, situacao
        });
    }

    public void adicionarHistorico(String dataHora, String documentoOrigem,
            String operacao, String tentativas, String idLancamento,
            String situacao, String ocorrencia) {
        modeloHistorico.addRow(new Object[] {
            dataHora, documentoOrigem, operacao, tentativas,
            idLancamento, situacao, ocorrencia
        });
    }

    public void limparTabelaConciliacao() { modeloConciliacao.setRowCount(0); }
    public void limparTabelaLancamentos() { modeloLancamentos.setRowCount(0); }
    public void limparTabelaHistorico() { modeloHistorico.setRowCount(0); }

    private void carregarDadosTeste() {
        adicionarConciliacao("ICMS", "NF-e 000125", "2.01.01 - ICMS a Recolher",
                "5.300,00", "5.300,00", "0,00", "Conciliado");
        adicionarConciliacao("IPI", "NF-e 000126", "2.01.02 - IPI a Recolher",
                "800,00", "750,00", "50,00", "Com Divergência");
        adicionarConciliacao("PIS", "NFS-e 000245", "2.01.03 - PIS a Recolher",
                "700,00", "700,00", "0,00", "Conciliado");
        adicionarConciliacao("COFINS", "NFS-e 000245", "2.01.04 - COFINS a Recolher",
                "3.200,00", "3.200,00", "0,00", "Conciliado");

        adicionarLancamento("NF-e 000125", "CTB-2026-00125", "Nota Fiscal de Entrada",
                "1.01.02 - Estoques", "11/11/2026 14:32", "5.250,00", "0,00", "Integrado");
        adicionarLancamento("NFS-e 000245", "CTB-2026-00245", "Nota Fiscal de Saída",
                "1.01.01 - Clientes", "18/11/2026 10:15", "2.060,00", "0,00", "Integrado");
        adicionarLancamento("NFS-e 000246", "-", "Nota Fiscal de Saída",
                "3.01.01 - Receita de Serviços", "-", "0,00", "3.450,00", "Pendente");

        adicionarHistorico("18/11/2026 10:15", "NFS-e 000245", "Integração",
                "1", "CTB-2026-00245", "Integrada", "Integração concluída com sucesso");
        adicionarHistorico("20/11/2026 09:42", "NFS-e 000246", "Integração",
                "2", "-", "Com Erro", "Conta contábil de destino não identificada");
        adicionarHistorico("20/11/2026 10:05", "NFS-e 000246", "Reprocessamento",
                "3", "-", "Pendente", "Aguardando correção do vínculo contábil");

        txtDataHoraIntegracao.setText("20/11/2026 10:05");
        txtIdentificadorLancamento.setText("Não gerado");
        txtQuantidadeTentativas.setText("3");
        txtMensagemErro.setText("Conta contábil de destino não identificada. "
                + "Documento mantido pendente para correção e novo processamento.");

        txtTotalFiscal.setText("10.000,00");
        txtTotalContabil.setText("9.950,00");
        txtTotalDiferenca.setText("50,00");
        txtDocumentosPendentes.setText("1");
        txtDocumentosIntegrados.setText("2");
        txtIntegracoesComErro.setText("1");
    }

    public JComboBox<String> getCbMes() { return cbMes; }
    public JTextField getTxtAno() { return txtAno; }
    public JComboBox<String> getCbSituacao() { return cbSituacao; }
    public JTextField getTxtDocumentoOrigem() { return txtDocumentoOrigem; }

    public JTextField getTxtTotalFiscal() { return txtTotalFiscal; }
    public JTextField getTxtTotalContabil() { return txtTotalContabil; }
    public JTextField getTxtTotalDiferenca() { return txtTotalDiferenca; }
    public JTextField getTxtDocumentosPendentes() { return txtDocumentosPendentes; }
    public JTextField getTxtDocumentosIntegrados() { return txtDocumentosIntegrados; }
    public JTextField getTxtIntegracoesComErro() { return txtIntegracoesComErro; }

    public JTable getTabelaConciliacao() { return tabelaConciliacao; }
    public JTable getTabelaLancamentos() { return tabelaLancamentos; }
    public JTable getTabelaHistorico() { return tabelaHistorico; }

    public JTextField getTxtDataHoraIntegracao() { return txtDataHoraIntegracao; }
    public JTextField getTxtIdentificadorLancamento() { return txtIdentificadorLancamento; }
    public JTextField getTxtQuantidadeTentativas() { return txtQuantidadeTentativas; }
    public JTextArea getTxtMensagemErro() { return txtMensagemErro; }

    public JButton getBtnConsultar() { return btnConsultar; }
    public JButton getBtnIntegrar() { return btnIntegrar; }
    public JButton getBtnReprocessar() { return btnReprocessar; }
    public JButton getBtnVisualizar() { return btnVisualizar; }
    public JButton getBtnGerarRelatorio() { return btnGerarRelatorio; }
    public JButton getBtnLimpar() { return btnLimpar; }
    public JButton getBtnConsultarHistorico() { return btnConsultarHistorico; }
    public JButton getBtnVisualizarOrigem() { return btnVisualizarOrigem; }
}
