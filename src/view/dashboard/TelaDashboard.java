package view.dashboard;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import model.DadosContabeis;
import model.InconsistenciaAuditoria;
import model.LancamentoContabil;
import model.StatusAuditoria;
import model.StatusLancamento;
import view.ComponentesContabeis;

public class TelaDashboard extends JPanel {
    private static final long serialVersionUID = 1L;
    private final DadosContabeis dados;
    private LocalDate inicioAplicado = LocalDate.now().withDayOfYear(1);
    private LocalDate fimAplicado = LocalDate.now().withMonth(12).withDayOfMonth(31);
    private final JSpinner periodo_1 = ComponentesContabeis.criarData("dashboard.inicio", LocalDate.now().withDayOfYear(1));
    private final JSpinner periodo_2 = ComponentesContabeis.criarData("dashboard.fim", LocalDate.now().withMonth(12).withDayOfMonth(31));
    private final JLabel lbl_debitos = new JLabel();
    private final JLabel lbl_creditos = new JLabel();
    private final JLabel lbl_lancamentos = new JLabel();
    private final JLabel lbl_inconsistencias = new JLabel();
    private final JLabel lbl_situacao = new JLabel();
    private final JLabel lbl_diferenca = new JLabel();
    private final JTextArea txt_pendencias = new JTextArea(5, 40);
    private final DefaultTableModel modeloTabela = ComponentesContabeis.modeloTabela("ID", "Data", "Documento", "Histórico", "Débitos", "Créditos");

    public TelaDashboard() {
        this(new DadosContabeis());
    }

    public TelaDashboard(DadosContabeis dados) {
        this.dados = dados;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar_tela();
        atualizar_tela();
    }

    private void montar_tela() {
        JPanel painelFiltros = new JPanel(new GridBagLayout());
        painelFiltros.setBorder(BorderFactory.createTitledBorder("Visão Contábil — período"));
        ComponentesContabeis.adicionar_componente(0, painelFiltros, "Período inicial:", periodo_1);
        ComponentesContabeis.adicionar_componente(1, painelFiltros, "Período final:", periodo_2);
        JPanel jp_superior = new JPanel(new BorderLayout());
        jp_superior.add(painelFiltros, BorderLayout.CENTER);
        jp_superior.add(ComponentesContabeis.botoes(ComponentesContabeis.botao("dashboard.atualizar", "Atualizar",
                () -> aplicar_periodo())), BorderLayout.SOUTH);
        add(jp_superior, BorderLayout.NORTH);

        JPanel painelIndicadores = new JPanel(new GridLayout(1, 4, 8, 8));
        painelIndicadores.add(indicador("Débitos", "dashboard.debitos", lbl_debitos));
        painelIndicadores.add(indicador("Créditos", "dashboard.creditos", lbl_creditos));
        painelIndicadores.add(indicador("Lançamentos", "dashboard.lancamentos", lbl_lancamentos));
        painelIndicadores.add(indicador("Inconsistências pendentes", "dashboard.inconsistencias", lbl_inconsistencias));
        JPanel jp_centro = new JPanel(new BorderLayout(8, 8));
        jp_centro.add(painelIndicadores, BorderLayout.NORTH);
        JPanel jp_movimentos = new JPanel(new BorderLayout(8, 8));
        JTable tabela = ComponentesContabeis.tabela("dashboard.movimentacoes", modeloTabela);
        jp_movimentos.add(ComponentesContabeis.lista("Movimentações recentes", tabela), BorderLayout.CENTER);
        JPanel painelSituacao = new JPanel(new GridBagLayout());
        painelSituacao.setBorder(BorderFactory.createTitledBorder("Situação contábil"));
        lbl_situacao.setName("dashboard.situacao");
        ComponentesContabeis.adicionar_componente(0, painelSituacao, "Balanceamento:", lbl_situacao);
        ComponentesContabeis.adicionar_componente(1, painelSituacao, "Diferença total:", lbl_diferenca);
        jp_movimentos.add(painelSituacao, BorderLayout.SOUTH);
        jp_centro.add(jp_movimentos, BorderLayout.CENTER);
        add(jp_centro, BorderLayout.CENTER);
        txt_pendencias.setName("dashboard.pendencias");
        txt_pendencias.setEditable(false);
        txt_pendencias.setLineWrap(true);
        txt_pendencias.setWrapStyleWord(true);
        txt_pendencias.setBackground(javax.swing.UIManager.getColor("Panel.background"));
        JScrollPane painelPendencias = new JScrollPane(txt_pendencias);
        painelPendencias.setBorder(BorderFactory.createTitledBorder("Pendências — consulte o Livro Diário e a Auditoria"));
        add(painelPendencias, BorderLayout.SOUTH);
    }

