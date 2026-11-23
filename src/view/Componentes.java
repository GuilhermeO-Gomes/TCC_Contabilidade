package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

final class Componentes {

    private Componentes() {
    }

    static void componente(JPanel painel, GridBagConstraints organizador,
            int linha, String texto, Component campo) {
        organizador.gridx = 0;
        organizador.gridy = linha;
        organizador.gridwidth = 1;
        organizador.weightx = 0;
        organizador.fill = GridBagConstraints.NONE;
        JLabel rotulo = new JLabel(texto);
        rotulo.setLabelFor(campo);
        painel.add(rotulo, organizador);

        organizador.gridx = 1;
        organizador.weightx = 1;
        organizador.fill = GridBagConstraints.HORIZONTAL;
        painel.add(campo, organizador);
    }

    static JPanel montarTotal(String titulo, JTextField campo) {
        JPanel painel = new JPanel(new BorderLayout(4, 4));
        painel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
        painel.add(new JLabel(titulo), BorderLayout.NORTH);
        painel.add(campo, BorderLayout.CENTER);
        campo.setEditable(false);
        campo.setHorizontalAlignment(JTextField.RIGHT);
        return painel;
    }

    static void carregarCombo(JComboBox<Long> combo, Map<Long, String> opcoes) {
        final Map<Long, String> descricoes = new java.util.LinkedHashMap<>(opcoes);
        combo.removeAllItems();
        mostrarDescricoes(combo, descricoes);
        for (Long id : descricoes.keySet()) {
            combo.addItem(id);
        }
        combo.setSelectedIndex(-1);
    }

    static void selecionarItem(JComboBox<Long> combo, Object valor) {
        combo.setSelectedIndex(-1);
        if (valor instanceof Number) {
            long id = ((Number) valor).longValue();
            for (int i = 0; i < combo.getItemCount(); i++) {
                if (combo.getItemAt(i).longValue() == id) {
                    combo.setSelectedIndex(i);
                    return;
                }
            }
        }
    }

    static void mostrarDescricoes(JComboBox<Long> combo, Map<Long, String> descricoes) {
        combo.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            public Component getListCellRendererComponent(JList<?> lista, Object valor,
                    int indice, boolean selecionado, boolean foco) {
                super.getListCellRendererComponent(lista, valor, indice, selecionado, foco);
                setText(valor == null ? "" : descricoes.get(valor));
                return this;
            }
        });
    }

    static void aoAlterar(Runnable acao, JTextComponent... campos) {
        DocumentListener listener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                acao.run();
            }

            public void removeUpdate(DocumentEvent e) {
                acao.run();
            }

            public void changedUpdate(DocumentEvent e) {
                acao.run();
            }
        };
        for (JTextComponent campo : campos) {
            campo.getDocument().addDocumentListener(listener);
        }
    }
}
