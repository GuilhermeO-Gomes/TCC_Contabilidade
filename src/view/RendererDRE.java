
package view;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RendererDRE extends DefaultTableCellRenderer {

    private static final long serialVersionUID = 1L;

    @Override
    public Component getTableCellRendererComponent(
            JTable tabela,
            Object valor,
            boolean selecionado,
            boolean focado,
            int linha,
            int coluna) {

        super.getTableCellRendererComponent(
            tabela,
            valor,
            selecionado,
            focado,
            linha,
            coluna
        );

        setFont(
            tabela.getFont()
        );

        if (tabela.getValueAt(linha, 0) != null) {
            String descricao = tabela.getValueAt(linha, 0).toString().trim();

            if (
                descricao.equals("RECEITA BRUTA") ||
                descricao.equals("DEDUÇÕES DAS RECEITAS") ||
                descricao.equals("RECEITA LÍQUIDA") ||
                descricao.equals("CUSTOS") ||
                descricao.equals("LUCRO BRUTO") ||
                descricao.equals("DESPESAS OPERACIONAIS") ||
                descricao.equals("LUCRO OPERACIONAL") ||
                descricao.equals("RESULTADO DO EXERCÍCIO")
            ) {

                setFont(
                    getFont().deriveFont(Font.BOLD)
                );
            }
        }

        return this;
    }
}