    private JPanel indicador(String titulo, String nome, JLabel valor) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder(titulo));
        valor.setName(nome);
        valor.setHorizontalAlignment(SwingConstants.CENTER);
        valor.setFont(valor.getFont().deriveFont(Font.BOLD, 16f));
        valor.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 8));
        painel.add(valor);
        return painel;
    }

    private void aplicar_periodo() {
        LocalDate inicio = ComponentesContabeis.data(periodo_1);
        LocalDate fim = ComponentesContabeis.data(periodo_2);
        ComponentesContabeis.verificarPeriodo(inicio, fim);
        inicioAplicado = inicio;
        fimAplicado = fim;
        atualizar_tela();
    }

    public void atualizar_tela() {
        // Atualizações de outras páginas usam o último filtro aplicado, sem ler texto ainda em edição.
        LocalDate inicio = inicioAplicado;
        LocalDate fim = fimAplicado;
        List<LancamentoContabil> ativos = new ArrayList<LancamentoContabil>();
        BigDecimal debitos = BigDecimal.ZERO, creditos = BigDecimal.ZERO;
        int desequilibrados = 0;
        for (LancamentoContabil lancamento : dados.getLancamentos()) {
            if (lancamento.getStatus() != StatusLancamento.CANCELADO && lancamento.estaNoPeriodo(inicio, fim)) {
                ativos.add(lancamento);
                debitos = debitos.add(lancamento.calcularTotalDebitos());
                creditos = creditos.add(lancamento.calcularTotalCreditos());
                if (lancamento.getItens().isEmpty() || !lancamento.estaBalanceado()) {
                    desequilibrados++;
                }
            }
        }
        List<InconsistenciaAuditoria> achados = InconsistenciaAuditoria.analisarLancamentos(ativos, inicio, fim);
        int pendentes = 0;
        StringBuilder pendencias = new StringBuilder();
        for (InconsistenciaAuditoria achado : achados) {
            for (InconsistenciaAuditoria existente : dados.getInconsistencias()) {
                if (existente.getChave().equals(achado.getChave())) {
                    achado.setStatus(existente.getStatus());
                    break;
                }
            }
            if (achado.getStatus() == StatusAuditoria.PENDENTE) {
                pendentes++;
                pendencias.append(achado.getCodigo()).append(" | Lançamento ").append(achado.getLancamentoId())
                        .append(" | ").append(achado.getDescricao()).append('\n');
            }
        }
        lbl_debitos.setText(ComponentesContabeis.moeda(debitos));
        lbl_creditos.setText(ComponentesContabeis.moeda(creditos));
        lbl_lancamentos.setText(String.valueOf(ativos.size()));
        lbl_inconsistencias.setText(String.valueOf(pendentes));
        lbl_diferenca.setText(ComponentesContabeis.moeda(debitos.subtract(creditos)));
        lbl_situacao.setText(ativos.isEmpty() ? "Sem movimentação" : desequilibrados == 0 ? "Lançamentos balanceados"
                : desequilibrados + " lançamento(s) precisam de revisão");
        txt_pendencias.setText(pendentes == 0 ? "Nenhuma pendência no período selecionado." : pendencias.toString());
        txt_pendencias.setCaretPosition(0);
        Collections.sort(ativos, new Comparator<LancamentoContabil>() {
            @Override
            public int compare(LancamentoContabil primeiro, LancamentoContabil segundo) {
                int porData = segundo.getData().compareTo(primeiro.getData());
                return porData != 0 ? porData : Integer.compare(segundo.getId(), primeiro.getId());
            }
        });
        modeloTabela.setRowCount(0);
        for (int indice = 0; indice < Math.min(10, ativos.size()); indice++) {
            LancamentoContabil lancamento = ativos.get(indice);
            modeloTabela.addRow(new Object[] {lancamento.getId(), lancamento.getData(), lancamento.getDocumento(),
                    lancamento.getHistorico(), lancamento.calcularTotalDebitos(), lancamento.calcularTotalCreditos()});
        }
    }
}
