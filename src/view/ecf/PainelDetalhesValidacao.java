package view.ecf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.ValidacaoECF;
import view.ComponentesContabeis;

public class PainelDetalhesValidacao extends JPanel {
    private static final long serialVersionUID = 1L;

    public PainelDetalhesValidacao(ValidacaoECF validacao) {
        setLayout(new BorderLayout(8, 8));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setPreferredSize(new Dimension(650, 380));
        JPanel jp_dados = new JPanel(new GridBagLayout());
        jp_dados.setBorder(BorderFactory.createTitledBorder("Detalhes"));
        ComponentesContabeis.adicionar_componente(0, jp_dados, "Código:", new JLabel(validacao.getCodigo()));
        ComponentesContabeis.adicionar_componente(1, jp_dados, "Tipo:", new JLabel(validacao.getTipo().toString()));
        ComponentesContabeis.adicionar_componente(2, jp_dados, "Categoria:", new JLabel(validacao.getCategoria()));
        ComponentesContabeis.adicionar_componente(3, jp_dados, "Descrição:", ComponentesContabeis.texto(validacao.getDescricao()));
        ComponentesContabeis.adicionar_componente(4, jp_dados, "Mensagem:", ComponentesContabeis.texto(validacao.getMensagem()));
        ComponentesContabeis.adicionar_componente(5, jp_dados, "Situação:", new JLabel(validacao.isAprovado() ? "Aprovada" : "Requer atenção"));
        add(jp_dados, BorderLayout.CENTER);
        add(ComponentesContabeis.botoes(ComponentesContabeis.botao("ecf.detalhes.fechar", "Fechar",
                () -> ComponentesContabeis.fechar(this))), BorderLayout.SOUTH);
    }
}
