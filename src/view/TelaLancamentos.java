package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.Lancamento;
import model.PartidaLancamento;
import model.TipoMovimento;

public class TelaLancamentos extends JPanel {

    private static final long serialVersionUID = 1L;

    private final JTextField txtCod = new JTextField(8),
            txtData = new JTextField(10),
            txtDoc = new JTextField(15),
            txtHist = new JTextField(25),
            txtDeb = new JTextField("0,00", 12),
            txtCred = new JTextField("0,00", 12),
            txtDif = new JTextField("0,00", 12);

    private long empresaId;
    private Lancamento selecionado;
    private final JTextField txtStatus = new JTextField("RASCUNHO", 15),
            txtOrigem = new JTextField("MANUAL", 15);
    private final JComboBox<Long> cbExercicio = new JComboBox<>(),
            cbLote = new JComboBox<>(),
            cbHist = new JComboBox<>();
    private final JButton btContabilizar = new JButton("Contabilizar");

    private final JButton btNovo = new JButton("Novo"),
            btSalvar = new JButton("Salvar"),
            btLimpar = new JButton("Limpar");


    private final PainelConsultaLancamentos consulta = new PainelConsultaLancamentos();

    private final JTabbedPane abas = new JTabbedPane();

    private final EditorPartidasLancamento partidasEditor = new EditorPartidasLancamento(
            this, txtStatus, () -> selecionado == null ? 0 : selecionado.getId(), this::atualizarTotais);

    public TelaLancamentos() {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar();
        btNovo.addActionListener(e -> limpar());
        txtStatus.setEditable(false);
        txtOrigem.setEditable(false);
        txtOrigem.setToolTipText("Indica se o lançamento foi digitado manualmente ou de onde foi gerado.");
        setStatus("RASCUNHO");
    }

    private void montar() {
        abas.addTab("Lançamento", montarLancamento());
        abas.addTab("Consulta", consulta);
        consulta.btAbrir.addActionListener(e -> {
            Lancamento lancamento = consulta.lancamentoSelecionado();
            if (lancamento != null) {
                exibirLancamento(lancamento);
                abas.setSelectedIndex(0);
            }
        });
        add(abas, BorderLayout.CENTER);

        btLimpar.addActionListener(e -> limpar());
    }

