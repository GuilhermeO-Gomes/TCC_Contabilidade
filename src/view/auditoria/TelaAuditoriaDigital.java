package view.auditoria;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import model.DadosContabeis;
import model.InconsistenciaAuditoria;
import model.NivelSeveridade;
import model.StatusAuditoria;
import view.ComponentesContabeis;

public class TelaAuditoriaDigital extends JPanel {
    private static final long serialVersionUID = 1L;
    private final DadosContabeis dados;
    private final Runnable aoAlterar;
    private LocalDate inicioAplicado = LocalDate.now().withDayOfYear(1);
    private LocalDate fimAplicado = LocalDate.now().withMonth(12).withDayOfMonth(31);
    private final JSpinner periodo_1 = ComponentesContabeis.criarData("auditoria.inicio", LocalDate.now().withDayOfYear(1));
    private final JSpinner periodo_2 = ComponentesContabeis.criarData("auditoria.fim", LocalDate.now().withMonth(12).withDayOfMonth(31));
    private final JComboBox<Object> cmb_severidade = new JComboBox<Object>(new Object[] {"Todas", NivelSeveridade.CRITICO, NivelSeveridade.ALERTA, NivelSeveridade.INFORMACAO});
    private final JComboBox<Object> cmb_status = new JComboBox<Object>(new Object[] {"Todos", StatusAuditoria.PENDENTE, StatusAuditoria.REVISADO, StatusAuditoria.IGNORADO});
    private final JLabel lbl_criticos = new JLabel();
    private final JLabel lbl_alertas = new JLabel();
    private final JLabel lbl_informacoes = new JLabel();
    private final List<InconsistenciaAuditoria> exibidos = new ArrayList<InconsistenciaAuditoria>();
    private final DefaultTableModel modeloTabela = ComponentesContabeis.modeloTabela("Severidade", "Código", "Data", "Conta", "Lançamento", "Descrição", "Status");
    private final JTable tabelaAuditoria = ComponentesContabeis.tabela("auditoria.tabela", modeloTabela);

    public TelaAuditoriaDigital() {
        this(new DadosContabeis(), () -> { });
    }

    public TelaAuditoriaDigital(DadosContabeis dados, Runnable aoAlterar) {
        this.dados = dados;
        this.aoAlterar = aoAlterar;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar_tela();
        executar_auditoria();
    }

    private void montar_tela() {
        JPanel jp_filtros = new JPanel(new GridBagLayout());
        jp_filtros.setBorder(BorderFactory.createTitledBorder("Auditoria Digital — checagens acadêmicas"));
        cmb_severidade.setName("auditoria.severidade");
        cmb_status.setName("auditoria.status");
        ComponentesContabeis.adicionar_componente(0, jp_filtros, "Período inicial:", periodo_1);
        ComponentesContabeis.adicionar_componente(1, jp_filtros, "Período final:", periodo_2);
        ComponentesContabeis.adicionar_componente(2, jp_filtros, "Severidade:", cmb_severidade);
        ComponentesContabeis.adicionar_componente(3, jp_filtros, "Status:", cmb_status);
        JPanel jp_topo = new JPanel(new BorderLayout());
        jp_topo.add(jp_filtros, BorderLayout.CENTER);
        jp_topo.add(ComponentesContabeis.botoes(ComponentesContabeis.botao("auditoria.executar", "Executar Auditoria", () -> {
            executar_auditoria();
            aoAlterar.run();
        })), BorderLayout.SOUTH);
        add(jp_topo, BorderLayout.NORTH);
        JPanel jp_indicadores = new JPanel(new GridLayout(1, 3, 8, 8));
        jp_indicadores.add(indicador("Críticos", "auditoria.criticos", lbl_criticos));
        jp_indicadores.add(indicador("Alertas", "auditoria.alertas", lbl_alertas));
        jp_indicadores.add(indicador("Informações", "auditoria.informacoes", lbl_informacoes));
        JPanel jp_lista = new JPanel(new BorderLayout(8, 8));
        jp_lista.add(jp_indicadores, BorderLayout.NORTH);
        jp_lista.add(ComponentesContabeis.lista("Inconsistências encontradas", tabelaAuditoria), BorderLayout.CENTER);
        add(jp_lista, BorderLayout.CENTER);
        add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("auditoria.detalhes", "Detalhes", () -> ComponentesContabeis.abrirDialogo(this,
                        "Detalhes da auditoria", new PainelDetalhesAuditoria(selecionado(), () -> notificar_alteracao()))),
                ComponentesContabeis.botao("auditoria.revisar", "Marcar revisado", () -> {
                    selecionado().marcarRevisado();
                    notificar_alteracao();
                }),
                ComponentesContabeis.botao("auditoria.ignorar", "Ignorar", () -> {
                    selecionado().ignorar();
                    notificar_alteracao();
                })), BorderLayout.SOUTH);
        cmb_severidade.addActionListener(e -> atualizar_tabela());
        cmb_status.addActionListener(e -> atualizar_tabela());
    }

    private JPanel indicador(String titulo, String nome, JLabel valor) {
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createTitledBorder(titulo));
        valor.setName(nome);
        valor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        valor.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        painel.add(valor);
        return painel;
    }

    public void executar_auditoria() {
        LocalDate inicio = ComponentesContabeis.data(periodo_1);
        LocalDate fim = ComponentesContabeis.data(periodo_2);
        ComponentesContabeis.verificarPeriodo(inicio, fim);
        inicioAplicado = inicio;
        fimAplicado = fim;
        // Analisar a sessão toda conserva revisões ao alternar entre períodos.
        dados.atualizarInconsistencias(LocalDate.MIN, LocalDate.MAX);
        atualizar_tabela();
    }

    public void atualizar_tabela() {
        LocalDate inicio = inicioAplicado;
        LocalDate fim = fimAplicado;
        exibidos.clear();
        modeloTabela.setRowCount(0);
        int criticos = 0, alertas = 0, informacoes = 0;
        for (InconsistenciaAuditoria achado : dados.getInconsistencias()) {
            if (achado.getData().isBefore(inicio) || achado.getData().isAfter(fim)
                    || (cmb_severidade.getSelectedIndex() > 0 && cmb_severidade.getSelectedItem() != achado.getSeveridade())
                    || (cmb_status.getSelectedIndex() > 0 && cmb_status.getSelectedItem() != achado.getStatus())) {
                continue;
            }
            exibidos.add(achado);
            modeloTabela.addRow(new Object[] {achado.getSeveridade(), achado.getCodigo(), achado.getData(),
                    achado.getConta(), achado.getLancamentoId(), achado.getDescricao(), achado.getStatus()});
            if (achado.getSeveridade() == NivelSeveridade.CRITICO) {
                criticos++;
            } else if (achado.getSeveridade() == NivelSeveridade.ALERTA) {
                alertas++;
            } else {
                informacoes++;
            }
        }
        lbl_criticos.setText(String.valueOf(criticos));
        lbl_alertas.setText(String.valueOf(alertas));
        lbl_informacoes.setText(String.valueOf(informacoes));
    }

    private InconsistenciaAuditoria selecionado() {
        return exibidos.get(ComponentesContabeis.linhaSelecionada(tabelaAuditoria));
    }

    private void notificar_alteracao() {
        atualizar_tabela();
        aoAlterar.run();
    }
}
