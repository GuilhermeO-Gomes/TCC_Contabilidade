package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.Bem;

public class TelaBens extends JPanel {

    private static final long serialVersionUID = 1L;

    private static final DateTimeFormatter DATA = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private final JButton btSalvar = new JButton("Salvar");
    private final JTextField txtEmpresa = new JTextField(24);
    private final JComboBox<Long> cbCategoria = new JComboBox<>();
    private final JTextField txtCod = new JTextField(16);
    private final JTextField txtDesc = new JTextField(32);
    private final JTextField txtAquisicao = new JTextField(10);
    private final JTextField txtValor = new JTextField(12);
    private final JTextField txtResidual = new JTextField("0,00", 12);
    private final JTextField txtVidaUtil = new JTextField(8);
    private final JComboBox<String> cbMetodo = new JComboBox<>(new String[] {
        "Linear", "Saldos decrescentes", "Unidades produzidas", "Outro"
    });

    public TelaBens() {
        super(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel identificacao = secao("Identificação do bem");
        JPanel aquisicao = secao("Aquisição");
        JPanel depreciacao = secao("Depreciação");

        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(3, 6, 3, 6);
        g.anchor = GridBagConstraints.WEST;

        Componentes.componente(identificacao, g, 0, "Código da empresa", txtEmpresa);
        Componentes.componente(identificacao, g, 1, "Tipo de bem", cbCategoria);
        Componentes.componente(identificacao, g, 2, "Código patrimonial", txtCod);
        Componentes.componente(identificacao, g, 3, "Descrição do bem", txtDesc);
        Componentes.componente(aquisicao, g, 0, "Data de aquisição (dd/mm/aaaa)", txtAquisicao);
        Componentes.componente(aquisicao, g, 1, "Valor de aquisição (R$)", txtValor);
        Componentes.componente(depreciacao, g, 0, "Valor residual (R$)", txtResidual);
        Componentes.componente(depreciacao, g, 1, "Vida útil (meses)", txtVidaUtil);
        Componentes.componente(depreciacao, g, 2, "Método de depreciação", cbMetodo);

        JPanel secoes = new JPanel(new GridLayout(0, 1, 0, 8));
        secoes.add(identificacao);
        secoes.add(aquisicao);
        secoes.add(depreciacao);

        JPanel conteudo = new JPanel(new BorderLayout());
        conteudo.add(secoes, BorderLayout.NORTH);
        add(new JScrollPane(conteudo), BorderLayout.CENTER);

        JButton btLimpar = new JButton("Limpar");
        btLimpar.addActionListener(e -> limpar());
        btSalvar.setEnabled(false);

        JPanel acoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        acoes.add(btLimpar);
        acoes.add(btSalvar);
        add(acoes, BorderLayout.SOUTH);
    }

    private JPanel secao(String titulo) {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder(titulo));
        return painel;
    }

    private void limpar() {
        JTextField[] campos = {txtEmpresa, txtCod, txtDesc,
                txtAquisicao, txtValor, txtVidaUtil};
        for (JTextField campo : campos) {
            campo.setText("");
        }
        txtResidual.setText("0,00");
        cbCategoria.setSelectedIndex(-1);
        cbMetodo.setSelectedIndex(0);
        txtEmpresa.requestFocusInWindow();
    }

    public JButton getBtSalvar() {
        return btSalvar;
    }

    public void carregarCategorias(Map<Long, String> categorias) {
        Componentes.carregarCombo(cbCategoria, categorias);
    }

    public Bem lerBem() {
        Long categoriaId = (Long) cbCategoria.getSelectedItem();
        if (categoriaId == null || categoriaId <= 0) {
            throw new IllegalArgumentException("Selecione uma categoria cadastrada.");
        }
        Bem bem = new Bem();
        bem.setCategoriaId(categoriaId);
        bem.setCodigoPatrimonial(txtCod.getText().trim());
        bem.setDescricao(txtDesc.getText().trim());
        if (bem.getCodigoPatrimonial().isEmpty() || bem.getCodigoPatrimonial().length() > 50
                || bem.getDescricao().isEmpty() || bem.getDescricao().length() > 200) {
            throw new IllegalArgumentException("Confira o código patrimonial e a descrição.");
        }

        try {
            bem.setEmpresaId(Long.parseLong(txtEmpresa.getText().trim()));
            bem.setVidaUtilMeses(Integer.parseInt(txtVidaUtil.getText().trim()));
            bem.setValorAquisicao(lerValor(txtValor));
            bem.setValorResidual(lerValor(txtResidual));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Confira o código da empresa, a vida útil e os valores.");
        }
        if (bem.getEmpresaId() <= 0 || bem.getVidaUtilMeses() <= 0
                || bem.getValorAquisicao().signum() <= 0 || bem.getValorResidual().signum() < 0
                || bem.getValorResidual().compareTo(bem.getValorAquisicao()) > 0) {
            throw new IllegalArgumentException("Informe código da empresa, vida útil e valor de aquisição positivos. "
                    + "O valor residual deve ficar entre zero e o valor de aquisição.");
        }

        try {
            bem.setDataAquisicao(LocalDate.parse(txtAquisicao.getText().trim(), DATA));
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Informe datas válidas no formato dd/mm/aaaa.");
        }

        String[] metodos = {"LINEAR", "SALDOS_DECRESCENTES", "UNIDADES_PRODUZIDAS", "OUTRO"};
        bem.setMetodoDepreciacao(metodos[cbMetodo.getSelectedIndex()]);
        return bem;
    }

    private BigDecimal lerValor(JTextField campo) {
        String valor = campo.getText().trim();
        if (!valor.matches("[0-9]{1,13}(,[0-9]{1,2})?")) {
            throw new NumberFormatException();
        }
        return new BigDecimal(valor.replace(',', '.'));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame janela = new JFrame("Cadastro de bens");
            janela.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            TelaBens tela = new TelaBens();
            Map<Long, String> exemplos = new LinkedHashMap<>();
            exemplos.put(-1L, "Veículo");
            exemplos.put(-2L, "Imóvel");
            exemplos.put(-3L, "Ferramenta");
            exemplos.put(-4L, "Máquina ou equipamento");
            exemplos.put(-5L, "Móvel ou utensílio");
            exemplos.put(-6L, "Outro");
            tela.carregarCategorias(exemplos);
            janela.setContentPane(tela);
            janela.pack();
            janela.setLocationRelativeTo(null);
            janela.setVisible(true);
        });
    }
}