    private JPanel montarLancamento() {
        JPanel painel = new JPanel(new BorderLayout(8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JPanel dados = new JPanel(new GridBagLayout());
        dados.setBorder(BorderFactory.createTitledBorder("Dados do lançamento"));
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4, 4, 4, 4);
        g.anchor = GridBagConstraints.WEST;

        Componentes.componente(dados, g, 0, "Código:", txtCod);
        Componentes.componente(dados, g, 1, "Data (dd/mm/aaaa):", txtData);
        Componentes.componente(dados, g, 2, "Documento:", txtDoc);
        Componentes.componente(dados, g, 3, "Lote:", cbLote);
        Componentes.componente(dados, g, 4, "Histórico padrão:", cbHist);
        Componentes.componente(dados, g, 5, "Complemento:", txtHist);
        Componentes.componente(dados, g, 6, "Origem do lançamento:", txtOrigem);
        Componentes.componente(dados, g, 7, "Status:", txtStatus);
        Componentes.componente(dados, g, 8, "Exercício:", cbExercicio);
        txtCod.setEditable(false);
        txtHist.setToolTipText("Descrição complementar do lançamento.");


        JPanel acoesPartida = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        acoesPartida.add(partidasEditor.btNovaPartida);
        acoesPartida.add(partidasEditor.btEditarPartida);
        acoesPartida.add(partidasEditor.btRemover);
        JPanel topo = new JPanel(new BorderLayout(4, 4));
        topo.add(dados, BorderLayout.CENTER);
        topo.add(acoesPartida, BorderLayout.SOUTH);

        painel.add(topo, BorderLayout.NORTH);

        painel.add(partidasEditor.tabela(), BorderLayout.CENTER);

        JPanel totais = new JPanel(new GridLayout(1, 3, 8, 0));
        totais.setBorder(BorderFactory.createTitledBorder("Totais do lançamento"));
        totais.add(Componentes.montarTotal("Débito (R$)", txtDeb));
        totais.add(Componentes.montarTotal("Crédito (R$)", txtCred));
        totais.add(Componentes.montarTotal("Diferença (R$)", txtDif));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        botoes.add(btNovo);
        botoes.add(btSalvar);
        botoes.add(btContabilizar);
        botoes.add(btLimpar);

        JPanel rodape = new JPanel(new BorderLayout(4, 4));
        rodape.add(totais, BorderLayout.NORTH);
        rodape.add(botoes, BorderLayout.SOUTH);
        painel.add(rodape, BorderLayout.SOUTH);
        return painel;
    }

    public void limpar() {
        selecionado = null;
        partidasEditor.partidas.clear();
        txtCod.setText("");
        txtData.setText("");
        txtDoc.setText("");
        cbLote.setSelectedIndex(-1);
        cbHist.setSelectedIndex(-1);
        txtHist.setText("");
        partidasEditor.limparPartida();
        partidasEditor.modeloPartidas.setRowCount(0);
        txtDeb.setText("0,00");
        txtCred.setText("0,00");
        txtDif.setText("0,00");
        txtOrigem.setText("MANUAL");
        setStatus("RASCUNHO");
    }

    public JTextField getTxtCod() {
        return txtCod;
    }

    public JTextField getTxtData() {
        return txtData;
    }

    public JTextField getTxtDoc() {
        return txtDoc;
    }

    public JTextField getTxtHist() {
        return txtHist;
    }

    public JTextField getTxtValor() {
        return partidasEditor.txtValor;
    }

    public JTextField getTxtDeb() {
        return txtDeb;
    }

    public JTextField getTxtCred() {
        return txtCred;
    }

    public JTextField getTxtDif() {
        return txtDif;
    }

    public JTextField getTxtInicio() {
        return consulta.txtInicio;
    }

    public JTextField getTxtFim() {
        return consulta.txtFim;
    }

    public JTextField getTxtPesq() {
        return consulta.txtPesq;
    }

    public JComboBox<Long> getCbLote() {
        return cbLote;
    }

    public JComboBox<Long> getCbHist() {
        return cbHist;
    }

    public JComboBox<Long> getCbConta() {
        return partidasEditor.cbConta;
    }

    public JComboBox<TipoMovimento> getCbTipo() {
        return partidasEditor.cbTipo;
    }

    public JComboBox<Long> getCbCC() {
        return partidasEditor.cbCC;
    }

    public JComboBox<Long> getCbContaPesq() {
        return consulta.cbContaPesq;
    }

    public JComboBox<Long> getCbCCPesq() {
        return consulta.cbCCPesq;
    }

    public JButton getBtEstornar() {
        return consulta.btEstornar;
    }

    public JButton getBtNovo() {
        return btNovo;
    }

    public JButton getBtSalvar() {
        return btSalvar;
    }

    public JButton getBtLimpar() {
        return btLimpar;
    }

    public JButton getBtAdicionar() {
        return partidasEditor.btAdicionar;
    }

    public JButton getBtAlterar() {
        return partidasEditor.btAlterar;
    }

    public JButton getBtRemover() {
        return partidasEditor.btRemover;
    }

    public JButton getBtBuscar() {
        return consulta.btBuscar;
    }

    public JButton getBtTodos() {
        return consulta.btTodos;
    }

    public JButton getBtAbrir() {
        return consulta.btAbrir;
    }

    public JTable getTabPartidas() {
        return partidasEditor.tabelaPartidas;
    }

    public JTable getTabLancamentos() {
        return consulta.tabelaLancamentos;
    }

    public JTabbedPane getAbas() {
        return abas;
    }

    public JButton getBtNovaPartida() {
        return partidasEditor.btNovaPartida;
    }

    public JButton getBtEditarPartida() {
        return partidasEditor.btEditarPartida;
    }

    public JDialog getDialogoPartida() {
        return partidasEditor.dialogoPartida;
    }

    public void setContexto(long empresaId, String empresa) {
        this.empresaId = empresaId;
        cbExercicio.removeAllItems();
        cbLote.removeAllItems();
        cbHist.removeAllItems();
        partidasEditor.cbHistPartida.removeAllItems();
        partidasEditor.historicosPadrao = java.util.Collections.emptyMap();
        partidasEditor.contasDespesa.clear();
        partidasEditor.cbConta.removeAllItems();
        partidasEditor.cbCC.removeAllItems();
        consulta.cbContaPesq.removeAllItems();
        consulta.cbCCPesq.removeAllItems();
        consulta.carregar(empresaId, java.util.Collections.emptyList());
        limpar();
    }

    public void setStatus(String status) {
        txtStatus.setText(status);
        boolean rascunho = "RASCUNHO".equals(status);
        txtData.setEditable(rascunho);
        txtDoc.setEditable(rascunho);
        txtHist.setEditable(rascunho);
        cbExercicio.setEnabled(rascunho);
        cbLote.setEnabled(rascunho);
        cbHist.setEnabled(rascunho);
        partidasEditor.cbHistPartida.setEnabled(rascunho);
        partidasEditor.cbConta.setEnabled(rascunho);
        partidasEditor.atualizarCentroCusto();
        partidasEditor.cbTipo.setEnabled(rascunho);
        partidasEditor.txtValor.setEditable(rascunho);
        partidasEditor.txtDescricaoPartida.setEditable(rascunho);
        btSalvar.setEnabled(rascunho);
        btContabilizar.setEnabled(rascunho);
        partidasEditor.btNovaPartida.setEnabled(rascunho);
        partidasEditor.btEditarPartida.setEnabled(rascunho);
        partidasEditor.btRemover.setEnabled(rascunho);
        partidasEditor.btAdicionar.setEnabled(rascunho);
        partidasEditor.btAlterar.setEnabled(rascunho && partidasEditor.tabelaPartidas.getSelectedRow() != -1);
    }

    public long getEmpresaId() {
        return empresaId;
    }

    public JComboBox<Long> getCbExercicio() {
        return cbExercicio;
    }

    public JTextField getTxtStatus() {
        return txtStatus;
    }

    public JTextField getTxtOrigem() {
        return txtOrigem;
    }

    public JTextField getTxtDescricaoPartida() {
        return partidasEditor.txtDescricaoPartida;
    }

    public JComboBox<String> getCbStatusPesq() {
        return consulta.cbStatusPesq;
    }

    public JButton getBtContabilizar() {
        return btContabilizar;
    }

    public void carregarCombo(JComboBox<Long> combo, Map<Long, String> opcoes) {
        final Map<Long, String> descricoes = new java.util.LinkedHashMap<>(opcoes);
        Componentes.mostrarDescricoes(combo, descricoes);
        DefaultComboBoxModel<Long> modelo = new DefaultComboBoxModel<>(descricoes.keySet().toArray(new Long[0]));
        modelo.setSelectedItem(null);
        combo.setModel(modelo);
        if (combo == partidasEditor.cbConta) {
            partidasEditor.atualizarCentroCusto();
        }
        if (combo == cbHist) {
            partidasEditor.historicosPadrao = descricoes;
            carregarCombo(partidasEditor.cbHistPartida, descricoes);
        }
    }

    private void atualizarTotais() {
        Lancamento lancamento = new Lancamento();
        lancamento.setPartidas(partidasEditor.partidas);
        java.text.NumberFormat formato = java.text.NumberFormat.getNumberInstance(new java.util.Locale("pt", "BR"));
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        txtDeb.setText(formato.format(lancamento.getTotalDebitos()));
        txtCred.setText(formato.format(lancamento.getTotalCreditos()));
        txtDif.setText(formato.format(lancamento.getTotalDebitos().subtract(lancamento.getTotalCreditos())));
    }

    public Lancamento lerLancamento() {
        if (!"RASCUNHO".equals(txtStatus.getText())) {
            throw new IllegalArgumentException("Somente rascunhos podem ser alterados.");
        }
        if (empresaId <= 0 || cbExercicio.getSelectedItem() == null
                || txtDoc.getText().trim().isEmpty() || txtHist.getText().trim().isEmpty()) {
            throw new IllegalArgumentException("Informe empresa, exercício, documento e descrição.");
        }
        if (txtDoc.getText().trim().length() > 50 || txtHist.getText().trim().length() > 255) {
            throw new IllegalArgumentException("Documento ou descrição excedem o tamanho permitido.");
        }
        Lancamento lancamento = new Lancamento();
        try {
            java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(java.time.format.ResolverStyle.STRICT);
            lancamento.setDataLancamento(java.time.LocalDate.parse(txtData.getText().trim(), formato));
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException("Informe uma data válida no formato dd/mm/aaaa.");
        }
        if (selecionado != null) {
            lancamento.setId(selecionado.getId());
            lancamento.setCriadoPor(selecionado.getCriadoPor());
            lancamento.setCriadoEm(selecionado.getCriadoEm());
            lancamento.setEstornoDeId(selecionado.getEstornoDeId());
        }
        lancamento.setEmpresaId(empresaId);
        lancamento.setExercicioSocialId((Long) cbExercicio.getSelectedItem());
        lancamento.setNumeroDocumento(txtDoc.getText().trim());
        lancamento.setDescricao(txtHist.getText().trim());
        lancamento.setOrigem(txtOrigem.getText());
        lancamento.setLoteId((Long) cbLote.getSelectedItem());
        lancamento.setHistoricoPadraoId((Long) cbHist.getSelectedItem());
        lancamento.setPartidas(new ArrayList<>(partidasEditor.partidas));
        return lancamento;
    }

    public void exibirLancamento(Lancamento lancamento) {
        if (lancamento.getEmpresaId() != empresaId) {
            throw new IllegalArgumentException("O lançamento não pertence à empresa selecionada.");
        }
        limpar();
        selecionado = lancamento;
        txtCod.setText(String.valueOf(lancamento.getId()));
        txtData.setText(lancamento.getDataLancamento().format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtDoc.setText(lancamento.getNumeroDocumento());
        txtHist.setText(lancamento.getDescricao());
        txtOrigem.setText(lancamento.getOrigem());
        Componentes.selecionarItem(cbExercicio, lancamento.getExercicioSocialId());
        Componentes.selecionarItem(cbLote, lancamento.getLoteId());
        Componentes.selecionarItem(cbHist, lancamento.getHistoricoPadraoId());
        for (PartidaLancamento partida : lancamento.getPartidas()) {
            PartidaLancamento copia = new PartidaLancamento(partida.getId(), partida.getLancamentoId(),
                    partida.getContaId(), partida.getCentroCustoId(), partida.getTipoMovimento(),
                    partida.getValor(), partida.getDescricao());
            partidasEditor.partidas.add(copia);
            partidasEditor.modeloPartidas.addRow(partidasEditor.dadosPartida(copia));
        }
        atualizarTotais();
        setStatus(lancamento.getStatus());
    }


    public void limparPartida() {
        partidasEditor.limparPartida();
    }

    public void setContasDespesa(Collection<Long> ids) {
        partidasEditor.setContasDespesa(ids);
    }

    public PartidaLancamento lerPartida() {
        return partidasEditor.lerPartida();
    }

    public void carregarLancamentos(List<Lancamento> registros) {
        consulta.carregar(empresaId, registros);
    }

}
