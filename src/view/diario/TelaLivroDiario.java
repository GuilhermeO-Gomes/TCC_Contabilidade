package view.diario;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import model.DadosContabeis;
import model.ItemLancamento;
import model.LancamentoContabil;
import view.ComponentesContabeis;

public class TelaLivroDiario extends JPanel {
    private static final long serialVersionUID = 1L;
    private final DadosContabeis dados;
    private final Runnable aoAlterar;
    private LocalDate inicioAplicado = LocalDate.now().withDayOfYear(1);
    private LocalDate fimAplicado = LocalDate.now().withMonth(12).withDayOfMonth(31);
    private String contaAplicada = "";
    private String documentoAplicado = "";
    private String historicoAplicado = "";
    private final List<LancamentoContabil> exibidos = new ArrayList<LancamentoContabil>();
    private final JSpinner periodo_1 = ComponentesContabeis.criarData("diario.inicio", LocalDate.now().withDayOfYear(1));
    private final JSpinner periodo_2 = ComponentesContabeis.criarData("diario.fim", LocalDate.now().withMonth(12).withDayOfMonth(31));
    private final JTextField txt_conta = new JTextField(20);
    private final JTextField txt_documento = new JTextField(20);
    private final JTextField txt_historico = new JTextField(30);
    private final DefaultTableModel modeloTabela = ComponentesContabeis.modeloTabela("ID", "Data", "Documento", "Histórico", "Débitos", "Créditos", "Situação");
    private final JTable tabelaLancamentos = ComponentesContabeis.tabela("diario.tabela", modeloTabela);

    public TelaLivroDiario() {
        this(new DadosContabeis(), () -> { });
    }

    public TelaLivroDiario(DadosContabeis dados, Runnable aoAlterar) {
        this.dados = dados;
        this.aoAlterar = aoAlterar;
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        montar_tela();
        atualizar_tabela();
    }

    private void montar_tela() {
        JPanel jp_form = new JPanel(new GridBagLayout());
        jp_form.setBorder(BorderFactory.createTitledBorder("Livro Diário — filtros e pesquisa"));
        txt_conta.setName("diario.conta");
        txt_documento.setName("diario.documento");
        txt_historico.setName("diario.historico");
        ComponentesContabeis.adicionar_componente(0, jp_form, "Período inicial:", periodo_1);
        ComponentesContabeis.adicionar_componente(1, jp_form, "Período final:", periodo_2);
        ComponentesContabeis.adicionar_componente(2, jp_form, "Conta:", txt_conta);
        ComponentesContabeis.adicionar_componente(3, jp_form, "Documento:", txt_documento);
        ComponentesContabeis.adicionar_componente(4, jp_form, "Histórico:", txt_historico);
        JPanel jp_pesquisa = new JPanel(new BorderLayout());
        jp_pesquisa.add(jp_form, BorderLayout.CENTER);
        jp_pesquisa.add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("diario.pesquisar", "Pesquisar", () -> pesquisar()),
                ComponentesContabeis.botao("diario.limpar", "Limpar", () -> limpar())), BorderLayout.SOUTH);
        add(jp_pesquisa, BorderLayout.NORTH);
        add(ComponentesContabeis.lista("Lançamentos da sessão", tabelaLancamentos), BorderLayout.CENTER);
        add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("diario.novo", "Novo", () -> abrir_editor(null)),
                ComponentesContabeis.botao("diario.visualizar", "Visualizar", () -> ComponentesContabeis.abrirDialogo(this,
                        "Detalhes do lançamento", new PainelDetalhesLancamento(selecionado()))),
                ComponentesContabeis.botao("diario.editar", "Editar", () -> abrir_editor(selecionado())),
                ComponentesContabeis.botao("diario.excluir", "Excluir", () -> excluir())), BorderLayout.SOUTH);
    }

    private void pesquisar() {
        LocalDate inicio = ComponentesContabeis.data(periodo_1);
        LocalDate fim = ComponentesContabeis.data(periodo_2);
        ComponentesContabeis.verificarPeriodo(inicio, fim);
        inicioAplicado = inicio;
        fimAplicado = fim;
        contaAplicada = txt_conta.getText();
        documentoAplicado = txt_documento.getText();
        historicoAplicado = txt_historico.getText();
        atualizar_tabela();
    }

    public void atualizar_tabela() {
        exibidos.clear();
        modeloTabela.setRowCount(0);
        for (LancamentoContabil lancamento : dados.getLancamentos()) {
            if (!lancamento.estaNoPeriodo(inicioAplicado, fimAplicado) || !contem(lancamento.getDocumento(), documentoAplicado)
                    || !contem(lancamento.getHistorico(), historicoAplicado) || !contaCorresponde(lancamento)) {
                continue;
            }
            exibidos.add(lancamento);
            modeloTabela.addRow(new Object[] {lancamento.getId(), lancamento.getData(), lancamento.getDocumento(),
                    lancamento.getHistorico(), lancamento.calcularTotalDebitos(), lancamento.calcularTotalCreditos(), lancamento.getStatus()});
        }
    }

    private boolean contem(String valor, String filtro) {
        return valor.toLowerCase(Locale.ROOT).contains(filtro.trim().toLowerCase(Locale.ROOT));
    }

    private boolean contaCorresponde(LancamentoContabil lancamento) {
        if (contaAplicada.trim().isEmpty()) {
            return true;
        }
        for (ItemLancamento item : lancamento.getItens()) {
            if (contem(item.getConta(), contaAplicada)) {
                return true;
            }
        }
        return false;
    }

    private LancamentoContabil selecionado() {
        return exibidos.get(ComponentesContabeis.linhaSelecionada(tabelaLancamentos));
    }

    private void abrir_editor(LancamentoContabil original) {
        PainelLancamento painel = new PainelLancamento(original, salvo -> {
            if (original == null) {
                salvo.setId(dados.proximoIdLancamento());
                dados.getLancamentos().add(salvo);
            } else {
                int indice = dados.getLancamentos().indexOf(original);
                dados.getLancamentos().set(indice, salvo);
            }
            atualizar_tabela();
            aoAlterar.run();
        });
        ComponentesContabeis.abrirDialogo(this, original == null ? "Novo lançamento" : "Editar lançamento", painel);
    }

    private void excluir() {
        LancamentoContabil lancamento = selecionado();
        if (JOptionPane.showConfirmDialog(this, "Excluir o lançamento " + lancamento.getId() + " desta sessão?",
                "Excluir lançamento", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            dados.getLancamentos().remove(lancamento);
            atualizar_tabela();
            aoAlterar.run();
        }
    }

    private void limpar() {
        txt_conta.setText("");
        txt_documento.setText("");
        txt_historico.setText("");
        ComponentesContabeis.definirData(periodo_1, LocalDate.now().withDayOfYear(1));
        ComponentesContabeis.definirData(periodo_2, LocalDate.now().withMonth(12).withDayOfMonth(31));
        pesquisar();
    }

    public JTable getTabelaLancamentos() {
        return tabelaLancamentos;
    }
}
