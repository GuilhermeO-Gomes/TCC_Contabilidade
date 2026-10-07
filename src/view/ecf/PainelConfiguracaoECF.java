package view.ecf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import model.GeracaoECF;
import model.ValidacaoECF;
import view.ComponentesContabeis;

public class PainelConfiguracaoECF extends JPanel {
    private static final long serialVersionUID = 1L;
    private final JTextField txt_empresa = new JTextField(30);
    private final JTextField txt_responsavel = new JTextField(30);
    private final JSpinner txt_ano;
    private final JSpinner periodo_1;
    private final JSpinner periodo_2;
    private final JComboBox<String> cmb_tipo = new JComboBox<String>(new String[] {"Original", "Retificadora"});
    private final JComboBox<String> cmb_regime = new JComboBox<String>(new String[] {"Lucro Real", "Lucro Presumido", "Lucro Arbitrado"});
    private final JComboBox<String> cmb_forma = new JComboBox<String>(new String[] {"Trimestral", "Anual"});

    public PainelConfiguracaoECF(GeracaoECF original, Consumer<GeracaoECF> aoSalvar) {
        GeracaoECF configuracao = new GeracaoECF(original);
        txt_ano = new JSpinner(new SpinnerNumberModel(configuracao.getAnoCalendario(), 1900, 9999, 1));
        txt_ano.setEditor(new JSpinner.NumberEditor(txt_ano, "0"));
        periodo_1 = ComponentesContabeis.criarData("ecf.config.inicio", configuracao.getPeriodoInicial());
        periodo_2 = ComponentesContabeis.criarData("ecf.config.fim", configuracao.getPeriodoFinal());
        txt_empresa.setText(configuracao.getEmpresa());
        txt_responsavel.setText(configuracao.getResponsavel());
        cmb_tipo.setSelectedItem(configuracao.getTipoEscrituracao());
        cmb_regime.setSelectedItem(configuracao.getRegimeTributario());
        cmb_forma.setSelectedItem(configuracao.getFormaTributacao());
        txt_empresa.setName("ecf.config.empresa");
        txt_responsavel.setName("ecf.config.responsavel");
        txt_ano.setName("ecf.config.ano");
        cmb_tipo.setName("ecf.config.tipo");
        cmb_regime.setName("ecf.config.regime");
        cmb_forma.setName("ecf.config.forma");
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(650, 430));
        JPanel jp_form = new JPanel(new GridBagLayout());
        jp_form.setBorder(BorderFactory.createTitledBorder("Configuração da ECF demonstrativa"));
        ComponentesContabeis.adicionar_componente(0, jp_form, "Empresa:", txt_empresa);
        ComponentesContabeis.adicionar_componente(1, jp_form, "Ano-calendário:", txt_ano);
        ComponentesContabeis.adicionar_componente(2, jp_form, "Período inicial:", periodo_1);
        ComponentesContabeis.adicionar_componente(3, jp_form, "Período final:", periodo_2);
        ComponentesContabeis.adicionar_componente(4, jp_form, "Tipo de escrituração:", cmb_tipo);
        ComponentesContabeis.adicionar_componente(5, jp_form, "Regime tributário:", cmb_regime);
        ComponentesContabeis.adicionar_componente(6, jp_form, "Forma de tributação:", cmb_forma);
        ComponentesContabeis.adicionar_componente(7, jp_form, "Responsável:", txt_responsavel);
        add(jp_form, BorderLayout.CENTER);
        txt_ano.addChangeListener(e -> {
            int ano = ((Number) txt_ano.getValue()).intValue();
            ComponentesContabeis.definirData(periodo_1, LocalDate.of(ano, 1, 1));
            ComponentesContabeis.definirData(periodo_2, LocalDate.of(ano, 12, 31));
        });
        add(ComponentesContabeis.botoes(
                ComponentesContabeis.botao("ecf.config.salvar", "Salvar configuração", () -> {
                    try {
                        txt_ano.commitEdit();
                    } catch (java.text.ParseException problema) {
                        throw new IllegalArgumentException("Informe um ano válido.");
                    }
                    configuracao.setEmpresa(txt_empresa.getText().trim());
                    configuracao.setResponsavel(txt_responsavel.getText().trim());
                    configuracao.setAnoCalendario(((Number) txt_ano.getValue()).intValue());
                    configuracao.setPeriodoInicial(ComponentesContabeis.data(periodo_1));
                    configuracao.setPeriodoFinal(ComponentesContabeis.data(periodo_2));
                    configuracao.setTipoEscrituracao((String) cmb_tipo.getSelectedItem());
                    configuracao.setRegimeTributario((String) cmb_regime.getSelectedItem());
                    configuracao.setFormaTributacao((String) cmb_forma.getSelectedItem());
                    String problema = configuracao.verificarConfiguracao();
                    if (!problema.isEmpty()) {
                        throw new IllegalArgumentException(problema);
                    }
                    configuracao.setValidacoes(new ArrayList<ValidacaoECF>());
                    configuracao.setDataGeracao(null);
                    configuracao.setSituacao("Não validada");
                    aoSalvar.accept(configuracao);
                    ComponentesContabeis.fechar(this);
                }),
                ComponentesContabeis.botao("ecf.config.cancelar", "Cancelar", () -> ComponentesContabeis.fechar(this))), BorderLayout.SOUTH);
    }
}
