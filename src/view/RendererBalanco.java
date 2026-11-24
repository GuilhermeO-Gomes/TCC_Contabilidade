
package view;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class RendererBalanco extends DefaultTableCellRenderer {

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

    
        LinhaBalanco item =
                (LinhaBalanco) tabela.getValueAt(linha, 0);

        
        setFont(new Font("Arial", Font.PLAIN, 13));
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

       
        if (coluna == 0) {

            setHorizontalAlignment(SwingConstants.LEFT);

            setText(item.getDescricao());

           
            if (item.getTipo() == LinhaBalanco.Tipo.GRUPO) {

                setBorder(
                    BorderFactory.createEmptyBorder(0, 8, 0, 0)
                );

            } else if (item.getTipo() == LinhaBalanco.Tipo.SUBGRUPO) {

                setBorder(
                    BorderFactory.createEmptyBorder(0, 23, 0, 0)
                );

            } else {

                setBorder(
                    BorderFactory.createEmptyBorder(0, 38, 0, 0)
                );
            }

        } else {

            setHorizontalAlignment(SwingConstants.RIGHT);

            setBorder(
                BorderFactory.createEmptyBorder(0, 0, 0, 12)
            );
        }

       
        if (item.getTipo() == LinhaBalanco.Tipo.GRUPO) {

            setFont(new Font("Arial", Font.BOLD, 14));

            setBackground(new Color(242, 189, 61));

        } else if (item.getTipo() == LinhaBalanco.Tipo.SUBGRUPO) {

            setFont(new Font("Arial", Font.BOLD, 13));

            setBackground(new Color(235, 238, 242));
        }

       
        if (selecionado) {

            setBackground(tabela.getSelectionBackground());

            setForeground(tabela.getSelectionForeground());
        }

        return this;
    }
}
